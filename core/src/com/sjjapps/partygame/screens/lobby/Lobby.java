package com.sjjapps.partygame.screens.lobby;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.sjjapps.partygame.Game;
import com.sjjapps.partygame.common.Utils;
import com.sjjapps.partygame.common.models.User;
import com.sjjapps.partygame.common.realms.DialogRealm;
import com.sjjapps.partygame.common.stages.Alert;
import com.sjjapps.partygame.common.stages.Dialog;
import com.sjjapps.partygame.managers.DataManager;
import com.sjjapps.partygame.network.NetGameState;
import com.sjjapps.partygame.network.NetUsers;
import com.sjjapps.partygame.network.NetworkHelper;
import com.sjjapps.partygame.screens.games.runaway.RunAway;
import com.sjjapps.partygame.screens.lobby.stages.UiStage;
import com.sjjapps.partygame.screens.mainmenu.MainMenu;

/**
 * Created by Shane Jansen on 12/17/15.
 */
public class Lobby extends DialogRealm implements NetworkHelper.NetworkInterface {
    private Listener mListener;
    private UiStage mUiStage;

    public Lobby() {
        super();
        // Loading assets
        UiStage.addAssets();

        Game.ASSETS.finishLoading();
        finishedLoading();
    }

    private void finishedLoading() {
        // Network setup
        Game.NETWORK_HELPER.setNetworkInterface(this);

        // UI stage
        mUiStage = new UiStage(new UiStage.UiInterface() {
            @Override
            public void btnAddSubtractClicked() {
                updateUi();
            }

            @Override
            public void btnBackClicked() {
                Game.NETWORK_HELPER.getEndPoint().close();
                changeRealm(new MainMenu());
            }

            @Override
            public void btnStartClicked() {
                NetGameState gameState = Game.NETWORK_HELPER.gameState;
                gameState.setIsStarted(true);
                Server server = (Server) Game.NETWORK_HELPER.getEndPoint();
                server.sendToAllTCP(gameState);
                changeRealm(Utils.createRealmFromName(gameState.getMiniGames().iterator().next()));
            }
        });
        addStage(mUiStage);

        // Setup server or client
        String ipAddress;
        if (Game.NETWORK_HELPER.isServer()) {
            ipAddress = Game.NETWORK_HELPER.getServerIp();
            Game.log("Server running at address: " + ipAddress);
            Game.NETWORK_HELPER.users.getUsers().add(new User(DataManager.USER_NAME));
            updateUi();
        }
        else {
            Client client = (Client) Game.NETWORK_HELPER.getEndPoint();
            ipAddress = client.getRemoteAddressTCP().getAddress().getHostAddress();
            client.sendTCP(new User(DataManager.USER_NAME));
        }
        mUiStage.getLblAddress().setText("Server IP: " + ipAddress);

        // Finalize
        addInputListeners();
    }

    /**
     * Updates the player list and visibility
     * of the start button.
     */
    private void updateUi() {
        String players = "Players:\n";
        for (User u: Game.NETWORK_HELPER.users.getUsers()) {
            players += u.getName() + "\n";
        }
        mUiStage.getLblPlayers().setText(players);
        if (Game.NETWORK_HELPER.users.getUsers().size() > 1
                && Game.NETWORK_HELPER.isServer()
                && Game.NETWORK_HELPER.gameState.getMiniGames().size() > 0
                || DataManager.DEBUG) {
            mUiStage.getBtnStart().setVisible(true);
        }
        else mUiStage.getBtnStart().setVisible(false);
    }

    @Override
    public void addServerListeners() {
        final Server server = (Server) Game.NETWORK_HELPER.getEndPoint();
        mListener = new Listener() {
            @Override
            public void received(Connection connection, Object object) {
                if (object instanceof User) {
                    // A new user has joined the game
                    Game.log("Network: A user has joined the game.");
                    User user = (User)object;
                    user.setId(connection.getID());
                    user.setScore(0);
                    Game.NETWORK_HELPER.users.getUsers().add(user);
                    server.sendToAllTCP(Game.NETWORK_HELPER.users);
                    updateUi();
                }
            }
        };
        server.addListener(mListener);
    }

    @Override
    public void addClientListeners() {
        Client client = (Client) Game.NETWORK_HELPER.getEndPoint();
        mListener = new Listener() {
            @Override
            public void received(Connection connection, Object object) {
                if (object instanceof NetUsers) {
                    Game.log("Network: Received new user list.");
                    Game.NETWORK_HELPER.setUsers((NetUsers) object);
                    // Update the list of users
                    updateUi();
                }
                if (object instanceof NetGameState) {
                    Game.log("Network: Received new GameState.");
                    Game.NETWORK_HELPER.setGameState((NetGameState) object);
                    // Check if the game has started
                    if (((NetGameState) object).isStarted()) {
                        changeRealm(new RunAway());
                    }
                }
            }
        };
        client.addListener(mListener);
    }

    @Override
    public void removeListeners() {
        Game.NETWORK_HELPER.getEndPoint().removeListener(mListener);
    }

    @Override
    public void clientDisconnected() {
        updateUi();
    }

    @Override
    public void serverDisconnected() {
        addDialog(new Alert(new Dialog.DialogInterface() {
            @Override
            public void btnExitPressed() {
                Game.NETWORK_HELPER.getEndPoint().close();
                changeRealm(new MainMenu());
            }
        }, "You have been disconnected from the server."));
    }
}

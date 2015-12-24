package com.sjjapps.partygame.screens.lobby;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.sjjapps.partygame.Game;
import com.sjjapps.partygame.common.Alert;
import com.sjjapps.partygame.common.Dialog;
import com.sjjapps.partygame.common.realms.DialogRealm;
import com.sjjapps.partygame.managers.DataManager;
import com.sjjapps.partygame.network.GameState;
import com.sjjapps.partygame.network.NetworkHelper;
import com.sjjapps.partygame.network.User;
import com.sjjapps.partygame.screens.games.bubby.Bubby;
import com.sjjapps.partygame.screens.lobby.stages.UiStage;
import com.sjjapps.partygame.screens.mainmenu.MainMenu;

/**
 * Created by Shane Jansen on 12/17/15.
 */
public class Lobby extends DialogRealm implements NetworkHelper.NetworkInterface {
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
            public void btnBackClicked() {
                Game.NETWORK_HELPER.getEndPoint().close();
                changeRealm(new MainMenu());
            }

            @Override
            public void btnStartClicked() {
                Game.NETWORK_HELPER.getGameState().started = true;
                Server server = (Server) Game.NETWORK_HELPER.getEndPoint();
                server.sendToAllTCP(Game.NETWORK_HELPER.getGameState());
                changeRealm(new Bubby());
            }
        });
        addStage(mUiStage);

        // Setup server or client
        String ipAddress;
        if (Game.NETWORK_HELPER.isServer()) {
            ipAddress = Game.NETWORK_HELPER.getServerIp();
            Game.log("Server running at address: " + ipAddress);
            Game.NETWORK_HELPER.getNetworkUsers().users.add(new User(DataManager.USER_NAME));
            updatePlayerList();
        }
        else {
            Client client = (Client) Game.NETWORK_HELPER.getEndPoint();
            ipAddress = client.getRemoteAddressTCP().getAddress().getHostAddress();
            client.sendTCP(new User(DataManager.USER_NAME));
        }
        mUiStage.getLblAddress().setText("Server IP:" + ipAddress);

        // Finalize
        addInputListeners();
    }

    private void updatePlayerList() {
        String players = "Players:\n";
        for (User u: Game.NETWORK_HELPER.getNetworkUsers().users) {
            players += u.getName() + "\n";
        }
        mUiStage.getLblPlayers().setText(players);
        if (Game.NETWORK_HELPER.getNetworkUsers().users.size() > 1
                && Game.NETWORK_HELPER.isServer()) {
            mUiStage.getBtnStart().setVisible(true);
        }
        else {
            mUiStage.getBtnStart().setVisible(false);
        }
    }

    @Override
    public void addServerListeners() {
        final Server server = (Server) Game.NETWORK_HELPER.getEndPoint();
        server.addListener(new Listener() {
            @Override
            public void received(Connection connection, Object object) {
                if (object instanceof User) {
                    // A new user has joined the game
                    Game.log("A user has joined the game.");
                    User user = (User)object;
                    user.setId(connection.getID());
                    user.setScore(0);
                    Game.NETWORK_HELPER.getNetworkUsers().users.add(user);
                    server.sendToAllTCP(Game.NETWORK_HELPER.getNetworkUsers());
                    updatePlayerList();
                }
            }
        });
    }

    @Override
    public void addClientListeners() {
        Client client = (Client) Game.NETWORK_HELPER.getEndPoint();
        client.addListener(new Listener() {
            @Override
            public void received(Connection connection, Object object) {
                if (object instanceof User.NetworkUsers) {
                    // Update the list of users
                    Game.log("Received new user list.");
                    Game.NETWORK_HELPER.setNetworkUsers((User.NetworkUsers) object);
                    updatePlayerList();
                }
                if (object instanceof GameState) {
                    // Check if the game has started
                    if (((GameState) object).started) {
                        Game.log("Game started.");
                        changeRealm(new Bubby());
                    }
                }
            }
        });
    }

    @Override
    public void clientDisconnected() {
        updatePlayerList();
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

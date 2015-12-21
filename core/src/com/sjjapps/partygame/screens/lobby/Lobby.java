package com.sjjapps.partygame.screens.lobby;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;
import com.sjjapps.partygame.Game;
import com.sjjapps.partygame.common.DialogRealm;
import com.sjjapps.partygame.managers.DataManager;
import com.sjjapps.partygame.network.NetworkHelper;
import com.sjjapps.partygame.network.User;
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
        Log.set(Log.LEVEL_DEBUG);
        Game.NETWORK_HELPER.setNetworkInterface(this);

        // UI Stage
        mUiStage = new UiStage(new UiStage.UiInterface() {
            @Override
            public void btnBackClicked() {
                Game.GAME.setScreen(new MainMenu());
                dispose();
            }
        });
        addStage(mUiStage);

        // Setup server or client
        String ipAddress = "";
        if (Game.NETWORK_HELPER.isServer()) {
            ipAddress = Game.NETWORK_HELPER.getServerIp();
            Game.log("Server running at address: " + ipAddress);
            Game.NETWORK_HELPER.getNetworkUsers().users.add(new User(DataManager.USER_NAME));
            mUiStage.updatePlayerList();
        }
        else {
            Client client = (Client) Game.NETWORK_HELPER.getEndPoint();
            ipAddress = client.getRemoteAddressTCP().getAddress().getHostAddress();
            client.sendTCP(new User(DataManager.USER_NAME));
        }
        mUiStage.updateIpAddress(ipAddress);

        // Finalize
        addInputListeners();
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
                    mUiStage.updatePlayerList();
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
                    mUiStage.updatePlayerList();
                }
            }
        });
    }

    @Override
    public void clientDisconnected() {
        mUiStage.updatePlayerList();
    }

    @Override
    public void serverDisconnected() {
        dispose();
    }
}

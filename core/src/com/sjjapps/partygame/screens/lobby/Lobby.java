package com.sjjapps.partygame.screens.lobby;

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

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;

/**
 * Created by Shane Jansen on 12/17/15.
 */
public class Lobby extends DialogRealm {

    public Lobby() {
        super();
        // Loading assets
        UiStage.addAssets();

        Game.ASSETS.finishLoading();
        finishedLoading();
    }

    private void finishedLoading() {
        Log.set(Log.LEVEL_DEBUG);
        // Check if server or client
        String ipAddress = "";
        if (Game.CLIENT == null) {
            // Server
            ipAddress = startServer();
            Game.log("Server running at address: " + ipAddress);
            if (ipAddress == null) {
                // Server could not be started; Return to menu
                Game.GAME.setScreen(new MainMenu());
                dispose();
            }
            else {
                NetworkHelper.registerEndpoint(Game.SERVER);
                NetworkHelper.USERS.users.add(new User(DataManager.USER_NAME));

                // TEST
                Game.SERVER.addListener(new Listener() {
                    @Override
                    public void received(Connection connection, Object object) {
                        if (object instanceof User) {
                            // A new user has joined the game
                            Game.log("A user has joined the game.");
                            User user = (User)object;
                            user.setId(connection.getID());
                            user.setScore(0);
                            NetworkHelper.USERS.users.add(user);
                            Game.SERVER.sendToAllTCP(NetworkHelper.USERS);
                        }
                    }

                    @Override
                    public void disconnected(Connection connection) {
                        // TODO: Check who disconnected based on connection id
                        super.disconnected(connection);
                    }
                });
            }
        }
        else {
            // Client
            NetworkHelper.registerEndpoint(Game.CLIENT);
            Game.CLIENT.sendTCP(new User(DataManager.USER_NAME));

            // TEST
            Game.CLIENT.addListener(new Listener() {
                @Override
                public void received(Connection connection, Object object) {
                    if (object instanceof User.Users) {
                        // Update the list of users
                        Game.log("Received new user list.");
                        NetworkHelper.USERS = (User.Users) object;
                    }
                }

                @Override
                public void disconnected(Connection connection) {
                    super.disconnected(connection);
                }
            });
        }

        // UI Stage
        addStage(new UiStage(new UiStage.UiInterface() {
            @Override
            public void btnBackClicked() {
                Game.GAME.setScreen(new MainMenu());
                dispose();
            }
        }, ipAddress));
    }

    /**
     * Starts a server on the specified TCP and UDP ports.
     * @return IP address of the running server
     */
    private String startServer() {
        Game.SERVER = new Server();
        Game.SERVER.start();
        try {
            Game.SERVER.bind(NetworkHelper.TCP_PORT, NetworkHelper.UDP_PORT);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        // Get the ip address of the server
        try {
            for (NetworkInterface iface : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                for (InetAddress address : Collections.list(iface.getInetAddresses())) {
                    if(!address.isLoopbackAddress() && !address.isLinkLocalAddress()) {
                        return address.getHostAddress().trim();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "unknown";
    }

    /*private void addListeners() {
        Game.SERVER.addListener(new Listener() {
            @Override
            public void received(Connection connection, Object object) {
                Gdx.app.log("TEST", "RECEIVED DATA");
            }
        });
    }*/
}

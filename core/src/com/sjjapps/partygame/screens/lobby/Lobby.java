package com.sjjapps.partygame.screens.lobby;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.sjjapps.partygame.Game;
import com.sjjapps.partygame.common.DialogRealm;
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

        Game.ASSETS.finishLoading();
        finishedLoading();
    }

    private void finishedLoading() {
        // Check if server or client
        String ipAddress = "";
        if (Game.CLIENT == null) {
            ipAddress = startServer();
            Game.log("Server running at address: " + ipAddress);
            if (ipAddress == null) {
                // Server could not be started; Return to menu
                Game.GAME.setScreen(new MainMenu());
                dispose();
            }

            // TEST
            Game.SERVER.addListener(new Listener() {
                @Override
                public void received(Connection connection, Object object) {
                    Game.log("Something received.");
                }

                @Override
                public void disconnected(Connection connection) {
                    // TODO: Store connection object with user to handle disconnect
                    super.disconnected(connection);
                }
            });
        }
    }

    private String startServer() {
        Game.SERVER = new Server();
        Game.SERVER.start();
        try {
            Game.SERVER.bind(54555, 54777);
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

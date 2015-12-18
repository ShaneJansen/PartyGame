package com.sjjapps.partygame.screens.lobby;

import com.esotericsoftware.kryonet.Server;
import com.sjjapps.partygame.Game;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;

/**
 * Created by Shane Jansen on 12/18/15.
 */
public class ServerHandler {

    public static String startServer() {
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

    public static void addListeners() {
        /*Game.SERVER.addListener(new Listener() {
            @Override
            public void received(Connection connection, Object object) {
                Gdx.app.log("TEST", "RECEIVED DATA");
            }
        });*/
    }
}

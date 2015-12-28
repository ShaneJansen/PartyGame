package com.sjjapps.partygame.network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.sjjapps.partygame.Game;

import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Shane Jansen on 12/17/15.
 */
public class NetworkHelper {
    public static final int TCP_PORT = 54555;
    public static final int UDP_PORT = 54777;
    private NetworkInterface mNetworkInterface;
    private EndPoint mEndPoint;
    private boolean mIsServer;

    // These network objects should persist for the life of the network connection
    private User.NetworkUsers mNetworkUsers;
    private GameState mGameState;

    public interface NetworkInterface {
        void addServerListeners();
        void addClientListeners();
        void clientDisconnected();
        void serverDisconnected();
    }

    public NetworkHelper(EndPoint endPoint) {
        this.mEndPoint = endPoint;
        if (endPoint instanceof Server) mIsServer = true;
        if (endPoint instanceof Client) mIsServer = false;
        mNetworkUsers = new User.NetworkUsers();
        mGameState = new GameState();
        registerEndpoint(endPoint);
        addEndpointListener(endPoint);
    }

    private void registerEndpoint(EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(ArrayList.class);
        kryo.register(User.class);
        kryo.register(User.NetworkUsers.class);
        kryo.register(GameState.class);
        kryo.register(String[].class);
        kryo.register(Map.class);
        kryo.register(HashMap.class);
    }

    private void addEndpointListener(EndPoint endpoint) {
        endpoint.addListener(new Listener() {
            @Override
            public void received(Connection connection, Object object) {
                if (object instanceof GameState) {
                    GameState gameState = (GameState) object;
                    Game.PAUSED = gameState.paused;
                }
            }

            @Override
            public void disconnected(Connection connection) {
                if (isServer()) {
                    // Check who disconnected based on connection id
                    Game.log("INTERFACE - CLIENT DISCONNECTED");
                    Server server = (Server) getEndPoint();
                    for (User u: Game.NETWORK_HELPER.getNetworkUsers().users) {
                        if (u.getId() == connection.getID()) {
                            Game.NETWORK_HELPER.getNetworkUsers().users.remove(u);
                            server.sendToAllTCP(Game.NETWORK_HELPER.getNetworkUsers());
                            if (mNetworkInterface != null) mNetworkInterface.clientDisconnected();
                            break;
                        }
                    }
                } else {
                    Game.log("INTERFACE - SERVER DISCONNECTED");
                    if (mNetworkInterface != null) mNetworkInterface.serverDisconnected();
                }
            }
        });
    }

    /**
     * @return IP address of the running server.
     * "unknown" if the server IP could not be found.
     */
    public String getServerIp() {
        try {
            for (java.net.NetworkInterface iface : Collections.list(java.net.NetworkInterface.getNetworkInterfaces())) {
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

    /*
        Getters and setters
     */

    public EndPoint getEndPoint() {
        return mEndPoint;
    }

    public boolean isServer() {
        return mIsServer;
    }

    public User.NetworkUsers getNetworkUsers() {
        return mNetworkUsers;
    }

    public GameState getGameState() {
        return mGameState;
    }

    public void setNetworkInterface(NetworkInterface networkInterface) {
        mNetworkInterface = networkInterface;
        if (isServer()) networkInterface.addServerListeners();
        else networkInterface.addClientListeners();
    }

    public void setNetworkUsers(User.NetworkUsers networkUsers) {
        mNetworkUsers = networkUsers;
    }

    public void setGameState(GameState gameState) {
        mGameState = gameState;
    }
}

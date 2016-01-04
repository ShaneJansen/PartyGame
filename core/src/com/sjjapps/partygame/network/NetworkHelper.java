package com.sjjapps.partygame.network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.sjjapps.partygame.Game;
import com.sjjapps.partygame.common.models.MiniGame;
import com.sjjapps.partygame.common.models.User;
import com.sjjapps.partygame.screens.games.runaway.models.GameUser;

import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

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
    public Users users;
    public GameState gameState;

    public interface NetworkInterface {
        void addServerListeners();
        void addClientListeners();
        void removeListeners();
        void clientDisconnected();
        void serverDisconnected();
    }

    public NetworkHelper(EndPoint endPoint) {
        this.mEndPoint = endPoint;
        if (endPoint instanceof Server) mIsServer = true;
        if (endPoint instanceof Client) mIsServer = false;
        users = new Users();
        gameState = new GameState();
        registerEndpoint(endPoint);
        addEndpointListener(endPoint);
    }

    private void registerEndpoint(EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        // General
        kryo.register(ArrayList.class);
        // Users
        kryo.register(User.class);
        kryo.register(Users.class);
        // GameState
        kryo.register(GameState.class);
        kryo.register(MiniGame.class);
        kryo.register(MiniGame[].class);
        kryo.register(HashSet.class);
        // RunAway
        kryo.register(GameUser.class);
    }

    private void addEndpointListener(EndPoint endpoint) {
        endpoint.addListener(new Listener() {
            @Override
            public void received(Connection connection, Object object) {
                if (object instanceof GameState) {
                    GameState gameState = (GameState) object;
                    Game.PAUSED = gameState.isPaused();
                }
            }

            @Override
            public void disconnected(Connection connection) {
                if (isServer()) {
                    // Check who disconnected based on connection id
                    Game.log("INTERFACE - CLIENT DISCONNECTED");
                    Server server = (Server) getEndPoint();
                    for (User u : users.getUsers()) {
                        if (u.getId() == connection.getID()) {
                            users.getUsers().remove(u);
                            server.sendToAllTCP(users);
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

    public User findThisUser() {
        if (isServer()) {
            for (User u: users.getUsers()) {
                if (u.getId() == -1) return u;
            }
        }
        else {
            Client client = (Client) getEndPoint();
            for (User u: users.getUsers()) {
                if (u.getId() == client.getID()) return u;
            }
        }
        return null;
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

    public void setNetworkInterface(NetworkInterface networkInterface) {
        // Old listener
        if (mNetworkInterface != null) mNetworkInterface.removeListeners();

        // New listener
        mNetworkInterface = networkInterface;
        if (isServer()) networkInterface.addServerListeners();
        else networkInterface.addClientListeners();
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
}

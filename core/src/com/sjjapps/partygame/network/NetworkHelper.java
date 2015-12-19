package com.sjjapps.partygame.network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

import java.util.ArrayList;

/**
 * Created by Shane Jansen on 12/17/15.
 */
public class NetworkHelper {
    public static final int TCP_PORT = 54555;
    public static final int UDP_PORT = 54777;

    public static User.Users USERS = new User.Users();

    public static void registerEndpoint(EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(ArrayList.class);
        kryo.register(User.class);
        kryo.register(User.Users.class);
    }
}

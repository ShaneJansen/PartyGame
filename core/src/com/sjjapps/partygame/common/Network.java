package com.sjjapps.partygame.common;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

/**
 * Created by Shane Jansen on 12/17/15.
 */
public class Network {
    public static final int TCP_PORT = 54555;
    public static final int UDP_PORT = 54777;

    public static void register(EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        //kryo.register();
    }
}

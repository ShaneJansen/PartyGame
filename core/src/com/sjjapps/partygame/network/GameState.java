package com.sjjapps.partygame.network;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Shane Jansen on 12/20/15.
 */
public class GameState {
    public boolean paused;
    public boolean started;
    public String[] gameTypes = new String[]{"Run Away"};
    public Map<String, Integer> gamesMap = new HashMap<String, Integer>();

    public GameState() {}
}

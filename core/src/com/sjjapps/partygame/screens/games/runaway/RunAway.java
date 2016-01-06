package com.sjjapps.partygame.screens.games.runaway;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.sjjapps.partygame.Game;
import com.sjjapps.partygame.common.realms.GameRealm;
import com.sjjapps.partygame.network.MovablePlayer;
import com.sjjapps.partygame.screens.games.runaway.stages.GameStage;
import com.sjjapps.partygame.screens.games.runaway.stages.UiStage;

/**
 * Created by Shane Jansen on 12/21/15.
 */
public class RunAway extends GameRealm implements GameStage.GameStageInterface {
    private Listener mListener;
    private UiStage mUiStage;
    private GameStage mGameStage;

    public RunAway() {
        super();
        // Loading assets
        UiStage.addAssets();
        GameStage.addAssets();
    }

    @Override
    public void finishedLoading() {
        super.finishedLoading();

        // UI stage
        mUiStage = new UiStage();
        addStage(mUiStage);

        // MiniGame stage
        mGameStage = new GameStage(this, mUiStage.getTouchpad());
        addStage(mGameStage);

        // Finalize
        addInputListeners();
    }

    @Override
    public void playerMoved(MovablePlayer gameUser) {
        if (Game.NETWORK_HELPER.isServer()) {
            Server server = (Server) Game.NETWORK_HELPER.getEndPoint();
            server.sendToAllUDP(gameUser);
        }
        else {
            Client client = (Client) Game.NETWORK_HELPER.getEndPoint();
            client.sendUDP(gameUser);
        }
    }

    @Override
    public void addServerListeners() {
        final Server server = (Server) Game.NETWORK_HELPER.getEndPoint();
        mListener = new Listener() {
            @Override
            public void received(Connection connection, Object object) {
                if (object instanceof MovablePlayer) {
                    MovablePlayer gameUser = (MovablePlayer) object;
                    server.sendToAllUDP(gameUser);
                    mGameStage.updatePlayer(gameUser);
                }
            }
        };
        server.addListener(mListener);
    }

    @Override
    public void addClientListeners() {
        Client client = (Client) Game.NETWORK_HELPER.getEndPoint();
        mListener = new Listener() {
            @Override
            public void received(Connection connection, Object object) {
                if (object instanceof MovablePlayer) {
                    MovablePlayer gameUser = (MovablePlayer) object;
                    mGameStage.updatePlayer(gameUser);
                }
            }
        };
        client.addListener(mListener);
    }

    @Override
    public void removeListeners() {
        Game.NETWORK_HELPER.getEndPoint().removeListener(mListener);
    }

    @Override
    public void clientDisconnected() {

    }
}

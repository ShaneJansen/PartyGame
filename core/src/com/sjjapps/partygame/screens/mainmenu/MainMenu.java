package com.sjjapps.partygame.screens.mainmenu;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.sjjapps.partygame.Game;
import com.sjjapps.partygame.common.AlertDialog;
import com.sjjapps.partygame.common.AlertTextField;
import com.sjjapps.partygame.common.DialogRealm;
import com.sjjapps.partygame.common.actors.WidgetFactory;
import com.sjjapps.partygame.managers.PrefManager;
import com.sjjapps.partygame.screens.mainmenu.dialogs.AboutDialog;
import com.sjjapps.partygame.screens.mainmenu.stages.BackgroundStage;
import com.sjjapps.partygame.screens.mainmenu.stages.PencilStage;
import com.sjjapps.partygame.screens.mainmenu.stages.UiStage;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Shane Jansen on 11/17/15.
 */
public class MainMenu extends DialogRealm {
    private PencilStage mStgPencil;

    public MainMenu() {
        super();
        // Loading Assets
        WidgetFactory.initialize(Gdx.graphics.getWidth());

        UiStage.addAssets();
        PencilStage.addAssets();
        BackgroundStage.addAssets();

        AboutDialog.addAssets();
        AlertDialog.addAssets();
        AlertTextField.addAssets();

        Game.ASSETS.finishLoading(); // Blocks main thread. No loading screen.
        finishedLoading();
    }

    private void finishedLoading() {
        // Initialize
        addStage(new UiStage(new UiStage.UiInterface() {
            @Override
            public void btnHostClicked() {
                String name = Game.PREFS.getString(PrefManager.USER_NAME, null);
                AlertTextField alertTextField = new AlertTextField(MainMenu.this,
                        new AlertTextField.AlertTextFieldInterface() {
                            @Override
                            public void btnContinueClicked(String tfText) {
                                if (tfText.trim().length() != 0) {
                                    Game.PREFS.putString(PrefManager.USER_NAME, tfText);
                                    Game.PREFS.flush();
                                    startServer();
                                }
                            }
                        }, "Enter your name here.");
                addDialog(alertTextField, true);
                if (name != null) alertTextField.getTf().setDefaultText(name);
            }

            @Override
            public void btnJoinClicked() {
                final AlertDialog alertDialog = new AlertDialog(MainMenu.this, "");
                addDialog(alertDialog, false);
                new Timer().scheduleAtFixedRate(new TimerTask() {
                    String ellipsis = "Searching ";

                    @Override
                    public void run() {
                        if (ellipsis.length() == 16) ellipsis = "Searching ";
                        else ellipsis += ". ";
                        alertDialog.getLbl().setText(ellipsis);
                    }
                }, 0, 1000);
            }

            @Override
            public void btnAboutClicked() {
                addDialog(new AboutDialog(MainMenu.this), true);
            }
        }));
        mStgPencil = new PencilStage();
        addStage(mStgPencil);
        addStage(new BackgroundStage(new BackgroundStage.BackgroundInterface() {
            @Override
            public void backgroundClicked(int posX, int posY) {
                mStgPencil.addPencil(posX, posY, false);
            }
        }));

        // Finalize
        addInputListeners();
        mStgPencil.addPencil(0, 0, true);
    }

    private void startServer() {
        Game.SERVER = new Server();
        Game.SERVER.start();
        try {
            Game.SERVER.bind(54555, 54777);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Game.SERVER.addListener(new Listener() {
            @Override
            public void received(Connection connection, Object object) {
                Gdx.app.log("TEST", "RECEIVED DATA");
            }
        });
    }
}

package com.sjjapps.partygame.screens.mainmenu;

import com.badlogic.gdx.Gdx;
import com.sjjapps.partygame.Game;
import com.sjjapps.partygame.common.AlertDialog;
import com.sjjapps.partygame.common.AlertTextField;
import com.sjjapps.partygame.common.DialogRealm;
import com.sjjapps.partygame.common.actors.WidgetFactory;
import com.sjjapps.partygame.managers.DataManager;
import com.sjjapps.partygame.managers.PrefManager;
import com.sjjapps.partygame.screens.lobby.Lobby;
import com.sjjapps.partygame.screens.mainmenu.dialogs.AboutDialog;
import com.sjjapps.partygame.screens.mainmenu.stages.BackgroundStage;
import com.sjjapps.partygame.screens.mainmenu.stages.PencilStage;
import com.sjjapps.partygame.screens.mainmenu.stages.UiStage;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Shane Jansen on 11/17/15.
 */
public class MainMenu extends DialogRealm {

    public MainMenu() {
        super();
        // Loading assets
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
        // UI Stage
        addStage(new UiStage(new UiStage.UiInterface() {
            @Override
            public void btnHostClicked() {
                Game.CLIENT = null;
                showNameDialog();
            }

            @Override
            public void btnJoinClicked() {
                Game.SERVER = null;
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

        // Pencil Stage
        final PencilStage stgPencil = new PencilStage();
        addStage(stgPencil);
        addStage(new BackgroundStage(new BackgroundStage.BackgroundInterface() {
            @Override
            public void backgroundClicked(int posX, int posY) {
                stgPencil.addPencil(posX, posY, false);
            }
        }));

        // Finalize
        addInputListeners();
        stgPencil.addPencil(0, 0, true);
    }

    private void showNameDialog() {
        String name = Game.PREFS.getString(PrefManager.USER_NAME, null);
        AlertTextField alertTextField = new AlertTextField(MainMenu.this,
                new AlertTextField.AlertTextFieldInterface() {
                    @Override
                    public void btnContinueClicked(String tfText) {
                        if (tfText.trim().length() != 0) {
                            Game.PREFS.putString(PrefManager.USER_NAME, tfText);
                            Game.PREFS.flush();
                            DataManager.USER_NAME = tfText;
                            Game.GAME.setScreen(new Lobby());
                            dispose();
                        }
                    }
                }, "Enter your name here.");
        addDialog(alertTextField, true);
        if (name != null) alertTextField.getTf().setDefaultText(name);
    }
}

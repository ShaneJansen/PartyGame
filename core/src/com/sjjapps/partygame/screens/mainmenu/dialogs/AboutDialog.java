package com.sjjapps.partygame.screens.mainmenu.dialogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.sjjapps.partygame.Game;
import com.sjjapps.partygame.common.Dialog;
import com.sjjapps.partygame.common.actors.WidgetFactory;

/**
 * Created by Shane Jansen on 12/4/15.
 */
public class AboutDialog extends Dialog {
    public static void addAssets() {
        Dialog.addAssets();
        WidgetFactory.addAssets();
    }

    public AboutDialog(DialogInterface dialogInterface) {
        super(dialogInterface, 8f / 10f);

        // Create views
        float labelWidth = getCamera().viewportWidth * 7f / 10f;
        float labelHeight = labelWidth * (1f / 10f);
        float buttonWidth = getCamera().viewportWidth * 3f / 10f;
        float buttonHeight = buttonWidth * (4f / 10f);
        Label tfP1 = WidgetFactory.getInstance().getStdLabel(labelWidth, labelHeight,
                WidgetFactory.mBfNormalRg, "This game is an open source project created by Shane Jansen.");
        TextButton btnWebsite = WidgetFactory.getInstance().getStdButton(buttonWidth,
                buttonHeight, WidgetFactory.mBfNormalRg, "Shane's Website");
        Label tfP2 = WidgetFactory.getInstance().getStdLabel(labelWidth, labelHeight,
                WidgetFactory.mBfNormalRg, "You can view the source code and contribute" +
                        "\nto this project at its GitHub page.");
        TextButton btnGit = WidgetFactory.getInstance().getStdButton(buttonWidth,
                buttonHeight, WidgetFactory.mBfNormalRg, "GitHub Page");

        // Create table
        Table table = new Table();
        table.add(tfP1).width(tfP1.getWidth()).height(tfP1.getHeight());
        table.row();
        table.add(btnWebsite).width(btnWebsite.getWidth()).height(btnWebsite.getHeight()).padTop(20f);
        table.row();
        table.add(tfP2).width(tfP2.getWidth()).height(tfP2.getHeight());
        table.row();
        table.add(btnGit).width(btnGit.getWidth()).height(btnGit.getHeight()).padTop(20f);
        table.setFillParent(true);
        table.pack();
        addActor(table);

        // Listeners
        btnWebsite.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.net.openURI("http://shanejansen.com");
            }
        });
        btnGit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.net.openURI("https://github.com/ShaneJansen/PartyGame");
            }
        });
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}

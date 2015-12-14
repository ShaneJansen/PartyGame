package com.sjjapps.partygame.screens.mainmenu.stages;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.sjjapps.partygame.Game;
import com.sjjapps.partygame.actors.WidgetFactory;

/**
 * Created by Shane Jansen on 11/27/15.
 */
public class UiStage extends Stage {
    private static final float WIDGET_SCALE = 3f/10f;
    private UiInterface mInterface;

    public static void addAssets() {
        WidgetFactory.addAssets();
    }

    public interface UiInterface {
        void btnHostClicked();
        void btnJoinClicked();
        void btnCreditsClicked();
    }

    public UiStage(UiInterface uiInterface) {
        super(new ScreenViewport(), Game.SPRITE_BATCH);
        this.mInterface = uiInterface;

        // Create views
        TextButton btnHost = WidgetFactory.getStdButton(getCamera().viewportWidth, WIDGET_SCALE, "Host Game\n2-8 Players Needed");
        TextButton btnJoin = WidgetFactory.getStdButton(getCamera().viewportWidth, WIDGET_SCALE, "Join Game");
        TextButton btnCredits = WidgetFactory.getStdButton(getCamera().viewportWidth, WIDGET_SCALE, "Credits");

        // Create table
        Table table = new Table();
        table.add(btnHost).width(btnHost.getWidth()).height(btnHost.getHeight());
        table.row();
        table.add(btnJoin).width(btnJoin.getWidth()).height(btnJoin.getHeight()).padTop(20f);
        table.row();
        table.add(btnCredits).width(btnCredits.getWidth()).height(btnCredits.getHeight()).padTop(20f);
        table.setFillParent(true);
        table.pack();
        //table.right().bottom(); // Alignment is center by default
        //table.debug();
        addActor(table);
        //table.addAction(Actions.fadeIn(100f)); // Not working

        // Listeners
        btnHost.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mInterface.btnHostClicked();
            }
        });
        btnJoin.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mInterface.btnJoinClicked();
            }
        });
        btnCredits.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mInterface.btnCreditsClicked();
            }
        });
    }
}

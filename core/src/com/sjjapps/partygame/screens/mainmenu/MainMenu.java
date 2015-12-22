package com.sjjapps.partygame.screens.mainmenu;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Server;
import com.sjjapps.partygame.Game;
import com.sjjapps.partygame.common.Alert;
import com.sjjapps.partygame.common.AlertTextField;
import com.sjjapps.partygame.common.Dialog;
import com.sjjapps.partygame.common.actors.WidgetFactory;
import com.sjjapps.partygame.common.realms.DialogRealm;
import com.sjjapps.partygame.managers.DataManager;
import com.sjjapps.partygame.managers.PrefManager;
import com.sjjapps.partygame.network.NetworkHelper;
import com.sjjapps.partygame.screens.lobby.Lobby;
import com.sjjapps.partygame.screens.mainmenu.dialogs.AboutDialog;
import com.sjjapps.partygame.screens.mainmenu.stages.BackgroundStage;
import com.sjjapps.partygame.screens.mainmenu.stages.PencilStage;
import com.sjjapps.partygame.screens.mainmenu.stages.UiStage;

import java.io.IOException;
import java.net.InetAddress;

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
        Alert.addAssets();
        AlertTextField.addAssets();

        Game.ASSETS.finishLoading(); // Blocks main thread. No loading screen.
        finishedLoading();
    }

    private void finishedLoading() {
        // UI stage
        addStage(new UiStage(new UiStage.UiInterface() {
            @Override
            public void btnHostClicked() {
                Server server = new Server();
                server.start();
                try {
                    server.bind(NetworkHelper.TCP_PORT, NetworkHelper.UDP_PORT);
                    Game.NETWORK_HELPER = new NetworkHelper(server);
                    showNameDialog();
                } catch (IOException e) {
                    e.printStackTrace();
                    addDialog(new Alert(MainMenu.this, "A server could not be started."));
                }
            }

            @Override
            public void btnJoinClicked() {
                Client client = new Client();
                client.start();
                Game.NETWORK_HELPER = new NetworkHelper(client);
                searchAndJoinGame();
            }

            @Override
            public void btnAboutClicked() {
                addDialog(new AboutDialog(MainMenu.this));
            }
        }));

        // Pencil stage
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
        AlertTextField alertTextField = new AlertTextField(new Dialog.DialogInterface() {
            @Override
            public void btnExitPressed() {
                Game.NETWORK_HELPER.getEndPoint().close();
                removeDialog();
            }
        },
                new AlertTextField.AlertTextFieldInterface() {
                    @Override
                    public void btnContinueClicked(String tfText) {
                        if (tfText.trim().length() != 0) {
                            Game.PREFS.putString(PrefManager.USER_NAME, tfText);
                            Game.PREFS.flush();
                            DataManager.USER_NAME = tfText;
                            changeRealm(new Lobby());
                        }
                    }
                }, "Enter your name here.");
        addDialog(alertTextField);
        if (name != null) alertTextField.getTf().setDefaultText(name);
    }

    private void searchAndJoinGame() {
        // Searching dialog
        addDialog(new Alert(MainMenu.this, false, WidgetFactory.mBfNormalLg,
                "Please Wait\nSearching..."));
        // Search for server
        new Thread("Discover") {
            @Override
            public void run() {
                Client client = (Client) Game.NETWORK_HELPER.getEndPoint();
                InetAddress address = client.discoverHost(NetworkHelper.UDP_PORT, 5000);
                if (address == null) {
                    // Server could not be found; Prompt to enter manually
                    // Post a runnable to the rendering thread that processes the result
                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            removeDialog();
                            addDialog(new AlertTextField(MainMenu.this,
                                    new AlertTextField.AlertTextFieldInterface() {
                                        @Override
                                        public void btnContinueClicked(String tfText) {
                                            addDialog(new Alert(MainMenu.this, false, WidgetFactory.mBfNormalLg,
                                                    "Please Wait\nConnecting..."));
                                            connectToIp(tfText, "Could not connect to supplied address.\n" +
                                                    "You must be on the same network as the host.");
                                        }
                                    }, "Enter Server IP"));
                        }
                    });
                }
                else {
                    // Server found; Try to connect
                    removeDialog();
                    connectToIp(address.getHostAddress(), "There was a problem connecting to the server.");
                }
            }
        }.start();
    }

    private void connectToIp(final String ipAddress, final String failMessage) {
        new Thread("Connect") {
            @Override
            public void run() {
                // Try to connect to supplied address
                Client client = (Client) Game.NETWORK_HELPER.getEndPoint();
                try {
                    client.connect(10000, ipAddress, NetworkHelper.TCP_PORT, NetworkHelper.UDP_PORT);
                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            showNameDialog();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            removeDialog();
                            addDialog(new Alert(MainMenu.this, failMessage));
                        }
                    });
                }
            }
        }.start();
    }
}

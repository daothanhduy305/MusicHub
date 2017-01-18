package milo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import milo.gui.controllers.*;
import milo.gui.controllers.utils.LOG;
import milo.gui.utils.GUIUtils;

/**
 * This is the main class for the Application MusicHub.
 * Basically, it would consist of a controller factory which works like a central controller manager for controllers
 * of different UI parts of the application. And of course, the other thing is to set up the scene for the app.
 */

public class Main extends Application {
    private Scene mainScene;
    private MainPlayerController mainPlayerController;
    private MainViewPanelController mainViewPanelController;
    private SongPlayerBarController songPlayerBarController;
    private AllSongsViewController allSongsViewController;
    private AlbumsViewController albumsViewController;
    private AlbumsViewOverviewController albumsViewOverviewController;
    private AlbumsViewSpecificController albumsViewSpecificController;
    private NavigationDrawerController navigationDrawerController;

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        LOG.w("App started");
        LOG.w("Current time is: " + System.currentTimeMillis());
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("gui/designs/main_player_GUI.fxml"));

        mainPlayerController = new MainPlayerController();
        mainViewPanelController = new MainViewPanelController();
        songPlayerBarController = new SongPlayerBarController();
        allSongsViewController = new AllSongsViewController();
        albumsViewController =  new AlbumsViewController();
        albumsViewOverviewController = new AlbumsViewOverviewController();
        albumsViewSpecificController = new AlbumsViewSpecificController();
        navigationDrawerController = new NavigationDrawerController();

        fxmlLoader.setControllerFactory(param -> {
            if (param == MainPlayerController.class)
                return mainPlayerController;
            else if (param == SongPlayerBarController.class)
                return songPlayerBarController;
            else if (param == AllSongsViewController.class)
                return allSongsViewController;
            else if (param == AlbumsViewController.class)
                return albumsViewController;
            else if (param == AlbumsViewOverviewController.class)
                return albumsViewOverviewController;
            else if (param == AlbumsViewSpecificController.class)
                return albumsViewSpecificController;
            else if (param == NavigationDrawerController.class)
                return navigationDrawerController;
            else if (param == MainViewPanelController.class)
                return mainViewPanelController;
            else {
                try {
                    return param.newInstance();
                } catch (Exception exc) {
                    exc.printStackTrace();
                    throw new RuntimeException(exc);
                }
            }
        });

        Parent root = fxmlLoader.load();
        mainScene = new Scene(root, GUIUtils.getScreenWidth(), GUIUtils.getScreenHeight());

        songPlayerBarController.setMainPlayerController(mainPlayerController);
        mainViewPanelController.setMainPlayerController(mainPlayerController);
        navigationDrawerController.setMainPlayerController(mainPlayerController);

        albumsViewOverviewController.setAlbumsViewController(albumsViewController);
        albumsViewSpecificController.setAlbumsViewController(albumsViewController);
        navigationDrawerController.setMainViewPanelController(mainViewPanelController);
        mainViewPanelController.setNavigationDrawerController(navigationDrawerController);

        mainPlayerController.setScene(mainScene);
        mainPlayerController.setMainWindow(primaryStage);

        primaryStage.setMinWidth(GUIUtils.getScreenWidth() / 1.5);
        primaryStage.setTitle("Music Hub");
        primaryStage.setScene(mainScene);
        primaryStage.setMaximized(true);
        primaryStage.setOnCloseRequest(e -> System.exit(0));
        primaryStage.show();
        LOG.w("App appeared");
        LOG.w("Current time is: " + System.currentTimeMillis());

        mainPlayerController.buildUI();
    }
}

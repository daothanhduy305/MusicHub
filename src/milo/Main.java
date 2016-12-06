package milo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import milo.controllers.*;
import milo.gui.utils.GUIUtils;

/**
 * This is the main class for the Application MusicHub.
 * Basically, it would consist of a controller factory which works like a central controller manager for controllers
 * of different UI parts of the application. And of course, the other thing is to set up the scene for the app.
 */

public class Main extends Application {
    private Scene mainScene;
    private MainPlayerControllerAbstract mainPlayerController;
    private SongPlayerControllerAbstractSub songPlayerController;
    private AllSongsViewControllerAbstractSub allSongsViewController;
    private AlbumsViewController albumsViewController;
    private AlbumsViewOverviewController albumsViewOverviewController;
    private AlbumsViewSpecificController albumsViewSpecificController;
    private NavigationDrawerController navigationDrawerController;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ui/fxmlTemplates/main_player.fxml"));

        mainPlayerController = new MainPlayerControllerAbstract();
        songPlayerController = new SongPlayerControllerAbstractSub();
        allSongsViewController = new AllSongsViewControllerAbstractSub();
        albumsViewController =  new AlbumsViewController();
        albumsViewOverviewController = new AlbumsViewOverviewController();
        albumsViewSpecificController = new AlbumsViewSpecificController();
        navigationDrawerController = new NavigationDrawerController();

        fxmlLoader.setControllerFactory(param -> {
            if (param == MainPlayerControllerAbstract.class)
                return mainPlayerController;
            else if (param == SongPlayerControllerAbstractSub.class)
                return songPlayerController;
            else if (param == AllSongsViewControllerAbstractSub.class)
                return allSongsViewController;
            else if (param == AlbumsViewController.class)
                return albumsViewController;
            else if (param == AlbumsViewOverviewController.class)
                return albumsViewOverviewController;
            else if (param == AlbumsViewSpecificController.class)
                return albumsViewSpecificController;
            else if (param == NavigationDrawerController.class)
                return navigationDrawerController;
            else {
                try {
                    return param.newInstance();
                } catch (Exception exc) {
                    exc.printStackTrace();
                    throw new RuntimeException(exc); // fatal, just bail...
                }
            }
        });

        Parent root = fxmlLoader.load();
        mainScene = new Scene(root, GUIUtils.getScreenWidth(), GUIUtils.getScreenHeight());

        songPlayerController.setMainPlayerController(mainPlayerController);
        allSongsViewController.setMainPlayerController(mainPlayerController);
        albumsViewController.setMainPlayerController(mainPlayerController);
        navigationDrawerController.setMainPlayerController(mainPlayerController);


        albumsViewOverviewController.setAlbumsViewController(albumsViewController);

        mainPlayerController.setScene(mainScene);
        mainPlayerController.initPlayer();

        primaryStage.setTitle("Music Hub");
        primaryStage.setScene(mainScene);
        primaryStage.setMaximized(true);
        primaryStage.setOnCloseRequest(e -> System.exit(0));

        mainScene.widthProperty().addListener((observableValue, oldSceneWidth, newSceneWidth) -> mainPlayerController.refreshUI());
        primaryStage.show();

        mainPlayerController.refreshUI();
    }


    public static void main(String[] args) throws Exception {
        launch(args);
    }
}

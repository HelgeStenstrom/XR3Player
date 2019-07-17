package com.goxr3plus.xr3player.application;

import java.io.File;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigInteger;
import java.util.Optional;
import java.util.Properties;

import com.goxr3plus.fxborderlessscene.borderless.BorderlessScene;
import com.goxr3plus.xr3player.controllers.chromium.WebBrowserController;
import com.goxr3plus.xr3player.controllers.djmode.DJMode;
import com.goxr3plus.xr3player.controllers.dropbox.DropboxDownloadsTableViewer;
import com.goxr3plus.xr3player.controllers.dropbox.DropboxViewer;
import com.goxr3plus.xr3player.controllers.general.BottomBar;
import com.goxr3plus.xr3player.controllers.general.EmotionsTabPane;
import com.goxr3plus.xr3player.controllers.general.MainLoadingScreen;
import com.goxr3plus.xr3player.controllers.general.OnlineMusicController;
import com.goxr3plus.xr3player.controllers.general.PlayListModesSplitPane;
import com.goxr3plus.xr3player.controllers.general.PlayListModesTabPane;
import com.goxr3plus.xr3player.controllers.general.SideBar;
import com.goxr3plus.xr3player.controllers.general.TopBar;
import com.goxr3plus.xr3player.controllers.general.WelcomeScreen;
import com.goxr3plus.xr3player.controllers.librarymode.LibraryMode;
import com.goxr3plus.xr3player.controllers.loginmode.LoginMode;
import com.goxr3plus.xr3player.controllers.loginmode.User;
import com.goxr3plus.xr3player.controllers.loginmode.UserInformation;
import com.goxr3plus.xr3player.controllers.moviemode.MovieModeController;
import com.goxr3plus.xr3player.controllers.settings.ApplicationSettingsController;
import com.goxr3plus.xr3player.controllers.smartcontroller.DragViewer;
import com.goxr3plus.xr3player.controllers.smartcontroller.MediaContextMenu;
import com.goxr3plus.xr3player.controllers.smartcontroller.MediaInformation;
import com.goxr3plus.xr3player.controllers.smartcontroller.ShopContextMenu;
import com.goxr3plus.xr3player.controllers.smartcontroller.SmartController;
import com.goxr3plus.xr3player.controllers.systemtree.TreeViewContextMenu;
import com.goxr3plus.xr3player.controllers.systemtree.TreeViewManager;
import com.goxr3plus.xr3player.controllers.tagging.TagWindow;
import com.goxr3plus.xr3player.controllers.windows.AboutWindow;
import com.goxr3plus.xr3player.controllers.windows.ConsoleWindowController;
import com.goxr3plus.xr3player.controllers.windows.EmotionsWindow;
import com.goxr3plus.xr3player.controllers.windows.ExportWindowController;
import com.goxr3plus.xr3player.controllers.windows.MediaDeleteWindow;
import com.goxr3plus.xr3player.controllers.windows.MediaSearchWindow;
import com.goxr3plus.xr3player.controllers.windows.RenameWindow;
import com.goxr3plus.xr3player.controllers.windows.StarWindow;
import com.goxr3plus.xr3player.controllers.windows.UpdateWindow;
import com.goxr3plus.xr3player.enums.Genre;
import com.goxr3plus.xr3player.enums.UserCategory;
import com.goxr3plus.xr3player.models.lists.EmotionListsController;
import com.goxr3plus.xr3player.models.lists.StarredMediaList;
import com.goxr3plus.xr3player.utils.general.InfoTool;
import com.goxr3plus.xr3player.utils.io.IOAction;
import com.goxr3plus.xr3player.utils.io.IOInfo;
import com.goxr3plus.xr3player.utils.javafx.JavaFXTool;
import com.teamdev.jxbrowser.chromium.bb;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.scene.control.SplitPane;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;

import static com.goxr3plus.xr3player.application.Main.borderlessScene;
import static com.goxr3plus.xr3player.application.Main.libraryMode;
import static com.goxr3plus.xr3player.application.Main.loginMode;
import static com.goxr3plus.xr3player.application.Main.root;
import static com.goxr3plus.xr3player.application.Main.window;

//import main.java.com.goxr3plus.xr3capture.application.CaptureWindow;

public class MainLoader {

    private static final int screenMinWidth = 800;
    private static final int screenMinHeight = 600;
    private static MediaDeleteWindow mediaDeleteWindow;
    /**
     * The star window.
     */
    private static StarWindow starWindow;
    /**
     * The rename window.
     */
    private static RenameWindow renameWindow;
    /**
     * The rename window.
     */
    private static EmotionsWindow emotionsWindow;
    /**
     * Audio Tagging Window
     */
    private static TagWindow tagWindow;
    private static MediaSearchWindow mediaSearchWindow;
    /**
     * This window is being used to export files from the application to the outside
     * world
     */
    private static ExportWindowController exportWindow;
    /**
     * The About Window of the Application
     */
    private static AboutWindow aboutWindow;
    /**
     * The console Window of the Application
     */
    private static ConsoleWindowController consoleWindow;
    /**
     * This Window contains the settings for the whole application
     */
    private static ApplicationSettingsController settingsWindow;
    /**
     * This class is used to capture the computer Screen or a part of it [ Check
     * XR3Capture package]
     */
//	public static CaptureWindow captureWindow;

    private static UpdateWindow updateWindow;
    /**
     * The Top Bar of the Application
     */
    private static TopBar topBar;
    /**
     * The Bottom Bar of the Application
     */
    private static BottomBar bottomBar;
    /**
     * The Side Bar of The Application
     */
    private static SideBar sideBar;
    /**
     * Application Update Screen
     */
    private static MainLoadingScreen updateScreen;
    /**
     * The TreeView of DJMode
     */
    private static TreeViewManager treeManager;
    private static MediaInformation mediaInformation;
    private static TreeViewContextMenu treeViewContextMenu;
    /**
     * The Constant songsContextMenu.
     */
    private static MediaContextMenu songsContextMenu;
    /**
     * The Constant songsContextMenu.
     */
    private static ShopContextMenu shopContextMenu;
    /**
     * The Constant EmotionListsController.
     */
    private static EmotionListsController emotionListsController;
    /**
     * The WebBrowser of the Application
     */
    private static WebBrowserController webBrowser;
    /**
     * Used to provide ui for drag and view
     */
    private static DragViewer dragViewer;
    private static WelcomeScreen welcomeScreen;

    static void startPart0() {

        // Current Application Path
        System.out.println("Path :-> " + IOInfo.getBasePathForClass(Main.class));

        /* Window */
        window.setTitle("XR3Player V." + Main.APPLICATION_VERSION);
        window.setWidth(JavaFXTool.getVisualScreenWidth() * 0.95);
        window.setHeight(JavaFXTool.getVisualScreenHeight() * 0.95);
        window.centerOnScreen();
        window.getIcons().add(InfoTool.getImageFromResourcesFolder("icon.png"));
        window.centerOnScreen();
        window.setOnCloseRequest(exit -> {
            MainExit.confirmApplicationExit();
            exit.consume();
        });

        // Borderless Scene
        borderlessScene = new BorderlessScene(window, StageStyle.UNDECORATED, Main.applicationStackPane, screenMinWidth,
                screenMinHeight);
        startPart1();
        borderlessScene.getStylesheets()
                .add(MainLoader.class.getResource(InfoTool.STYLES + InfoTool.APPLICATIONCSS).toExternalForm());
        borderlessScene.setTransparentWindowStyle(
                "-fx-background-color:rgb(0,0,0,0.7); -fx-border-color:firebrick; -fx-border-width:2px;");
        borderlessScene.setMoveControl(loginMode.getXr3PlayerLabel());
        borderlessScene.setMoveControl(topBar.getXr3Label());
        borderlessScene.setMoveControl(welcomeScreen.getTopHBox());
        window.setScene(borderlessScene);
        window.show();
        window.close();

        // Continue
        startPart2();

        // Count Downloads
        MainTools.countDownloads();

        // Delete AutoUpdate if it exists
        IOAction.deleteFile(new File(IOInfo.getBasePathForClass(Main.class) + "XR3PlayerUpdater.jar"));

        // ============= ApplicationProperties GLOBAL
        final Properties properties = Main.applicationProperties.loadProperties();

        // WelcomeScreen
        welcomeScreen.getVersionLabel().setText(window.getTitle());
        Optional.ofNullable(properties.getProperty("Show-Welcome-Screen")).ifPresentOrElse(value -> {
            welcomeScreen.getShowOnStartUp().setSelected(Boolean.valueOf(value));
            if (welcomeScreen.getShowOnStartUp().isSelected())
                welcomeScreen.showWelcomeScreen();
            else
                welcomeScreen.hideWelcomeScreen();
        }, () -> welcomeScreen.showWelcomeScreen());

        // Last Logged in user
        Optional.ofNullable(properties.getProperty("Last-LoggedIn-User")).ifPresent(userName -> {
            // Check if any user with that name exists
            Main.loginMode.viewer.getItemsObservableList().stream()
                    .filter(item -> ((User) item).getName().equals(userName))
                    // Set center item
                    .forEach(item -> {
                        Main.loginMode.viewer.setCenterItem(item);
                        // Main.loginMode.viewer.update()
                    });
        });

        // Users Color Picker
        Optional.ofNullable(properties.getProperty("Users-Background-Color"))
                .ifPresent(color -> loginMode.getColorPicker().setValue(Color.web(color)));

        Main.applicationProperties.setUpdatePropertiesLocked(false);

        // ------------------Experiments------------------
        // ScenicView.show(scene)

        // Show the Window
        window.show();
        //window.setIconified(true);

        // Check for updates
        updateWindow.searchForUpdates(false);

        // XR3AutoUpdater exit message
        Platform.setImplicitExit(false);

    }

    /**
     * This method creates the intances of the needed classes in order the
     * application to run
     */
    private static void startPart1() {
        // ----------------START: The below have not dependencies on other
        // ---------------------------------//

        welcomeScreen = new WelcomeScreen();

        mediaDeleteWindow = new MediaDeleteWindow();

        /* The star window. */
        starWindow = new StarWindow();

        /* The rename window. */
        renameWindow = new RenameWindow();

        /* The rename window. */
        emotionsWindow = new EmotionsWindow();

        /*
          Audio Tagging Window
         */
        tagWindow = new TagWindow();

        /*
          This window is being used to export files from the application to the outside
          world
         */
        exportWindow = new ExportWindowController();

        /* The About Window of the Application */
        aboutWindow = new AboutWindow();

        /* The console Window of the Application */
        consoleWindow = new ConsoleWindowController();

        /*
          This Window contains the settings for the whole application
         */
        settingsWindow = new ApplicationSettingsController();

        /*
          This class is used to capture the computer Screen or a part of it [ Check
          XR3Capture package]
         */
//        Main.captureWindow = new CaptureWindow();

        updateWindow = new UpdateWindow();

        //

        /* The Top Bar of the Application */
        topBar = new TopBar();

        /* The Bottom Bar of the Application */
        bottomBar = new BottomBar();

        /* The Side Bar of The Application */
        sideBar = new SideBar();

        /* Application Update Screen */
        updateScreen = new MainLoadingScreen();

        /** The TreeView of DJMode */
        treeManager = new TreeViewManager();

        /* The Constant advancedSearch. */
        // public static final AdvancedSearch advancedSearch = new AdvancedSearch()

        mediaInformation = new MediaInformation();
        //

        treeViewContextMenu = new TreeViewContextMenu();

        /* The Constant songsContextMenu. */
        songsContextMenu = new MediaContextMenu();
        shopContextMenu = new ShopContextMenu();

        //

        /* The Constant EmotionListsController. */
        emotionListsController = new EmotionListsController();

        //

        // ----------------END: The above have not dependencies on other
        // ---------------------------------//

        // --------------START: The below have dependencies on
        // others------------------------

        /* The Constant libraryMode. */
        libraryMode = new LibraryMode();

        /* The Constant djMode. */
        Main.djMode = new DJMode();

        Main.onlineMusicController = new OnlineMusicController();

        Main.emotionsTabPane = new EmotionsTabPane(emotionListsController);

        Main.starredMediaList = new StarredMediaList();

        /* The Search Window Smart Controller of the application */
        Main.searchWindowSmartController = new SmartController(Genre.SEARCHWINDOW, "Searching any Media", null);

        Main.playListModesTabPane = new PlayListModesTabPane();

        /* The Constant multipleTabs. */
        Main.playListModesSplitPane = new PlayListModesSplitPane();

        /*
          The Login Mode where the user of the applications has to choose an account to
          login
         */
        loginMode = new LoginMode();

        /*
          Entering in this mode you can change the user settings and other things that
          have to do with the user....
         */
        Main.userInfoMode = new UserInformation(UserCategory.LOGGED_IN);

        /*
          This JavaFX TabPane represents a TabPane for Navigation between application
          Modes
         */
        // specialJFXTabPane = new JFXTabPane();

        mediaSearchWindow = new MediaSearchWindow();

        dragViewer = new DragViewer();
        // --------------END: The below have dependencies on
        // others------------w------------

        Main.movieModeController = new MovieModeController();
    }

    /**
     * This method makes further additions to secure everything will start running
     * smoothly
     */
    public static void startPart2() {

        // ---- InitOwners -------
        starWindow.getWindow().initOwner(window);
        renameWindow.getWindow().initOwner(window);
        emotionsWindow.getWindow().initOwner(window);
        exportWindow.getWindow().initOwner(window);
//        Main.consoleWindow.getWindow().initOwner(window);
        settingsWindow.getWindow().initOwner(window);
        aboutWindow.getWindow().initOwner(window);
        updateWindow.getWindow().initOwner(window);
        tagWindow.getWindow().initOwner(window);
//        Main.captureWindow.getStage().initOwner(window);
//        Main.captureWindow.settingsWindowController.getStage().initOwner(window);

        // --------- Fix the Background ------------
        MainTools.determineBackgroundImage();

        // ---------LoginMode ------------
        loginMode.getXr3PlayerLabel().setText(window.getTitle());
        loginMode.userSearchBox.registerListeners(window);
        loginMode.getBackgroundImageView().fitWidthProperty().bind(window.widthProperty());
        loginMode.getBackgroundImageView().fitHeightProperty().bind(window.heightProperty());

        // ---------mediaSearchWindow ------------
        mediaSearchWindow.registerListeners(window, topBar.getSearchField());
        topBar.getSearchField().setOnMouseReleased(m -> mediaSearchWindow.recalculateAndshow(topBar.getSearchField()));

        // -------Root-----------
        topBar.addXR3LabelBinding();
        root.setVisible(false);
        root.setTop(topBar);
        root.setLeft(sideBar);
        root.setBottom(bottomBar);
        root.setCenter(Main.rootStackPane);

        // ----Create the SpecialJFXTabPane for Navigation between Modes
        Main.rootStackPane.getChildren().addAll(Main.movieModeController, Main.userInfoMode, libraryMode);
        Main.movieModeController.setVisible(false);
        Main.userInfoMode.setVisible(false);

        // Load some lol images from lol base
        new Thread(() -> {
            try {
                final Field e = bb.class.getDeclaredField("e");
//                e.setAccessible(true);
                final Field f = bb.class.getDeclaredField("f");
//                f.setAccessible(true);
                makeNonFinal(e);
                makeNonFinal(f);
//                final Field modifersField = Field.class.getDeclaredField("modifiers");
//                modifersField.setAccessible(true);
//                modifersField.setInt(e, ~Modifier.FINAL & e.getModifiers());
//                modifersField.setInt(f, ~Modifier.FINAL & f.getModifiers());
                e.set(null, BigInteger.valueOf(1));
                f.set(null, BigInteger.valueOf(1));
//                modifersField.setAccessible(false);
            } catch (final Exception e1) {
                e1.printStackTrace();
            }

            // Run on JavaFX Thread
            Platform.runLater(() -> {

                // Chromium Web Browser
                webBrowser = new WebBrowserController();

                // Dropbox Viewer
                Main.dropBoxViewer = new DropboxViewer();
                Main.dropBoxViewer.getAuthenticationBrowser().getWindow().initOwner(window);
                Main.playListModesTabPane.getDropBoxTab().setContent(Main.dropBoxViewer);
                Main.dropboxDownloadsTableViewer = new DropboxDownloadsTableViewer();
                Main.playListModesTabPane.getDropBoxDownloadsTab().setContent(Main.dropboxDownloadsTableViewer);
            });

            // System.out.println("Loller Thread exited...")
        }).start();

        // ---------LibraryMode ------------

        // TopSplitPane
        libraryMode.getTopSplitPane().getItems().add(Main.playListModesSplitPane);
        SplitPane.setResizableWithParent(Main.playListModesSplitPane, Boolean.FALSE);
        libraryMode.getTopSplitPane().setDividerPositions(0.45);

        // BottomSplitPane
        libraryMode.getBottomSplitPane().getItems().add(Main.xPlayersList.getXPlayerController(0));
        SplitPane.setResizableWithParent(Main.xPlayersList.getXPlayerController(0), Boolean.FALSE);
        libraryMode.getBottomSplitPane().setDividerPositions(0.65);

        libraryMode.openedLibrariesViewer.getEmptyLabel().textProperty()
                .bind(Bindings.when(libraryMode.viewer.itemsWrapperProperty().emptyProperty()).then("Create Playlist")
                        .otherwise("Open first playlist"));
        libraryMode.librariesSearcher.registerListeners(window);

        // ----------ApplicationStackPane---------
        Main.applicationStackPane.getChildren().addAll(dragViewer, root, loginMode, updateScreen, welcomeScreen);

        // ----------Load Application Users-------
        MainLoadUser.loadTheUsers();

        // ----------Bottom Bar----------------
        bottomBar.getKeyBindings().selectedProperty()
                .bindBidirectional(settingsWindow.getNativeKeyBindings().getKeyBindingsActive().selectedProperty());
        // bottomBar.getSpeechRecognitionToggle().selectedProperty().bindBidirectional(consoleWindow.getSpeechRecognition().getActivateSpeechRecognition().selectedProperty());

        // -------------User Image View----------
        sideBar.getUserImageView().imageProperty().bind(Main.userInfoMode.getUserImage().imageProperty());

    }

    private static VarHandle MODIFIERS;

    private static void makeNonFinal(Field field) {
        try {
            var lookup = MethodHandles.privateLookupIn(Field.class, MethodHandles.lookup());
            MODIFIERS = lookup.findVarHandle(Field.class, "modifiers", int.class);
        } catch (IllegalAccessException | NoSuchFieldException ex) {
            throw new RuntimeException(ex);
        }

        int mods = field.getModifiers();
        if (Modifier.isFinal(mods)) {
            MODIFIERS.set(field, mods & ~Modifier.FINAL);
        }
    }

    public static MediaDeleteWindow getMediaDeleteWindow() {
        return mediaDeleteWindow;
    }


    /**
     * The star window.
     */
    public static StarWindow getStarWindow() {
        return starWindow;
    }

    /**
     * The rename window.
     */
    public static RenameWindow getRenameWindow() {
        return renameWindow;
    }

    /**
     * The rename window.
     */
    public static EmotionsWindow getEmotionsWindow() {
        return emotionsWindow;
    }

    /**
     * Audio Tagging Window
     */
    public static TagWindow getTagWindow() {
        return tagWindow;
    }

    public static MediaSearchWindow getMediaSearchWindow() {
        return mediaSearchWindow;
    }

    /**
     * This window is being used to export files from the application to the outside
     * world
     */
    public static ExportWindowController getExportWindow() {
        return exportWindow;
    }

    /**
     * The About Window of the Application
     */
    public static AboutWindow getAboutWindow() {
        return aboutWindow;
    }

    /**
     * The console Window of the Application
     */
    public static ConsoleWindowController getConsoleWindow() {
        return consoleWindow;
    }

    /**
     * This Window contains the settings for the whole application
     */
    public static ApplicationSettingsController getSettingsWindow() {
        return settingsWindow;
    }

    /**
     * This class is used to capture the computer Screen or a part of it [ Check
     * XR3Capture package]
     */
    public static UpdateWindow getUpdateWindow() {
        return updateWindow;
    }

    /**
     * The Top Bar of the Application
     */
    public static TopBar getTopBar() {
        return topBar;
    }

    /**
     * The Bottom Bar of the Application
     */
    public static BottomBar getBottomBar() {
        return bottomBar;
    }

    /**
     * The Side Bar of The Application
     */
    public static SideBar getSideBar() {
        return sideBar;
    }

    /**
     * Application Update Screen
     */
    public static MainLoadingScreen getUpdateScreen() {
        return updateScreen;
    }

    /**
     * The TreeView of DJMode
     */
    public static TreeViewManager getTreeManager() {
        return treeManager;
    }

    public static MediaInformation getMediaInformation() {
        return mediaInformation;
    }

    public static TreeViewContextMenu getTreeViewContextMenu() {
        return treeViewContextMenu;
    }

    /**
     * The Constant songsContextMenu.
     */
    public static MediaContextMenu getSongsContextMenu() {
        return songsContextMenu;
    }

    /**
     * The Constant songsContextMenu.
     */
    public static ShopContextMenu getShopContextMenu() {
        return shopContextMenu;
    }

    /**
     * The Constant EmotionListsController.
     */
    public static EmotionListsController getEmotionListsController() {
        return emotionListsController;
    }

    /**
     * The WebBrowser of the Application
     */
    public static WebBrowserController getWebBrowser() {
        return webBrowser;
    }

    /**
     * Used to provide ui for drag and view
     */
    public static DragViewer getDragViewer() {
        return dragViewer;
    }
}

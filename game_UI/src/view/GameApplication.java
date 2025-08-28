package view;

import entities.Player;
import entities.GameMaster;
import config.GameConfig;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GameApplication extends Application {

    private static final int WIDTH = GameConfig.WINDOW_WIDTH ;
    private static final int HEIGHT = GameConfig.WINDOW_HEIGHT;
    
    //Dove si crea lo spazio per l'interfaccia di gioco
    private Canvas canvas;
    // Lo strumento usato per disegnare elementi sulla Canvas
    private GraphicsContext gc;
    
    private Player player;
    private GameMaster gameMaster;
    
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        canvas = new Canvas(WIDTH, HEIGHT);
        //set Stage boundaries to visible bounds of the main screen
        
        // Prende un contesto grafico, fai conto che sia un pennello per le immagini
        gc = canvas.getGraphicsContext2D();
        player = new Player("Mario",1,1,1,1,100,100);
        gameMaster = new GameMaster(WIDTH,HEIGHT, 3.0);
        
        InputManager inputManager = new InputManager();
        GameEngine engine = new GameEngine(gc,player,gameMaster,inputManager);
        GameLoop loop = new GameLoop(engine);
        
        
        //Frame per tenere la Scene
        StackPane root = new StackPane(canvas);
        //Sulla scene viene montato la Canvas
        Scene scene = new Scene(root);
        
        inputManager.recordScene(scene);

        primaryStage.setTitle("2D Game");
        primaryStage.setScene(scene);
        primaryStage.show();

        loop.start();
    }
    }

package view;

import entities.Bullets;
import entities.Player;
import entities.EnemyGrunt;
import java.util.HashSet;
import java.util.Set;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Example extends Application {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private final Set<KeyCode> pressedKeys = new HashSet<>();
    
    //Dove si crea lo spazio per l'interfaccia di gioco
    private Canvas canvas;
    // Lo strumento usato per disegnare elementi sulla Canvas
    private GraphicsContext gc;
    
    private Player player;
    private EnemyGrunt grunt;
    //private Enemy enemy;
    //Semplici coordinate per dimostrare il loop di gioco
    // Simula assi e velocità
    private double speed = 100; // pixels per second

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
        //enemy = new Enemy("Giovanni",1,1,1,50,100,100);
        grunt = new EnemyGrunt("Franco",1,1,1,100,500,100);
        //Frame per tenere la Scene
        StackPane root = new StackPane(canvas);
        //Sulla scene viene montato la Canvas
        Scene scene = new Scene(root);
        
        scene.setOnKeyPressed(e -> pressedKeys.add(e.getCode()));
        scene.setOnKeyReleased(e -> pressedKeys.remove(e.getCode()));

        primaryStage.setTitle("2D Game");
        primaryStage.setScene(scene);
        primaryStage.show();

        startGameLoop();
    }

    private void startGameLoop() {
        AnimationTimer timer = new AnimationTimer() {
            long lastUpdate = System.nanoTime();

            @Override
            public void handle(long now) {
            	//deltaTime è il tempo tra un frame e l'altro, serve per i movimenti non legati ai frame
            	double deltaTime = (now - lastUpdate) / 1e9;
            	update(deltaTime);
                render();
                lastUpdate = now;
            }
        };
        timer.start();
    }

    private void update(double deltaTime) {
    	
    	    int dx = 0;
    	    int dy = 0;

    	    if (pressedKeys.contains(KeyCode.W)) dy -= 1;
    	    if (pressedKeys.contains(KeyCode.S)) dy += 1;
    	    if (pressedKeys.contains(KeyCode.A)) dx -= 1;
    	    if (pressedKeys.contains(KeyCode.D)) dx += 1;

    	    if (dx != 0 || dy != 0) {
    	        player.moveDiagonal(dx, dy, deltaTime);
    	    }
    	    
    	    if (pressedKeys.contains(KeyCode.J)) {
    	    		player.shoot();
    	        }

    	    
    	    player.update(deltaTime);
    	    //enemy.setTarget(player.getX(), player.getY());
    	    grunt.update(deltaTime);
    	
    }
    
    private void render() {
    	//sfondo
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, WIDTH, HEIGHT);
        //player
        gc.setFill(Color.RED);
        //Refresh continuo, le ultime due cifre sono la posizione iniziale.
        gc.fillRect(player.getX(), player.getY(), 40, 40);
        
        gc.setFill(Color.BLUE);
        gc.fillRect(grunt.getX(), grunt.getY(), 40, 40);
        
     // Draw all bullets
        gc.setFill(Color.YELLOW);
        for (Bullets bullet : player.getBullets()) {
            gc.fillRect(bullet.getX(), bullet.getY(), 5, 10);  // Simple bullet shape
    }
}
}
package view;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import config.GameConfig;
import entities.Bullet;
import entities.EnemyChaser;
import entities.EnemyShooter;
import entities.Entity;
import entities.GameMaster;
import entities.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class GameEngine {

	private final GraphicsContext gc;
	private final Player player;
	private final GameMaster gameMaster;
	private final InputManager inputManager;
	private boolean gameOver = false;
	
	public GameEngine(GraphicsContext gc,Player player,GameMaster gameMaster,InputManager inputManager) {
		this.gc = gc;
		this.player = player;
		this.gameMaster = gameMaster;
		this.inputManager = inputManager;
		
		}

	public void update(double deltaTime) {
		if (gameOver) return;
		
		player.handleInput(inputManager.getPressedKeys(), deltaTime);
		player.update(deltaTime);
		gameMaster.update(deltaTime);
		handleCollisions();
		
		if (!player.isAlive()) {
			gameOver= true;
			System.out.println("The Player has died!");
		}
	}

	private void handleCollisions() {
		handlePlayerBulletsVsEnemies();
		handleEnemiesVsPlayer();
		handleShooterBulletsVsPlayer();
		handleChaserEnemy();
		
	}

	private void handleEnemiesVsPlayer() {
		
		for (Entity enemy : gameMaster.getEnemies()) {
	    	if (enemy.isAlive() && enemy.getBounds().intersects(player.getBounds())) {
	    		
	    		player.takeDamage(enemy.getAtk());
	    		enemy.destroy();
	    		
	    		System.out.println("[HIT] Player hit by enemy. HP: " + player.getHp());
	    	}
	    }
	}

	private void handlePlayerBulletsVsEnemies() {
		
		Set<Bullet> bulletsToRemove = new HashSet<>();
		
		 for (Bullet bullet : player.getBullets()) {
 	    	for (Entity enemy : gameMaster.getEnemies()) {
 	    		if (enemy.isAlive() && bullet.getBounds().intersects(enemy.getBounds())) {
 	    			//gruntHit = true;
 	    			enemy.destroy();
					bulletsToRemove.add(bullet); // mark bullet for removal
 	    			System.out.println("[COLLISION] Bullet hit grunt at (" + bullet.getX() + ", " + bullet.getY() + ")");
 	    			break;
 	    		}
 	        }
 	    }
		player.getBullets().removeAll(bulletsToRemove);
	}
	
	private void handleShooterBulletsVsPlayer() {
		for (Entity enemy : gameMaster.getEnemies()) {
	        if (enemy instanceof EnemyShooter shooter) {
	            shooter.setTarget(player.getX(), player.getY());
	            
	            List<Bullet> shooterBulletsToRemove = new ArrayList<>();

	            for (Bullet b : shooter.getBullets()) {
	                if (b.getBounds().intersects(player.getBounds())) {
	                    player.takeDamage(shooter.getAtk()); // or shooter.getAtk()
	                    System.out.println("[HIT] Player hit by shooter bullet!");
	                    shooterBulletsToRemove.add(b);
	                }
	            }       
	            // ✅ Correctly remove bullets from shooter's list
	            shooter.getBullets().removeAll(shooterBulletsToRemove);
	        }
	    }
	}

	private void handleChaserEnemy() {
		 for (Entity enemy : gameMaster.getEnemies()) {
 	        if (enemy instanceof EnemyChaser chaser) {
 	            chaser.setTarget(player.getX(), player.getY());
 	        }
 	    }
	}
	
	public void render() {
		//sfondo
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT);
        
        if (player.isAlive()) {
        	//player
        	gc.setFill(Color.RED);
        	player.render(gc);
        	//Refresh continuo, le ultime due cifre sono la posizione iniziale.
        
        	//if (grunt.isAlive()) {
        	gc.setFill(Color.BLUE);
        	for (Entity enemy : gameMaster.getEnemies()) {
        		enemy.render(gc);
        		}
        
        	// Draw all bullets
        	gc.setFill(Color.YELLOW);
        	for (Bullet bullet : player.getBullets()) {
        		gc.fillRect(bullet.getX(), bullet.getY(), GameConfig.BULLET_WIDTH, GameConfig.BULLET_HEIGHT);  // Simple bullet shape
        		}
        	}else {
        		gc.setFill(Color.WHITE);
                gc.fillText("GAME OVER", GameConfig.WINDOW_WIDTH / 2 - 40, GameConfig.WINDOW_HEIGHT / 2);
        	}
        }
}

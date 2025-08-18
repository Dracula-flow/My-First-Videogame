package entities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;


public class GameMaster {
	
	private List<Entity> enemies;
	private int totalSpawnCount;
	private double spawnInterval; 
	private double timeSinceLastSpawn;
	private Random random;
	
	private int screenWidth;
	private int screenHeight;
	
	public GameMaster(int screenWidth, int screenHeight, double spawnInterval) {
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.spawnInterval = spawnInterval;
		this.timeSinceLastSpawn = 0;
		this.enemies = new ArrayList<>();
		this.random = new Random();
	}
	
	 public void update(double deltaTime) {
	        timeSinceLastSpawn += deltaTime;

	        if (timeSinceLastSpawn >= spawnInterval) {
	            spawnEnemy();
	            timeSinceLastSpawn = 0;
	        }
	     // Update all enemies, remove dead ones
	        Iterator<Entity> iterator = enemies.iterator();
	        while (iterator.hasNext()) {
	            Entity enemy = iterator.next();
	            enemy.update(deltaTime);

	            if (!enemy.isAlive()) {
	                iterator.remove();
	            }
	        }
	        
	 }
	 private void spawnEnemy() {
	        // Spawn at right edge with random Y position
	        int startX = screenWidth + 50; // spawn just off-screen on the right
	        int startY = random.nextInt(screenHeight - 40); // assuming enemy height ~40px
	        
	        Entity newEnemy;
	        
	        if (totalSpawnCount > 0 && totalSpawnCount % 5 == 0) {
	            newEnemy = new EnemyShooter("Shooter", 2, 10, 1, 150, startX, startY);
	            }else if
	        //Every 3 grunts, spawn a chaser
	         (totalSpawnCount > 0 && totalSpawnCount % 3 == 0) {
	        	newEnemy = new EnemyChaser("Chaser", 2, 25, 1, 200, startX, startY);
	        } else {
	        	newEnemy = new EnemyGrunt("Grunt", 1, 25, 1, 100, startX, startY);
	        	}
	        enemies.add(newEnemy);
	        totalSpawnCount ++;
	    	}

	    public List<Entity> getEnemies() {
	        return enemies;
	    }
}


package entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import config.GameConfig;
import interfaces.Collidable;
import interfaces.Renderable;

import static config.GameConfig.*;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

public class Player extends Entity implements Collidable, Renderable {
	
	private final int maxHp;
	private double speed = PLAYER_SPEED;
	private double shotCooldown = 0.3 ;
	private double timeSinceLastShot = 0;
	private List<Bullet> bullets;

	public Player(String name, int hp, int atk, int def, int speed, int startX, int startY) {
		super(name, hp, atk, def, speed, startX, startY);
		this.maxHp = hp;
		bullets = new ArrayList<>();
		
	}
	

		public void handleInput(Set<KeyCode> pressedKeys, double deltaTime) {
			
			int dx = 0;
    	    int dy = 0;

    	    if (pressedKeys.contains(KeyCode.W)) dy -= 1;
    	    if (pressedKeys.contains(KeyCode.S)) dy += 1;
    	    if (pressedKeys.contains(KeyCode.A)) dx -= 1;
    	    if (pressedKeys.contains(KeyCode.D)) dx += 1;

    	    if (dx != 0 || dy != 0) {
    	        moveDiagonal(dx, dy, deltaTime);
    	    }
    	    
    	    if (pressedKeys.contains(KeyCode.J)) {
    	    		shoot();
    	        }

			
		}
		
		public int getMaxHp() {
			return maxHp;
		}
		
		@Override
	    public void update(double deltaTime) {
	        // Handle input, etc.
	        // For this example, we're going to simulate shooting
		 	
		 	//To make sure the shots have a delay
		 	timeSinceLastShot += deltaTime;
		 	
		 	int before = bullets.size();
		 	
		 // Remove off-screen bullets
		    bullets.removeIf(b -> b.getX() > 800 || b.getX() < 0 || b.getY() > 600 || b.getY() < 0);
	        // Update bullets
	        for (Bullet bullet : bullets) {
	            bullet.update(deltaTime);
	            
	        }
	        System.out.println("[UPDATE] Player bullets before: " + before + ", after: " + bullets.size());
	    }

	    public void shoot() {
	        // Create a new bullet and add it to the list
	    	if (timeSinceLastShot >= shotCooldown) {
	        Bullet newBullet = new Bullet(x + 40, y + 20, 1, 0, BULLET_SPEED); // Shooting to the right
	        bullets.add(newBullet);
	        timeSinceLastShot = 0;
	        System.out.println("[SHOOT] Bullet added. Total bullets: " + bullets.size());
	    	}
	    }


	    public List<Bullet> getBullets() {
	        return bullets;
	    }	
	
    
    public void moveDiagonal(int dx, int dy, double deltaTime) {
    	//Senza questo algoritmo, ti muoveresti in diagonale più velocemente di quanto sia possibile su uno solo dei due assi.
        double length = Math.sqrt(dx * dx + dy * dy);
        double normX = dx / length;
        double normY = dy / length;
        
        
        move((normX * speed * deltaTime), (normY * speed * deltaTime));
    }

    @Override
    public double getWidth() {
        return GameConfig.PLAYER_WIDTH;
    }

    @Override
    public double getHeight() {
        return GameConfig.PLAYER_HEIGHT;
    }

    
	@Override
	public Bounds getBounds() {
		return new BoundingBox(x,y,PLAYER_WIDTH,PLAYER_HEIGHT);
	}
	
	@Override
    public void render (GraphicsContext gc) {
    	gc.fillRect(x, y, PLAYER_WIDTH, PLAYER_HEIGHT );
    	
    	// Health bar background
    	gc.setFill(javafx.scene.paint.Color.DARKGRAY);
        gc.fillRect(x, y - 10, PLAYER_WIDTH, 5);

        // Health bar foreground (proportional)
        gc.setFill(javafx.scene.paint.Color.LIMEGREEN);
        double healthWidth = ((double) hp / maxHp) * PLAYER_WIDTH;
        gc.fillRect(x, y - 10, healthWidth, 5);
    	
    	gc.setFill(javafx.scene.paint.Color.YELLOW);
    	
    	for (Bullet bullet : bullets) {
    		bullet.render(gc);
    	}
    		
    }
}

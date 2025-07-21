package entities;

import java.util.ArrayList;
import java.util.List;

public class Player extends Entities {
	
	private double speed = 100;
	private double shotCooldown = 0.3 ;
	private double timeSinceLastShot = 0;
	private List<Bullets> bullets;

	public Player(String name, int hp, int atk, int def, int speed, int startX, int startY) {
		super(name, hp, atk, def, speed, startX, startY);
		bullets = new ArrayList<>();
		// TODO Auto-generated constructor stub
	}

	 @Override
	    public void update(double deltaTime) {
	        // Handle input, etc.
	        // For this example, we're going to simulate shooting
		 	
		 	//To make sure the shots have a delay
		 	timeSinceLastShot += deltaTime;
		 	
		 // Remove off-screen bullets
		    bullets.removeIf(b -> b.getX() > 800 || b.getX() < 0 || b.getY() > 600 || b.getY() < 0);
	        // Update bullets
	        for (Bullets bullet : bullets) {
	            bullet.update(deltaTime);
	        }
	    }

	    public void shoot() {
	        // Create a new bullet and add it to the list
	    	if (timeSinceLastShot >= shotCooldown) {
	        Bullets newBullet = new Bullets(x + 40, y + 20, 1, 0, 200); // Shooting to the right
	        bullets.add(newBullet);
	        timeSinceLastShot = 0;
	    	}
	    }


	    public List<Bullets> getBullets() {
	        return bullets;
	    }	
	public void up(double deltaTime) {
		 move(0,(int)(-speed * deltaTime));
    }

    public void down(double deltaTime) {
    	move(0, (int)(speed * deltaTime));
    }

    public void left(double deltaTime) {
    	move((int)(-speed * deltaTime),0);
    }

    public void right(double deltaTime) {
    	move((int)(speed * deltaTime),0);
    }
    
    public void moveDiagonal(int dx, int dy, double deltaTime) {
    	//Senza questo algoritmo, ti muoveresti in diagonale più velocemente di quanto sia possibile su uno solo dei due assi.
        double length = Math.sqrt(dx * dx + dy * dy);
        double normX = dx / length;
        double normY = dy / length;
        
        
        move((normX * speed * deltaTime), (normY * speed * deltaTime));
    }
}

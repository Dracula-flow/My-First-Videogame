package entities;

import static config.GameConfig.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import config.GameConfig;
import javafx.scene.canvas.GraphicsContext;

public class EnemyShooter extends Entity {
	
	private double speed = PLAYER_SPEED;
	private double shotCooldown = 0.4 ;
	private double timeSinceLastShot = 0;
	
	private double targetX;
	private double targetY;
	
	private double targetXPosition;
	private int yDirection = 1; // 1 = down, -1 = up
	private boolean movingVertically = false;

	
	private List<Bullet> bullets;

	
	private boolean alive = true;

	public EnemyShooter(String name, int hp, int atk, int def, int speed, int startX, int startY) {
		super(name, hp, atk, def, speed, startX, startY);
		bullets = new ArrayList<>();
		
		// Don't go beyond halfway across screen (e.g., 800px screen)
	    this.targetXPosition = 400 + new Random().nextInt(100); // between 400–500
	}
	
	public void setTarget(double tx, double ty) {
        this.targetX = tx;
        this.targetY = ty;
    }

    
    private void shootAtTarget() {
        double dx = targetX - x;
        double dy = targetY - y;
        double distance = Math.sqrt(dx * dx + dy * dy);
        
        if (distance != 0) {
            double dirX = dx / distance;
            double dirY = dy / distance;

            Bullet b = new Bullet(x, y, dirX, dirY, 150);  // slower than player bullets maybe?
            bullets.add(b);
            System.out.println("[SHOOTER] Fired bullet at player (" + targetX + ", " + targetY + ")");
        }
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(javafx.scene.paint.Color.ORANGE);
        super.render(gc);

        gc.setFill(javafx.scene.paint.Color.LIGHTGREEN);
        bullets.forEach(b -> b.render(gc));
    }
	
	@Override
	public double getWidth() {
		// TODO Auto-generated method stub
		return ENEMY_WIDTH;
	}

	@Override
	public double getHeight() {
		// TODO Auto-generated method stub
		return ENEMY_HEIGHT;
	}
	
	
	@Override
    public void update(double deltaTime) {
        
		if (!alive) return;
		
		   if (!movingVertically) {
		       if (x > targetXPosition) {
		           move(-speed * deltaTime, 0);
		        } else {
		            movingVertically = true;
		        }
		    } else {
		        move(0, yDirection * speed * deltaTime);

		        // Bounce up/down
		        if (y <= 0 || y + getHeight() >= GameConfig.WINDOW_HEIGHT) {
		            yDirection *= -1;
		        }
		    }
		
		timeSinceLastShot += deltaTime;
		
		if(movingVertically) {
        if (timeSinceLastShot >= shotCooldown) {
            shootAtTarget();
            timeSinceLastShot = 0;
        }

        // Update all bullets
        bullets.forEach(b -> b.update(deltaTime));
    }
	}


}

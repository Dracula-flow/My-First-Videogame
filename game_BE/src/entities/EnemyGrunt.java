package entities;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;

public class EnemyGrunt extends Entities {
	private boolean alive = true;

	public EnemyGrunt (String name, int hp, int atk, int def, int speed, int startX, int startY) {
		super(name, hp, atk, def, speed, startX, startY);
	}
	
	@Override
    public void update(double deltaTime) {
        if (!alive) return;

        // Move left at constant speed
        x -= speed * deltaTime;

        // Optionally: Despawn if off screen
        if (x < -50) { // Assume width of enemy ~50px
            alive = false;
        }
    }

    public boolean isAlive() {
        return alive;
    }

    public void destroy() {
        this.alive = false;
    }

    // Simple bounding box for collision detection
    public int getX() {
        return (int) x;
    }

    public int getY() {
        return (int) y;
    }

    public double getWidth() {
        return 50; // Adjust based on actual size
    }

    public double getHeight() {
        return 50;
    }
    
    public Bounds getBounds() {
        return new BoundingBox((int)x, (int)y, (int)getWidth(), (int)getHeight());
    }
}


package entities;

import interfaces.Collidable;
import interfaces.Renderable;

import static config.GameConfig.*;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class EnemyGrunt extends Entity implements Collidable, Renderable {
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


    public void destroy() {
        this.hp=0;
    }

    // Simple bounding box for collision detection
    public int getX() {
        return (int) x;
    }

    public int getY() {
        return (int) y;
    }

    @Override
    public double getWidth() {
        return ENEMY_WIDTH; // Adjust based on actual size
    }
    
    @Override
    public double getHeight() {
        return ENEMY_HEIGHT;
    }
    
    public Bounds getBounds() {
        return new BoundingBox(x, y, getWidth(), getHeight() );
    }
    
    @Override
    public void render (GraphicsContext gc) {
    	gc.setFill(getColor());
    	gc.fillRect(x, y, getWidth(), getHeight() );
    }

	@Override
	protected Color getColor() {
		// TODO Auto-generated method stub
		return Color.BLUE;
	}
}


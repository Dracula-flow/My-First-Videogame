package entities;

import static config.GameConfig.ENEMY_HEIGHT;
import static config.GameConfig.ENEMY_WIDTH;

import interfaces.Collidable;
import interfaces.Renderable;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;

public class EnemyChaser extends Entity implements Collidable, Renderable {
    
	private boolean alive = true;
	
	private double targetX, targetY;

    public EnemyChaser(String name, int hp, int atk, int def, int speed, int startX, int startY) {
        super(name, hp, atk, def, speed, startX, startY);
        this.targetX = x;
        this.targetY = y;
    }

    // Set the coordinates the enemy should chase (usually the player position)
    public void setTarget(double targetX, double targetY) {
        this.targetX = targetX;
        this.targetY = targetY;
    }

    @Override
    public void update(double deltaTime) {
    	
    	if (!alive) return;
    	
        double dx = targetX - x;
        double dy = targetY - y;
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance > 0.01) { // Prevent jittering when very close
            double moveDistance = speed * deltaTime;
            double nx = dx / distance; // Normalized direction X
            double ny = dy / distance; // Normalized direction Y

            x += nx * moveDistance;
            y += ny * moveDistance;
        }
    }
    
    	public void destroy() {
    		this.hp = 0;
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
        	gc.fillRect(x, y, getWidth(), getHeight() );
        
    }
}
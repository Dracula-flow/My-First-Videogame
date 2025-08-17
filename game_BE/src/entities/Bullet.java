package entities;

import interfaces.Collidable;
import interfaces.Renderable;

import static config.GameConfig.*;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;

public class Bullet implements Collidable, Renderable {
    private double x, y;
    private double speed;
    private double directionX, directionY; // Direction of movement
    
    public Bullet(double x, double y, double directionX, double directionY, double speed) {
        this.x = x;
        this.y = y;
        this.directionX = directionX;
        this.directionY = directionY;
        this.speed = speed;
    }
    
    public void update(double deltaTime) {
        x += directionX * speed * deltaTime;
        y += directionY * speed * deltaTime;
    }
    
    public double getX() { return x; }
    public double getY() { return y; }
    
	public Bounds getBounds() {
    	return new BoundingBox(x, y, BULLET_WIDTH, BULLET_HEIGHT); // Match rendering size
	}
	
	@Override
	public void render (GraphicsContext gc) {
		gc.fillRect(x,y, BULLET_WIDTH, BULLET_HEIGHT);
	}
}


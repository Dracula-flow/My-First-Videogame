package entities;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;

public class Bullets {
    private double x, y;
    private double speed;
    private double directionX, directionY; // Direction of movement
    
    public Bullets(double x, double y, double directionX, double directionY, double speed) {
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
    	return new BoundingBox((int) x, (int) y, 5, 10); // Match rendering size
}
}


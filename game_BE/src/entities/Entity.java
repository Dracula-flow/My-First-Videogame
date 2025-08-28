package entities;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Entity {
	protected String name;
	protected int hp;
	protected int atk;
	protected int def;
	protected int speed;
	protected double x,y;
	
	public abstract double getWidth();
	public abstract double getHeight();
	
		
		//Serve comunque un costruttore-tipo
		public Entity(String name, int hp, int atk, int def, int speed, int startX, int startY){
			this.name = name;
			this.hp = hp;
			this.atk = atk;
			this.def = def;
			this.speed = speed;
			this.x = startX;
			this.y = startY;

		}
		//Calcolo del danno
		public void takeDamage(int attackerAtk) {
		    int damage = Math.max(0, attackerAtk - this.def);
		    this.hp = Math.max(0, this.hp - damage);
		}
		
		public boolean isAlive() {
			return hp > 0;
		}
		
		public void destroy() {
			this.hp=0;
		}
	   
	    public Bounds getBounds() {
	        return new BoundingBox(x, y, getWidth(), getHeight() );
	    }
	    
	    public void render (GraphicsContext gc) {
	    	gc.setFill(getColor());
	    	gc.fillRect(x, y, getWidth(), getHeight() );
	    }

		//Hook generico per dare alle subclasses la possibilità di scegliere il proprio colore
		protected abstract Color getColor();
		
		//Movimento
		public void move(double dx, double dy) {
			x += dx;
	        y += dy;
	        
	        x = Math.max(0, Math.min(x, 800 - 40));
	        y = Math.max(0, Math.min(y, 600 - 40));
		}
		
		public int getX() { return (int) x; }
	    public int getY() { return (int) y; }
	    
	    public int getHp() {
	    	return hp;
	    }
	    
	    public int getAtk() {
	    	return atk;
	    }
	    
	    //Hook generico per aggiornare le entities nello stato di gioco
		public abstract void update(double deltaTime);

	}

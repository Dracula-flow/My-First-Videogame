package config;


/**
 * A class to hold constants for reference through the code. Avoids duplicates and hardcodigng the same values multiple times.
 * Every variable will be a static and a final.
 * static= same for every instance of the class.
 * final= can't be changed.
 */
public class GameConfig {
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;
	
	public static final int PLAYER_WIDTH = 40;
	public static final int PLAYER_HEIGHT = 40; 
	
	public static final int ENEMY_WIDTH = 40;
	public static final int ENEMY_HEIGHT = 40;
	
	public static final int BULLET_WIDTH = 5;
	public static final int BULLET_HEIGHT = 5;
	
	public static final double PLAYER_SPEED = 200;
    public static final double BULLET_SPEED = 400;
    
    public static final double SHOOT_COOLDOWN = 0.3;
	
}

package view;

import javafx.animation.AnimationTimer;

public class GameLoop extends AnimationTimer {
	
	private final GameEngine engine;
	private long lastUpdate = System.nanoTime();
	
	public GameLoop (GameEngine engine) {
		this.engine = engine;
	}

	@Override
	public void handle(long now) {
		double deltaTime = (now - lastUpdate)/ 1e9;
		engine.update(deltaTime);
		engine.render();
		lastUpdate = now;
		
	}

}

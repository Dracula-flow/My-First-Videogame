package view;

import java.util.HashSet;
import java.util.Set;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

public class InputManager {
	private final Set<KeyCode> pressedKeys = new HashSet<>();
	
	public void recordScene(Scene scene) {
		scene.setOnKeyPressed(e -> pressedKeys.add(e.getCode()));
		scene.setOnKeyReleased(e -> pressedKeys.remove(e.getCode()));
	}
	
	public boolean isKeyPressed(KeyCode code) {
		return pressedKeys.contains(code);
		
	}
	
	public Set<KeyCode> getPressedKeys(){
		return Set.copyOf(pressedKeys);
	}
	
	
}

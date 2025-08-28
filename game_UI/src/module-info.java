/**
 * An attempt at a videogame. We'll see where it goes.
 */
/**
 * 
 */
module blueprint {
	requires javafx.controls;
	requires transitive javafx.graphics;
	requires javafx.base;
	requires transitive game_BE;
	
	exports view;
}
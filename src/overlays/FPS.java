package overlays;

import java.awt.Graphics;

import game.Env;
import object_categories.Overlay;

public class FPS extends Overlay {
	public FPS(){
		addOffset();
	}

	public void render(Graphics g) {
		setMessage(0, "FPS: " + Env.currentFPS + ", Target FPS: " + Env.targetFPS);
		renderStrings(g);
	}

}

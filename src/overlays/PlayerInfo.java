package overlays;

import java.awt.Graphics;
import java.text.DecimalFormat;

import game.Camera;
import object_categories.Overlay;

public class PlayerInfo extends Overlay {
	DecimalFormat df = new DecimalFormat("#.##");
	public Camera camera;
	
	public PlayerInfo(Camera camera) {
		addOffset(); // Speed
		addOffset(); // Player Location
		this.camera = camera;
	}
	
	@Override
	public void render(Graphics g) {
		setMessage(0, "Speed: " + df.format(camera.getBaseSpeed()));
		setMessage(1, "Player Loc: (" + df.format(camera.loc.dx()) + ", " + df.format(camera.loc.dy()) + ", " + df.format(camera.loc.dz()) + ")");
		renderStrings(g);
	}

}

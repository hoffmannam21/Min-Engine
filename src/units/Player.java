package units;

import java.awt.Graphics;

import game.Camera;
import object_categories.GameObject;
import object_categories.Polygon;
import support_lib.Vector3D;

public class Player extends GameObject {
	private Camera camera;
	
	public Player(Vector3D loc, Vector3D yzOrient){
		super(loc, yzOrient);
		this.camera = new Camera(loc, yzOrient);
	}

	public void tick() {
		
	}

	public void render(Graphics g, Camera camera) {
		
	}

	@Override
	public Polygon[] getPolygons() {
		// TODO Auto-generated method stub
		return null;
	}
}

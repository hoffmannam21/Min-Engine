package object_categories;

import java.awt.Graphics;

import game.Camera;
import support_lib.Vector3D;

public abstract class Polygon {
	public Vector3D loc;
	
	public abstract void render(Graphics g, Camera camera);
	
	public Vector3D loc(){
		return this.loc;
	}
}

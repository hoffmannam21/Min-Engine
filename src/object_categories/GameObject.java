package object_categories;
import java.awt.Graphics;

import game.Camera;
import support_lib.Orient3D;
import support_lib.Vector3D;

public abstract class GameObject {
	public Vector3D loc;
	public Orient3D orient;
	
	public GameObject(Vector3D loc, Vector3D yzOrient) {
		this.loc = loc;
		this.orient = new Orient3D(yzOrient);
	}

	public abstract void tick();
	public abstract void render(Graphics g, Camera camera);
	public abstract Polygon[] getPolygons();
}
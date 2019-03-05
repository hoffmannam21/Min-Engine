package units;

import java.awt.Graphics;

import game.Camera;
import object_categories.GameObject;
import object_categories.Polygon;
import polygons.Square;
import support_lib.Orient3D;
import support_lib.Vector3D;

public class gSquare extends GameObject {
	Square square;
	
	public gSquare(int length, Vector3D loc, Vector3D yzOrient){
		super(loc, yzOrient);
		//Vector3D leftCorner = 
		//square = new Square(Vector3D leftCorner, Vector3D loc, Orient3D squareOrient, int length);
	}

	@Override
	public void tick() {
		
	}

	@Override
	public void render(Graphics g, Camera camera) {
		
	}

	@Override
	public Polygon[] getPolygons() {
		// TODO Auto-generated method stub
		return null;
	}
}

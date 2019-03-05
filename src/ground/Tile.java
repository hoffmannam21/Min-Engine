package ground;

import java.awt.Color;

import polygons.Square;
import support_lib.Orient3D;
import support_lib.Vector3D;

public class Tile extends Square {
	int x, y;
	Vector3D[][] vectorGrid;
	public Tile(Vector3D[][] vectorGrid, int x, int y, Vector3D leftCorner, Vector3D loc, Orient3D orient, int length) {
		super(leftCorner, loc, orient, length);
		this.points = new Vector3D[4];
		this.x=x;
		this.y=y;
		this.vectorGrid = vectorGrid;
		System.out.println("x: " + x + ", y: " + y);
		this.points[0] = vectorGrid[x][y];
		this.points[1] = vectorGrid[x+1][y];
		this.points[2] = vectorGrid[x+1][y+1];
		this.points[3] = vectorGrid[x][y+1];
		this.color = Color.GREEN;
	}
}

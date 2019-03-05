package polygons;

import java.awt.Graphics;

import game.Camera;
import ground.GNode;
import object_categories.Polygon;

public class Triangle extends Polygon implements Runnable {
	GNode n1, n2, n3;
	
	public Triangle(GNode n1, GNode n2, GNode n3) {
		this.n1 = n1;
		this.n2 = n2;
		this.n3 = n3;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g, Camera camera) {
		// TODO Auto-generated method stub
		
	}

}

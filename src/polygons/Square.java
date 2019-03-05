package polygons;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import game.Camera;
import game.Env;
import object_categories.Polygon;
import rendering.Render;
import support_lib.Orient3D;
import support_lib.Vector3D;

public class Square extends Polygon {
	public Orient3D orient;
	public Vector3D[] points;
	public Color color = null;
	public int length;
	int id;
	public static long fillTime = 0;
	public static long outlineTime = 0;
	
	// centered: If true, 
	public Square(Vector3D referencePoint, boolean centered, Vector3D loc, Orient3D squareOrient, int length) {
		if(centered == false){
			// referencePoint refers to the left corner of the square
			this.loc = (Vector3D) referencePoint.add(squareOrient.yz.multiply(length));
		}else{
			this.loc = referencePoint;
		}
	}

	public Square(Vector3D leftCorner, Vector3D loc, Orient3D squareOrient, int length) {
		this.loc = (Vector3D) leftCorner.add(squareOrient.yz.multiply(length));
	}
	
	public Square(Vector3D cubeLoc, Orient3D cubeOrient, int length, int id){
		this.length = length;
		this.id = id;

		// Initialize points
		points = new Vector3D[4];
		for(int i=0; i<4; i++){
			points[i] = new Vector3D(0,0,0);
		}

		// Calculate cubes orient
		setOrient(cubeOrient);

		// Get location according to cubeLoc
		setLoc(cubeLoc, cubeOrient);

		setPoints();
	}

	public void setPoints(){
		points[0] = (Vector3D) loc.add(orient.xy.multiply(-length/2).add(orient.xz.multiply(length/2)));
		points[1] = (Vector3D) loc.add(orient.xy.multiply(length/2).add(orient.xz.multiply(length/2)));
		points[2] = (Vector3D) loc.add(orient.xy.multiply(length/2).add(orient.xz.multiply(-length/2)));
		points[3] = (Vector3D) loc.add(orient.xy.multiply(-length/2).add(orient.xz.multiply(-length/2)));
	}

	public Vector3D setLoc(Vector3D cubeLoc, Orient3D cubeOrient){
		this.loc = (Vector3D)cubeLoc.add(orient.yz.multiply(( -1.0 * (double)length)/2.0));
		return this.loc;
	}

	public Orient3D setOrient(Orient3D cubeOrient){
		Orient3D myOrient = null;
		
		switch(id){
		case 0:
			myOrient = cubeOrient.clone();
			break;
		case 1:
			Vector3D orientXY1 = (Vector3D)cubeOrient.yz.multiply(-1);
			Vector3D orientYZ1 = (Vector3D)cubeOrient.xy.multiply(1);
			Vector3D orientXZ1 = (Vector3D)cubeOrient.xz.multiply(1);
			myOrient = new Orient3D(orientXY1, orientYZ1, orientXZ1);
			break;
		case 2:
			Vector3D orientXY2 = (Vector3D)cubeOrient.xy.multiply(-1);
			Vector3D orientYZ2 = (Vector3D)cubeOrient.yz.multiply(-1);
			Vector3D orientXZ2 = (Vector3D)cubeOrient.xz.multiply(1);
			myOrient = new Orient3D(orientXY2, orientYZ2, orientXZ2);
			break;
		case 3:
			// Square 3
			Vector3D orientXY3 = (Vector3D)cubeOrient.yz.multiply(1);
			Vector3D orientYZ3 = (Vector3D)cubeOrient.xy.multiply(-1);
			Vector3D orientXZ3 = (Vector3D)cubeOrient.xz.multiply(1);
			myOrient = new Orient3D(orientXY3, orientYZ3, orientXZ3);
			break;
		case 4:
			Vector3D orientXY4 = (Vector3D)cubeOrient.xy.multiply(1);
			Vector3D orientYZ4 = (Vector3D)cubeOrient.xz.multiply(-1);
			Vector3D orientXZ4 = (Vector3D)cubeOrient.yz.multiply(1);
			myOrient = new Orient3D(orientXY4, orientYZ4, orientXZ4);
			break;
		case 5:
			Vector3D orientXY5 = (Vector3D)cubeOrient.xy.multiply(1);
			Vector3D orientYZ5 = (Vector3D)cubeOrient.xz.multiply(1);
			Vector3D orientXZ5 = (Vector3D)cubeOrient.yz.multiply(-1);
			myOrient = new Orient3D(orientXY5, orientYZ5, orientXZ5);
			break;
		default:
			return null;
		}
		
		this.orient = myOrient;
		return myOrient;
	}
	
	public boolean inFocalView(Vector3D mults, Camera camera, double xyAngle, double xzAngle) {
		// check xy focal
		double xyFocalLength = mults.dy() * Math.tan(xyAngle);
		
		if(mults.dx() < -xyFocalLength || mults.dx() > xyFocalLength) {
			return false;
		}
		
		// check xz focal
		double xzFocalLength = mults.dy() * Math.tan(xzAngle);
		
		if(mults.dz() < -xzFocalLength || mults.dz() > xzFocalLength) {
			return false;
		}
		
		return true;
	}

	public int[] getRender(Graphics g, Camera camera){
		Vector3D[] diffs = new Vector3D[4];

		for(int i=0; i<4; i++){
			// Get the difference from the camera to the point
			diffs[i] = new Vector3D(
					this.points[i].dx() - camera.loc.dx(),
					this.points[i].dy() - camera.loc.dy(),
					this.points[i].dz() - camera.loc.dz()
					);
		}
		
		Vector3D mults[] = new Vector3D[4];
		boolean atLeastOneIsVisible = false;
		for(int i=0; i<4; i++){
			mults[i] = Vector3D.getBasisMultiples(camera.orient, diffs[i]);
			/*
			if(mults[i].dy() > 0){
				atLeastOneIsVisible = true;
			}
			*/
			if(inFocalView(mults[i], camera, Render.xyAngle, Render.xzAngle)) {
				atLeastOneIsVisible = true;
			}
		}
		
		if(atLeastOneIsVisible){
			// Then draw it
		}else{
			// If no visible, no draw
			return null;
		}

		int xPoints[] = new int[4];
		int yPoints[] = new int[4];

		for(int i=0; i<4; i++){
			int[] screenLoc = Render.getScreenLoc(mults[i]);

			if(null == screenLoc){
				return null;
			}

			xPoints[i] = screenLoc[0];
			yPoints[i] = screenLoc[1]; 
		}

		int[] points = new int[8];
		for(int i=0; i<4; i++){
			points[i] = xPoints[i];
			points[i+4] = yPoints[i];
		}
		return points;
	}

	public void render(Graphics g, Camera camera){
		int[] points = new int[8];
		int[] xPoints = new int[4];
		int[] yPoints = new int[4];

		points = getRender(g, camera);
		if(null == points){
			return;
		}

		for(int i=0; i<4; i++){
			xPoints[i] = points[i];
			yPoints[i] = points[i+4];
		}
		
		g.setColor(this.color);
		Env.timeTracker.start("squareFillTime");
		g.fillPolygon(xPoints, yPoints, 4);
		Env.timeTracker.end("squareFillTime", 6*1000);
		
		g.setColor(Color.WHITE);
		Env.timeTracker.start("squareOutlineTime");
		g.drawPolygon(xPoints, yPoints, 4);
		Env.timeTracker.end("squareOutlineTime", 6*1000);

		//time++;
	}

}

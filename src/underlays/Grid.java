package underlays;

import java.awt.Color;
import java.awt.Graphics;

import game.Camera;
import game.Env;
import object_categories.Underlay;
import support_lib.Vector3D;

public class Grid extends Underlay {
	// Had to make these static for the static methods
	// Should probably find another way to do that
	static Graphics g = null;
	static double initXSpacingPercent = .15;
	static double initYSpacingPercent = .1;
	static double xSpacingDec = .9;
	static double ySpacingDec = .9;
	static double gridScale = 10;

	public int fociX;
	public int fociY;

	Camera camera;
	public int resWidth;
	public int resHeight;
	double fociLengthPercent = 1.5;

	public Grid(Camera camera, int resWidth, int resHeight){
		this.camera = camera;
		this.resWidth = resWidth;
		this.resHeight = resHeight;
		fociX = resWidth / 2;
		fociY = 0;
	}

	public void render(Graphics g) {
		Grid.g = g;
		drawGrid();
	}

	private void drawGrid(){
		//drawCenter();
		drawXLines();
		drawYLines();
	}

	public static void drawCenter(){
		g.setColor(Color.RED);
		g.fillOval(Env.resWidth/2 - 10, Env.resHeight/2 - 10, 20, 20);
	}
	
	public int[] getScreenLoc(Vector3D mults, Camera camera){
		int x, y, z;

		// Calculate the lengths
		x = distToPixX(mults.dx());
		y = distToPixY(mults.dy());
		z = distToPixY(mults.dz());
		
		//System.out.println("mults: " + mults.dx + ", distToPix: " + x);
		//System.out.println("mults: " + mults.dy + ", distToPix: " + y);
		//System.out.println("mults: " + mults.dz + ", distToPix: " + z);

		double[] centerPlusXOffset = {Env.resWidth / 2 + x, Env.resHeight / 2};
		double[] vectorToFoci = {fociX - centerPlusXOffset[0], fociY - centerPlusXOffset[1]};

		// Normalize the vector
		double normalizeValue = Math.sqrt(Math.pow(vectorToFoci[0], 2) + Math.pow(vectorToFoci[1], 2));
		vectorToFoci[0] = (vectorToFoci[0] / normalizeValue);
		vectorToFoci[1] = (vectorToFoci[1] / normalizeValue);
		// Multiply by y length
		vectorToFoci[0] = vectorToFoci[0] * y;
		vectorToFoci[1] = vectorToFoci[1] * y;

		int[] finalLoc = new int[2];
		finalLoc[0] = (int)vectorToFoci[0] + x + Env.resWidth / 2;
		finalLoc[1] = (Env.resHeight/2) - (int)vectorToFoci[1] - z;
		
		// Checking if the lengths added up
		//double length = Math.sqrt(Math.pow(vectorToFoci[0], 2) + Math.pow(vectorToFoci[1], 2));
		//System.out.println("This: " + length + " should == this: " + y);

		return finalLoc;
	}

	private void drawXLines(){
		g.setColor(Color.green);

		int curDist = 0;

		for(int i=0; i<100; i++){
			int curY = (int)(Env.resHeight - distToPixY(curDist) - 1);
			g.drawLine(0, curY, Env.resWidth, curY);
			curDist += 50;
		}
	}

	private void drawYLines(){
		g.setColor(Color.green);
		g.drawLine(Env.resWidth/2, Env.resHeight, fociX, fociY);
		
		int curDist = 50;
		
		for(int i=0; i<100; i++){
			int curX = distToPixX(curDist);
			g.drawLine(Env.resWidth/2 - curX, Env.resHeight, fociX, fociY);
			g.drawLine(Env.resWidth/2 + curX, Env.resHeight, fociX, fociY);
			curDist += 50;
		}
	}
	
	public static int distToPixX(double dist){
		int val = (int) (4 * Math.pow(Math.abs(dist), .9));
		if(dist < 0){
			return -1 * val;
		}
		return val;
	}
	
	public static int distToPixY(double dist){
		int val = (int) (4 * Math.pow(Math.abs(dist), .80));
		if(dist < 0){
			return -1 * val;
		}
		return val;
	}
	
}

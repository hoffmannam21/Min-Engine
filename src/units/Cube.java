package units;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import game.Camera;
import game.Env;
import object_categories.GameObject;
import object_categories.Polygon;
import polygons.Square;
import support_lib.Vector3D;

public class Cube extends GameObject {
	public static Camera camera;
	public Square[] squares;
	
	public int length;
	double rotateSpeed = 10; // Env.rand(0, 10);
	double velocity = 10;
	public boolean rotate = false;
	public double speed = 0;
	static Random rand = new Random();
	boolean ready = false;

	public Cube(Vector3D loc, Vector3D dir, int length, Camera cam){
		super(loc, dir.normalize());
		camera = cam;
		this.length = length;
		initSquares();
		chanceRotate();
		chanceMove();
	}

	public void revolveShow(){
		
	}
	
	public void rotate(){
		if(rotate){
			Env.timeTracker.start("rotateCheck1");
			//this.orient.rotate("XY", rotateSpeed);
			this.orient.rotate("YZ", rotateSpeed);
			//this.orient.rotate("XZ", rotateSpeed);
			//this.loc.add$(this.orient.yz.multiply(speed));
			//recalcSquares();
			Env.timeTracker.end("rotateCheck1", 1000);
		}
	}
	
	public void chanceRotate() {
		if(rand.nextFloat() > -1) {
			rotate = true;
			rotateSpeed = .95;
			//rotateSpeed = rand.nextFloat();
		}
	}
	
	public void chanceMove() {
		//speed = rand.nextFloat() * 20;
		speed = 5;
	}
	
	public void tick() {
		Env.timeTracker.start("tickCheck");
		rotate();
		move();
		Env.timeTracker.end("tickCheck", 1000);
	}
	
	public void move() {
		if(speed == 0) {
			
		}else {
			this.loc.add$(orient.yz.multiply(speed));
			recalcSquares();
		}
	}

	public void render(Graphics g, Camera camera){
		/*
		double[] distances = new double[6];
		for(int i=0; i<6; i++){
			distances[i] = camera.loc.distanceBetween(squares[i].loc());
		}

		int[] indexArr = {0,1,2,3,4,5};
		sort(distances, indexArr);
		for(int i=5; i>=0; i--){
			squares[i].render(g,camera);
		}
		*/
	}

	public void sort(double[] arr, int[] indexArr){
		for(int i=0; i<arr.length-1; i++){
			for(int j=0; j<arr.length-1; j++){
				if(arr[j] > arr[j+1]){
					double temp = arr[j];
					arr[j] = arr[j+1];
					arr[j+1] = temp;

					int tempInt = indexArr[j];
					indexArr[j] = indexArr[j+1];
					indexArr[j+1] = tempInt;
				}
			}
		}
	}

	/*
	 * Adjusts their location and orientation
	 */
	public void recalcSquares(){
		for(int i=0; i<6; i++){
			squares[i].setOrient(this.orient);
			squares[i].setLoc(this.loc, this.orient);
			squares[i].setPoints();
		}
	}

	public void initSquares(){
		squares = new Square[6];

		for(int i=0; i<6; i++){
			squares[i] = new Square(this.loc, this.orient, length, i);
		}

		Color c = new Color(rand.nextInt(0xFFFFFF));
		for(int i=0; i<6; i++){
			squares[i].color = c;
		}
	}

	@Override
	public Polygon[] getPolygons() {
		//Polygon[] singleRender = new Polygon[1];
		//singleRender[0] = squares[0];
		//return (Polygon[])singleRender;
		return (Polygon[])squares;
	}

}

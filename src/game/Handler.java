package game;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedList;

import object_categories.GameObject;
import object_categories.Overlay;
import object_categories.Polygon;
import object_categories.Underlay;
import polygons.Square;
import support_lib.EffTrackerList;
import support_lib.ParallelMergeSort;
import units.Cube;

public class Handler implements Runnable {
	Camera camera;
	Square[] background = new Square[6];
	static int count = 0;

	ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
	ArrayList<Polygon> polygons = new ArrayList<Polygon>();
	ArrayList<Overlay> overlays = new ArrayList<Overlay>();
	ArrayList<Underlay> underlays = new ArrayList<Underlay>();

	public void run(){

	}

	public void tick(){
		Env.timeTracker.start("tickCheck2");
		for(int i = 0; i < gameObjects.size(); i++) {
			Env.timeTracker.start("tickGettingGameObject");
			GameObject tempObject = gameObjects.get(i);
			Env.timeTracker.end("tickGettingGameObject", gameObjects.size());
			tempObject.tick();
			if(!tempObject.getClass().equals(Cube.class)) {
				System.out.println("\nGameObject not cube, is: " + tempObject.getClass().getName() + "\n");
			}
		}
		Env.timeTracker.end("tickCheck2");
		camera.tick();
		/* TODO
		 * instead of camera.tick(), have
		 * 'player.tick()'
		 * player will have a camera
		 * apply controller input
		 *  (should we use listeners instead?)
		 */
	}

	public void render(Graphics g) {
		for(int i=0; i<underlays.size(); i++){
			Underlay temp = underlays.get(i);
			temp.render(g);
		}

		renderGameObjects(g);

		for(int i=0; i<overlays.size(); i++){
			Overlay temp = overlays.get(i);
			temp.render(g);
		}
	}

	public void renderGameObjects(Graphics g){
		int numPolygons = polygons.size();
		double[] distances = new double[numPolygons];
		int[] indexArr = new int[polygons.size()];
		
		Env.timeTracker.start("polyDist");
		for(int i=0; i<numPolygons; i++){
			distances[i] = polygons.get(i).loc().distanceBetween(camera.loc);
		}
		Env.timeTracker.end("polyDist");
		
		for(int i=0; i<numPolygons; i++){
			indexArr[i] = i;
		}
		
		//Env.timeTracker.start("bubbleSort");
		//bubbleSorts(distances, indexArr);
		//Env.timeTracker.end("bubbleSort");

		Env.timeTracker.start("parallelMergeSort");
		ParallelMergeSort.sort(distances, indexArr);
		Env.timeTracker.end("parallelMergeSort");

		Env.timeTracker.start("renderTime");
		for(int i=polygons.size() - 1; i >= 0; i--) {
			polygons.get(indexArr[i]).render(g,camera);
		}
		Env.timeTracker.end("renderTime");
		
		count++;
		
		if(count == 100) {
			Env.timeTracker.print();
			count = 0;
		}
	}

	public void bubbleSorts(double[] arr, int[] indexArr) {
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

	public void initPolygons() {
		// Collect all the polygons
		GameObject tempObject = null;
		Polygon[] tempPolygons = null;
		for(int i=0; i<gameObjects.size(); i++){
			tempObject = gameObjects.get(i);
			tempPolygons = tempObject.getPolygons();
			for(int j=0; j<tempPolygons.length; j++) {
				polygons.add(tempPolygons[j]);
			}
		}
	}

	public void addUnderlay(Underlay underlay){
		this.underlays.add(underlay);
	}

	public void removeUnderlay(Underlay underlay){
		this.underlays.remove(underlay);
	}

	public void addOverlay(Overlay overlay) {
		this.overlays.add(overlay);
	}

	public void removeOverlay(Overlay overlay) {
		this.overlays.remove(overlay);
	}

	public void addObject(GameObject object) {
		this.gameObjects.add(object);
		addPolygons(object.getPolygons());
	}

	public void addPolygons(Polygon[] objectPolygons) {
		for(int i=0; i<objectPolygons.length; i++) {
			this.polygons.add(objectPolygons[i]);
		}
	}

	public void removeObject(GameObject object) {
		this.gameObjects.remove(object);
	}

	public void setCamera(Camera camera){
		this.camera = camera;
	}

}

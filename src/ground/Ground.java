package ground;

import java.awt.Graphics;

import game.Camera;
import object_categories.GameObject;
import object_categories.Polygon;
import support_lib.Orient3D;
import support_lib.Vector3D;

public class Ground extends GameObject {
	// Stored as both a Vector web and a list of squares
	Vector3D[][] vectorGrid;
	Tile[][] tiles;

	public Ground(Vector3D initPos, int tilesWide, Orient3D orientation) {
		super(initPos, new Vector3D(0,0,0));
		constructGround(initPos, tilesWide, orientation);
	}

	public void constructGround(Vector3D initPos, int tilesWide, Orient3D orientation) {
		vectorGrid = new Vector3D[tilesWide][tilesWide];
		tiles = new Tile[tilesWide][tilesWide];
		double stepSize = 10;
		for(int i=0; i<tilesWide; i++) {
			for(int j=0; j<tilesWide; j++) {
				vectorGrid[i][j] = (Vector3D)initPos.add(orientation.xy.multiply(i*stepSize).add(orient.yz.multiply(-j*stepSize)));
			}
		}
		
		for(int i=0; i<tilesWide-1; i++) {
			for(int j=0; j<tilesWide-1; j++) {
				tiles[i][j] = new Tile(vectorGrid, i,j, vectorGrid[i][j], initPos, orientation, 10);
			}
		}
	}

	public void transform(Vector3D impulseDirection, double strength, int midX, int midY) {
		Vector3D trans = (Vector3D)impulseDirection.multiply(strength);
		vectorGrid[midX][midY].add$(trans);
		int ripples = 5;
		
		// Ripple throughout shells
		for(int i=1; i<ripples; i++) {
			// top ripple
			for(int x=(midX-i); x<(midX+i); x++) {
				vectorGrid[x][midY-i].add$(trans);
			}

			// bottom ripple
			for(int x=(midX-i); x<(midX+i); x++) {
				vectorGrid[x][midY+i].add$(trans);
			}

			// left ripple
			for(int y=(midY-i+1); y<(midY+i-1); y++) {
				vectorGrid[midX-i][y].add$(trans);
			}

			// right ripple
			for(int y=(midY-i+1); y<(midY+i-1); y++) {
				vectorGrid[midX-i][y].add$(trans);
			}

			trans.multiply(.8);
		}
	}
	
	Vector3D collisionDetection(Vector3D playerLoc) {
		
		return null;
	}

	@Override
	public void tick() {
		
	}

	@Override
	public void render(Graphics g, Camera camera) {
		for(int i=0; i<tiles.length; i++) {
			for(int j=0; j<tiles[0].length; j++) {
				tiles[i][j].render(g, camera);
			}
		}
	}

	@Override
	public Polygon[] getPolygons() {
		Polygon[] polygons = new Polygon[tiles.length * tiles[0].length];
		for(int i=0; i<tiles.length; i++) {
			for(int j=0; j<tiles[0].length; j++) {
				int index = i*tiles.length + j;
				polygons[index] = tiles[i][j];
				polygons[index].loc = tiles[i][j].loc();
			}
		}
		return polygons;
	}


}

package ground;

import support_lib.Vector3D;

public class GNode {
	Vector3D location;
	GNode[] neighbors;
	
	void drawIt() {
		GNode pointer = null; // startNode
		GNode prev = pointer;
		
		while(true) {
			int curPoint = 0;
			//collect nodes
			pointer = pointer.neighbors[curPoint++];
			pointer = pointer.neighbors[curPoint++];
			
		}
	}
}

package support_lib;

public class Vector3D extends Matrix {
	public static final  Vector3D origin = new Vector3D(0,0,0);
	
	public static final Vector3D XY = new Vector3D(1,0,0);
	public static final Vector3D YZ = new Vector3D(0,1,0);
	public static final Vector3D XZ = new Vector3D(0,0,1);

	public Vector3D(double dx, double dy, double dz) {
		super(new double[][]{
			{dx},
			{dy},
			{dz}
			});
	}
	
	public Vector3D(double[][] values){
		super(values);
		if(
				(values.length != 3) ||
				(values[0].length != 1)
				){
			System.out.println("Error creating Vector3D, exiting");
			System.exit(-1);
		}
	}
	
	public void print(){
		System.out.println("(" + dx() + ", " + dy() + ", " + dz() + ")");
	}
	
	public static Vector3D crossProduct(Vector3D u, Vector3D v){
		return new Vector3D(
				(u.dy() * v.dz() - u.dz() * v.dy()),
				(-(u.dx() * v.dz() - u.dz() * v.dx())),
				(u.dx() * v.dy() - u.dy() * v.dx())
				);
	}
	
	public static Matrix combineVectors(Vector3D[] vectors){
		int cols = vectors.length;
		int rows = 3;
		
		double[][] values = new double[rows][cols];

		for(int col = 0; col < cols; col++){
			for(int row = 0; row < rows; row++){
				values[row][col] = vectors[col].values()[row][0];
			}
		}
		
		return new Matrix(values);
	}
	
	public double distanceBetween(Vector3D point){
		return Math.sqrt(Math.pow((point.dx() - dx()), 2) + Math.pow((point.dy() - dy()), 2) + Math.pow((point.dz() - dz()), 2));
	}
	
	public static Vector3D getBasisMultiples(Orient3D orient, Vector3D camLoc){
		Vector3D XY = orient.xy;
		Vector3D YZ = orient.yz;
		Vector3D XZ = orient.xz;
		
		// Combine all vectors into a matrix
		// Create vector array
		Vector3D[] vectors = new Vector3D[4];
		vectors[0] = XY;
		vectors[1] = YZ;
		vectors[2] = XZ;
		vectors[3] = camLoc;
		
		Matrix system = combineVectors(vectors);
		Matrix clone = system.clone();
		clone.rref();
		
		return new Vector3D((int)clone.values()[0][3], (int)clone.values()[1][3], (int)clone.values()[2][3]);
	}
	
	public Vector3D projectOntoPlane(Vector3D planePoint, Vector3D planeNormal){
		Vector3D projection;
		//double t = (a*d - a*x + b*e - b*y + c*f - c*z) / 
		//		( Math.pow(a, 2) + Math.pow(b, 2) + Math.pow(c, 2) );
		
		double t = (planeNormal.dx()*planePoint.dx() - planeNormal.dx()*dx() + planeNormal.dy()*planePoint.dy() - planeNormal.dy()*dy() + planeNormal.dz()*planePoint.dz() - planeNormal.dz()*dz()) / 
						( Math.pow(planeNormal.dx(), 2) + Math.pow(planeNormal.dy(), 2) + Math.pow(planeNormal.dz(), 2) );
		
		projection = new Vector3D((dx() + t*planeNormal.dx()), (dy() + t*planeNormal.dy()), (dz() + t*planeNormal.dz()));
		
		return projection;
	}
	
	public Vector3D normalize(){
		double dist = Math.sqrt(Math.pow(dx(), 2) + Math.pow(dy(), 2) + Math.pow(dz(), 2));
		return new Vector3D(dx()/dist, dy()/dist, dz()/dist);
	}
	
	public double dot(Vector3D v){
		return dx() * v.dx() + dy() * v.dy() + dz() * v.dz();
	}
	
	public double dx(){
		return values()[0][0];
	}
	
	public double dy(){
		return values()[1][0];
	}
	
	public double dz(){
		return values()[2][0];
	}
	
	public void setDX(double dx){
		values()[0][0] = dx;
	}
	
	public void setDY(double dy){
		values()[1][0] = dy;
	}
	
	public void setDZ(double dz){
		values()[2][0] = dz;
	}
}

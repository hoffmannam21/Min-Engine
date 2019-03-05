package rendering;

import game.Env;
import support_lib.Vector3D;

public class Render {
	public static double xyAngle = Math.toRadians(30);
	public static double xzAngle = Math.toRadians(45);

	public static int[] getScreenLoc(Vector3D mults){
		double dx = mults.dx();
		double dy = mults.dy();
		double dz = mults.dz();

		//double resXHalf = Env.resWidth / 2;
		//double resYHalf = Env.resHeight / 2;

		int rw = Env.resWidth;
		int rh = Env.resHeight;

		if(dy == 0){
			return null;
		}
		
		//int x = (int) ( resXHalf + ( dx * Math.tan(xyAngle) / dy * resXHalf ) );
		//int y = (int) ( resYHalf - ( dz * Math.tan(xzAngle) / dy * resYHalf ) );

		int x, y = 0;

		double xFocalLength = dy * Math.tan(xyAngle);
		double zFocalLength = dy * Math.tan(xzAngle);

		double dxFocalRatio = dx / ( xFocalLength );
		double dzFocalRatio = dz / ( zFocalLength );
		
		if(dy > 0) {
			// regular render
			x = (int) ( (rw/2) + ( (rw/2) * dxFocalRatio ) );
			y = rh - (int) ( (rh/2) + ( (rh/2) * dzFocalRatio ) );
		} else {
			// one of the square's points is behind the camera
			int dxDirection = 1;
			int dzDirection = 1;
			if(dx < 0) {
				dxDirection = -1;
			}
			if(dz < 0) {
				dzDirection = -1;
			}

			x = (int) ( (rw/2) + ( (rw/2) * ( dxDirection + (rw/2) * -dxFocalRatio ) ) );
			y = rh - (int) ( (rh/2) + ( (rh/2) * ( dzDirection + (rh/2) * -dzFocalRatio ) ) );
		}

		return new int[]{x,y};
	}
}

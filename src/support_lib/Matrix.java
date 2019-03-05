package support_lib;

public class Matrix {
	private double[][] values;

	public Matrix(double[][] values){
		checkInvalidMatrix(values);
		this.values = values;
	}
	
	void checkInvalidMatrix(double[][] values){
		if(null == values){
			invalidMatrix("Null pointer");
		}
		if(values.length == 0) {
			invalidMatrix("Size 0 Array, 1");
		}else if(values[0].length == 0){
			invalidMatrix("Size 0 Array, 2");
		}
	}
	
	void invalidMatrix(String reason){
		System.err.println("Invalid matrix (" + reason + "), exiting");
		System.exit(-1);
	}
	
	public Matrix add$(Matrix matrix){
		this.values = this.add(matrix).values;
		return this;
	}
	
	public Matrix add(Matrix matrix){
		if(
				(values.length != matrix.values.length) ||
				(values[0].length != matrix.values[0].length)
				){
			return null;
		}
		double[][] newValues = new double[values.length][values[0].length];
		for(int i=0; i<values.length; i++){
			for(int j=0; j<values[0].length; j++){
				//System.out.println("add: values[i][j]: " + values[i][j]);
				//System.out.println("add: matrix.values[i][j]: " + matrix.values[i][j]);
				newValues[i][j] = values[i][j] + matrix.values[i][j];
			}
		}
		if(this.getClass().equals(Vector3D.class)){
			return new Vector3D(newValues);
		}
		return new Matrix(newValues);
	}
	
	public Matrix clone(){
		double[][] clone = new double[rows()][columns()];
		
		for(int i=0; i<this.rows(); i++){
			for(int j=0; j<this.columns(); j++){
				clone[i][j] = this.values[i][j];
			}
		}
		
		if(getClass() == Vector3D.class){
			return new Vector3D(clone);
		}
		
		return new Matrix(clone);
	}
	
	public Matrix multiply(double d){
		double[][] newValues = new double[values.length][values[0].length];
		for(int i=0; i<values.length; i++){
			for(int j=0; j<values[0].length; j++){
				newValues[i][j] = values[i][j] * d;
			}
		}
		
		if(this.getClass().equals(Vector3D.class)){
			return new Vector3D(newValues);
		}
		return new Matrix(newValues);
	}

	public Matrix multiply(Matrix m){
		if(this.invalid()){
			System.out.println("Error in matrix mult\n"
					+ "Left matrix is invalid\n"
					+ "Exiting");
			System.exit(-1);
		}else if(!this.canMultiplyWith(m)){
			System.out.println("Error in matrix mult\n"
					+ "Matrices cannot be multiplied: invalid dimensions\n"
					+ "Exiting");
			System.exit(-1);
		}

		double[][] result = new double[this.rows()][m.columns()];

		// recurse through the rows and add
		for(int i=0; i<this.rows(); i++){
			for(int k=0; k<m.columns(); k++){
				double colSum = 0;
				for(int j=0; j<m.rows(); j++){
					colSum += this.values[i][j] * m.values[j][k];
				}
				result[i][k] = colSum;
			}
		}
		if(result[0].length == 1 && result.length == 3 && 
				((this.getClass() == Vector3D.class) || (m.getClass() == Vector3D.class))
				){
			return new Vector3D(result);
		}
		return new Matrix(result);
	}

	public boolean invalid(){
		if((this.values.length == 0) || (this.values[0].length == 0)){
			return true;
		}
		return false;
	}

	public boolean canMultiplyWith(Matrix m){
		if(this.columns() == m.rows()){
			return true;
		}
		return false;
	}

	public int rows(){
		return this.values.length;
	}

	public int columns(){
		return this.values[0].length;
	}
	
	public static double[][] rref(double[][] mat)
	{
	    double[][] rref = new double[mat.length][mat[0].length];

	    /* Copy matrix */
	    for (int r = 0; r < rref.length; ++r)
	    {
	        for (int c = 0; c < rref[r].length; ++c)
	        {
	            rref[r][c] = mat[r][c];
	        }
	    }

	    for (int p = 0; p < rref.length; ++p)
	    {
	        /* Make this pivot 1 */
	        double pv = rref[p][p];
	        if (pv != 0)
	        {
	            double pvInv = 1.0 / pv;
	            for (int i = 0; i < rref[p].length; ++i)
	            {
	                rref[p][i] *= pvInv;
	            }
	        }

	        /* Make other rows zero */
	        for (int r = 0; r < rref.length; ++r)
	        {
	            if (r != p)
	            {
	                double f = rref[r][p];
	                for (int i = 0; i < rref[r].length; ++i)
	                {
	                    rref[r][i] -= f * rref[p][i];
	                }
	            }
	        }
	    }

	    return rref;
	}
	
	public void rref(){
		int pivotRow = 0;
		int rows = values.length;
		int cols = values[0].length;
		int colsToIterate;
		if(rows >= cols){
			colsToIterate = cols;
		}else{
			colsToIterate = rows;
		}
		
		//System.out.println("cols: " + cols + ", rows: " + rows);
		
		for(int col=0; col<colsToIterate; col++){
			double pivot = values[pivotRow][col];
			if(pivot == 0){
				// Search for a pivot in the column
				// If found, swap with that row and continue
				// Else continue to the next column, but print an error warning
				boolean foundPivot = false;
				for(int i=pivotRow+1; i<rows; i++){
					if(values[i][col] != 0){
						// We've found our pivot
						// Swap rows and break
						rowSwap(pivotRow, i);
						foundPivot = true;
						break;
					}
				}
				if(foundPivot){
					// No need to do anything else
				}else{
					// Continue to the next column
					pivotRow++;
					continue;
				}
			}
			
			// Make the pivot 1
			rowMult(pivotRow, 1/values[pivotRow][col]);
			
			// Zero out the values below pivot
			for(int row=pivotRow+1; row<rows; row++){
				double rowMult = -1 * values[row][col] / values[pivotRow][col];
				rowAdd(pivotRow, row, rowMult);
			}
			
			pivotRow++;
		}
		
		// Now to zero out the upper half
		//System.out.println("Zero upper half:");
		pivotRow = rows-1;
		
		//System.out.println("colsToIterate: " + colsToIterate + ", pivotRow: " + pivotRow + ", rows: " + rows);
		
		for(int col = colsToIterate-1; col >= 1; col--){
			double pivot = values[pivotRow][col];
			if(pivot == 0){
				//System.out.println("Pivot is zero: @ (" + pivotRow + ", " + col + ")");
				pivotRow--;
				col++;
				continue;
			}
			for(int row = pivotRow-1; row >= 0; row--){
				rowAdd(pivotRow, row, -values[row][col]/pivot);
			}
			pivotRow--;
		}
	}
	
	public void rowSwap(int r1, int r2){
		for(int i=0; i<values[0].length; i++){
			double temp = values[r1][i];
			values[r1][i] = values[r2][i];
			values[r2][i] = temp;
		}
		//System.out.println("c" + r1 + " <-> c" + r2);
		//print();
	}
	
	public void rowAdd(int c_adder, int c_addedTo, double mult){
		for(int col=0; col<values[0].length; col++){
			values[c_addedTo][col] += mult * values[c_adder][col];
		}
		
		/*
		String sign;
		if(mult >= 0){
			sign = "+";
		}else{
			sign = "-";
		}
		System.out.println("c" + c_addedTo + " " + sign + " " + Math.abs(mult) + "c" + c_adder);
		print();
		*/
	}
	
	public void rowMult(int row, double mult){
		for(int col=0; col<values[0].length; col++){
			values[row][col] *= mult;
		}
		//System.out.println("c" + row + " * " + mult);
		//print();
	}
	
	public void print(){
		System.out.println(valuesToStr());
	}
	
	public void printMatrix(){
		System.out.println(valuesToStr());
	}
	
	public String valuesToStr(){
		int spaces = getSpacing();
		String str = "";
		for(int i=0; i<values.length; i++){
			for(int j=0; j<values[0].length; j++){
				double abs = Math.abs(values[i][j]);
				int addSpaces;
				if(closeToZero(abs)){
					addSpaces = spaces - 1;
				}else{
					addSpaces = spaces - (int)Math.log10(abs);
				}
				
				if(closeToZero(values[i][j])){
					addSpaces++;
				}
				
				if(values[i][j] >= 0){
					// Sometimes a negative 0 happens, so this is to avoid that
					values[i][j] = abs;
					str += String.format(" %.7f", values[i][j]);
					for(int k=0; k<addSpaces; k++){
						str += " ";
					}
					//str += "+";
				}else{
					str += String.format("%.7f", values[i][j]);
					for(int k=0; k<addSpaces; k++){
						str += " ";
					}
					//str += "-";
				}
			}str += "\n";
		}
		return str;
	}
	
	public int getSpacing(){
		double max = Math.abs(values[0][0]);
		for(int i=0; i<values.length; i++){
			for(int j=0; j<values[0].length; j++){
				double abs = Math.abs(values[i][j]);
				if(abs > max){
					max = abs;
				}
			}
		}
		return (int)Math.log10(max);
	}
	
	public static boolean closeToZero(double val){
		if(Math.abs(val) < .00001){
			return true;
		}
		return false;
	}
	
	protected double[][] values(){
		return this.values;
	}
	
}

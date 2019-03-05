package support_lib;

public class Vector extends Matrix {
	double[][] values;
	
	Vector(double[][] values){
		super(values);
		checkInvalidVector(values);
	}

	void checkInvalidVector(double[][] values){
		if(!
		(values.length == 1 ||
		values[0].length == 1))
		{
			invalidVector("Not single dimensional");
		}
	}

	void invalidVector(String reason){
		System.err.println("Invalid vector (" + reason + "), exiting");
		System.exit(-1);
	}
}

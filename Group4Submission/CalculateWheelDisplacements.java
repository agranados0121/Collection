package finalProject;
import java.io.FileWriter;
import java.io.IOException;

public class CalculateWheelDisplacements {
	private Double[][] wheelDisplacementMatrix;
	private Double[][] trajectoryMatrix;
	private Double alphaFactor; //this is axle length / 2 * wheel radius (in meters)
	
	public CalculateWheelDisplacements(Double[][] trajectoryMatrix) {
		this.trajectoryMatrix = trajectoryMatrix;
		this.wheelDisplacementMatrix = new Double[trajectoryMatrix.length*2][2];
		this.alphaFactor = 0.12 / (2 * 0.028);		
	}
	
	public void calculateDisplacements() {
		/* 
		Calculates wheel displacements from trajectory matrix and stores results in wheelDisplacementMatrix. 
		Matrix is for relative movements. Stores theta rotation first and then x,y translation. 
		wheelDisplacementMatrix[i][0] = left wheel rotation
		wheelDisplacementMatrix[i][1] = right wheel rotation
		Also calculates and prints total distance traveled in meters
		*/
		Double totalDistanceTraveled = 0.0;
		for(int i = 0; i < trajectoryMatrix.length; i++) {
			//rotation component
			if(trajectoryMatrix[i][2] == 0.0) {
				wheelDisplacementMatrix[2*i][0] = 0.0;
				wheelDisplacementMatrix[2*i][1] = 0.0;
			}
			//if nonzero, right wheel is sign of theta, left wheel is opposite.
			else{
				wheelDisplacementMatrix[2*i][0] = -(trajectoryMatrix[i][2] * alphaFactor);
				wheelDisplacementMatrix[2*i][1] = trajectoryMatrix[i][2] * alphaFactor;
			}
			
			//translation component
			Double distance = Math.sqrt((trajectoryMatrix[i][0] * trajectoryMatrix[i][0]) + (trajectoryMatrix[i][1] * trajectoryMatrix[i][1]));
			wheelDisplacementMatrix[(2*i) + 1][0] = distance / 0.028;
			wheelDisplacementMatrix[(2*i) + 1][1] = distance / 0.028;
			totalDistanceTraveled += distance;
		}
		System.out.printf("Total distance traveled: %.2f" + "m" + "\n", totalDistanceTraveled);
	}
	
	//returns nx2 wheel displacement matrix 
	public Double[][] getWheelDisplacments(){
			return wheelDisplacementMatrix;
	}
	
	//prints wheel displacements
	public void printDisplacements() {
		System.out.println("*** Displacements: ");
		for(int i = 0; i < wheelDisplacementMatrix.length; i++) {
			System.out.println(wheelDisplacementMatrix[i][0] + " " + wheelDisplacementMatrix[i][1]);
		}	
	}
	
	//writes wheel displacements to text file
	public void outputDisplacementsFile() throws IOException {
		FileWriter f = new FileWriter("c:/Users/P3DR0/Desktop/Classes/CSC/CSC595/Project Stuff/FinalProjectSimulatorV2/plan1.txt");
		for(int i = 0; i < wheelDisplacementMatrix.length; i++) {
			f.write(wheelDisplacementMatrix[i][0] + " " + wheelDisplacementMatrix[i][1] + "\n");
		}
		f.close();
	}
}

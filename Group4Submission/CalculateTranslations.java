package finalProject;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class CalculateTranslations {
	private Integer numberNodesOnPath;
	private ArrayList<Point> optimalPath;
	private Queue<Double> rotations = new LinkedList<Double>();
	private Queue<Double[]> translations = new LinkedList<Double[]>();
	private Double[][] trajectoryMatrix;
	private Double currentHeading;
	
	public CalculateTranslations(ArrayList<Point> optimalPath) {
		this.optimalPath = optimalPath;									//optimal path assumes start point and end point are included on path
		this.numberNodesOnPath = optimalPath.size();
		this.trajectoryMatrix = new Double[numberNodesOnPath][3];		//trajectories are relative from one point to the next
		this.currentHeading = Math.PI;									    //this is initial theta rotation at start point. In sim, robot starts at (0,0,pi)
	}
	
	public void calculateTranslations() {
		for(int i = 0; i < optimalPath.size() - 1; i++) {
			/*
			// trajectoryMatrix[i][0] = movement in x to next point
			// trajectoryMatrix[i][1] = movement in y to next point
			// trajectoryMatrix[i][2] = theta rotation to aim to next point
			*/
			// adds translations to trajectory matrix.
			trajectoryMatrix[i][0] = optimalPath.get(i+1).getX() - optimalPath.get(i).getX();
			trajectoryMatrix[i][1] = optimalPath.get(i+1).getY() - optimalPath.get(i).getY();
			
			//adds x,y translations to the translations queue (x, y).
			Double[] currentTranslation = {trajectoryMatrix[i][0], trajectoryMatrix[i][1]};
			translations.add(currentTranslation);
			
			//adds rotations to trajectory matrix.
			if (trajectoryMatrix[i][0] >= 0 && trajectoryMatrix[i][1] >= 0) {
				if(trajectoryMatrix[i][0] == 0 && trajectoryMatrix[i][1] == 0) trajectoryMatrix[i][2] = 0.0;
				else{
					trajectoryMatrix[i][2] = Math.atan((trajectoryMatrix[i][1] / trajectoryMatrix[i][0])) - currentHeading;
					currentHeading = Math.atan((trajectoryMatrix[i][1] / trajectoryMatrix[i][0]));
				}
			}
			if (trajectoryMatrix[i][0] < 0 && trajectoryMatrix[i][1] < 0) {
				trajectoryMatrix[i][2] = Math.atan((trajectoryMatrix[i][1] / trajectoryMatrix[i][0])) - Math.PI - currentHeading;
				currentHeading = Math.atan((trajectoryMatrix[i][1] / trajectoryMatrix[i][0])) - Math.PI;
			}
			if (trajectoryMatrix[i][0] >= 0 && trajectoryMatrix[i][1] < 0) {
				trajectoryMatrix[i][2] = Math.atan((trajectoryMatrix[i][1] / trajectoryMatrix[i][0])) - currentHeading;
				currentHeading = Math.atan((trajectoryMatrix[i][1] / trajectoryMatrix[i][0]));
			}
			if (trajectoryMatrix[i][0] < 0 && trajectoryMatrix[i][1] >= 0) {
				trajectoryMatrix[i][2] = Math.atan((trajectoryMatrix[i][1] / trajectoryMatrix[i][0])) + Math.PI - currentHeading;
				currentHeading = Math.atan((trajectoryMatrix[i][1] / trajectoryMatrix[i][0])) + Math.PI;
			}
			if (trajectoryMatrix[i][2] > Math.PI) {
				Double temp1 = trajectoryMatrix[i][2];
				trajectoryMatrix[i][2] = -((2*Math.PI) - temp1);
			}
			if (trajectoryMatrix[i][2] < -Math.PI) {
				Double temp2 = trajectoryMatrix[i][2];
				trajectoryMatrix[i][2] = (2* Math.PI) + temp2;
			}
			rotations.add(trajectoryMatrix[i][2]);
		}
		
		//fill in last trajectory. Will be on goal. Will be same x,y. Re-adjust theta to 0. Add to translation matrix, translation queue and rotation queue.
		trajectoryMatrix[optimalPath.size()-1][0] = 0.0;
		trajectoryMatrix[optimalPath.size()-1][1] = 0.0;
		trajectoryMatrix[optimalPath.size()-1][2] = 0.0;
		//trajectoryMatrix[optimalPath.size()-1][2] = -currentHeading;
		Double[] lastTranslation = {0.0, 0.0};
		translations.add(lastTranslation);
		rotations.add(-currentHeading);
		
	}
	
	//returns the path
	public ArrayList<Point> getPath(){
		return optimalPath;
	}
	
	//returns a queue of just rotations
	public Queue<Double> getRotations(){
		return rotations;
	}
	
	//returns queue of just translations
	public Queue<Double[]> getTranslations(){
		return translations;
	}
	
	//returns nx3 matrix of trajectories
	public Double[][] getTrajectories(){
		return trajectoryMatrix;
	}
	
	public void printTrajectories(){
		for(int i = 0; i < trajectoryMatrix.length; i++) System.out.println(trajectoryMatrix[i][0] + ", " + trajectoryMatrix[i][1] + ", " + trajectoryMatrix[i][2]);
	}
}

package finalProject;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Math;

public class RectIntersect {
	/**
	 * invalid list of points that are covered with an obstacle
	 * toVisit list of extra points that are not covered
	 * PADDING adds 10 cm to the points to convert robot to a single point
	 * cm to aid converting measurements into cm
	 * max x and y represent upper bounds of plane gets converted to cm
	 * Assume points are given in meters and they're converted to cm
	 * before creating the object
	 */
	public static ArrayList<Point> invalid = new ArrayList<Point>();
	public static ArrayList<Point> toVisit = new ArrayList<Point>();
	public static ArrayList<Point> freeSpace = new ArrayList<Point>();
	public static Point start, end;
	public static final double PADDING = 3;
	public static final int CM = 10;
	public static double MAX_X;
	public static double MAX_Y;
	public static String pathType;
	public static int extra;
	
	
	public static void setMAX_X(double x) {
		MAX_X = x;
	}

	public static void setMAX_Y(double y) {
		MAX_Y = y;
	}

	public static void createOccupied(ArrayList<Point> rects){
		for(int i = (int) rects.get(0).getX(); i <= rects.get(1).getX(); i++) {
			for(int j = (int) rects.get(0).getY(); j <= rects.get(1).getY(); j++) {
				invalid.add(new Point(i,j));
			}
		}
			
		
	}
	public static ArrayList<Point> createRec(Scanner s){
		ArrayList<Point> x = new ArrayList<Point>();
		/*Point p1;
		Point p2;
		System.out.println("Enter the first point in the format of x y. Make sure there is a space between the x and y");
		p1 = new Point((s.nextDouble()*CM)+PADDING,(s.nextDouble()*CM)+PADDING);
		System.out.println("Enter the second point in the format x y. Make sure there is a space between the x and y");
		p2 = new Point((s.nextDouble()*CM)+PADDING,(s.nextDouble()*CM)+PADDING);
		
		x.add(new Point(Math.floor(Math.min(p1.getX(), p2.getX())), 
				Math.floor(Math.min(p1.getY(), p2.getY()))));
		
		x.add(new Point(Math.ceil(Math.max(p1.getX(), p2.getX())), 
				Math.ceil(Math.max(p1.getY(), p2.getY()))));*/
		System.out.println("Enter the first point in the format of x y. Make sure there is a space between the x and y");
		x.add(new Point(Math.floor(s.nextDouble()*CM- PADDING), Math.floor(s.nextDouble()*CM-PADDING)));
		System.out.println("Enter the second point in the format x y. Make sure there is a space between the x and y");
		x.add(new Point(Math.ceil(s.nextDouble()*CM + PADDING), Math.ceil(s.nextDouble()*CM+PADDING)));
		x.sort(null);
		return x;
	}
	
	public static void main(String[] args) throws IOException {
		Scanner s = new Scanner(System.in);
		int rec;
		ArrayList<Point> temp;
		Point pt;
		System.out.println("Enter max x coordinate in meters");
		setMAX_X(s.nextDouble()*CM);
		System.out.println("Enter max y coordinate in meters");
		setMAX_Y(s.nextDouble()*CM);
		System.out.println("How many rectangles to create?");
		rec = s.nextInt();
		for(int i= 0; i < rec; i++) {
			temp = createRec(s);
			createOccupied(temp);
			temp.clear();
		}
		
		System.out.println("Enter start location");
		start = new Point(s.nextDouble()*CM, s.nextDouble()*CM);
		System.out.println("Enter end location");
		end = new Point(s.nextDouble()*CM, s.nextDouble()*CM);
		System.out.print("How many extra points to visit?");
		extra = s.nextInt();
		for(int i = 0; i < extra; i++) {
			System.out.println("Enter extra point to visit");
			pt = new Point(s.nextDouble()*CM, s.nextDouble()*CM);
			if(!invalid.contains(pt) & pt.getX() < MAX_X & pt.getY() < MAX_Y) {
				toVisit.add(pt);
			}
			else {
				System.err.println(pt.toString()+ " is an invalid point");
			}
		}
		toVisit.sort(null);
		System.out.println("Path type: shortest (s) or energy efficient (ee)? ");
		pathType = s.next();
		s.close();
		createEmptySpace();
		//System.out.println(freeSpace.toString());
		
	}

	public static void createEmptySpace() throws IOException {
		Point temp;
		int index_holder = 0;
		FileWriter f = new FileWriter("pointOut.txt");
		for(double i = 0; i <= MAX_X; i++) {
			for(double j = 0; j <= MAX_Y; j++) {
				temp = new Point(i,j);
				temp.index = index_holder++;
				if(!invalid.contains(temp)) {
					f.write(temp.toString()+"\n");
					freeSpace.add(temp);
				}
			}
			
		}
		f.close();
	}

}

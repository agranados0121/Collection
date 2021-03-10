package finalProject;
import java.util.Comparator;

public class Point implements Comparable<Point> {
	 public static final Comparator<Point> X_ORDER = new XOrder();
	 public static final Comparator<Point> Y_ORDER = new YOrder();

	 private final double x;
	 private final double y;
	 private final double theta;
	 public int index;
	
	
	 public Point(double x, double y, double theta) {
	        if (x == 0.0) this.x = 0.0; 
	        else          this.x = x;

	        if (y == 0.0) this.y = 0.0; 
	        else          this.y = y;
	        this.theta = theta;
	    }
	 public Point(double x, double y) {
	     if (x == 0.0) this.x = 0.0; 
	     else          this.x = x;

	     if (y == 0.0) this.y = 0.0; 
	     else          this.y = y;
	     this.theta = 0;
	        
	 }
	 public double getX() {
		 return this.x;
	 }
	 
	 public double getY() {
		 return this.y;
	 }
	 
	 public double getTheta() {
		 return this.theta;
	 }
	 public double distance(Point that) {
		 double dx = this.x - that.x;
		 double dy = this.y - that.y;
		 return Math.sqrt((dx*dx)+(dy*dy)); 
	 }
	 
	 public double energyEfficientDistance(Point that) {
		 if ((that.x - this.x == 1.0) && (that.y - this.y == 0.0)) return 0.357;
		 if ((that.x - this.x == 1.0) && (that.y - this.y == 1.0)) return 3.869;
		 if ((that.x - this.x == 0.0) && (that.y - this.y == 1.0)) return 7.089;
		 if ((that.x - this.x == -1.0) && (that.y - this.y == 1.0)) return 10.601;
		 if ((that.x - this.x == -1.0) && (that.y - this.y == 0.0)) return 13.822;
		 if ((that.x - this.x == -1.0) && (that.y - this.y == -1.0)) return 10.601;
		 if ((that.x - this.x == 0.0) && (that.y - this.y == -1.0)) return 7.089;
		 if ((that.x - this.x == 1.0) && (that.y - this.y == -1.0)) return 3.869;
		 else return 50.0;
	 }
	 
	public int compareTo(Point that) {
		if (this.x < that.x) return -1; 
        if (this.x > that.x) return +1; 
		if (this.y < that.y) return -1;
        if (this.y > that.y) return +1;
        
        return 0;
	}
	
	private static class XOrder implements Comparator<Point>{
		public int compare(Point a, Point b) {
			if(a.x < b.x) return -1;
			if(a.x > b.x) return -1;
			return 0;
		}
	}
	private static class YOrder implements Comparator<Point>{
		public int compare(Point a, Point b) {
			if(a.y < b.y) return -1;
			if(a.y > b.y) return -1;
			return 0;
		}
	}
	@Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null) return false;
        if (other.getClass() != this.getClass()) return false;
        Point that = (Point) other;
        return this.x == that.x && this.y == that.y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
    
    @Override
    public int hashCode() {
    	int hash = 7;
    	hash = 31 * hash * (int)this.x;
    	hash = 31 * hash * (int)this.y;
    	return hash;
    	
    }
    
}

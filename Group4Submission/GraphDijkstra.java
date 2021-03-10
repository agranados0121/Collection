package finalProject;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GraphDijkstra {
	private HashMap<Point, HashMap<Point, Double>> map;
	private int nodeNums;
	
	public GraphDijkstra(int nodeNums) {
		this.nodeNums = nodeNums;
		this.map = new HashMap<>(nodeNums);
	}

	public void addNode(Point node) {
		map.put(node, new HashMap<Point, Double>());
	}
	
	public void addEdge(Point source, Point destination, Double weight) {
		if(!map.containsKey(source)) addNode(source);
		if(!map.containsKey(destination)) addNode(destination);
		map.get(source).put(destination, weight);
	}
	
	public int getNodeNums() {
		return nodeNums;
	}
	
	public Set<Point> getKeys() {
		return map.keySet();
	}
	
	public void viewValues() {
		Collection<HashMap<Point, Double>> values = map.values();
		for(Map<Point, Double> maps : values) {
			Set<Point> keys = maps.keySet();
			for(Point key: keys) System.out.print(key + ": " + maps.get(key) + " ");
			System.out.println();
		}
	}
	
	public boolean hasChildren(Point node) {
		if (map.containsKey(node)) return !map.get(node).isEmpty();
		else return false;
	}
	
	public Set<Point> getChildren(Point parent){
		HashMap<Point, Double> destMap = map.get(parent);
		if(!destMap.keySet().isEmpty()) return destMap.keySet();
		else {
			Set<Point> s = Collections.emptySet();
			return s;
		}
	}
	
	public Double getEdge(Point source, Point destination) {
		HashMap<Point, Double> sourceMap = map.get(source);
		return sourceMap.get(destination);
	}
}

package finalProject;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
//Author: Pete Gillespie
//CSC595 HW Problem #2 - Dijkstra
//2-7-2020

public class Dijkstra_2 {
	public static ArrayList<Point> optimalPath = new ArrayList<Point>();
	
	public static void dijkstra(GraphDijkstra graph, Point source, Point destination) {
		dijkstraH(graph, source, destination);
	}

	//Dijkstra
	private static void dijkstraH(GraphDijkstra graph, Point source, Point destination) {
		HashMap<Point, Double> distanceTo = new HashMap<>(graph.getNodeNums());			//distance from source (A) to node
		HashMap<Point, Point> previousNode = new HashMap<>(graph.getNodeNums());			//value holds parent node
		HashSet<Point> visited = new HashSet<>();
		MinHeap minHeap = new MinHeap(graph.getNodeNums());
		
		for (Point key: graph.getKeys()) distanceTo.put(key, Double.POSITIVE_INFINITY);
		distanceTo.put(source, 0.0);
		previousNode.put(source, source);
		
		minHeap.add(source, distanceTo.get(source));
		Point node = source;
		while (!minHeap.isEmpty()) {
			node = minHeap.remove();
			visited.add(node);
			if(graph.hasChildren(node)) {
				for (Point child: graph.getChildren(node)) {
					if(!visited.contains(child)) {
						if(minHeap.contains(child)) { //check if in heap. possible shorter path exists
							Double tempWeight = distanceTo.get(node) + graph.getEdge(node, child);
							if(tempWeight < distanceTo.get(child)) {
								distanceTo.put(child, tempWeight);
								previousNode.put(child, node);
								minHeap.updateHeap(child, tempWeight);
							}
						}
						else {
							minHeap.add(child, distanceTo.get(node) + graph.getEdge(node, child));
							distanceTo.put(child, distanceTo.get(node) + graph.getEdge(node, child));
							previousNode.put(child, node);
						}
					}
				}
			}
		}
		
		System.out.println("Shortest distance AB is: " + distanceTo.get(destination));
		
		//Shortest Path
		Stack<Point> path = new Stack<>();
		path.push(destination);
		Point temp = previousNode.get(destination);
		while(!temp.equals(source)) {
			path.push(temp);
			temp = previousNode.get(temp);
		}
		path.push(source);		
		ArrayList<Point> tempPath = new ArrayList<Point>(path.size());
		System.out.print("Shortest Path is: ");
		if(distanceTo.get(destination) == Double.POSITIVE_INFINITY) System.out.println("No direct path found!");
		else while(!path.isEmpty()) {
			Point temp1 = path.pop();
			System.out.print(temp1 + " ");
			tempPath.add(new Point((temp1.getX() / 10.0), (temp1.getY() / 10.0)));			//converts back to meters and adds to list to pass for translations and displacements
		}
		for(Point p : tempPath) {
			optimalPath.add(p);
		}
		
		//optimalPath = tempPath;
	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {


		//USE THIS MAIN TO RUN THE PROJECT

		RectIntersect.main(args);
		List<PointGraph.Edge> edges = new ArrayList<PointGraph.Edge>();
		int v = (int)(RectIntersect.MAX_X*RectIntersect.MAX_Y)*RectIntersect.freeSpace.size();
		/*Iterator<Point> itr = RectIntersect.freeSpace.iterator();
		while(itr.hasNext()) {
			Point tmp1 = itr.next();
			Point tmp2 = itr.next();

			edges.add(new Edge(tmp1, tmp2, tmp1.distance(tmp2)));
		}*/
			
		//energy efficient path graph
		if(RectIntersect.pathType.equals("ee")) {
			for(int i = 0; i < RectIntersect.freeSpace.size();i++) {
				Point tmp1 = RectIntersect.freeSpace.get(i);
				for(int j = 1; j < RectIntersect.freeSpace.size(); j++) {
					Point tmp2 = RectIntersect.freeSpace.get(j);
					if(Math.abs(tmp1.getX()-tmp2.getX()) <= 1 && Math.abs(tmp1.getY()-tmp2.getY()) <=1) {
						edges.add(new PointGraph.Edge(tmp1, tmp2, tmp1.energyEfficientDistance(tmp2)));
					}
				}
			}
		}
		//shortest path graph
		else {
			for(int i = 0; i < RectIntersect.freeSpace.size();i++) {
				Point tmp1 = RectIntersect.freeSpace.get(i);
				for(int j = 1; j < RectIntersect.freeSpace.size(); j++) {
					Point tmp2 = RectIntersect.freeSpace.get(j);
					if(Math.abs(tmp1.getX()-tmp2.getX()) <= 1 && Math.abs(tmp1.getY()-tmp2.getY()) <=1) {
						edges.add(new PointGraph.Edge(tmp1, tmp2, tmp1.distance(tmp2)));
					}
				}
			}
		}
		
		FileWriter f = new FileWriter("adjacent.txt");
		for(PointGraph.Edge e: edges) {
			f.write("Source: " +e.src + " destination: "+ e.dest+ " weight of: "+e.weight + "\n");
		}
		f.close();
		PointGraph.Graph g = new PointGraph.Graph(edges, v);

		//Creates a hashmap of the adjacency list
		HashMap<Point, List<Point>> map = new HashMap<>();
		for (PointGraph.Edge e : edges){
			if (!map.containsKey(e.src)){
				map.put(e.src, new ArrayList<>());
				map.get(e.src).add(e.dest);

			}else if (map.containsKey(e.src)){
				map.get(e.src).add(e.dest);

			}
		}

		FileWriter fn = new FileWriter("adjList.txt");

		for (Point point : map.keySet()){
			fn.write("Source: " + point + " Adjacent: " + map.get(point).toString() + "\n");
		}
		
		fn.close();
		
		PointGraph.BreadthFirst breadthFirst = new PointGraph.BreadthFirst(map, RectIntersect.start);


		System.out.println(breadthFirst.pathTo(RectIntersect.end).toString());


		Integer nodeNums = map.keySet().size();
		GraphDijkstra graph = new GraphDijkstra(nodeNums);
		for (PointGraph.Edge e : edges){
			graph.addEdge(e.src, e.dest, e.weight);
		}
		if(RectIntersect.start.compareTo(RectIntersect.end)>1) {
			if(RectIntersect.toVisit.size() == 0) {
				dijkstra(graph, RectIntersect.start, RectIntersect.end);
			}
			else if(RectIntersect.toVisit.size() == 1) {
				dijkstra(graph, RectIntersect.start, RectIntersect.toVisit.get(0));
				dijkstra(graph, RectIntersect.toVisit.get(0), RectIntersect.end);
			}
			else if(RectIntersect.toVisit.size() == 2) {
				dijkstra(graph, RectIntersect.start, RectIntersect.toVisit.get(0));
				dijkstra(graph, RectIntersect.toVisit.get(0), RectIntersect.toVisit.get(1));
				dijkstra(graph, RectIntersect.toVisit.get(1), RectIntersect.end);
				
				
			}
			else if(RectIntersect.toVisit.size() == 3) {
				dijkstra(graph, RectIntersect.start, RectIntersect.toVisit.get(0));
				dijkstra(graph, RectIntersect.toVisit.get(0), RectIntersect.toVisit.get(1));
				dijkstra(graph, RectIntersect.toVisit.get(1), RectIntersect.toVisit.get(2));
				dijkstra(graph, RectIntersect.toVisit.get(2), RectIntersect.end);
			}
		}
		else {
			if(RectIntersect.toVisit.size() == 0) {
				dijkstra(graph, RectIntersect.start, RectIntersect.end);
			}
			else if(RectIntersect.toVisit.size() == 1) {
				dijkstra(graph, RectIntersect.start, RectIntersect.toVisit.get(0));
				dijkstra(graph, RectIntersect.toVisit.get(0), RectIntersect.end);
			}
			else if(RectIntersect.toVisit.size() == 2) {
				dijkstra(graph, RectIntersect.start, RectIntersect.toVisit.get(1));
				dijkstra(graph, RectIntersect.toVisit.get(1), RectIntersect.toVisit.get(0));
				dijkstra(graph, RectIntersect.toVisit.get(0), RectIntersect.end);
			}
			else if(RectIntersect.toVisit.size() == 3) {
				dijkstra(graph, RectIntersect.start, RectIntersect.toVisit.get(2));
				dijkstra(graph, RectIntersect.toVisit.get(2), RectIntersect.toVisit.get(1));
				dijkstra(graph, RectIntersect.toVisit.get(1), RectIntersect.toVisit.get(0));
				dijkstra(graph, RectIntersect.toVisit.get(0), RectIntersect.end);
			}
		}
		
		
		// *** Add code here to begin calculating translations and wheel displacements ***
		CalculateTranslations ct = new CalculateTranslations(optimalPath);
		ct.calculateTranslations();
		
		CalculateWheelDisplacements wd = new CalculateWheelDisplacements(ct.getTrajectories());
		wd.calculateDisplacements();
		wd.outputDisplacementsFile();		//writes displacements file to path specified in CalculateWheelDisplacements *** Remember to change ***

		/*
		//get path string from user
		System.out.print("Please enter the path of file (no spaces): ");					
		Scanner scIn = new Scanner(System.in);
		String filePath = scIn.nextLine(); 
		scIn.close();
				
		File textFile = new File(filePath);
		Scanner scOut = new Scanner(textFile);
						
		Integer nodeNums = Integer.parseInt(scOut.nextLine());
		GraphDijkstra graph = new GraphDijkstra(nodeNums);
		
				
		//Parse file and build Graph
		while(scOut.hasNextLine()) {														
			String line = scOut.nextLine();
			String[] fields = line.split("\\s+");
			String startNode = fields[0];
			String endNode = fields[1];
			Integer weight = Integer.parseInt(fields[2]);
			graph.addEdge(startNode, endNode, weight);		
		}   
		scOut.close();
		
		//call Dijkstra
		dijkstra(graph);
		*/

	}
	
}
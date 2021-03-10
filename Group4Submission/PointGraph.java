package finalProject;

import javax.annotation.processing.Filer;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


@SuppressWarnings("unused")
public class PointGraph {
	public static class Edge{
		Point src, dest;
		double weight;
		
		public Edge(Point source, Point dest, double weight) {
			this.src = source;
			this.dest = dest;
			this.weight = weight;
		}
	};
	
	public static class Node{
		int vertex;
		double weight;
		public Node(int vertex, double weight) {
			this.vertex = vertex;
			this.weight = weight;
		}
	};
	
	public static class Graph{
		List<List<Edge>> adjList = null;
		Graph(List<Edge> edges, int N){
			adjList = new ArrayList<>(N);

			for (int i = 0; i < N; i++) {
				adjList.add(i, new ArrayList<>());
			}

			for (Edge edge: edges) {
				adjList.get(edge.src.index).add(edge);
			}
		}
	};



	static class BreadthFirst{
		HashMap<Point, Boolean> marked;
		HashMap<Point, Point> edgeTo;
		final Point s;


		BreadthFirst(HashMap<Point, List<Point>> graph, Point s){
			marked = new HashMap<>();
			for (Point p : graph.keySet()){
				marked.put(p, false);
			}

			edgeTo = new HashMap<>();
			for(Point p : graph.keySet()){
				edgeTo.put(p, p);
			}

			this.s = s;

			bfs(graph, s);
		}


		void bfs(HashMap<Point, List<Point>> graph, Point s){
			Queue<Point> queue = new LinkedList<>();
			marked.put(s, true);
			queue.add(s);

			while(!queue.isEmpty()){
				Point v = queue.remove();
				for(Point w : graph.get(v)){
					if (marked.get(w) == false){
						edgeTo.put(w, v);
						marked.put(w,true);
						queue.add(w);
					}

				}


			}

		}


		public boolean hasPathTo(Point v){
			return marked.get(v);
		}


		public Iterable<Point> pathTo(Point v){
			if(!hasPathTo(v)) return null;

			Stack<Point> path = new Stack<>();
			for (Point x = v; x!=s; x = edgeTo.get(x)){
				path.push(x);
			}
			path.push(s);
			return path;


		}







	}


	public static void main(String[] args) throws IOException, InstantiationException, IllegalAccessException {
		RectIntersect.main(args);
		List<Edge> edges = new ArrayList<Edge>();
		int v = (int)(RectIntersect.MAX_X*RectIntersect.MAX_Y)*RectIntersect.freeSpace.size();
		
		for(int i = 0; i < RectIntersect.freeSpace.size();i++) {
			Point tmp1 = RectIntersect.freeSpace.get(i);
			for(int j = 1; j < RectIntersect.freeSpace.size(); j++) {
				Point tmp2 = RectIntersect.freeSpace.get(j);
					if(Math.abs(tmp1.getX()-tmp2.getX()) <= 1 && Math.abs(tmp1.getY()-tmp2.getY()) <=1) {
						edges.add(new Edge(tmp1, tmp2, tmp1.distance(tmp2)));
					}
				}
			
		}
		FileWriter f = new FileWriter("adjacent.txt");
		for(Edge e: edges) {
			f.write("Source: " +e.src + " destination: "+ e.dest+ " weight of: "+e.weight + "\n");
		}
		f.close();
		Graph g = new Graph(edges, v);

		//Creates a hashmap of the adjacency list
		HashMap<Point, List<Point>> map = new HashMap<>();
		for (Edge e : edges){
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
		BreadthFirst breadthFirst = new BreadthFirst(map, RectIntersect.start);


		System.out.println(breadthFirst.pathTo(RectIntersect.end).toString());









	}
}



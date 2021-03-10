package finalProject;

import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Set;

public class MinHeap {
	HashMap<Point, Double> tempDistanceTo;
	PriorityQueue<Double> minPQ;
	
	public MinHeap(Integer nodeNums) {
		this.tempDistanceTo = new HashMap<Point, Double>(nodeNums);
		this.minPQ = new PriorityQueue<Double>();
	}
	
	//adds node and weight from source to node from distTo into minHeap and hashMap
	public void add(Point node, Double weight) {
		tempDistanceTo.put(node, weight);
		minPQ.add(weight);
	}
	
	//removes minHeap, checks hashMap for corresponding node, removes from hashMap and returns node removed
	public Point remove() {
		Double temp = minPQ.remove();
		Point nodeRemoved = null;
		for (Point key: tempDistanceTo.keySet()) {
			if (tempDistanceTo.get(key).equals(temp)) {
				nodeRemoved = key;
				tempDistanceTo.remove(key);
				return nodeRemoved;
			}
		}
		return nodeRemoved;
	}
	
	//updates minHeap. Updates distance in hashMap, clears minHeap and copies updated hashMap values into minHeap
	public void updateHeap(Point node, Double weight) {
		tempDistanceTo.put(node, weight); //update node in hashMap
		minPQ.clear(); //empty priority queue
		for (Point key: tempDistanceTo.keySet()) minPQ.add(tempDistanceTo.get(key)); //copy hashMap back to minHeap
	}
	
	public boolean isEmpty() {
		return minPQ.isEmpty();
	}
	
	@SuppressWarnings("unlikely-arg-type")
	public Double getHeapWeight(String node) {
		return tempDistanceTo.get(node);
	}
	
	public boolean contains(Point node) {
		return tempDistanceTo.containsKey(node);
	}
	
	public Set<Point> getHashMapKeys(){
		return tempDistanceTo.keySet();
	}
	
	public void printHeap() {
		Object[] iter = minPQ.toArray();
		System.out.print("Heap: ");
		for (Object integ: iter) System.out.print(integ + " ");
		System.out.println();
	}
}

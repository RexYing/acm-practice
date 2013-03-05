package pacnorthwest.acm10;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

/**
 * K-Dimensional Tree for all mines. Check the nearest neighbor for each zombie.
 * 
 * @author Rex
 * 
 * @date 03/03/2013
 * 
 */
public class ZombieBlast_K {

	private ArrayList<Point2D> zombies;
	private ArrayList<Point2D> mines;

	public void solve() {
		Scanner scanner = new Scanner(System.in);
		int n = scanner.nextInt();
		for (int i = 0; i < n; i++) {
			int width = scanner.nextInt();
			int height = scanner.nextInt();
			// maximum of all minimum distances between a zombie and other mines
			double distance = 0;
			scanner.nextLine();
			zombies = new ArrayList<Point2D>();
			mines = new ArrayList<Point2D>();
			// the following three lines are for the convenience of checking
			// mines
			char[] defaultChars = new char[width];
			Arrays.fill(defaultChars, 'M');
			String previousLine = new String(defaultChars);
			String currentLine = scanner.nextLine();
			String nextLine;
			if (height == 1) // only one row...
				nextLine = new String(defaultChars);
			else
				nextLine = scanner.nextLine();
			// nextLine is actually the second line
			for (int j = 0; j < height; j++) {
				// String currentLine = scanner.nextLine();
				for (int k = 0; k < width; k++) {
					if (currentLine.charAt(k) == 'Z')
						zombies.add(new Point(j, k));
					else if (currentLine.charAt(k) == 'M') {
						// check if this mine is surrounded by other mines
						// if so, ignore this mine
						if (k == 0) {
							if (width == 1) { // only one column
								if ((previousLine.charAt(k) == 'M') && (nextLine.charAt(k) == 'M'))
									continue;
							} else {
								if ((previousLine.charAt(k) == 'M') && (nextLine.charAt(k) == 'M')
										&& (currentLine.charAt(k + 1) == 'M'))
									continue;
								if ((height != 1) && (previousLine.charAt(k + 1) == 'M')
										&& (nextLine.charAt(k + 1) == 'M'))
									continue;
							}
						} else if (k == width - 1) {
							if ((previousLine.charAt(k) == 'M') && (nextLine.charAt(k) == 'M')
									&& (currentLine.charAt(k - 1) == 'M'))
								continue;
							if ((width != 1) && (height != 1) && (previousLine.charAt(k - 1) == 'M')
									&& (nextLine.charAt(k - 1) == 'M'))
								continue;
						} else {
							if ((previousLine.charAt(k) == 'M') && (nextLine.charAt(k) == 'M')
									&& (currentLine.charAt(k - 1) == 'M')
									&& (currentLine.charAt(k + 1) == 'M'))
								continue;
							if ((width != 1) && (height != 1) && (previousLine.charAt(k - 1) == 'M')
									&& (nextLine.charAt(k - 1) == 'M') && (previousLine.charAt(k + 1) == 'M')
									&& (nextLine.charAt(k + 1) == 'M'))
								continue;
						}
						mines.add(new Point(j, k));
					}
				}
				previousLine = currentLine;
				currentLine = nextLine;
				if (j >= height - 2) {
					// the "line" after the last line
					nextLine = new String(defaultChars);
				} else
					nextLine = scanner.nextLine();
			}

			PlaneTree planeTree = new PlaneTree(mines);
			//System.out.println(planeTree.myPoints.size());
			//planeTree.printInOrderTraversal();
			for (Point2D zombiePoint : zombies) {
				distance = Math.max(distance, findNearestNeighbor(planeTree, zombiePoint, distance));
			}
			System.out.println(distance);
		}
	}

	private double findNearestNeighbor(PlaneTree planeTree, Point2D requestPoint, double currentMaxDist) {
		Stack<Node> searchPath = planeTree.generateSearchPath(requestPoint);
		double minDistance = searchPath.peek().point.distance(requestPoint);
		int index = searchPath.size() - 1; // for determining axis
		ArrayList<Node> nodesToCheck = new ArrayList<Node>();
		// backtrack
		while (!searchPath.isEmpty()) {
			Node currentNearNode = searchPath.pop();
			Point2D currentNearPoint = currentNearNode.point;
			double distanceToSeparatingLine = planeTree.valueAccordingToAxis(requestPoint, index
					% PlaneTree.NUM_DIMENSION)
					- planeTree.valueAccordingToAxis(currentNearPoint, index % PlaneTree.NUM_DIMENSION);
			// the circle with radius minDistance might cross the line
			if (Math.abs(distanceToSeparatingLine) < minDistance) {
				// check distance with this current near point
				minDistance = Math.min(minDistance, currentNearPoint.distance(requestPoint));
				// need to enter the OTHER sub-space
				if (!currentNearNode.flag) {
					// the requestPoint is on the left, go to right
					if (currentNearNode.right != null) {
						nodesToCheck.add(currentNearNode.right);
					}
				} else {
					if (currentNearNode.left != null) {
						nodesToCheck.add(currentNearNode.left);
					}
				}
			}
			index--; // NEVER FORGET TO DECREMENT INDEX!!!!!!!
			// if the min distance is never gonna be the biggest among all min
			// distance, just ignore it
			if (minDistance < currentMaxDist)
			   return 0;
		}
		// check these additional nodes
		for (Node node : nodesToCheck) {
			double distance = planeTree.findMinDistance(requestPoint, node);
			minDistance = Math.min(minDistance, distance);
			// same thing as line 80
			if (minDistance < currentMaxDist)
			   return 0;
		}
		return minDistance;
	}

	public static void main(String[] args) {
		ZombieBlast_K zb = new ZombieBlast_K();
		zb.solve();
	}
}

/**
 * a 2 dimensional tree
 * 
 * @author Rex
 * 
 */
class PlaneTree {

	public static final int NUM_DIMENSION = 2;
	private Node myRoot;
	public ArrayList<Point2D> myPoints;

	public PlaneTree(ArrayList<Point2D> points) {
		myPoints = points;
		myRoot = buildTree(0, myPoints.size(), 0);
	}

	public double findMinDistance(Point2D requestPoint, Node node) {
		double distance = node.point.distance(requestPoint);
		if (node.left != null)
			distance = Math.min(distance, findMinDistance(requestPoint, node.left));
		if (node.right != null)
			distance = Math.min(distance, findMinDistance(requestPoint, node.right));
		return distance;
	}

	public Node getRoot() {
		return myRoot;
	}

	/**
	 * build 2D-tree
	 * 
	 * @param begin
	 *            begin index
	 * @param end
	 *            end index (the index of the last element + 1) c++?!
	 * @param depth
	 *            depth of the tree (also used to determine whether partition of
	 *            points is horizontal or vertical)
	 * @return the root of the tree / sub-tree (null if no element in myPoints
	 *         with the range of index from begin to end)
	 */
	public Node buildTree(int begin, int end, int depth) {
		if (end - begin == 0) // no elements
			return null;
		else if (end - begin == 1) // one element
			return new Node(myPoints.get(begin));
		int medianIndex = (begin + end - 1) >> 1;
		if (depth > 0) // Assumption: the initial input is already sorted...
			customizedQuickSort(depth % NUM_DIMENSION, begin, end - 1);
		Node newNode = new Node(myPoints.get(medianIndex));
		newNode.left = buildTree(begin, medianIndex, depth + 1);
		newNode.right = buildTree(medianIndex + 1, end, depth + 1);
		return newNode;
	}

	/*
	 * axis could be either x or y axis in this 2D case. sort from index first
	 * to index last. if axis is 0, sort according to x-coordinates, otherwise
	 * sort according to y-coordinates.
	 */
	private void customizedQuickSort(int axis, int first, int last) {
		int i = first;
		int j = last;
		Point2D temp = myPoints.get(first);
		while (i < j) {
			while (i < j && valueAccordingToAxis(myPoints.get(j), axis) >= valueAccordingToAxis(temp, axis))
				j--;
			myPoints.set(i, myPoints.get(j));
			while (i < j && valueAccordingToAxis(myPoints.get(i), axis) <= valueAccordingToAxis(temp, axis))
				i++;
			myPoints.set(j, myPoints.get(i));
		}
		myPoints.set(i, temp);
		if (first < i - 1)
			customizedQuickSort(axis, first, i - 1);
		if (last > i + 1)
			customizedQuickSort(axis, i + 1, last);
	}

	public double valueAccordingToAxis(Point2D point, int axis) {
		if (axis == 0) // x-axis
			return point.getX();
		else if (axis == 1)
			// y-axis
			return point.getY();
		else { // something is wrong!! only 2 dimension
			System.err.println("invalid axis argument!!");
			return 0;
		}
	}

	/**
	 * print the entire 2D tree in in-order to stdout
	 */
	public void printInOrderTraversal() {
		printInOrderTraversal(myRoot, 0);
	}

	private void printInOrderTraversal(Node root, int depth) {
		if (root == null)
			return;
		printInOrderTraversal(root.left, depth + 1);
		System.out.printf("depth %d; point: (%.2f, %.2f)\n", depth, root.point.getX(), root.point.getY());
		printInOrderTraversal(root.right, depth + 1);
	}

	public Stack<Node> generateSearchPath(Point2D point) {
		Stack<Node> searchPath = new Stack<Node>();
		Node currentNode = myRoot;
		int axis = 0;
		while (currentNode != null) {
			searchPath.push(currentNode);
			if (valueAccordingToAxis(point, axis) <= valueAccordingToAxis(currentNode.point, axis)) {
				currentNode.flag = false;
				currentNode = currentNode.left;
			} else {
				currentNode.flag = true;
				currentNode = currentNode.right;
			}
			axis = (axis + 1) % NUM_DIMENSION;
		}
		return searchPath;
	}
}

/**
 * Represents a node of the tree. constructed from a Point2D
 * 
 * @author Rex
 * 
 */
class Node {
	Point2D point;
	Node left, right;
	int num;
	// flag: used for searchPath:
	// false - requestPoint less than this point in the appropriate axis;
	// true - right
	boolean flag;

	Node(Point2D point) {
		this.point = point;
		this.num = 1;
		this.flag = false;
	}
}

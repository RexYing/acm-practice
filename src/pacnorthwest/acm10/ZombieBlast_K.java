package pacnorthwest.acm10;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;

/**
 * K-Dimensional Tree for all mines. Check the nearest neighbor for each zombie.
 * 
 * @author Rex
 * 
 * @date 03/03/2013
 * 
 */
public class ZombieBlast_K {

	public static void main(String[] args) {

	}
}

/**
 * a 2 dimensional tree
 * 
 * @author Rex
 * 
 */
class PlaneTree {

	private static final int NUM_DIMENSION = 2;
	private Node myRoot;
	private Node myCurrentNode;
	private ArrayList<Point2D> myPoints;

	public PlaneTree() {
		myPoints = new ArrayList<Point2D>();
	}

	/**
	 * inner class that represents a node of the tree. constructed from a
	 * Point2D
	 * 
	 * @author Rex
	 * 
	 */
	class Node {
		Point2D point;
		Node left, right;
		int num;
		boolean flag;

		Node(Point2D point) {
			this.point = point;
			this.num = 1;
			this.flag = false;
		}
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
		customizedQuickSort(depth, begin, end - 1);
		Node newNode = new Node(myPoints.get(medianIndex));
		newNode.left = buildTree(begin, medianIndex, (depth + 1)
				% NUM_DIMENSION);
		newNode.right = buildTree(medianIndex + 1, end, (depth + 1)
				% NUM_DIMENSION);
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
			while (i < j
					&& valueAccordingToAxis(myPoints.get(j), axis) >= valueAccordingToAxis(
							temp, axis))
				j--;
			myPoints.set(i, myPoints.get(j));
			while (i < j
					&& valueAccordingToAxis(myPoints.get(i), axis) <= valueAccordingToAxis(
							temp, axis))
				i++;
			myPoints.set(j, myPoints.get(i));
		}
		myPoints.set(i, temp);
		if (first < i - 1)
			customizedQuickSort(axis, first, i - 1);
		if (last > i + 1)
			customizedQuickSort(axis, i + 1, last);
	}

	private double valueAccordingToAxis(Point2D point, int axis) {
		if (axis == 0) // x-axis
			return point.getX();
		else
			// y-axis
			return point.getY();
	}
}

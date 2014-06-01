package utilities.geom;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ConvexHull {

	/**
	 * Convex hull algorithm using Graham Scan
	 * 
	 * @param points
	 */
	public List<Point2D> convexHullGraham(Point2D[] points) {

		List<Point2D> convexHull = new ArrayList<Point2D>();
		// edge cases
		if (points.length <= 3) {
			for (int i = 0; i < points.length; i++)
				convexHull.add(points[i]);
			return convexHull;
		}

		Point2D init = new Point2D.Double(0, Integer.MAX_VALUE);
		// Find point with smallest y-coords. If multiple pts have the same
		// lowest y-coords, pick the one with smallest x-coords
		int refIndex = -1;
		for (int i = 0; i < points.length; i++) {
			if ((points[i].getY() < init.getY())
					|| ((points[i].getY() == init.getY()) && (points[i].getX() < init.getX()))) {
				init = points[i];
				refIndex = i;
			}
		}
		points[refIndex] = points[0];
		points[0] = init;
		final Point2D ref = new Point2D.Double(init.getX(), init.getY());
		// if the points array is not to be modified, copy to new array, or use
		// sth like TreeSet
		Arrays.sort(points, 1, points.length, new Comparator<Point2D>() {
			@Override
			public int compare(Point2D pt1, Point2D pt2) {
				double deg1 = Math.atan2(pt1.getY() - ref.getY(), pt1.getX() - ref.getX());
				double deg2 = Math.atan2(pt2.getY() - ref.getY(), pt2.getX() - ref.getX());
				if (Double.compare(deg1, deg2) == 0)
					return Double.compare(ref.distance(pt1), ref.distance(pt2));
				else
					return Double.compare(deg1, deg2);
			}
		});

		convexHull.add(init);
		for (int i = 1; i < points.length; i++) {
			if (convexHull.size() == 1)
				convexHull.add(points[i]);
			//Point2D 
		}
		return convexHull;
	}

}

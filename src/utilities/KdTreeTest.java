package utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Stack;

/**
 * first tried in pacific northwest 2010
 * 
 * @author a user in CSDN
 *
 */
class KDTree {
	KPoint myPoints[]; // a list of all k-dimensional points in the tree
	int K; // dimension
	int n; // number of nodes
	PriorityQueue<RNode> pq;
	Node root, cur;
	PrintWriter out;
	KPoint aim;

	public KDTree(int K, int n, PrintWriter out) {
		this.out = out;
		this.n = n;
		myPoints = new KPoint[n];
		for (int i = 0; i < n; i++) {
			myPoints[i] = new KPoint(K);
		}
		this.K = K;
		pq = new PriorityQueue<RNode>(10, new CompareRNode());
	}

	class KPoint {
		int coords[]; // coordinates (K-dimensional)
		int lx[], mx[]; //least, most K-dimensional point
		int k;

		/**
		 * 
		 * @param M dimension
		 */
		public KPoint(int M) {
			this.coords = new int[M];
			this.lx = new int[M];
			this.mx = new int[M];
			Arrays.fill(lx, -1 << 30);
			Arrays.fill(mx, 1 << 30);
			k = M;
		}

		double dis(KPoint t) {
			double ans = 0;
			for (int i = 0; i < k; i++)
				ans += (coords[i] - t.coords[i]) * (coords[i] - t.coords[i]);
			return Math.sqrt(ans);
		}
	}

	class Node {
		KPoint myKPoints;
		Node left, right;
		int num;
		boolean flag;

		Node(KPoint p) {
			this.myKPoints = p;
			this.num = 1;
			this.flag = false;
		}
	}

	class RNode {
		Node v;
		double r; //distance?

		public RNode(Node v, double r) {
			this.v = v;
			this.r = r;
		}
	}

	/**
	 * from biggest to smallest......
	 *
	 */
	class CompareRNode implements Comparator<RNode> {
		@Override
		public int compare(RNode o1, RNode o2) {
			if (o1.r > o2.r) {
				return -1;
			}
			return 1;
		}

	}

	/**
	 * build kd-tree
	 */
	public Node buildTree(int f, int t, int depth) {
		if (t - f == 0)
			return null;
		else if (t - f == 1)
			return new Node(myPoints[f]);
		int mid = (f + t - 1) >> 1;
		QuickSort(depth, f, t - 1);
		Node v = new Node(myPoints[mid]);
		v.left = buildTree(f, mid, (depth + 1) % K);
		v.right = buildTree(mid + 1, t, (depth + 1) % K);
		return v;
	}

	void QuickSort(int ii, int first, int end) {
		int i = first, j = end;
		KPoint tmp = myPoints[first];
		while (i < j) {
			while (i < j && myPoints[j].coords[ii] >= tmp.coords[ii])
				j--;
			myPoints[i] = myPoints[j];
			while (i < j && myPoints[i].coords[ii] <= tmp.coords[ii])
				i++;
			myPoints[j] = myPoints[i];
		}
		myPoints[i] = tmp;
		if (first < i - 1)
			QuickSort(ii, first, i - 1);
		if (end > i + 1)
			QuickSort(ii, i + 1, end);
	}

	int buildSqr(Node v, int depth) {
		if (v == null)
			return 0;
		if (v.left != null) {
			for (int i = 0; i < K; i++) {
				v.left.myKPoints.mx[i] = v.myKPoints.mx[i];
				v.left.myKPoints.lx[i] = v.myKPoints.lx[i];
			}
			v.left.myKPoints.mx[depth] = v.myKPoints.coords[depth];
		}
		if (v.right != null) {
			for (int i = 0; i < K; i++) {
				v.right.myKPoints.mx[i] = v.myKPoints.mx[i];
				v.right.myKPoints.lx[i] = v.myKPoints.lx[i];
			}
			v.right.myKPoints.lx[depth] = v.myKPoints.coords[depth];
		}
		int na = buildSqr(v.left, (depth + 1) % K);
		int nb = buildSqr(v.right, (depth + 1) % K);
		v.num = na + nb + 1;
		return na + nb + 1;
	}

	/**
	 * 
	 * @param searchedPoint aim
	 * @param k Kth nearest neighbor
	 * @return
	 */
	double searchKR(KPoint searchedPoint, int k) {
		Node v = SearchKNode(root, searchedPoint, k, 0);
		if (v == null)
			return -1.0;
		SearchMinr(v, searchedPoint);
		while (pq.size() > k) {
			pq.poll();
		}
		if (pq.size() == 0)
			return 0.0;
		else
			return pq.peek().r;
	}

	/**
	 * search for the KPoint in the tree
	 * @param v root of tree / sub-tree
	 * @param searchedPoint KPoint to be searched for
	 * @param k Kth nearest neighbor
	 * @param dp depth?
	 * @return
	 */
	public Node SearchKNode(Node v, KPoint searchedPoint, int k, int dp) {
		if (v == null)
			return null;
		if (v.num < k)
			return null;
		if ((v.left == null || v.left.num < k)
				&& (v.right == null || v.right.num < k)) {
			v.flag = true;
			cur = v;
			return v;
		}
		if (searchedPoint.coords[dp] < v.myKPoints.coords[dp] && v.left != null && v.left.num >= k) {
			return SearchKNode(v.left, searchedPoint, k, (dp + 1) % K);
		} else if (searchedPoint.coords[dp] >= v.myKPoints.coords[dp] && v.right != null && v.right.num >= k) {
			return SearchKNode(v.right, searchedPoint, k, (dp + 1) % K);
		} else {
			v.flag = true;
			cur = v;
			return v;
		}
	}

	void SearchMinr(Node v, KPoint t) {
		if (v == null)
			return;
		RNode tmp = new RNode(v, v.myKPoints.dis(t));
		pq.add(tmp);
		SearchMinr(v.left, t);
		SearchMinr(v.right, t);
	}

	double r, eps = 1e-8;

	/**
	 * Kth nearest neighbor
	 * which points are closest to "aim"?
	 * @param k
	 */
	void KNN(int k) {
		while (!pq.isEmpty())
			pq.poll();
		r = searchKR(aim, k);
		KFind(k, aim, root);
		cur.flag = false;
		out.println("the closest " + k + " points are:");
		Stack<Node> sta = new Stack<Node>();
		while (!sta.empty())
			sta.pop();
		while (!pq.isEmpty())
			sta.push(pq.poll().v);
		while (!sta.empty()) {
			Node v = sta.pop();
			for (int i = 0; i < K - 1; i++) {
				out.print(v.myKPoints.coords[i] + " ");
			}
			out.println(v.myKPoints.coords[K - 1]);
		}

	}

	void KFind(int k, KPoint t, Node v) {
		if (v.flag)
			return;
		double dd = v.myKPoints.dis(t);
		if (dd + eps < r) {
			RNode tmp = new RNode(v, dd);
			tmp.r = dd;
			tmp.v = v;
			pq.add(tmp);
			pq.poll();
			r = pq.peek().r;
		}
		if (v.left != null) {
			if (check(v.left, t, r)) {
				KFind(k, t, v.left);
			}
		}
		if (v.right != null) {
			if (check(v.right, t, r)) {
				KFind(k, t, v.right);
			}
		}
	}

	boolean check(Node v, KPoint t, double r) {
		KPoint now = new KPoint(K);
		for (int i = 0; i < K; i++) {
			if (v.myKPoints.lx[i] <= t.coords[i] && t.coords[i] <= v.myKPoints.mx[i]) {
				now.coords[i] = t.coords[i];
			} else if (t.coords[i] > v.myKPoints.mx[i]) {
				now.coords[i] = v.myKPoints.mx[i];
			} else
				now.coords[i] = v.myKPoints.lx[i];
		}
		now.k = K;
		if (now.dis(t) < eps + r)
			return true;
		return false;
	}

	public void work() {
		root = buildTree(0, n, 0);
		buildSqr(root, 0);
		aim = new KPoint(K);
	}

}

class KdTreeTest {

	public static void main(String[] args) throws IOException {
		BufferedReader f = new BufferedReader(new FileReader("src/utilities/kdtree.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(
				"src/utilities/kdtree.out")));
		Scanner in = new Scanner(f);
		// Scanner skp=new Scanner(System.in);
		while (in.hasNext()) {
			int n = in.nextInt(), k = in.nextInt();
			KDTree kdTree = new KDTree(k, n, out);
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < k; j++) {
					kdTree.myPoints[i].coords[j] = in.nextInt();
				}
			}
			// initialize
			kdTree.work();
			// output
			int cin = in.nextInt();
			for (int ci = 0; ci < cin; ci++) {
				for (int i = 0; i < k; i++) {
					kdTree.aim.coords[i] = in.nextInt();
				}
				kdTree.KNN(in.nextInt());
				// skp.nextLine();
			}
		}
		out.close();

		/*
		 * 15 2 1 3 2 7 3 5 4 2 5 3 5 7 6 6 7 3 8 7 11 7 10 4 9 2 11 3 8 1 10 1
		 * 1 1 3 10
		 */
	}
}

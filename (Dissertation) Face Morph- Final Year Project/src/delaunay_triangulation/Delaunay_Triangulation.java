package delaunay_triangulation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.Vector;

/**
 * 
 * This class represents a Delaunay Triangulation. The class was written for a
 * large scale triangulation (1000 - 200,000 vertices). The application main use is 3D surface (terrain) presentation. 
 * <br>
 * The class main properties are the following:<br>
 * - fast point location. (O(n^0.5)), practical runtime is often very fast. <br>
 * - handles degenerate cases and none general position input (ignores duplicate points). <br>
 * - save & load from\to text file in TSIN format. <br>
 * - 3D support: including z value approximation. <br>
 * - standard java (1.5 generic) iterators for the vertices and triangles. <br>
 * - smart iterator to only the updated triangles - for terrain simplification <br>
 * <br>
 *  
 * Testing (done in early 2005): Platform java 1.5.02 windows XP (SP2), AMD laptop 1.6G sempron CPU
 * 512MB RAM. Constructing a triangulation of 100,000 vertices takes ~ 10
 * seconds. point location of 100,000 points on a triangulation of 100,000
 * vertices takes ~ 5 seconds.
 * 
 * Note: constructing a triangulation with 200,000 vertices and more requires
 * extending java heap size (otherwise an exception will be thrown).<br>
 * 
 * Bugs: if U find a bug or U have an idea as for how to improve the code,
 * please send me an email to: benmo@ariel.ac.il
 * 
 * @author Boaz Ben Moshe 5/11/05 <br>
 * The project uses some ideas presented in the VoroGuide project, written by Klasse f?r Kreise (1996-1997), 
 * For the original applet see: http://www.pi6.fernuni-hagen.de/GeomLab/VoroGlide/ . <br>
 */

public class Delaunay_Triangulation {

	// the first and last points (used only for first step construction)
	private Point_dt firstP;
	private Point_dt lastP;

	// for degenerate case!
	private boolean allCollinear;

	// the first and last triangles (used only for first step construction)
	private Triangle_dt firstT, lastT, currT;

	// the triangle the fond (search start from
	private Triangle_dt startTriangle;

	// the triangle the convex hull starts from
	public Triangle_dt startTriangleHull;

	private int nPoints = 0; // numbr of points
	// additional data 4/8/05 used by the iterators
	private Set<Point_dt> _vertices;
	private Vector<Triangle_dt> _triangles;

	private int _modCount = 0, _modCount2 = 0;

	// the Bounding Box, {{x0,y0,z0} , {x1,y1,z1}}
	private Point_dt _bb_min, _bb_max;

	/**
	 * creates an empty Delaunay Triangulation.
	 */
	public Delaunay_Triangulation() {
		this(new Point_dt[] {});
	}

	/**
	 * creates a Delaunay Triangulation from all the points. Note: duplicated
	 * points are ignored.
	 */
	public Delaunay_Triangulation(Point_dt[] ps) {
		_modCount = 0;
		_modCount2 = 0;
		_bb_min = null;
		_bb_max = null;
		this._vertices = new TreeSet<Point_dt>(Point_dt.getComparator());
		_triangles = new Vector<Triangle_dt>();
		allCollinear = true;
		for (int i = 0; ps != null && i < ps.length && ps[i] != null; i++) {
			this.insertPoint(ps[i]);
		}
	}

	/**
	 * creates a Delaunay Triangulation from all the points in the suggested
	 * tsin file or from a smf file (off like). if the file name is .smf - read
	 * it as an smf file as try to read it as .tsin <br>
	 * Note: duplicated points are ignored! <br>
	 * SMF file has an OFF like format (a face (f) is presented by the indexes
	 * of its points - starting from 1 - not from 0): <br>
	 * begin <br>
	 * v x1 y1 z1 <br>
	 * ... <br>
	 * v xn yn zn <br>
	 * f i11 i12 i13 <br>
	 * ... <br>
	 * f im1 im2 im3 <br>
	 * end <br>
	 * <br>
	 * The tsin text file has the following (very simple) format <br>
	 * vertices# (n) <br>
	 * x1 y1 z1 <br>
	 * ... <br>
	 * xn yn zn <br>
	 * 
	 * 
	 */
	public Delaunay_Triangulation(String file) throws Exception {
		this(read_file(file));
	}

	/**
	 * the number of (different) vertices in this triangulation.
	 * 
	 * @return the number of vertices in the triangulation (duplicates are
	 *         ignore - set size).
	 */
	public int size() {
		if (_vertices == null)
		{
			return 0;
		}
		return _vertices.size();
	}

	/**
	 * @return the number of triangles in the triangulation. <br />
	 * Note: includes infinife faces!!.
	 */
	public int trianglesSize() {
		this.initTriangles();
		return _triangles.size();
	}

	/**
	 * returns the changes counter for this triangulation
	 */
	public int getModeCounter() {
		return this._modCount;
	}

	/**
	 * insert the point to this Delaunay Triangulation. Note: if p is null or
	 * already exist in this triangulation p is ignored.
	 * 
	 * @param p
	 *            new vertex to be inserted the triangulation.
	 */
	public void insertPoint(Point_dt p) {
		if (this._vertices.contains(p))
			return;
		_modCount++;
		updateBoundingBox(p);
		this._vertices.add(p);
		Triangle_dt t = insertPointSimple(p);
		if (t == null) // 
			return;
		Triangle_dt tt = t;
		currT = t; // recall the last point for - fast (last) update iterator.
		do {
			flip(tt, _modCount);
			tt = tt.canext;
		} while (tt != t && !tt.halfplane);
	}

	/**
	 * returns an iterator object involved in the last update. 
	 * @return iterator to all triangles involved in the last update of the
	 *         triangulation NOTE: works ONLY if the are triangles (it there is
	 *         only a half plane - returns an empty iterator
	 */
	public Iterator<Triangle_dt> getLastUpdatedTriangles() {
		Vector<Triangle_dt> tmp = new Vector<Triangle_dt>();
		if (this.trianglesSize() > 1) {
			Triangle_dt t = currT;
			allTriangles(t, tmp, this._modCount);
		}
		return tmp.iterator();
	}

	private void allTriangles(Triangle_dt curr, Vector<Triangle_dt> front, int mc) {
		if (curr != null && curr._mc == mc && !front.contains(curr)) {
			front.add(curr);
			allTriangles(curr.abnext, front, mc);
			allTriangles(curr.bcnext, front, mc);
			allTriangles(curr.canext, front, mc);
		}
	}

	private Triangle_dt insertPointSimple(Point_dt p) {
		nPoints++;
		if (!allCollinear) {
			Triangle_dt t = find(startTriangle, p);
			if (t.halfplane)
				startTriangle = extendOutside(t, p);
			else
				startTriangle = extendInside(t, p);
			return startTriangle;
		}

		if (nPoints == 1) {
			firstP = p;
			return null;
		}

		if (nPoints == 2) {
			startTriangulation(firstP, p);
			return null;
		}

		switch (p.pointLineTest(firstP, lastP)) {
		case Point_dt.LEFT: 
			startTriangle = extendOutside(firstT.abnext, p);
			allCollinear = false;
			break;
		case Point_dt.RIGHT:
			startTriangle = extendOutside(firstT, p);
			allCollinear = false;
			break;
		case Point_dt.ONSEGMENT: 
			insertCollinear(p, Point_dt.ONSEGMENT);
			break;
		case Point_dt.INFRONTOFA:
			insertCollinear(p, Point_dt.INFRONTOFA);
			break;
		case Point_dt.BEHINDB:
			insertCollinear(p, Point_dt.BEHINDB);
			break;
		}
		return null;
	}

	private void insertCollinear(Point_dt p, int res) {
		Triangle_dt t, tp, u;

		switch (res) {
		case Point_dt.INFRONTOFA:
			t = new Triangle_dt(firstP, p);
			tp = new Triangle_dt(p, firstP);
			t.abnext = tp;
			tp.abnext = t;
			t.bcnext = tp;
			tp.canext = t;
			t.canext = firstT;
			firstT.bcnext = t;
			tp.bcnext = firstT.abnext;
			firstT.abnext.canext = tp;
			firstT = t;
			firstP = p;
			break;
		case Point_dt.BEHINDB:
			t = new Triangle_dt(p, lastP);
			tp = new Triangle_dt(lastP, p);
			t.abnext = tp;
			tp.abnext = t;
			t.bcnext = lastT;
			lastT.canext = t;
			t.canext = tp;
			tp.bcnext = t;
			tp.canext = lastT.abnext;
			lastT.abnext.bcnext = tp;
			lastT = t;
			lastP = p;
			break;
		case Point_dt.ONSEGMENT: 
			u = firstT;
			while (p.isGreater(u.a))
				u = u.canext;
			t = new Triangle_dt(p, u.b);
			tp = new Triangle_dt(u.b, p);
			u.b = p;
			u.abnext.a = p;
			t.abnext = tp;
			tp.abnext = t;
			t.bcnext = u.bcnext;
			u.bcnext.canext = t;
			t.canext = u;
			u.bcnext = t;
			tp.canext = u.abnext.canext;
			u.abnext.canext.bcnext = tp;
			tp.bcnext = u.abnext;
			u.abnext.canext = tp;
			if (firstT == u) {
				firstT = t;
			}
			break;
		}
	}

	private void startTriangulation(Point_dt p1, Point_dt p2) {
		Point_dt ps, pb;
		if (p1.isLess(p2)) {
			ps = p1;
			pb = p2;
		} else {
			ps = p2;
			pb = p1;
		}
		firstT = new Triangle_dt(pb, ps);
		lastT = firstT;
		Triangle_dt t = new Triangle_dt(ps, pb);
		firstT.abnext = t;
		t.abnext = firstT;
		firstT.bcnext = t;
		t.canext = firstT;
		firstT.canext = t;
		t.bcnext = firstT;
		firstP = firstT.b;
		lastP = lastT.a;
		startTriangleHull = firstT;
	}

	private Triangle_dt extendInside(Triangle_dt t, Point_dt p) {

		Triangle_dt h1, h2;
		h1 = treatDegeneracyInside(t, p);
		if (h1 != null)
			return h1;

		h1 = new Triangle_dt(t.c, t.a, p);
		h2 = new Triangle_dt(t.b, t.c, p);
		t.c = p;
		t.circumcircle();
		h1.abnext = t.canext;
		h1.bcnext = t;
		h1.canext = h2;
		h2.abnext = t.bcnext;
		h2.bcnext = h1;
		h2.canext = t;
		h1.abnext.switchneighbors(t, h1);
		h2.abnext.switchneighbors(t, h2);
		t.bcnext = h2;
		t.canext = h1;
		return t;
	}

	private Triangle_dt treatDegeneracyInside(Triangle_dt t, Point_dt p) {

		if (t.abnext.halfplane
				&& p.pointLineTest(t.b, t.a) == Point_dt.ONSEGMENT)
			return extendOutside(t.abnext, p);
		if (t.bcnext.halfplane
				&& p.pointLineTest(t.c, t.b) == Point_dt.ONSEGMENT)
			return extendOutside(t.bcnext, p);
		if (t.canext.halfplane
				&& p.pointLineTest(t.a, t.c) == Point_dt.ONSEGMENT)
			return extendOutside(t.canext, p);
		return null;
	}

	private Triangle_dt extendOutside(Triangle_dt t, Point_dt p) {

		if (p.pointLineTest(t.a, t.b) == Point_dt.ONSEGMENT) { 
			Triangle_dt dg = new Triangle_dt(t.a, t.b, p);
			Triangle_dt hp = new Triangle_dt(p, t.b);
			t.b = p;
			dg.abnext = t.abnext;
			dg.abnext.switchneighbors(t, dg);
			dg.bcnext = hp;
			hp.abnext = dg;
			dg.canext = t;
			t.abnext = dg;
			hp.bcnext = t.bcnext;
			hp.bcnext.canext = hp;
			hp.canext = t;
			t.bcnext = hp;
			return dg;
		}
		Triangle_dt ccT = extendcounterclock(t, p);
		Triangle_dt cT = extendclock(t, p);
		ccT.bcnext = cT;
		cT.canext = ccT;
		startTriangleHull = cT;
		return cT.abnext;
	}

	private Triangle_dt extendcounterclock(Triangle_dt t, Point_dt p) {

		t.halfplane = false;
		t.c = p;
		t.circumcircle();

		Triangle_dt tca = t.canext;

		if (p.pointLineTest(tca.a, tca.b) >= Point_dt.RIGHT) {
			Triangle_dt nT = new Triangle_dt(t.a, p);
			nT.abnext = t;
			t.canext = nT;
			nT.canext = tca;
			tca.bcnext = nT;
			return nT;
		}
		return extendcounterclock(tca, p);
	}

	private Triangle_dt extendclock(Triangle_dt t, Point_dt p) {

		t.halfplane = false;
		t.c = p;
		t.circumcircle();

		Triangle_dt tbc = t.bcnext;

		if (p.pointLineTest(tbc.a, tbc.b) >= Point_dt.RIGHT) {
			Triangle_dt nT = new Triangle_dt(p, t.b);
			nT.abnext = t;
			t.bcnext = nT;
			nT.bcnext = tbc;
			tbc.canext = nT;
			return nT;
		}
		return extendclock(tbc, p);
	}

	private void flip(Triangle_dt t, int mc) {

		Triangle_dt u = t.abnext, v;
		t._mc = mc;
		if (u.halfplane || !u.circumcircle_contains(t.c)) 
			return;

		if (t.a == u.a) {
			v = new Triangle_dt(u.b, t.b, t.c);
			v.abnext = u.bcnext;
			t.abnext = u.abnext;
		} else if (t.a == u.b) {
			v = new Triangle_dt(u.c, t.b, t.c);
			v.abnext = u.canext;
			t.abnext = u.bcnext;
		} else if (t.a == u.c) {
			v = new Triangle_dt(u.a, t.b, t.c);
			v.abnext = u.abnext;
			t.abnext = u.canext;
		} else {
			throw new RuntimeException("Error in flip.");
		}

		v._mc = mc;
		v.bcnext = t.bcnext;
		v.abnext.switchneighbors(u, v);
		v.bcnext.switchneighbors(t, v);
		t.bcnext = v;
		v.canext = t;
		t.b = v.a;
		t.abnext.switchneighbors(u, t);
		t.circumcircle();

		currT = v;
		flip(t, mc);
		flip(v, mc);
	}

	/**
	 * write all the vertices of this triangulation to a text file of the
	 * following format <br>
	 * #vertices (n) <br>
	 * x1 y1 z1 <br>
	 * ... <br>
	 * xn yn zn <br>
	 */
	public void write_tsin(String tsinFile) throws Exception {
		FileWriter fw = new FileWriter(tsinFile);
		PrintWriter os = new PrintWriter(fw);
		// prints the tsin file header:
		int len = this._vertices.size();
		os.println(len);
		Iterator<Point_dt> it = this._vertices.iterator();
		while (it.hasNext()) {
			os.println(it.next().toFile());
		}
		os.close();
		fw.close();
	}

	/**
	 * this method write the triangulation as an SMF file (OFF like format)
	 * 
	 * 
	 * @param smfFile
	 *            - file name
	 * @throws Exception
	 */
	public void write_smf(String smfFile) throws Exception {
		int len = this._vertices.size();
		Point_dt[] ans = new Point_dt[len];
		Iterator<Point_dt> it = this._vertices.iterator();
		Comparator<Point_dt> comp = Point_dt.getComparator();
		for (int i = 0; i < len; i++) {
			ans[i] = it.next();
		}
		Arrays.sort(ans, comp);

		FileWriter fw = new FileWriter(smfFile);
		PrintWriter os = new PrintWriter(fw);
		// prints the tsin file header:
		os.println("begin");

		for (int i = 0; i < len; i++) {
			os.println("v " + ans[i].toFile());
		}
		int t = 0, i1 = -1, i2 = -1, i3 = -1;
		for (Iterator<Triangle_dt> dt = this.trianglesIterator(); dt.hasNext();) {
			Triangle_dt curr = dt.next();
			t++;
			if (!curr.halfplane) {
				i1 = Arrays.binarySearch(ans, curr.a, comp);
				i2 = Arrays.binarySearch(ans, curr.b, comp);
				i3 = Arrays.binarySearch(ans, curr.c, comp);
				if (i1 < 0 | i2 < 0 | i3 < 0)
					throw new RuntimeException(
							"wrong triangulation inner bug - cant write as an SMF file!");
				os.println("f " + (i1 + 1) + " " + (i2 + 1) + " " + (i3 + 1));
			}
		}
		os.println("end");
		os.close();
		fw.close();
	}

	/**
	 * compute the number of vertices in the convex hull. <br />
	 * NOTE: has a 'bug-like' behavor: <br />
	 * in cases of colinear - not on a asix parallel rectangle,
	 * colinear points are reported
	 * 
	 * @return the number of vertices in the convex hull.
	 */
	public int CH_size() {
		int ans = 0;
		Iterator<Point_dt> it = this.CH_vertices_Iterator();
		while (it.hasNext()) {
			ans++;
			it.next();
		}
		return ans;
	}

	public void write_CH(String tsinFile) throws Exception {
		FileWriter fw = new FileWriter(tsinFile);
		PrintWriter os = new PrintWriter(fw);
		// prints the tsin file header:
		os.println(CH_size());
		Iterator<Point_dt> it = this.CH_vertices_Iterator();
		while (it.hasNext()) {
			os.println(it.next().toFileXY());
		}
		os.close();
		fw.close();
	}

	private static Point_dt[] read_file(String file) throws Exception {
		if (file.substring(file.length() - 4).equals(".smf")
				| file.substring(file.length() - 4).equals(".SMF"))
			return read_smf(file);
		else
			return read_tsin(file);
	}

	private static Point_dt[] read_tsin(String tsinFile) throws Exception {
		FileReader fr = new FileReader(tsinFile);
		BufferedReader is = new BufferedReader(fr);
		String s = is.readLine();

		while (s.charAt(0) == '/')
			s = is.readLine();
		StringTokenizer st = new StringTokenizer(s);
		int numOfVer = new Integer(s).intValue();

		Point_dt[] ans = new Point_dt[numOfVer];

		// ** reading the file verteces - insert them to the triangulation **
		for (int i = 0; i < numOfVer; i++) {
			st = new StringTokenizer(is.readLine());
			double d1 = new Double(st.nextToken()).doubleValue();
			double d2 = new Double(st.nextToken()).doubleValue();
			double d3 = new Double(st.nextToken()).doubleValue();
			ans[i] = new Point_dt((int) d1, (int) d2, d3);
		}
		return ans;
	}

	/*
	 * SMF file has an OFF like format (a face (f) is presented by the indexes
	 * of its points - starting from 1, and not from 0): 
	 * begin 
	 * v x1 y1 z1
	 * ... 
	 * v xn yn zn 
	 * f i11 i12 i13 
	 * ... 
	 * f im1 im2 im3 
	 * end 
	 */
	private static Point_dt[] read_smf(String smfFile) throws Exception {
		return read_smf(smfFile, 1, 1, 1, 0, 0, 0);
	}

	private static Point_dt[] read_smf(String smfFile, double dx, double dy,
			double dz, double minX, double minY, double minZ) throws Exception {
		FileReader fr = new FileReader(smfFile);
		BufferedReader is = new BufferedReader(fr);
		String s = is.readLine();

		while (s.charAt(0) != 'v')
			s = is.readLine();

		Vector<Point_dt> vec = new Vector<Point_dt>();
		Point_dt[] ans = null; // 
		while (s != null && s.charAt(0) == 'v') {
			StringTokenizer st = new StringTokenizer(s);
			st.nextToken();
			double d1 = new Double(st.nextToken()).doubleValue() * dx + minX;
			double d2 = new Double(st.nextToken()).doubleValue() * dy + minY;
			double d3 = new Double(st.nextToken()).doubleValue() * dz + minZ;
			vec.add(new Point_dt((int) d1, (int) d2, d3));
			s = is.readLine();
		}
		ans = new Point_dt[vec.size()];
		for (int i = 0; i < vec.size(); i++)
			ans[i] = (Point_dt) vec.elementAt(i);
		return ans;
	}

	/**
	 * finds the triangle the query point falls in, note if out-side of this
	 * triangulation a half plane triangle will be returned (see contains), the
	 * search has expected time of O(n^0.5), and it starts form a fixed triangle
	 * (this.startTriangle),
	 * 
	 * @param p
	 *            query point
	 * @return the triangle that point p is in.
	 */
	public Triangle_dt find(Point_dt p) {
		return find(this.startTriangle, p);
	}

	/**
	 * finds the triangle the query point falls in, note if out-side of this
	 * triangulation a half plane triangle will be returned (see contains). the
	 * search starts from the the start triangle
	 * 
	 * @param p
	 *            query point
	 * @param start
	 *            the triangle the search starts at.
	 * @return the triangle that point p is in..
	 */
	public Triangle_dt find(Point_dt p, Triangle_dt start) {
		if (start == null)
			start = this.startTriangle;
		Triangle_dt T = find(start, p);
		return T;
	}

	
	private static Triangle_dt find(Triangle_dt curr, Point_dt p) {
		if (p == null)
			return null; 
		Triangle_dt next_t;
		if (curr.halfplane) {
			next_t = findnext2(p, curr);
			if (next_t == null || next_t.halfplane)
				return curr;
			curr = next_t;
		}
		while (true) {
			next_t = findnext1(p, curr);
			if (next_t == null)
				return curr;
			if (next_t.halfplane)
				return next_t;
			curr = next_t;
		}
	}

	/*
	 * assumes v is NOT an halfplane!
	 * returns the next triangle for find.
	 */
	private static Triangle_dt findnext1(Point_dt p, Triangle_dt v) {
		if (p.pointLineTest(v.a, v.b) == Point_dt.RIGHT && !v.abnext.halfplane)
			return v.abnext;
		if (p.pointLineTest(v.b, v.c) == Point_dt.RIGHT && !v.bcnext.halfplane)
			return v.bcnext;
		if (p.pointLineTest(v.c, v.a) == Point_dt.RIGHT && !v.canext.halfplane)
			return v.canext;
		if (p.pointLineTest(v.a, v.b) == Point_dt.RIGHT)
			return v.abnext;
		if (p.pointLineTest(v.b, v.c) == Point_dt.RIGHT)
			return v.bcnext;
		if (p.pointLineTest(v.c, v.a) == Point_dt.RIGHT)
			return v.canext;
		return null;
	}

	/** assumes v is an halfplane! - returns another (none halfplane) triangle */
	private static Triangle_dt findnext2(Point_dt p, Triangle_dt v) {
		if (v.abnext != null && !v.abnext.halfplane)
			return v.abnext;
		if (v.bcnext != null && !v.bcnext.halfplane)
			return v.bcnext;
		if (v.canext != null && !v.canext.halfplane)
			return v.canext;
		return null;
	}

	/**
	 * 
	 * @param p
	 *            query point
	 * @return true iff p is within this triangulation (in its 2D convex hull).
	 */

	public boolean contains(Point_dt p) {
		Triangle_dt tt = find(p);
		return !tt.halfplane;
	}

	/**
	 * 
	 * @param x
	 *            - X cordination of the query point
	 * @param y
	 *            - Y cordination of the query point
	 * @return true iff (x,y) falls inside this triangulation (in its 2D convex
	 *         hull).
	 */
	public boolean contains(double x, double y) {
		return contains(new Point_dt(x, y));
	}

	/**
	 * 
	 * @param q
	 *            Query point
	 * @return the q point with updated Z value (z value is as given the
	 *         triangulation).
	 */
	public Point_dt z(Point_dt q) {
		Triangle_dt t = find(q);
		return t.z(q);
	}

	/**
	 * 
	 * @param x
	 *            - X cordination of the query point
	 * @param y
	 *            - Y cordination of the query point
	 * @return the q point with updated Z value (z value is as given the
	 *         triangulation).
	 */
	public double z(double x, double y) {
		Point_dt q = new Point_dt(x, y);
		Triangle_dt t = find(q);
		return t.z_value(q);
	}

	private void updateBoundingBox(Point_dt p) {
		double x = p.x(), y = p.y(), z = p.z();
		if (_bb_min == null) {
			_bb_min = new Point_dt(p);
			_bb_max = new Point_dt(p);
		} else {
			if (x < _bb_min.x())
				_bb_min.x = x;
			else if (x > _bb_max.x())
				_bb_max.x = x;
			if (y < _bb_min.y)
				_bb_min.y = y;
			else if (y > _bb_max.y())
				_bb_max.y = y;
			if (z < _bb_min.z)
				_bb_min.z = z;
			else if (z > _bb_max.z())
				_bb_max.z = z;
		}
	}

	/**
	 * return the min point of the bounding box of this triangulation
	 * {{x0,y0,z0}}
	 */
	public Point_dt minBoundingBox() {
		return _bb_min;
	}

	/**
	 * return the max point of the bounding box of this triangulation
	 * {{x1,y1,z1}}
	 */
	public Point_dt maxBoundingBox() {
		return _bb_max;
	}

	/**
	 * computes the current set (vector) of all triangles and 
	 * return an iterator to them.
	 * 
	 * @return an iterator to the current set of all triangles. 
	 */
	public Iterator<Triangle_dt> trianglesIterator() {
		if (this.size() <= 2)
			_triangles = new Vector<Triangle_dt>();
		initTriangles();
		return _triangles.iterator();
	}

	/**
	 * returns an iterator to the set of all the points on the XY-convex hull
	 * @return iterator to the set of all the points on the XY-convex hull.
	 */
	public Iterator<Point_dt> CH_vertices_Iterator() {
		Vector<Point_dt> ans = new Vector<Point_dt>();
		Triangle_dt curr = this.startTriangleHull;
		boolean cont = true;
		double x0 = _bb_min.x(), x1 = _bb_max.x();
		double y0 = _bb_min.y(), y1 = _bb_max.y();
		boolean sx, sy;
		while (cont) {
			sx = curr.p1().x() == x0 || curr.p1().x() == x1;
			sy = curr.p1().y() == y0 || curr.p1().y() == y1;
			if ((sx & sy) | (!sx & !sy)) {
				ans.add(curr.p1());
			}
			if (curr.bcnext != null && curr.bcnext.halfplane)
				curr = curr.bcnext;
			if (curr == this.startTriangleHull)
				cont = false;
		}
		return ans.iterator();
	}

	/**
	 * returns an iterator to the set of points compusing this triangulation.
	 * @return iterator to the set of points compusing this triangulation.
	 */
	public Iterator<Point_dt> verticesIterator() {
		return this._vertices.iterator();
	}

	private void initTriangles() {
		if (_modCount == _modCount2)
			return;
		if (this.size() > 2) {
			_modCount2 = _modCount;
			Vector<Triangle_dt> front = new Vector<Triangle_dt>();
			_triangles = new Vector<Triangle_dt>();
			front.add(this.startTriangle);
			while (front.size() > 0) {
				Triangle_dt t = front.remove(0);
				if (t._mark == false) {
					t._mark = true;
					_triangles.add(t);
					if (t.abnext != null && !t.abnext._mark) {
						front.add(t.abnext);
					}
					if (t.bcnext != null && !t.bcnext._mark) {
						front.add(t.bcnext);
					}
					if (t.canext != null && !t.canext._mark) {
						front.add(t.canext);
					}
				}
			}
			// _triNum = _triangles.size();
			for (int i = 0; i < _triangles.size(); i++) {
				_triangles.elementAt(i)._mark = false;
			}
		}
	}
}

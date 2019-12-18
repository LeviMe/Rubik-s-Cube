import java.awt.Color;
import java.util.LinkedList;

class Cube {
	
	LinkedList<Triangle> triangles;
	int halfSize;
	Point[] points;
	double thetaX,thetaY,thetaZ;
	
	public Cube(CubeModel mini) {
		triangles=new LinkedList<Triangle>();
		points=new Point[96];
		thetaX=0;
		thetaY=0;
		thetaZ=0;
		

		int count24=0;
		double st=10; double l=140; double size=2*st+l;
		Point2D[] p2D= new Point2D[]{
			new Point2D(0,0),
			new Point2D(st,0),
			new Point2D(st+l,0),
			new Point2D(2*st+l,0),

			new Point2D(0,st),
			new Point2D(st,st),
			new Point2D(st+l,st),
			new Point2D(2*st+l,st),


			new Point2D(0,st+l),
			new Point2D(st,st+l),
			new Point2D(st+l,st+l),
			new Point2D(2*st+l,st+l),

			new Point2D(0,2*st+l),
			new Point2D(st,2*st+l),
			new Point2D(st+l,2*st+l),
			new Point2D(2*st+l,2*st+l)};


		int tab[][]=new int[][] {
				{1,2,6},	{1,6,5},	{4,1,5},	{6,2,7},
				{6,7,10},	{10,7,11},	{14,10,11},	{14,9,10},
				{14,13,9},	{13,8,9},	{8,4,9},	{9,4,5},
				{5,6,10},{5,10,9}
				};

		for (SymFace courante:SymFace.values()) {
			Point[] p=new Point[16];int count4=0;
			for (Point2D p2d:p2D) {
				Point courant=p2d.AssigneFace(courante,	size);
				p[count4++]=courant;
				points[count24++]=courant;
			}
		}

		for (int indiceFace=0;indiceFace<6;indiceFace++) {
			int countBreak=0;
			for (int indicePoints[]:tab) {
				if (countBreak++==12) break;
				triangles.add(new Triangle(	points[16*indiceFace+indicePoints[0]],
											points[16*indiceFace+indicePoints[1]],
											points[16*indiceFace+indicePoints[2]],
											Color.black	));


			}
			triangles.add(new Triangle(points[16*indiceFace+tab[12][0]],
					points[16*indiceFace+tab[12][1]],
					points[16*indiceFace+tab[12][2]],
					mini.faces[indiceFace]));
			triangles.add(new Triangle(points[16*indiceFace+tab[13][0]],
					points[16*indiceFace+tab[13][1]],
					points[16*indiceFace+tab[13][2]],
					mini.faces[indiceFace]));
		}

		triangles.add(new Triangle(points[4],points[1],points[80+2], Color.black));
		triangles.add(new Triangle(points[16+8],points[16+13],points[64+1], Color.black));
		triangles.add(new Triangle(points[16+4],points[80+4],points[48+4], Color.black));
		triangles.add(new Triangle(points[64+2],points[48+14],points[12+1], Color.black));
		triangles.add(new Triangle(points[32+11],points[64+11],points[11], Color.black));
		triangles.add(new Triangle(points[32+2],points[16+14],points[64+13], Color.black));
		triangles.add(new Triangle(points[2],points[7],points[32+8], Color.black));
		triangles.add(new Triangle(points[16+2],points[32+4],points[32+1], Color.black));

		halfSize=(int)(size/2);
		for (Point p:points) {
			p.Dx(-halfSize);  p.Dy(-halfSize); p.Dz(-halfSize);
			p.scale(0.4);
		}

	}

	public void Rx(double theta) {
		for (Point P:this.points)
			P.Rx(theta);
	}

	public void Ry(double theta) {
		for (Point P:this.points)
			P.Ry(theta);
	}

	public void Rz(double theta) {
		for (Point P:this.points)
			P.Rz(theta);
	}
	

	

	public void Dx(double dx) {
		for (Point P:this.points)
			P.Dx(dx);
			
	
	}

	public void Dy(double dy) {
		for (Point P:this.points)
			P.Dy(dy);
	}

	public void Dz(double dz) {
		for (Point P:this.points)
			P.Dz(dz);
	}

	


	public void ajusteRange(double fov,double range) {
		for (Point P:this.points)
			P.ajusteRange(fov,range);
	}

}
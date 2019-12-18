

enum SymFace {Xp,Xm,Yp,Ym,Zp,Zm}

public class Point2D {
	double a,b;
	
	public Point2D(double a1,double b1) {
		a=a1;b=b1;
	}
	
	
public Point AssigneFace(SymFace face, double size) {
	Point res=null;
	
	if (face.equals(SymFace.Xm)) {
		res=new Point(0,a,b);
	}
	if (face.equals(SymFace.Xp)) {
		res=new Point(size,a,b);
	}
	if (face.equals(SymFace.Ym)) {
		res=new Point(b,0,a);
	}
	if (face.equals(SymFace.Yp)) {
		res=new Point(b,size,a);
	}
	if (face.equals(SymFace.Zm)) {
		res=new Point(a,b,0);
	}
	if (face.equals(SymFace.Zp)) {
		res=new Point(a,b,size);
	}
	return res;
	
}
	
	



}

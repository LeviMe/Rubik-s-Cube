class Point {
	double x;
	double y;
	double z;
	
	public Point(double a, double b, double c) {
		x = a;	
		y = b;
		z = c;
	}
	
	public void Ry(double theta) {
		double cos = Math.cos(theta);
		double sin = Math.sin(theta);
		double xn = cos*x + sin*z;
		double zn = -sin*x + cos*z;
		x = xn; z = zn;
	}
	
	public void Rx(double theta) {
		double cos = Math.cos(theta);
		double sin = Math.sin(theta);
		double yn = cos*y - sin*z;
		double zn =   sin*y + cos*z;
		y = yn; z = zn;
	}	
	
	public void Rz(double theta) {
		double cos = Math.cos(theta);
		double sin = Math.sin(theta);
		double xn = cos*x - sin*y;
		double yn = sin*x + cos*y;
		x = xn; y = yn;
	}
	
	public void Dx(double dx) {
		x+=dx;
	}
	
	public void Dy(double dy) {
		y+=dy;
	}
	
	public void Dz(double dz) {
		z+=dz;
	}

	public void scale(double factor) {
		x*=factor;
		y*=factor;
		z*=factor;
	}
		
	public void ajusteRange(double fov,double range) {
		x=fov/(range+-z)*x;
		y=fov/(range+-z)*y;
	}
	
	public String toString() {
		return "["+(int)x+" "+(int)y+" "+(int)z+"]";
	}
}

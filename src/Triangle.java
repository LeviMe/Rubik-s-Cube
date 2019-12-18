import java.awt.Color;
import java.awt.image.BufferedImage;

class Triangle {
	Point p0, p1, p2;
	Color couleur;
	Color couleurOrigine;
	
	public Triangle (Point a, Point b, Point c) {
		p0=a;p1=b;p2=c;
	}
	
	public Triangle (Point a, Point b, Point c, Color col) {
		p0=a;p1=b;p2=c;couleurOrigine=col;
	}
	
	public Triangle (Point[] tab, Color col) {
		p0=tab[0];p1=tab[1];p2=tab[3];couleurOrigine=col;
	}
	
	public void ConfigureCouleur() {
		Point vec1=new Point(p2.x-p0.x,p2.y-p0.y,p2.z-p0.z);
		Point vec2=new Point(p2.x-p1.x,p2.y-p1.y,p2.z-p1.z);
		Point vecteurNormal= new Point (vec1.y*vec2.z - vec1.z*vec2.y,
										vec1.z*vec2.x - vec1.x*vec2.z,
										vec1.x*vec2.y - vec1.y*vec2.x);
		
		double norme= Math.sqrt(vecteurNormal.x*vecteurNormal.x+
								vecteurNormal.y*vecteurNormal.y+
								vecteurNormal.z*vecteurNormal.z);
		vecteurNormal.x/=norme;
		vecteurNormal.y/=norme;
		vecteurNormal.z/=norme;
		double AngleCos=Math.abs(vecteurNormal.z);
		
		double factor=3;
		double redLinear = Math.pow(couleurOrigine.getRed(), factor) * AngleCos;
		double greenLinear = Math.pow(couleurOrigine.getGreen(), factor) * AngleCos;
		double blueLinear = Math.pow(couleurOrigine.getBlue(), factor) * AngleCos;

		int red = (int) Math.pow(redLinear, 1/factor);
		int green = (int) Math.pow(greenLinear, 1/factor);
		int blue = (int) Math.pow(blueLinear, 1/factor);

		this.couleur=new Color(red, green, blue);	
	}
	

	public static double Aire(Point p0,Point p1, Point p2) {
		double Aire=(p0.y-p2.y)*(p1.x-p2.x) - (p1.y-p2.y)*(p0.x-p2.x);
		return Aire;
	}
	

	public double[] TrCst() {
		Point[] ps={p0,p1,p2};
		double[] res=new double[] {
			(ps[1].y-ps[0].y)*(ps[2].z-ps[0].z)-(ps[1].z-ps[0].z)*(ps[2].y-ps[0].y),
			(ps[1].x-ps[0].x)*(ps[2].z-ps[0].z)-(ps[1].z-ps[0].z)*(ps[2].x-ps[0].x),
			(ps[1].x-ps[0].x)*(ps[2].y-ps[0].y)-(ps[1].y-ps[0].y)*(ps[2].x-ps[0].x)};

	return res;}

	public int[] minEtMax() {
		Point[] qp={p0,p1,p2};
		int [] res=new int[]{10000,10000,-10000,-10000};

		for (int i=0;i<3;i++) {
			if (qp[i].x<res[0]) res[0]=(int) qp[i].x;	//minX
			if (qp[i].x>res[2]) res[2]=(int) qp[i].x;	//maxX
			if (qp[i].y<res[1]) res[1]=(int) qp[i].y;	//minY
			if (qp[i].y>res[3]) res[3]=(int) qp[i].y;	//maxY
		}
		return res;
	}


	public boolean contains(Point p) {
		boolean b=true;
		double AireTr1=Aire(p0,p1,p2);
		double Aire2=Aire(p0,p1,p)/AireTr1;
		double Aire3=Aire(p1,p2,p)/AireTr1;
		double Aire4=Aire(p2,p0,p)/AireTr1;
	    b = (0<=Aire2 && Aire2<=1) && 
	    	(0<=Aire3 && Aire3<=1) &&
	    	(0<=Aire4 && Aire4<=1);
		return b;
		}

	public void affiche(BufferedImage img, double[][] zBuffer,int xcenter, int ycenter) {
		int[] minEtMax=minEtMax();
		ConfigureCouleur();
		double cst[]=TrCst();

		for (int x=minEtMax[0];x<minEtMax[2]+1;x++) {
			for (int y=minEtMax[1];y<minEtMax[3]+1;y++) {

				double z=-((x-p1.x)*cst[0] - (y-p1.y)*cst[1])/cst[2]+p1.z;

					if (contains(new Point(x,y,0))) {
						if (z>zBuffer[x+xcenter][-y+ycenter]) {zBuffer[x+xcenter][-y+ycenter]=z;
							img.setRGB(x+xcenter, -y+ycenter, couleur.getRGB());
						}
				}
			}
		}
	}

	
}
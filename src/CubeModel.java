import java.awt.Color;

class CubeModel {
	public int id;
	Color[] faces;
	int posx, posy, posz; // position dans le tableau
	int ortx,orty,ortz; // orientation par rapport à l'origine
	
	public CubeModel(int id, Color[] faces, int posx, int posy, int posz) {
		this.id=id;
		this.faces=faces;
		this.posx=posx;
		this.posy=posy;
		this.posz=posz;
		ortx=0;orty=0;ortz=0;
	}

	public void rotation(int axe) {
		byte[][] matrice = new byte[][]
										{{1,1,	6,5,	3,4},
										{2,2,	5,6,	4,3},
										{5,6,	3,3,	2,1},
										{6,5,	4,4,	1,2},
										{4,3,	1,2,	5,5},
										{3,4,	2,1,	6,6}};
		Color facesNouveau[]= new Color[6];
		for (int i=0;i<6;i++) {
			facesNouveau[matrice[i][axe-1]-1]=this.faces[i];
		}
		this.faces=facesNouveau;

		switch (axe) {
		case 1: ortx=(ortx+1)%4;break;
		case 3: orty=(orty+1)%4;break;
		case 5: ortz=(ortz+1)%4;break;
		case 2: ortx=(ortx+3)%4;break;
		case 4: orty=(orty+3)%4;break;
		case 6: ortz=(ortz+3)%4;break;}
		
	}
	
	public boolean orientationCorrect() {
		int c1=0;
		c1+=posx==1?1:0;  
		c1+=posy==1?1:0;  
		c1+=posz==1?1:0;
		boolean res= (c1==2) || (ortx==0 && orty==0 && ortz==0); 	
		return res;
	}

	public String toString() {
		String res="id "+id+"\n";
		res+="col [";
		for (Color c:this.faces)
			{if (c!=null)
				res+=c.toString()+"  ";
			else res+="***** ";}
		res+="]\n";
		res+=("pos :["+posx+" "+posy+" "+posz+"]\n");
		res+=("ort :["+ortx+" "+orty+" "+ortz+"]\n");
	return res;
	}
}

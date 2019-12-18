import java.awt.Color;

class RubikCubeModel {
	CubeModel[][][] tableau;
	
	public RubikCubeModel () {
		tableau=new CubeModel[3][3][3];
		int countcube=1;
	
		for (int v=0;v<27;v++) {
			Color[] col=new Color[] {Color.black,Color.black,Color.black,
									Color.black,Color.black,Color.black};
			int count=v;
			int i=count%3;count/=3;
			int j=count%3;count/=3;
			int k=count%3;count/=3;
			
			if (i==2)	col[0]=Color.white;
			if (i==0)	col[1]=Color.red;
			if (j==2)	col[2]=new Color(255,139,23);
			if (j==0)	col[3]=Color.blue;
			if (k==2)	col[4]=new Color(0,150,0);
			if (k==0)	col[5]=Color.yellow;
			
			if (i!=1 || j!=1 || k!=1) 
				{tableau[i][j][k]=new CubeModel(countcube++,col,i,j,k);
				//System.out.println(tableau[i][j][k].id);
				}
		}	
	}
	

	public String[] dispFace2(int face) {
		String[] outp=new String[3];
		
		for (int i=0;i<3;i++) {
			String res="";
			for (int j=0;j<3;j++) {
				int ajout=0;
				switch (face) {
				case 1: ajout=tableau[2][2-i][2-j].id;break;
				case 2: ajout=tableau[0][2-i][j].id;break;
				case 3: ajout=tableau[j][2][i].id;break;
				case 4: ajout=tableau[j][0][2-i].id;break;
				case 5: ajout=tableau[j][2-i][2].id;break;
				case 6: ajout=tableau[2-j][2-i][0].id;break;}
				res+=ajout;
				if (ajout<10) res+=" ";
				if (j!=2) res+=", ";
			}
			outp[i]=res;
		}
		return outp;
	}
	
	public void dispRap() {
		for (int v=0;v<27;v++) {
			int count=v;
			int i=count%3;count/=3;int j=count%3;count/=3;int k=count%3;count/=3;
			if (i!=1 || j!=1 || k!=1) {
				System.out.println(i+"  "+j+"  "+k+"  "+tableau[i][j][k].id);
			}
		}
	}
	
	
	public String toString() {
		String res="";
		String[][] dispFace=new String[][] {
				this.dispFace2(1),this.dispFace2(2),
				this.dispFace2(3),this.dispFace2(4),
				this.dispFace2(5),this.dispFace2(6)
		};
		for (int i=0;i<3;i++) { 
				res+="             "+dispFace[2][i]+"\n";
		}
		res+="\n";
		for (int i=0;i<3;i++) { 
			res+=dispFace[1][i]+"   "+dispFace[4][i]+"   "
				+dispFace[0][i]+"  "+dispFace[5][i]+"\n";
		}	
		res+="\n";
		for (int i=0;i<3;i++) { 
			res+="             "+dispFace[3][i]+"\n";		
		}	
		return res;
	}


	public void rotation(int axe, int face) {
		int cas=-1;
		if ((axe==1 || axe==2)   &&	(face==1 || face==2)) cas=1;
		if ((axe==3 || axe==4)   &&	(face==3 || face==4)) cas=2;
		if ((axe==5 || axe==6)   &&	(face==5 || face==6)) cas=3;
		
		byte[][][] corsp= new byte[][][]
				{{{0,2},	{0,0}},
				 {{0,1},	{1,0}},
				 {{0,0},	{2,0}},
				 {{1,2},	{0,1}},
				 {{1,1},	{1,1}},
				 {{1,0},	{2,1}},
				 {{2,2},	{0,2}},
				 {{2,1},	{1,2}},
				 {{2,0},	{2,2}}};
		
		
		int coord=2*(face%2);
		CubeModel[][] tabTemp=new CubeModel[3][3];
			
			for (int i=0;i<3;i++) {
				for (int j=0;j<3;j++) {
					switch (cas) {
					case 1:tabTemp[i][j]=tableau[coord][i][j];break;
					case 2:tabTemp[i][j]=tableau[i][coord][j];break;
					case 3:tabTemp[i][j]=tableau[i][j][coord];break;}
				}
			}
			for (byte[][] permutation:corsp) {
				byte[] ancien=new byte[2];
				byte[] nouveau=new byte[2];
				if(axe==1 || axe ==4 || axe==5)
					  {ancien  =permutation[0];
				   	   nouveau =permutation[1];}
				if (axe==2 || axe==3 || axe==6)
					  {ancien  =permutation[1];
				   	   nouveau =permutation[0];}
				
				CubeModel moved=tabTemp[ancien[0]][ancien[1]];
				moved.rotation(axe);
				switch (cas) {
				case 1:tableau[coord][nouveau[0]][nouveau[1]]=moved; break;
				case 2:tableau[nouveau[0]][coord][nouveau[1]]=moved; break;
				case 3:tableau[nouveau[0]][nouveau[1]][coord]=moved; break;}

					
			}
	}
	
		public void melange(int card) {
			
			for (int i=0;i<card;i++) {
				int face=(int)(Math.random()*6)+1;
				int s=(int)(Math.random()*2)+1;
				int axe=2*((face-1)/2)+s;
				this.rotation(axe, face);				
			}
		}
		
		public boolean termine() {
			boolean res=true;
			int w=0;
			for (int v=0;v<27 && res;v++) {
				int count=v;
				int i=count%3;count/=3;
				int j=count%3;count/=3;
				int k=count%3;count/=3;
				if (i!=1 || j!=1 || k!=1)
					{res&=(tableau[i][j][k].id== (w++ + 1) 
					    && tableau[i][j][k].orientationCorrect());
				//	System.out.println(i+","+j+","+k+"---"+tableau[i][j][k].id+"  "+(w));
					}
		
			} 
			return res;
			
		}
	
}
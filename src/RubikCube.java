class RubikCube{
	Cube[][][] tableau;
	double thetaX, thetaY, thetaZ;
	RubikCubeModel rubikCubeModel;
	final double grosmt=1.03;
	final double fov=510;
	final double range=645;
	
	public RubikCube(RubikCubeModel rubikCubeModel, double thetaX, double thetaY, double thetaZ) {
	tableau=new Cube[3][3][3];	
	this.thetaX=thetaX;
	this.thetaY=thetaY;
	this.thetaZ=thetaZ;
	this.rubikCubeModel=rubikCubeModel;
	
	for (int v=0;v<27;v++) {
		int count=v;
		int i=count%3;count/=3;int j=count%3;count/=3;int k=count%3;count/=3;
		if (i!=1 || j!=1 || k!=1) 
			{Cube cube=tableau[i][j][k]=new Cube(rubikCubeModel.tableau[i][j][k]);
			double dx=65*(i-1);double dy=65*(j-1);double dz=65*(k-1);
			cube.Dx(grosmt*dx);cube.Dy(grosmt*dy);cube.Dz(grosmt*dz);
			cube.thetaX=thetaX;
			cube.thetaY=thetaY;
			cube.thetaZ=thetaZ;
			cube.Rx(thetaX); cube.Ry(thetaY); cube.Rz(thetaZ);
			cube.ajusteRange(fov,range);}
		}
	}
	
	public void Rotation(int face, int axe, double angle) {
		int i1=0,i2=0,i3=0;
		for (int i=0;i<3;i++) {
			for (int j=0;j<3;j++) {
				switch (face) {
				case 1: i1=2;i2=i;i3=j;break;
				case 2: i1=0;i2=i;i3=j;break;
				case 3: i1=i;i2=2;i3=j;break;
				case 4: i1=i;i2=0;i3=j;break;
				case 5: i1=i;i2=j;i3=2;break;
				case 6: i1=i;i2=j;i3=0;break;}	
			Cube cube=tableau[i1][i2][i3]=new Cube(rubikCubeModel.tableau[i1][i2][i3]);
			double dx=65*(i1-1);double dy=65*(i2-1);double dz=65*(i3-1);
			cube.Dx(grosmt*dx); cube.Dy(grosmt*dy); cube.Dz(grosmt*dz);
			
			
			switch ((face+1)/2) {
			case 1: cube.Rx(angle);break;
			case 2: cube.Ry(angle);break;
			case 3: cube.Rz(angle);break;}
			
			cube.Rx(thetaX);cube.Ry(thetaY);cube.Rz(thetaZ);
			
			//cube.Ry(thetaY);cube.Rx(thetaX);
			cube.ajusteRange(fov,range);
			}}
}
	
}
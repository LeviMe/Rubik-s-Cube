import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;





import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;


class PanneauCube extends JFrame implements KeyListener, ActionListener {
	RubikCubeModel rubikCubeModel;
	RubikCube rCube;
	
	JPanel panneau;
	double thetaX, thetaY, thetaZ;
	int xcenter=220;
	int ycenter=220;
	private Timer timer;
	
	private boolean rotationEC=false;
	private int axeRotationEC=0;
	private double angleRotationEC=0;
	private int instantRotationEC=0;
	private int faceRotationEC=0;
	
	int compteurMouvements=0;
	long instantDebut;

	public PanneauCube() {
		addKeyListener(this);
		timer=new Timer(40,this);
		instantDebut=System.currentTimeMillis();
		
		thetaX=Math.PI/5;//15;
		thetaY=Math.PI/6;//-35;
		thetaZ=Math.PI/9;//15;

		rubikCubeModel=new RubikCubeModel();
		rubikCubeModel.melange(324);
		
		
		panneau=new JPanel() {
			
			final File file=new File("C:\\Users\\LeviMe\\Pictures\\bg2.png");

			public void paintComponent(Graphics g) {
				int t0=(int)System.currentTimeMillis()%100000;
				rCube=new RubikCube(rubikCubeModel,thetaX,thetaY,thetaZ);
				if (rotationEC) { //acceleration possible: creer deuxieme constructeur pour ne pas avoir à faire deux
					//fois la rotation de même cubes:
					rCube.Rotation(faceRotationEC, axeRotationEC, angleRotationEC);}
				
				super.paintComponent(g);
				try {g.drawImage(ImageIO.read(file), 0, 0,getWidth(), getHeight(), null);}
				catch (IOException e) {}
			//	g.setColor(new Color(143,192,192));
			//	g.fillRect(0, 0, getWidth(), getHeight());
				BufferedImage img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
				double[][] zBuffer = new double[img.getWidth()][img.getHeight()];
				for (int i=0;i<img.getWidth();i++) {
					for (int j=0;j<img.getHeight();j++) {
						zBuffer[i][j]=Double.NEGATIVE_INFINITY;
					}
				}
					
				for (int v=0;v<27;v++) {
					int count=v;
					int i=count%3;count/=3;int j=count%3;count/=3;int k=count%3;count/=3;
					if (i!=1 || j!=1 || k!=1)
						{Cube cube=rCube.tableau[i][j][k];
						for (Triangle Tr:cube.triangles) 
							Tr.affiche(img,zBuffer,xcenter,ycenter);	
						}}
			g.drawImage(img, 0, 0, null);
			g.setColor(Color.black);
		/**	g.drawString("angles panneau", 10,10);
			g.drawString(Integer.toString((int)Math.toDegrees(thetaX)%360), 10, 25);
			g.drawString(Integer.toString((int)Math.toDegrees(thetaY)%360), 10, 40);
			g.drawString(Integer.toString((int)Math.toDegrees(thetaZ)%360), 10, 55);**/
			int tf=(int)System.currentTimeMillis()%100000;
			int dt=tf-t0;
		//	System.out.println(dt);
			}
			};


		panneau.setSize(139,139);

		getContentPane().add(panneau);
		setSize((int)2*xcenter+50, (int)2*ycenter+50);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setTitle("LeviMe Rubik's Cube");
	}

	@Override
	public void keyTyped(KeyEvent e) {

		char c=e.getKeyChar();
		boolean b=true;
		int face=0; int axe=0;
		switch (c) {
		case 'a':face=1;	axe=1  ;break;
		case 'A':face=1;	axe=2  ;break;
		case 'z':face=2;	axe=2  ;break;
		case 'Z':face=2;	axe=1  ;break;
		case 'e':face=3;	axe=3  ;break;
		case 'E':face=3;	axe=4  ;break;
		case 'q':face=4;	axe=4  ;break;
		case 'Q':face=4;	axe=3  ;break;
		case 's':face=5;	axe=5  ;break;
		case 'S':face=5;	axe=6  ;break;
		case 'd':face=6;	axe=6  ;break;
		case 'D':face=6;	axe=5  ;break;
		default: b=false;
		}
		if (b && !timer.isRunning()) {
				timer.setActionCommand(Integer.toString(face*10+axe));				
				timer.start();
				
				}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		boolean b=true;
		//System.out.println(e.getKeyCode());
		double step=Math.PI/32;
		switch(e.getKeyCode()) {
			case 37:  thetaX+=step; break;
			case 39:  thetaX-=step; break;
			case 38:  thetaY+=step; break;
			case 40:  thetaY-=step; break;
			case 513: thetaZ+=step; break;
			case 517: thetaZ-=step; break;
			default: b=false;
		}
		if (b) panneau.repaint();
	}


	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String s=e.getActionCommand();
		int i=Integer.parseInt(s);
		int axe=i%10; int face=i/10;
		double step=10*Math.PI/180;
		
		rotationEC=true;
		axeRotationEC=axe;
		faceRotationEC=face;
		int signe=2*((axe)%2)-1;
		angleRotationEC+=step*signe;
		instantRotationEC+=1;
		
		
		//step est l'angle en radians donnant le pas de la rotation;
		//l'instant maximal de l'animation est 90/(step en degre) -1.
		//l'actualisation du modèle faisant la derniere image:
		//pas d'actualisation redondante
		if (instantRotationEC==8) {
			rotationEC=false;
			angleRotationEC=0;
			instantRotationEC=0;
			timer.stop();
			rubikCubeModel.rotation(axe, face);
			compteurMouvements++;
			//System.out.println(rubikCubeModel);
			
		
		if (rubikCubeModel.termine())
		{System.out.println("jeu terminé"
				+ "\n en "+compteurMouvements+" mouvements"
				+ " et "+calculTemp());
		this.removeKeyListener(this.getKeyListeners()[0]);
		
		}
		}
		panneau.repaint();
		}
	
	public String calculTemp() {
		long instantFin=System.currentTimeMillis();
		long dt=(instantFin-instantDebut)/1000;
		long mn=dt/60;
		long s=dt%60;
		long h=mn/60;
		mn%=60;
		String res="";
		res+=(h!=0)?h+"h ":"";
		res+=(mn!=0)?mn+"mn ":"";
		res+=(s!=0)?s+" s":"";
		return res;
	}
		
}
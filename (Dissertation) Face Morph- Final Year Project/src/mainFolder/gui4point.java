package mainFolder;

import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Objects;
import delaunay_triangulation.*;

public class gui4point extends JFrame implements ActionListener{
	
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	double newWidth = screenSize.getWidth();
	double newHeight = screenSize.getHeight();
	
	double oldWidth = 1950.0;
	double oldHeight = 1100.0;
	
	double width = newWidth/oldWidth;
	double height = newHeight/oldHeight;

	int spaceToHold = 300;
	int firstSelected = 0;
	int secondSelected = 0;
	int meshSelected = 0;
	int numberStep;
	int save1X = 0;
	int save1Y = 0;
	int save2X = 0;
	int save2Y = 0;
	int highestTraverse = 0;
	int minLength = 0;
	int maxLength = 0;
	int minHeight = 0;
	int maxHeight = 0;
	int counter = 0;
	int numberPoints = 45;
	int firstImageXStart = (int)(130.0*width);
	int secondImageXStart = (int)(1320.0*width);;
	int imagesYStart = (int)(378.0*height);
	int smallX = 0;
	int bigX = 0;
	int smallY = 0;
	int bigY = 0;
	int lowerX = 0;
	int upperX = 0;
	int Xposition = 0;
	int Yposition = 0;
	int Xmin = 0;
	int Xmax = 0;
	int Ymin = 0;
	int Ymax = 0;
	int a1 = 0;
	int a2 = 0;
	int b1 = 0;
	int b2 = 0;
	int clr1 = 0; 
	int clr2 =  0;									
	int  red1 = 0;
	int  green1 = 0;
	int  blue1 = 0;
	int  red2 = 0;
	int  green2 = 0;
	int  blue2 = 0;
	int red = 0;
	int green = 0;
	int blue = 0;
	int theXpos = 10;
	int theYpos = (int)(1500.0*height);
	int numberDownX = 0;
	int numberDownY = 0;
	int pixelRateX = 0;
	int pixelRateY = 0;
	int OriginalImageYcoordinate = (int)(340.0*height);
	int midPoint = 0;
	int XratioTemp = 0;
	int Yratio = 0;
	int Xratio = 0;
	int positionAdOn = (int)(10.0*width);
	int colorInt = 0;
	int numberCuts = 0;
	
	int[] image1PointX = new int[spaceToHold];
	int[] image1PointY = new int[spaceToHold];
	int[] image2PointX = new int[spaceToHold];
	int[] image2PointY = new int[spaceToHold];
	int[] xCoords = new int[10000];
	int[] yCoords = new int[10000];
	int[] sourceCoords1X = new int[10000];
	
	double image1Xmin = 0;
	double image1Xmax = 0;
	double image1Ymin = 0;
	double image1Ymax = 0;
	double image2Xmin = 0;
	double image2Xmax = 0;
	double image2Ymin = 0;
	double image2Ymax = 0;
	double ratio = 0.5;
	double Barry1 = 0;
	double Barry2 = 0;
	double Barry3 = 0;			
	double x1 = 0;
	double x2 = 0;
	double x3 = 0;
	double y1 = 0;
	double y2 = 0;
	double y3 = 0;
	double ratioXmin = 0;
	double ratioXmax = 0;
	double ratioYmin = 0;
	double ratioYmax = 0;
	double ratioDivisour = 0;

	double[] image3PointX = new double[1000];
	double[] image3PointY = new double[1000];
		
	DoublePair[] CoordsImage3 = new DoublePair[numberPoints+1];

	boolean image1pressed = false;
	boolean image2pressed = false;
	boolean isFilmStrip = false;
	boolean morphingTime = false;	
	
	boolean[] hasBeenTraversed = new boolean[spaceToHold];
	boolean[] has1BeenModified = new boolean[spaceToHold];
	boolean[] has2BeenModified = new boolean[spaceToHold];
	boolean[] isTheLimit = new boolean[50];
	
	int[][] outputCoordinates = new int[10000][10000];
	
	JButton button1;
	JButton button2;
	JButton mash;
	JButton next;
	JButton previous;
	JButton morphImages;
	JButton filmStrip;	
	JButton fiveTimes;
	JButton tenTimes;
	JButton twentyTimes;
	JButton saver;
	JButton stripSaver;
	JLabel imageLabel;
	JLabel instructions;
	JLabel label1;
	JLabel label2;	
	JLabel labely;
	JSlider slider;
	JPanel boarder;
	JPanel boarderImage1Vert1 = new JPanel();
	JPanel boarderImage1Vert2 = new JPanel();
	JPanel boarderImage1Hori1 = new JPanel();
	JPanel boarderImage1Hori2 = new JPanel();
	JPanel boarderImage2Vert1 = new JPanel();
	JPanel boarderImage2Vert2 = new JPanel();
	JPanel boarderImage2Hori1 = new JPanel();
	JPanel boarderImage2Hori2 = new JPanel();
	JFrame displayFrame;

	
	ImageIcon faceSketch;	
	ImageIcon icon;
	
	String contentType;
	
	String[] instructionWords = new String[spaceToHold];
	
	Color alternateGreen = new Color(83, 221, 89);
    Color alternatePurple = new Color(177, 88, 255);
    Color panelColor = new Color(1, 1, 25);
    Color newColour = new Color(red, green, blue);
    Color[] colors = new Color[spaceToHold];
    
	Triangle_dt[] triangleList1 = new Triangle_dt[spaceToHold];
	Triangle_dt[] triangleList2 = new Triangle_dt[spaceToHold];
	Triangle_dt[] triangleList3 = new Triangle_dt[spaceToHold];
	
	File ImageFile1 = null;
	File ImageFile2 = null;
	
	BufferedImage Image1 = null;
	BufferedImage Image2 = null;	
	BufferedImage anImage;
	BufferedImage totalImage;
	BufferedImage imageOutput;
	
	Graphics graphics;
	
	ImageIcon[] faceImages = new ImageIcon[numberPoints];
	
	Point_dt pointHolderI1 = new Point_dt(0,0);
	Point_dt pointHolderI2 = new Point_dt(0,0);
	Point_dt pointHolderI3 = new Point_dt(0,0);
	Point_dt pointHolderII1 = new Point_dt(0,0);
	Point_dt pointHolderII2 = new Point_dt(0,0);
	Point_dt pointHolderII3 = new Point_dt(0,0);
	
	public gui4point(){
		
	    instructionWords[0] = "Upper left forehead";
	    instructionWords[1] = "Upper right forehead";
	    instructionWords[2] = "Lower left forehead";
	    instructionWords[3] = "Lower right forehead";
	    instructionWords[4] = "Left above ear";
	    instructionWords[5] = "Right above ear";
	    instructionWords[6] = "Left below ear";
	    instructionWords[7] = "Right below ear";	    
	    instructionWords[8] = "Upper left chin";
	    instructionWords[9] = "Upper right chin";	    
	    instructionWords[10] = "Mid left chin";
	    instructionWords[11] = "Mid right chin";
	    instructionWords[12] = "Bottom middle chin";
	    instructionWords[13] = "Centre of left eye";
	    instructionWords[14] = "Centre of right eye";
	    instructionWords[15] = "Inner left eyebrow";
	    instructionWords[16] = "Inner right eyebrow";
	    instructionWords[17] = "Middle left eyebrow";
	    instructionWords[18] = "Middle right eyebrow";
	    instructionWords[19] = "Outer left eyebrow";
	    instructionWords[20] = "Outer right eyebrow";
	    instructionWords[21] = "Centre of upper nose";
	    instructionWords[22] = "Center directly below nose";
	    instructionWords[23] = "Left directly below nose";
	    instructionWords[24] = "Right directly below nose";
	    instructionWords[25] = "Left of lip";
	    instructionWords[26] = "Right of lip";
	    instructionWords[27] = "Centre above lip";
	    instructionWords[28] = "Center below lip";
	    instructionWords[29] = "Left of centre below lip";
	    instructionWords[30] = "Right of centre below lip";
	    instructionWords[31] = "Bottom left ear";
	    instructionWords[32] = "Bottom right ear";
	    instructionWords[33] = "Outer left ear";
	    instructionWords[34] = "Outer right ear";
	    instructionWords[35] = "Upper left ear";
	    instructionWords[36] = "Upper right ear";
	    instructionWords[37] = "Left mid-upper head";
	    instructionWords[38] = "Right mid-upper head";
	    instructionWords[39] = "Left upper head";
	    instructionWords[40] = "Right upper head";
	    
	    colors[0] = Color.RED;
	    colors[1] = Color.CYAN;
	    colors[2] = Color.GREEN;
	    colors[3] = Color.MAGENTA;
	    colors[4] = Color.BLUE;
	    colors[5] = Color.PINK;
	    colors[6] = alternateGreen;
	    colors[7] = Color.YELLOW;
	    colors[8] = alternatePurple;
	    colors[9] = Color.ORANGE;
	    colors[10] = Color.RED;
	    colors[11] = Color.CYAN;
	    colors[12] = Color.GREEN;
	    colors[13] = Color.MAGENTA;
	    colors[14] = Color.BLUE;
	    colors[15] = Color.PINK;
	    colors[16] = alternateGreen;
	    colors[17] = Color.YELLOW;
	    colors[18] = alternatePurple;
	    colors[19] = Color.ORANGE;	    
	    colors[20] = Color.RED;
	    colors[21] = Color.CYAN;
	    colors[22] = Color.GREEN;
	    colors[23] = Color.MAGENTA;
	    colors[24] = Color.BLUE;
	    colors[25] = Color.PINK;
	    colors[26] = alternateGreen;
	    colors[27] = Color.YELLOW;
	    colors[28] = alternatePurple;
	    colors[29] = Color.ORANGE;
	    colors[30] = Color.RED;
	    colors[31] = Color.CYAN;
	    colors[32] = Color.GREEN;
	    colors[33] = Color.MAGENTA;
	    colors[34] = Color.BLUE;
	    colors[35] = Color.PINK;
	    colors[36] = alternateGreen;
	    colors[37] = Color.YELLOW;
	    colors[38] = alternatePurple;
	    colors[39] = Color.ORANGE;
	    colors[40] = Color.RED;
		
		faceImages[0] = new ImageIcon(new ImageIcon(getClass().getResource("HumanFace1.jpg")).getImage().getScaledInstance(150, 150,  java.awt.Image.SCALE_SMOOTH));
		faceImages[1] = new ImageIcon(new ImageIcon(getClass().getResource("HumanFace2.jpg")).getImage().getScaledInstance(150, 150,  java.awt.Image.SCALE_SMOOTH));
		faceImages[2] = new ImageIcon(new ImageIcon(getClass().getResource("HumanFace3.jpg")).getImage().getScaledInstance(150, 150,  java.awt.Image.SCALE_SMOOTH));
		faceImages[3] = new ImageIcon(new ImageIcon(getClass().getResource("HumanFace4.jpg")).getImage().getScaledInstance(150, 150,  java.awt.Image.SCALE_SMOOTH));
		faceImages[4] = new ImageIcon(new ImageIcon(getClass().getResource("HumanFace5.jpg")).getImage().getScaledInstance(150, 150,  java.awt.Image.SCALE_SMOOTH));
		faceImages[5] = new ImageIcon(new ImageIcon(getClass().getResource("HumanFace6.jpg")).getImage().getScaledInstance(150, 150,  java.awt.Image.SCALE_SMOOTH));
		faceImages[6] = new ImageIcon(new ImageIcon(getClass().getResource("HumanFace7.jpg")).getImage().getScaledInstance(150, 150,  java.awt.Image.SCALE_SMOOTH));
		faceImages[7] = new ImageIcon(new ImageIcon(getClass().getResource("HumanFace8.jpg")).getImage().getScaledInstance(150, 150,  java.awt.Image.SCALE_SMOOTH));
		faceImages[8] = new ImageIcon(new ImageIcon(getClass().getResource("HumanFace9.jpg")).getImage().getScaledInstance(150, 150,  java.awt.Image.SCALE_SMOOTH));
		faceImages[9] = new ImageIcon(new ImageIcon(getClass().getResource("HumanFace10.jpg")).getImage().getScaledInstance(150, 150,  java.awt.Image.SCALE_SMOOTH));
		faceImages[10] = new ImageIcon(new ImageIcon(getClass().getResource("HumanFace11.jpg")).getImage().getScaledInstance(150, 150,  java.awt.Image.SCALE_SMOOTH));
		faceImages[11] = new ImageIcon(new ImageIcon(getClass().getResource("HumanFace12.jpg")).getImage().getScaledInstance(150, 150,  java.awt.Image.SCALE_SMOOTH));
		faceImages[12] = new ImageIcon(new ImageIcon(getClass().getResource("HumanFace13.jpg")).getImage().getScaledInstance(150, 150,  java.awt.Image.SCALE_SMOOTH));
		faceImages[13] = new ImageIcon(new ImageIcon(getClass().getResource("HumanFace14.jpg")).getImage().getScaledInstance(150, 150,  java.awt.Image.SCALE_SMOOTH));
		faceImages[14] = new ImageIcon(new ImageIcon(getClass().getResource("HumanFace15.jpg")).getImage().getScaledInstance(150, 150,  java.awt.Image.SCALE_SMOOTH));
		faceImages[15] = new ImageIcon(new ImageIcon(getClass().getResource("HumanFace16.jpg")).getImage().getScaledInstance(150, 150,  java.awt.Image.SCALE_SMOOTH));
		faceImages[16] = new ImageIcon(new ImageIcon(getClass().getResource("HumanFace17.jpg")).getImage().getScaledInstance(150, 150,  java.awt.Image.SCALE_SMOOTH));
		faceImages[17] = new ImageIcon(new ImageIcon(getClass().getResource("HumanFace18.jpg")).getImage().getScaledInstance(150, 150,  java.awt.Image.SCALE_SMOOTH));		
		faceImages[18] = new ImageIcon(new ImageIcon(getClass().getResource("HumanFace19.jpg")).getImage().getScaledInstance(150, 150,  java.awt.Image.SCALE_SMOOTH));
		faceImages[19] = new ImageIcon(new ImageIcon(getClass().getResource("HumanFace20.jpg")).getImage().getScaledInstance(150, 150,  java.awt.Image.SCALE_SMOOTH));
		faceImages[20] = new ImageIcon(new ImageIcon(getClass().getResource("HumanFace21.jpg")).getImage().getScaledInstance(150, 150,  java.awt.Image.SCALE_SMOOTH));
		faceImages[21] = new ImageIcon(new ImageIcon(getClass().getResource("HumanFace22.jpg")).getImage().getScaledInstance(150, 150,  java.awt.Image.SCALE_SMOOTH));
		faceImages[22] = new ImageIcon(new ImageIcon(getClass().getResource("HumanFace23.jpg")).getImage().getScaledInstance(150, 150,  java.awt.Image.SCALE_SMOOTH));
		faceImages[23] = new ImageIcon(new ImageIcon(getClass().getResource("HumanFace24.jpg")).getImage().getScaledInstance(150, 150,  java.awt.Image.SCALE_SMOOTH));
		faceImages[24] = new ImageIcon(new ImageIcon(getClass().getResource("HumanFace25.jpg")).getImage().getScaledInstance(150, 150,  java.awt.Image.SCALE_SMOOTH));
		faceImages[25] = new ImageIcon(new ImageIcon(getClass().getResource("HumanFace26.jpg")).getImage().getScaledInstance(150, 150,  java.awt.Image.SCALE_SMOOTH));
		faceImages[26] = new ImageIcon(new ImageIcon(getClass().getResource("HumanFace27.jpg")).getImage().getScaledInstance(150, 150,  java.awt.Image.SCALE_SMOOTH));
		faceImages[27] = new ImageIcon(new ImageIcon(getClass().getResource("HumanFace28.jpg")).getImage().getScaledInstance(150, 150,  java.awt.Image.SCALE_SMOOTH));
		faceImages[28] = new ImageIcon(new ImageIcon(getClass().getResource("HumanFace29.jpg")).getImage().getScaledInstance(150, 150,  java.awt.Image.SCALE_SMOOTH));
		faceImages[29] = new ImageIcon(new ImageIcon(getClass().getResource("HumanFace30.jpg")).getImage().getScaledInstance(150, 150,  java.awt.Image.SCALE_SMOOTH));
		faceImages[30] = new ImageIcon(new ImageIcon(getClass().getResource("HumanFace31.jpg")).getImage().getScaledInstance(150, 150,  java.awt.Image.SCALE_SMOOTH));
		faceImages[31] = new ImageIcon(new ImageIcon(getClass().getResource("HumanFace32.jpg")).getImage().getScaledInstance(150, 150,  java.awt.Image.SCALE_SMOOTH));
		faceImages[32] = new ImageIcon(new ImageIcon(getClass().getResource("HumanFace33.jpg")).getImage().getScaledInstance(150, 150,  java.awt.Image.SCALE_SMOOTH));
		faceImages[33] = new ImageIcon(new ImageIcon(getClass().getResource("HumanFace34.jpg")).getImage().getScaledInstance(150, 150,  java.awt.Image.SCALE_SMOOTH));
		faceImages[34] = new ImageIcon(new ImageIcon(getClass().getResource("HumanFace35.jpg")).getImage().getScaledInstance(150, 150,  java.awt.Image.SCALE_SMOOTH));
		faceImages[35] = new ImageIcon(new ImageIcon(getClass().getResource("HumanFace36.jpg")).getImage().getScaledInstance(150, 150,  java.awt.Image.SCALE_SMOOTH));
		faceImages[36] = new ImageIcon(new ImageIcon(getClass().getResource("HumanFace37.jpg")).getImage().getScaledInstance(150, 150,  java.awt.Image.SCALE_SMOOTH));
		faceImages[37] = new ImageIcon(new ImageIcon(getClass().getResource("HumanFace38.jpg")).getImage().getScaledInstance(150, 150,  java.awt.Image.SCALE_SMOOTH));
		faceImages[38] = new ImageIcon(new ImageIcon(getClass().getResource("HumanFace39.jpg")).getImage().getScaledInstance(150, 150,  java.awt.Image.SCALE_SMOOTH));
		faceImages[39] = new ImageIcon(new ImageIcon(getClass().getResource("HumanFace40.jpg")).getImage().getScaledInstance(150, 150,  java.awt.Image.SCALE_SMOOTH));
		faceImages[40] = new ImageIcon(new ImageIcon(getClass().getResource("HumanFace41.jpg")).getImage().getScaledInstance(150, 150,  java.awt.Image.SCALE_SMOOTH));
		
		getContentPane().setLayout(null);

		//1950, 1100
		
		setSize((int)newWidth, (int)newHeight);

		getContentPane().setBackground(panelColor);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		button1 = new JButton("Click me");
		button2 = new JButton("Click me");
		button1.setBounds((int)((double)(firstImageXStart)-5.0*width), (int)(300.0*height), (int)(100.0*width), (int)(30.0*height));
		button2.setBounds((int)((double)(secondImageXStart)-5.0*width), (int)(300.0*height), (int)(100.0*width), (int)(30.0*height));
		//button2.setBounds(secondImageXStart-5, 300, 100, 30);
		button1.addActionListener(this);
		button2.addActionListener(this);
		getContentPane().add(button1);
		getContentPane().add(button2);
		
		faceSketch = new ImageIcon(getClass().getResource("humanFaceexample.jpg"));
		Image image = faceSketch.getImage(); 
		Image newimg = image.getScaledInstance((int)(150.0*width), (int)(150.0*height),  java.awt.Image.SCALE_SMOOTH);  
		faceSketch = new ImageIcon(newimg);
		imageLabel = new JLabel(faceSketch);
		imageLabel.setBounds((int)(915.0*width),(int)(25.0*height), (int)(120.0*width), (int)(150.0*height));
		getContentPane().add(imageLabel);
		
		slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
		slider.setMajorTickSpacing(10);
		slider.setMinorTickSpacing(1);
		slider.setPaintLabels(true);
		slider.setPaintTicks(true);
		slider.setValue(50);
		slider.setBounds((int)(475.0*width), (int)(250.0*height), (int)(1000.0*width), (int)(40.0*height));
		slider.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				JSlider source = (JSlider) e.getSource();				
				ratio = (double)(source.getValue())/100;
				System.out.println(ratio);
			}
		});
		getContentPane().add(slider);
		slider.setVisible(false);
		
		instructions = new JLabel();
		instructions.setBounds((int)(835.0*width), (int)(190.0*height), (int)(280.0*width), (int)(20.0*height));
		getContentPane().add(instructions);
		
		boarder = new JPanel();
		//boarder.setBounds(835, 190, 280, 20);
		boarder.setBounds((int)(835.0*width), (int)(190.0*height), (int)(280.0*width), (int)(20.0*height));
		getContentPane().add(boarder);
		
		mash = new JButton("Begin!");
		mash.setBounds((int)(200.0*width), (int)(20.0*height), (int)(120*width), (int)(40.0*height));
		getContentPane().add(mash);
		mash.addActionListener(this);
		mash.setVisible(false);
		
		next = new JButton("Next");
		next.setBounds((int)(1650.0*width), (int)(60.0*height), (int)(120.0*width), (int)(80.0*height));
		getContentPane().add(next);
		next.addActionListener(this);
		next.setVisible(false);
		
		previous = new JButton("Previous");
		previous.setBounds((int)(200.0*width), (int)(60.0*height), (int)(120.0*width), (int)(80.0*height));
		getContentPane().add(previous);
		previous.addActionListener(this);
		previous.setVisible(false);
		
		morphImages = new JButton("Morph Images");
		morphImages.setBounds((int)(910.0*width), (int)(300.0*height) , (int)(140.0*width), (int)(40.0*height));
		getContentPane().add(morphImages);
		morphImages.addActionListener(this);
		morphImages.setVisible(false);
		
		filmStrip = new JButton("Film Strip");
		filmStrip.setBounds((int)(1060.0*width), (int)(50.0*height), (int)(140.0*width), (int)(30.0*height));
		getContentPane().add(filmStrip);
		filmStrip.addActionListener(this);
		filmStrip.setVisible(false);
		
		saver = new JButton("Save");
		saver.setBounds((int)(1060.0*width), (int)(100.0*height), (int)(140.0*width), (int)(30.0*height));
		getContentPane().add(saver);
		saver.addActionListener(this);
		saver.setVisible(false);
		
		boarderImage1Vert1.setBounds((int)((double)(firstImageXStart)-5*width), OriginalImageYcoordinate, (int)(5.0*width), (int)(600.0*height));
		getContentPane().add(boarderImage1Vert1);
		boarderImage1Vert2.setBounds((int)((double)(firstImageXStart)+500*width), OriginalImageYcoordinate, (int)(5.0*width), (int)(600.0*height));
		getContentPane().add(boarderImage1Vert2);
		boarderImage1Hori1.setBounds((int)((double)(firstImageXStart)-5*width), (int)((double)OriginalImageYcoordinate-5.0*height), (int)(510.0*height), (int)(5.0*width));
		getContentPane().add(boarderImage1Hori1);
		boarderImage1Hori2.setBounds((int)((double)(firstImageXStart)-5*width), (int)((double)OriginalImageYcoordinate+600.0*height), (int)(510.0*height), (int)(5.0*width));
		getContentPane().add(boarderImage1Hori2);
		
		boarderImage2Vert1.setBounds((int)((double)(secondImageXStart)-5*width), OriginalImageYcoordinate, (int)(5.0*width), (int)(600.0*height));
		getContentPane().add(boarderImage2Vert1);
		boarderImage2Vert2.setBounds((int)((double)(secondImageXStart)+500*width), OriginalImageYcoordinate, (int)(5.0*width), (int)(600.0*height));
		getContentPane().add(boarderImage2Vert2);
		boarderImage2Hori1.setBounds((int)((double)(secondImageXStart)-5*width),  (int)((double)OriginalImageYcoordinate-5.0*height), (int)(510.0*height), (int)(5.0*width));
		getContentPane().add(boarderImage2Hori1);
		boarderImage2Hori2.setBounds((int)((double)(secondImageXStart)-5*width), (int)((double)OriginalImageYcoordinate+600.0*height), (int)(510.0*height), (int)(5.0*width));
		getContentPane().add(boarderImage2Hori2);
			    
	    for (int i = 0; i < spaceToHold; i++){
	    	hasBeenTraversed[i] = false;
	    }
	    for (int i = 0; i < spaceToHold; i++){
	    	has1BeenModified[i] = false;
	    }
	    for (int i = 0; i < spaceToHold; i++){
	    	has2BeenModified[i] = false;
	    }
	    	    	    
	    for (int i = 0; i < isTheLimit.length; i++){
	    	isTheLimit[i] = false;
	    }	  
	}
	
	public void actionPerformed(ActionEvent e){
		if (e.getSource() == button1){
			
			System.out.println(System.getProperty("user.dir"));

			//drawBoarder();
			if (meshSelected == 0){
				JFileChooser chooser = new JFileChooser();
				chooser.showOpenDialog(null);
				File file = chooser.getSelectedFile();
				Path filePath = Paths.get(file.getAbsolutePath());
				System.out.println(filePath);
				try {
					contentType = Files.probeContentType(filePath);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				if ((Objects.equals("image/jpeg", contentType))||(Objects.equals("image/jpg", contentType))){
		    	
					//System.out.println("goodbye");

					Image picture;
					try {
						picture = ImageIO.read(file);
						icon = new ImageIcon(picture);
						
						if (icon.getIconWidth() < (int)(501.0*width) && icon.getIconHeight() < (int)(601.0*height)){
							
							//System.out.println((int)(501.0*width) + " " + (int)(601.0*height));
						
							if (label1 != null){
								getContentPane().remove(label1);
								getContentPane().revalidate();
								getContentPane().repaint();
							}
						
							label1 = new JLabel(icon);
							label1.setBounds(firstImageXStart, OriginalImageYcoordinate, icon.getIconWidth(), icon.getIconHeight());
							getContentPane().add(label1);
						
							image1Xmin = firstImageXStart;
							image1Xmax = firstImageXStart + icon.getIconWidth();
							image1Ymin = imagesYStart;
							image1Ymax = imagesYStart + icon.getIconHeight();
					
							firstSelected = 1;
					
							ImageFile1 = new File(filePath.toString());
							try {
								Image1 = ImageIO.read(ImageFile1);
								System.out.println("Image 1: " + Image1.getWidth() + " " + Image1.getHeight());
							} 
							catch (IOException e1) {
								e1.printStackTrace();
							}
				
							if (secondSelected == 1){
								mash.setVisible(true);	
							}
				
							label1.addMouseListener(new MouseAdapter() { 
								public void mousePressed(MouseEvent me) { 
									if (meshSelected == 1){
										if (me.getX()+firstImageXStart > firstImageXStart + (int)(5.0*width) && me.getX()+firstImageXStart < firstImageXStart-(int)(5.0*width) + icon.getIconWidth() && me.getY() + imagesYStart-(int)(3.0*height) > imagesYStart + (int)(7.0*height) && me.getY() + imagesYStart-(int)(3.0*height) < imagesYStart - (int)(8.0*height) + icon.getIconHeight()){
											graphics = getGraphics();
											save1X = me.getX()+firstImageXStart;
											save1Y = me.getY()+imagesYStart - (int)(3.0*height);
											System.out.println(save1X + " " + save1Y);
											has1BeenModified[numberStep] = true;
											paintComponent(graphics);
											image1pressed = true;
										}
										if (image1pressed == true && image2pressed == true){
											if (numberStep != 3){
												next.setVisible(true);
											}	
											else{
												morphImages.setVisible(true);
											}
										}
									}
								} 
							});
							repaint();
						} 
					}
					catch (IOException e2) {
						e2.printStackTrace();
					}
				}
			}
		}
		else if (e.getSource() == button2){
			if (meshSelected ==0){
				JFileChooser chooser = new JFileChooser();
				chooser.showOpenDialog(null);
				File file = chooser.getSelectedFile();
				Path filePath = Paths.get(file.getAbsolutePath());
				try {
					contentType = Files.probeContentType(filePath);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				if (Objects.equals("image/jpeg", contentType)){
					try{
						Image picture2 = ImageIO.read(file);
						final ImageIcon icon2 = new ImageIcon(picture2);

						if (icon2.getIconWidth() < (int)(501.0*width) && icon2.getIconHeight() < (int)(601.0*height)){
						
							if (label2 != null){
								getContentPane().remove(label2);
								getContentPane().revalidate();
								getContentPane().repaint();
							}
							
							label2 = new JLabel(icon2);
							label2.setBounds(secondImageXStart, OriginalImageYcoordinate, icon2.getIconWidth(), icon2.getIconHeight());
							getContentPane().add(label2);
						
							image2Xmin = secondImageXStart;
							image2Xmax = secondImageXStart + icon2.getIconWidth();
							image2Ymin = imagesYStart;
							image2Ymax = imagesYStart + icon2.getIconHeight();
					
							secondSelected = 1;
						
							ImageFile2 = new File(filePath.toString());
							try {
								Image2 = ImageIO.read(ImageFile2);
								System.out.println("Image 2: " + Image2.getWidth() + " " + Image2.getHeight());
							} 	
							catch (IOException e1) {
								e1.printStackTrace();
							}
					
							if (firstSelected == 1){
								mash.setVisible(true);
							}
				
							label2.addMouseListener(new MouseAdapter() { 
								public void mousePressed(MouseEvent me) { 
									if (meshSelected == 1){
										if (me.getX()+secondImageXStart > secondImageXStart + (int)(5.0*width) && me.getX()+secondImageXStart < secondImageXStart - (int)(5.0*width) + icon2.getIconWidth() && me.getY() + imagesYStart - (int)(3.0*height) > imagesYStart + (int)(8.0*height) && me.getY() + imagesYStart - (int)(3.0*height) < imagesYStart - (int)(8.0*height) + icon2.getIconHeight()){
											graphics = getGraphics();
											save2X = me.getX()+secondImageXStart;
											save2Y = me.getY()+imagesYStart - (int)(3.0*height);
											//System.out.println("//////////////////////////////////////" + save2X + " " + save2Y);
											has2BeenModified[numberStep] = true;
											paintComponent(graphics);
											image2pressed = true;
										}
										if (image1pressed == true && image2pressed == true){
											if (numberStep != 3){
												next.setVisible(true);
											}
											else{
												morphImages.setVisible(true);
											}
										}
									}
								} 
							});
							repaint();
						}
					}
					catch (IOException e2) {
						e2.printStackTrace();
					}
					
				}
		    }
		}
		else if (e.getSource() == mash){;
			getContentPane().remove(imageLabel);
			imageLabel = new JLabel(faceImages[1]);
			imageLabel.setBounds((int)(915.0*width), (int)(25.0*height), (int)(120.0*width), (int)(150.0*height));
			getContentPane().add(imageLabel);
			button1.setVisible(false);
			button2.setVisible(false);
			mash.setVisible(false);
			meshSelected = 1;
			numberStep = 0;
			instructions.setText(instructionWords[numberStep]);
		}
		else if (e.getSource() == next){
			if (numberStep < 40){
				getContentPane().remove(imageLabel);
				imageLabel = new JLabel(faceImages[numberStep+1]);
				imageLabel.setBounds((int)(915.0*width), (int)(25.0*height), (int)(120.0*width), (int)(150.0*height));
				getContentPane().add(imageLabel);
			}
			previous.setVisible(true);
			if (has1BeenModified[numberStep] == true){
				image1PointX[numberStep] = save1X;
				image1PointY[numberStep] = save1Y;
			}
			if (has2BeenModified[numberStep] == true){
				image2PointX[numberStep] = save2X;
				image2PointY[numberStep] = save2Y;	
			}
			if (hasBeenTraversed[numberStep] == false){
				next.setVisible(false);
				highestTraverse = numberStep+1;
			}
			if (isTheLimit[numberStep] == true){
				next.setVisible(false);
			}
			has1BeenModified[numberStep] = false;
			has2BeenModified[numberStep] = false;
			hasBeenTraversed[numberStep] = true;
			numberStep = numberStep+1;
		    instructions.setText(instructionWords[numberStep]);
		    image1pressed = false;
		    image2pressed = false;
		    save1X = 0;
		    save1Y = 0;
		    save2X = 0;
		    save2Y = 0;
		}
		else if (e.getSource() == previous){	
			
			getContentPane().remove(imageLabel);
			imageLabel = new JLabel(faceImages[numberStep-1]);
			imageLabel.setBounds((int)(915.0*width), (int)(25.0*height), (int)(120.0*width), (int)(150.0*height));
			getContentPane().add(imageLabel);
			previous.setVisible(true);
			next.setVisible(true);
			if (has1BeenModified[numberStep] == true){
				image1PointX[numberStep] = save1X;
				image1PointY[numberStep] = save1Y;
			}
			if (has2BeenModified[numberStep] == true){
				image2PointX[numberStep] = save2X;
				image2PointY[numberStep] = save2Y;	
			}
			has1BeenModified[numberStep] = false;
			has2BeenModified[numberStep] = false;
			
			if (hasBeenTraversed[numberStep+1] == false){
				isTheLimit[numberStep] = true;
			}
			else{
				isTheLimit[numberStep] = false;
			}
			numberStep = numberStep - 1;
		    instructions.setText(instructionWords[numberStep]);
		    save1X = 0;
		    save1Y = 0;
		    save2X = 0;
		    save2Y = 0;			
		    if (numberStep == 0){
				previous.setVisible(false);
			}
		}
		else if (e.getSource() == morphImages){
			
			previous.setVisible(false);
			filmStrip.setVisible(true);
	
			numberPoints = 8;
			
			int smallX = 0;
			int bigX = 0;
			int smallY = 0;
			int bigY = 0;
			
			if (image1Xmax - image1Xmin > image2Xmax - image2Xmin){
				smallX = (int) image1Xmin;
				bigX = (int) image1Xmax;
			}
			else{
				smallX = (int) image2Xmin;
				bigX = (int) image2Xmax;
			}
			
			if (image1Ymax - image1Ymin > image2Ymax - image2Ymin){
				smallY = (int) image1Ymin;
				bigY = (int) image1Ymax;
			}
			else{
				smallY = (int) image2Ymin;
				bigY = (int) image2Ymax;
			}			
			
			int lowerX = (int)((image1Xmax + image2Xmin)/2 -(bigX-smallX)/2);
			int upperX = (int)((image1Xmax + image2Xmin)/2 +(bigX-smallX)/2);

			
			for (int v = lowerX; v < upperX+30; v++){
				for (int w = smallY; w < bigY+50; w++){
					paintComponent(v, w, graphics, panelColor, 0, 0, 0, 0, 0);
				}
			}
			
			Delaunay_Triangulation dt3 = new Delaunay_Triangulation();
			
			int Xposition = (int) (image1Xmin*0.5+image2Xmin*0.5+1.0);
			int Yposition = (int) (image1Ymin*0.5+image2Ymin*0.5+1.0);
			
			int Xmin = (int) (image1Xmin*(1.0-ratio)+image2Xmin*ratio+1.0);
			int Xmax = (int) (image1Xmax*(1.0-ratio)+image2Xmax*ratio-1.0);
			int Ymin = (int) (image1Ymin*(1.0-ratio)+image2Ymin*ratio+1.0);
			int Ymax = (int) (image1Ymax*(1.0-ratio)+image2Ymax*ratio-1.0);		
			
			imageOutput = new BufferedImage(Xmax-Xmin, Ymax-Ymin, BufferedImage.TYPE_INT_ARGB);
		
			if (has1BeenModified[numberStep] == true){
				image1PointX[numberStep] = save1X;
				image1PointY[numberStep] = save1Y;
			}
			if (has2BeenModified[numberStep] == true){
				image2PointX[numberStep] = save2X;
				image2PointY[numberStep] = save2Y;	
			}
			
			image1PointX[4] = (int)image1Xmin + 1;
			image1PointX[5] = (int)image1Xmax - 1;
			image1PointX[6] = (int)image1Xmin + 1;
			image1PointX[7] = (int)image1Xmax - 1;
			
			image1PointY[4] = (int)image1Ymin + 1;
			image1PointY[5] = (int)image1Ymin + 1;
			image1PointY[6] = (int)image1Ymax - 1;
			image1PointY[7] = (int)image1Ymax - 1;
			
			image2PointX[4] = (int)image2Xmin + 1;
			image2PointX[5] = (int)image2Xmax - 1;
			image2PointX[6] = (int)image2Xmin + 1;
			image2PointX[7] = (int)image2Xmax - 1;
			
			image2PointY[4] = (int)image2Ymin + 1;
			image2PointY[5] = (int)image2Ymin + 1;
			image2PointY[6] = (int)image2Ymax - 1;
			image2PointY[7] = (int)image2Ymax - 1;
			
			morphingTime = true;
			
			Delaunay_Triangulation dt1 = new Delaunay_Triangulation();
			Delaunay_Triangulation dt2 = new Delaunay_Triangulation();
			
			for (int i = 0; i < numberPoints+1; i++){
				dt1.insertPoint(new Point_dt(image1PointX[i], image1PointY[i]));
				dt2.insertPoint(new Point_dt(image2PointX[i], image2PointY[i]));				
			}	
			
			DoublePair[] CoordsImage3 = new DoublePair[numberPoints+1];
			double[] image3PointX = new double[1000];
			double[] image3PointY = new double[1000];
			
			for (int i = 0; i < dt1.size(); i++){
				dt3.insertPoint(new Point_dt(image1PointX[i]*(1.0-ratio) + image2PointX[i]*ratio, image1PointY[i]*(1.0-ratio) + image2PointY[i]*ratio));
				CoordsImage3[i] = new DoublePair(image1PointX[i]*(1.0-ratio) + image2PointX[i]*ratio, image1PointY[i]*(1.0-ratio) + image2PointY[i]*ratio);
				image3PointX[i] = image1PointX[i]*(1.0-ratio) + image2PointX[i]*ratio;
				image3PointY[i] = image1PointY[i]*(1.0-ratio) + image2PointY[i]*ratio;
			}	
		
			Iterator<Triangle_dt> iterator3 = dt3.trianglesIterator();
			counter = 0;
		
			while (iterator3.hasNext()) {
				Triangle_dt curr3 = iterator3.next();
				if (!curr3.isHalfplane()) {
					triangleList3[counter] = curr3;
					counter++;
				}
			}	
			
			Point_dt pointHolderI1 = new Point_dt(0,0);
			Point_dt pointHolderI2 = new Point_dt(0,0);
			Point_dt pointHolderI3 = new Point_dt(0,0);
			Point_dt pointHolderII1 = new Point_dt(0,0);
			Point_dt pointHolderII2 = new Point_dt(0,0);
			Point_dt pointHolderII3 = new Point_dt(0,0);
			
			for (int i = 0; i < counter; i++){
				for (int j = 0; j < numberPoints+1; j++){
					if (triangleList3[i].p1().x() == image3PointX[j] && triangleList3[i].p1().y() == image3PointY[j]){
						pointHolderI1 = new Point_dt(image1PointX[j], image1PointY[j]);
						pointHolderII1 = new Point_dt(image2PointX[j], image2PointY[j]);
					}
					if (triangleList3[i].p2().x() == image3PointX[j] && triangleList3[i].p2().y() == image3PointY[j]){
						pointHolderI2 = new Point_dt(image1PointX[j], image1PointY[j]);
						pointHolderII2 = new Point_dt(image2PointX[j], image2PointY[j]);
					}
					if (triangleList3[i].p3().x() == image3PointX[j] && triangleList3[i].p3().y() == image3PointY[j]){
						pointHolderI3 = new Point_dt(image1PointX[j], image1PointY[j]);
						pointHolderII3 = new Point_dt(image2PointX[j], image2PointY[j]);
					}	
				}
				triangleList1[i] = new Triangle_dt (pointHolderI1, pointHolderI2, pointHolderI3);
				triangleList2[i] = new Triangle_dt (pointHolderII1, pointHolderII2, pointHolderII3);
			}
			
			double Barry1 = 0;
			double Barry2 = 0;
			double Barry3 = 0;		
				
			double x1 = 0;
			double x2 = 0;
			double x3 = 0;
			double y1 = 0;
			double y2 = 0;
			double y3 = 0;
			
			int a1 = 0;
			int a2 = 0;
			int b1 = 0;
			int b2 = 0;
			
			midPoint = (int)(975.0*width) - ((Xmax - Xmin)/2 + Xposition);
			
			System.out.println("****************************************");
			System.out.println(Xmin + " " + Ymin);
			System.out.println("****************************************");
						
			for (int a = Xmin; a < Xmax; a++){
				for (int b = Ymin; b < Ymax; b++){
					Point_dt point = new Point_dt(a,b);
					Triangle_dt aTriangle = dt3.find(point);
					if(dt3.find(point).p3() != null){
						for (int z = 0; z < counter; z++){
							if (dt3.find(point).p1().x() == triangleList3[z].p1().x() && dt3.find(point).p1().y() == triangleList3[z].p1().y() && 
									dt3.find(point).p2().x() == triangleList3[z].p2().x() && dt3.find(point).p2().y() == triangleList3[z].p2().y() &&
										dt3.find(point).p3().x() == triangleList3[z].p3().x() && dt3.find(point).p3().y() == triangleList3[z].p3().y()){
								
								x1 = aTriangle.p1().x();	
								x2 = aTriangle.p2().x();
								x3 = aTriangle.p3().x();
								y1 = aTriangle.p1().y();
								y2 = aTriangle.p2().y();
								y3 = aTriangle.p3().y();
							
								Barry1 = ((y2-y3)*(a-x3) + (x3-x2)*(b-y3))/((y2-y3)*(x1-x3)+(x3-x2)*(y1-y3));
								Barry2 = ((y3-y1)*(a-x3) + (x1-x3)*(b-y3))/((y2-y3)*(x1-x3)+(x3-x2)*(y1-y3));
								Barry3 = 1 - Barry1 - Barry2;
																				
								a1 = (int)(triangleList1[z].p1().x()*Barry1 + triangleList1[z].p2().x()*Barry2 + triangleList1[z].p3().x()*Barry3);
								b1 = (int)(triangleList1[z].p1().y()*Barry1 + triangleList1[z].p2().y()*Barry2 + triangleList1[z].p3().y()*Barry3);
								a2 = (int)(triangleList2[z].p1().x()*Barry1 + triangleList2[z].p2().x()*Barry2 + triangleList2[z].p3().x()*Barry3);
								b2 = (int)(triangleList2[z].p1().y()*Barry1 + triangleList2[z].p2().y()*Barry2 + triangleList2[z].p3().y()*Barry3);
								
								//System.out.println(a + " " + b + " " + a1 + " " + b1 + " " + a2 + " " + b2);
								
								System.out.println(a + " " + b);
															
								int clr1=  Image1.getRGB(a1-(int)(130.0*width), b1-(int)(378.0*height)); 
								int clr2=  Image2.getRGB(a2-(int)(1320.0*width), b2-(int)(378.0*height));
																			
								int  red1   = (clr1 & 0x00ff0000) >> 16;
								int  green1 = (clr1 & 0x0000ff00) >> 8;
								int  blue1  =  clr1 & 0x000000ff;
							
								int  red2   = (clr2 & 0x00ff0000) >> 16;
								int  green2 = (clr2 & 0x0000ff00) >> 8;
								int  blue2  =  clr2 & 0x000000ff;
							
								int red = (int)(red1*(1.0-ratio) + red2*ratio);
								int green = (int)(green1*(1.0-ratio) + green2*ratio);
								int blue = (int)(blue1*(1.0-ratio) + blue2*ratio);
								
								Color newColour = new Color(red, green, blue);
								
								graphics = getGraphics();
								
								paintComponent(a, b, graphics, newColour, Xmin, Ymin, Xposition, Yposition, midPoint);
								
								imageOutput.setRGB(a-Xmin, b-Ymin, newColour.getRGB());
							}
						}
					}
				}
			}
			slider.setVisible(true);
			saver.setVisible(true);
		}
		else if (e.getSource() == saver){
			File outputfile = new File("saved.png");
		    try {
				ImageIO.write(imageOutput, "png", outputfile);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if (e.getSource() == filmStrip){
			displayFrame = new JFrame();            
			//displayFrame.setBackground(panelColor);
			displayFrame.setLayout(null);
            displayFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            displayFrame.setSize((int)newWidth, (int)newHeight);
			displayFrame.setVisible(true);
			
			fiveTimes = new JButton("20% Intevals");
			fiveTimes.setBounds((int)(10.0*width), (int)(10.0*height), (int)(200.0*width), (int)(50.0*height));
			fiveTimes.addActionListener(this);
			displayFrame.add(fiveTimes);
						
			tenTimes = new JButton("10% Intervals");
			tenTimes.setBounds((int)(300.0*width), (int)(10.0*height), (int)(200.0*width), (int)(50.0*height));
			tenTimes.addActionListener(this);
			displayFrame.add(tenTimes);
			
			twentyTimes = new JButton("5% Intervals");
			twentyTimes.setBounds((int)(590.0*width), (int)(10.0*height), (int)(200.0*width), (int)(50.0*height));
			twentyTimes.addActionListener(this);
			displayFrame.add(twentyTimes);
			
			stripSaver = new JButton("save");
			stripSaver.setBounds((int)(1500.0*width), (int)(10.0*height), (int)(400.0*width), (int)(50.0*height));
			stripSaver.addActionListener(this);
			displayFrame.add(stripSaver);
		}
		else if (e.getSource() == fiveTimes || e.getSource() == tenTimes || e.getSource() == twentyTimes){
			
			numberPoints = 44;
			
			Graphics graphicsar = displayFrame.getGraphics();
			
			Color background = displayFrame.getBackground();
			
			for (int i = 0; i < newWidth; i++){
				for (int j = 0; j < newHeight; j++ ){
					paintComponent(i, j, 0, graphicsar, background);
				}
			}
			
			positionAdOn = 0;
			
			if (e.getSource() == fiveTimes){
				XratioTemp = (int)(310.0*width);
				numberCuts = 6;
				ratioDivisour = 5.0;
			}
			else if (e.getSource() == tenTimes){
				XratioTemp = (int)(170.0*width);
				numberCuts = 11;
				ratioDivisour = 10.0;
			}
			else{
				XratioTemp = (int)(89.0*width);
				numberCuts = 21;
				ratioDivisour = 20.0;
			}
			
			double Xsmall = (int) (image1Xmin*0.5+image2Xmin*0.5+1.0);
			double Xbig = (int) (image1Xmax*0.5+image2Xmax*0.5-1.0);
			double Ysmall = (int) (image1Ymin*0.5+image2Ymin*0.5+1.0); 
			double Ybig = (int) (image1Ymax*0.5+image2Ymax*0.5-1.0);
						
			Yratio = (int) (XratioTemp*(Ybig-Ysmall)/(Xbig-Xsmall));
						
			for (int o = 0; o < numberCuts; o++){
				
				ratio = (double)o/(ratioDivisour);
			
				image1PointX[41] = (int)image1Xmin + 1;
				image1PointX[42] = (int)image1Xmax - 1;
				image1PointX[43] = (int)image1Xmin + 1;
				image1PointX[44] = (int)image1Xmax - 1;
			
				image1PointY[41] = (int)image1Ymin + 1;
				image1PointY[42] = (int)image1Ymin + 1;
				image1PointY[43] = (int)image1Ymax - 1;
				image1PointY[44] = (int)image1Ymax - 1;
			
				image2PointX[41] = (int)image2Xmin + 1;
				image2PointX[42] = (int)image2Xmax - 1;
				image2PointX[43] = (int)image2Xmin + 1;
				image2PointX[44] = (int)image2Xmax - 1;
			
				image2PointY[41] = (int)image2Ymin + 1;
				image2PointY[42] = (int)image2Ymin + 1;
				image2PointY[43] = (int)image2Ymax - 1;
				image2PointY[44] = (int)image2Ymax - 1;
			
				filmStrip.setVisible(true);
				morphImages.setVisible(true);
				slider.setVisible(true);			
				previous.setVisible(false);
			
				morphingTime = true;
			
				if (image1Xmax - image1Xmin > image2Xmax - image2Xmin){
					smallX = (int) image1Xmin;
					bigX = (int) image1Xmax;
				}
				else{
					smallX = (int) image2Xmin;
					bigX = (int) image2Xmax;
				}
			
				if (image1Ymax - image1Ymin > image2Ymax - image2Ymin){
					smallY = (int) image1Ymin;
					bigY = (int) image1Ymax;
				}
				else{
					smallY = (int) image2Ymin;
					bigY = (int) image2Ymax;
				}			
			
				lowerX = (int)((image1Xmax + image2Xmin)/2 -(bigX-smallX)/2);
				upperX = (int)((image1Xmax + image2Xmin)/2 +(bigX-smallX)/2);

				/*for (int v = (int)(636.0*width); v < (int)(1315.0*width); v++){
					for (int w = smallY; w < bigY+20; w++){
						paintComponent(v, w, graphics, panelColor, 0, 0, 0, 0, 0);
					}
				}*/
			
				Xposition = (int) (image1Xmin*0.5+image2Xmin*0.5+1.0);
				Yposition = (int) (image1Ymin*0.5+image2Ymin*0.5+1.0);
			
				Xmin = (int) (image1Xmin*(1.0-ratio)+image2Xmin*ratio+1.0);
				Xmax = (int) (image1Xmax*(1.0-ratio)+image2Xmax*ratio-1.0);
				Ymin = (int) (image1Ymin*(1.0-ratio)+image2Ymin*ratio+1.0);
				Ymax = (int) (image1Ymax*(1.0-ratio)+image2Ymax*ratio-1.0);
				
				anImage = new BufferedImage(Xmax-Xmin, Ymax-Ymin, BufferedImage.TYPE_INT_ARGB);
						
				Delaunay_Triangulation dt1 = new Delaunay_Triangulation();
				Delaunay_Triangulation dt2 = new Delaunay_Triangulation();
				Delaunay_Triangulation dt3 = new Delaunay_Triangulation();
				
				graphics = getGraphics();

				for (int i = 0; i < numberPoints+1; i++){
					dt1.insertPoint(new Point_dt(image1PointX[i], image1PointY[i]));
					dt2.insertPoint(new Point_dt(image2PointX[i], image2PointY[i]));
				}		
			
				for (int i = 0; i < dt1.size(); i++){
					dt3.insertPoint(new Point_dt(image1PointX[i]*(1.0-ratio) + image2PointX[i]*ratio, image1PointY[i]*(1.0-ratio) + image2PointY[i]*ratio));
					CoordsImage3[i] = new DoublePair(image1PointX[i]*(1.0-ratio) + image2PointX[i]*ratio, image1PointY[i]*(1.0-ratio) + image2PointY[i]*ratio);
					image3PointX[i] = image1PointX[i]*(1.0-ratio) + image2PointX[i]*ratio;
					image3PointY[i] = image1PointY[i]*(1.0-ratio) + image2PointY[i]*ratio;
				}		
		
				Iterator<Triangle_dt> iterator3 = dt3.trianglesIterator();
				counter = 0;
		
				while (iterator3.hasNext()) {
					Triangle_dt curr3 = iterator3.next();
					if (!curr3.isHalfplane()) {
						triangleList3[counter] = curr3;
						counter++;
					}
				}
			
				Point_dt pointHolderI1 = new Point_dt(0,0);
				Point_dt pointHolderI2 = new Point_dt(0,0);
				Point_dt pointHolderI3 = new Point_dt(0,0);
				Point_dt pointHolderII1 = new Point_dt(0,0);
				Point_dt pointHolderII2 = new Point_dt(0,0);
				Point_dt pointHolderII3 = new Point_dt(0,0);
				
				for (int i = 0; i < counter; i++){
					for (int j = 0; j < numberPoints+1; j++){
						if (triangleList3[i].p1().x() == image3PointX[j] && triangleList3[i].p1().y() == image3PointY[j]){
							pointHolderI1 = new Point_dt(image1PointX[j], image1PointY[j]);
							pointHolderII1 = new Point_dt(image2PointX[j], image2PointY[j]);
						}	
						if (triangleList3[i].p2().x() == image3PointX[j] && triangleList3[i].p2().y() == image3PointY[j]){
							pointHolderI2 = new Point_dt(image1PointX[j], image1PointY[j]);
							pointHolderII2 = new Point_dt(image2PointX[j], image2PointY[j]);
						}
						if (triangleList3[i].p3().x() == image3PointX[j] && triangleList3[i].p3().y() == image3PointY[j]){
							pointHolderI3 = new Point_dt(image1PointX[j], image1PointY[j]);
							pointHolderII3 = new Point_dt(image2PointX[j], image2PointY[j]);
						}	
					}
					triangleList1[i] = new Triangle_dt (pointHolderI1, pointHolderI2, pointHolderI3);
					triangleList2[i] = new Triangle_dt (pointHolderII1, pointHolderII2, pointHolderII3);
				}
					
				midPoint = (int)(975.0*width) - ((Xmax - Xmin)/2 + Xposition);
						
				for (int a = Xmin; a < Xmax; a++){
					for (int b = Ymin; b < Ymax; b++){
						Point_dt point = new Point_dt(a,b);
						Triangle_dt aTriangle = dt3.find(point);
						if(dt3.find(point).p3() != null){
							for (int z = 0; z < counter; z++){
								if (dt3.find(point).p1().x() == triangleList3[z].p1().x() && dt3.find(point).p1().y() == triangleList3[z].p1().y() && 
									dt3.find(point).p2().x() == triangleList3[z].p2().x() && dt3.find(point).p2().y() == triangleList3[z].p2().y() &&
										dt3.find(point).p3().x() == triangleList3[z].p3().x() && dt3.find(point).p3().y() == triangleList3[z].p3().y()){
								
									x1 = aTriangle.p1().x();	
									x2 = aTriangle.p2().x();
									x3 = aTriangle.p3().x();
									y1 = aTriangle.p1().y();
									y2 = aTriangle.p2().y();
									y3 = aTriangle.p3().y();
							
									Barry1 = ((y2-y3)*(a-x3) + (x3-x2)*(b-y3))/((y2-y3)*(x1-x3)+(x3-x2)*(y1-y3));
									Barry2 = ((y3-y1)*(a-x3) + (x1-x3)*(b-y3))/((y2-y3)*(x1-x3)+(x3-x2)*(y1-y3));
									Barry3 = 1 - Barry1 - Barry2;
																				
									a1 = (int)(triangleList1[z].p1().x()*Barry1 + triangleList1[z].p2().x()*Barry2 + triangleList1[z].p3().x()*Barry3);
									b1 = (int)(triangleList1[z].p1().y()*Barry1 + triangleList1[z].p2().y()*Barry2 + triangleList1[z].p3().y()*Barry3);
									a2 = (int)(triangleList2[z].p1().x()*Barry1 + triangleList2[z].p2().x()*Barry2 + triangleList2[z].p3().x()*Barry3);
									b2 = (int)(triangleList2[z].p1().y()*Barry1 + triangleList2[z].p2().y()*Barry2 + triangleList2[z].p3().y()*Barry3);
																							
									clr1=  Image1.getRGB(a1-firstImageXStart, b1-imagesYStart); 
									clr2=  Image2.getRGB(a2-secondImageXStart, b2-imagesYStart);
																			
									red1   = (clr1 & 0x00ff0000) >> 16;
									green1 = (clr1 & 0x0000ff00) >> 8;
									blue1  =  clr1 & 0x000000ff;
							
									red2   = (clr2 & 0x00ff0000) >> 16;
									green2 = (clr2 & 0x0000ff00) >> 8;
									blue2  =  clr2 & 0x000000ff;
							
									red = (int)(red1*(1.0-ratio) + red2*ratio);
									green = (int)(green1*(1.0-ratio) + green2*ratio);
									blue = (int)(blue1*(1.0-ratio) + blue2*ratio);
								
									newColour = new Color(red, green, blue);
								
									graphics = getGraphics();
																
									colorInt = newColour.getRGB();
								
									anImage.setRGB(a-Xmin, b-Ymin, colorInt);
								}
							}
						}
					}
				}
				
				Xratio = (int)(Yratio*((double)(Xmax-Xmin)/(double)(Ymax-Ymin)));
				
				BufferedImage scaledImage = resize(anImage, Xratio, Yratio);
				
				if (o == 0){
					totalImage = new BufferedImage((int)newWidth, Yratio + 1, BufferedImage.TYPE_INT_ARGB);
				}
			
				Graphics graphicso = displayFrame.getGraphics();
				
				for (int i = 0; i < scaledImage.getWidth(); i++){
					for (int j = 0; j < scaledImage.getHeight(); j++){
						Color anotherColor = new Color((scaledImage.getRGB(i, j) & 0x00ff0000) >> 16, (scaledImage.getRGB(i, j) & 0x0000ff00) >> 8, scaledImage.getRGB(i, j) & 0x000000ff);
						paintComponent(i, j, positionAdOn, graphicso, anotherColor);
						totalImage.setRGB(i+positionAdOn, j, anotherColor.getRGB());
					}
				}				
				positionAdOn = positionAdOn + Xratio;
			}
		}
		else if (e.getSource() == stripSaver){
			File outputfile = new File("saved");
			try {
				ImageIO.write(totalImage, "png", outputfile);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	public void paintComponent(Graphics graphics){
		super.paintComponents(graphics);
		for (int i = 0; i < highestTraverse; i++){
			if(i != numberStep){
				graphics.setColor(colors[i]);
				graphics.fillOval(image1PointX[i], image1PointY[i], 6, 6);					
				graphics.fillOval(image2PointX[i], image2PointY[i], 6, 6);	
			}
		}				
		if (has1BeenModified[numberStep] == true){
			graphics.setColor(colors[numberStep]);										
			graphics.fillOval(save1X, save1Y, 6, 6);
		}
		else{
			graphics.setColor(colors[numberStep]);
			graphics.fillOval(image1PointX[numberStep], image1PointY[numberStep], 6, 6);				
		}
		if (has2BeenModified[numberStep] == true){
			graphics.setColor(colors[numberStep]);										
			graphics.fillOval(save2X, save2Y, 6, 6);
		}
		else{
			graphics.setColor(colors[numberStep]);
			graphics.fillOval(image2PointX[numberStep], image2PointY[numberStep], 6, 6);				
		}
		if (morphingTime == true){
			for (int i = minLength; i < maxLength; i++){
				for (int j = minHeight; j < maxHeight; j++){
					int  red   = (outputCoordinates[i][j] & 0x00ff0000) >> 16;
					int  green = (outputCoordinates[i][j] & 0x0000ff00) >> 8;
					int  blue  =  outputCoordinates[i][j] & 0x000000ff;
					
					graphics.setColor(new Color (red, green, blue));
					graphics.fillRect(i, j, 1, 1);
				}
			}
		}
		graphics.dispose();
	}
	public void paintComponent(int a, int b, Graphics graphicsy, Color color, int XMIN, int YMIN, int positionX, int positionY, int pointMid){
		graphicsy.setColor(color);
		graphicsy.drawRect(a+positionX-XMIN + pointMid, b+positionY-YMIN+10, 1, 1);
	}
	public void paintComponent(int a, int b, int positionX, Graphics graphicsy, Color color){
		graphicsy.setColor(color);
		graphicsy.drawRect(a + positionX, (int)(150.0*height) + b, 1, 1);
	}
	public void paintComponent(int a, int b, int positionX, int positionY, Graphics graphicsy, Color color){
		graphicsy.setColor(color);
		graphicsy.drawRect(a + positionX, (int)(150.0*height) + b, 10, 10);
	}
	class DoublePair {
		final double x;
		final double y;
		DoublePair(double x, double y){
			this.x=x;
			this.y=y;
		}
	}
	public static BufferedImage resize(BufferedImage img, int newW, int newH) { 
	    Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
	    BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

	    Graphics2D g2d = dimg.createGraphics();
	    g2d.drawImage(tmp, 0, 0, null);
	    g2d.dispose();

	    return dimg;
	}  
}
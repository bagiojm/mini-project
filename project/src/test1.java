
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;

 public class test1{
	 static String plName; 
	 static ImageIcon image,img,start;
	 public static JFrame frame=new JFrame("Queen Quest");
	 public static JPanel base=new JPanel(new CardLayout()); 
	 public static CardLayout c1 = (CardLayout)(base.getLayout());

	 test1(){
	 URL path=getClass().getResource("/crown.jpg");
	 image=new ImageIcon(path);
	 URL pathlogo=getClass().getResource("/logo.png");
	 img=new ImageIcon(pathlogo);
	 //System.out.println("bagio path="+path);
	 URL inst=getClass().getResource("/instructions.png");
	 start=new ImageIcon(inst);
	 
	 startScreen();
	 joinGameScreen();
	 }
	 //startScreen():Starting screen of the game
	 public void startScreen()
	 {
		final JPanel startPanel=new JPanel(new BorderLayout());
    	 startPanel.setBackground(Color.WHITE);
    	 JLabel startLabel=new JLabel();
    	 startLabel.setIcon(start);
    	 
    	 JButton Strt=new JButton("START GAME");
    	 Strt.setFont(new Font("Arial Rounded MT Bold",Font.BOLD,30));
    	 Strt.setForeground(Color.WHITE);
    	 Strt.setBackground(Color.blue);
    	 Strt.setPreferredSize(new Dimension(200,80));
    	 Strt.setAlignmentY(startPanel.BOTTOM_ALIGNMENT);
    	 //b.setBackground(Color.green);
    	 startPanel.add(startLabel,BorderLayout.CENTER);
    	 startPanel.add(Strt,BorderLayout.SOUTH);
    	 base.add(startPanel);
    	 Strt.addMouseListener(new MouseAdapter() { 
             public void mousePressed(MouseEvent me) { 
            	
            	 c1.next(base);
            	 frame.add(base);
            	
                  
               } 
             }); ;
    	 
            frame.add(base);
    	 frame.pack();
    	 frame.setLocationRelativeTo(null);
    	 
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
 frame.setVisible(true);
		 }
	
	 //joinGameScreen:panel for joining and hosting the game
	 public void joinGameScreen()
	 {
		 
		 
	 		JLabel label=new JLabel();
	    	label.setIcon(image);
	    	JLabel blank=new JLabel();
	    	blank.setPreferredSize(new Dimension(200,80));
	    	final JPanel main;
	    	JPanel a=new JPanel(new GridBagLayout());
	    	JPanel b=new JPanel();
	    	b.add(label);
	    	GridBagConstraints gbc=new GridBagConstraints();
	    	gbc.fill=GridBagConstraints.VERTICAL;
	    	main=new JPanel(new BorderLayout());
	    	
	    	
	    	
	    	gbc.gridx=0;
	    	gbc.gridy=0;
	    	
	    	gbc.anchor=GridBagConstraints.NORTH;
	    	JLabel logo=new JLabel();
	    	
	    	logo.setIcon(img);
	    	
	    	a.add(logo,gbc);
	    	gbc.gridx=0;
	    	gbc.gridy=1;
	    	
	    	JLabel nameLabel=new JLabel("Enter your Name");
	    	nameLabel.setFont(new Font("Arial Rounded MT Bold",Font.BOLD,15));
	    	nameLabel.setPreferredSize(new Dimension(300,100));
	    	a.add(nameLabel,gbc);
	    	final JTextField nameText=new JTextField("NOOBIE");
	    	nameText.setPreferredSize(new Dimension(300,30));
	    	
	    	gbc.gridy=2;
	    	
	    	a.add(nameText, gbc);
	    	gbc.gridx=0;
	    	gbc.gridy=3;
	    	JLabel nul1=new JLabel();
	    	nul1.setPreferredSize(new Dimension(300,100));
	    	a.add(nul1,gbc);
	    	gbc.gridx=0;
	    	gbc.gridy=4;
	    	JButton join=new JButton("Join Quest");
	    	join.setFont(new Font("Arial Rounded MT Bold",Font.BOLD,20));
	    	join.setBackground(Color.WHITE);
	    	
	    	a.add(join,gbc);
	    	
	    	join.addMouseListener(new MouseAdapter() { 
	             public void mousePressed(MouseEvent me) {
	            	 plName=nameText.getText();
	            	 client clientobj=new client();
	            	  
	            	  
	            	 
	                  
	               } 
	             }); ;
	    	
	    	gbc.gridx=0;
	    	gbc.gridy=5; 
	    	JButton host=new JButton("Make Quest");
	    	host.setFont(new Font("Arial Rounded MT Bold",Font.BOLD,20));
	    	host.setBackground(Color.WHITE);
	    	
	    	a.add(host,gbc); 
	    	host.setPreferredSize(new Dimension(200,80));
	    	 join.setPreferredSize(new Dimension(200,80));
	    	 host.addMouseListener(new MouseAdapter() { 
	             public void mousePressed(MouseEvent me) { 
	            	 plName=nameText.getText();
	            //	 thr f=new thr();
	            	// f.serverSync();
	        	    //f.prepareGUI();
	                 
	               } 
	             }); ;
	    	 a.setBackground(Color.WHITE);
	    	 
	    	 
	    	
	    	 main.add(a, BorderLayout.LINE_START);
	    	 main.add(b, BorderLayout.LINE_END);
	    	 base.add(main);
	    	 /*frame.add(main);
	    	 frame.pack();*/
	    	 frame.setLocationRelativeTo(null);
	    	 
	    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
	 frame.setVisible(true);
		 
		 
	 }
	 
	 
	 
	 public static void main(String[] args)
	 {
		test1 ob=new test1();
		 
		 		
		
		 
	 }
	 
	 
 }
/*Server employs the parts of server as well as  client  
 server is maintained to accept and configure positions in the game
 server is also a player in the game hence employs the client part
 
 Whenever one client makes a move it sends position to the server ,server will make req updates and send the 
 array consists of current pos of all clients to moved client.
 */


import java.net.*;
import java.util.Enumeration;
import java.util.Scanner;
import java.awt.Color;
import java.awt.*;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.*;
import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.util.concurrent.*;
import java.util.Timer;
import java.util.TimerTask;

public class server {
static DatagramSocket socket;
static DatagramSocket socket1;
private static JPanel panel;
private static JFrame frame;
public static int aflag=0;
public static int cid=0;
public static Semaphore z=new Semaphore(1);
public static JButton[][] buttonArray=new JButton[23][23];
public static int i,j;
public static int x,y;	
public static int arr[]={0,0,0,22,22,0,22,22};
public static int a[][]=new int[23][23];
public static InetAddress[] iparr=new InetAddress[4];
public static InetAddress serverip;
server()
{



	mine cm=new mine(1);
	a=cm.m;
		
    try
    {
    	socket=new DatagramSocket(40000);
    	int ctr=1;
    	serverip=getCurrentIp();
    	iparr[0]=serverip;//hosted server consists of last part of ipaddress 0;
    	while(ctr!=3)//ctr stands for no of clients
    	{
    	byte[] rd=new byte[2];
    	DatagramPacket rpacket=new DatagramPacket(rd,rd.length);
    	socket.receive(rpacket);
    	System.out.println(rd[0]);
    	InetAddress ip=rpacket.getAddress();
    	iparr[ctr]=ip;//saves the ip of client in the ip array
    	int port=rpacket.getPort();
    	int sync=ctr;
    	byte[] sd=new byte[2];
    	sd[0]=(byte)sync;
    	DatagramPacket spacket=new DatagramPacket(sd,sd.length,ip,port);//sends the client no to client
    	socket.send(spacket);
    	byte[] msend=new byte[530];
    	int n=0;
    	for(int i=0;i<23;i++)
    		for(int j=0;j<23;j++)
    		{
    			msend[n]=(byte)a[i][j];//sending mine cords to all of the clients
    			n++;
    		}
    	DatagramPacket spacket2=new DatagramPacket(rd,rd.length);
    	socket.receive(spacket2);
    	
    	DatagramPacket spacket1=new DatagramPacket(msend,msend.length,ip,port);
    	socket.send(spacket1);
    	System.out.println("Sent");
    	ctr++;
    	if(ctr==3)
    		
    	{ 
    		byte[] q=new byte[4];
   		   q[0]=(byte)999;
   		   for(int i=0;i<3;i++)
    		{
    		 
    		 DatagramPacket s=new DatagramPacket(q,q.length,iparr[i],port);
    		 socket.send(s);
    		 
    		}
    	}
    	}
        
		prepareGUI();

    }
    catch(Exception e)
    {
    	e.printStackTrace();
    }
		


}
	public static InetAddress getCurrentIp() {
	   try {
	       Enumeration<NetworkInterface> networkInterfaces = NetworkInterface
	               .getNetworkInterfaces();
	       while (networkInterfaces.hasMoreElements()) {
	           NetworkInterface ni = (NetworkInterface) networkInterfaces
	                   .nextElement();
	           Enumeration<InetAddress> nias = ni.getInetAddresses();
	           while(nias.hasMoreElements()) {
	               InetAddress ia= (InetAddress) nias.nextElement();
	               if (!ia.isLinkLocalAddress() 
	                && !ia.isLoopbackAddress()
	                && ia instanceof Inet4Address) {
	                   return ia;
	               }
	           }
	       }
	   } catch (SocketException e) {
	   	System.out.println("Unable to get address");e.printStackTrace();
	   }
	   return null;
	}
	public static class mytask extends TimerTask
	{
		public void run()
		{   //System.out.println("10 seconds have elapsed");
			aflag=0;
		   z.release(); 	
		}
	}

	public static void sendCord(int x)
	{ if(aflag==0)
	{
		try{
		byte[] send=new byte[4];
		//System.out.println("Flag is now " +aflag);
		send[0]=(byte)x;
		DatagramPacket snd=new DatagramPacket(send,send.length,serverip,40000);
		socket1.send(snd);
		
		}
	catch(Exception e){e.printStackTrace();}
	}
	}
	

	public static class s extends AbstractAction
	{  String b;
		s(String a)
		{
           this.b=a;
		}
		public void actionPerformed(ActionEvent e) {
			if(b=="LEFT")
			{ System.out.println("LEFT");
				sendCord(3);
			}
			else if(b=="UP")
				{System.out.println("UP");
				sendCord(1);
				}
			else if(b=="DOWN")
				{System.out.println("DOWN");
				sendCord(2);
				}
			else
				sendCord(4);
			
	     
	   }
	}
	public static void  addaction(String name)
	{
		s action = new s(name);
	
	   KeyStroke pressedKeyStroke = KeyStroke.getKeyStroke(name);
	   InputMap inputMap = panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
	   inputMap.put(pressedKeyStroke, name);
	   panel.getActionMap().put(name, action);
	 
	 
	}
	public static int number(int p,int q,int mx,int mn)
	{
	int m,n,r,s,flag=0;
	if(p-1<mn){m=mn;}else m=p-1;
	if(q-1<mn){n=mn;}else n=q-1;
	if(q+1>mx){r=mx;}else r=q+1;
	if(p+1>mx){s=mx;}else s=p+1;

	for(;m<=s;m++)
	{	
		if(q-1<mn){n=mn;}else n=q-1;
		for(;n<=r;n++)
			{
				if(a[m][n]==1)
					{
						flag++;
						}
				}
	}
	return(flag);
	}//endof-funtion-number

	public static class socketReceive extends Thread
	{
		public void run()
		{
			
		byte[] prevpos=new byte[32];
			prevpos[0]=0;
			prevpos[1]=0;
			prevpos[2]=0;
			prevpos[3]=22;
			prevpos[4]=22;
			prevpos[5]=0;
			prevpos[6]=22;
			prevpos[7]=22;
			/*prevpos array is maintained to save the previous position of all players when a change is made 
			 whenever a player makes a move his updated position must be in his color and previous position 
			 must be changed to grid colorc ie.White */
			
			while(true){
		
				
				try{
				byte[] ad=new byte[4];
				//synchronisation od server and client
	   		DatagramPacket apacket=new DatagramPacket(ad,ad.length);
	   		System.out.println("Hello");
	   		socket.receive(apacket);
	   		System.out.println("Hi");
	   		InetAddress ip=apacket.getAddress();
	   		int p;
	   		String k=ip.toString();
	   		//loop to identify the client
	   		for(p=0;p<3;p++)
	   			{String h=iparr[p].toString();
	   			 if(h.matches(k))
	   			
	   				break;
	   			}
	   		System.out.println("RECEIVED FROM CLIENT "+p);
	   		p*=2;
	   		int port=apacket.getPort();
	   		int b=(int)ad[0];
	   		int e=arr[p];
	   		int f=arr[p+1];
	   		System.out.println(b+"  ");
	   		int flag=0;
	   		switch( b ) { 
		       case 1:
		           if(arr[p]>0)arr[p]--;
		           break;
		       case 2:
		           if(arr[p]<22)arr[p]++; 
		           break;
		       case 3:
		           if(arr[p+1]>0)arr[p+1]--;
		           break;
		       case 4:
		           if(arr[p+1]<22)arr[p+1]++;
		           
		           break;}
	   		for(int i=0;i<8;i+=2)
	   			if(i==p)
	   				continue;
	   			else
	   				if(arr[i]==arr[p]&&arr[i+1]==arr[p+1])
	   				{
	   					flag=1;
	   					break;
	   				}
	           if(flag==1)
	           {
	           	arr[p]=e;
	           	arr[p+1]=f;
	           }
	           
		       byte[] c=new byte[8];
		       for(int i=0;i<8;i++)
	                c[i]=(byte)arr[i];
		       //System.out.print(arr[0]+":a : b:"+arr[1]);
		       
		       for(p=0;p<3;p++)
		       { 
		       	DatagramPacket send=new DatagramPacket(c,c.length,iparr[p],port);
		         System.out.println("Sent to client"+p);
		         socket.send(send);
		       }
				}
				catch(Exception e){e.printStackTrace();}
				
				
				
	   int i=0,q;
	   for(q=0;q<8;q=q+2)buttonArray[prevpos[q]][prevpos[q+1]].setBackground(Color.WHITE);
	   for(q=0;q<8;q++)prevpos[q]=(byte)arr[q];
	   
	   for(i=0;i<8;i=i+2)
	   {
	   	
	   	x=arr[i];
	       y=arr[i+1];
	       		
	   
	       if(a[x][y]==1)
	   {    if(i/2==cid) 
	   	{
	   	//System.out.println("aflag is (in mine) 1");
	   	a[x][y]=0;
	   	z.acquireUninterruptibly();
	   	aflag=1;
	   	Timer t=new Timer();
			mytask x=new mytask();
			t.schedule(x,10000);
	   	}
	       buttonArray[x][y].setBackground(Color.RED);
		   // buttonArray[x][y].setIcon(bomb);
		    
		   
		    
	   
	   
	   
	   }
	   else if(x==11&&y==11)
		   {    JPanel exit=new JPanel();
		   JLabel exbut;
		   System.out.print("bagio");
		   if(i/2==0)
		   	
		       exbut=new JLabel("PINK WINS THE GAME");
		   else if(i/2==1) exbut=new JLabel("BLUE WINS THE GAME");
		   else if(i/2==2) exbut=new JLabel("GREEN WINS THE GAME");
		   else exbut=new JLabel("CYAN WINS THE GAME");
		   socket.close();
		   socket1.close();
		   aflag=1;
		       exbut.setFont(new Font("seriff",Font.BOLD,50));
		   	exit.add(exbut);
		   	JButton sysexit=new JButton("EXIT GAME");
		   	sysexit.setFont(new Font("seriff",Font.BOLD,30));
		   	sysexit.setBackground(Color.RED);
		   	sysexit.addMouseListener(new MouseAdapter() { 
		            public void mousePressed(MouseEvent me) {
		           	System.exit(0); 
		           	exit.add(sysexit);
		              } 
		            }); 
		   	test1.base.add(exit);
		   	test1.c1.next(test1.base);
	   	test1.frame.add(test1.base);
	   }
		else 
			{if(i/2==0)
			buttonArray[x][y].setBackground(Color.MAGENTA);
			else if(i/2==1)
			buttonArray[x][y].setBackground(Color.BLUE);
			else if(i/2==2)
			buttonArray[x][y].setBackground(Color.GREEN);
			else if(i/2==3)	 
			{buttonArray[x][y].setBackground(Color.CYAN);
			//System.out.println("CLient no:"+i+" "+i+1+" "+x+" "+y);
			
			}
			}
	   
	}

			}//endofwhile
	}//endof run

	}//endof class

	public static class socketSend extends Thread
	{
	

		public void run()
		{
			try{
			socket1=new DatagramSocket(25000);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			addaction("LEFT");
			addaction("RIGHT");
			addaction("UP");
			addaction("DOWN");
			//frame.addKeyListener(key);		
		
		}	
}//send class end

	public static void prepareGUI()
    {
    	panel=new JPanel();	
    	panel.setLayout(new GridLayout(23,23));
    	int i,j;

    	for(i=0;i<23;i++)
    	{
    		for(j=0;j<23;j++)
    		{
    			
    			if(i==11&&j==11){
    				buttonArray[i][j]=new JButton();
    				buttonArray[i][j].setBackground(Color.YELLOW);
    				
    			}
    			else
    				{
    				int p=number(i,j,22,0);
    			buttonArray[i][j]=new JButton(p+"");
    			buttonArray[i][j].setFont(new Font("seriff",Font.BOLD,20));
    			if(p==1)
    				buttonArray[i][j].setForeground(Color.BLACK);
    			else if(p==2)
    				buttonArray[i][j].setForeground(Color.ORANGE);
    			else
    				buttonArray[i][j].setForeground(Color.DARK_GRAY);
    			
    			buttonArray[i][j].setBackground(Color.WHITE);
    				}
    			panel.add(buttonArray[i][j]);
    		}
    	}


    	/*if(cid==0)
    		buttonArray[x][y].setBackground(Color.MAGENTA);
    		else if(cid==1)
    		buttonArray[x][y].setBackground(Color.BLUE);
    		else if(cid==2)
    		buttonArray[x][y].setBackground(Color.GREEN);
    		else if(cid==3)	 
    		buttonArray[x][y].setBackground(Color.CYAN);*/
    buttonArray[0][0].setBackground(Color.MAGENTA);
    buttonArray[0][22].setBackground(Color.BLUE);
    buttonArray[22][0].setBackground(Color.GREEN);
    buttonArray[22][22].setBackground(Color.CYAN);	
    	frame=new JFrame("QUEEN QUEST");
    	
    	test1.base.add(panel);
    	test1.c1.next(test1.base);
	test1.frame.add(test1.base);
    test1.frame.setVisible(true);
    test1.frame.setExtendedState(test1.frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
   test1.frame.pack();
    test1.frame.setFocusable(true);
    test1.frame.requestFocus();
   socketReceive r=new socketReceive();
   r.start();
   socketSend s=new socketSend();
   s.start();
	}

	
	
	
	public static void main(String[] args) {
		
	
	}
	


	 
}
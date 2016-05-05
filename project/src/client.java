
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.*;
import java.awt.event.KeyEvent;
import java.net.*;
import java.util.Enumeration;
import javax.swing.*;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;
public class client {
	static DatagramSocket socket;
	Scanner in=new Scanner(System.in);
	private static JFrame frame;
	private static JPanel panel;
	 static int x,y;
	 public static int aflag=0;
	 public static Semaphore z=new Semaphore(1);
	private int p=0,q=0;
	static int flag=0;
	private static JButton[][] buttonArray=new JButton[23][23];
	private static int[][] a=new int[23][23];	
	static InetAddress serverip;
	
	
	public static int port;
	static int cid;
 //client(){
	
	
//}
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
	public static class s extends AbstractAction
	{  String b;
		 s(String a)
		 {
           this.b=a;
		 }
		public void actionPerformed(ActionEvent e) {
			if(b=="LEFT")
                sendCord(3);
			else if(b=="UP")
				 sendCord(1);
			else if(b=="DOWN")
				 sendCord(2);
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
	public static class mytask extends TimerTask
	{
		public void run()
		{
			aflag=0;
			z.release();
			
		}
		
	}
	
	public static void prepareGUI()
	{
		panel=new JPanel();	
		panel.setLayout(new GridLayout(23,23,10,10));
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
	frame.add(panel);
	frame.setVisible(true);
	frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
	frame.pack();
	frame.setFocusable(true);
	frame.requestFocus();


		
	buttonArray[x][y].setBackground(Color.YELLOW);
	socketSend s=new socketSend();
	socketReceive r=new socketReceive();
	s.start();r.start();
	}
	public static void sendCord(int x)
	{  
		if(aflag==0){
		try{
		byte[] send=new byte[4];
		send[0]=(byte)x;
		DatagramPacket snd=new DatagramPacket(send,send.length,serverip,30000);
		socket.send(snd);
		System.out.println("BAGIO");
		}
	catch(Exception e){e.printStackTrace();}
	}
	}
	public static  class socketSend extends Thread
	{

	

		public void run()
		{
			
			
			addaction("LEFT");
			addaction("RIGHT");
			addaction("UP");
			addaction("DOWN");
		
		}	
}//send class end
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
		//System.out.println("Super");
		while(true){
	byte[] bytearr=new byte[32];
    DatagramPacket arry=new DatagramPacket(bytearr,bytearr.length);
    try{
    	
    	socket.receive(arry);
        
        System.out.print(x+":x y:"+y+"\n");
    }
    catch(Exception s){s.printStackTrace();}
    int i=0,q;
    for(q=0;q<8;q=q+2)buttonArray[prevpos[q]][prevpos[q+1]].setBackground(Color.WHITE);
    for(q=0;q<8;q++)prevpos[q]=bytearr[q];
    
    for(i=0;i<8;i=i+2)
    {
    	
    	x=(int)bytearr[i];
        y=(int)bytearr[i+1];
        		
    
        if(a[x][y]==1)
    {
	     buttonArray[x][y].setBackground(Color.RED);
	     if(i/2==cid)
	     {
	    	 a[x][y]=0;
	    	 z.acquireUninterruptibly();
	    	 aflag=1;
	    	 Timer t=new Timer();
	    	 mytask x=new mytask();
	    	 t.schedule(x, 10000);
	     }
	     flag=1;
	     
	     
	     
	    /* JPanel exit=new JPanel();
 	        JButton exbut=new JButton("TRAPPED!!!");
 	        
 	        exbut.setFont(new Font("seriff",Font.BOLD,40));
		    	panel.setVisible(false);
		    	exit.add(exbut);
	    	frame.add(exit);
    socket.close();
    */
	         }
    else if(x==11&&y==11)
	    {   JPanel exit=new JPanel();
	    JButton exbut;
	    if(i/2==0)
	        exbut=new JButton("PINK WINS THE GAME");
	    else if(i/2==1) exbut=new JButton("BLUE WINS THE GAME");
	    else if(i/2==2) exbut=new JButton("CYAN WINS THE GAME");
	    else exbut=new JButton("GREEN WINS THE GAME");
	        exbut.setFont(new Font("seriff",Font.BOLD,40));
	    	panel.setVisible(false);
	    	exit.add(exbut);
    	frame.add(exit);
    	
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


	public static void main(String[] args) {
		try
		{
			socket=new DatagramSocket(20000);
			
			serverip=getCurrentIp(); 
			//serverip=InetAddress.getLocalHost();
			
			byte[] ar=new byte[4];
			
				
			ar[0]=(byte)1;
			byte[] b=new byte[4];
			byte[] mine=new byte[530];
			
		   String ipa=serverip.getHostName();
			System.out.print(ipa);
			byte[] ip=serverip.getAddress();
		     ip[3]=(byte)1;
		     serverip=InetAddress.getByAddress(ip);
		     String S=serverip.toString();
		     System.out.println("Connecting to "+S);
		     
			DatagramPacket syncpacket=new DatagramPacket(ar,ar.length,serverip,30000);
			socket.send(syncpacket);
			System.out.println("worked");
		
			DatagramPacket sync1packet=new DatagramPacket(b,b.length);
			socket.receive(sync1packet);//color recognising
			System.out.println(b[0]);
			DatagramPacket sync2packet=new DatagramPacket(b,b.length,serverip,30000);
			socket.send(sync2packet);
			DatagramPacket minepacket=new DatagramPacket(mine,mine.length);
			socket.receive(minepacket);
			int i,j,c=0;
			for(i=0;i<23;i++)
				for(j=0;j<23;j++,c++)
				{
					a[i][j]=(int)mine[c];
					
					System.out.println(a[i][j]);
				}
			port=sync1packet.getPort();
			//System.out.println(port);
			
			//String s=new String(sync1packet.getData());
			System.out.print("Connected and received ");
			System.out.println(b[0]);
			cid=b[0];
			if(cid==0)
			   {
				x=0;
				y=0;
			   }
			else if(cid==1)
			{
				x=0;
				y=22;
			}
			else if(cid==2)
			{
				x=22;
				y=0;
			}
			else if(cid==3){x=22;y=22;}
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			
		}
		
		System.out.println("bagio");
	     prepareGUI();

		
		
	}

}

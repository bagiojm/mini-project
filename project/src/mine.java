import java.util.Random;

public class mine {
	
	
/*
 mine class is implemented to arrange mines randomly in the grid 
 every time the game starts.It works by dividing the grid in to 
 4 quadrants an apply the same algorithm 4 times ,thereby assuring
  that every player has equal game experience 
  Algorithm consists of applying random function to grid to find x and y cords 
 */	
public static int m[][]=new int[23][23];
int i,j;
int r1i,r2i,r3i,r4i,r1j,r2j,r3j,r4j;

	mine(int a)//a stands for the level of the game
	{
		for(i=0;i<22;i++)
			for(j=0;j<22;j++)
				m[i][j]=0;
		int limit;
		if(a==1)
		   limit=26;
		else if(a==2)
			limit=40;
		else
			limit=66;
			Random g=new Random();
			int h;
			for(h=1;h<=limit;h++)
			{
				int r1i=g.nextInt(11);
				int r1j=g.nextInt(12);
				m[r1i][r1j]=1;
			}
			for(h=1;h<=limit;h++)
			{	int r2i=g.nextInt(12);
				int r2j=g.nextInt(23);
				if(r2j<12)
					r2j+=11;
				m[r2i][r2j]=1;
			}
			for(h=1;h<=limit;h++)
			{
				int r3i=g.nextInt(23);
				if(r3i<11)
					r3i+=12;
				int r3j=g.nextInt(11);
				m[r3i][r3j]=1;
			}
			for(h=1;h<=limit;h++)
			{
				int r4i=g.nextInt(23);
				if(r4i<12)
					r4i+=11;
				int r4j=g.nextInt(23);
				if(r4j<11)
				   r4j+=12;
				   m[r4i][r4j]=1;
			}
				
			
			m[0][0]=0;
			m[0][22]=0;
			m[22][0]=0;
			m[22][22]=0;
			m[11][11]=0;
			
		}
	
}
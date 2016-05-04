package dataObstacles;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;

public class Bomb 
{
	Image immagine;
	
	public Circle bomba;
	
	int beginPosX = 0;
	int beginPosY = 418;
	int centroX = beginPosX;
	int centroY = beginPosY;
	int raggio = 15;
	
	int timer = 15000;
	
	int time = 0;
	
	public Bomb() throws SlickException
		{
			immagine = new Image( "./data/Image/bomba.png" );
			
			if(time == 0)
				bomba = new Circle( centroX, centroY, raggio );
			else
				bomba = new Circle( -100, -100, 0 );
		}
	
	public void makeArea()
		{
			if(time == 0)
				bomba = new Circle( centroX, centroY, raggio );
			else
				bomba = new Circle( -100, -100, 0 );
		}
	
	public void draw( Graphics g )
		{
			if(time == 0)
				immagine.draw( centroX - raggio, centroY - raggio, 2*raggio, 2*raggio );
			
			g.draw( bomba );
		}
	
	public void update( int delta )
		{
			if(time == 0)
				if(centroX + raggio + 1 <= 800)
					centroX = centroX + 1;
				else
					time = time + delta;
			else
				{
					if(time < timer)
						time = time + delta;
					else
						{
							centroX = beginPosX;
							centroY = beginPosY;
							time = 0;
						}
				}
		}
}

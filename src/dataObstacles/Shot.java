package dataObstacles;

import interfaces.InGame;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class Shot 
{
	private float posX;
	private float posY;
	
	int width = 20;
	int height = 100;
	
	Image immagine;
	
	public Shot() throws SlickException
		{ immagine = new Image( "./data/Image/pole.png" ); }
	
	public void setXY( float x, float y )
		{
			posX = x;
			posY = y;
		}

	public void draw() throws SlickException
		{ immagine.draw( posX, posY, width, height ); }
	
	public Rectangle getArea()
		{ return new Rectangle( posX, posY, width, height ); }
	
	public boolean collision( Ostacolo ost, int index ) throws SlickException
		{ 
			if(this.getArea().intersects( ost.component( "" ) ))
				{
					if(ost.getWidth() > 6)
						{
							ost.setXY( (int) ost.getWidth()/2, (int) ost.getWidth()/2, "setRay" );
							
							Bubble temp1 = new Bubble( ost );
							Bubble temp2 = new Bubble( ost );
							
							temp1.setMaxHeight( ost.getMaxHeight() );
							temp1.setSpeedX( ost.getSpeedX() );
							if(ost.getSpeedY() > 0)
								temp1.setSpeedY( ost.getSpeedY() );
							else
								temp1.setSpeedY( -ost.getSpeedY() );
							
							temp2.setMaxHeight( ost.getMaxHeight() );
							temp2.setSpeedX( -ost.getSpeedX() );
							if(ost.getSpeedY() > 0)
								temp2.setSpeedY( ost.getSpeedY() );
							else
								temp2.setSpeedY( -ost.getSpeedY() );
							
							InGame.ostacoli.remove( ost );
							InGame.ostacoli.add( temp1 );
							InGame.ostacoli.add( temp2 );
						}
					else
						InGame.ostacoli.remove( index );
					
					return true;
				}
			else
				return false;
		}
}
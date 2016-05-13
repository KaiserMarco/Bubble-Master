package dataObstacles;

import interfaces.InGame;

import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

public class Shot 
{
	private int posX;
	private int posY;
	
	int width = 20;
	int height = 100;
	
	private int widthC, heightC;
	private int widthS, heightS;
	
	private Image shot[];
	private ArrayList<Image> sparo;
	
	private SpriteSheet sheetShot = new SpriteSheet( new Image( "./data/Image/shot.png" ), 10, 17 ), sheetChain = new SpriteSheet( new Image( "./data/Image/chain.png" ), 6, 6 );
	
	public Shot() throws SlickException
		{
			sparo = new ArrayList<Image>();
			shot = new Image[2];
			shot[0] = sheetShot.getSubImage( , y, width, height );
			shot[1] = sheetShot.getSubImage( , y, width, height );
		}
	
	public void setXY( int x, int y )
		{
			posX = x;
			posY = y;
		}

	public void draw() throws SlickException
		{
			
		}
	
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
package dataObstacles;

import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

import interfaces.InGame;

public class Shot 
{
	private int posX, posY, startY;
	
	int width = 20;
	int height = 100;
	
	private int widthS = 10, heightS = 16;
	private int widthC = 6, heightC = 6;
	
	private Image shot[];
	private ArrayList<Image> sparo;
	
	private SpriteSheet sheetShot = new SpriteSheet( new Image( "./data/Image/shot.png" ), 10, 16 );
	private SpriteSheet sheetChainHor = new SpriteSheet( new Image( "./data/Image/chainHor.png" ), 6, 6 );
	
	public Shot() throws SlickException
		{
			shot = new Image[2];			
			shot[0] = sheetShot.getSubImage( 0, 0 );
			shot[1] = sheetChainHor.getSubImage( 0, 0 );
			
			sparo = new ArrayList<Image>();
			posY = posY - heightS;
		}
	
	public void setFloor( double val )
		{ posY = (int) val; }
	
	public void setXY( int x, int y )
		{
			posX = x;
			sparo.add( shot[0] );
			posY = y - heightS;
			startY = posY;
		}

	public void draw() throws SlickException
		{
			for(int i = 0; i < sparo.size(); i++)
				{
					if(i < sparo.size() - 2)
						{
							sparo.get( i ).draw( posX, startY, widthC, heightC );
							startY = startY - heightC;
						}
					else
						sparo.get( i ).draw( posX, posY, widthS, heightS );
				}
		}
	
	public Rectangle getArea()
		{ return new Rectangle( posX, posY, width, posY - startY ); }
	
	public boolean collision( Ostacolo ost, int index, String type ) throws SlickException
		{
			sparo.add( 0, shot[1] );
			posY = posY - heightC;

			if(posY <= 0)
				{
					sparo.clear();
					return true;
				}
		
			if(getArea().intersects( ost.component( "rect" ) ))
				{
					if(type.equals( "bolla" ))
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
						}
					
					sparo.clear();
					
					return true;
				}
			else
				return false;
		}
}
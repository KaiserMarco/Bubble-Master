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
	
	private int widthS = 15, heightS = 21;
	private int widthC = 8, heightC = 8;
	
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
	
	public void setXY( int x, int y )
		{
			sparo.clear();
			sparo.add( shot[0] );
			posX = x;
			posY = y - heightS;
			startY = y;
		}

	public void draw() throws SlickException
		{
			for(int i = 0; i < sparo.size() - 1; i++)
				{
					sparo.get( i ).draw( posX, startY, widthC, heightC );
					startY = startY - heightC;
				}
			sparo.get( sparo.size() - 1 ).draw( posX + widthC/2 - widthS/2, posY, widthS, heightS );
			
			startY = startY + heightC * (sparo.size() - 1);
		}
	
	public int getWidth()
		{ return widthS; }
	
	public Rectangle getArea()
		{ return new Rectangle( posX, posY, widthS, posY + startY ); }
	
	public boolean collision( Ostacolo ost, int index, String type ) throws SlickException
		{		
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
					
					return true;
				}
			else
				return false;
		}
	
	public boolean update()
		{
			/*aggiunge un nuovo pezzo allo sparo*/
			sparo.add( 0, shot[1] );
			posY = posY - heightC;
			if(posY <= 0)
				return true;
			return false;
		}
}
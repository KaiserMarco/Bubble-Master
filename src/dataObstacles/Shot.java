package dataObstacles;

import interfaces.InGame;

import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

public class Shot 
{
	private int posX, posY, startY;
	
	private int widthS = 15, heightS = 21;
	private int widthC = 9, heightC = 9;
	
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
			int saveY = startY;
			for(int i = 0; i < sparo.size() - 1; i++)
				{
					startY = startY - heightC;
					sparo.get( i ).draw( posX, startY, widthC, heightC );
				}
			sparo.get( sparo.size() - 1 ).draw( posX + widthC/2 - widthS/2, posY, widthS, heightS );
			
			startY = saveY;
		}
	
	public int getWidth()
		{ return widthS; }
	
	public Rectangle getArea()
		{ return new Rectangle( posX, posY, widthS, startY - posY ); }
	
	public boolean collision( Ostacolo ost, String type ) throws SlickException
		{		
			if(posY <= 0)
				return true;
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
								InGame.ostacoli.remove( ost );
						}

					return true;
				}
			else
				return false;
		}
	
	public void update()
		{
			/*aggiunge un nuovo pezzo allo sparo*/
			sparo.add( 0, shot[1] );
			posY = posY - heightC;
		}
}
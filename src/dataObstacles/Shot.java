package dataObstacles;

import interfaces.InGame;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

public class Shot 
{
	private int posX, posY, startY;
	
	private int widthS, heightS;
	private int widthC, heightC;
	
	private Image shot[];
	private ArrayList<Image> sparo;
	
	private SpriteSheet sheetShot;
	private SpriteSheet sheetChainHor;
	
	public Shot( GameContainer gc ) throws SlickException
		{
			widthS = gc.getHeight()/40;
			heightS = gc.getHeight()*10/285;
			
			widthC = gc.getHeight()*10/666;
			heightC = gc.getHeight()*10/666;
			
			sheetShot = new SpriteSheet( new Image( "./data/Image/shot.png" ), gc.getWidth()/80, gc.getWidth()/50 );
			sheetChainHor = new SpriteSheet( new Image( "./data/Image/chainHor.png" ), gc.getHeight()/100, gc.getHeight()/100 );
		
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
	
	public boolean collision( Ostacolo ost, String type, GameContainer gc ) throws SlickException
		{		
			if(posY <= 0)
				return true;
			if(getArea().intersects( ost.component( "rect" ) ))
				{
					if(type.equals( "bolla" ))
						{
							if(ost.getWidth() > gc.getHeight()/100)
								{
									ost.setXY( (int) ost.getWidth()/2, (int) ost.getWidth()/2, "setRay" );
									
									Bubble temp1 = new Bubble( ost, gc );
									Bubble temp2 = new Bubble( ost, gc );
									
									temp1.setMaxHeight( ost.getMaxHeight() );
									if(ost.getSpeedY() > 0)
										temp1.setSpeed( ost.getSpeedX(), ost.getSpeedY() );
									else
										temp1.setSpeed( ost.getSpeedX(), -ost.getSpeedY() );
									
									temp2.setMaxHeight( ost.getMaxHeight() );
									if(ost.getSpeedY() > 0)
										temp2.setSpeed( -ost.getSpeedX(), ost.getSpeedY() );
									else
										temp2.setSpeed( -ost.getSpeedX(), -ost.getSpeedY() );
									
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
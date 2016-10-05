package dataObstacles;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

import Utils.Global;
import dataPowers.Coin;
import dataPowers.DoubleShot;
import dataPowers.Invincible;
import dataPowers.Life;
import dataPowers.TripleShot;
import interfaces.InGame;

public class Shot 
{
	private int posX, posY, startY;
	
	private int widthS, heightS;
	private int widthC, heightC;
	
	private Image shot[];
	private ArrayList<Image> sparo;
	
	private SpriteSheet sheetShot;
	private SpriteSheet sheetChainHor;
	
	private boolean shooting;
	
	private int countShot;
	
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
			
			shooting = false;
			
			countShot = 0;
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
			for(Image fuoco: sparo)
				{
					startY = startY - heightC;
					fuoco.draw( posX, startY, widthC, heightC );
				}
			sparo.get( sparo.size() - 1 ).draw( posX + widthC/2 - widthS/2, posY, widthS, heightS );
			
			startY = saveY;
		}
	
	public int getWidth()
		{ return widthS; }
	
	public Rectangle getArea()
		{ return new Rectangle( posX, posY, widthS, startY - posY ); }
	
	public void setShot( boolean val )
		{ shooting = val; }
	
	/**return true = se spara
	 * return false = se non spara*/
	public boolean isShooting()
		{ return shooting; }
	
	public boolean getShot()
		{ return shooting; }
	
	public int getAnimTime()
		{ return countShot; }
	
	public void setAnimTime( int val )
		{ countShot = val; }
	
	public boolean collision( Player play, Ostacolo ost, String type, GameContainer gc ) throws SlickException
		{
			if(getArea().intersects( ost.component( "rect" ) ))
				{
					if(type.equals( "bolla" ))
						{
							if(Math.random() >= 0)
								{
									int ray = (int) (gc.getHeight()/40 * Global.H/Global.Height);
									double power = Math.random();
									if(power <= 0.5)
										InGame.powerUp.add( new TripleShot( (int) (ost.getArea().getCenterX() - ray), (int) ost.getArea().getCenterY(), ray, ost.getMaxHeight() ) );
									else if(power <= -1)
										InGame.powerUp.add( new Invincible( (int) (ost.getArea().getCenterX() - ray), (int) ost.getArea().getCenterY(), ray, ost.getMaxHeight() ) );
									else if(power <= 1)
										InGame.powerUp.add( new DoubleShot( (int) (ost.getArea().getCenterX() - ray), (int) ost.getArea().getCenterY(), ray, ost.getMaxHeight() ) );
									else if(power <= 0.8)
										InGame.powerUp.add( new Coin( (int) (ost.getArea().getCenterX() - ray), (int) ost.getArea().getCenterY(), ray, ost.getMaxHeight() ) );
									else
										InGame.powerUp.add( new Life( (int) (ost.getArea().getCenterX() - ray), (int) ost.getArea().getCenterY(), ray, ost.getMaxHeight() ) );
								}
						
							if(ost.getWidth() == gc.getWidth()/32)
								play.setPoint( 50 );
							else if(ost.getWidth() == gc.getWidth()/64)
								play.setPoint( 150 );
							else
								play.setPoint( 300 );
							if(ost.getWidth() > gc.getWidth()/128)
								{
									ost.setXY( (int) ost.getWidth()/2, (int) ost.getWidth()/2, "setRay" );
									
									Bubble temp1 = new Bubble( ost, gc );
									Bubble temp2 = new Bubble( ost, gc );
									
									temp1.setMaxHeight( ost.getMaxHeight() );
									temp2.setMaxHeight( ost.getMaxHeight() );
									
									temp1.setPrimoTubo( ((Bubble) ost).getPrimoTubo() );
									temp2.setPrimoTubo( ((Bubble) ost).getPrimoTubo() );
									
									temp1.setIndexTube( ((Bubble) ost).getIndexTube() );
									temp2.setIndexTube( ((Bubble) ost).getIndexTube() );
									
									float speedX = ((Bubble) ost).getSpeedX(), speedY = ((Bubble) ost).getSpeedY();
									
									if(speedY == 0)
										{
											temp1.setSpeed( speedX, Global.H/Global.Height );
											temp2.setSpeed( -speedX,  Global.H/Global.Height );
										}
									else
										{
											temp1.setSpeed( -speedX, Math.abs( speedY ) );
											temp2.setSpeed( speedX, Math.abs( speedY ) );
										}
									
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
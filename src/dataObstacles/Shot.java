package dataObstacles;

import interfaces.InGame;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

import Utils.Global;
import dataPowers.Ammo;
import dataPowers.Coin;
import dataPowers.Invincible;
import dataPowers.Life;

public class Shot 
{
	private float posX, posY, startY;
	
	private int widthS, heightS;
	// altezza e lunghezza del colpo (uguale per entrambe)
	private int valC;
	
	private Image shot[];
	private ArrayList<Image> sparo;
	
	private SpriteSheet sheetShot;
	private SpriteSheet sheetChainHor;
	
	private boolean shooting;
	
	private int countShot;
	
	// determina se il colpo ha colpito una sfera
	private boolean hit;
	
	public Shot( GameContainer gc ) throws SlickException
		{
			widthS = Global.Width/53;
			heightS = Global.Height*10/285;
			
			valC = Global.Height*10/666;
			
			sheetShot = new SpriteSheet( new Image( "./data/Image/shot.png" ), Global.Width/80, Global.Height*10/375 );
			sheetChainHor = new SpriteSheet( new Image( "./data/Image/chainHor.png" ), Global.Height/100, Global.Height/100 );
		
			shot = new Image[2];
			shot[0] = sheetShot.getSubImage( 0, 0 );
			shot[1] = sheetChainHor.getSubImage( 0, 0 );
			
			sparo = new ArrayList<Image>();
			posY = posY - heightS;
			
			shooting = false;
			
			countShot = 0;
			
			hit = false;
		}
	
	public void setXY( float x, float y )
		{
			sparo.clear();
			sparo.add( shot[0] );
			posX = x;
			posY = y - heightS;
			startY = y;
		}

	public void draw() throws SlickException
		{
			float saveY = startY;
			for(Image fuoco: sparo)
				{
					startY = startY - valC;
					fuoco.draw( posX, startY, valC, valC );
				}
			sparo.get( sparo.size() - 1 ).draw( posX + valC/2 - widthS/2, posY, widthS, heightS );
			
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
	
	/** controlla se lo shot ha colpito una sfera */
	public boolean checkHit()
		{
			if(hit)
				{
					hit = false;
					return true;
				}
			else
				return hit;
		}
	
	public boolean collision( Player play, Ostacolo ost, String type, GameContainer gc ) throws SlickException
		{
			if(getArea().intersects( ost.getArea() ))
				{
					if(type.equals( Global.BOLLA ))
						{
							hit = true;
							if(Math.random() <= Global.dropRate)
								{
									float ray = Global.Height/40;
									double power = Math.random();
									if(power <= 0.2)
										InGame.powerUp.add( new Invincible( ost.getArea().getX(), ost.getArea().getCenterY(), ray, ost.getMaxHeight() ) );
									else if(power <= 0.5)
										InGame.powerUp.add( new Ammo( ost.getArea().getX(), ost.getArea().getCenterY(), ray, ost.getMaxHeight() ) );
									else if(power <= 0.7)
										InGame.powerUp.add( new Coin( ost.getArea().getX(), ost.getArea().getCenterY(), ray, ost.getMaxHeight() ) );
									else
										{
											InGame.powerUp.add( new Life( ost.getArea().getCenterX() - ray, ost.getArea().getCenterY(), ray, ost.getMaxHeight() ) );
											((Life) InGame.powerUp.get( InGame.powerUp.size() - 1 )).setPlayers();
										}
								}
						
							if(ost.getWidth() == Global.Width/16)
								play.setPoint( 50 );
							else if(ost.getWidth() == Global.Width/32)
								play.setPoint( 150 );
							else
								play.setPoint( 300 );
							if(ost.getWidth() > Global.Width/64)
								{
									ost.setXY( ost.getWidth()/4, ost.getWidth()/4, "setRay" );
									
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
											temp1.setSpeed( speedX, Math.abs( speedX/(1.5f) ) );
											temp2.setSpeed( -speedX,  Math.abs( speedX/(1.5f) ) );
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
			posY = posY - valC;
		}
}
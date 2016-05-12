package dataObstacles;

import interfaces.InGame;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;

import bubbleMaster.Start;

public class Bubble extends Ostacolo
{
	private float ray;
	
	private Circle ostr;

	private int speedX = 1, speedY = 1;
	
	private Image immagine = new Image( "./data/Image/palla.png" );
	
	private int maxH;
	private int maxW = 800;
	
	private boolean collided;
	
	private boolean insert = false, checkInsert = false;
	
	private Color cg = new Color( 50, 170, 50, 150 ), cr = new Color( 170, 50, 50, 150 );
	
	public Bubble( Ostacolo ost ) throws SlickException
		{ this( ost.getX(), ost.getY(), (int) ost.getWidth() ); }
	
	public Bubble( float x, float y, float ray ) throws SlickException
		{		
			super( "bolla" );

			collided = false;
			
			this.ray = ray;
			ostr = new Circle( x + ray, y + ray, ray );
		}
	
	public void draw( Graphics g ) throws SlickException
		{
			immagine.draw( ostr.getX(), ostr.getY(), ray*2, ray*2 );
			if(Start.editGame == 1)
				if(checkInsert)
					{
						if(!insert)
							immagine.draw( ostr.getX(), ostr.getY(), ray*2, ray*2, cr);
						else
							immagine.draw( ostr.getX(), ostr.getY(), ray*2, ray*2, cg);
					}
		}
	
	public Circle getArea()
		{ return ostr; }

	public void setXY(int x, int y, String function)
		{
			if(function.compareTo( "move" ) == 0)
				ostr.setLocation( ostr.getX() + x, ostr.getY() + y );
			
			else if(function.compareTo( "restore" ) == 0)
				ostr.setLocation( x, y );
			
			else if(function.compareTo( "setRay" ) == 0)
				ray = x;
		}

	public boolean contains( int x, int y )
		{ return ostr.contains( x, y ); }

	public int getX()
		{ return (int) ostr.getX(); }

	public int getY()
		{ return (int) ostr.getY(); }
	
	public void setMaxHeight( double val )
		{ maxH = (int) val; }
	
	public double getMaxHeight()
		{ return maxH; }

	public Shape component( String part )
		{ return ostr; }

	public float getWidth()
		{ return ray; }

	public float getHeight()
		{ return ray; }
	
	public void setCollided( boolean val )
		{ collided = val; }
	
	public boolean isCollided()
		{ return collided; }

	public void setInsert(boolean insert, boolean change)
		{
			checkInsert = !change;
			this.insert = insert;
		}

	public int getSpeedX()
		{ return speedX; }

	public int getSpeedY()
		{ return speedY; }

	public void setSpeedX( int val )
		{ speedX = val; }

	public void setSpeedY( int val )
		{ speedY = val; }
	
	private void setCenter( Shape ostr, float x, float y )
		{
			ostr.setCenterX( ostr.getCenterX() + x );
			ostr.setCenterY( ostr.getCenterY() + y );
		}
	
	public boolean checkCollide( Bubble circleTest, Ostacolo ost, float distX, float distY )
		{
			float testSpeedX = -((float) speedX/5f), testSpeedY = -((float) speedY/5f);
			if(circleTest.component( "rect" ).intersects( ost.component( "rect" ) ))
				{
					while(circleTest.ostr.intersects( ost.component( "rect" ) ))
						{
							distX = distX + testSpeedX;
							distY = distY + testSpeedY;
							circleTest.setCenter( circleTest.ostr, testSpeedX, testSpeedY );
						}
					return true;
				}
		
			return false;
		}

	public void update( GameContainer gc ) throws SlickException
		{			
			boolean collide = false;
			boolean adjuste = false;
			float distX = 0, distY = 0;

			setCenter( ostr, speedX, speedY );

			Bubble circleTest = (Bubble) this.clone();
			circleTest.setXY( -speedX, -speedY, "move" );
			
			// TODO SISTEMARE LA COMPENETRAZIONE SFERE-OSTACOLI
			for(int i = 0; i < InGame.ostacoli.size(); i++)
				{
					Ostacolo ost = InGame.ostacoli.get( i );
					if(!ost.ID.equals( "bolla" ))
						{
							if(ostr.intersects( ost.component( "rect" ) ))
								adjuste = checkCollide( circleTest, ost, distX, distY );
							if(adjuste)
								{
									collide = true;
									
									if(ostr.intersects( ost.component( "latoSu" ) ) || ostr.intersects( ost.component( "latoGiu" ) ))
										speedY = -speedY;
									else if(ostr.intersects( ost.component( "latoDx" ) ) || ostr.intersects( ost.component( "latoSx" ) ))
										speedX = -speedX;

									if(ostr.intersects( ost.component( "spigADx" ) ))
										{	
											if(speedX < 0 && speedY > 0)
												{
													speedX = -speedX;
													speedY = -speedY;
												}
											else if(speedX > 0 && speedY > 0)
												speedY = -speedY;
											else
												speedX = -speedX;
										}
									else if(ostr.intersects( ost.component( "spigBDx" ) ))
										{
											if(speedX < 0 && speedY < 0)
												{
													speedX = -speedX;
													speedY = -speedY;
												}
											else if(speedX < 0 && speedY > 0)
												speedX = -speedX;
											else
												speedY = -speedY;
										}
									else if(ostr.intersects( ost.component( "spigASx" ) ))
										{
											if(speedY > 0 && speedX > 0)
												{
													speedX = -speedX;
													speedY = -speedY;
												}
											else if(speedX > 0 && speedY < 0)
												speedX = -speedX;
											else
												speedY = -speedY;
										}
									else if(ostr.intersects( ost.component( "spigBSx" ) ))
										{
											if(speedX > 0 && speedY < 0)
												{
													speedX = -speedX;
													speedY = -speedY;
												}
											else if(speedX < 0 && speedY < 0)
												speedY = -speedY;
											else
												speedX = -speedX;
										}
									else if(ostr.intersects( ost.component( "latoSx" ) ) || ostr.intersects( ost.component( "latoDx" ) ))
										speedX = -speedX;
									else if(ostr.intersects( ost.component( "latoSu" ) ) || ostr.intersects( ost.component( "latoGiu" ) ))
										speedY = -speedY;
								}
						}
				}
			
			if(!collide)
				{
					if(ostr.getX() + 2*ray >= maxW || ostr.getX() <= 0)
						speedX = -speedX;
					if(ostr.getY() + 2*ray >= maxH || ostr.getY() <= 0)
						speedY = -speedY;
				}
			
			if(adjuste)
				setCenter( ostr, distX, distY );
		}

	public void setType(String type)
		{}

	public float getMaxX()
		{ return ray*2; }

	public Ostacolo clone() {
		try {
			return new Bubble( ostr.getX(), ostr.getY(), ray );
		} catch (SlickException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		// TODO Auto-generated method stub
		
	}
}

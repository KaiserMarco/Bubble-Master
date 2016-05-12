package dataObstacles;

import interfaces.InGame;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;

public class Bubble extends Ostacolo
{
	private int ray;
	
	private Circle ostr;

	private int speedX = 1, speedY = 1;
	
	private Image immagine = new Image( "./data/Image/palla.png" );
	
	private int maxH;
	private int maxW = 800;
	
	private boolean collided;
	
	public Bubble( Ostacolo ost ) throws SlickException
		{ this( ost.getX(), ost.getY(), (int) ost.getWidth() ); }
	
	public Bubble( int x, int y, int ray ) throws SlickException
		{		
			super( "bolla" );

			collided = false;
			
			this.ray = ray;
			ostr = new Circle( x + ray, y + ray, ray );
		}
	
	public void draw( Graphics g ) throws SlickException
		{ immagine.draw( ostr.getX(), ostr.getY(), ray*2, ray*2 );
		g.draw( ostr );
		g.draw( new Circle( ostr.getCenterX(), ostr.getCenterY(), getWidth() ) );}
	
	public Circle getArea()
		{ return ostr; }

	public boolean contains( int x, int y )
		{ return ostr.contains( x, y ); }

	public int getX()
		{ return (int) ostr.getX(); }

	public int getY()
		{ return (int) ostr.getY(); }
	
	public void setMaxHeight( int val )
		{ maxH = val; }
	
	public int getMaxHeight()
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

	public int getSpeedX()
		{ return speedX; }

	public int getSpeedY()
		{ return speedY; }

	public void setSpeedX( int val )
		{ speedX = val; }

	public void setSpeedY( int val )
		{ speedY = val; }

	public void setXY( int x, int y, String function )
		{
			if(function.equals( "move" ))
				ostr.setLocation( ostr.getX() + x, ostr.getY() + y );
			
			else if(function.equals( "restore" ))
				ostr.setLocation( x, y );
			
			else if(function.equals( "setRay" ))
				ray = x;
			
			else if(function.equals( "collide" ))
				{
					ostr.setLocation( ostr.getX() + x, ostr.getY() + y );
					setCenter( ostr, x, y );
				}
		}
	
	private void setCenter( Shape ostr, int x, int y )
		{
			ostr.setCenterX( ostr.getCenterX() + x );
			ostr.setCenterY( ostr.getCenterY() + y );
		}
	
	// TODO DEVE DIVENTARE UN CONTROLLO DELLE LINEE SPIGOLO-CENTRO E MOVE-CENTRO
	public boolean checkCollide( Bubble circleTest, Ostacolo ost )
		{
			//if(Math.sqrt( circleTest.getX() ))
		
			return false;
		}

	public void update( GameContainer gc ) throws SlickException
		{			
			boolean collide = false;
			boolean adjuste = false;

			//setXY( speedX, speedY, "move" );

			Bubble circleTest = (Bubble) this.clone();
			circleTest.setXY( -speedX, -speedY, "move" );
			
			// TODO SISTEMARE LA COMPENETRAZIONE SFERE-OSTACOLI
			for(int i = 0; i < InGame.ostacoli.size(); i++)
				{
					Ostacolo ost = InGame.ostacoli.get( i );
					if(!ost.ID.equals( "bolla" ))
						{
							if(ostr.intersects( ost.component( "rect" ) ))
								{
									collide = true;
									if(ostr.intersects( ost.component( "latoSu" ) ) || ostr.intersects( ost.component( "latoGiu" ) ))
										speedY = -speedY;
									else if(ostr.intersects( ost.component( "latoDx" ) ) || ostr.intersects( ost.component( "latoSx" ) ))
										speedX = -speedX;
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
		}

	public void setType(String type)
		{}

	public float getMaxX()
		{ return ray*2; }

	public Ostacolo clone() {
		try {
			return new Bubble( (int) ostr.getX(), (int) ostr.getY(), ray );
		} catch (SlickException e) {
			e.printStackTrace();
			return null;
		}
	}
}

package dataObstacles;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import bubbleMaster.Start;

public class Sbarra extends Ostacolo{

	Image immagine = new Image( "./data/Image/sbarra.png" );
	int width = 130;
	int heigth = 20;
	
	public Rectangle ostr;
	
	/*sottodivisione del rettangolo in lati e spigoli*/
	public Rectangle latoSu, latoGiu, latoDx, latoSx;
	public Rectangle spigASx, spigADx, spigBSx, spigBDx;
	
	private boolean insert = false, checkInsert = false;	

	private Color cg = new Color( 50, 170, 50, 150 ), cr = new Color( 170, 50, 50, 150 );
	
	public Sbarra( int x, int y ) throws SlickException
		{
			super( "sbarra" );
		
			ostr = new Rectangle( x, y, width, heigth );
			
			/*creazione lati*/
			latoSu = new Rectangle( ostr.getX() + 1, ostr.getY(), ostr.getWidth() - 2, 1 );
			latoGiu = new Rectangle( ostr.getX() + 1, ostr.getY() + ostr.getHeight() - 1, ostr.getWidth() - 2, 1 );
			latoSx = new Rectangle( ostr.getX(), ostr.getY() + 1, 1, ostr.getHeight() - 2 );
			latoDx = new Rectangle( ostr.getX() + ostr.getWidth() - 1, ostr.getY() + 1, 1, ostr.getHeight() - 2 );
			
			/*creazione spigoli*/
			spigASx = new Rectangle( ostr.getX(), ostr.getY(), 1, 1 );
			spigADx = new Rectangle( ostr.getX() + ostr.getWidth() - 1, ostr.getY(), 1, 1 );
			spigBSx = new Rectangle( ostr.getX(), ostr.getY() + ostr.getHeight() - 1, 1, 1 );
			spigBDx = new Rectangle( ostr.getX() + ostr.getWidth() - 1, ostr.getY() + ostr.getHeight() - 1, 1, 1 );
		}
	
	public Sbarra clone()
		{ try {
			return new Sbarra( (int) ostr.getX(), (int) ostr.getY() );
		} catch (SlickException e) {
			e.printStackTrace();
			return null;
		} }
	
	public void draw( Graphics g ) throws SlickException
		{
			immagine.draw( ostr.getX(), ostr.getY(), ostr.getWidth(), ostr.getHeight() );
			if(Start.editGame == 1)
				if(checkInsert)
					if(!insert)
						immagine.draw( ostr.getX(), ostr.getY(), ostr.getWidth(), ostr.getHeight(), cr);
					else
						immagine.draw( ostr.getX(), ostr.getY(), ostr.getWidth(), ostr.getHeight(), cg);
			
			g.draw( ostr );
			g.draw( new Rectangle( ostr.getX(), ostr.getY(), ostr.getMaxX() - ostr.getX(), heigth ) );
		}
	
	public float getX()
		{ return (int)ostr.getX(); }

	public float getY()
		{ return (int)ostr.getY(); }
	
	public float getWidth()
		{ return ostr.getWidth(); }
	
	public float getHeight()
		{ return ostr.getHeight(); }
	
	public boolean contains( int x, int y )
		{ return ostr.contains( x, y ); }

	public void setInsert(boolean insert, boolean change)
		{
			checkInsert = !change;
			this.insert = insert;
		}
	
	public void setXY( float x, float y, String function )
		{			
			if(function.compareTo( "move" ) == 0)
				ostr.setLocation( ostr.getX() + x, ostr.getY() + y );
			
			else if(function.compareTo( "restore" ) == 0)
				ostr.setLocation( x, y );
			
			latoSu.setLocation( ostr.getX() + 1, ostr.getY() );
			latoGiu.setLocation( ostr.getX() + 1, ostr.getY() + ostr.getHeight() - 1 );
			latoSx.setLocation( ostr.getX(), ostr.getY() + 1 );
			latoDx.setLocation( ostr.getX() + ostr.getWidth() - 1, ostr.getY() + 1 );
			
			spigASx.setLocation( ostr.getX(), ostr.getY() );
			spigADx.setLocation( ostr.getX() + ostr.getWidth() - 1, ostr.getY() );
			spigBSx.setLocation( ostr.getX(), ostr.getY() + ostr.getHeight() - 1 );
			spigBDx.setLocation( ostr.getX() + ostr.getWidth() - 1, ostr.getY() + ostr.getHeight() - 1 );
		}

	public Rectangle component( String part ) 
		{
			if(part.equals( "latoSu" ))
				return latoSu;
			else if(part.equals( "latoGiu" ))
				return latoGiu;
			else if(part.equals( "latoSx" ))
				return latoSx;
			else if(part.equals( "latoDx" ))
				return latoDx;
			else if(part.equals( "spigASx" ))
				return spigASx;
			else if(part.equals( "spigADx" ))
				return spigADx;
			else if(part.equals( "spigBSx" ))
				return spigBSx;
			else if(part.equals( "spigBDx" ))
				return spigBDx;
			else if(part.equals( "rect" ))
				return ostr;
			
			return null;
		}

	public float getMaxX()
		{ return ostr.getX() + width; }

	public void setType(String type) 
		{}

	public void update( GameContainer gc ) 
		{}

	public Shape getArea()
		{ return ostr; }
	
	public void setMaxHeight( double val )
		{}
	
	public double getMaxHeight()
		{ return 0; }

	public int getSpeedX()
		{ return 0; }

	public int getSpeedY()
		{ return 0; }

	public void setSpeedX(int val)
		{}

	public void setSpeedY(int val)
		{}
	
	public void setCollided( boolean val )
		{}
	
	public boolean isCollided()
		{ return true; }

	public void update(GameContainer gc, int delta) throws SlickException 
		{}

	public double getMaxWidth()
		{ return 0; }
}























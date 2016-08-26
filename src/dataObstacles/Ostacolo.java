package dataObstacles;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public abstract class Ostacolo 
{
	public Rectangle ostr;
	
	public Shape latoSu, latoGiu, latoSx, latoDx;
	public Shape spigASx, spigADx, spigBSx, spigBDx;
	
	private String ID;
	
	public Ostacolo( String ID ) throws SlickException
		{ this.ID = ID; }
	
	public String getID()
		{ return ID; }
	
	public abstract void draw( Graphics g ) throws SlickException;
	
	public abstract float getX();	
	public abstract float getY();
	
	public abstract int getSpeedX();
	public abstract int getSpeedY();
	public abstract void setSpeed( Integer x, Integer y );
	
	public abstract void setInsert( boolean insert, boolean change );
	
	public abstract Ostacolo clone();
	
	public abstract void setMaxHeight( double val );	
	public abstract double getMaxHeight();
	public abstract double getMaxWidth();
	
	public abstract void setType( String type );
	
	public abstract Shape component( String part );
	
	public abstract boolean isCollided();
	public abstract void setCollided( boolean val );
	
	public abstract float getMaxX();
	
	public abstract float getWidth();	
	public abstract float getHeight();
	
	public abstract boolean contains( int x, int y );
	
	public abstract void setXY( float x, float y, String function );
	
	public abstract void update( GameContainer gc ) throws SlickException;
	public abstract void update( GameContainer gc, int delta ) throws SlickException;
	
	public abstract boolean getCollide();
	public abstract void setCollide( boolean val );
	
	public abstract void setOrienting( String direction );
	public abstract String getOrienting();

	public abstract Shape getArea();
	
	public abstract void setSpigoli();
	
	public abstract int getUnion();
	public abstract void setUnion( int val );
	
	public abstract Point getMidArea();
}

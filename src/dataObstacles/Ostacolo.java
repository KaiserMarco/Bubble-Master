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
	
	public String ID; 
	
	public Ostacolo( String ID ) throws SlickException
		{ this.ID = ID; }
	
	public String getID()
		{ return ID; }
	
	public abstract void draw( Graphics g ) throws SlickException;
	
	public abstract int getX();	
	public abstract int getY();
	
	public abstract int getSpeedX();
	public abstract int getSpeedY();
	public abstract void setSpeedX( int val );
	public abstract void setSpeedY( int val );
	
	public abstract void setInsert( boolean insert, boolean change );
	
	public abstract Ostacolo clone();
	
	public abstract void setMaxHeight( double val );	
	public abstract double getMaxHeight(); 
	
	public abstract void setType( String type );
	
	public abstract Shape component( String part );
	
	public abstract boolean isCollided();
	public abstract void setCollided( boolean val );
	
	public abstract float getMaxX();
	
	public abstract float getWidth();	
	public abstract float getHeight();
	
	public abstract boolean contains( int x, int y );
	
	public abstract void setXY( int x, int y, String function );
	
	public abstract void update( GameContainer gc ) throws SlickException;

	public abstract Shape getArea();
}

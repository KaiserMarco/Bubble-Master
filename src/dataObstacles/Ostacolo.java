package dataObstacles;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;

public abstract class Ostacolo 
{	
	private String ID;
	
	public Ostacolo( String ID ) throws SlickException
		{ this.ID = ID; }
	
	public String getID()
		{ return ID; }
	
	public abstract void draw( Graphics g ) throws SlickException;
	
	public abstract float getRotate();
	public abstract void setRotate( float val );
	
	public abstract float getX();	
	public abstract float getY();
	
	public abstract void setInsert( boolean insert, boolean change );
	
	public abstract Ostacolo clone( GameContainer gc );
	
	public abstract void setMaxHeight( double val );	
	public abstract double getMaxHeight();
	public abstract double getMaxWidth();
	
	public abstract boolean contains( Shape shape );
	
	public abstract void setArea( GameContainer gc );
	
	public abstract void setType( String type );
	
	public abstract Shape component( String part );
	
	public abstract float getMaxX();
	public abstract float getMaxY();
	
	public abstract void setWidth( float val );
	public abstract float getWidth();

	public abstract void setHeight( float val );
	public abstract float getHeight();
	
	public abstract boolean contains( int x, int y );
	
	public abstract void setXY( float x, float y, String function );
	
	public abstract void update( GameContainer gc ) throws SlickException;
	public abstract void update( GameContainer gc, int delta ) throws SlickException;
	
	public abstract void setOrienting( GameContainer gc )  throws SlickException;
	public abstract String getOrienting();

	public abstract Shape getArea();
	
	public abstract void setSpigoli() throws SlickException;
	
	public abstract int getUnion();
	public abstract void setUnion( int val );
	
	public abstract float[] getMidArea();
}

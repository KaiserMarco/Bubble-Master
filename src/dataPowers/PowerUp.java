package dataPowers;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import dataObstacles.Player;

public abstract class PowerUp
{	
	private String ID;
	
	public PowerUp( String ID )
		{ this.ID = ID; }
	
	public String getID()
		{ return ID; }
	
	public abstract void update( GameContainer gc, int delta );
	
	public abstract void draw( Graphics g ) throws SlickException;
	
	public abstract Rectangle getArea();
	
	public abstract Image getImage();
	
	public abstract boolean isArrived();
	
	public abstract void effect( Player player, GameContainer gc )  throws SlickException;
}

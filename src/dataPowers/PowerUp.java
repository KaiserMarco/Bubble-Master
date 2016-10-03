package dataPowers;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;

public abstract class PowerUp
{	
	private String ID;
	
	public PowerUp( String ID )
		{ this.ID = ID; }
	
	public String getID()
		{ return ID; }
	
	public abstract void update( GameContainer gc, int delta );
	
	public abstract void draw( Graphics g );
	
	public abstract Circle getArea();
}

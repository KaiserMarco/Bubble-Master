package dataPowers;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;

public abstract class PowerUp
{
	public Circle ostr;
	public double maxH;
	
	public PowerUp()
		{}
	
	public abstract void update( GameContainer gc, int delta );
	
	public abstract void draw( Graphics g );
	
	public abstract Circle getArea();
}

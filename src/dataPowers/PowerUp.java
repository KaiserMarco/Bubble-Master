package dataPowers;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;

public abstract class PowerUp
{
	public Circle ostr;
	public double maxH;
	
	public PowerUp( int x, int y, int ray, double maxH )
		{
			ostr = new Circle( x, y, ray );
			this.maxH = maxH;
		}
	
	public abstract void update( GameContainer gc, int delta );
	
	public abstract void draw( Graphics g );
}

package dataPowers;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;

public class DoubleShot extends PowerUp
{
	private double maxH;
	private Circle ostr;
	
	// determina se l'oggetto ha raggiunto terra
	private boolean arrived = false;
	
	public DoubleShot( int x, int y, int ray, double maxH )
		{
			super( x, y, ray, maxH );
		}
	
	public void update(GameContainer gc, int delta)
		{
			if(!arrived)
				if(ostr.getY() > maxH)
					ostr.setCenterY( ostr.getY() - delta );
				else
					{
						ostr.setCenterY( (float) maxH - ostr.getRadius()*2 );
						arrived = true;
					}
		}
	
	public void draw( Graphics g )
		{
			g.fill( ostr );
		}
}

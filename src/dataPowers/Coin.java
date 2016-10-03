package dataPowers;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;

public class Coin extends PowerUp
{
	private double maxH;
	private Circle ostr;
	
	// determina se l'oggetto ha raggiunto terra
	private boolean arrived = false;
	
	public Coin( int x, int y, int ray, double maxH )
		{
			super( "coin" );
		
			ostr = new Circle( x, y, ray );
			this.maxH = maxH;
		}
	
	public Circle getArea()
		{ return ostr; }

	public void update(GameContainer gc, int delta)
		{
			if(!arrived)
				{
					if(ostr.getY() + ostr.getRadius()*2 < maxH)
						ostr.setCenterY( ostr.getCenterY() + delta/5 );
					else
						{
							ostr.setCenterY( (float) maxH - ostr.getRadius() );
							arrived = true;
						}
				}
		}
	
	public void draw( Graphics g )
		{
			g.fill( ostr );
		}
}

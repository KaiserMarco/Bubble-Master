package dataPowers;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;

import Utils.Global;

public class TripleShot extends PowerUp
{
	private double maxH;
	private Circle ostr;
	private Image img;
	
	// determina se l'oggetto ha raggiunto terra
	private boolean arrived = false;
	
	public TripleShot( int x, int y, int ray, double maxH ) throws SlickException
		{
			super( "tShot" );
			
			ostr = new Circle( x, y, ray );
			this.maxH = maxH;
			
			img = new Image( "./data/Image/bomba.png" );
		}
	
	public Circle getArea()
		{ return ostr; }
	
	public Image getImage()
		{ return img; }

	public void update(GameContainer gc, int delta)
		{
			if(!arrived)
				{
					if(ostr.getY() + ostr.getRadius()*2 < maxH)
						ostr.setCenterY( ostr.getCenterY() + (delta/5 * Global.H/Global.Height) );
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

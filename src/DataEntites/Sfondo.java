package DataEntites;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;

public class Sfondo
{
	private Image sfondo;
	private double maxHeight, maxWidth;
	
	public Sfondo( Image sfondo, double maxH, double maxW )
		{
			this.sfondo = sfondo;
			this.maxHeight = maxH;
			this.maxWidth = maxW;
		}
	
	public Image getSfondo()
		{ return sfondo; }
	
	public void setSfondo( Image sfondo )
		{ this.sfondo = sfondo; }
	
	public double getMaxWidth()
		{ return maxWidth; }
	
	public double getMaxHeight()
		{ return maxHeight; }
	
	public void setMaxHeight( int maxH )
		{ this.maxHeight = maxH; }
	
	public void draw( GameContainer gc )
		{ sfondo.draw( 0, 0, gc.getWidth(), gc.getHeight() ); }
}

package DataEntites;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;

public class Sfondo
{
	private Image sfondo;
	private int maxHeight;
	
	public Sfondo( Image sfondo, int index )
		{
			this.sfondo = sfondo;
			
			if(index == 0)
				maxHeight = 438;
			else if(index == 1 || index == 4)
				maxHeight = 600;
			else if(index == 2)
				maxHeight = 420;
			else if(index == 3)
				maxHeight = 575;
		}
	
	public Image getSfondo()
		{ return sfondo; }
	
	public void setSfondo( Image sfondo )
		{ this.sfondo = sfondo; }
	
	public int getMaxHeight()
		{ return maxHeight; }
	
	public void setMaxHeight( int maxH )
		{ this.maxHeight = maxH; }
	
	public void draw( GameContainer gc )
		{ sfondo.draw( 0, 0, gc.getWidth(), gc.getHeight() ); }
}

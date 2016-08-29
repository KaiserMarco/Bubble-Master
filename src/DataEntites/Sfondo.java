package DataEntites;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

public class Sfondo
{
	private Image sfondo;
	private double maxHeight, maxWidth;
	private int x, y, width, height;
	//il nome dello sfondo
	private String name;
	
	public Sfondo( Image sfondo, double maxH, double maxW, int x, int y, int width, int height, String name )
		{
			this.sfondo = sfondo;
			this.maxHeight = maxH;
			this.maxWidth = maxW;
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
			this.name = name;
		}
	
	public String getName()
		{ return name; }
	
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
	
	public void drawArea( GameContainer gc )
		{ gc.getGraphics().fill( new Rectangle( x, y, width, height ) ); }
	
	/**disegna lo sfondo a schermo intero*/
	public void draw( GameContainer gc )
		{ sfondo.draw( 0, 0, gc.getWidth(), gc.getHeight() ); }
	
	/**disegna lo sfondo nella selezione sfondo*/
	public void draw()
		{ sfondo.draw( x, y, width, height ); }
	
	public int getX()
		{ return x; }

	public int getY()
		{ return y; }
	
	public boolean contains( int x, int y )
		{ return new Rectangle( this.x, this.y, width, height ).contains( x, y ); }
}
package Utils;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

public class Sfondo
{
	private Image sfondo;
	private float maxHeight, maxWidth;
	private float x, y, width, height;
	//il nome dello sfondo
	private String name;
	//l'indice dello sfondo
	private int index;
	
	public Sfondo( Image sfondo, float maxH, float maxW, int x, int y, int width, int height, String name, int index )
		{
			this.sfondo = sfondo;
			this.maxHeight = maxH;
			this.maxWidth = maxW;
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
			this.name = name;
			this.index = index;
		}
	
	public int getIndex()
		{ return index; }
	
	public String getName()
		{ return name; }
	
	public Image getSfondo()
		{ return sfondo; }
	
	public void setSfondo( Image sfondo )
		{ this.sfondo = sfondo; }
	
	public void setX( float val )
		{ x = val; }
	
	public void setY( float val )
		{ y = val; }
	
	public void setWidth( float val )
		{ width = val; }
	
	public void setHeight( float val )
		{ height = val; }
	
	public float getWidth()
		{ return width; }
	
	public float getHeight()
		{ return height; }
	
	public float getMaxWidth()
		{ return maxWidth; }

	public void setMaxWidth( float val )
		{ maxWidth = val; }
	
	public float getMaxHeight()
		{ return maxHeight; }
	
	public void setMaxHeight( float maxH )
		{ this.maxHeight = maxH; }
	
	public void drawArea( GameContainer gc )
		{ gc.getGraphics().fill( new Rectangle( x, y, width, height ) ); }
	
	/**disegna lo sfondo a schermo intero*/
	public void draw( GameContainer gc )
		{ sfondo.draw( 0, 0, Global.Width, Global.Height ); }
	
	/**disegna lo sfondo nella selezione sfondo*/
	public void draw()
		{ sfondo.draw( x, y, width, height ); }
	
	public float getX()
		{ return x; }

	public float getY()
		{ return y; }
	
	public boolean contains( int x, int y )
		{ return new Rectangle( this.x, this.y, width, height ).contains( x, y ); }
}
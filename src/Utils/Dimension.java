package Utils;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Rectangle;

public class Dimension
{
	/** il rettangolo della dimensione */
	private Rectangle ostr;
	/** le dimensioni della finestra */
	private String w, h;
	/** determina se disegnare la casella di grigio o di bianco */
	private Color color;
	/** determina se il valore e' disegnabile */
	private boolean drawble;
	
	public Dimension( float x, float y, float width, float height, String w, String h, Color c, boolean val )
		{
			ostr = new Rectangle( x, y, width, height );
			
			this.w = w;
			this.h = h;
			
			color = c;
			
			drawble = val;
		}
	
	public boolean isDrawble()
		{ return drawble; }
	
	public void setDrawble( boolean val )
		{ drawble = !drawble; }
	
	public Color getColor()
		{ return color; }
	
	public boolean contains( float mouseX, float mouseY )
		{ return ostr.contains( mouseX, mouseY ); }
	
	public Rectangle getArea()
		{ return ostr; }
	
	public String getW()
		{ return w; }
	
	public String getH()
		{ return h; }
	
	public String getFullName()
		{ return w + "x" + h; }
	
	public void setName( String w, String h )
		{ 
			this.w = w;
			this.h = h;
		}
}

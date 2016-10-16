package Utils;

import org.newdawn.slick.geom.Rectangle;

public class Dimension
{
	/** il rettangolo della dimensione */
	private Rectangle ostr;
	/** le dimensioni della finestra */
	private String w, h;
	/** determina se disegnare la casella di grigio o di bianco */
	private boolean gray;
	
	public Dimension( float x, float y, float width, float height, String w, String h, boolean val )
		{
			ostr = new Rectangle( x, y, width, height );
			
			this.w = w;
			this.h = h;
			
			gray = val;
		}
	
	public boolean getGray()
		{ return gray; }
	
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

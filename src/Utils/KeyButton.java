package Utils;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

public class KeyButton
{
	private Rectangle ostr;
	
	// il tasto associato al binding
	private String bind;
	
	// determina se il pulsante e' stato selezionato oppure no
	private boolean selected;
	
	public KeyButton( float x, float y , float width )
		{
			ostr = new Rectangle( x, y, width, width );
			
			selected = false;
			
			bind = "";
		}
	
	public void setKey( String val )
		{ bind = val; }
	
	public String getKey()
		{ return bind; }
	
	public boolean isSelected()
		{ return selected; }
	
	public void setSelected()
		{ selected = !selected; }
	
	public float getY()
		{ return ostr.getY(); }
	
	public float getX()
		{ return ostr.getX(); }
	
	public float getMaxX()
		{ return ostr.getMaxX(); }
	
	public float getMaxY()
		{ return ostr.getMaxY(); }
	
	public float getWidth()
		{ return ostr.getWidth(); }
	
	public float getHeight()
		{ return ostr.getHeight(); }
	
	public boolean contains( float mouseX, float mouseY )
		{ return ostr.contains( mouseX, mouseY ); }
	
	public void draw( Graphics g )
		{
			g.setColor( Color.orange );
			g.fill( ostr );
			g.setColor( Color.red );
			if(selected)
				g.draw( ostr );
			
			g.scale( 2, 2 );
			g.drawString( bind, (ostr.getX() + Global.Width/100)/2, (ostr.getY() + Global.Height/300)/2 );
			g.resetTransform();
		}
}

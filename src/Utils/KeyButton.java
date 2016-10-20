package Utils;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

public class KeyButton
{
	private Rectangle ostr;
	
	// determina se il pulsante e' stato selezionato oppure no
	private boolean selected;
	
	public KeyButton( float x, float y , float width, float height )
		{
			ostr = new Rectangle( x, y, width, height );
			
			selected = false;
		}
	
	public boolean isSelected()
		{ return selected; }
	
	public void setSelected()
		{ selected = !selected; }
	
	public boolean contains( float mouseX, float mouseY )
		{ return ostr.contains( mouseX, mouseY ); }
	
	public void draw( Graphics g )
		{
			g.setColor( Color.orange );
			g.fill( ostr );
			g.setColor( Color.red );
			if(selected)
				g.draw( ostr );
		}
}

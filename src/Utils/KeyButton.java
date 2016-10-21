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
	
	private float width;
	
	public KeyButton( float x, float y , float width )
		{
			ostr = new Rectangle( x, y, width, width );
			
			selected = false;
			
			bind = "";
			
			this.width = width;
		}
	
	public void setKey( String val )
		{ bind = val; }
	
	public String getKey()
		{ return bind; }
	
	public boolean isSelected()
		{ return selected; }
	
	public void setSelected()
		{ selected = !selected; }
	
	public boolean contains( float mouseX, float mouseY )
		{ return ostr.contains( mouseX, mouseY ); }
	
	public void updateDates()
		{
			width = width*Global.ratioW;
			ostr = new Rectangle( ostr.getX()*Global.ratioW, ostr.getY()*Global.ratioH, width, width );
		}
	
	public void draw( Graphics g )
		{
			g.setColor( Color.orange );
			g.fill( ostr );
			g.setColor( Color.red );
			if(selected)
				g.draw( ostr );
			
			g.scale( 2, 2 );
			g.scale( Global.W/Global.Width, Global.H/Global.Height );
			g.drawString( bind, ((ostr.getX() + Global.W/100)/2)*Global.Width/Global.W, ((ostr.getY() + Global.H/300)/2)*Global.Height/Global.H );
			g.resetTransform();
		}
}

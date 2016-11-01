package Utils;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.geom.Rectangle;

public class KeyButton
{
	private Rectangle ostr;
	
	// il tasto associato al binding
	private String bind = "";
	private int sizeW = 0, sizeH = 0;
	
	private static UnicodeFont font = null;
	
	// determina se il pulsante e' stato selezionato oppure no
	private boolean selected;
	
	private static final float OFFSET_X = 5, OFFSET_Y = 0;
	
	@SuppressWarnings("unchecked")
    public KeyButton( float x, float y , float height ) throws SlickException
		{
    	    if(font == null) {
                font = new UnicodeFont( "./data/fonts/prstart.ttf", 25, false, true );
                font.addAsciiGlyphs();
                font.addGlyphs( Global.Width, Global.Height );
                font.getEffects().add( new ColorEffect( java.awt.Color.WHITE ) );
                font.loadGlyphs();
            }
	        
	        final float maxWidth = font.getWidth( "SPACE" ) + OFFSET_X * 2;
			ostr = new Rectangle( x, y, maxWidth, height + OFFSET_Y * 2 );
			
			selected = false;
		}
	
	public void setKey( String val )
		{
	        bind = val;
	        // Update the dimension of the key.
	        sizeW = font.getWidth( bind );
	        sizeH = font.getHeight( bind );
	    }
	
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
			
			float X = ostr.getX() + ((ostr.getWidth() - sizeW) / 2);
			float Y = ostr.getY() + ((ostr.getHeight() - sizeH) / 2);
			font.drawString( X, Y, bind, Color.red );
		}
}

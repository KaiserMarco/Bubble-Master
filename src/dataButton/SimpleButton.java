
package dataButton;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.geom.Rectangle;

public class SimpleButton extends Button
{
	/* determina se e' possibile premere il tasto */
	private boolean active = true;
	/* il colore del bottone */
	private Color c;
	
	public static UnicodeFont font;
	public static float ratioH = 1, ratioW = 1;
	/* offset per lo spostamento */
	private float offset = 20.f * ratioH;
	
	//private float posX, posY;

	/** crea un nuovo bottone
	 * @param x - coordinata X
	 * @param y - coordinata Y
	 * @param name - il nome del bottone
	 * @throws SlickException 
	*/
	@SuppressWarnings("unchecked")
    public SimpleButton( float x, float y, String name, Color color ) throws SlickException
		{
			super();
			
			c = color;
			
			if(font == null) {
				font = new UnicodeFont( "./data/fonts/prstart.ttf", (int)(10.f * ratioH), false, true );
				font.addAsciiGlyphs();
		        font.addGlyphs( 600, 400 );
		        font.getEffects().add( new ColorEffect( java.awt.Color.WHITE ) );
		        font.loadGlyphs();
			}
			
			int width = font.getWidth( name ), height = font.getHeight( name );
			rect = new Rectangle( x, y, width + offset, height + offset );
			this.name = name;
		}

	/** modifica il valore di attivazione*/
	public void setActive()
		{ active = !active; }

	/** determina se il tasto e' attivo
	 * @return active - TRUE se e' attivo, FALSE altrimenti
	*/
	public boolean isActive()
		{ return active; }

	public void draw( Graphics g )
		{
			g.setColor( c );
			g.fill( rect );
	
			super.draw( g );
	
			float width = 1.f * ratioH;
			if(pressed)
				font.drawString( rect.getX() + offset/2 + width, rect.getY() + offset/2 + width, name, Color.black );
			else
				font.drawString( rect.getX() + offset/2, rect.getY() + offset/2, name, Color.black );
	
			if(!active)
				{
					g.setColor( new Color( 0, 0, 0, 100 ) );
					g.fill( rect );
				}
		}
	
	/** restituisce il nome del bottone
	 * @return name - il nome
	*/
	/*public String getName()
		{ return name; }*/
}
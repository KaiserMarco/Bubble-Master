
package dataButton;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.geom.Polygon;
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
	/* i punti del bottone triangolare */
	float[] points = new float[6];
	/* lunghezza e altezza del bottone triangolare */
	int width, height;

	/** crea un nuovo bottone rettangolare
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
	
	/** crea un nuovo bottone triangolare
	 * @param x - coordinata X
	 * @param y - coordinata Y
	 * @param width - la lunghezza del bottone
	 * @param heigth - l'altezza del bottone
	 * @throws SlickException 
	*/
	public SimpleButton( float x, float y, String name, int width, int heigth, Image change )
		{
			this.name = name;
			if(name.equals( "Right" ))
				{
					points[0] = x; points[1] = y;
					points[2] = x; points[3] = y + heigth;
					points[4] = x + width; points[5] = y + heigth/2;
				}
			else if(name.equals( "Left" ))
				{
					points[0] = x; points[1] = y + heigth/2;
					points[2] = x + width; points[3] = y;
					points[4] = x + width; points[5] = y + heigth;
				}
			rect = new Polygon( points );
			
			this.change = change;
			this.width = width;
			this.height = heigth;
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
			g.setAntiAlias( true );
			g.setColor( c );
			g.fill( rect );
			
			if(name.equals( "Right" ) || name.equals( "Left" ))
				{
					if(name.equals( "Right" ))
						change.draw( points[0], points[1], width, height );
					else
						change.draw( points[0], points[3], width, height );
				}
			else
				{
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
		}
	
	/** restituisce il nome del bottone
	 * @return name - il nome
	*/
	/*public String getName()
		{ return name; }*/
}
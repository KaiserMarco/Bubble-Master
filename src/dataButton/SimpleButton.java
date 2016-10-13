
package dataButton;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.geom.Rectangle;

import Utils.Global;

public class SimpleButton extends Button
{
	/* determina se e' possibile premere il tasto */
	private boolean active = true;
	/* il colore del bottone */
	private Color c;
	
	public static UnicodeFont font;
	public float ratioH = 0;
	/* offset per lo spostamento */
	private float offset = 20.f * Global.ratioH;
	/* i punti del bottone triangolare */
	float[] points = new float[6];
	/* lunghezza e altezza del bottone triangolare */
	int width, height;
	/* lunghezza e altezza del bottone rettangolare */
	float lungh, alt;

	/** crea un nuovo bottone rettangolare
	 * @param x - coordinata X
	 * @param y - coordinata Y
	 * @param name - il nome del bottone
	 * @throws SlickException 
	*/
    public SimpleButton( float x, float y, String name, Color color ) throws SlickException
		{
			super();
			
			c = color;

			this.name = name;
			buildButton( x, y );
		}
	
	@SuppressWarnings("unchecked")
	public void buildButton( float x, float y ) throws SlickException
		{
			if(ratioH != Global.ratioH)
				{
					ratioH = Global.ratioH;
					
					font = new UnicodeFont( "./data/fonts/prstart.ttf", (int)(10.f * ratioH), false, true );
					font.addAsciiGlyphs();
			        font.addGlyphs( 600, 400 );
			        font.getEffects().add( new ColorEffect( java.awt.Color.WHITE ) );
			        font.loadGlyphs();
				}
			
			int width = font.getWidth( name ), height = font.getHeight( name );
			rect = new Rectangle( x, y, width + offset, height + offset );
			lungh = width + offset; alt = height + offset;
		}
	
	/** modifica la lunghezza del bottone*/
	public float getLungh()
		{  return lungh; }
	
	/** modifica l'altezza del bottone*/
	public float getAlt()
		{  return alt; }

	/** modifica il valore di attivazione*/
	public void setActive()
		{ active = !active; }

	/** determina se il tasto e' attivo
	 * @return active - TRUE se e' attivo, FALSE altrimenti
	*/
	public boolean isActive()
		{ return active; }
	
	/** modifica il nome del livello*/
	public void setName( String name )
		{ this.name = name; }

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
		
					float width = 1.f * Global.ratioH;
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
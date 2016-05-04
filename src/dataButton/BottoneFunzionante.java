package dataButton;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

public class BottoneFunzionante 
{
	Rectangle area;
	String name;
	
	public BottoneFunzionante( int x, int y, String nome, Graphics g )
		{
			Font f = g.getFont();
			area = new Rectangle( x, y, f.getWidth( nome ), f.getHeight( nome ) );
			name = nome;
		}
	
	/**Disegna(draw) un pulsante
	 * @param g - Oggetto grafico
	*/
	public void draw( Graphics g )
		{	
			g.setColor( Color.red );
			g.fill( area );
			g.setColor( Color.white );
			g.draw( area );
			g.drawString( name, area.getX(), area.getY() );
		}
	
	public boolean checkClick( int x, int y )
		{
			return area.contains( x, y );
		}
}

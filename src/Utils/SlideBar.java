
package Utils;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.RoundedRectangle;

public class SlideBar
{
	/* area della barra */
	private RoundedRectangle area;
	/* indicatore del valore */
	private Polygon pointer;
	/* valori di minimo e massimo */
	private float min, max;
	/* nome da visualizzare */
	private String name;
	/* punto in cui e' stato cliccato */
	private int click;
	/* determina se e' stato premuto l'indicatore */
	private boolean pressed = false;

	public SlideBar( float x, float y, String name, float value, float min, float max )
	{
		this.min = min;
		this.max = max;
		this.name = name + ":";

		float w = Global.sizewBox, h = Global.sizehBox;
		area = new RoundedRectangle( x, y, w * 6, h/3, 5.f );
		float maxY = area.getMaxY(), width = w/3;
		// piazza l'indicatore dove indicato da value
		float x_p;
		if(value == min) x_p = area.getX();
		else if(value == max) x_p = area.getMaxX();
		else x_p = area.getX() + (area.getWidth() / (max - min)) * (value - min);

		float points[] = { x_p - width/2, y - h/8, x_p + width/2, y - h/8, x_p + width/2, maxY - h/8, x_p, maxY + h/8, x_p - width/2, maxY - h/8 };
		pointer = new Polygon( points );
	}

	/** determina se le coordinate sono all'interno dell'indicatore
	 * @param x - coordinata X del mouse
	 * @param y - coordinata Y del mouse
	 * 
	 * @return TRUE se le coordinate sono all'interno dell'indicatore, FALSE altrimenti
	*/
	public boolean contains( int x, int y )
	{
		if(pointer.contains( x, y ) || area.contains( x, y )){
			// controlla se far muover il puntatore
			if(!pointer.contains( x, y ) && pointer.getCenterX() != x)
				pointer.setX( area.getX() + (x - area.getX()) - pointer.getWidth() / 2 );
			click = x;
			pressed = true;
			return true;
		}
		else
			return false;
	}
	
	/** restituisce il valore di y
	 * @return il valore della coordinata y
	*/
	public float getY()
		{ return area.getY(); }

	/** determina se il puntatore e' stato cliccato
	 * @return TRUE se e' stato cliccato, FALSE altrimenti
	*/
	public boolean isPressed()
	{
		return pressed;
	}

	/** modifica il valore del click */
	public void setPressed()
	{
		pressed = false;
	}

	/** assegna una nuova coordinata x
	 * @param value - la nuova posizone
	*/
	public void setX( float value )
	{
		float w = pointer.getWidth();
		if(value == min) pointer.setX( area.getX() - w / 2 );
		else if(value == max) pointer.setX( area.getMaxX() - w / 2 );
		else pointer.setX( area.getX() + (area.getWidth() / (max - min)) * (value - min) - w / 2 );
	}

	/** restituisce il valore selezionato
	 * @return la proporzione tra la lunghezza della barra e i valori
	*/
	public float getValue()
	{
		float x = pointer.getX() + pointer.getWidth() / 2;

		if(x == area.getX())
			return min;
		if(x == area.getMaxX())
			return max;

		float x_abs = pointer.getCenterX() - area.getX();
		float max = this.max - min;
		return min + (max / (area.getWidth() / x_abs));
	}

	/** aggiorna la posizione dell'indicatore
	 * @param x - la coordinata X del mouse
	*/
	public void update( int x )
	{
		if(pressed){
			if(x >= area.getX() && x <= area.getMaxX()){
				float updateX = pointer.getX() + (x - click), w = pointer.getWidth();
				if(updateX < area.getX() - w/2)
					updateX = area.getX() - w / 2;
				else{
					if(updateX > area.getMaxX() - w / 2)
						updateX = area.getMaxX() - w / 2;
				}
				pointer.setX( updateX );
			}
			else{
				float w = pointer.getWidth();
				if(x < area.getX())
					pointer.setX( area.getX() - w / 2 );
				else
					pointer.setX( area.getMaxX() - w / 2 );
			}			
			click = x;
		}
	}

	/** disegna l'oggetto
	 * @param g - il contesto grafico
	*/
	public void render( Graphics g )
	{
		// disegna la barra
		g.setColor( Color.gray );
		g.fill( area );
		// colora fino a dove e' il puntatore
		g.setColor( new Color( 25, 186, 255 ) );
		g.fillRoundRect( area.getX(), area.getY(), pointer.getCenterX() - area.getX(), area.getHeight(), (int)area.getCornerRadius() );
		g.setLineWidth( 1.6f );
		g.setColor( Color.black );
		g.draw( area );

		// inserisce le tacchette per capire dove si sta puntando
		int cleats = 20;
		float distance = area.getWidth() / cleats;
		float x = area.getX(), h = Global.sizehBox, startY = area.getMaxY() + h/8;
		g.setLineWidth( 2.f );
		g.setColor( Color.lightGray );
		for(int i = 0; i <= cleats; i++)
			g.drawLine( x + distance * i, startY, x + distance * i, startY + ((i % 4 == 0) ? h/3 : h/5));

		// inserisce i valori minimi e massimi
		float width = Global.stateFont.getWidth( "MIN" );
		Global.stateFont.drawString( area.getX() - width/2, pointer.getMaxY() + h/2, "MIN" );
		width = Global.stateFont.getWidth( "MAX" );
		Global.stateFont.drawString( area.getMaxX() - width/2, pointer.getMaxY() + h/2, "MAX" );

		// inserisce il nome
		width = Global.stateFont.getWidth( name );
		Global.stateFont.drawString( area.getX() - Global.sizewBox/2 - width, area.getY(), name, Color.white );

		// disegna il puntatore
		g.setLineWidth( 1.f );
		g.setColor( Color.lightGray );
		g.fill( pointer );
		g.setColor( Color.black );
		g.draw( pointer );
	}
}
package dataPowers;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;

import dataObstacles.Ostacolo;
import dataObstacles.Player;
import interfaces.InGame;

public class Life extends PowerUp
{
	private double maxH;
	private Circle ostr;
	private Image img = null;
	
	// determina se l'oggetto ha raggiunto terra
	private boolean arrived = false;
	
	// determina quale cuore sto disegnando
	private int index;
	private final long limit = 500;
	
	// determina il tempo corrente di rendering del cuore
	private int currTimeHeart;
	
	// vettore contenenti le immagini delle vite relative dei players
	private ArrayList<Image> cuori;
	
	public Life( float x, float y, float ray, double maxH ) throws SlickException
		{
			super( "life" );
			
			ostr = new Circle( x, y, ray );
			this.maxH = maxH;
			
			cuori = new ArrayList<Image>();
			
			index = 0;
			currTimeHeart = 0;
		}
	
	public Circle getArea()
		{ return ostr; }
	
	public Image getImage()
		{ return img; }
	
	public void setPlayers() throws SlickException
		{
			for(Ostacolo player: InGame.players)
				cuori.add( new Image( "./data/Image/heart" + ((Player) player).getColor() + ".png" ) );
			
			img = cuori.get( 0 );
		}

	public void update(GameContainer gc, int delta)
		{
			currTimeHeart = currTimeHeart + delta;
			if(currTimeHeart >= limit)
				{
					img = cuori.get( (++index)%cuori.size() );
					currTimeHeart = 0;
				}
		
			if(!arrived)
				{
					if(ostr.getY() + ostr.getRadius()*2 < maxH)
						ostr.setCenterY( ostr.getCenterY() + delta/5 );
					else
						{
							ostr.setCenterY( (float) maxH - ostr.getRadius() );
							arrived = true;
						}
				}
		}
	
	public void draw( Graphics g ) throws SlickException
		{ img.draw( ostr.getX(), ostr.getY(), ostr.getWidth(), ostr.getHeight() ); }
}

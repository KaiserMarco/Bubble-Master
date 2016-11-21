package dataPowers;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import Utils.Global;
import dataObstacles.Ostacolo;
import dataObstacles.Player;
import interfaces.InGame;

public class Life extends PowerUp
{
	private float maxH;
	private Rectangle ostr;
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
	
	public Life( float x, float y, float maxH ) throws SlickException
		{
			super( "life" );
			
			ostr = new Rectangle( x, y, Global.Width/24, Global.Height/28 );
			this.maxH = maxH;
			
			cuori = new ArrayList<Image>();
			
			index = 0;
			currTimeHeart = 0;
		}
	
	public Rectangle getArea()
		{ return ostr; }
	
	public Image getImage()
		{ return img; }
	
	public float getWidth()
		{ return ostr.getWidth(); }
	
	public float getHeight()
		{ return ostr.getHeight(); }
	
	public boolean isArrived()
		{ return arrived; }
	
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
					for(Ostacolo ost: InGame.ostacoli)
						if(!(ost.getID().equals( Global.BOLLA ) || ost.getID().equals( Global.TUBO )))
							if(ostr.intersects( ost.getArea() ))
								{
									arrived = true;
									ostr.setY( ost.getArea().getY() - getHeight() );
									break;
								}
				
					if(ostr.getY() + ostr.getHeight() < maxH)
						ostr.setY( ostr.getY() + delta/5 );
					else
						{
							ostr.setY( maxH - ostr.getHeight() );
							arrived = true;
						}
				}
		}
	
	public void draw( Graphics g ) throws SlickException
		{ img.draw( ostr.getX(), ostr.getY(), ostr.getWidth(), ostr.getHeight() ); }
}

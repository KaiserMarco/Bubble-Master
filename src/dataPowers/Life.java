package dataPowers;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;

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
	private long limit;
	private boolean change;
	
	// determina il tempo corrente di rendering del cuore
	private int currTimeHeart;
	
	public Life( int x, int y, float ray, double maxH ) throws SlickException
		{
			super( "life" );
			
			ostr = new Circle( x, y, ray );
			this.maxH = maxH;
			
			index = 0;
			change = false;
			currTimeHeart = 0;
			limit = 500;
		}
	
	public Circle getArea()
		{ return ostr; }
	
	public Image getImage()
		{ return img; }

	public void update(GameContainer gc, int delta)
		{
			currTimeHeart = currTimeHeart + delta;
			if(currTimeHeart >= limit)
				{
					change = true;
					index = (++index)%InGame.players.size();
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
		{
			if(img == null)
				img = new Image( "./data/Image/heart" + ((Player) InGame.players.get( index )).getColor() + ".png" );
			if(change)
				{
					img = new Image( "./data/Image/heart" + ((Player) InGame.players.get( index )).getColor() + ".png" );
					change = false;
					currTimeHeart = 0;
				}
			img.draw( ostr.getX(), ostr.getY(), ostr.getWidth(), ostr.getHeight() );
		}
}

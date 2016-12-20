package dataPowers;

import interfaces.InGame;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import Utils.Global;
import dataObstacles.Ostacolo;
import dataObstacles.Player;

public class Ammo extends PowerUp
{
	private float maxH;
	private Rectangle ostr;
	private Image img;
	
	// determina se l'oggetto ha raggiunto terra
	private boolean arrived = false;
	
	public Ammo( float x, float y, float maxH ) throws SlickException
		{
			super( Global.AMMO );
			
			ostr = new Rectangle( x, y, Global.Width/25, Global.Height/20 );
			this.maxH = maxH;
			
			img = new Image( "./data/Image/bullet.png" );
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
	
	public void update( GameContainer gc, int delta )
		{
			for(Ostacolo ost: InGame.ostacoli)
				if(!(ost.getID().equals( Global.BOLLA ) || ost.getID().equals( Global.TUBO )))
					if(ostr.intersects( ost.getArea() ))
						{
							arrived = true;
							ostr.setY( ost.getArea().getY() - getHeight() );
							break;
						}
		
			if(ostr.getMaxY() < maxH)
				ostr.setY( ostr.getY() + delta/5 );
			else
				{
					ostr.setY( maxH - getHeight() );
					arrived = true;
				}
		}
	
	public void draw( Graphics g )
		{ img.draw( ostr.getX(), ostr.getY(), ostr.getWidth(), ostr.getHeight() ); }
	
	public void effect( Player player, GameContainer gc ) throws SlickException
		{ InGame.players.get( player.getNumPlayer() - 1 ).setNewAmmo( gc ); }
}

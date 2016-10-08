package dataObstacles;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class Enter extends Ostacolo
{
	private Rectangle ostr;
	
	/*sottodivisione dell'area in lati e spigoli*/
	public Rectangle latoSu, latoGiu, latoDx, latoSx;
	public Rectangle spigASx, spigADx, spigBSx, spigBDx;
	
	public final static String ID = "enter";
	
	public Enter( float x, float y, float width, float height ) throws SlickException
		{
			super( "enter" );
			ostr = new Rectangle( x, y, width, height );
			
			/*creazione lati*/
	        latoSu = new Rectangle( ostr.getX() + 1, ostr.getY(), ostr.getWidth() - 2, 1 );
	        latoGiu = new Rectangle( ostr.getX() + 1, ostr.getY() + ostr.getHeight() - 1, ostr.getWidth() - 2, 1 );
	        latoSx = new Rectangle( ostr.getX(), ostr.getY() + 1, 1, ostr.getHeight() - 2 );
	        latoDx = new Rectangle( ostr.getX() + ostr.getWidth() - 1, ostr.getY() + 1, 1, ostr.getHeight() - 2 );
	        
	        /*creazione spigoli*/
	        spigASx = new Rectangle( ostr.getX(), ostr.getY(), 1, 1 );
	        spigADx = new Rectangle( ostr.getX() + ostr.getWidth() - 1, ostr.getY(), 1, 1 );
	        spigBSx = new Rectangle( ostr.getX(), ostr.getY() + ostr.getHeight() - 1, 1, 1 );
	        spigBDx = new Rectangle( ostr.getX() + ostr.getWidth() - 1, ostr.getY() + ostr.getHeight() - 1, 1, 1 );
		}
	
	public void setSpigoli()
		{
			/*creazione lati*/
	        latoSu = new Rectangle( ostr.getX() + 1, ostr.getY(), ostr.getWidth() - 2, 1 );
	        latoGiu = new Rectangle( ostr.getX() + 1, ostr.getY() + ostr.getHeight() - 1, ostr.getWidth() - 2, 1 );
	        latoSx = new Rectangle( ostr.getX(), ostr.getY() + 1, 1, ostr.getHeight() - 2 );
	        latoDx = new Rectangle( ostr.getX() + ostr.getWidth() - 1, ostr.getY() + 1, 1, ostr.getHeight() - 2 );
	        
	        /*creazione spigoli*/
	        spigASx = new Rectangle( ostr.getX(), ostr.getY(), 1, 1 );
	        spigADx = new Rectangle( ostr.getX() + ostr.getWidth() - 1, ostr.getY(), 1, 1 );
	        spigBSx = new Rectangle( ostr.getX(), ostr.getY() + ostr.getHeight() - 1, 1, 1 );
	        spigBDx = new Rectangle( ostr.getX() + ostr.getWidth() - 1, ostr.getY() + ostr.getHeight() - 1, 1, 1 );
		}
	
	public Rectangle getArea()
		{ return ostr; }
	
	public float getWidth()
		{ return ostr.getWidth(); }
	
	public void setWidth( float val )
		{ ostr.setWidth( val ); }
	
	public float getHeight()
		{ return ostr.getHeight(); }
	
	public void setHeight( float val )
		{ ostr.setHeight( val ); }
	
	public float getX()
		{ return ostr.getX(); }
	
	public float getY()
		{ return ostr.getY(); }
	
	public float getMaxX()
		{ return ostr.getMaxX(); }
	
	public float getMaxY()
		{ return ostr.getMaxY(); }
	
	public Rectangle component( String part ) 
		{
			if(part.equals( "latoSu" ))
				return latoSu;
			else if(part.equals( "latoGiu" ))
				return latoGiu;
			else if(part.equals( "latoSx" ))
				return latoSx;
			else if(part.equals( "latoDx" ))
				return latoDx;
			else if(part.equals( "spigASx" ))
				return spigASx;
			else if(part.equals( "spigADx" ))
				return spigADx;
			else if(part.equals( "spigBSx" ))
				return spigBSx;
			else if(part.equals( "spigBDx" ))
				return spigBDx;
			else if(part.equals( "rect" ))
				return getArea();
			
			return null;
		}

	@Override
	public void draw(Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setInsert(boolean insert, boolean change) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Ostacolo clone(GameContainer gc) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMaxHeight(double val) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getMaxHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getMaxWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setArea(GameContainer gc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setType(String type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isCollided() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setCollided(boolean val) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean contains(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setXY(float x, float y, String function) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(GameContainer gc) throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getCollide() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setCollide(boolean val) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setOrienting(String direction) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getOrienting() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getUnion() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setUnion(int val) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Point getMidArea() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateStats(GameContainer gc) {
		// TODO Auto-generated method stub
		
	}
}

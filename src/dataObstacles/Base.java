package dataObstacles;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class Base extends Ostacolo
{
	private Rectangle ostr;
	
	/*sottodivisione dell'area in lati e spigoli*/
	public Rectangle latoSu, latoGiu, latoDx, latoSx;
	public Rectangle spigASx, spigADx, spigBSx, spigBDx;
	
	public final static String ID = "base";
	
	private float width, height;
	
	public int indexTube;
	
	//determina il tipo di direzione del tubo
	private String dir = null;
	
	public Base( float x, float y, float width, float height ) throws SlickException
		{
			super( ID );
			ostr = new Rectangle( x, y, width, height );
			
			this.width = width;
			this.height = height;
		}
	
	public void setDirection( String direction )
		{ dir = direction; }
	
	public String getDirection()
		{ return dir; }
	
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
	
	public void setIndexTube( int val )
		{ indexTube = val; }
	
	public int getIndexTube()
		{ return indexTube; }

	public void draw(Graphics g) throws SlickException
		{ g.draw( ostr ); }
	
	public Rectangle getArea()
		{ return ostr; }
    
    public void setArea( GameContainer gc )
    	{
    		ostr = new Rectangle( getX(), getY(), width, height );
    		setSpigoli();
		}
	
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
				return ostr;
			
			return null;
		}

	public void setInsert(boolean insert, boolean change)
		{}

	public Ostacolo clone(GameContainer gc)
		{ return null; }

	public void setMaxHeight(double val)
		{}

	public double getMaxHeight()
		{ return 0; }

	public double getMaxWidth()
		{ return 0; }

	public void setType(String type)
		{}

	public boolean contains(int x, int y)
		{ return false; }

	public void setXY(float x, float y, String function)
		{}

	public void update(GameContainer gc) throws SlickException
		{}

	public void update(GameContainer gc, int delta) throws SlickException
		{}

	public void setOrienting( GameContainer gc )
		{}

	public String getOrienting()
		{ return null; }

	public int getUnion() { return 0; }

	public void setUnion(int val)
		{}

	public Point getMidArea()
		{ return null; }

	public void updateStats(GameContainer gc)
		{}
}

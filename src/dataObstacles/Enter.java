package dataObstacles;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import Utils.Global;

public class Enter extends Ostacolo
{
	private Rectangle ostr;
	
	/*sottodivisione dell'area in lati e spigoli*/
	public Rectangle latoSu, latoGiu, latoDx, latoSx;
	public Rectangle spigASx, spigADx, spigBSx, spigBDx;
	
	public final static String ID = "enter";
	
	//determina il tipo di direzione del tubo
	private String dir = null;
	
	public int indexTube;
	
	private float width, height;
	
	public Enter( float x, float y, float width, float height ) throws SlickException
		{
			super( ID );
			ostr = new Rectangle( x, y, width, height );
			
			this.width = width;
			this.height = height;
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

	public void draw( Graphics g ) throws SlickException
		{ g.fill( ostr ); }
	
	public void setIndexTube( int val )
		{ indexTube = val; }
	
	public int getIndexTube()
		{ return indexTube; }
	
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
			if(part.equals( Global.LATOSU ))
				return latoSu;
			else if(part.equals( Global.LATOGIU ))
				return latoGiu;
			else if(part.equals( Global.LATOSX ))
				return latoSx;
			else if(part.equals( Global.LATODX ))
				return latoDx;
			else if(part.equals( Global.SPIGASX ))
				return spigASx;
			else if(part.equals( Global.SPIGADX ))
				return spigADx;
			else if(part.equals( Global.SPIGBSX ))
				return spigBSx;
			else if(part.equals( Global.SPIGBDX ))
				return spigBDx;
			else if(part.equals( Global.RECT ))
				return ostr;
			else if(part.equals( "latoIngresso" ))
				if(dir.equals( "sx" ))
					return latoSx;
				else if(dir.equals( "dx" ))
					return latoDx;
				else if(dir.equals( "su" ))
					return latoSu;
				else
					return latoGiu;
			
			return null;
		}
    
    public void setArea( GameContainer gc )
    	{
    		ostr = new Rectangle( getX(), getY(), width, height );
    		setSpigoli();
		}

    public void setInsert( boolean insert, boolean change )
		{}
	
	public Ostacolo clone( GameContainer gc )
		{ return null; }
	
	public void setMaxHeight( float val )
		{}
	
	public float getMaxHeight()
		{ return 0; }
	
	public float getMaxWidth()
		{ return 0; }
	
	public void setType( String type )
		{}
	
	public boolean contains( int x, int y )
		{ return false; }
	
	public void setXY( float x, float y, String function )
		{}
	
	public void update( GameContainer gc ) throws SlickException
		{}
	
	public void update( GameContainer gc, int delta ) throws SlickException
		{}
	
	public void setOrienting( GameContainer gc )
		{}
	
	public String getOrienting()
		{ return null; }
	
	public int getUnion() { return 0; }
	
	public void setUnion( int val )
		{}
	
	public float[] getMidArea()
		{ return null; }
	
	public void updateStats( GameContainer gc )
		{}

	public float getRotate()
		{ return 0; }

	public void setRotate( float val )
		{}

	public boolean contains( Shape shape )
		{ return false; }

	public boolean getInsert()
		{ return false; }
}

package dataObstacles;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import Utils.Global;
import bubbleMaster.Start;

public class Tubo extends Ostacolo{

	private Image immagine;
	private Image tubosx = new Image( "./data/Image/tuboSx.png" ), tubodx = new Image( "./data/Image/tuboDx.png" );
	private Image tuboup = new Image( "./data/Image/tuboUp.png" ), tubodown = new Image( "./data/Image/tuboDown.png" );    

    private Color cg = new Color( 50, 170, 50, 150 ), cr = new Color( 170, 50, 50, 150 );
    
    //insert = false -> oggetto rosso | insert = true -> oggetto verde
    private boolean insert = false, checkInsert = false;
	
    //lunghezza e altezza del tubo
    private float width, height;
	
	//determina il tipo di direzione del tubo
	private String type = null;
	
	public Rectangle ostr;
	
	/*sottodivisione del rettangolo in lati e spigoli*/
	private Rectangle latoSu, latoGiu, latoDx, latoSx;
	private Rectangle spigASx, spigADx, spigBSx, spigBDx;
	
	private boolean collide;
	
	//indice del tubo a cui e' collegato
	private int unione;
	
	private Base base;
	private Enter enter;
	
	private int indexBase, indexEnter;
	
	private static final String ID = "tubo";
	
	private float rotation;
	
	private int index;
	
	public Tubo( int x, int y, String type, GameContainer gc ) throws SlickException
	{
		super( ID );
		
		this.type = type;
		
		if(type.equals( "sx" ))
			immagine = tubosx;
		else if(type.equals( "dx" ))
			immagine = tubodx;
		else if(type.equals( "down" ))
			immagine = tubodown;
		else
			immagine = tuboup;

		collide = false;
		
		unione = -1;
		
		if(type.equals( "sx" ) || type.equals( "dx" ))
			{
				width = Global.Width/10;
				height = Global.Height/10;
			}
		else
			{
				width = Global.Width*10/133;
				height = Global.Height*10/75;
			}
		
		ostr = new Rectangle( x, y, width, height );
		
		rotation = 0;
	}

	public void draw( Graphics g ) throws SlickException
		{
            immagine.draw( ostr.getX(), ostr.getY(), width, height );
            if(Start.editGame == 1)
                if(checkInsert)
                    if(!insert)
                        immagine.draw( ostr.getX(), ostr.getY(), width, height, cr);
                    else
                        immagine.draw( ostr.getX(), ostr.getY(), width, height, cg);
            //enter.draw( g );
            //base.draw( g );
		}
	
	public void setIndex( int val )
		{ index = val; }
	
	public int getIndex()
		{ return index; }
	
	public int getIndexBase()
		{ return indexBase; }
	
	public void setIndexBase( int val )
		{ indexBase = val; }
	
	public int getIndexEnter()
		{ return indexEnter; }
	
	public void setIndexEnter( int val )
		{ indexEnter = val; }
	
	public void setArea( GameContainer gc )
		{}
	
	public void setType( String type )
		{ this.type = type; }

	public float getX()
		{ return (int)ostr.getX(); }

	public float getY()
		{ return ostr.getY(); }
	
	public float getWidth()
		{ return width; }
	
	public float getHeight()
		{ return ostr.getHeight(); }
	
	public boolean contains( int x, int y )
		{ return ostr.contains( x, y ); }
	
	public boolean getInsert()
		{ return insert; }

    public void setInsert(boolean insert, boolean change)
        {
            checkInsert = change;
            this.insert = insert;
        }
	
	public void setXY( float x, float y, String function ) throws SlickException
		{ 
			if(function.equals( "move" ))
				ostr.setLocation( ostr.getX() + x, ostr.getY() + y );
			else
				ostr.setLocation( x, y );
			
			setSpigoli();
		}
	
	public Ostacolo getBase()
		{ return base; }
	
	public Ostacolo getEnter()
		{ return enter; }
	
	public String getDirection()
		{ return type; }
	
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
				if(type.equals( "sx" ))
					return latoSx;
				else if(type.equals( "dx" ))
					return latoDx;
				else if(type.equals( "up" ))
					return latoSu;
				else if(type.equals( "down" ))
					return latoGiu;
			
			return null;
		}

	public float getMaxX()
		{ return ostr.getMaxX(); }

	public void update(GameContainer gc) throws SlickException 
		{}
	
	public void setMaxHeight( float val )
		{}
	
	public float getMaxHeight()
		{ return 0; }
	
	public Shape getArea()
		{ return ostr; }

	public Ostacolo clone( GameContainer gc )
		{		
			try { return new Tubo( (int) getX(), (int) getY(), type, gc ); } 
			catch (SlickException e)
				{
					e.printStackTrace();
					return null;
				}
		}
	
	public boolean getCollide()
		{ return collide; }

	public void setCollide( boolean val )
		{ collide = val; }

	public void update(GameContainer gc, int delta) throws SlickException 
		{}

	public float getMaxWidth()
		{ return 0; }

	private void modificaArea( int type, GameContainer gc ) throws SlickException
	    {
			float tmp;
		
	        //se la modifica e' da orizzontale a verticale
	        if(type == 1)
    	        {
	        		tmp = width;
	                width = height;
	                height = tmp;
	                
                    setSpigoli();
    	        }
	        //se la modifica e' da verticale a orizzontale
	        else
    	        {
		        	tmp = width;
	                width = height;
	                height = tmp;
	                
                    setSpigoli();
    	        }
	    }
	
	public void setOrienting(GameContainer gc ) throws SlickException
	    {
	        if(type.equals( "sx" ))
	            {
	                type = "up";
	                immagine = tuboup;
	                modificaArea( 1, gc );
	            }
	        else if(type.equals( "up" ))
	            {
	                type = "dx";
	                immagine = tubodx;
	                modificaArea( 2, gc );
	            }
	        else if(type.equals( "dx" ))
	            {
	                type = "down";
	                immagine = tubodown;
	                modificaArea( 1, gc );
	            }
	        else
	            {
	                type = "sx";
	                immagine = tubosx;
	                modificaArea( 2, gc );
	            }
	    }
	
	public void orienting( GameContainer gc ) throws SlickException
        { rotation = (rotation + 1)%360; }

	public String getOrienting()
        { return type; }

	public void setSpigoli() throws SlickException
        {
			ostr = new Rectangle( getX(), getY(), width, height );
			if(type.equals( "dx" ))
				{
					base = new Base( getX(), getY() + Global.Height/120, Global.Width*10/119, height - Global.Height/60 );
					enter = new Enter( base.getMaxX(), getY(), width - base.getWidth(), height );
				}
			else if(type.equals( "sx" ))
				{
					enter = new Enter( getX(), getY(), width - Global.Width*10/119, height );
					base = new Base( enter.getMaxX(), getY() + Global.Height/120, Global.Width*10/119, height - Global.Height/60 );
				}
			else if(type.equals( "up" ))
				{
					enter = new Enter( getX(), getY(), width, height - Global.Height*10/89 );
					base = new Base( getX() + Global.Width/160, enter.getMaxY(), width - Global.Width/80, height - enter.getHeight() );
				}
			else
				{
					base = new Base( getX() + Global.Width/160, getY(), width - Global.Width/80, Global.Height*10/89 );
					enter = new Enter( getX(), base.getMaxY(), width, height - base.getHeight() );
				}
			base.setSpigoli();
			enter.setSpigoli();
		
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
	
	public void setUnion( int val )
		{ unione = val; }
	
	public int getUnion()
		{ return unione; }
	
	//calcola il punto a meta' dell'oggetto per generare la linea che unisce i tubi connessi
	public float[] getMidArea()
		{ return ostr.getCenter(); }
	
	public void setWidth( float val )
		{
			width = val;
			ostr.setWidth( width );
		}
	
	public void setHeight( float val )
		{
			height = val;
			ostr.setHeight( height );
		}

	public float getRotate()
		{ return rotation; }

	public void setRotate( float val )
		{ rotation = val; }

	public boolean contains( Shape shape )
		{
			if(shape.getY() >= ostr.getY() && shape.getMaxY() <= ostr.getMaxY())
				if(shape.getX() >= ostr.getX() && shape.getMaxX() <= ostr.getMaxX())
					return true;
		
			return false;
		}

	public float getMaxY()
		{ return ostr.getMaxY(); }

	public void setX(float x)
		{}

	public void setY(float y)
		{}
}
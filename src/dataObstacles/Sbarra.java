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

public class Sbarra extends Ostacolo
{

	private Image immagine;
	private Image hor = new Image( "./data/Image/sbarra.png" ), ver = new Image( "./data/Image/sbarraVer.png" );
	
	// lunghezza e altezza della sbarra
	private float width, height;
	
	public Rectangle ostr;
	
	private boolean collide;
	
	/*sottodivisione del rettangolo in lati e spigoli*/
	public Rectangle latoSu, latoGiu, latoDx, latoSx;
	public Rectangle spigASx, spigADx, spigBSx, spigBDx;
	
	//insert = false -> oggetto rosso | insert = true -> oggetto verde
	private boolean insert = false, checkInsert = false;

	private Color cg = new Color( 50, 170, 50, 150 ), cr = new Color( 170, 50, 50, 150 );
	
	//determina la verticalita' o orizzontalita' della sbarra
	private String type;
	
	public static final String ID = "sbarra";
	
	public Sbarra( float x, float y, String type, GameContainer gc ) throws SlickException
		{
			super( ID );
			
			if(type.equals( "hor" ))
				{
					immagine = hor;					
					width = Global.Width*10/61;
					height = Global.Height/30;
				}
			else
				{
					immagine = ver;					
					width = Global.Width/40;
					height = Global.Height*1000/4575;
				}
			
			this.type = type;
		
			ostr = new Rectangle( x, y, width, height );
			
			collide = false;
		}
	
	public Sbarra clone( GameContainer gc )
		{ try {
			return new Sbarra( (int) ostr.getX(), (int) ostr.getY(), type, gc );
		} catch (SlickException e) {
			e.printStackTrace();
			return null;
		} }
	
	public void draw( Graphics g ) throws SlickException
		{
			immagine.draw( ostr.getX(), ostr.getY(), width, height );
			if(Start.editGame == 1)
				if(checkInsert)
					if(!insert)
						immagine.draw( ostr.getX(), ostr.getY(), width, height, cr);
					else
						immagine.draw( ostr.getX(), ostr.getY(), width, height, cg);
		}
    
    public void setArea( GameContainer gc )
    	{
    		ostr = new Rectangle( getX(), getY(), width, height );
    		setSpigoli();
		}
	
	public float getX()
		{ return (int)ostr.getX(); }

	public float getY()
		{ return (int)ostr.getY(); }
	
	public float getWidth()
		{ return ostr.getWidth(); }
	
	public float getHeight()
		{ return ostr.getHeight(); }
	
	public boolean getCollide()
		{ return collide; }

	public void setCollide( boolean val )
		{ collide = val; }
	
	public boolean contains( int x, int y )
		{ return ostr.contains( x, y ); }

	public void setInsert(boolean insert, boolean change)
		{
			checkInsert = !change;
			this.insert = insert;
		}
	
	public void setXY( float x, float y, String function )
		{			
			if(function.compareTo( "move" ) == 0)
				ostr.setLocation( ostr.getX() + x, ostr.getY() + y );
			
			else if(function.compareTo( "restore" ) == 0)
				ostr.setLocation( x, y );
		}

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
			
			return null;
		}

	public float getMaxX()
		{ return ostr.getX() + width; }

	public void setType(String type) 
		{}

	public void update( GameContainer gc ) 
		{}

	public Shape getArea()
		{ return ostr; }
	
	public void setMaxHeight( double val )
		{}
	
	public double getMaxHeight()
		{ return 0; }

	public void update(GameContainer gc, int delta) throws SlickException 
		{}

	public double getMaxWidth()
		{ return 0; }

    public void setOrienting( GameContainer gc )
        {
    		float tmp;
    	
            if(type.equals( "ver" ))
                {
                    type = "hor";                    

	        		tmp = width;
	                width = height;
	                height = tmp;
                    
                    //modifica l'area dell'oggetto
                    ostr.setX( getX() - width/2 + height/2 );
                    ostr.setY( getY() + width/2 - height/2 );
                    ostr.setWidth( width );
                    ostr.setHeight( height );
                    
                    immagine = hor;
                }
            else
                {           
                    type = "ver";
                    
                    //modifica l'area dell'oggetto
                    ostr.setX( getX() + width/2 - height/2 );
                    ostr.setY( getY() - width/2 + height/2 );

	        		tmp = width;
	                width = height;
	                height = tmp;
                    ostr.setWidth( width );
                    ostr.setHeight( height );
                    
                    immagine = ver;
                }
        }

    public String getOrienting()
        { return type; }

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

	public int getUnion()
		{ return -1; }

	public void setUnion(int val)
		{}

	public float[] getMidArea()
		{ return null; }
	
	public void setWidth( float val )
		{ width = val; }
	
	public void setHeight( float val )
		{ height = val; }
}
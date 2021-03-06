package dataObstacles;
 
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;

import Utils.Global;
import bubbleMaster.Start;
 
public class Bubble extends Ostacolo
{
    private float ray;
     
    private Circle ostr;
 
    private float speedX, speedY;
     
    private Image immagine = new Image( "./data/Image/Palla.png" );
     
    private float maxH;
    private float maxW;
	
	private boolean insert = false, checkInsert = false;
	
	private Color cg = new Color( 50, 170, 50, 100 ), cr = new Color( 170, 50, 50, 100 );
	
	//determina il tubo in cui e' entrata la sfera (ma non il tubo in uscita)
	private int indexTube;
	//determina se la sfera e' nel primo o secondo tubo
	private boolean primoTubo, secondoTubo;
	
	private float backupSpeedX, backupSpeedY;
	
	private static final String UP = "up", DOWN = "down", SX = "sx", DX = "dx", RESTORE = "restore", RAY = "setRay", MOVE = "move";
	
	private int index;
	
	private ArrayList<Ostacolo> ostacoli;
 
    public Bubble( Ostacolo ost, GameContainer gc ) throws SlickException
        { this( ost.getX(), ost.getY(), ost.getWidth()/2, ost.getMaxWidth(), gc ); }
     
    public Bubble( float x, float y, float ray, float maxW, GameContainer gc ) throws SlickException
        {       
            super( "bolla" );
             
            this.ray = ray;
            ostr = new Circle( x + ray, y + ray, ray );
            
            this.maxW = maxW;
            
            primoTubo = false;
            secondoTubo = false;
            indexTube = -1;
            
            float minSpeed = 0.4f;
            
            speedX = (float) Math.random() * 2;
            if(speedX < minSpeed)
            	speedX = minSpeed;
            speedY = (float) Math.random() * 2;
            if(speedY < minSpeed)
            	speedY = minSpeed;
            
            float sign = (float) Math.random();
            if(sign < 0.5f)
            	speedX = -speedX;
            sign = (float) Math.random();
            if(sign < 0.5f)
            	speedY = -speedY;
        }
     
    public void draw( Graphics g ) throws SlickException
        {
    		immagine.draw( ostr.getX(), ostr.getY(), ray*2, ray*2 );
    		
    		if(Start.editGame == 1)
    			{	
    				if(checkInsert)
    					if(!insert)
    						immagine.draw( ostr.getX(), ostr.getY(), ray*2, ray*2, cr );
    					else
    						immagine.draw( ostr.getX(), ostr.getY(), ray*2, ray*2, cg );
    			}
		}
    
    public float getMaxWidth()
    	{ return maxW; }
     
    public Circle getArea()
        { return ostr; }
 
    public void setXY( float x, float y, String function )
        {
            if(function.equals( MOVE ))
                ostr.setLocation( ostr.getX() + x, ostr.getY() + y );
             
            else if(function.equals( RESTORE ))
                ostr.setLocation( x, y );
             
            else if(function.equals( RAY ))
            	ray = x;
        }
    
    public void setPrimoTubo( boolean val )
    	{ primoTubo = val; }
    
    public boolean getPrimoTubo()
    	{ return primoTubo; }
    
    public void setIndexTube( int val )
    	{ indexTube = val; }
    
    public int getIndexTube()
    	{ return indexTube; }
 
    public boolean contains( int x, int y )
        { return ostr.contains( x, y ); }
 
    public float getX()
        { return (int) ostr.getX(); }
 
    public float getY()
        { return (int) ostr.getY(); }
     
    private void setCenter( Shape ostr, float x, float y )
        {
            ostr.setCenterX( ostr.getCenterX() + x );
            ostr.setCenterY( ostr.getCenterY() + y );
        }
    
    public void setOstacoli( ArrayList<Ostacolo> ostacoli )
    	{ this.ostacoli = ostacoli; }
     
    public void setMaxHeight( float val )
        { maxH = val; }
     
    public float getMaxHeight()
        { return maxH; }
 
    public Shape component( String part )
        { return ostr; }
 
    public float getWidth()
        { return 2*ray; }
 
    public float getHeight()
        { return 2*ray; }
 
    public float getSpeedX()
        { return speedX; }
 
    public float getSpeedY()
        { return speedY; }
 
    public void setSpeed( float x, float y )
        {
    		speedX = x;	
    		speedY = y;
        }
    
    public boolean getCollide()
    	{ return true; }
    
    public void setCollide( boolean val )
    	{}
    
    public void setArea()
    	{ ostr = new Circle( ostr.getCenterX(), ostr.getCenterY(), ray ); }
    
    public void setMaxWidth( float val )
    	{ maxW = val; }
    
    /** inverte le due componenti della velocita' */
    private void reverseSpeed( float speed1, float speed2 )
    	{ 
    		speedX = speed1;
    		speedY = speed2;
    	}
    
    /**determina la velocita' risultante nella collisione fra sfera e altri ostacoli*/
    public void gestioneCollisioni( Ostacolo ost )
    	{
    		// TODO MIGLIORARE (SE POSSIBILE) IL RIMBALZO CON GLI SPIGOLI
    		// E' BUONO GIA COSI, MA IN ALCUNI CASI NON MI CONVINCE
    	
			// alto a sinistra || in alto
			if((speedX < 0 && speedY < 0) || (speedX == 0 && speedY < 0))
				{
					if(ostr.intersects( ost.component( Global.SPIGBSX ) ))
						{
							speedY = -speedY;
							if(speedX == 0)
								if(ostr.getCenterX() < ost.component( Global.SPIGBSX ).getX())
									speedX = -1;
						}
					else if(ostr.intersects( ost.component( Global.SPIGADX ) ))
						speedX = -speedX;
					else if(ostr.intersects( ost.component( Global.SPIGBDX ) ))
						{
							if(ostr.getCenterX() > ost.component( Global.SPIGBDX ).getX() && ostr.getCenterY() > ost.component( Global.SPIGBDX ).getY())
								{
									reverseSpeed( -speedY, speedX );
									if(speedX == 0)
										speedX = 1;
								}
							else if(speedX == 0)
								speedY = -speedY;
							else if(ostr.intersects( ost.component( Global.LATODX ) ) && ostr.getCenterY() < ost.component( Global.SPIGBDX ).getY())
	    						speedX = -speedX;
	    					else if(ostr.intersects( ost.component( Global.LATOGIU ) ))
	    						speedY = -speedY;
						}
					else if(ostr.intersects( ost.component( Global.LATODX ) ))
						speedX = -speedX;
					else if(ostr.intersects( ost.component( Global.LATOGIU ) ))
						speedY = -speedY;
				}
			// alto a destra || a destra
			else if((speedX > 0 && speedY < 0) || (speedX > 0 && speedY == 0))
				{
					if(ostr.intersects( ost.component( Global.SPIGBDX ) ))
						speedY = -speedY;
					else if(ostr.intersects( ost.component( Global.SPIGASX ) ))
						{
							speedX = -speedX;
							if(speedY == 0)
								if(ostr.getCenterY() < ost.component( Global.SPIGASX ).getY())
									speedY = -1;
						}
					else if(ostr.intersects( ost.component( Global.SPIGBSX ) ))
						{
    						if(ostr.getCenterX() < ost.component( Global.SPIGBSX ).getMaxX() && ostr.getCenterY() > ost.component( Global.SPIGBSX ).getY())
								{
    								reverseSpeed( speedY, speedX );
    								if(speedY == 0)
    									speedY = 1;
								}
    						else if(ostr.intersects( ost.component( Global.LATOSX ) ) && ostr.getCenterY() < ost.component( Global.SPIGBSX ).getY())
	    						speedX = -speedX;
	    					else if(ostr.intersects( ost.component( Global.LATOGIU ) ))
	    						speedY = -speedY;
						}
					else if(ostr.intersects( ost.component( Global.LATOSX ) ))
						speedX = -speedX;
					else if(ostr.intersects( ost.component( Global.LATOGIU ) ))
						speedY = -speedY;
				}
			// basso a destra || in basso
			else if((speedX > 0 && speedY > 0) || (speedX == 0 && speedY > 0))
				{
    				if(ostr.intersects( ost.component( Global.SPIGBSX ) ))
						speedX = -speedX;
					else if(ostr.intersects( ost.component( Global.SPIGADX ) ))
						{
							speedY = -speedY;
							if(speedX == 0)
								if(ostr.getCenterX() > ost.component( Global.SPIGADX ).getMaxX())
									speedX = 1;
						}
					else if(ostr.intersects( ost.component( Global.SPIGASX ) ))
						{
							if(ostr.getCenterY() < ost.component( Global.SPIGASX ).getMaxY() && ostr.getCenterX() < ost.component( Global.SPIGASX ).getMaxX())
								{
									reverseSpeed( -speedY, speedX );
									if(speedX == 0)
										speedX = -1;
								}
	    					else if(ostr.intersects( ost.component( Global.LATOSX ) ) && ostr.getCenterY() > ost.component( Global.SPIGASX ).getMaxX())
	    						speedX = -speedX;
	    					else if(ostr.intersects( ost.component( Global.LATOSU ) ))
	    						speedY = -speedY;
						}
					else if(ostr.intersects( ost.component( Global.LATOSX ) ))
						speedX = -speedX;
					else if(ostr.intersects( ost.component( Global.LATOSU ) ))
						speedY = -speedY;
				}
			// basso a sinistra || a sinistra
			else if((speedX < 0 && speedY > 0) || (speedX < 0 && speedY == 0))
				{
    				if(ostr.intersects( ost.component( Global.SPIGBDX ) ))
    					{
							speedX = -speedX;
							if(speedY == 0)
								if(ostr.getCenterY() > ost.component( Global.SPIGBDX ).getY())
									speedY = 1;
    					}
					else if(ostr.intersects( ost.component( Global.SPIGASX ) ))
						speedY = -speedY;
					else if(ostr.intersects( ost.component( Global.SPIGADX ) ))
						{
							if(ostr.getCenterX() > ost.component( Global.SPIGADX ).getX() && ostr.getCenterY() < ost.component( Global.SPIGADX ).getMaxY())
								{
									reverseSpeed( speedY, speedX );
									if(speedY == 0)
										speedY = -1;
								}
	    					else if(ostr.intersects( ost.component( Global.LATODX  ) ) && ostr.getCenterY() > ost.component( Global.SPIGADX ).getMaxY())
	    						speedX = -speedX;
	    					else if(ostr.intersects( ost.component( Global.LATOSU ) ))
	    						speedY = -speedY;
						}
					else if(ostr.intersects( ost.component( Global.LATODX  ) ))
						speedX = -speedX;
					else if(ostr.intersects( ost.component( Global.LATOSU ) ))
						speedY = -speedY;
				}
    		
    		if(secondoTubo)
    			{
    				secondoTubo = false;
    				indexTube = -1;
    			}
        }
    
    /** setta posizione e velocita' nei tubi */
    public void setValuesInTube( Ostacolo ost )
    	{
    		//orientamento del tubo
			String pos = ost.getOrienting();
			
    		//la sfera e' nel PRIMO tubo
    		if(primoTubo)
    			{
    				if(speedX != 0)
    					backupSpeedX = Math.abs( speedX );
    				if(speedY != 0)
    					backupSpeedY = Math.abs( speedY );
    				
	    			if(pos.equals( SX ) || pos.equals( DX ))
	    				{
							setXY( ostr.getX(), ost.getMidArea()[1] - ray, RESTORE );
							speedY = 0;
	    				}
	    			else if(pos.equals( DOWN ) || pos.equals( UP ))
	    				{
							setXY( ost.getMidArea()[0] - ray, ostr.getY(), RESTORE );
							speedX = 0;
	    				}
    			}
    		//la sfera e' nel SECONDO tubo
    		else if(secondoTubo)
    			{
	    			setXY( ost.getMidArea()[0] - getWidth()/2, ost.getMidArea()[1] - getHeight()/2, RESTORE );
					
					if(pos.equals( SX ))
						{
							speedX = -backupSpeedX;
							speedY = 0;
						}
					else if(pos.equals( DX ))
						{
							speedX = backupSpeedX;
							speedY = 0;
						}
					else if(pos.equals( UP ))
						{
							speedX = 0;
							speedY = -backupSpeedY;
						}
					else
						{
							speedX = 0;
							speedY = backupSpeedY;
						}
    			}
    	}
    
    /**determina se la sfera e' ancora nel primo o nel secondo tubo*/
    public void gestioneSferaInTubo( Ostacolo tubo )
    	{
    		// se la sfera e' nel PRIMO tubo
    		if(primoTubo)
	    		{
    				if(tubo.getOrienting().equals( SX ) && ostr.getCenterX() >= tubo.getMidArea()[0])
						primoTubo = false;
    				else if(tubo.getOrienting().equals( DX ) && ostr.getCenterX() <= tubo.getMidArea()[0])
						primoTubo = false;
    				else if(tubo.getOrienting().equals( UP ) && ostr.getCenterY() >= tubo.getMidArea()[1])
						primoTubo = false;
    				else if(tubo.getOrienting().equals( DOWN ) && ostr.getCenterY() <= tubo.getMidArea()[1])
						primoTubo = false;
        			
        			if(!primoTubo)
        				{
        					secondoTubo = true;
        					indexTube = ostacoli.get( indexTube ).getUnion();
        					setValuesInTube( ostacoli.get( indexTube ) );
        				}
	    		}
    		// la sfera e' nel SECONDO tubo
    		else if(secondoTubo && !tubo.getArea().intersects( ostr ))
				{
					secondoTubo = false;
					indexTube = -1;
				}
    	}
    
    /** controlla se la sfera ha colliso con l'ingresso di un tubo */
    public boolean checkEnter( Tubo ost )
    	{
    		String dir = ost.getDirection();
    		Enter enter = (Enter) ost.getEnter();
    		Shape ingr = enter.getArea();

    		if(dir.equals( SX ) || dir.equals( DX ))
	    		{
					if(ostr.intersects( ingr ))
						if(!ostr.intersects( enter.component( Global.LATOSU ) ) && !ostr.intersects( enter.component( Global.LATOGIU ) ))
	    					if((ostr.getCenterY() > ingr.getY() && ostr.getCenterY() < ingr.getMaxY()))
		    					{
	    							primoTubo = true;
	    							secondoTubo = false;
	    							setValuesInTube( ost );
		        					return true;
	    						}
	    		}
    		else if(dir.equals( UP ) || dir.equals( DOWN ))
    			{
					if(ostr.intersects( ingr ))
						if(!ostr.intersects( enter.component( Global.LATOSX ) ) && !ostr.intersects( enter.component( Global.LATODX ) ))
	    					if((ostr.getCenterX() > ingr.getX() && ostr.getCenterX() < ingr.getMaxX()))
		    					{
	    							primoTubo = true;
	    							secondoTubo = false;
	    							setValuesInTube( ost );
		        					return true;
	    						}
    			}
    		
    		return false;
    	}
    
    /**gestisce collisioni fra tutti gli elementi*/
    public void checkAll( Ostacolo ost )
    	{
    		// controlla se la sfera ha colliso con l'ingresso di un tubo
    		if(ost.getID().equals( Global.TUBO ) && !primoTubo)
				if(ost.getIndex() != indexTube && checkEnter( ((Tubo) ost) ))
					indexTube = ost.getIndex();
        	
			if(secondoTubo)
				{
					if(!ost.getID().equals( Global.TUBO ))
						{
							if(ost.getID().equals( Global.BASE ))
								{
									if(((Base) ost).getIndexTube() != indexTube && ostr.intersects( ost.getArea() ))
										gestioneCollisioni( ost );
								}
								
							else if(ost.getID().equals( Global.ENTER ))
								{
									if(((Enter) ost).getIndexTube() != indexTube && ostr.intersects( ost.getArea() ))
										gestioneCollisioni( ost );
								}
							else if(ostr.intersects( ost.getArea() ))
								gestioneCollisioni( ost );
						}
				}
			else if(!primoTubo && !ost.getID().equals( Global.TUBO ))
				if(ostr.intersects( ost.getArea() ))
					gestioneCollisioni( ost );
			
			if(primoTubo || secondoTubo)
				gestioneSferaInTubo( ostacoli.get( indexTube ) );
    	}
 
    public void update( GameContainer gc, int delta ) throws SlickException
        {
            for(Ostacolo ost: ostacoli)
            	if(!ost.getID().equals( Global.BOLLA ))
            		checkAll( ost );
             
            /*controllo collisione con i bordi della schermata*/
            checkBorders();

            setCenter( ostr, speedX, speedY );
            
            if((speedX == 0 || speedY == 0) && !primoTubo)
            	{
                    for(Ostacolo ost: ostacoli)
                    	if(!ost.getID().equals( Global.BOLLA ))
                    		checkAll( ost );
                    
                	/*controllo collisione con i bordi della schermata*/
                    checkBorders();

                    setCenter( ostr, speedX, speedY );
            	}
        }
    
    public void checkBorders()
    	{
	        if(ostr.getX() <= 0 || getMaxX() >= maxW)
	    		{
	    			secondoTubo = false;
	        		speedX = -speedX;
	    		}
	        if(ostr.getY() <= 0 || getMaxY() >= maxH)
	        	{
    				secondoTubo = false;
	        		speedY = -speedY;
	        	}
    	}
 
    public void setType(String type)
        {}
 
    public float getMaxX()
        { return getX() + ray*2; }
 
    public Ostacolo clone( GameContainer gc ) {
        try
	        {        	
	            Bubble b = new Bubble( ostr.getX(), ostr.getY(), ray, maxW, gc );
	            b.setSpeed( speedX, speedY );
	            return b;
	        }
        catch (SlickException e)
	        {
	            e.printStackTrace();
	            return null;
	        }
    }

    public void setInsert(boolean insert, boolean change)
		{
			checkInsert = change;
			this.insert = insert;
		}

	public void update(GameContainer gc) throws SlickException 
		{}

    public void setOrienting( GameContainer gc )
    	{}

    public String getOrienting()
    	{ return null; }

    public void setSpigoli()
    	{}

	public int getUnion()
		{ return -1; }

	public void setUnion(int val)
		{}

	public float[] getMidArea()
		{ return null; }
	
	public void setWidth( float val )
		{ ray = val; }
	
	public void setHeight( float val )
		{ ray = val; }
    
    public void setArea( GameContainer gc )
    	{ ostr = new Circle( ostr.getCenterX(), ostr.getCenterY(), ray ); }

	public float getRotate()
		{ return 0; }

	public void setRotate( float val )
		{}

	public boolean contains( Shape shape )
		{
			if(shape.getY() > ostr.getY() && shape.getMaxY() < ostr.getMaxY())
				if(shape.getX() > ostr.getX() && shape.getMaxX() < ostr.getMaxX())
					return true;
		
			return false;
		}

	public float getMaxY()
		{ return ostr.getY() + 2*ray; }

	public boolean getInsert()
		{ return insert; }

	public void setX(float x)
		{}

	public void setY(float y)
		{}
	
	public void setIndex( int val )
		{ index = val; }
	
	public int getIndex()
		{ return index; }
}
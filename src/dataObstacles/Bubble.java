package dataObstacles;
 
import interfaces.InGame;
 










import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;

import bubbleMaster.Start;
 
public class Bubble extends Ostacolo
{
    private float ray;
     
    private Circle ostr;
 
    private int speedX = -1, speedY = -1;
     
    private Image immagine = new Image( "./data/Image/Palla.png" );
     
    private double maxH;
    private double maxW;
     
    private boolean collided;
	
	private boolean insert = false, checkInsert = false;
	
	private Color cg = new Color( 50, 170, 50, 100 ), cr = new Color( 170, 50, 50, 100 );
	
	//determina se la sfera e' all'interno di un tubo
	private boolean inTube;
	//determina il tubo in cui e' entrata la sfera (ma non il tubo in uscita)
	private int indexTube;
	//determina se la sfera e' nel primo o secondo tubo
	private boolean primoTubo, secondoTubo;
	//determina quanto tempo deve rimanere la sfera all'interno del primo tubo
	private int countMove;
	//determina se le velocita' della sfera sono state settate
	private boolean setSpeed;
     
    public Bubble( Ostacolo ost ) throws SlickException
        { this( (int) ost.getX(), (int) ost.getY(), (int) ost.getWidth(), ost.getMaxWidth() ); }
     
    public Bubble( int x, int y, int ray, double maxW ) throws SlickException
        {       
            super( "bolla" );
 
            collided = false;
             
            this.ray = ray;
            ostr = new Circle( x + ray, y + ray, ray );
            
            this.maxW = maxW;
            
            inTube = false;
            primoTubo = false;
            secondoTubo = false;
        }
     
    public void draw( Graphics g ) throws SlickException
        {
    		immagine.draw( ostr.getX(), ostr.getY(), ray*2, ray*2 );
    		
    		if(Start.editGame == 1)
    			{	
    				if(checkInsert)
    					if(!insert)
    						immagine.draw( ostr.getX(), ostr.getY(), ray*2, ray*2, cr);
    					else
    						immagine.draw( ostr.getX(), ostr.getY(), ray*2, ray*2, cg);
    			}
		}
    
    public double getMaxWidth()
    	{ return maxW; }
     
    public Circle getArea()
        { return ostr; }
 
    public void setXY( float x, float y, String function )
        {
            if(function.compareTo( "move" ) == 0)
                ostr.setLocation( ostr.getX() + x, ostr.getY() + y );
             
            else if(function.compareTo( "restore" ) == 0)
                ostr.setLocation( x, y );
             
            else if(function.compareTo( "setRay" ) == 0)
                ray = x;
        }
 
    public boolean contains( int x, int y )
        { return ostr.contains( x, y ); }
 
    public float getX()
        { return (int) ostr.getX(); }
 
    public float getY()
        { return (int) ostr.getY(); }
     
    private void setCenter( Shape ostr, int x, int y )
        {
            ostr.setCenterX( ostr.getCenterX() + x );
            ostr.setCenterY( ostr.getCenterY() + y );
        }
     
    public void setMaxHeight( double val )
        { maxH = val; }
     
    public double getMaxHeight()
        { return maxH; }
 
    public Shape component( String part )
        { return ostr; }
 
    public float getWidth()
        { return ray; }
 
    public float getHeight()
        { return ray; }
     
    public void setCollided( boolean val )
        { collided = val; }
     
    public boolean isCollided()
        { return collided; }
 
    public int getSpeedX()
        { return speedX; }
 
    public int getSpeedY()
        { return speedY; }
 
    public void setSpeed( Integer x, Integer y )
        {
    		if(x != null)
    			speedX = x;
    		if(y != null)
    			speedY = y;
        }
    
    public boolean getCollide()
    	{ return true; }
    
    public void setCollide( boolean val )
    	{}
    
    //determina la collisione con gli spigoli con speedX o speedY nulla
    private boolean collisionEdge( Ostacolo ost )
    	{
    		if(ostr.intersects( ost.component( "spigASx" ) ))
	    		{
	    			if(speedX == 0 && speedY > 0)
	    				{
	    					if(ostr.getCenterX() < ost.getX())
	    						{
	    							speedX = -1;
	    							speedY = -speedY;
	    						}
	    				}
	    			else if(speedX > 0 && speedY == 0)
	    				{
	    					if(ostr.getCenterY() < ost.getY())
	    						{
	    							speedX = -speedX;
	    							speedY = -1;
	    						}
	    				}
	    			else
	    				return false;
	    		}
    		else if(ostr.intersects( ost.component( "spigADx" ) ))
    			{
	    			if(speedX == 0 && speedY > 0)
						{
							if(ostr.getCenterX() > ost.getMaxX())
								{
									speedX = 1;
									speedY = -speedY;
								}
						}
					else if(speedX < 0 && speedY == 0)
						{
							if(ostr.getCenterY() < ost.getY())
								{
									speedX = -speedX;
									speedY = -1;
								}
						}
	    			else
	    				return false;
    			}
    		else if(ostr.intersects( ost.component( "spigBSx" ) ))
	    		{
	    			if(speedX == 0 && speedY < 0)
	    				{
	    					if(ostr.getCenterX() < ost.getX())
	    						{
	    							speedX = -1;
	    							speedY = -speedY;
	    						}
	    				}
	    			else if(speedX > 0 && speedY == 0)
	    				{
	    					if(ostr.getCenterY() > ost.getY() + ost.getHeight())
	    						{
	    							speedX = -speedX;
	    							speedY = -1;
	    						}
	    				}
	    			else
	    				return false;
	    		}
    		else if(ostr.intersects( ost.component( "spigBDx" ) ))
    			{
	    			if(speedX == 0 && speedY > 0)
						{
							if(ostr.getCenterX() > ost.getMaxX())
								{
									speedX = 1;
									speedY = -speedY;
								}
						}
					else if(speedX < 0 && speedY == 0)
						{
							if(ostr.getCenterY() > ost.getY() + ost.getHeight())
								{
									speedX = -speedX;
									speedY = -1;
								}
						}
	    			else
	    				return false;
    			}
		
	    	return true;
    	}

    //determina la collisione con gli spigoli con speedX o speedY nulla
    private boolean collisionEdge( Ostacolo ost, boolean dritto )
    	{
    		if(!dritto)
    			{
		    		if(speedX > 0 && speedY > 0)
		    			{
		    				if(ost.component( "spigASx" ).getX() != Math.abs(ost.getY() - ostr.getCenterY() + ostr.getCenterX()))
		    					return false;
		    			}
		    			
		    		else if(speedX < 0 && speedY > 0)
		    			{
		    				if(ost.component( "spigADx" ).getX() != Math.abs(ost.getY() - ostr.getCenterY() + ostr.getCenterX()))
		    					return false;
		    			}
		    			
		    		else if(speedX > 0 && speedY < 0)
		    			{
		    				if(ost.component( "spigBSx" ).getX() != Math.abs(ost.getY() - ostr.getCenterY() + ostr.getCenterX()))
		    					return false;
		    			}
		    		
		    		else if(speedX < 0 && speedY < 0)
		    			if(ost.component( "spigBDx" ).getX() != Math.abs(ost.getY() - ostr.getCenterY() + ostr.getCenterX()))
		    				{
								/*System.out.println( "spig.x = " + ost.component( "spigBDx" ).getX() );
								System.out.println( "retta.x = " + (Math.abs(ost.getY() - ostr.getCenterY() + ostr.getCenterX())));*/
		    				
		    					System.out.println( "(centro.x, centro.y) = " + ostr.getCenterX() + " " + ostr.getCenterY() );
		    					System.out.println( "(spig.x, spig.y) = " + ost.component( "spigBDx" ).getX() + " " + ost.component( "spigBDx" ).getY() );
		    					
		    					return false;
		    				}
		    		
					speedX = -speedX;
					speedY = -speedY;
					
					System.out.println( "touch!" );
					
					return true;
    			}
    		else
				return collisionEdge( ost );
    	}
    
    public void gestioneSferaInTubo( int delta )
    	{
    		// se la sfera e' ancora all'interno del primo tubo
    		if(countMove < 26)
	    		{
        			countMove = countMove + delta;
        			if(!setSpeed)
	        			{
		    				Ostacolo tubo = InGame.ostacoli.get( indexTube );
		    				if(tubo.getOrienting().equals( "sx" ) || tubo.getOrienting().equals( "dx" ))
		    					speedY = 0;
		    				else if(tubo.getOrienting().equals( "down" ) || tubo.getOrienting().equals( "up" ))
		    					speedX = 0;
		    				
		    				setSpeed = false;
	        			}
	    		}
    		// la sfera e' nel secondo tubo
    		else if(countMove > 0)
    			{
    				primoTubo = false;
    				secondoTubo = true;
    				
    				Ostacolo tubo = InGame.ostacoli.get( indexTube );
    				if(tubo.getOrienting().equals( "sx" ))
    					{
    						speedY = 0;
    						speedX = -1;
    					}
    				else if(tubo.getOrienting().equals( "dx" ))
    					{
    						speedY = 0;
    						speedX = 1;
    					}
    				else if(tubo.getOrienting().equals( "down" ))
    					{
    						speedX = 0;
    						speedY = 1;
    					}
    				else
    					{
    						speedX = 0;
    						speedY = -1;
    					}
        			countMove = countMove - delta;
    			}
    		//la sfera e' fuori dal secondo tubo
    		else
    			secondoTubo = false;
    	}
    
    public void gestioneCollisioni( Ostacolo ost, boolean dritto )
    	{
	    	if(ostr.intersects( ost.component( "rect" ) ) && !ost.getCollide())
		        {
		    		ost.setCollide( true );
		    		if(!collisionEdge( ost, dritto ))
		        		{
		                	if(ostr.intersects( ost.component( "latoSu" ) ))
		            			{
		                			if(speedX <= 0 && speedY > 0)
		                				if(dritto)
		                					speedY = -speedY;
		                				else if(ostr.intersects( ost.component( "latoDx" ) ))
		                					if(ostr.getCenterX() > ost.getMaxX())
		                						speedX = -speedX;
		                					else
		                						speedY = -speedY;
		                				else
		                					speedY = -speedY;
		                			else if(speedX >= 0 && speedY > 0)
		                				if(dritto)
		                					speedY = -speedY;
		                				else if(ostr.intersects( ost.component( "latoSx" ) ))
		                					if(ostr.getCenterX() < ost.getX())
		                						speedX = -speedX;
		                					else
		                						speedY = -speedY;
		                				else
		                					speedY = -speedY;
		                		}
		                	else if(ostr.intersects( ost.component( "latoGiu" ) ))
		                		{
		            				if(speedX < 0 && speedY < 0)
		            					if(dritto)
		            						speedY = -speedY;
		            					else if(ostr.intersects( ost.component( "latoDx" ) ))
		            						if(ostr.getCenterX() > ost.getMaxX())
		            							speedX = -speedX;
		            						else
		            							speedY = -speedY;
		            					else
		            						speedY = -speedY;
		            				else if(speedX > 0 && speedY < 0)
		            					if(dritto)
		            						speedY = -speedY;
		            					else if(ostr.intersects( ost.component( "latoSx" ) ))
		            						if(ostr.getCenterX() < ost.getX())
		            							speedX = -speedX;
		            						else
		            							speedY = -speedY;
		            					else
		            						speedY = -speedY;
		                		}
		                	else if(ostr.intersects( ost.component( "latoDx" ) ) || ostr.intersects( ost.component( "latoSx" ) ))
		                		speedX = -speedX;
		        		}
		        }
    	}
 
    public void update( GameContainer gc, int delta ) throws SlickException
        {
            for(int i = 0; i < InGame.ostacoli.size(); i++)
                {
                    if(!InGame.ostacoli.get( i ).getID().equals( "bolla" ))
                        {
                        	Ostacolo ost = InGame.ostacoli.get( i );                        	

                			// TODO IMPLEMENTARE SPOSTAMENTO DELLA SFERA ALL'INTERNO DEL TUBO
                        	
                        	if(!primoTubo && !secondoTubo)
                        		{
		                        	if(ost.getID().equals( "tubo" ) && ostr.intersects( ost.component( "latoSu" ) ))
		                        		{                        			
		                        			if(!inTube && indexTube != i)
		                        				{
		                        					indexTube = i;
		                        					primoTubo = true;
		                        					countMove = 0;
		                        				}
		                        		}
                        		}
                        	else if(primoTubo || secondoTubo)
                				gestioneSferaInTubo( delta );
                        	if(secondoTubo)
                        		{
                        			if(indexTube != i)
                        				gestioneCollisioni( ost, true );
                        		}
                        	else if(ostr.intersects( ost.component( "rect" ) ) && !ost.getCollide())
	                            gestioneCollisioni( ost, false );
                            else
                            	ost.setCollide( false );
                        }
                }
             
            /*controllo collisione con i bordi della schermata*/
            if(ostr.getX() + 2*ray >= maxW)
            	if(speedX > 0)
            		speedX = -speedX;
            if(ostr.getX() <= 0)
            	if(speedX < 0)
            		speedX = -speedX;
            if(ostr.getY() + 2*ray >= maxH)
            	if(speedY > 0)
            		speedY = -speedY;
            if(ostr.getY() <= 0)
            	if(speedY < 0)
            		speedY = -speedY;
 
            setCenter( ostr, speedX, speedY );
        }
 
    public void setType(String type)
        {}
 
    public float getMaxX()
        { return ray*2; }
 
    public Ostacolo clone() {
        try {
            return new Bubble( (int) ostr.getX(), (int) ostr.getY(), (int) ray, maxW );
        } catch (SlickException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setInsert(boolean insert, boolean change)
		{
			checkInsert = !change;
			this.insert = insert;
		}

	public void update(GameContainer gc) throws SlickException 
		{}

    public void setOrienting()
    	{}

    public String getOrienting()
    	{ return null; }

    public void setSpigoli()
    	{}

	public int getUnion()
		{ return -1; }

	public void setUnion(int val)
		{}

	public Point getMidArea()
		{ return null; }
}
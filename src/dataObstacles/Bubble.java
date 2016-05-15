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
    private int ray;
     
    private Circle ostr;
 
    private int speedX = 1, speedY = 1;
     
    private Image immagine = new Image( "./data/Image/palla.png" );
     
    private double maxH;
    private double maxW;
     
    private boolean collided;
	
	private boolean insert = false, checkInsert = false;
	
	private Color cg = new Color( 50, 170, 50, 100 ), cr = new Color( 170, 50, 50, 100 );
     
    public Bubble( Ostacolo ost ) throws SlickException
        { this( ost.getX(), ost.getY(), (int) ost.getWidth(), ost.getMaxWidth() ); }
     
    public Bubble( int x, int y, int ray, double maxW ) throws SlickException
        {       
            super( "bolla" );
 
            collided = false;
             
            this.ray = ray;
            ostr = new Circle( x + ray, y + ray, ray );
            
            this.maxW = maxW;
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
 
    public void setXY(int x, int y, String function)
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
 
    public int getX()
        { return (int) ostr.getX(); }
 
    public int getY()
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
 
    public void setSpeedX( int val )
        { speedX = val; }
 
    public void setSpeedY( int val )
        { speedY = val; }
     
    public void collisionBall( Ostacolo ost )
        {
            int newSpeedX, newSpeedY;
         
            newSpeedX = speedX + 2 * ost.getSpeedX();
            newSpeedY = speedY + 2 * ost.getSpeedY();
             
            ost.setSpeedX( ost.getSpeedX() + 2 * speedX );
            ost.setSpeedY( ost.getSpeedY() + 2 * speedY );
             
            speedX = newSpeedX;
            speedY = newSpeedY;
             
            collided = true;
            ost.setCollided( true );
        }
 
    public void update( GameContainer gc ) throws SlickException
        {           
            boolean collide = false;
             
            // TODO SISTEMARE LA COMPENETRAZIONE SFERE-SBARRE
            for(int i = 0; i < InGame.ostacoli.size(); i++)
                {
                    Ostacolo ost = InGame.ostacoli.get( i );
                    if(!ost.ID.equals( "bolla" ))
                        {
                            if(ostr.intersects( ost.component( "rect" ) ))
                                {
                                    collide = true;
                                    if(ostr.intersects( ost.component( "latoSx" ) ) || ostr.intersects( ost.component( "latoDx" ) ))
                                        speedX = -speedX;
                                    else if(ostr.intersects( ost.component( "latoSu" ) ) || ostr.intersects( ost.component( "latoGiu" ) ))
                                        speedY = -speedY;
                                    else if(ostr.intersects( ost.component( "spigADx" ) ))
                                        {   
                                            if(speedX < 0 && speedY > 0)
                                                {
                                                    speedX = -speedX;
                                                    speedY = -speedY;
                                                }
                                            else if(speedX > 0 && speedY > 0)
                                                speedY = -speedY;
                                            else
                                                speedX = -speedX;
                                        }
                                    else if(ostr.intersects( ost.component( "spigBDx" ) ))
                                        {
                                            if(speedX < 0 && speedY < 0)
                                                {
                                                    speedX = -speedX;
                                                    speedY = -speedY;
                                                }
                                            else if(speedX < 0 && speedY > 0)
                                                speedX = -speedX;
                                            else
                                                speedY = -speedY;
                                        }
                                    else if(ostr.intersects( ost.component( "spigASx" ) ))
                                        {
                                            if(speedY > 0 && speedX > 0)
                                                {
                                                    speedX = -speedX;
                                                    speedY = -speedY;
                                                }
                                            else if(speedX > 0 && speedY < 0)
                                                speedX = -speedX;
                                            else
                                                speedY = -speedY;
                                        }
                                    else if(ostr.intersects( ost.component( "spigBSx" ) ))
                                        {
                                            if(speedX > 0 && speedY < 0)
                                                {
                                                    speedX = -speedX;
                                                    speedY = -speedY;
                                                }
                                            else if(speedX < 0 && speedY < 0)
                                                speedY = -speedY;
                                            else
                                                speedX = -speedX;
                                        }
                                }
                        }
                }
             
            /*controllo collisione con i bordi della schermata*/
            if(!collide)
                {
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
                }
 
            setCenter( ostr, speedX, speedY );
        }
 
    public void setType(String type)
        {}
 
    public float getMaxX()
        { return ray*2; }
 
    public Ostacolo clone() {
        try {
            return new Bubble( (int) ostr.getX(), (int) ostr.getY(), ray, maxW );
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

	public void update(GameContainer gc, int delta) throws SlickException 
		{}
}
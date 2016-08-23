package dataObstacles;

import interfaces.InGame;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import bubbleMaster.Start;

public class Player extends Ostacolo
{
	private Image pgsx, pgdx;

	/**false = non sto saltando - true = sto saltando*/
	private boolean jump = false;

	private int offset = 15;
	
	private float xPlayer;
	private float yPlayer;
	private int widthI = 60, width = widthI - offset;
	private int height = 70;
	
	private Shot fire;
	private boolean shooting;
	
	private Rectangle area;
	
	private int numPlayer;
	
	private int maxHeight;
	
	private int maxJump = 0, tempJump;
	
	private int dir = 0;
	
	/*altezza e larghezza dei frame dello spostamento laterale*/
	private int widthS = 36, heightS = 41;
	/*larghezza e altezza dei frame del salto*/
	private int widthJ = 29, heightJ = 48;
	
	private boolean insert = false, checkInsert = false;
	
	private Color cg = new Color( 50, 170, 50, 100 ), cr = new Color( 170, 50, 50, 100 );
	
	private Image right[], left[], saltoDx[], saltoSx[];
	
	private float animTimeMove = 504, reachDelta = 0, animTimeJump = 396, reachDeltaJump = 0;
	private int countShot;
	
	private SpriteSheet sheetDx = new SpriteSheet( new Image( "./data/Image/animdx.png" ), 324, 41 );
	private SpriteSheet sheetSx = new SpriteSheet( new Image( "./data/Image/animsx.png" ), 324, 41 );	
	private SpriteSheet sheetJumpDx = new SpriteSheet( new Image( "./data/Image/jumpDx.png" ), 261, 48 );
	private SpriteSheet sheetJumpSx = new SpriteSheet( new Image( "./data/Image/jumpSx.png" ), 261, 48 );
	
	/*movimento a destra - movimento a sinistra - movimento in alto - movimento in basso*/
	boolean movingDx, movingSx, movingJ;
	
	public Player( int x, int y, int numPlayer ) throws SlickException
		{
			super( "player" + (numPlayer + 1) );
			
			fire = new Shot();
			
			this.numPlayer = numPlayer;	
			
			right = new Image[9];
			left = new Image[9];
			saltoDx = new Image[9];
			saltoSx = new Image[9];

			if(numPlayer == 0)
				{
					pgdx = new Image( "./data/Image/pgdx1.png" );
					pgsx = new Image( "./data/Image/pgsx1.png" );
				}
			else
				{
					pgdx = new Image( "./data/Image/pgdx1.png" );
					pgsx = new Image( "./data/Image/pgsx1.png" );
				}
			
			xPlayer = x;
			yPlayer = y;
			
			area = new Rectangle( xPlayer, yPlayer, width, height );
			
			for(int i = 0; i < 9; i++)
				{
					right[i] = sheetDx.getSubImage( widthS * i, 0, widthS, heightS );
					left[i] = sheetSx.getSubImage( sheetSx.getWidth() - widthS * (i + 1), 0, widthS, heightS );
					saltoDx[i] = sheetJumpDx.getSubImage( widthJ * i, 0, widthJ, heightJ );
					saltoSx[i] = sheetJumpSx.getSubImage( sheetJumpSx.getWidth() - widthJ * (i + 1), 0, widthJ, heightJ );
				}
			
			countShot = 0;
		}
	
	public void draw( Graphics g ) throws SlickException
		{			
			float frameMove = animTimeMove/right.length, frameJump = animTimeJump/saltoDx.length;
			
			if(dir == 0)
				{
					if(movingJ)
						{
							if(reachDeltaJump < frameJump)
								saltoDx[0].draw( xPlayer, yPlayer, width, height );
							else if(reachDeltaJump < frameJump * 2)
								saltoDx[1].draw( xPlayer, yPlayer, width, height );
							else if(reachDeltaJump < frameJump * 3)
								saltoDx[2].draw( xPlayer, yPlayer, width, height );
							else if(reachDeltaJump < frameJump * 4)
								saltoDx[3].draw( xPlayer, yPlayer, width, height );
							else if(reachDeltaJump < frameJump * 5)
								saltoDx[4].draw( xPlayer, yPlayer, width, height );
							else if(reachDeltaJump < frameJump * 6)
								saltoDx[5].draw( xPlayer, yPlayer, width, height );
							else if(reachDeltaJump < frameJump * 7)
								saltoDx[6].draw( xPlayer, yPlayer, width, height );
							else if(reachDeltaJump < frameJump * 8)
								saltoDx[7].draw( xPlayer, yPlayer, width, height );
							else
								saltoDx[8].draw( xPlayer, yPlayer, width, height );
						}
					else if(movingDx)
						{
							if(reachDelta < frameMove)
								right[0].draw( xPlayer, yPlayer, widthI, height );
							else if(reachDelta < frameMove*2)
								right[1].draw( xPlayer, yPlayer, widthI, height );
							else if(reachDelta < frameMove*3)
								right[2].draw( xPlayer, yPlayer, widthI, height );
							else if(reachDelta < frameMove*4)
								right[3].draw( xPlayer, yPlayer, widthI, height );
							else if(reachDelta < frameMove*5)
								right[4].draw( xPlayer, yPlayer, widthI, height );
							else if(reachDelta < frameMove*6)
								right[5].draw( xPlayer, yPlayer, widthI, height );
							else if(reachDelta < frameMove*7)
								right[6].draw( xPlayer, yPlayer, widthI, height );
							else if(reachDelta < frameMove*8)
								right[7].draw( xPlayer, yPlayer, widthI, height );
							else if(reachDelta <= frameMove*9)
								right[8].draw( xPlayer, yPlayer, widthI, height );
						}
					else
						pgdx.draw( xPlayer, yPlayer, widthI, height );
				}
			else 
				{
					if(movingJ)
						{
							if(reachDeltaJump < frameJump)
								saltoSx[0].draw( xPlayer, yPlayer, width, height );
							else if(reachDeltaJump < frameJump * 2)
								saltoSx[1].draw( xPlayer, yPlayer, width, height );
							else if(reachDeltaJump < frameJump * 3)
								saltoSx[2].draw( xPlayer, yPlayer, width, height );
							else if(reachDeltaJump < frameJump * 4)
								saltoSx[3].draw( xPlayer, yPlayer, width, height );
							else if(reachDeltaJump < frameJump * 5)
								saltoSx[4].draw( xPlayer, yPlayer, width, height );
							else if(reachDeltaJump < frameJump * 6)
								saltoSx[5].draw( xPlayer, yPlayer, width, height );
							else if(reachDeltaJump < frameJump * 7)
								saltoSx[6].draw( xPlayer, yPlayer, width, height );
							else if(reachDeltaJump < frameJump * 8)
								saltoSx[7].draw( xPlayer, yPlayer, width, height );
							else
								saltoSx[8].draw( xPlayer, yPlayer, width, height );
						}
					else if(movingSx)
						{
							if(reachDelta < frameMove)
								left[0].draw( xPlayer - offset, yPlayer, widthI, height );
							else if(reachDelta < frameMove*2)
								left[1].draw( xPlayer - offset, yPlayer, widthI, height );
							else if(reachDelta < frameMove*3)
								left[2].draw( xPlayer - offset, yPlayer, widthI, height );
							else if(reachDelta < frameMove*4)
								left[3].draw( xPlayer - offset, yPlayer, widthI, height );
							else if(reachDelta < frameMove*5)
								left[4].draw( xPlayer - offset, yPlayer, widthI, height );
							else if(reachDelta < frameMove*6)
								left[5].draw( xPlayer - offset, yPlayer, widthI, height );
							else if(reachDelta < frameMove*7)
								left[6].draw( xPlayer - offset, yPlayer, widthI, height );
							else if(reachDelta < frameMove*8)
								left[7].draw( xPlayer - offset, yPlayer, widthI, height );
							else if(reachDelta <= frameMove*9)
								left[8].draw( xPlayer - offset, yPlayer, widthI, height );
						}
					else
						pgsx.draw( xPlayer - offset, yPlayer, widthI, height );
				}
			/*inserisce la trasparenza rosso/verde nella modalita' di editing*/
			if(Start.editGame == 1)
				if(checkInsert)
					if(!insert)
						pgdx.draw( xPlayer, yPlayer, widthI, height, cr);
					else
						pgdx.draw( xPlayer, yPlayer, widthI, height, cg);
			
			if(shooting)
				fire.draw();
		}

	public boolean contains( int x, int y )
		{ return area.contains( x, y ); }

	public void setXY( float x, float y, String function ) 
		{
			if(function.equals( "move" ))
				{
					xPlayer = xPlayer + x;
					yPlayer = yPlayer + y;
				}
			
			else if(function.equals( "restore" ))
				{
					xPlayer = x;
					yPlayer = y;
				}
			
			area.setLocation( xPlayer, yPlayer );
		}
	
	public void setMaxHeight( double val )
		{ this.maxHeight = (int) val; }

	public Ostacolo clone() {
		try {
			return new Player( (int) xPlayer, (int) yPlayer, numPlayer );
		} catch (SlickException e) {
			e.printStackTrace();
			return null;
		}
	}

	public float getX()	
		{ return xPlayer; }

	public float getY()
		{ return yPlayer; }

	public Shape component( String part )
		{ return area; }

	public void setInsert(boolean insert, boolean change)
		{
			checkInsert = !change;
			this.insert = insert;
		}
	
	public void update( GameContainer gc )
		{}
	
	public void update( GameContainer gc, int delta ) throws SlickException
		{
			Input input = gc.getInput();
			
			int move = 2;
			
			/*la posizione del player un attimo prima di spostarsi*/
			Rectangle previousArea = new Rectangle( area.getX(), area.getY(), width, height );
			
			movingDx = false;
			movingSx = false;
			
			/*ZONA CONTROLLO COLLISIONE PERSONAGGIO - SFERE*/

			/*for(int i = 0; i < InGame.ostacoli.size(); i++)
				if(InGame.ostacoli.get( i ).ID.equals( "bolla" ))
					if(area.intersects( InGame.ostacoli.get( i ).component( "" ) ))
						{
							Start.startGame = 0;
							Start.endGame = 1;
						}
			
			/*ZONA SPOSTAMENTI-SALTI*/
			
			if(input.isKeyDown( Input.KEY_RIGHT ))
				{
					movingDx = true;
					dir = 0;
					setXY( move, 0, "move" );
				}
			else if(input.isKeyDown( Input.KEY_LEFT ))
				{
					movingSx = true;
					dir = 1;
					setXY( -move, 0, "move" );
				}
			if(input.isKeyPressed( Input.KEY_S ) && !shooting && Start.startGame == 1)
	            {
	                shooting = true;
	                fire.setXY( (int) xPlayer + width/2 - fire.getWidth()/2, (int) yPlayer + height - 1 );
	            }
			if(shooting)
				{
					if(++countShot % 2 == 0)
						fire.update();
					
					for(int i = 0; i < InGame.ostacoli.size(); i++)
						if(fire.collision( InGame.ostacoli.get( i ), InGame.ostacoli.get( i ).getID() ))
							{
								shooting = false;
								break;
							}
				}
			
			if(input.isKeyPressed( Input.KEY_SPACE ) && !jump)
				{
					movingJ = true;
					jump = true;
					maxJump = 1;
					tempJump = 60;
				}
			if(maxJump > 0)
				setXY( 0, -move + 0.2f * (40 - tempJump--), "move" );
			else
				{
					movingJ = true;
					setXY( 0, move + 0.1f * tempJump++, "move" );
				}
			
			/*controlla se non sono stati superati i limiti della schermata*/
			if(area.getX() + width > gc.getWidth())
				setXY( gc.getWidth() - width, (int) area.getY(), "restore" );
			else if(area.getX() < 0)
				setXY( 0, (int) area.getY(), "restore" );
			if(area.getY() + height > maxHeight)
				{
					maxJump = 0;
					tempJump = 0;
					jump = false;
					movingJ = false;
					reachDeltaJump = 0;
					setXY( (int) area.getX(), maxHeight - height, "restore" );
				}
			else if(area.getY() < 0)
				{
					maxJump = 0;
					tempJump = 0;
					reachDeltaJump = animTimeJump/5;
					setXY( (int) area.getX(), 0, "restore" );
				}
		
			/*controlla la collisione con gli ostacoli del gioco (tranne le sfere)*/
			for(int i = 0; i < InGame.ostacoli.size(); i++)
				{
					Ostacolo ost = InGame.ostacoli.get( i );
					if(!ost.getID().equals( "bolla" ))
						{
							if(area.intersects( ost.component( "rect" ) ))
								{
									if(area.intersects( ost.component( "latoSu" ) ) && (previousArea.getY() + height <= ost.getY()))
										{
											maxJump = 0;
											tempJump = 0;
											jump = false;
											movingJ = false;
											reachDeltaJump = 0;
											setXY( (int) area.getX(), (int) (ost.getY() - height), "restore" );
										}										
									else if(area.intersects( ost.component( "latoGiu" ) ) && (previousArea.getY() > ost.getY() + ost.getHeight()))
										{
											maxJump = 0;
											tempJump = 0;
											reachDeltaJump = animTimeJump/5;
											setXY( (int) area.getX(), (int) (ost.getY() + ost.getHeight()), "restore" );
										}
									else if(area.intersects( ost.component( "latoDx" ) ))
										setXY( (int) (ost.getX() + ost.getWidth()) + 1, (int) area.getY(), "restore" );
									else if(area.intersects( ost.component( "latoSx" ) ))
										setXY( (int) (ost.getX() - width) - 1, (int) area.getY(), "restore" );	
								}
						}
				}
			
			/*controlla la collisione con una sfera*/
			boolean check = true;
			for(int i = 0; i < InGame.ostacoli.size(); i++)
				if(InGame.ostacoli.get(i ).getID().equals( "bolla" ))
					check = false;
			
			if(check)
				{
					Start.startGame = 0;
					Start.endGame = 1;
				}
			
			/*gestione dell'animazione*/
			if(movingDx || movingSx)
				reachDelta = (reachDelta + delta) % animTimeMove;
			if(jump)
				if(reachDeltaJump < animTimeJump)
					reachDeltaJump = reachDeltaJump + delta;
		}

	public void setType( String type )
		{}

	public float getMaxX()
		{ return xPlayer + width;	}

	public float getWidth()
		{ return width;	}

	public float getHeight() 
		{ return height;	}

	public void isCollision( Bubble bolla )
		{}

	public Shape getArea()
		{ return null; }

	public double getMaxHeight()
		{ return maxHeight; }

	public int getSpeedX()
		{ return 0; }

	public int getSpeedY()
		{ return 0; }

	public void setSpeed(Integer x, Integer y)
		{}
	
	public void setCollided( boolean val )
		{}
	
	public boolean isCollided()
		{ return true; }

	public double getMaxWidth()
		{ return 0; }

	public boolean getCollide()
		{ return false; }

	public void setCollide( boolean val )
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
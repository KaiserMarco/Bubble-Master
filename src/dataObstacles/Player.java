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
	
	private int xPlayer;
	private int yPlayer;
	private int width = 60;
	private int height = 70;
	
	private Shot fire;
	private boolean shooting = false;
	
	private Rectangle area;
	
	private int numPlayer;
	
	private int maxHeight;
	
	private int maxJump = 0;
	private boolean glide = true;
	
	private int dir = 0;
	
	private boolean insert = false, checkInsert = false;
	
	private Color cg = new Color( 50, 170, 50, 100 ), cr = new Color( 170, 50, 50, 100 );
	
	private Image right[], left[], jump1[], jump2[];
	
	private float animTimeMove = 504, reachDelta = 0, animTimeJump1 = 205, animTimeJump2 = 205;
	
	private SpriteSheet sheetDx = new SpriteSheet( new Image( "./data/Image/animdx.png" ), 324, 41 ), sheetSx = new SpriteSheet( new Image( "./data/Image/animsx.png" ), 324, 41 );	
	private SpriteSheet sheetJump1 = new SpriteSheet( new Image( "./data/Image/firstJump.png" ), 150, 48 ), sheetJump2 = new SpriteSheet( new Image( "./data/Image/secondoJump.png" ), 123, 44 );
	
	/*movimento a destra - movimento a sinistra - movimento in alto - movimento in basso*/
	boolean movingDx, movingSx, movingJUp, movingJDw;
	
	public Player( int x, int y, int numPlayer ) throws SlickException
		{
			super( "player" + (numPlayer + 1) );
			
			fire = new Shot();
			
			this.numPlayer = numPlayer;	
			
			right = new Image[9];
			left = new Image[9];
			jump1 = new Image[5];
			jump2 = new Image[5];
			
			// TODO SISTEMARE MEGLIO I PERSONAGGI
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
			
			int widthS = 36, heightS = 41;
			int widthJ = 31, heightJ = 48;
			
			for(int i = 0; i < 9; i++)
				{
					right[i] = sheetDx.getSubImage( widthS * i, 0, widthS, heightS);
					left[i] = sheetSx.getSubImage( sheetSx.getWidth() - widthS * (i + 1), 0, widthS, heightS );
				}
			for(int i = 0; i < 5; i++)
				{
					jump1[i] = sheetJump1.getSubImage( widthJ * i, 0, widthJ, heightJ );
					jump2[i] = sheetJump2.getSubImage( widthJ * i, 0, widthJ, heightJ );
				}
		}
	
	public void draw( Graphics g ) throws SlickException
		{
			float frameMove = animTimeMove/right.length, frameJump1 = animTimeJump1/jump1.length, frameJump2 = animTimeJump2/jump2.length;
			/*if(glide)
				{
					if(maxJump > 0)
						{
							if(reachDelta < frameJump1)
								jump1[0].draw( xPlayer, yPlayer, width, height );
							else if(reachDelta < frameJump1*2)
								jump1[1].draw( xPlayer, yPlayer, width, height );
							else if(reachDelta < frameJump1*3)
								jump1[2].draw( xPlayer, yPlayer, width, height );
							else if(reachDelta < frameJump1*4)
								jump1[3].draw( xPlayer, yPlayer, width, height );
							else if(reachDelta <= frameJump1*5)
								jump1[4].draw( xPlayer, yPlayer, width, height );
						}
					else
						{
							if(reachDelta < frameJump2)
								jump2[0].draw( xPlayer, yPlayer, width, height );
							else if(reachDelta < frameJump2*2)
								jump2[1].draw( xPlayer, yPlayer, width, height );
							else if(reachDelta < frameJump2*3)
								jump2[2].draw( xPlayer, yPlayer, width, height );
							else if(reachDelta < frameJump2*4)
								jump2[3].draw( xPlayer, yPlayer, width, height );
							else
								jump2[4].draw( xPlayer, yPlayer, width, height );
						}
				}*/
			if(dir == 0)
				{
					if(movingDx)
						{
							if(reachDelta < frameMove)
								right[0].draw( xPlayer, yPlayer, width, height );
							else if(reachDelta < frameMove*2)
								right[1].draw( xPlayer, yPlayer, width, height );
							else if(reachDelta < frameMove*3)
								right[2].draw( xPlayer, yPlayer, width, height );
							else if(reachDelta < frameMove*4)
								right[3].draw( xPlayer, yPlayer, width, height );
							else if(reachDelta < frameMove*5)
								right[4].draw( xPlayer, yPlayer, width, height );
							else if(reachDelta < frameMove*6)
								right[5].draw( xPlayer, yPlayer, width, height );
							else if(reachDelta < frameMove*7)
								right[6].draw( xPlayer, yPlayer, width, height );
							else if(reachDelta < frameMove*8)
								right[7].draw( xPlayer, yPlayer, width, height );
							else if(reachDelta <= frameMove*9)
								right[8].draw( xPlayer, yPlayer, width, height );
						}
					else
						pgdx.draw( xPlayer, yPlayer, width, height );
					if(Start.editGame == 1)
						if(checkInsert)
							{
								if(!insert)
									pgdx.draw( xPlayer, yPlayer, width, height, cr);
								else
									pgdx.draw( xPlayer, yPlayer, width, height, cg);
							}
				}
			else
				if(movingSx)
					{
						if(reachDelta < frameMove)
							left[0].draw( xPlayer, yPlayer, width, height );
						else if(reachDelta < frameMove*2)
							left[1].draw( xPlayer, yPlayer, width, height );
						else if(reachDelta < frameMove*3)
							left[2].draw( xPlayer, yPlayer, width, height );
						else if(reachDelta < frameMove*4)
							left[3].draw( xPlayer, yPlayer, width, height );
						else if(reachDelta < frameMove*5)
							left[4].draw( xPlayer, yPlayer, width, height );
						else if(reachDelta < frameMove*6)
							left[5].draw( xPlayer, yPlayer, width, height );
						else if(reachDelta < frameMove*7)
							left[6].draw( xPlayer, yPlayer, width, height );
						else if(reachDelta < frameMove*8)
							left[7].draw( xPlayer, yPlayer, width, height );
						else if(reachDelta <= frameMove*9)
							left[8].draw( xPlayer, yPlayer, width, height );
					}
				else
					pgsx.draw( xPlayer, yPlayer, width, height );
			
			if(shooting)
				{
					fire.draw();
					shooting = false;
				}
		}

	public boolean contains( int x, int y )
		{ return area.contains( x, y ); }

	public void setXY( int x, int y, String function ) 
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
			return new Player( xPlayer, yPlayer, numPlayer );
		} catch (SlickException e) {
			e.printStackTrace();
			return null;
		}
	}

	public int getX()	
		{ return xPlayer; }

	public int getY()
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
			movingJUp = false;
			movingJDw = false;
			
			/*ZONA CONTROLLO COLLISIONE PERSONAGGIO - SFERE*/

			/*for(int i = 0; i < InGame.ostacoli.size(); i++)
				if(InGame.ostacoli.get( i ).ID.equals( "bolla" ))
					if(area.intersects( InGame.ostacoli.get( i ).component( "" ) ))
						{
							Start.startGame = 0;
							Start.endGame = 1;
						}
			
			/*ZONA SPOSTAMENTI/SALTI*/
			
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
			if(glide)
				{
					if(input.isKeyPressed( Input.KEY_SPACE ) && !jump)
						{
							jump = true;
							maxJump = 40;
						}
					/*fase di salita*/
					if(maxJump > 0)
						{
							movingJUp = true;
							maxJump--;
							setXY( 0, -move, "move" );
						}
					/*fase di discesa*/
					else if(maxJump == 0 || glide)
						{
							movingJDw = true;
							setXY( 0, move, "move" );
						}
				}

			if(movingDx || movingSx || movingJUp || movingJDw)
				{
					/*controlla se non sono stati superati i limiti della schermata*/
					if(area.getX() + width > gc.getWidth())
						setXY( gc.getWidth() - width, (int) area.getY(), "restore" );
					else if(area.getX() < 0)
						setXY( 0, (int) area.getY(), "restore" );
					if(area.getY() + height > maxHeight)
						{
							maxJump = -1;
							jump = false;
							setXY( (int) area.getX(), maxHeight - height, "restore" );
						}
					else if(area.getY() < 0)
						{
							maxJump = 0;
							setXY( (int) area.getX(), 0, "restore" );
						}
				
					/*controlla la collisione con gli ostacoli del gioco (tranne le sfere)*/
					for(int i = 0; i < InGame.ostacoli.size(); i++)
						{
							Ostacolo ost = InGame.ostacoli.get( i );
							if(!ost.ID.equals( "bolla" ))
								{
									if(area.intersects( ost.component( "rect" ) ))
										{
											if(area.intersects( ost.component( "latoSu" ) ) && (previousArea.getY() + height <= ost.getY()))
												{
													maxJump = -1;
													jump = false;
													setXY( (int) area.getX(), (int) (ost.getY() - height), "restore" );
												}										
											else if(area.intersects( ost.component( "latoGiu" ) ) && (previousArea.getY() > ost.getY() + ost.getHeight()))
												{
													maxJump = 0;
													setXY( (int) area.getX(), (int) (ost.getY() + ost.getHeight()), "restore" );
												}
											else if(area.intersects( ost.component( "latoDx" ) ))
												setXY( (int) (ost.getX() + ost.getWidth()) + 1, (int) area.getY(), "restore" );
											else if(area.intersects( ost.component( "latoSx" ) ))
												setXY( (int) (ost.getX() - width) - 1, (int) area.getY(), "restore" );	
										}
								}
						}
				}
			
			/*controlla la collisione con una sfera*/
			boolean check = true;
			for(int i = 0; i < InGame.ostacoli.size(); i++)
				if(InGame.ostacoli.get(i ).ID.equals( "bolla" ))
					check = false;
			
			if(check)
				{
					Start.startGame = 0;
					Start.endGame = 1;
				}
			
			/*gestione dell'animazione*/
			if(movingDx || movingSx || glide)
				{
					reachDelta = reachDelta + delta;
					if(reachDelta > animTimeMove)
						reachDelta = 0;
				}
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

	public void setSpeedX(int val)
		{}

	public void setSpeedY(int val)
		{}
	
	public void setCollided( boolean val )
		{}
	
	public boolean isCollided()
		{ return true; }
}
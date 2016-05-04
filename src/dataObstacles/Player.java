package dataObstacles;

import interfaces.InGame;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
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
	
	private int maxJump;
	
	private int dir = 0;
	
	public Player( int x, int y, int numPlayer ) throws SlickException
		{
			super( "player" + (numPlayer + 1) );
			
			fire = new Shot();
			
			this.numPlayer = numPlayer;			
			
			// TODO SISTEMARE MEGLIO I PERSONAGGI
			if(numPlayer == 0)
				{
					pgdx = new Image( "./data/Image/pg2.png" );
					pgsx = new Image( "./data/Image/pg2sx.png" );
				}
			else
				{
					pgdx = new Image( "./data/Image/pg2.png" );
					pgsx = new Image( "./data/Image/pg2sx.png" );
				}
			
			xPlayer = x;
			yPlayer = y;
			
			area = new Rectangle( xPlayer, yPlayer, width, height );
		}
	
	public void draw( Graphics g ) throws SlickException
		{
			if(dir == 0)
				pgdx.draw( xPlayer, yPlayer, width, height );
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
	
	public void setMaxHeight( int val )
		{ this.maxHeight = val; }

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
	
	public void update( GameContainer gc ) throws SlickException
		{
			Input input = gc.getInput();
			
			Rectangle testMove;
			
			int move = 2;
			
			Ostacolo ost = null;
			
			boolean hurt = false;
			boolean glide = true;
			
			/*ZONA CONTROLLO COLLISIONE PERSONAGGIO - SFERE*/

			/*for(int i = 0; i < InGame.ostacoli.size(); i++)
				if(InGame.ostacoli.get( i ).ID.equals( "bolla" ))
					if(area.intersects( InGame.ostacoli.get( i ).component( "" ) ))
						{
							Start.startGame = 0;
							Start.endGame = 1;
						}
			
			/*ZONA SPOSTAMENTI/SALTI*/	
			
			if(numPlayer == 0 && input.isKeyDown( Input.KEY_RIGHT ))
				{
					dir = 0;
					testMove = new Rectangle( xPlayer + move, yPlayer, width, height );

					if(testMove.getX() + width >= gc.getWidth())
						xPlayer = gc.getWidth() - width;
					
					for(int i = 0; i < InGame.ostacoli.size(); i++)
						{
							ost = InGame.ostacoli.get( i );
							if(!ost.ID.equals( "bolla" ))
								{
									if(testMove.intersects( ost.component( "latoSx" )))
										{
											hurt = true;
											xPlayer = ost.getX() - width;
										}
									if(glide)
										if(testMove.intersects( ost.component( "latoSu" ) ))
											glide = false;
								}
						}
					if(!hurt)
						xPlayer = xPlayer + move;
					if(glide)
						if(yPlayer + height < maxHeight - 1 && !jump)
							{
								jump = true;
								maxJump = 0;
							}
				}
			else if(numPlayer == 0 && input.isKeyDown( Input.KEY_LEFT ))
				{
					dir = 1;
					testMove = new Rectangle( xPlayer - move, yPlayer, width, height );

					if(testMove.getX() <= 0)
						xPlayer = 0;
					
					for(int i = 0; i < InGame.ostacoli.size(); i++)
						{
							ost = InGame.ostacoli.get( i );
							if(!ost.ID.equals( "bolla" ))
								{
									if(testMove.intersects( ost.component( "latoDx" )))
										{
											hurt = true;
											xPlayer = (int) (ost.getMaxX()) + 1;
										}
									if(glide)
										if(testMove.intersects( ost.component( "latoSu" ) ))
											glide = false;
								}
						}
					if(!hurt)
						xPlayer = xPlayer - move;
					if(glide)
						if(yPlayer + height < maxHeight && !jump)
							{
								jump = true;
								maxJump = 0;
							}
				}
			
			if(numPlayer == 0 && (input.isKeyPressed( Input.KEY_S )))
				{
					shooting = true;
					fire.setXY( xPlayer + width/2 - fire.width/2, yPlayer - fire.height );
					for(int i = 0; i < InGame.ostacoli.size(); i++)
						if(InGame.ostacoli.get( i ).ID.equals( "bolla" ))
							if(fire.collision( InGame.ostacoli.get( i ), i ))
								break;
				}
			
			hurt = false;
			if(numPlayer == 0 && (input.isKeyDown( Input.KEY_SPACE ) || jump))
				{
					if(!jump)
						{
							jump = true;
							maxJump = 40;
						}
					
					if(maxJump > 0)
						{
							testMove = new Rectangle( xPlayer, yPlayer - move, width, height );
							
							for(int i = 0; i < InGame.ostacoli.size(); i++)
								{
									ost = InGame.ostacoli.get( i );
									if(!ost.ID.equals( "bolla" ))
										{
											if(testMove.intersects( ost.component( "latoGiu" )))
												{
													hurt = true;
													yPlayer = (int) (ost.getY() + ost.getHeight());
													maxJump = 0;
												}
										}
								}
							if(!hurt)
								{
									if(testMove.getY() <= 0)
										{
											yPlayer = 0;
											maxJump = 0;
										}
									else
										{
											yPlayer = yPlayer - move;
											maxJump = maxJump - 1;
										}
								}
						}
					else if(maxJump == 0)
						{
							testMove = new Rectangle( xPlayer, yPlayer + move, width, height );
							for(int i = 0; i < InGame.ostacoli.size(); i++)
								{
									ost = InGame.ostacoli.get( i );
									if(!ost.ID.equals( "bolla" ))
										{
											if(testMove.intersects( ost.component( "latoSu" )))
												{
													hurt = true;
													yPlayer = (int) (ost.getY() - height);
													jump = false;
												}
										}
								}
							if(!hurt)
								{
									if(testMove.getY() + height >= maxHeight)
										{
											yPlayer = maxHeight - height;
											jump = false;
										}
									else
										yPlayer = yPlayer + move;
								}
						}
				}
			
			setXY( xPlayer, yPlayer, "restore" );
			
			boolean check = true;
			for(int i = 0; i < InGame.ostacoli.size(); i++)
				if(InGame.ostacoli.get(i ).ID.equals( "bolla" ))
					check = false;
			
			if(check)
				{
					Start.startGame = 0;
					Start.endGame = 1;
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

	public int getMaxHeight()
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
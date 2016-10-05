package dataObstacles;

import interfaces.InGame;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import Utils.Global;
import bubbleMaster.Start;
import dataPowers.PowerUp;

public class Player extends Ostacolo
{
	private Image pgsx, pgdx;

	/**false = non sto saltando - true = sto saltando*/
	private boolean jump = false;

	private int offset;
	
	private float xPlayer;
	private float yPlayer;
	private float widthI;
	private float width, height;
	
	private ArrayList <Shot> fire;	
	// determina il numero di colpi sparati
	private int shots;
	
	private Rectangle area, body, head;
	
	private int numPlayer;
	
	private int maxHeight;
	
	private int maxJump = 0, tempJump;
	
	// la direzione di movimento del personaggio
	private int dir = 0;
	
	/*sottodivisione del rettangolo in lati e spigoli*/
	public Rectangle latoSu = null, latoGiu = null, latoDx = null, latoSx = null;
	public Rectangle spigASx = null, spigADx = null, spigBSx = null, spigBDx = null;
	
	/*altezza e larghezza dei frame dello spostamento laterale*/
	private int widthS, heightS;
	/*larghezza e altezza dei frame del salto*/
	private int widthJ, heightJ;
	
	private boolean insert = false, checkInsert = false;
	
	private Color cg = new Color( 50, 170, 50, 100 ), cr = new Color( 170, 50, 50, 100 );
	private Color imm = new Color( 28, 57, 187, 220 );
	
	private Image right[], left[], saltoDx[], saltoSx[];
	
	private float animTime, animTimeMove, animTimeJump;
	
	private SpriteSheet sheetDx;
	private SpriteSheet sheetSx;	
	private SpriteSheet sheetJumpDx;
	private SpriteSheet sheetJumpSx;
	
	private int wMove, hMove;
	private int wJump, hJump;
	
	/*movimento a destra - movimento a sinistra - movimento in alto - movimento in basso*/
	boolean movingDx, movingSx, movingJ;
	
	// l'immagine delle vite del personaggio
	private Image heart, halfHeart, noHeart;
	private int widthH, heightH;
	
	//le vite/i punti del personaggio
	private int lifes, points;
	// determina se disegnare o meno le vite/i punti del personaggio
	private boolean drawLifes, drawPoints;
	
	//determina se il personaggio e' vulnerabile/mortale
	private boolean invincible, immortal;
	private final int timerInv = 100, tickInv = 2000/timerInv;
	private final int timerImm = 2000;
	private int currentTimeInv, currentTickInv;
	private long currentTimeImm;
	
	// il valore dei frame di movimento e salto
	float frameMove, frameJump;
	
	// i poteri posseduti dal personaggio
	private ArrayList<PowerUp> powerUp;
	
	// determina se il personaggio spara 2 o 3 colpi insieme
	private boolean dShot, tShot;

	// determina se il personaggio sta sparando
	boolean isShoting = false;
	
	public Player( int x, int y, int numPlayer, GameContainer gc ) throws SlickException
		{
			super( "player" + numPlayer );
			
			xPlayer = x;
			yPlayer = y;
			
			fire = new ArrayList<Shot>();
			fire.add( new Shot( gc ) );
			
			offset = gc.getHeight()/40;
			widthI = gc.getHeight()/10;
			
			width = widthI - offset;
			height = gc.getWidth()*100/1142;
			
			this.numPlayer = numPlayer;	
			
			right = new Image[9];
			left = new Image[9];
			
			saltoDx = new Image[9]; saltoSx = new Image[9];
			
			// questi valori devono restare assoluti (altrimenti l'animazione, giustamente, smatta)
			widthS = 36; heightS = 41;
			widthJ = 29; heightJ = 48;
			
			wMove = 32; hMove = 41;
			wJump = 261; hJump = 48;

			if(numPlayer == 1)
				{
					pgdx = new Image( "./data/Image/pgdx1.png" );
					pgsx = new Image( "./data/Image/pgsx1.png" );
					sheetDx = new SpriteSheet( new Image( "./data/Image/animdx.png" ), wMove, hMove );
					sheetSx = new SpriteSheet( new Image( "./data/Image/animsx.png" ), wMove, hMove );
					sheetJumpDx = new SpriteSheet( new Image( "./data/Image/jumpDx.png" ), wJump, hJump );
					sheetJumpSx = new SpriteSheet( new Image( "./data/Image/jumpSx.png" ), wJump, hJump );
				}
			else if(numPlayer == 2)
				{
					pgdx = new Image( "./data/Image/pgdx2.png" );
					pgsx = new Image( "./data/Image/pgsx2.png" );
					sheetDx = new SpriteSheet( new Image( "./data/Image/animdx2.png" ), wMove, hMove );
					sheetSx = new SpriteSheet( new Image( "./data/Image/animsx2.png" ), wMove, hMove );
					sheetJumpDx = new SpriteSheet( new Image( "./data/Image/jumpDx2.png" ), wJump, hJump );
					sheetJumpSx = new SpriteSheet( new Image( "./data/Image/jumpSx2.png" ), wJump, hJump );
				}
			else if(numPlayer == 3)
				{
					pgdx = new Image( "./data/Image/pgdx3.png" );
					pgsx = new Image( "./data/Image/pgsx3.png" );
					sheetDx = new SpriteSheet( new Image( "./data/Image/animdx3.png" ), wMove, hMove );
					sheetSx = new SpriteSheet( new Image( "./data/Image/animsx3.png" ), wMove, hMove );
					sheetJumpDx = new SpriteSheet( new Image( "./data/Image/jumpDx3.png" ), wJump, hJump );
					sheetJumpSx = new SpriteSheet( new Image( "./data/Image/jumpSx3.png" ), wJump, hJump );
				}
			else if(numPlayer == 4)
				{
					pgdx = new Image( "./data/Image/pgdx4.png" );
					pgsx = new Image( "./data/Image/pgsx4.png" );
					sheetDx = new SpriteSheet( new Image( "./data/Image/animdx4.png" ), wMove, hMove );
					sheetSx = new SpriteSheet( new Image( "./data/Image/animsx4.png" ), wMove, hMove );
					sheetJumpDx = new SpriteSheet( new Image( "./data/Image/jumpDx4.png" ), wJump, hJump );
					sheetJumpSx = new SpriteSheet( new Image( "./data/Image/jumpSx4.png" ), wJump, hJump );
				}
			
			area = new Rectangle( xPlayer, yPlayer, width, height );
			body = new Rectangle( xPlayer, yPlayer + Global.H/40, width, Global.H/10 );
			head = new Rectangle( xPlayer + width/2 - Global.W/600, yPlayer, width/2, Global.H/40 );
			
			for(int i = 0; i < 9; i++)
				{
					right[i] = sheetDx.getSubImage( widthS * i, 0, widthS, heightS );
					left[i] = sheetSx.getSubImage( sheetSx.getWidth() - widthS * (i + 1), 0, widthS, heightS );
					saltoDx[i] = sheetJumpDx.getSubImage( widthJ * i, 0, widthJ, heightJ );
					saltoSx[i] = sheetJumpSx.getSubImage( sheetJumpSx.getWidth() - widthJ * (i + 1), 0, widthJ, heightJ );
				}
			
			animTimeMove = 504;
			animTimeJump = 396;
			animTime = 0;
			
			shots = 0;
			
			heart = new Image( "./data/Image/heart.png" );
			halfHeart = new Image( "./data/Image/halfHeart.png" );
			noHeart = new Image( "./data/Image/noHeart.png" );
			widthH = gc.getWidth()/40; heightH = gc.getHeight()/30;
			
			invincible = false;
			currentTimeInv = 0;
			currentTimeImm = 0;
			
			immortal = false;
			
			lifes  = Global.lifes;
			
			drawLifes = false;
			drawPoints = false;
			
			points = 0;
			
			powerUp = new ArrayList<PowerUp>();
			
			dShot = false; tShot = false;
		}
	
	public void drawMoving( Graphics g )
		{
			frameMove = animTimeMove/right.length;
			frameJump = animTimeJump/saltoDx.length;

			// il personaggio si muove verso destra
			if(dir == 0)
				{
					// il personaggio sta saltando
					if(movingJ)
						{
							area = new Rectangle( xPlayer, yPlayer, width, height );
							body = new Rectangle( xPlayer, yPlayer + Global.H/40, width, Global.H/12 );
							head = new Rectangle( xPlayer + width/2 - Global.W/110, yPlayer, width/2, Global.H/40 );
							
							if(animTime < frameJump)
								{
									if(immortal)
										saltoDx[0].draw( xPlayer, yPlayer, width, height, imm );
									else
										saltoDx[0].draw( xPlayer, yPlayer, width, height );
								}
							else if(animTime < frameJump * 2)
								{
									if(immortal)
										saltoDx[1].draw( xPlayer, yPlayer, width, height, imm );
									else
										saltoDx[1].draw( xPlayer, yPlayer, width, height );
								}
							else if(animTime < frameJump * 3)
								{
									if(immortal)
										saltoDx[2].draw( xPlayer, yPlayer, width, height, imm );
									else
										saltoDx[2].draw( xPlayer, yPlayer, width, height );
								}
							else if(animTime < frameJump * 4)
								{
									if(immortal)
										saltoDx[3].draw( xPlayer, yPlayer, width, height, imm );
									else
										saltoDx[3].draw( xPlayer, yPlayer, width, height );
								}
							else if(animTime < frameJump * 5)
								{
									if(immortal)
										saltoDx[4].draw( xPlayer, yPlayer, width, height, imm );
									else
										saltoDx[4].draw( xPlayer, yPlayer, width, height );
								}
							else if(animTime < frameJump * 6)
								{
									if(immortal)
										saltoDx[5].draw( xPlayer, yPlayer, width, height, imm );
									else
										saltoDx[5].draw( xPlayer, yPlayer, width, height );
								}
							else if(animTime < frameJump * 7)
								{
									if(immortal)
										saltoDx[6].draw( xPlayer, yPlayer, width, height, imm );
									else
										saltoDx[6].draw( xPlayer, yPlayer, width, height );
								}
							else if(animTime < frameJump * 8)
								{
									if(immortal)
										saltoDx[7].draw( xPlayer, yPlayer, width, height, imm );
									else
										saltoDx[7].draw( xPlayer, yPlayer, width, height );
								}
							else
								{
									if(immortal)
										saltoDx[8].draw( xPlayer, yPlayer, width, height, imm );
									else
										saltoDx[8].draw( xPlayer, yPlayer, width, height );
								}
						}
					// il personaggio sta camminando
					else if(movingDx)
						{
							if(animTime < frameMove)
								{
									if(immortal)
										right[0].draw( xPlayer, yPlayer, widthI, height, imm );
									else										
										right[0].draw( xPlayer, yPlayer, widthI, height );
								}
							else if(animTime < frameMove*2)
								{
									if(immortal)
										right[1].draw( xPlayer, yPlayer, widthI, height, imm );
									else										
										right[1].draw( xPlayer, yPlayer, widthI, height );
								}
							else if(animTime < frameMove*3)
								{
									if(immortal)
										right[2].draw( xPlayer, yPlayer, widthI, height, imm );
									else										
										right[2].draw( xPlayer, yPlayer, widthI, height );
								}
							else if(animTime < frameMove*4)
								{
									if(immortal)
										right[3].draw( xPlayer, yPlayer, widthI, height, imm );
									else										
										right[3].draw( xPlayer, yPlayer, widthI, height );
								}
							else if(animTime < frameMove*5)
								{
									if(immortal)
										right[4].draw( xPlayer, yPlayer, widthI, height, imm );
									else										
										right[4].draw( xPlayer, yPlayer, widthI, height );
								}
							else if(animTime < frameMove*6)
								{
									if(immortal)
										right[5].draw( xPlayer, yPlayer, widthI, height, imm );
									else										
										right[5].draw( xPlayer, yPlayer, widthI, height );
								}
							else if(animTime < frameMove*7)
								{
									if(immortal)
										right[6].draw( xPlayer, yPlayer, widthI, height, imm );
									else										
										right[6].draw( xPlayer, yPlayer, widthI, height );
								}
							else if(animTime < frameMove*8)
								{
									if(immortal)
										right[7].draw( xPlayer, yPlayer, widthI, height, imm );
									else										
										right[7].draw( xPlayer, yPlayer, widthI, height );									
								}
							else if(animTime <= frameMove*9)
								{
									if(immortal)
										right[8].draw( xPlayer, yPlayer, widthI, height, imm );
									else										
										right[8].draw( xPlayer, yPlayer, widthI, height );
								}
						}
					// il personaggio e' fermo
					else
						{
							if(immortal)
								pgdx.draw( xPlayer, yPlayer, widthI, height, imm );
							else
								pgdx.draw( xPlayer, yPlayer, widthI, height );
						}
				}
			// il personaggio si muove verso sinistra
			else 
				{
					// il personaggio sta saltando
					if(movingJ)
						{
							area = new Rectangle( xPlayer, yPlayer, width, height );
							body = new Rectangle( xPlayer, yPlayer + Global.H/40, width, Global.H/10 );
							head = new Rectangle( xPlayer + Global.W/110, yPlayer, width/2, Global.H/40 );
							
							if(animTime < frameJump)
								{
									if(immortal)
										saltoSx[0].draw( xPlayer, yPlayer, width, height, imm );
									else
										saltoSx[0].draw( xPlayer, yPlayer, width, height );
								}
							else if(animTime < frameJump * 2)
								{
									if(immortal)
										saltoSx[1].draw( xPlayer, yPlayer, width, height, imm );
									else
										saltoSx[1].draw( xPlayer, yPlayer, width, height );
								}
							else if(animTime < frameJump * 3)
								{
									if(immortal)
										saltoSx[2].draw( xPlayer, yPlayer, width, height, imm );
									else
										saltoSx[2].draw( xPlayer, yPlayer, width, height );
								}
							else if(animTime < frameJump * 4)
								{
									if(immortal)
										saltoSx[3].draw( xPlayer, yPlayer, width, height, imm );
									else
										saltoSx[3].draw( xPlayer, yPlayer, width, height );
								}
							else if(animTime < frameJump * 5)
								{
									if(immortal)
										saltoSx[4].draw( xPlayer, yPlayer, width, height, imm );
									else
										saltoSx[4].draw( xPlayer, yPlayer, width, height );
								}
							else if(animTime < frameJump * 6)
								{
									if(immortal)
										saltoSx[5].draw( xPlayer, yPlayer, width, height, imm );
									else
										saltoSx[5].draw( xPlayer, yPlayer, width, height );
								}
							else if(animTime < frameJump * 7)
								{
									if(immortal)
										saltoSx[6].draw( xPlayer, yPlayer, width, height, imm );
									else
										saltoSx[6].draw( xPlayer, yPlayer, width, height );
								}
							else if(animTime < frameJump * 8)
								{
									if(immortal)
										saltoSx[7].draw( xPlayer, yPlayer, width, height, imm );
									else
										saltoSx[7].draw( xPlayer, yPlayer, width, height );
								}
							else
								{
									if(immortal)
										saltoSx[8].draw( xPlayer, yPlayer, width, height, imm );
									else
										saltoSx[8].draw( xPlayer, yPlayer, width, height );
								}
						}
					// il personaggio sta camminando
					else if(movingSx)
						{
							if(animTime < frameMove)
								{
									if(immortal)
										left[0].draw( xPlayer - offset, yPlayer, widthI, height, imm );
									else
										left[0].draw( xPlayer - offset, yPlayer, widthI, height );
								}
							else if(animTime < frameMove*2)
								{
									if(immortal)
										left[1].draw( xPlayer - offset, yPlayer, widthI, height, imm );
									else
										left[1].draw( xPlayer - offset, yPlayer, widthI, height );
								}
							else if(animTime < frameMove*3)
								{
									if(immortal)
										left[2].draw( xPlayer - offset, yPlayer, widthI, height, imm );
									else
										left[2].draw( xPlayer - offset, yPlayer, widthI, height );
								}
							else if(animTime < frameMove*4)
								{
									if(immortal)
										left[3].draw( xPlayer - offset, yPlayer, widthI, height, imm );
									else
										left[3].draw( xPlayer - offset, yPlayer, widthI, height );
								}
							else if(animTime < frameMove*5)
								{
									if(immortal)
										left[4].draw( xPlayer - offset, yPlayer, widthI, height, imm );
									else
										left[4].draw( xPlayer - offset, yPlayer, widthI, height );
								}
							else if(animTime < frameMove*6)
								{
									if(immortal)
										left[5].draw( xPlayer - offset, yPlayer, widthI, height, imm );
									else
										left[5].draw( xPlayer - offset, yPlayer, widthI, height );
								}
							else if(animTime < frameMove*7)
								{
									if(immortal)
										left[6].draw( xPlayer - offset, yPlayer, widthI, height, imm );
									else
										left[6].draw( xPlayer - offset, yPlayer, widthI, height );
								}
							else if(animTime < frameMove*8)
								{
									if(immortal)
										left[7].draw( xPlayer - offset, yPlayer, widthI, height, imm );
									else
										left[7].draw( xPlayer - offset, yPlayer, widthI, height );
								}
							else if(animTime <= frameMove*9)
								{
									if(immortal)
										left[8].draw( xPlayer - offset, yPlayer, widthI, height, imm );
									else
										left[8].draw( xPlayer - offset, yPlayer, widthI, height );
								}
						}
					// il personaggio e' fermo
					else
						{
							if(immortal)
								pgsx.draw( xPlayer - offset, yPlayer, widthI, height, imm );
							else
								pgsx.draw( xPlayer - offset, yPlayer, widthI, height );
						}
				}
		}
	
	public void draw( Graphics g ) throws SlickException
		{			
			if(!invincible)
				drawMoving( g );
			else if(invincible && currentTickInv > 0 && currentTickInv % 2 == 0)
				drawMoving( g );
			
			/*inserisce la trasparenza rosso/verde nella modalita' di editing*/
			if(Start.editGame == 1)
				{
					if(checkInsert)
						if(!insert)
							pgdx.draw( xPlayer, yPlayer, widthI, height, cr);
						else
							pgdx.draw( xPlayer, yPlayer, widthI, height, cg);
				}
			
			for(Shot fuoco: fire)
				if(fuoco.isShooting())
					fuoco.draw();

			int j = 0;
			if(drawLifes)
				{
					for(;j < lifes/2; j++)
						heart.draw( Global.W/40 + widthH*j, Global.H/30, widthH, heightH );
					if(lifes%2 == 1)
						halfHeart.draw( Global.W/40 + widthH*(j++), Global.H/30, widthH, heightH );
					for(;j < Global.lifes/2; j++)
						noHeart.draw( Global.W/40 + widthH*j, Global.H/30, widthH, heightH );
				}
			
			if(drawPoints)
				{
					g.setColor( Color.black );
					g.drawString( "SCORE : " + points, Global.W/40 + widthH*(j+1), Global.H/30);
				}
			
			int offset = (int) Global.Height*10/857;
			for(int i = 0; i < powerUp.size(); i++)
				powerUp.get( i ).getImage().draw( offset + (Global.Width/40)*i + offset*i, maxHeight, Global.H - maxHeight, Global.H - maxHeight );
		}
	
	public void checkPosition( ArrayList<Ostacolo> obstacles )
		{
			for(int i = 0; i < obstacles.size(); i++)
				if(!obstacles.get( i ).getID().startsWith( "player" ))
					if(area.intersects( obstacles.get( i ).component( "latoSu" ) ))
						yPlayer = obstacles.get( i ).getY() - height;
		}

    public void updateStats()
    	{
	    	width = width * Global.ratioW;
			height = height * Global.ratioH;
	    	widthI = widthI * Global.ratioW;
			xPlayer = xPlayer * Global.ratioW;
			yPlayer = yPlayer * Global.ratioH;
			
			maxHeight = (int) (maxHeight * Global.ratioH);
    		area = new Rectangle( xPlayer, yPlayer, width, height );
    	}
    
    public void setArea()
    	{ 
    		area = new Rectangle( xPlayer, yPlayer, width, height );
    		head = new Rectangle( xPlayer + Global.W/110, yPlayer, width/2, Global.H/40 );
    		body = new Rectangle( xPlayer, yPlayer + Global.H/40, width, Global.H/10 );
		}
    
    public void setDrawLifes( boolean val )
    	{ drawLifes = val; }
    
    public boolean getDrawLifes()
    	{ return drawLifes; }
    
    public void setDrawPoints( boolean val )
    	{ drawPoints = val; }
    
    public boolean getDrawPoints()
    	{ return drawPoints; }
	
	public Image getImage()
		{ return pgdx; }
	
	public int getNumPlayer()
		{ return numPlayer; }
	
	public int getShots()
		{ return shots; }
	
	public int getLifes()
		{ return lifes; }
	
	public void setLifes( int val )
		{ lifes = val; }

	public boolean contains( int x, int y )
		{
			if(head.contains( x, y ) || body.contains( x, y ))
				return true;
			
			return false;
		}

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
			if(dir == 0)
				{
					body.setLocation( xPlayer, yPlayer + Global.H/40 );
					head.setLocation( xPlayer + width/2 - Global.W/110, yPlayer );
				}
			else
				{
					body.setLocation( xPlayer, yPlayer + Global.H/40 );
					head.setLocation( xPlayer + Global.W/110, yPlayer );
				}
		}
	
	public void setMaxHeight( double val )
		{ this.maxHeight = (int) val; }

	public Ostacolo clone( GameContainer gc ) {
		try
			{
				Player p = new Player( (int) xPlayer, (int) yPlayer, numPlayer, gc );				
				p.setDrawLifes( getDrawLifes() );				
				return p;
			}
		catch (SlickException e)
			{
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
	
	public void setPoint( int points )
		{ this.points = this.points + points; }
	
	public int getPoints()
		{ return points; }
	
	/**return true = se alemeno 1 sparo e' attivo
	 * return false = se non spara niente*/
	private boolean checkFire()
		{
			for(Shot fuoco: fire)
				if(fuoco.isShooting())
					return true;
		
			return false;
		}
	
	public void update( GameContainer gc, int delta ) throws SlickException
		{
			Input input = gc.getInput();
			int move = Global.W/400;
			
			if(immortal)
				if((gc.getTime() - currentTimeImm) >= timerImm)
					immortal = false;
			
			if(invincible)
				{
					currentTimeInv = currentTimeInv + delta;
					if(currentTimeInv >= timerInv)						
						if(--currentTickInv == 0)
							invincible = false;
						else
							currentTimeInv = 0;
				}
			
			movingDx = false;
			movingSx = false;
			
			/*ZONA CONTROLLO COLLISIONE PERSONAGGIO - SFERE*/
			if(!invincible && !immortal)
				{
					for(int i = 0; i < InGame.ostacoli.size(); i++)
						{
							if(InGame.ostacoli.get( i ).getID().equals( "bolla" ))
								if(area.intersects( InGame.ostacoli.get( i ).component( "" ) ))
									{
										if(--lifes == 0)
											{
												Global.inGame = false;
												drawLifes = false;
												drawPoints = false;
											}
										else
											{
												points = points - 100;
												invincible = true;
												currentTimeInv = 0;
												currentTickInv = tickInv;
											}
									}
						}
				}
			
			/*ZONA CONTROLLO COLLISIONE PERSONAGGIO - POWERUP*/
			for(int i = 0; i < InGame.powerUp.size(); i++)
				{
					if(area.intersects( InGame.powerUp.get( i ).getArea() ))
						{							
							if(InGame.powerUp.get( i ).getID().equals( "coin" ))
								points = points + 500;
							else if(InGame.powerUp.get( i ).getID().equals( "life" ))
								lifes = Math.min( ++lifes, Global.lifes );
							else
								powerUp.add( InGame.powerUp.get( i ) );
							
							InGame.powerUp.remove( InGame.powerUp.get( i ) );
						}
				}
			
			/*la posizione del player un attimo prima di spostarsi*/
			Rectangle previousArea = new Rectangle( area.getX(), area.getY(), width, height );
			
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
			/*ZONA SPARO*/
			if(input.isKeyPressed( Input.KEY_S ) && !isShoting)
	            {
					if(dShot)
						{
							System.out.println( "abbiamo un doppio colpo" );
							for(int i = 0; i < fire.size(); i++)
								{
									fire.get( i ).setXY( (int) (xPlayer + width*i - fire.get( i ).getWidth()/2), (int) (yPlayer + height - 1) );
									fire.get( i ).setShot( true );
					                shots++;
								}
						}
					else if(tShot)
						{
							System.out.println( "abbiamo un triplo colpo" );
							for(int i = 0; i < fire.size(); i++)
								{
									fire.get( i ).setXY( (int) (xPlayer + width/2*i - fire.get( i ).getWidth()/2), (int) (yPlayer + height - 1) );
									fire.get( i ).setShot( true );
					                shots++;
								}
						}
					else if(!fire.get( 0 ).isShooting())
						{
							fire.get( 0 ).setXY( (int) (xPlayer + width/2 - fire.get( 0 ).getWidth()/2), (int) (yPlayer + height - 1) );
							fire.get( 0 ).setShot( true );
			                shots++;
						}
					
					isShoting = true;
	            }
			/*ZONA UTILIZZO POWERUP*/
			if(input.isKeyPressed( Input.KEY_V ) && powerUp.size() > 0)
	            {
					if(powerUp.get( 0 ).getID().equals( "invincible" ))
						{
							immortal = true;
							currentTimeImm = gc.getTime();
							powerUp.remove( 0 );
						}
					else if(!isShoting)
						{
							if(!dShot && !tShot)
								{
									if(powerUp.get( 0 ).getID().startsWith( "d" ))
										{
											fire.add( new Shot( gc ) );
											dShot = true;
											powerUp.remove( 0 );
											
											System.out.println( "fuochi = " + fire.size() );
										}
									else if(powerUp.get( 0 ).getID().startsWith( "t" ))
										{
											fire.add( new Shot( gc ) );
											fire.add( new Shot( gc ) );
											tShot = true;
											powerUp.remove( 0 );
											
											System.out.println( "fuochi = " + fire.size() );
										}
								}
						}
	            }
			/*ZONA UPDATE SPARO/I*/
			for(Shot fuoco: fire)
				{
					if(fuoco.isShooting())
						{
							fuoco.setAnimTime( fuoco.getAnimTime() + 1 );
							if(fuoco.getAnimTime()%2 == 0)
								fuoco.update();
							
							if(fuoco.getArea().getY() <= 0)
								fuoco.setShot( false );
							else
								{
									for(Ostacolo ost: InGame.ostacoli)
										if(fuoco.collision( this, ost, ost.getID(), gc ))
											{
												fuoco.setShot( false );
												break;
											}
								}
						}
				}
			if(isShoting && !checkFire())
				{
					dShot = false;
					tShot = false;

					for(int i = 1; i < fire.size(); i++)
						fire.remove( i );

					isShoting = false;
				}
			
			if(input.isKeyPressed( Input.KEY_SPACE ) && !jump)
				{
					movingJ = true;
					jump = true;
					maxJump = 1;
					tempJump = 60;
				}
			if(maxJump == 1)
				setXY( 0, -move + 0.2f * (40 - tempJump--), "move" );
			else
				{
					jump = true;
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
					setXY( (int) area.getX(), maxHeight - height, "restore" );
				}
			else if(area.getY() < 0)
				{
					maxJump = 0;
					tempJump = 0;
					animTime = animTimeJump/5;
					setXY( (int) area.getX(), 0, "restore" );
				}
		
			/*controlla la collisione con gli ostacoli del gioco (tranne le sfere)*/
			for(Ostacolo ost: InGame.ostacoli)
				{
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
											setXY( (int) area.getX(), (int) (ost.getY() - height), "restore" );
										}										
									else if(area.intersects( ost.component( "latoGiu" ) ) && (previousArea.getY() > ost.getY() + ost.getHeight()))
										{
											maxJump = 0;
											tempJump = 0;
											animTime = animTimeJump/5;
											setXY( (int) area.getX(), (int) (ost.getY() + ost.getHeight()), "restore" );
										}
									else if(area.intersects( ost.component( "latoDx" ) ))
										setXY( (int) (ost.getX() + ost.getWidth()) + 1, (int) area.getY(), "restore" );
									else if(area.intersects( ost.component( "latoSx" ) ))
										setXY( (int) (ost.getX() - width) - 1, (int) area.getY(), "restore" );
								}
						}
				}
			
			/*controlla se sono state distrutte tutte le sfere*/
			boolean check = true;
			for(int i = 0; i < InGame.ostacoli.size(); i++)
				if(InGame.ostacoli.get( i ).getID().equals( "bolla" ))
					check = false;
			
			if(check)
				Global.inGame = false;
			
			/*gestione dell'animazione*/
			if(movingDx || movingSx || jump)
				animTime = (animTime + delta) % animTimeMove;
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
		{ return area; }

	public double getMaxHeight()
		{ return maxHeight; }
	
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

    public void setOrienting( String direction )
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
	
	public void setWidth( float val )
		{ width = val; }
	
	public void setWidthI( float val )
		{ widthI = val; }
	
	public float getWidthI()
		{ return widthI; }
	
	public void setHeight( float val )
		{ height = val; }
}
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

import Utils.Global;
import bubbleMaster.Start;

public class Player extends Ostacolo
{
	private Image pgsx, pgdx;

	/**false = non sto saltando - true = sto saltando*/
	private boolean jump = false;

	private int offset;
	
	private float xPlayer;
	private float yPlayer;
	private int widthI;
	private float width, height;
	
	private Shot fire;
	private boolean shooting;
	
	private Rectangle area, body, head;
	
	private int numPlayer;
	
	private int maxHeight;
	
	private int maxJump = 0, tempJump;
	
	private int dir = 0;
	
	/*altezza e larghezza dei frame dello spostamento laterale*/
	private int widthS, heightS;
	/*larghezza e altezza dei frame del salto*/
	private int widthJ, heightJ;
	
	private boolean insert = false, checkInsert = false;
	
	private Color cg = new Color( 50, 170, 50, 100 ), cr = new Color( 170, 50, 50, 100 );
	
	private Image right[], left[], saltoDx[], saltoSx[];
	
	private float animTimeMove, reachDelta, animTimeJump, reachDeltaJump;
	private int countShot;
	
	private SpriteSheet sheetDx;
	private SpriteSheet sheetSx;	
	private SpriteSheet sheetJumpDx;
	private SpriteSheet sheetJumpSx;
	
	private int wMove, hMove;
	private int wJump, hJump;
	
	/*movimento a destra - movimento a sinistra - movimento in alto - movimento in basso*/
	boolean movingDx, movingSx, movingJ;
	
	// determina il numero di colpi sparati
	private int shots;
	
	// l'immagine delle vite del personaggio
	private Image heart, halfHeart, noHeart;
	private int widthH, heightH;
	
	//le vite del personaggio
	private int lifes;
	
	//determina se il personaggio e' vulnerabile
	private boolean invincible;
	private final int timerInv = 100, tickInv = 2000/timerInv;
	private int currentTimeInv, currentTickInv;
	
	// il punteggio del giocatore
	private int points;
	
	// il valore dei frame di movimento e salto
	float frameMove, frameJump;
	
	// salva il rapporto fra risoluzione di default e quella attuale (valore usato per i salti)
	private float lastRatioH;
	
	public Player( int x, int y, int numPlayer, GameContainer gc ) throws SlickException
		{
			super( "player" + numPlayer );
			
			xPlayer = x;
			yPlayer = y;
			
			fire = new Shot( gc );
			
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
			
			animTimeMove = 504; reachDelta = 0;
			animTimeJump = 396; reachDeltaJump = 0;
			
			countShot = 0;
			
			shots = 0;
			
			heart = new Image( "./data/Image/heart.png" );
			halfHeart = new Image( "./data/Image/halfHeart.png" );
			noHeart = new Image( "./data/Image/noHeart.png" );
			widthH = gc.getWidth()/40; heightH = gc.getHeight()/30;
			
			invincible = false;
			currentTimeInv = 0;
			
			lifes  = Global.lifes;
			
			points = 0;
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
					// il personaggio sta camminando
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
					// il personaggio e' fermo
					else
						pgdx.draw( xPlayer, yPlayer, widthI, height );
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
					// il personaggio sta camminando
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
					// il personaggio e' fermo
					else
						pgsx.draw( xPlayer - offset, yPlayer, widthI, height );
				}

			lastRatioH = 600/Global.H;
		}
	
	public void draw( Graphics g ) throws SlickException
		{
			if(!invincible)
				drawMoving( g );
			else if(invincible && currentTickInv > 0 && currentTickInv % 2 == 0)
				drawMoving( g );
			
			/*inserisce la trasparenza rosso/verde nella modalita' di editing*/
			if(Start.editGame == 1)
				if(checkInsert)
					if(!insert)
						pgdx.draw( xPlayer, yPlayer, widthI, height, cr);
					else
						pgdx.draw( xPlayer, yPlayer, widthI, height, cg);
			
			if(shooting)
				fire.draw();
			
			int j;
			for(j = 0; j < lifes/2; j++)
				heart.draw( Global.W/40 + widthH*j, Global.H/30, widthH, heightH );
			if(lifes%2 == 1)
				halfHeart.draw( Global.W/40 + widthH*(j++), Global.H/30, widthH, heightH );
			for(;j < Global.lifes/2; j++)
				noHeart.draw( Global.W/40 + widthH*j, Global.H/30, widthH, heightH );
			
			g.draw( area );
		}

    public void updateStats()
    	{
	    	width = width * Global.ratioW;
			height = height * Global.ratioH;
			xPlayer = xPlayer * Global.ratioW;
			yPlayer = yPlayer * Global.ratioH;
			
			maxHeight = (int) (maxHeight * Global.ratioH);
			
			lastRatioH = 600/Global.H;
    	}
	
	public void setLifes()
		{ lifes = Global.lifes; }
	
	public Image getImage()
		{ return pgdx; }
	
	public int getNumPlayer()
		{ return numPlayer; }
	
	public int getShots()
		{ return shots; }
	
	public int getLifes()
		{ return lifes; }

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
		try {
			return new Player( (int) xPlayer, (int) yPlayer, numPlayer, gc );
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
	
	public void setPoint( int points )
		{ this.points = this.points + points; }
	
	public int getPoints()
		{ return points; }
	
	public void update( GameContainer gc, int delta ) throws SlickException
		{
			Input input = gc.getInput();
			int move = Global.W/400;
			
			if(invincible)
				{
					currentTimeInv = currentTimeInv + delta;
					if(currentTimeInv >= timerInv)
						{
							if(--currentTickInv == 0)
								invincible = false;
							else
								currentTimeInv = 0;
						}
				}
			
			movingDx = false;
			movingSx = false;
			
			/*ZONA CONTROLLO COLLISIONE PERSONAGGIO - SFERE*/
			if(!invincible)
				for(int i = 0; i < InGame.ostacoli.size(); i++)
					if(InGame.ostacoli.get( i ).getID().equals( "bolla" ))
						if(area.intersects( InGame.ostacoli.get( i ).component( "" ) ))
							{
								if(--lifes == 0)
									Global.inGame = false;
								else
									{
										points = points - 100;
										invincible = true;
										currentTimeInv = 0;
										currentTickInv = tickInv;
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
			if(input.isKeyPressed( Input.KEY_S ) && !shooting && Start.startGame == 1)
	            {
	                shooting = true;
	                shots++;
	                fire.setXY( (int) (xPlayer + width/2 - fire.getWidth()/2), (int) (yPlayer + height - 1) );
	            }
			if(shooting)
				{
					if(++countShot % 2 == 0)
						fire.update();
					
					for(int i = 0; i < InGame.ostacoli.size(); i++)
						if(fire.collision( this, InGame.ostacoli.get( i ), InGame.ostacoli.get( i ).getID(), gc ))
							shooting = false;
				}
			
			if(input.isKeyPressed( Input.KEY_SPACE ) && !jump)
				{
					movingJ = true;
					jump = true;
					maxJump = 1;
					tempJump = 60;
				}
			if(maxJump == 1)
				setXY( 0, (-move + 0.2f * (40 - tempJump--) * lastRatioH), "move" );
			else
				{
					movingJ = true;
					setXY( 0, (move + 0.1f * tempJump++) * lastRatioH, "move" );
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
											System.out.println( "collidere collide" );
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
			
			/*controlla se sono state distrutte tute le sfere*/
			boolean check = true;
			for(int i = 0; i < InGame.ostacoli.size(); i++)
				if(InGame.ostacoli.get( i ).getID().equals( "bolla" ))
					check = false;
			
			if(check)
				Global.inGame = false;
			
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
}
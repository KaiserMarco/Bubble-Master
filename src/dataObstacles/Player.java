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
import dataPowers.Ammo;
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

	private static final String SINISTRA = "Sx", DESTRA = "Dx";
	private static final String RESTORE = "restore", MOVE = "move";
	
	// la direzione di movimento del personaggio
	private String dir;
	
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
	
	// il colore del personaggio
	private Color color;
	
	/*movimento a destra/sinistra - salto*/
	boolean moving, movingJ;
	
	// l'immagine delle vite del personaggio
	private Image heart, halfHeart, noHeart;
	private int widthH, heightH;
	
	//le vite/i punti del personaggio
	private int lifes, points;
	// determina se disegnare o meno le vite/i punti del personaggio
	private boolean drawLifes, drawPoints;
	
	//determina se il personaggio e' vulnerabile/mortale
	private boolean invincible, immortal;
	private final int timerInv = 100, tickInv = 2000/timerInv, timerShot = 4000;
	private final int timerImm = 2000;
	private long currentTimeInv, currentTickInv, currentTimeShot;
	private long currentTimeImm;
	// la differenza fra il tempo corrente e quello preso
	private float cd = 0;
	private float tickCd;	
	private int index = 0;
	
	// il numero di colpi andati a segno
	private int hits;
	
	// il valore dei frame di movimento e salto
	float frameMove, frameJump;
	
	// i poteri posseduti dal personaggio
	private ArrayList<PowerUp> powerUp;

	// determina se il personaggio sta sparando
	boolean isShooting = false;
	
	/*numero di proiettili sparabili*/
	private final int maxAmmo = 2;
	private int currAmmo;
	
	private Rectangle coolDown;
	
	private float space = Global.Height*10/857;
	
	private	int indice;
	
	private static final String LIFE = "life", COIN = "coin", INVINCIBLE = "invincible", AMMO = "ammo";
	
	private Color col;
	
	private boolean selectable;
	
	public Player( float x, float y, int numPlayer, GameContainer gc, Color color ) throws SlickException
		{
			super( "player" );
			
			this.color = color;
			col = new Color( color.getRed(), color.getGreen(), color.getBlue(), 220 );
			
			xPlayer = x;
			yPlayer = y;
			
			fire = new ArrayList<Shot>();
			fire.add( new Shot( gc ) );
			
			offset = Global.Height/40;
			widthI = Global.Width*10/133;
			
			width = widthI - offset;
			height = Global.Height*100/857;
			
			this.numPlayer = numPlayer;	
			
			right = new Image[9];
			left = new Image[9];			
			saltoDx = new Image[9];
			saltoSx = new Image[9];
			
			// questi valori devono restare assoluti (altrimenti l'animazione, giustamente, smatta)
			widthS = 36; heightS = 41;
			widthJ = 29; heightJ = 48;
			
			wMove = 32; hMove = 41;
			wJump = 261; hJump = 48;

			String colour;
			if(color.equals( Color.red ))
				colour = "red";
			else if(color.equals( Color.blue ))
				colour = "blue";
			else if(color.equals( Color.yellow ))
				colour = "yellow";
			else
				colour = "green";

			pgdx = new Image( "./data/Image/pgdx" + colour + ".png" );
			pgsx = new Image( "./data/Image/pgsx" + colour + ".png" );
			sheetDx = new SpriteSheet( new Image( "./data/Image/animdx" + colour + ".png" ), wMove, hMove );
			sheetSx = new SpriteSheet( new Image( "./data/Image/animsx" + colour + ".png" ), wMove, hMove );
			sheetJumpDx = new SpriteSheet( new Image( "./data/Image/jumpDx" + colour + ".png" ), wJump, hJump );
			sheetJumpSx = new SpriteSheet( new Image( "./data/Image/jumpSx" + colour + ".png" ), wJump, hJump );
			
			area = new Rectangle( xPlayer, yPlayer, width, height );
			body = new Rectangle( xPlayer, yPlayer + Global.Height/40, width, Global.Height/10 );
			head = new Rectangle( xPlayer + width/2 - Global.Width/600, yPlayer, width/2, Global.Height/40 );
			
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
		
			frameMove = animTimeMove/right.length;
			frameJump = animTimeJump/saltoDx.length;
			
			shots = 0;
			
			heart = new Image( "./data/Image/heart" + colour + ".png" );
			halfHeart = new Image( "./data/Image/halfHeart" + colour + ".png" );
			noHeart = new Image( "./data/Image/noHeart.png" );
			widthH = gc.getWidth()/40; heightH = gc.getHeight()/30;
			
			invincible = false;
			currentTimeInv = 0;
			currentTimeImm = 0;
			currentTimeShot = 0;
			
			immortal = false;
			
			lifes  = Global.lifes;
			
			drawLifes = false;
			drawPoints = false;
			
			points = 0;
			
			powerUp = new ArrayList<PowerUp>();
			powerUp.add( new Ammo( 0, 0, gc.getHeight()/40, maxHeight ) );

			coolDown = new Rectangle( 2*widthH + Global.Width/40 + Global.Width*10/42*(numPlayer-1), maxHeight, Global.Height - maxHeight, Global.Height - maxHeight );
			
			currAmmo = 0;
			hits = 0;
			
			dir = DESTRA;
			
			selectable = true;
		}
	
	public void drawMoving( Graphics g )
		{
			if(immortal)
				imm = new Color( 28, 57, 187, 200 );
			else
				imm = new Color( 255, 255, 255, 255 );

			// il personaggio sta saltando
			if(movingJ)
				{
					indice = Math.min( (int) (animTime/frameJump), 8 );
					if(dir == DESTRA)
						{
							area = new Rectangle( xPlayer, yPlayer, width, height );
							body = new Rectangle( xPlayer, yPlayer + Global.Height/40, width, Global.Height/12 );
							head = new Rectangle( xPlayer + width/2 - Global.Width/110, yPlayer, width/2, Global.Height/40 );
							
							saltoDx[indice].draw( xPlayer, yPlayer, width, height, imm );
						}
					else
						{
							area = new Rectangle( xPlayer, yPlayer, width, height );
							body = new Rectangle( xPlayer, yPlayer + Global.Height/40, width, Global.Height/10 );
							head = new Rectangle( xPlayer + Global.Width/110, yPlayer, width/2, Global.Height/40 );

							saltoSx[indice].draw( xPlayer, yPlayer, width, height, imm );
						}
				}
			// il personaggio si sta solo muovendo
			else if(moving)
				{
					indice = Math.min( (int) (animTime/frameMove), 8 );
					if(dir == DESTRA)
						right[indice].draw( xPlayer, yPlayer, widthI, height, imm );
					else
						left[indice].draw( xPlayer - offset, yPlayer, widthI, height, imm );
				}
			// il personaggio e' fermo
			else if(dir == DESTRA)
				pgdx.draw( xPlayer, yPlayer, widthI, height, imm );
			else
				pgsx.draw( xPlayer - offset, yPlayer, widthI, height, imm );
		}
	
	public void draw( Graphics g ) throws SlickException
		{
			/* disegna il player durante la scelta livello e durante l'editing */
			if(Start.editGame == 1 || Start.chooseLevel == 1)
				{
					pgdx.draw( xPlayer, yPlayer, widthI, height );
					Color col = null;
					if(!selectable)
						col = Color.black;
					if(checkInsert)
						if(!insert)
							col = cr;
						else
							col = cg;
					
					if(col != null)
						pgdx.draw( xPlayer, yPlayer, widthI, height, col );
				}
			/* disegna il player durante la durante la partita */
			else if(Start.startGame == 1)
				{
					if(!invincible || (invincible && currentTickInv > 0 && currentTickInv % 2 == 0))
						drawMoving( g );
					
					for(Shot fuoco: fire)
						if(fuoco.isShooting())
							fuoco.draw();
					
					float pos = Global.Width/40 + Global.Width*10/42*(numPlayer-1);
					g.setColor( Color.black );
					if(currAmmo > 0)
						{
							Rectangle zone = new Rectangle( pos + 2*widthH, maxHeight, Global.Height - maxHeight, Global.Height - maxHeight );
							g.fill( zone );
							g.setColor( col );
							powerUp.get( 0 ).getImage().draw( zone.getX(), maxHeight, Global.Height - maxHeight, Global.Height - maxHeight );
							g.drawString( "X " + currAmmo, zone.getMaxX() + space, maxHeight );
							coolDown.setY( coolDown.getY() + tickCd );
							coolDown.setHeight( coolDown.getHeight() - tickCd );
							g.fill( coolDown );
							index--;
						}
					if(drawLifes)
						{
							int j = 0;
							for(;j < lifes/2; j++)
								{
									heart.draw( pos, Global.Height/30, widthH, heightH );
									pos = pos + widthH;
								}
							if(lifes%2 == 1)
								{
									j++;
									halfHeart.draw( pos, Global.Height/30, widthH, heightH );
									pos = pos + widthH;
								}
							for(;j < Global.lifes/2; j++)
								{
									noHeart.draw( pos, Global.Height/30, widthH, heightH );
									pos = pos + widthH;
								}
						}
		
					g.setColor( col );
					if(drawPoints)
						g.drawString( "SCORE : " + points, pos + Global.Width/80, Global.Height/30);
				}
		}
	
	public void setSelectable( boolean val )
		{ selectable = val; }
	
	public boolean isSelectable()
		{ return selectable; }
	
	public void setNumPlayer( int val )
		{ numPlayer = val; }
	
	public void checkPosition( ArrayList<Ostacolo> obstacles )
		{
			for(Ostacolo obs: obstacles)
				if(!obs.getID().startsWith( Global.PLAYER ))
					if(area.intersects( obs.component( Global.LATOSU ) ))
						yPlayer = obs.getY() - height;
		}
    
    public void setArea( GameContainer gc )
    	{ 
    		area = new Rectangle( xPlayer, yPlayer, width, height );
    		head = new Rectangle( xPlayer + Global.Width/110, yPlayer, width/2, Global.Height/40 );
    		body = new Rectangle( xPlayer, yPlayer + Global.Height/40, width, Global.Height/10 );
		}
    
    public String getColor()
    	{
	    	if(color.equals( Color.red ))
				return "red";
			else if(color.equals( Color.blue ))
				return "blue";
			else if(color.equals( Color.yellow ))
				return "yellow";
			else
				return "green";
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
			if(function.equals( MOVE ))
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
			if(dir == DESTRA)
				{
					body.setLocation( xPlayer, yPlayer + Global.Height/40 );
					head.setLocation( xPlayer + width/2 - Global.Width/110, yPlayer );
				}
			else
				{
					body.setLocation( xPlayer, yPlayer + Global.Height/40 );
					head.setLocation( xPlayer + Global.Width/110, yPlayer );
				}
		}
	
	public void setMaxHeight( double val )
		{
			maxHeight = (int) val;
			coolDown.setY( maxHeight );
			coolDown.setWidth( Global.Height - maxHeight );
			coolDown.setHeight( Global.Height - maxHeight );
		}

	public Ostacolo clone( GameContainer gc ) {
		try
			{
				Player p = new Player( xPlayer, yPlayer, numPlayer, gc, color );				
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
	
	public int getHits()
		{ return hits; }
	
	public void update( GameContainer gc, int delta, Input input ) throws SlickException
		{
			float move = Global.Width/400;
			
			if(!isShooting && currAmmo == 0)
				if(fire.size() > 1)
					for(int i = fire.size() - 1; i > 0; i--)
						fire.remove( i );
			
			if(currAmmo > 0)
				{
					cd = gc.getTime() - currentTimeShot;
					if(cd >= timerShot)
						{
							cd = 0;
							currAmmo = 0;
							currentTimeShot = 0;
						}
				}
			
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
			
			moving = false;
			
			/*ZONA CONTROLLO COLLISIONE PERSONAGGIO - SFERE*/
			if(!invincible && !immortal)
				{
					for(int i = 0; i < InGame.ostacoli.size(); i++)
						{
							if(InGame.ostacoli.get( i ).getID().equals( Global.BOLLA ))
								if(area.intersects( InGame.ostacoli.get( i ).component( Global.RECT ) ))
									{
										if(--lifes == 0)
											{
												drawLifes = false;
												drawPoints = false;
												Global.inGame = false;
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
							if(InGame.powerUp.get( i ).getID().equals( COIN ))
								points = points + 500;
							else if(InGame.powerUp.get( i ).getID().equals( LIFE ))
								lifes = Math.min( ++lifes, Global.lifes );
							else if(InGame.powerUp.get( i ).getID().equals( INVINCIBLE ))
								{
									immortal = true;
									currentTimeImm = gc.getTime();
								}
							else if(InGame.powerUp.get( i ).getID().equals( AMMO ))
								{
									if(currAmmo < maxAmmo)
										{
											currAmmo++;
											fire.add( new Shot( gc ) );
											currentTimeShot = gc.getTime();
											coolDown.setY( maxHeight );
											coolDown.setHeight( Global.Height - maxHeight );
											index = 270*timerShot/3000;
											tickCd = coolDown.getHeight()/index;
										}
								}							
							InGame.powerUp.remove( InGame.powerUp.get( i ) );
						}
				}
			
			/*la posizione del player un attimo prima di spostarsi*/
			Rectangle previousArea = new Rectangle( area.getX(), area.getY(), width, height );
			
			/*ZONA SPOSTAMENTI-SALTI*/			
			if(input.isKeyDown( Global.mapButtons.get( numPlayer-1 ).get( DESTRA ) ))
				{
					moving = true;
					dir = DESTRA;
					setXY( move, 0, MOVE );
				}
			else if(input.isKeyDown( Global.mapButtons.get( numPlayer-1 ).get( SINISTRA ) ))
				{
					moving = true;
					dir = SINISTRA;
					setXY( -move, 0, MOVE );
				}
			/*ZONA SPARO*/
			if(input.isKeyDown( Global.mapButtons.get( numPlayer-1 ).get( Global.SPARO ) ) && !isShooting)
	            {					
					float space = widthI/(fire.size() + 1);

					for(int i = 0; i < fire.size(); i++)
						{
							fire.get( i ).setXY( (int) (xPlayer + space*(i + 1) - fire.get( i ).getWidth()/2), (int) (yPlayer + height - 1) );
							fire.get( i ).setShot( true );
			                shots++;
						}
					
					isShooting = true;
	            }
			/*ZONA SALTO*/
			if(input.isKeyDown( Global.mapButtons.get( numPlayer-1 ).get( Global.SALTO ) ) && !jump)
				{
					movingJ = true;
					jump = true;
					maxJump = 1;
					tempJump = 60;
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
										if(!ost.getID().equals( Global.TUBO ))
											if(fuoco.collision( this, ost, ost.getID(), gc ))
												{
													if(fuoco.checkHit())
														hits++;
													fuoco.setShot( false );
													break;
												}
								}
						}
				}
			if(isShooting && !checkFire())
				{ isShooting = false; }
			
			if(maxJump == 1)
				setXY( 0, -move + 0.2f*(40 - tempJump--), MOVE );
			else
				{
					jump = true;
					movingJ = true;
					setXY( 0, move + 0.1f*tempJump++, MOVE );
				}
			
			/*controlla se non sono stati superati i limiti della schermata*/
			if(area.getX() + width > gc.getWidth())
				setXY( gc.getWidth() - width, (int) area.getY(), RESTORE );
			else if(area.getX() < 0)
				setXY( 0, (int) area.getY(), RESTORE );
			if(area.getY() + height > maxHeight)
				{
					maxJump = 0;
					tempJump = 0;
					jump = false;
					movingJ = false;
					setXY( (int) area.getX(), maxHeight - height, RESTORE );
				}
			else if(area.getY() < 0)
				{
					maxJump = 0;
					tempJump = 0;
					animTime = animTimeJump/5;
					setXY( (int) area.getX(), 0, RESTORE );
				}
		
			/*controlla la collisione con gli ostacoli del gioco (tranne le sfere)*/
			for(Ostacolo ost: InGame.ostacoli)
				{
					if(!ost.getID().equals( Global.BOLLA ) && !ost.getID().equals( Global.TUBO ))
						{
							if(area.intersects( ost.component( Global.RECT ) ))
								{
									if(area.intersects( ost.component( Global.LATOSU ) ) && (previousArea.getY() + height <= ost.getY()))
										{
											maxJump = 0;
											tempJump = 0;
											jump = false;
											movingJ = false;
											setXY( (int) area.getX(), (int) (ost.getY() - height), RESTORE );
										}										
									else if(area.intersects( ost.component( Global.LATOGIU ) ) && (previousArea.getY() > ost.getY() + ost.getHeight()))
										{
											maxJump = 0;
											tempJump = 0;
											animTime = animTimeJump/5;
											setXY( (int) area.getX(), (int) (ost.getY() + ost.getHeight()), RESTORE );
										}
									else if(area.intersects( ost.component( Global.LATODX ) ))
										setXY( (int) (ost.getX() + ost.getWidth()) + 1, (int) area.getY(), RESTORE );
									else if(area.intersects( ost.component( Global.LATOSX ) ))
										setXY( (int) (ost.getX() - width) - 1, (int) area.getY(), RESTORE );
								}
						}
				}
			
			/*controlla se sono state distrutte tutte le sfere*/
			boolean check = true;
			for(Ostacolo ost: InGame.ostacoli)
				if(ost.getID().equals( Global.BOLLA ))
					check = false;
			
			if(check)
				Global.inGame = false;
			
			/*gestione dell'animazione*/
			if(moving || jump)
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

	public double getMaxWidth()
		{ return 0; }

	public boolean getCollide()
		{ return false; }

	public void setCollide( boolean val )
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
		{ width = val; }
	
	public void setWidthI( float val )
		{ widthI = val; }
	
	public float getWidthI()
		{ return widthI; }
	
	public void setHeight( float val )
		{ height = val; }

	public void update(GameContainer gc, int delta) throws SlickException 
		{}

	@Override
	public float getRotate() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setRotate(float val) {
		// TODO Auto-generated method stub
		
	}
}
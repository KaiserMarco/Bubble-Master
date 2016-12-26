package dataObstacles;

import interfaces.InGame;

import java.util.ArrayList;
import java.util.Map;

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
	
	/** vettore dei colpi */
	private ArrayList <Shot> fire;
	
	// determina il numero di colpi sparati
	private int shots;
	
	private Rectangle area, body, head;
	
	private int numPlayer;
	
	private float maxHeight = Global.maxHeight;
	
	private int maxJump = 0, tempJump;

	private static final String SINISTRA = "Sx", DESTRA = "Dx";
	private static final String RESTORE = "restore", MOVE = "move";
	
	// la direzione di movimento del personaggio
	private String dir;
	
	/*altezza e larghezza dei frame dello spostamento laterale*/
	private int widthS, heightS;
	/*larghezza e altezza dei frame del salto*/
	private int widthJ, heightJ;
	
	private boolean insert = true, checkInsert = false;
	
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
	private boolean moving, movingJ;
	
	// l'immagine delle vite del personaggio
	private Image heart, halfHeart, noHeart;
	private int widthH, heightH;
	
	//le vite/i punti del personaggio
	private int lifes, points;
	
	//determina se il personaggio e' vulnerabile/mortale
	private boolean invincible, immortal;
	private final int timerInv = 100, tickInv = 2000/timerInv, timerShot = 4000;
	private final int timerImm = 2000;
	private long currentTimeInv, currentTickInv, currentTimeShot;
	private long currentTimeImm;
	// la differenza fra il tempo corrente e quello preso
	private float cd = 0;
	private float tickCd;	
	private int pos, index = 0;
	
	// il numero di colpi andati a segno
	private int hits;
	
	// il valore dei frame di movimento e salto
	private float frameMove, frameJump;
	
	// i poteri posseduti dal personaggio
	private ArrayList<PowerUp> powerUp;

	// determina se il personaggio sta sparando
	private boolean isShooting = false;
	
	/*numero di proiettili sparabili*/
	private final int maxAmmo = 2;
	private int currAmmo;
	
	private Rectangle coolDown;
	
	private float space = Global.Height*10/857;
	
	private	int indice;
	
	private Color col;
	
	private boolean selectable;
	
	private final float move = Global.Width/400;
	
	private float spazio, posX, posY = Global.Height/30;

	/*la posizione del player un attimo prima di spostarsi*/ 
	private Rectangle prevArea;
	
	private Map<String, Integer> keyButtons;
	
	private boolean isMoved = false;

	// determina se e' stata colpita o meno una sfera
	private boolean hit;
	
	private int j;
	
	private ArrayList<Bubble> sfere;
	private ArrayList<Ostacolo> ostacoli;
	
	private ArrayList<Image> vite;
	private int indexLastLife;
	
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
			widthH = Global.Width/51; heightH = Global.Height/33;
			
			invincible = false;
			currentTimeInv = 0;
			currentTimeImm = 0;
			currentTimeShot = 0;
			
			immortal = false;
			
			lifes  = Global.lifes;
			
			points = 0;
			
			powerUp = new ArrayList<PowerUp>();
			powerUp.add( new Ammo( 0, 0, maxHeight ) );

			coolDown = new Rectangle( 2*widthH + Global.Width/40 + Global.Width*10/42*(numPlayer-1), maxHeight, Global.Height - maxHeight, Global.Height - maxHeight );
			
			currAmmo = 0;
			hits = 0;
			
			dir = DESTRA;
			
			selectable = true;
			
			prevArea = new Rectangle( area.getX(), area.getY(), width, height );
			
			vite = new ArrayList<Image>();
		}
	
	public void drawMoving( Graphics g )
		{
			// il personaggio NON si sta muovendo
			if(!isMoved && !jump)
				{
					if(dir == DESTRA)
						{
							pgdx.draw( xPlayer, yPlayer, widthI, height );
							pgdx.draw( xPlayer, yPlayer, widthI, height, imm );
						}
					else
						{
							pgsx.draw( xPlayer - offset, yPlayer, widthI, height );
							pgsx.draw( xPlayer - offset, yPlayer, widthI, height, imm );
						}
				}
			// il personaggio sta saltando
			else if(movingJ)
				{
					indice = Math.min( (int) (animTime/frameJump), 8 );
					if(dir == DESTRA)
						{
							area = new Rectangle( xPlayer, yPlayer, width, height );
							body = new Rectangle( xPlayer, yPlayer + Global.Height/40, width, Global.Height/12 );
							head = new Rectangle( xPlayer + width/2 - Global.Width/110, yPlayer, width/2, Global.Height/40 );
							
							saltoDx[indice].draw( xPlayer, yPlayer, width, height );
							saltoDx[indice].draw( xPlayer, yPlayer, width, height, imm );
						}
					else
						{
							area = new Rectangle( xPlayer, yPlayer, width, height );
							body = new Rectangle( xPlayer, yPlayer + Global.Height/40, width, Global.Height/10 );
							head = new Rectangle( xPlayer + Global.Width/110, yPlayer, width/2, Global.Height/40 );

							saltoSx[indice].draw( xPlayer, yPlayer, width, height );
							saltoSx[indice].draw( xPlayer, yPlayer, width, height, imm );
						}
				}
			// il personaggio si sta solo muovendo
			else if(moving)
				{
					indice = Math.min( (int) (animTime/frameMove), 8 );
					if(dir == DESTRA)
						{
							right[indice].draw( xPlayer, yPlayer, widthI, height );
							right[indice].draw( xPlayer, yPlayer, widthI, height, imm );
						}
					else
						{
							left[indice].draw( xPlayer - offset, yPlayer, widthI, height );
							left[indice].draw( xPlayer - offset, yPlayer, widthI, height, imm );
						}
				}
		}

	public void setMoving( boolean val )
		{ moving = val; }
	
	/** disegna il player durante e dopo la partita */
	public void drawPlay( Graphics g ) throws SlickException
		{
			if(immortal)
				imm = new Color( 28, 57, 187, 200 );
			else
				imm = new Color( 255, 255, 255, 255 );
			
			if(!invincible || (invincible && currentTickInv > 0 && currentTickInv % 2 == 0))
				drawMoving( g );
			
			for(Shot fuoco: fire)
				if(fuoco.isShooting())
					fuoco.draw();
			
			posX = Global.Width/40 + Global.Width/4*(numPlayer-1);
			g.setColor( Color.black );
			if(currAmmo > 0)
				{
					Rectangle zone = new Rectangle( posX + 2*widthH, maxHeight, Global.Height - maxHeight, Global.Height - maxHeight );
					g.fill( zone );
					g.setColor( col );
					powerUp.get( 0 ).getImage().draw( zone.getX(), maxHeight, Global.Height - maxHeight, Global.Height - maxHeight );
					g.drawString( "X " + currAmmo, zone.getMaxX() + space, maxHeight );
					coolDown.setLocation( zone.getX(), coolDown.getY() + tickCd );
					coolDown.setHeight( coolDown.getHeight() - tickCd );
					g.fill( coolDown );
					index--;
				}
				
			for(Image life: vite)
				{ 
					life.draw( posX, posY, widthH, heightH );
					posX = posX + widthH;
				}
	
			g.setColor( col );
			g.drawString( "SCORE : " + points, posX + Global.Width/100, posY );
		}

	/** disegna il player durante la scelta livello e durante l'editing */
	public void draw( Graphics g ) throws SlickException
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
	
	public Image getImage()
		{ return pgdx; }
	
	public int getNumPlayer()
		{ return numPlayer; }
	
	public int getShots()
		{ return shots; }
	
	/** aggiorna le vite del giocatore
	 * @return TRUE - se il personaggio ha finito le vite, FALSE - altrimenti */
	public boolean updateLifes( int sum )
		{
			lifes = Math.min( lifes + sum, Global.lifes );
			if(lifes == 0)
				{
					Global.inGame = false;
					return true;
				}
			
			if(sum < 0)
				{
					if(lifes % 2 == 0)
						{
							vite.remove( indexLastLife );
							vite.add( indexLastLife, noHeart );
							indexLastLife--;
						}
					else
						{
							vite.remove( indexLastLife );
							vite.add( indexLastLife, halfHeart );
						}
				}
			else if(lifes % 2 == 0)
				{
					vite.remove( indexLastLife );
					vite.add( indexLastLife, heart );
				}
			else
				{
					indexLastLife++;
					vite.remove( indexLastLife );
					vite.add( indexLastLife, halfHeart );
				}
			
			return false;
		}
	
	/** setta le vite del giocatore */
	public void setLifes( int val )
		{
			if(val % 2 == 0)
				indexLastLife = val/2 - 1;
			else
				indexLastLife = val/2;
			
			lifes = val;
			
			for(j = 0; j < lifes/2; j++)
				vite.add( heart );
			if(lifes%2 == 1)
				{
					j++;
					vite.add( halfHeart );
				}
			for(;j < lifes/2; j++)
				vite.add( noHeart );
		}
	
	public int getLifes()
		{ return lifes; }

	public boolean contains( int x, int y )
		{
			if(head.contains( x, y ) || body.contains( x, y ))
				return true;
			
			return false;
		}
	
	/** setta la posizione delle aree body e head */
	private void setBodyHead()
		{
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

	public void setXY( float x, float y, String function ) 
		{
			if(function.equals( MOVE ))
				{
					xPlayer = xPlayer + x;
					yPlayer = yPlayer + y;
				}
			else if(function.equals( RESTORE ))
				{
					xPlayer = x;
					yPlayer = y;
				}
			
			area.setLocation( xPlayer, yPlayer );
			setBodyHead();
		}
	
	public void setY( float y )
		{
			yPlayer = y;
			area.setLocation( xPlayer, yPlayer );
			setBodyHead();
		}
	
	public void setMaxHeight( float val )
		{
			coolDown.setY( maxHeight );
			coolDown.setWidth( Global.Height - maxHeight );
			coolDown.setHeight( Global.Height - maxHeight );
		}

	public Ostacolo clone( GameContainer gc )
		{
			try { return new Player( xPlayer, yPlayer, numPlayer, gc, color ); }
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

	public void setInsert( boolean insert, boolean change )
		{
			checkInsert = change;
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

	/** setta i tasti del player */
	public void setKeyButtons()
		{ keyButtons = Global.mapButtons.get( numPlayer - 1 ); }
	
	/** controlla lo stato delle munizioni */
	private void checkCurrAmmo( GameContainer gc )
		{
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
			
			if(!isShooting && currAmmo == 0)
				if(fire.size() > 1)
					for(int i = fire.size() - 1; i > 0; i--)
						fire.remove( i );
		}
	
	public void setImmortality( GameContainer gc )
		{
			immortal = true;
			invincible = false;
			currentTimeImm = gc.getTime();
		}
	
	public void setNewAmmo( GameContainer gc ) throws SlickException
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
	
	/** ritorna lo stato di invincibilita' del personaggio */
	public boolean isInvincible()
		{ return invincible; }
	
	public void setSpheres( ArrayList<Bubble> bolle )
		{ sfere = bolle; }
	
	public void setOstacoli( ArrayList<Ostacolo> ostacoli )
		{ this.ostacoli = ostacoli; }
	
	public void update( GameContainer gc, int delta, Input input ) throws SlickException
		{
			moving = false;
			
			// controlla lo stato delle munizioni
			checkCurrAmmo( gc );
			
			/*ZONA SPOSTAMENTI DESTRA-SINISTRA*/			
			if(input.isKeyDown( keyButtons.get( DESTRA ) ))
				{
					moving = true;
					dir = DESTRA;
					setXY( move, 0, MOVE );
				}
			else if(input.isKeyDown( keyButtons.get( SINISTRA ) ))
				{
					moving = true;
					dir = SINISTRA;
					setXY( -move, 0, MOVE );
				}
			/*ZONA SALTO*/
			if(input.isKeyDown( keyButtons.get( Global.SALTO ) ) && !jump)
				{
					movingJ = true;
					jump = true;
					maxJump = 1;
					tempJump = 60;
				}
			/*ZONA SPARO*/
			if(input.isKeyPressed( keyButtons.get( Global.SPARO ) ) && !isShooting)
	            {					
					spazio = widthI/(currAmmo + 2);

					for(int i = 0; i < currAmmo + 1; i++)
						{
							fire.get( i ).setXY( xPlayer + spazio*(i + 1) - fire.get( i ).getWidth()/2, getMaxY() - 1 );
							fire.get( i ).setShot( true );
			                shots++;
						}
					
					isShooting = true;
	            }

			/*controllo del salto/planata*/
			if(maxJump > 0)
				setXY( 0, -move + 0.2f*(40 - tempJump--), MOVE );
			else
				{
					movingJ = true;
					setXY( 0, move + Math.abs( 0.1f * tempJump-- ), MOVE );
				}
			
			if(immortal && gc.getTime() - currentTimeImm >= timerImm)
				immortal = false;
			else if(invincible)
				{
					currentTimeInv = currentTimeInv + delta;
					if(currentTimeInv >= timerInv)						
						if(--currentTickInv == 0)
							invincible = false;
						else
							currentTimeInv = 0;
				}
			
			/*controlla se sono stati superati i limiti della schermata*/
			if(area.getMaxY() > Global.maxHeight)
				{
					maxJump = 0;
					jump = false;
					movingJ = false;
					setXY( area.getX(), maxHeight - height, RESTORE );
				}
			if(moving || jump)
				{
					if(area.getMaxX() > Global.Width)
						setXY( Global.Width - width, area.getY(), RESTORE );
					else if(area.getX() < 0)
						setXY( 0, area.getY(), RESTORE );
					if(area.getY() < 0)
						{
							maxJump = 0;
							tempJump = 0;
							animTime = animTimeJump/5;
							setXY( area.getX(), 0, RESTORE );
						}
				}
			
			/*ZONA CONTROLLO COLLISIONE PERSONAGGIO - OSTACOLI*/
			for(Ostacolo ost: ostacoli)
				{
					if(!ost.getID().equals( Global.TUBO ))
						{
							if(area.intersects( ost.component( Global.LATOSU ) ) && prevArea.getMaxY() <= ost.getY())
								{
									maxJump = 0;
									tempJump = 0;
									jump = false;
									movingJ = false;
									setXY( area.getX(), ost.getY() - height, RESTORE );
								}										
							else if(area.intersects( ost.component( Global.LATOGIU ) ) && prevArea.getY() > ost.getMaxY())
								{
									maxJump = 0;
									tempJump = 0;
									animTime = animTimeJump/5;
									setXY( area.getX(), ost.getMaxY(), RESTORE );
								}
							else if(area.intersects( ost.component( Global.LATODX ) ))
								setXY( ost.getMaxX(), area.getY(), RESTORE );
							else if(area.intersects( ost.component( Global.LATOSX ) ))
								setXY( ost.getX() - width, area.getY(), RESTORE );
						}
				}
			
			/*ZONA CONTROLLO COLLISIONE PERSONAGGIO-SFERE*/
			if(!immortal && !invincible)
				{
					for(Bubble sfera: sfere)
						{
							if(area.intersects( sfera.getArea() ))
								{
									if(updateLifes( -1 ))
										return;
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
			for(int i = InGame.powerUp.size() - 1; i >= 0; i--)
				{
					PowerUp powerUp = InGame.powerUp.get( i );
					if(area.intersects( powerUp.getArea() ))
						{
							powerUp.effect( this, gc );
							InGame.powerUp.remove( powerUp );
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
								{
									fuoco.setShot( false );
									continue;
								}
								
							hit = false;
							for(Bubble sfera: sfere)
								{
									if(fuoco.collisionSphere( this, sfere, sfera, gc ) )
										{
											hits++;
											fuoco.setShot( false );
											if(sfere.size() == 0)
												{
													Global.inGame = false;
													return;
												}
											break;
										}
								}
						
							if(!hit)
								{
									for(Ostacolo ost: ostacoli)
										if(!ost.getID().equals( Global.TUBO ))
											if(fuoco.getArea().intersects( ost.getArea() ))
												{
													fuoco.setShot( false );
													break;
												}
								}
						}
				}
			if(isShooting && !checkFire())
				{ isShooting = false; }
			
			/*gestione dell'animazione*/
			if(moving || jump)
				animTime = (animTime + delta) % animTimeMove;
			
			if(area.getLocation().equals( prevArea.getLocation() ))
				isMoved = false;
			else
				{
					isMoved = true;
					prevArea.setLocation( area.getX(), area.getY() );
				}
		}

	public void setType( String type )
		{}

	public float getMaxX()
		{ return area.getMaxX(); }

	public float getWidth()
		{ return width;	}

	public float getHeight() 
		{ return height; }

	public Shape getArea()
		{ return area; }

	public float getMaxHeight()
		{ return maxHeight; }

	public float getMaxWidth()
		{ return 0; }

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

	public float getRotate()
		{ return 0; }

	public void setRotate( float val )
		{}

	public boolean contains( Shape shape )
		{
			if(shape.getY() >= body.getY() && shape.getMaxY() <= body.getMaxY())
				if(shape.getX() >= body.getX() && shape.getMaxX() <= body.getMaxX())
					return true;
		
			return false;
		}

	public float getMaxY()
		{ return yPlayer + height; }

	public boolean getInsert()
		{ return insert; }

	public void setX(float x)
		{}
	
	public void setIndex( int val )
		{ pos = val; }
	
	public int getIndex()
		{ return pos; }
}
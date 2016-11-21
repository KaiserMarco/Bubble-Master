package interfaces;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import Utils.Global;
import bubbleMaster.Start;
import dataButton.Button;
import dataButton.SimpleButton;
import dataObstacles.Ostacolo;
import dataObstacles.Player;

public class End
{
	// i bottoni dell'interfaccia
	private SimpleButton replay, begin, choose;
	/*immagine del cursore*/
	private Image cursor;
	/**array contenente i bottoni della schermata*/
	private ArrayList<SimpleButton> buttons;
	/*posizione del cursore*/
	private int indexCursor;
	/*dimensioni del cursore*/
	private int widthC, heightC;
	// vettore dei giocatori partecipanti alla partita
	private ArrayList<Ostacolo> ostacoli;
	
	private boolean mouseDown = false;
	
	private static final String REPLAY = "GIOCA ANCORA", HOME = "TORNA ALLA SCHERMATA PRINCIPALE", LEVELS = "TORNA ALLA SCHERMATA DEI LIVELLI";
	
	private float width = Global.Width/17, height = Global.Height/10;
	private float startX = Global.Width*10/26, startY = Global.Height/25;
	private float offset = Global.Width/10;
	// ascissa e ordinata delle stringhe da stampare
	private float x = Global.Height/8, y = Global.Height/6;
	
	private int pos = 50;
	
	private int timing, h, m, s;
	
	public End() throws SlickException
		{
			cursor = new Image( "./data/Image/cursore.png" ); 
			//lunghezza e altezza del cursore
			widthC = Global.Height*10/133;
			heightC = Global.Height/24;
			
			buttons = new ArrayList<SimpleButton>();

			float buttonX = Global.Width/20, buttonAlt = Global.Width/30;
			replay = new SimpleButton( buttonX, Global.Height*2/3, REPLAY, Color.orange, 0 );
			begin = new SimpleButton( buttonX, replay.getY() + replay.getAlt() + buttonAlt, HOME, Color.orange, 1 );
			choose = new SimpleButton( buttonX, begin.getY() + begin.getAlt() + buttonAlt, LEVELS, Color.orange, 2 );
			
			buttons.add( replay );
			buttons.add( begin );
			buttons.add( choose );

			indexCursor = -1;
			
			ostacoli = InGame.ostacoli;
		}
	
	/** calcola il tempo di gioco in termini di ore minuti e secondi */
	public void setTime()
		{
			timing = (int)(Start.stats.getTempo())/1000;
			h = timing/3600;
			m = (timing - (h*3600))/60;
			s = timing - h*3600 - m*60;
		}
	
	public void draw( GameContainer gc ) throws SlickException
		{
			Graphics g = gc.getGraphics();

			Global.sfondo.draw( gc );
			
			for(Ostacolo player: InGame.players)
				{
					System.out.println( "size = " + InGame.players.size() );
					player.draw( g );
				}

			for(int i = ostacoli.size() - 1; i >= 0; i--)
				ostacoli.get( i ).draw( g );
			
			Color black = new Color( 0, 0, 0, 185 );
			g.setColor( black );
			g.fillRect( 0, 0, Global.Width, Global.Height );
			
			g.setColor( Color.lightGray );

			g.scale( 1.05f, 1.05f );
			
			String colpi = "COLPI SPARATI =       ";
			g.drawString( colpi, x, y );
			
			String hits = "COLPI A SEGNO =        ";
			g.drawString( hits, x, y + pos );
			
			String vite = "VITE PERSE =          ";
			g.drawString( vite, x, y + pos*2 );
			
			String punti = "PUNTEGGIO OTTENUTO =   ";
			g.drawString( punti, x, y + pos*3 );
			
			String seconds = "TEMPO IMPIEGATO =     " + h + "h : " + m + "m : " + s + "s";
			g.drawString( seconds, x, y + pos*44/10 );

			for(int i = 0; i < InGame.players.size(); i++)
				{
					Player player = ((Player) InGame.players.get( i ));
					player.getImage().draw( startX + width + Global.Width/100 + offset*i, startY, Global.Width/17, height );
	
					float xString = startX + width/2 + width + offset*i;
					g.setColor( Color.gray );
					String ammo = "" + player.getShots();
					g.drawString( ammo, xString - ammo.length()/2, y );
		
					g.setColor( Color.green );
					String target = "" + player.getHits();
					g.drawString( target, xString - target.length()/2, y + pos );
					
					g.setColor( Color.red );
					String lifes = "" + (Global.lifes - player.getLifes());
					g.drawString( lifes, xString - lifes.length()/2, y + pos*2 );
					
					g.setColor( Color.yellow );
					String points = "" + player.getPoints();
					g.drawString( points, xString - points.length()/2, y + pos*3 );
					g.setColor( Color.black );
				}
			
			g.resetTransform();
			
			for(int i = 0; i < buttons.size(); i++)
				buttons.get( i ).draw( g );
			
			if(indexCursor >= 0)
				cursor.draw( buttons.get( indexCursor ).getX() - widthC, buttons.get( indexCursor ).getY(), widthC, heightC );
		}
	
	private int checkButton( Button button, Input input, int i )
		{
			if(button.isPressed())
				return 1;
			else if(indexCursor >= 0 && indexCursor == i)
				if(input.isKeyPressed( Input.KEY_ENTER ))
					return 2;
		
			return 0;
		}

	public void update( GameContainer gc, Input input ) throws SlickException
		{
			if(indexCursor < 0 &&((input.isKeyPressed( Input.KEY_UP ) || input.isKeyPressed( Input.KEY_DOWN )
			|| input.isKeyPressed( Input.KEY_LEFT ) || input.isKeyPressed( Input.KEY_RIGHT ))))
				indexCursor = 0;
			else if(input.isKeyPressed( Input.KEY_LEFT ))
				{
					if(--indexCursor < 0)
						indexCursor = buttons.size() - 1;
				}
			else if(input.isKeyPressed( Input.KEY_RIGHT ))
            	indexCursor = (indexCursor + 1)%(buttons.size() - 1);
			
			if(input.isKeyPressed( Input.KEY_ESCAPE ))
				{
            		Start.endGame = 0;
                	indexCursor = -1;
                	Start.begin = 1;
				}
			
			if(input.isMouseButtonDown( Input.MOUSE_LEFT_BUTTON ))
				{
	                if(!mouseDown)
		                {
		                    mouseDown = true;
		                    
		                    for(SimpleButton button : buttons)
		                        if(button.checkClick( input.getMouseX(), input.getMouseY(), input ))
		                        	if(!button.isPressed())
	                            		button.setPressed();
		                }
	            }
            else
	            {
	                if(mouseDown || checkKeyPressed( input ))
		                {
		                    mouseDown = false;
		                    
		                    for(SimpleButton button: buttons)
		                    	{
		                    		int value = checkButton( button, input, button.getIndex() );
		                        	// se e' stato premuto il tasto
		                    		if(value > 0)
		                    			{
				                            // pressed tramite mouse || value==2 tramite tastiera
				                            if(button.checkClick( input.getMouseX(), input.getMouseY(), input ) || value == 2)
					                            {				                    			
					                    			for(SimpleButton bottone : buttons)
					                    				if(bottone.isPressed())
					                    					bottone.setPressed();
					                    			
				                                	Start.endGame = 0;
					                                indexCursor = -1;
				                            		if(button.getName().equals( REPLAY ))
					                            		{
							                                Start.ig.addOstacoli( Begin.livelli.get( Start.cl.getIndexLevel() ).getElements(), Begin.livelli.get( Start.cl.getIndexLevel() ).getImage(), gc );
							                                Global.drawCountdown = true;
							                                Start.stats.startTempo();
							                                Global.inGame = true;
							                                Start.startGame = 1;
					                            		}
				                            		else if(button.getName().equals( HOME ))
				                            			Start.begin = 1;
				                            		else if(button.getName().equals( LEVELS ))
				                                        Start.chooseLevel = 1;

						                            break;
					                            }
		                    			}
		                    	}
		                }
	            }
		}
	
	private boolean checkKeyPressed( final Input input )
    	{ return input.isKeyDown( Input.KEY_ENTER );  }
}
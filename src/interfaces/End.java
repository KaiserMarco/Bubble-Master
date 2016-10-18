package interfaces;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import Utils.Global;
import Utils.Sfondo;
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
	// lo sfondo del livello
	private Sfondo sfondo = null;
	// il giocatore della partita
	Player player;
	
	private boolean mouseDown = false;
	
	private float currRatioW = Global.Width, currRatioH = Global.Height;
	
	private static final String REPLAY = "GIOCA ANCORA", HOME = "TORNA ALLA SCHERMATA PRINCIPALE", LEVELS = "TORNA ALLA SCHERMATA DEI LIVELLI";
	
	public End() throws SlickException
		{
			cursor = new Image( "./data/Image/cursore.png" ); 
			//lunghezza e altezza del cursore
			widthC = Global.H*10/133;
			heightC = Global.H/24;
			
			buttons = new ArrayList<SimpleButton>();

			float buttonX = Global.Width/20, buttonAlt = Global.Width/30;
			replay = new SimpleButton( buttonX, Global.H*2/3, REPLAY, Color.orange );
			begin = new SimpleButton( buttonX, replay.getY() + replay.getAlt() + buttonAlt, HOME, Color.orange );
			choose = new SimpleButton( buttonX, begin.getY() + begin.getAlt() + buttonAlt, LEVELS, Color.orange );
			
			buttons.add( replay );
			buttons.add( begin );
			buttons.add( choose );

			indexCursor = -1;
			
			ostacoli = InGame.ostacoli;
		}
	
	public void draw( GameContainer gc ) throws SlickException
		{
			Graphics g = gc.getGraphics();

			if(sfondo == null)				
				sfondo = Global.sfondo;
			sfondo.draw( gc );
			
			player.draw( g );

			for(int i = 0; i < ostacoli.size(); i++)
				ostacoli.get( i ).draw( g );
			
			Color black = new Color( 0, 0, 0, 185 );
			g.setColor( black );
			g.fillRect( 0, 0, Global.W, Global.H );
			
			g.setColor( Color.lightGray );
			
			// TODO INSERIRE "HAI VINTO" O "HAI PERSO"

			// ascissa e ordinata delle stringhe da stampare
			float x = Global.H/8 * Global.Width/Global.W, y = Global.H/6 * Global.Height/Global.H;
			
			//trasformo il tempo da millisecondi a secondi
			int timing = (int)(Start.stats.getTempo())/1000;
			g.scale( 1.05f, 1.05f );
			g.scale( Global.W/Global.Width, Global.H/Global.Height );
			int h = timing/3600;
			int m = (timing - (h*3600))/60;
			int s = timing - h*3600 - m*60;
			
			int pos = 50;
			
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
			
			float width = Global.W/17 * Global.Width/Global.W, height = Global.H/10 * Global.Height/Global.H;
			float startX = Global.W*10/26 * Global.Width/Global.W, startY = Global.H/25 * Global.Height/Global.H;
			float offset = Global.W/10 * Global.Width/Global.W;
			
			player.getImage().draw( startX + width + offset, startY, Global.W/17 * Global.Width/Global.W, height );
			
			// TODO STAMPARE I RISULTATI UN PO' PIU VICINI

			float xString = startX + width/2 + width + offset;
			g.setColor( Color.gray );
			String ammo = "" + player.getShots();
			g.drawString( ammo, xString, y );

			g.setColor( Color.green );
			String target = "" + player.getHits();
			g.drawString( target, xString, y + pos );
			
			g.setColor( Color.red );
			String lifes = "" + (Global.lifes - player.getLifes());
			g.drawString( lifes, xString, y + pos*2 );
			
			g.setColor( Color.yellow );
			String points = "" + player.getPoints();
			g.drawString( points, xString, y + pos*3 );
			g.setColor( Color.black );
			
			g.resetTransform();
			
			for(int i = 0; i < buttons.size(); i++)
				buttons.get( i ).draw( g );
			
			if(indexCursor >= 0)
				cursor.draw( buttons.get( indexCursor ).getX() - widthC, buttons.get( indexCursor ).getY(), widthC, heightC );
		}
	
	public void setPlayer( GameContainer gc )
		{ player = InGame.player; }
	
	private void returnToBegin()
		{ Start.begin = 1; }
	
	private int checkButton( Button button, Input input, int i )
		{
			if(button.isPressed())
				return 1;
			else if(indexCursor >= 0 && indexCursor == i)
				if(input.isKeyPressed( Input.KEY_ENTER ))
					return 2;
		
			return 0;
		}
	
	public void updateDates() throws SlickException
		{
			for(SimpleButton button: buttons)
				button.buildButton( button.getX() * Global.ratioW, button.getY() * Global.ratioH );
			
			currRatioH = Global.H;
			currRatioW = Global.W;
		}

	public void update(GameContainer gc) throws SlickException
		{
			Input input = gc.getInput();
			int mouseX = input.getMouseX();
			int mouseY = input.getMouseY();
			
			if(currRatioW != Global.W || currRatioH != Global.H)
				updateDates();

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
                	sfondo = null;
					returnToBegin();
				}
			
			if(input.isMouseButtonDown( Input.MOUSE_LEFT_BUTTON ))
				{
	                if(!mouseDown)
		                {
		                    mouseDown = true;
		                    
		                    for(SimpleButton button : buttons)
		                        if(button.checkClick( mouseX, mouseY, input ))
		                        	if(!button.isPressed())
	                            		button.setPressed();
		                }
	            }
            else
	            {
	                if(mouseDown || checkKeyPressed( input ))
		                {
		                    mouseDown = false;
		                    
		                    for(int i = 0; i < buttons.size(); i++)
		                    	{
		                    		int value = checkButton( buttons.get( i ), input, i );
		                        	boolean pressed = true;
		                        	// se e' stato premuto il tasto
		                    		if(value > 0)
		                    			{
			                                buttons.get( i ).setPressed();
			                                pressed = buttons.get( i ).checkClick( mouseX, mouseY, input );
				                            // pressed tramite mouse || value==2 tramite tastiera
				                            if(pressed || value == 2)
					                            {				                    			
					                    			for(SimpleButton button : buttons)
					                    				if(button.isPressed())
					                    					button.setPressed();
				                                	Start.endGame = 0;
					                                indexCursor = -1;
				                            		if(buttons.get( i ).getName().equals( REPLAY ))
					                            		{
							                                Start.ig.addOstacoli( Begin.livelli.get( Start.cl.getIndexLevel() ).getElements(), Begin.livelli.get( Start.cl.getIndexLevel() ).getImage(), gc );
							                                Global.drawCountdown = true;
							                                Start.stats.startTempo();
							                                Global.inGame = true;
							                                Start.startGame = 1;
					                            		}
				                            		else if(buttons.get( i ).getName().equals( HOME ))
				                            			returnToBegin();
				                            		else if(buttons.get( i ).getName().equals( LEVELS ))
				                                        Start.chooseLevel = 1;
				                            		
				                            		sfondo = null;
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
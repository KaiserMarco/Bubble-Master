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
	private ArrayList<Ostacolo> players, ostacoli;
	// lo sfondo del livello
	private Sfondo sfondo = null;
	
	private boolean mouseDown = false;
	
	private static final String REPLAY = "RIGIOCA";
	
	public End() throws SlickException
		{
			cursor = new Image( "./data/Image/cursore.png" ); 
			//lunghezza e altezza del cursore
			widthC = Global.H*10/133;
			heightC = Global.H/24;
			
			buttons = new ArrayList<SimpleButton>();
			
			replay = new SimpleButton( Global.W/5, Global.H*3/4, REPLAY, Color.orange );
			begin = new SimpleButton( Global.W/2, Global.H*3/4, "TORNA ALLA SCHERMATA PRINCIPALE", Color.orange );
			choose = new SimpleButton( Global.W*10/33, Global.H*6/7, "TORNA ALLA SCELTA LIVELLI", Color.orange );
			
			buttons.add( replay );
			buttons.add( begin );
			buttons.add( choose );

			indexCursor = -1;
			
			players = InGame.players;
			ostacoli = InGame.ostacoli;
		}
	
	public void draw( GameContainer gc ) throws SlickException
		{
			
			Graphics g = gc.getGraphics();			

			if(sfondo == null)				
				sfondo = Global.sfondo;
			sfondo.draw( gc );
			
			for(int i = 0; i < players.size(); i++)
				players.get( i ).draw( g );

			for(int i = 0; i < ostacoli.size(); i++)
				ostacoli.get( i ).draw( g );
			
			Image fine = new Image( "./data/Image/vuoto.png" );
			Color black = new Color( 0, 0, 0, 185 );
			fine.draw( 0, 0, Global.W, Global.H, black );

			// TODO INSERIRE EVENTUALI ALTRE STATISTICHE

			// ascissa e ordinata delle stringhe da stampare
			int x = Global.H/8, y = Global.H/6;
			
			//trasformo il tempo da millisecondi a secondi
			int timing = (int)(Start.stats.getTempo())/1000;
			//g.setFont( new UnicodeFont( "./data/fonts/prstart.ttf", (int)(10.f), false, true ) );
			g.scale( 1.05f, 1.05f );
			int h = timing/3600;
			int m = (timing - (h*3600))/60;
			int s = timing - h*3600 - m*60;
			String seconds = "TEMPO IMPIEGATO =     " + h + "h : " + m + "m : " + s + "s";
			g.drawString( seconds, x, y );
			
			String colpi = "COLPI SPARATI =       ";
			g.drawString( colpi, x, y + 50 );
			
			String vite = "VITE PERSE =          ";
			g.drawString( vite, x, y + 100 );
			
			String punti = "PUNTEGGIO OTTENUTO =   ";
			g.drawString( punti, x, y + 150 );
			
			int width = Global.W/17, height = Global.H/10;
			int startX = Global.W*10/26, startY = Global.H/25;
			int offset = Global.W/10;
			for(int i = 0; i < players.size(); i++)
				{
					((Player) players.get( i )).getImage().draw( startX + (width + offset) * i, startY, Global.W/17, height );
					
					String ammo = "" + ((Player) players.get( i )).getShots();
					g.drawString( ammo, startX + width/2 + (width + offset) * i, y + 50 );
					
					String lifes = "" + (Global.lifes - ((Player) players.get( i )).getLifes());
					g.drawString( lifes, startX + width/2 + (width + offset) * i, y + 100 );
					
					String points = "" + ((Player) players.get( i )).getPoints();
					g.drawString( points, startX + width/2 + (width + offset) * i, y + 150 );
				}
			
			g.resetTransform();
			
			for(int i = 0; i < buttons.size(); i++)
				buttons.get( i ).draw( gc.getGraphics() );
			
			if(indexCursor >= 0)
				cursor.draw( buttons.get( indexCursor ).getX() - widthC, buttons.get( indexCursor ).getY(), widthC, heightC );
		}

	public void update(GameContainer gc) throws SlickException
		{
			Input input = gc.getInput();
			int mouseX = input.getMouseX();
			int mouseY = input.getMouseY();
			
			if((input.isKeyPressed( Input.KEY_UP ) || input.isKeyPressed( Input.KEY_DOWN ) || input.isKeyPressed( Input.KEY_LEFT ) || input.isKeyPressed( Input.KEY_RIGHT )))
                {
                    if(indexCursor < 0)
                        indexCursor = 0;
                    else if(indexCursor == 0)
                        indexCursor = 1;
                    else
                        indexCursor = 0;
                }
			
			if(input.isMouseButtonDown( Input.MOUSE_LEFT_BUTTON )) {
                if(!mouseDown) {
                    mouseDown = true;
                    
                    for(SimpleButton button : buttons) {
                        if(button.checkClick( mouseX, mouseY, input )) {
                            if(!button.isPressed())
                                button.setPressed();
                        }
                    }
                }
            }
            else {
                if(mouseDown || checkKeyPressed( input )) {
                    mouseDown = false;
                    
                    if(replay.isPressed() || (buttons.get( indexCursor ).getName().equals( REPLAY ) && input.isKeyPressed( Input.KEY_ENTER )))
                        {
                            boolean pressed = true;
                            
                            if(replay.isPressed()) {
                                replay.setPressed();
                                pressed = replay.checkClick( mouseX, mouseY, input );
                            }
                            
                            if(pressed) {
                                indexCursor = -1;
                                Start.ig.addOstacoli( Begin.livelli.get( Start.cl.getIndexLevel() ).getElements(), Begin.livelli.get( Start.cl.getIndexLevel() ).getImage(), gc );
                                Global.drawCountdown = true;
                                Start.stats.startTempo();
                                Global.inGame = true;
                                Start.endGame = 0;
                                Start.startGame = 1;
                            }
                        }
                    
                    else if(begin.isPressed() || (buttons.get( indexCursor ).getName().startsWith( "TORNA ALLA SCHERMATA" ) && input.isKeyPressed( Input.KEY_ENTER )))
                        {
                            boolean pressed = true;
                            
                            if(begin.isPressed()) {
                                begin.setPressed();
                                pressed = begin.checkClick( mouseX, mouseY, input );
                            }
                            
                            if(pressed) {
                                indexCursor = -1;
                                Start.endGame = 0;
                                Start.begin = 1;
                            }
                        }
                    
                    else if(choose.isPressed() || (buttons.get( indexCursor ).getName().startsWith( "TORNA ALLA SCELTA" ) && input.isKeyPressed( Input.KEY_ENTER )))
                        {
                            boolean pressed = true;
                            
                            if(choose.isPressed()) {
                                choose.setPressed();
                                pressed = choose.checkClick( mouseX, mouseY, input );
                            }
                            
                            if(pressed) {
                                indexCursor = -1;
                                Start.endGame = 0;
                                Start.chooseLevel = 1;
                            }
                        }
                }
            }
		}
	
	private boolean checkKeyPressed( final Input input )
    {
        return input.isKeyDown( Input.KEY_ENTER );
    }
}

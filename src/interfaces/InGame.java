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
import dataObstacles.Bubble;
import dataObstacles.Ostacolo;
import dataObstacles.Player;

public class InGame 
{	
	/**array contenente gli ostacoli della partita*/
	public static ArrayList<Ostacolo> ostacoli;
	
	/**array contenente i giocatori della partita*/
	public static ArrayList<Ostacolo> players;
	
	private Sfondo sfondo;
	
	private Image uno, due, tre;
	private int animNumbers;
	
	private int decrNumb;
	
	// i bottoni dell'interfaccia
	private SimpleButton replay, begin, choose;	
	// lunghezza e altezza iniziali dei numeri
	private int widthI, heightI;
	/*immagine del cursore*/
	private Image cursor;
	/**array contenente i bottoni della schermata*/
	private ArrayList<SimpleButton> buttons;
	/*posizione del cursore*/
	private int indexCursor;
	/*dimensioni del cursore*/
	private int widthC, heightC;

	public InGame() throws SlickException
		{
			ostacoli = new ArrayList<Ostacolo>();
			players = new ArrayList<Ostacolo>();
			
			tre = new Image( "./data/Image/3.png" );
			due = new Image( "./data/Image/2.png" );
			uno = new Image( "./data/Image/1.png" );
			
			widthI = Global.W/25;
			heightI = Global.H/20;
			
			animNumbers = 30;
			
			decrNumb = 4;			

			cursor = new Image( "./data/Image/cursore.png" ); 
			//lunghezza e altezza del cursore
			widthC = Global.H*10/133;
			heightC = Global.H/24;
			
			buttons = new ArrayList<SimpleButton>();
			
			replay = new SimpleButton( Global.W/5, Global.H*3/4, "RIGIOCA", Color.orange );
			begin = new SimpleButton( Global.W/2, Global.H*3/4, "TORNA ALLA SCHERMATA PRINCIPALE", Color.orange );
			choose = new SimpleButton( Global.W*10/33, Global.H*6/7, "TORNA ALLA SCELTA LIVELLI", Color.orange );
			
			buttons.add( replay );
			buttons.add( begin );
			buttons.add( choose );
		}
	
	public void addOstacoli( ArrayList<Ostacolo> obs, Sfondo sfondo, GameContainer gc ) throws SlickException
		{
			players.clear();
			ostacoli.clear();
			
			int numPlayer = 0;
			
			this.sfondo = sfondo;
		
			for(int i = 0; i < obs.size(); i++)
				{
					Ostacolo ost = obs.get( i );
					if(ost.getID().startsWith( "player" ))
						{
							players.add( new Player( (int) ost.getX(), (int) ost.getY(), ++numPlayer, gc ) );
							players.get( players.size() - 1 ).setMaxHeight( sfondo.getMaxHeight() );
						}
					else if(obs.get( i ).getID().equals( "bolla" ))
						{
							ostacoli.add( new Bubble( (int) ost.getX(), (int) ost.getY(), (int) ost.getWidth(), ost.getMaxWidth(), gc ) );
							ostacoli.get( ostacoli.size() - 1 ).setMaxHeight( sfondo.getMaxHeight() );
						}
					else
						ostacoli.add( ost );
				}
			
			Global.giocatori = numPlayer;
			
			cursor = new Image( "./data/Image/cursore.png" );
			
			indexCursor = -1;
		}

	public void draw( GameContainer gc, Graphics g) throws SlickException
		{		
			g.setAntiAlias( true );
			sfondo.draw( gc );
			
			for(int i = ostacoli.size() - 1; i >= 0; i--)
				ostacoli.get( i ).draw( g, 0, 0, true );
			
			for(int i = 0; i < players.size(); i++)
				players.get( i ).draw( g, 0, 0, true );
			
			if(!Global.inGame)
				{				
					Image fine = new Image( "./data/Image/vuoto.png" );
					Color black = new Color( 0, 0, 0, 185 );
					fine.draw( 0, 0, Global.W, Global.H, black );

					// ascissa e ordinata delle stringhe da stampare
					int x = Global.H/8, y = Global.H/6;
					
					//trasformo il tempo da millisecondi a secondi
					int timing = (int)Start.stats.getTempo()/1000;
					int h = timing/3600;
					int m = (timing - (h*3600))/60;
					int s = timing - h*3600 - m*60;
					String seconds = "TEMPO IMPIEGATO =     " + h + "h : " + m + "m : " + s + "s";
					g.drawString( seconds, x, y );
				
					// TODO SETTARE CORRETTAMENTE I VALORI
					for(int i = 0; i < players.size(); i++)
						players.get( i ).draw( g, 0, 0, false );
					
					for(int i = 0; i < buttons.size(); i++)
						buttons.get( i ).draw( gc.getGraphics() );
					
					if(indexCursor >= 0)
						cursor.draw( buttons.get( indexCursor ).getX() - widthC, buttons.get( indexCursor ).getY(), widthC, heightC );
				}

			// disegna il countdown iniziale
			if(Global.drawCountdown)
				{
					if(animNumbers > 20)
						tre.draw( Global.W/2 - (widthI*(10 - animNumbers%10))/2, Global.H/2 - (heightI*(10 - animNumbers%10))/2, widthI*(10 - animNumbers%10), heightI*(10 - animNumbers%10) );
					else if(animNumbers > 10)
						due.draw( Global.W/2 - (widthI*(10 - animNumbers%10))/2, Global.H/2 - (heightI*(10 - animNumbers%10))/2, widthI*(10 - animNumbers%10), heightI*(10 - animNumbers%10) );	
					else if(animNumbers > 0)
						uno.draw( Global.W/2 - (widthI*(10 - animNumbers%10))/2, Global.H/2 - (heightI*(10 - animNumbers%10))/2, widthI*(10 - animNumbers%10), heightI*(10 - animNumbers%10) );
					else
						{
							Global.drawCountdown = false;
							animNumbers = 30;
						}
					
					if(decrNumb == 0)
						{
							animNumbers--;
							decrNumb = 4;
						}
					else
						decrNumb--;
				}
		}
	
	public void update(GameContainer gc, int delta) throws SlickException
		{
			Input input = gc.getInput();
			int mouseX = input.getMouseX();
			int mouseY = input.getMouseY();
		
			if(gc.getInput().isKeyPressed( Input.KEY_ESCAPE ))
				{
					Start.startGame = 0;
					Start.chooseLevel = 1;
					Start.setPreviuosStats( "startGame" );
				}
		
			if(!Global.drawCountdown && Global.inGame)
				{
					for(int i = 0; i < players.size(); i++)
						players.get( i ).update( gc, delta );
					
					for(int i = 0; i < ostacoli.size(); i++)
						{
							if(ostacoli.get( i ).getID().equals( "bolla" ))
								ostacoli.get( i ).update( gc, delta );
							else
								ostacoli.get( i ).update( gc );
						}
				}

			if(!Global.inGame)
				{
					if((input.isKeyPressed( Input.KEY_UP ) || input.isKeyPressed( Input.KEY_DOWN ) || input.isKeyPressed( Input.KEY_LEFT ) || input.isKeyPressed( Input.KEY_RIGHT )))
						{
							if(indexCursor < 0)
								indexCursor = 0;
							else if(indexCursor == 0)
								indexCursor = 1;
							else
								indexCursor = 0;
						}
		
					if((replay.checkClick( mouseX, mouseY ) && input.isMousePressed( Input.MOUSE_LEFT_BUTTON )) || (input.isKeyPressed( Input.KEY_ENTER ) && buttons.get( indexCursor ).getName().equals( "RITENTA" )))
						{
							indexCursor = -1;
							Start.ig.addOstacoli( Begin.livelli.get( Start.cl.getIndexLevel() ).getElements(), Begin.livelli.get( Start.cl.getIndexLevel() ).getImage(), gc );
							Global.drawCountdown = true;
							Start.stats.startTempo();
							Global.inGame = true;
						}
					
					else if((begin.checkClick( mouseX, mouseY ) && input.isMousePressed( Input.MOUSE_LEFT_BUTTON )) || (input.isKeyPressed( Input.KEY_ENTER ) && buttons.get( indexCursor ).getName().startsWith( "TORNA ALLA SCHERMATA" )))
						{
							indexCursor = -1;
							Start.startGame = 0;
							Start.begin = 1;
						}
					
					else if((choose.checkClick( mouseX, mouseY ) && input.isMousePressed( Input.MOUSE_LEFT_BUTTON )) || (input.isKeyPressed( Input.KEY_ENTER ) && buttons.get( indexCursor ).getName().startsWith( "TORNA ALLA SCELTA" )))
						{
							indexCursor = -1;
							Start.startGame = 0;
							Start.chooseLevel = 1;
						}
				}
		}
}




















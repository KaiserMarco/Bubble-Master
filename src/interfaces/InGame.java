package interfaces;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import Utils.Global;
import Utils.Sfondo;
import bubbleMaster.Start;
import dataObstacles.Ostacolo;

public class InGame
{	
	/**array contenente gli ostacoli della partita*/
	public static ArrayList<Ostacolo> ostacoli;
	
	/**array contenente i giocatori della partita*/
	public static ArrayList<Ostacolo> players;
	
	// lo sfondo del livello
	private Sfondo sfondo;
	
	// le immagini dei numeri del countdown
	private Image uno, due, tre;
	// i valori relativi all'animazione del countdown
	private int animNumbers;	
	private int decrNumb;
	
	// lunghezza e altezza base dei numeri del countdown
	private int widthI, heightI;

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
		}
	
	public void addOstacoli( ArrayList<Ostacolo> obs, Sfondo sfondo, GameContainer gc ) throws SlickException
		{
			players.clear();
			ostacoli.clear();
			
			this.sfondo = sfondo;
		
			for(int i = 0; i < obs.size(); i++)
				{
					Ostacolo ost = obs.get( i );
					if(ost.getID().startsWith( "player" ))
						{
							players.add( obs.get( i ).clone( gc ) );
							players.get( players.size() - 1 ).setMaxHeight( sfondo.getMaxHeight() );
						}
					else if(obs.get( i ).getID().equals( "bolla" ))
						{
							ostacoli.add( obs.get( i ).clone( gc ) );
							ostacoli.get( ostacoli.size() - 1 ).setMaxHeight( sfondo.getMaxHeight() );
						}
					else
						ostacoli.add( ost );
				}
			
			Global.sfondo = sfondo;
		}

	public void draw( GameContainer gc, Graphics g) throws SlickException
		{		
			g.setAntiAlias( true );
			sfondo.draw( gc );
			
			for(int i = ostacoli.size() - 1; i >= 0; i--)
				ostacoli.get( i ).draw( g );
			
			for(int i = 0; i < players.size(); i++)
				players.get( i ).draw( g );
			
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
			if(gc.getInput().isKeyPressed( Input.KEY_ESCAPE ))
				{
					animNumbers = 30;
					decrNumb = 4;
					Start.startGame = 0;
					Start.chooseLevel = 1;
				}
		
			if(!Global.drawCountdown && Global.inGame)
				{
					for(int i = 0; i < players.size(); i++)
						players.get( i ).update( gc, delta );
					
					for(int i = 0; i < ostacoli.size(); i++)
						if(ostacoli.get( i ).getID().equals( "bolla" ))
							ostacoli.get( i ).update( gc, delta );
				}
			
			if(!Global.inGame)
				{
					Start.stats.stopTempo();
				
					Start.startGame = 0;
					Start.endGame = 1;
				}
		}
}
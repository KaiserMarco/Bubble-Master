package interfaces;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import bubbleMaster.Start;
import DataEntites.Sfondo;
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

	public InGame() throws SlickException
		{
			ostacoli = new ArrayList<Ostacolo>();
			players = new ArrayList<Ostacolo>();
		}
	
	public void addOstacoli( ArrayList<Ostacolo> obs, Sfondo sfondo ) throws SlickException
		{
			players.clear();
			ostacoli.clear();
			
			int numPlayer = 0;
			
			this.sfondo = sfondo;
		
			for(int i = 0; i < obs.size(); i++)
				{
					Ostacolo ost = obs.get( i );
					if(ost.ID.startsWith( "player" ))
						{
							players.add( new Player( ost.getX(), ost.getY(), numPlayer++ ) );
							players.get( players.size() - 1 ).setMaxHeight( sfondo.getMaxHeight() );
						}
					else if(obs.get( i ).ID.equals( "bolla" ))
						{
							ostacoli.add( new Bubble( ost.getX(), ost.getY(), (int) ost.getWidth(), ost.getMaxWidth() ) );
							ostacoli.get( ostacoli.size() - 1 ).setMaxHeight( sfondo.getMaxHeight() );
						}
					else
						ostacoli.add( ost );
				}
		}

	public void draw( GameContainer gc, Graphics g) throws SlickException
		{
			sfondo.draw( gc );
			
			for(int i = 0; i < ostacoli.size(); i++)
				ostacoli.get( i ).draw( g );
			
			for(int i = 0; i < players.size(); i++)
				players.get( i ).draw( g );
		}
	
	public void update(GameContainer gc, int delta) throws SlickException
		{
			if(gc.getInput().isKeyPressed( Input.KEY_ESCAPE ))
				{
					Start.startGame = 0;
					Start.begin = 1;
				}
		
			for(int i = 0; i < players.size(); i++)
				players.get( i ).update( gc, delta );
			
			for(int i = 0; i < ostacoli.size(); i++)
				ostacoli.get( i ).update( gc );
		}
}

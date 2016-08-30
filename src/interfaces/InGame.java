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
		}

	public void draw( GameContainer gc, Graphics g) throws SlickException
		{
			g.setAntiAlias( true );
			sfondo.draw( gc );
			
			for(int i = ostacoli.size() - 1; i >= 0; i--)
				ostacoli.get( i ).draw( g );
			
			for(int i = 0; i < players.size(); i++)
				players.get( i ).draw( g );
		}
	
	public void update(GameContainer gc, int delta) throws SlickException
		{
			if(gc.getInput().isKeyPressed( Input.KEY_ESCAPE ))
				{
					Start.startGame = 0;
					Start.chooseLevel = 1;
					Start.setPreviuosStats( "startGame" );
				}
		
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
}

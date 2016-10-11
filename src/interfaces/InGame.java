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
import dataObstacles.Enter;
import dataObstacles.Ostacolo;
import dataObstacles.Player;
import dataObstacles.Tubo;
import dataPowers.PowerUp;

public class InGame
{	
	/**array contenente gli ostacoli della partita*/
	public static ArrayList<Ostacolo> ostacoli;
	
	/**array contenente i giocatori della partita*/
	public static ArrayList<Ostacolo> players;
	
	// il vettore dei potenziamenti del personaggio
	public static ArrayList<PowerUp> powerUp;
	
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
			powerUp = new ArrayList<PowerUp>();
			
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
			powerUp.clear();
			
			this.sfondo = sfondo;
		
			for(Ostacolo elem: obs)
				{
					Ostacolo ost = elem;
					if(ost.getID().startsWith( "player" ))
						{
							players.add( elem.clone( gc ) );
							
							Ostacolo p = players.get( players.size() - 1 );
							
							((Player) p).setDrawLifes( true );
							((Player) p).setDrawPoints( true );
							p.setHeight( ost.getHeight() );
							p.setWidth( ost.getWidth() );
							((Player) p).setWidthI( ((Player) ost).getWidthI() );
							p.setMaxHeight( sfondo.getMaxHeight() );							
							
							p.setArea( gc );
						}
					else if(elem.getID().equals( "bolla" ))
						{
							ostacoli.add( elem.clone( gc ) );
							ostacoli.get( ostacoli.size() - 1 ).setMaxHeight( sfondo.getMaxHeight() );
						}
					else
						ostacoli.add( ost );
				}
			
			int size = ostacoli.size();
			for(int i = 0; i < size; i++)
				if(ostacoli.get( i ).getID().equals( "tubo" ))
					{
						((Tubo) ostacoli.get( i )).setSpace( gc );
						ostacoli.add( ((Tubo) ostacoli.get( i )).getBase() );
						ostacoli.add( ((Tubo) ostacoli.get( i )).getEnter() );
						ostacoli.get( ostacoli.size() - 1 ).setArea( gc );
						ostacoli.get( ostacoli.size() - 2 ).setArea( gc );
						((Enter) ostacoli.get( ostacoli.size() - 1 )).setIndexTube( i );
						((Enter) ostacoli.get( ostacoli.size() - 1 )).setDirection( ((Tubo) ostacoli.get( i )).getDirection() );
					}
			
			Global.sfondo = sfondo;
		}

	public void draw( GameContainer gc, Graphics g) throws SlickException
		{		
			g.setAntiAlias( true );
			sfondo.draw( gc );

			for(PowerUp pu: powerUp)
				pu.draw( g );
			
			for(int i = ostacoli.size() - 1; i >= 0; i--)
				{
					Ostacolo obsI = ostacoli.get( i );
					if(obsI.getID().equals( "bolla" ))
						for(int j = 0; j < ostacoli.size(); j++)
							{
								if(j != i)
									{
										Ostacolo obsJ = ostacoli.get( j );
										if(obsI.getX() >= obsJ.getX() && obsI.getX() + obsI.getWidth() <= obsJ.getX() + obsJ.getWidth()
										&& obsI.getY() >= obsJ.getY() && obsI.getY() + obsI.getHeight() <= obsJ.getY() + obsJ.getHeight())
											break;
										else
											obsI.draw( g );
									}
							}
					else
						obsI.draw( g );								
				}
			
			for(Ostacolo p: players)
				p.draw( g );
			
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
					
					for(Ostacolo p: players)
						{
							((Player) p).setDrawLifes( false );
							((Player) p).setDrawPoints( false );
						}
				}
		
			if(!Global.drawCountdown && Global.inGame)
				{				
					for(PowerUp pu: powerUp)
						pu.update( gc, delta );
					
					for(Ostacolo p: players)
						p.update( gc, delta );
					
					for(Ostacolo ost: ostacoli)
						if(ost.getID().equals( "bolla" ))
							ost.update( gc, delta );
				}
			
			if(!Global.inGame)
				{
					Start.stats.stopTempo();
				
					Start.startGame = 0;
					Start.endGame = 1;
				}
		}
}
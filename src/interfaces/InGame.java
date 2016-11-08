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
import dataObstacles.Base;
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
			
			widthI = Global.Width/25;
			heightI = Global.Height/20;
			
			animNumbers = 30;
			
			decrNumb = 4;
		}
	
	public void addOstacoli( ArrayList<Ostacolo> obs, Sfondo sfondo, GameContainer gc ) throws SlickException
		{
			ostacoli.clear();
			powerUp.clear();
			players.clear();
			
			this.sfondo = sfondo;
		
			for(Ostacolo elem: obs)
				{
					System.out.println( "class = " + elem.getClass() );
					if(elem.getID().equals( Global.PLAYER ))
						{
							players.add( elem.clone( gc ) );
							
							Player player = ((Player) players.get( players.size() - 1 ));
							player.setDrawLifes( true );
							player.setDrawPoints( true );
							player.setHeight( elem.getHeight() );
							player.setWidth( elem.getWidth() );
							player.setWidthI( ((Player) elem).getWidthI() );
							player.setMaxHeight( sfondo.getMaxHeight() );
							
							player.setArea( gc );
						}
					else if(elem.getID().equals( Global.BOLLA ))
						{
							ostacoli.add( elem.clone( gc ) );
							ostacoli.get( ostacoli.size() - 1 ).setMaxHeight( sfondo.getMaxHeight() );
						}
					else
						ostacoli.add( elem );
				}
			
			int size = ostacoli.size();
			for(int i = 0; i < size; i++)
				{
					Ostacolo ost = ostacoli.get( i );
					if(ost.getID().equals( Global.TUBO ))
						{
							ost.setSpigoli();
							ostacoli.add( ((Tubo) ost).getBase() );
							((Base) ostacoli.get( ostacoli.size() - 1 )).setIndexTube( i );
							ostacoli.add( ((Tubo) ost).getEnter() );
							((Enter) ostacoli.get( ostacoli.size() - 1 )).setIndexTube( i );
						}
				}
			
			Global.sfondo = sfondo;
		}

	public void draw( GameContainer gc, Graphics g) throws SlickException
		{		
			g.setAntiAlias( true );
			sfondo.draw( gc );

			for(PowerUp pu: powerUp)
				pu.draw( g );
			
			// evita di non far disegnare entrambe le sfere sovrapposte
			int dodgeIndex = -1;
			for(int i = ostacoli.size() - 1; i >= 0; i--)
				{
					if(i != dodgeIndex && ostacoli.get( i ).getID().equals( Global.BOLLA ))
						{
							boolean dark = false;
							for(int j = 0; j < ostacoli.size(); j++)
								{
									if(j != i && ostacoli.get( j ).contains( ostacoli.get( i ).getArea() ))
										{
											if(ostacoli.get( j ).getID().equals( Global.BOLLA ))
												dodgeIndex = j;
											dark = true;
											break;
										}
								}
							if(!dark)
								ostacoli.get( i ).draw( g );
						}
					else
						ostacoli.get( i ).draw( g );
				}
			
			for(Ostacolo player: players)
				player.draw( g );
			
			// disegna il countdown iniziale
			if(Global.drawCountdown)
				{
					if(animNumbers > 20)
						tre.draw( Global.Width/2 - (widthI*(10 - animNumbers%10))/2, Global.Height/2 - (heightI*(10 - animNumbers%10))/2, widthI*(10 - animNumbers%10), heightI*(10 - animNumbers%10) );
					else if(animNumbers > 10)
						due.draw( Global.Width/2 - (widthI*(10 - animNumbers%10))/2, Global.Height/2 - (heightI*(10 - animNumbers%10))/2, widthI*(10 - animNumbers%10), heightI*(10 - animNumbers%10) );	
					else if(animNumbers > 0)
						uno.draw( Global.Width/2 - (widthI*(10 - animNumbers%10))/2, Global.Height/2 - (heightI*(10 - animNumbers%10))/2, widthI*(10 - animNumbers%10), heightI*(10 - animNumbers%10) );
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
			
			Global.drawScreenBrightness( g );
		}
	
	public void update( GameContainer gc, int delta, End end ) throws SlickException
		{		
			Input input = gc.getInput();
		
			if(input.isKeyPressed( Input.KEY_ESCAPE ))
				{
					animNumbers = 30;
					decrNumb = 4;
					Start.startGame = 0;
					Start.chooseLevel = 1;
					
					for(Ostacolo player: players)
						{
							((Player) player).setDrawLifes( false );
							((Player) player).setDrawPoints( false );
						}
				}
		
			if(!Global.drawCountdown && Global.inGame)
				{				
					for(PowerUp pu: powerUp)
						pu.update( gc, delta );

					for(Ostacolo player: players)
						((Player) player).update( gc, delta, input );
					
					for(Ostacolo ost: ostacoli)
						if(ost.getID().equals( Global.BOLLA ))
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
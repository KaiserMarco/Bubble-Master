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
	public static ArrayList<Player> players;
	
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

	// l'ostacolo di turno da analizzare
	private Ostacolo ost;
	
	// determina se l'oggetto e' visibile
	private boolean dark;

	public InGame() throws SlickException
		{
			ostacoli = new ArrayList<Ostacolo>();
			players = new ArrayList<Player>();
			powerUp = new ArrayList<PowerUp>();
			
			tre = new Image( "./data/Image/3.png" );
			due = new Image( "./data/Image/2.png" );
			uno = new Image( "./data/Image/1.png" );
			
			widthI = Global.Width/25;
			heightI = Global.Height/20;
			
			animNumbers = 30;
			
			decrNumb = 4;
		}
	
	public void setSpheres( GameContainer gc )
		{
			for(int i = ostacoli.size() - 1; i >= 0; i--)
				{
					ost = ostacoli.get( i );
					if(ost.getID().equals( Global.BOLLA ))
						{
							ostacoli.add( ost.clone( gc ) );
							ostacoli.get( ostacoli.size() - 1 ).setMaxHeight( sfondo.getMaxHeight() );
							ostacoli.remove( i );
						}
				}
		}
	
	public void addOstacoli( ArrayList<Ostacolo> obs, Sfondo sfondo, GameContainer gc ) throws SlickException
		{
			ostacoli.clear();
			powerUp.clear();
			players.clear();
			
			this.sfondo = sfondo;
		
			for(Ostacolo elem: obs)
				{
					if(elem.getID().equals( Global.PLAYER ))
						{
							players.add( (Player) elem.clone( gc ) );
							
							Player player = players.get( players.size() - 1 );
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
					ost = ostacoli.get( i );
					if(ost.getID().equals( Global.TUBO ))
						{
							ost.setSpigoli();
							ostacoli.add( ((Tubo) ost).getBase() );
							((Base) ostacoli.get( ostacoli.size() - 1 )).setIndexTube( i );
							ostacoli.add( ((Tubo) ost).getEnter() );
							((Enter) ostacoli.get( ostacoli.size() - 1 )).setIndexTube( i );
						}
				}
			
			setSpheres( gc );
			
			Global.sfondo = sfondo;
		}

	public void draw( GameContainer gc, Graphics g ) throws SlickException
		{
			sfondo.draw( gc );

			for(PowerUp pu: powerUp)
				pu.draw( g );
			
			for(int i = ostacoli.size() - 1; i >= 0; i--)
				{
					ost = ostacoli.get( i );
					if(!(ost.getID().equals( Global.BASE ) || ost.getID().equals( Global.ENTER )))
						{
							if(ost.getID().equals( Global.BOLLA ))
								{
									dark = false;
									for(Ostacolo player: players)
										if(player.contains( ost.getArea() ))
											dark = true;
									
									if(!dark)
										for(int j = i - 1; j >= 0; j--)
											if(ostacoli.get( j ).contains( ost.getArea() ))
												dark = true;
									
									if(!dark)
										ost.draw( g );
								}
							else
								ost.draw( g );
						}
				}
			
			for(Player player: players)
				player.drawPlay( g );
			
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
	
	public void update( GameContainer gc, int delta, End end, Input input ) throws SlickException
		{
			if(input.isKeyPressed( Input.KEY_ESCAPE ))
				{
					Start.startGame = 0;
					Start.chooseLevel = 1;
				}
		
			if(!Global.drawCountdown)
				{				
					for(PowerUp pu: powerUp)
						if(pu.getID().equals( Global.LIFE ) || !pu.isArrived())
							pu.update( gc, delta );

					for(Player player: players)
						player.update( gc, delta, input );
					
					for(Ostacolo ost: ostacoli)
						if(ost.getID().equals( Global.BOLLA ))
							ost.update( gc, delta );
				}
			
			if(!Global.inGame)
				{
					Start.stats.stopTempo();
					end.setTime();
				
					Start.startGame = 0;
					Start.endGame = 1;
				}
		}
}
package interfaces;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import DataEntites.Sfondo;
import bubbleMaster.Start;
import dataButton.SimpleButton;
import dataObstacles.Bubble;
import dataObstacles.Ostacolo;
import dataObstacles.Player;
import dataObstacles.Sbarra;
import dataObstacles.Tubo;

public class Edit
{
	private Ostacolo temp;
	private int tempX, tempY;
	private SimpleButton saveLevel, chooseLevel, back;
	
	private int ray = 25;
	
	private ArrayList<Ostacolo> items;	
	private ArrayList<Ostacolo> ostacoli;
	
	private ArrayList<Sfondo> sfondi;
	
	private int gamer, ball = 0;
	
	private int indexSfondo = 3;
	
	private Image up, down;
	private int widthArrow, heightArrow;
	
	private Image choiseI, baseI;
	
	private boolean insertEditor;
	private Rectangle choise, base;
	private int widthChoise, heightChoise;
	private int widthBase, heightBase;
	
	public Edit( GameContainer gc ) throws SlickException
		{		
			double maxH = gc.getHeight()/(1.04), maxW = gc.getWidth();
			sfondi = new ArrayList<Sfondo>();
			sfondi.add( new Sfondo( new Image( "./data/Image/sfondo.png" ), maxH, maxW ) );
			sfondi.add( new Sfondo( new Image( "./data/Image/sfondo2.png" ), maxH, maxW ) );
			sfondi.add( new Sfondo( new Image( "./data/Image/sfondo3.jpg" ), maxH, maxW ) );
			sfondi.add( new Sfondo( new Image( "./data/Image/sfondo4.jpg" ), maxH, maxW ) );
			sfondi.add( new Sfondo( new Image( "./data/Image/sfondo6.jpg" ), maxH, maxW ) );
			
			up = new Image( "./data/Image/up.png" );
			down = new Image( "./data/Image/down.png" );
			widthArrow = gc.getWidth()/15;
			heightArrow = gc.getHeight()/40;

			back = new SimpleButton( gc.getWidth()/15, gc.getHeight()*17/18, "INDIETRO", Color.orange );
			saveLevel = new SimpleButton( gc.getWidth()*3/4, gc.getHeight()*17/18, "SALVA LIVELLO", Color.orange );
			chooseLevel = new SimpleButton( gc.getWidth()*2/5, gc.getHeight()*17/18, "SCEGLI LIVELLO", Color.orange );
			
			temp = null;
			
			items = new ArrayList<Ostacolo>();
			items.add( new Sbarra( gc.getWidth()/9, gc.getHeight()*78/100 ) );
			items.add( new Tubo( gc.getWidth()*2/7, gc.getHeight()*3/4, "sx" ) );
			items.add( new Tubo( gc.getWidth()*3/7, gc.getHeight()*3/4, "dx" ) );
			items.add( new Player( gc.getWidth()*4/7, gc.getHeight()*3/4, 0 ) );
			items.add( new Player( gc.getWidth()*5/7, gc.getHeight()*3/4, 1 ) );
			items.add( new Bubble( gc.getWidth()*6/7, gc.getHeight()*3/4, ray, maxW ) );
			
			ostacoli = new ArrayList<Ostacolo>();

			choiseI = new Image( "./data/Image/choise.png" );
			baseI = new Image( "./data/Image/base.png" );
			
			widthChoise = gc.getWidth()/8;
			heightChoise = gc.getHeight()/30;
			widthBase = (int) (gc.getWidth()/1.11);
			heightBase = (int) (gc.getHeight()/1.04);
			choise = new Rectangle( gc.getWidth()/2 - widthChoise/2, gc.getHeight() - heightChoise, widthChoise, heightChoise );
			base = new Rectangle( gc.getWidth()/2 - widthBase/2, gc.getHeight()/24, widthBase, heightBase );
			
			insertEditor = false;
		}
	
	public void draw( GameContainer gc, Graphics g ) throws SlickException
		{		
			sfondi.get( indexSfondo ).draw( gc );
						
			for(int i = 0; i < ostacoli.size(); i++)
				ostacoli.get( i ).draw( g );
			
			if(temp != null)
				temp.draw( g );
			
			back.draw( g );
			saveLevel.draw( g );
			chooseLevel.draw( g );
			
			if(insertEditor)
				{
					baseI.draw( base.getX(), base.getY(), base.getWidth(), base.getHeight() );
					for(int i = 0; i < items.size(); i++)
						items.get( i ).draw( g );
				}
			
			choiseI.draw( choise.getX(), choise.getY(), choise.getWidth(), choise.getHeight() );
			
			if(insertEditor)
				down.draw( choise.getX() + widthChoise/2 - widthArrow/2, choise.getY() + gc.getHeight()/200, widthArrow, heightArrow );
			else
				up.draw( choise.getX() + widthChoise/2 - widthArrow/2, choise.getY() + gc.getHeight()/200, widthArrow, heightArrow );
		}
	
	public void setChoise( GameContainer gc )
		{
			if(insertEditor)
				choise.setLocation( choise.getX(), base.getY() - heightChoise + gc.getWidth()/150 );
			else
				choise.setLocation( choise.getX(), gc.getHeight() - heightChoise );
		}
	
	public boolean checkPressed( int x, int y ) throws SlickException
		{
			if(insertEditor)
				{
					for(int i = 0; i < items.size(); i++)
						{
							Ostacolo item = items.get( i );
							if(item.contains( x, y ))
								{
									temp = item.clone();
									
									if(temp.ID.startsWith( "player" ))
										gamer++;
									else if(temp.ID.equals( "bolla" ))
										ball++;
									temp.setInsert( true, true );
									
									tempX = x;
									tempY = y;
									
									return true;
								}
						}
				}
			
			for(int i = 0; i < ostacoli.size(); i++)
				{
					if(ostacoli.get( i ).contains( x, y ))
						{
							temp = ostacoli.get( i );
							ostacoli.remove( i );
							temp.setInsert( true, true );
							
							tempX = x;
							tempY = y;
							
							return false;
						}
				}
			
			return false;
		}
	
	public void update( GameContainer gc )throws SlickException
		{
			Input input = gc.getInput();
			int mouseX = input.getMouseX();
			int mouseY = input.getMouseY();
			
			boolean collide = false;
			
			int winner = -1;
			
			if(temp != null)
				{
					if(temp.getY() + temp.getHeight() > sfondi.get( indexSfondo ).getMaxHeight())
						collide = true;
					else
						for(int i = 0; i < ostacoli.size(); i++)
							if(!temp.ID.startsWith( "player" ))
									{
										if(temp.component( "rect" ).intersects( ostacoli.get( i ).component( "rect" ) ))
											collide = true;
									}
							else if(!ostacoli.get( i ).ID.equals( "sbarra" ))
								{
									if(temp.component( "rect" ).intersects( ostacoli.get( i ).component( "rect" ) ))
										collide = true;
								}
							else if(ostacoli.get( i ).ID.equals( "sbarra" ))
								if(temp.component( "rect" ).intersects( ostacoli.get( i ).component( "latoGiu" ) ))
									collide = true;
					
					if(collide)
						temp.setInsert( false, false );
					else
						temp.setInsert( true, false );
				}
	
			if(input.isMousePressed( Input.MOUSE_LEFT_BUTTON ))
				{
					if(choise.contains( mouseX, mouseY ))
						{
							insertEditor = !insertEditor;
							setChoise( gc );
						}
					else if(back.checkClick( mouseX, mouseY ))
						{
							ostacoli.clear();
							
							Start.editGame = 0;
							Start.begin = 1;
						}
					else if(saveLevel.checkClick( mouseX, mouseY ))
						{
							if(temp == null)
								if(gamer > 0 && ball > 0)
									{
										Begin.livelli.add( new Livello( ostacoli, sfondi.get( indexSfondo ) ) );
										gamer = 0;
										ball = 0;
										
										ostacoli.clear();
									}
						}
					else if(chooseLevel.checkClick( mouseX, mouseY ))
						{
							if(temp == null)
								if(Begin.livelli.size() > 0)
									{
										Start.editGame = 0;
										Start.chooseLevel = 1;
									}
						}
					else
						{
							if(temp == null)
								{
									if(checkPressed( mouseX, mouseY ))
										{
											insertEditor = false;
											choise.setLocation( choise.getX(), gc.getHeight() - heightChoise );
										}
								}
							else if(!collide)
								{
									temp.setInsert( true, true );
									ostacoli.add( temp );
									temp = null;
								}
						}
				}
			
			if(input.isMousePressed( Input.MOUSE_RIGHT_BUTTON ) || input.isKeyPressed( Input.KEY_DELETE ))
				{
					if(temp != null)
						{
							if(temp.ID.equals( "bolla" ))
								ball = Math.max( ball - 1, 0);
							else if(temp.ID.startsWith( "player" ))
								gamer = Math.max( gamer - 1, 0 );
							ostacoli.remove( temp );
						}
					temp = null;
				}
			
			/*posizionamento del giocatore nel gioco*/
			if(temp != null)
				if(tempX != mouseX || tempY != mouseY)
					{					
						if(temp.ID.startsWith( "player" ))
							{
								double tmp = gc.getHeight();
								for(int i = 0; i < ostacoli.size(); i++)
									if(mouseY < ostacoli.get( i ).getY())
										if(mouseX > ostacoli.get( i ).getX() && mouseX < ostacoli.get( i ).getX() + ostacoli.get( i ).getWidth())
											if(Math.abs( mouseY - ostacoli.get( i ).getY() ) < tmp)
												{
													tmp = Math.abs( mouseY - ostacoli.get( i ).getY() );
													winner = i;
												}
								
								if(winner == -1)
									temp.setXY( mouseX, (int) (sfondi.get( indexSfondo ).getMaxHeight() - temp.getHeight()), "restore" );
								else
									temp.setXY( mouseX, (int) (ostacoli.get( winner ).getY() - temp.getHeight()), "restore" );
							}
	
						else
							temp.setXY( mouseX - tempX, mouseY - tempY, "move" );
						
						if(temp.getX() <= 0)
							temp.setXY( 0, temp.getY(), "restore" );
						else if(temp.ID.equals( "bolla" ))
							{
								if(temp.getX() + 2*temp.getWidth() >= gc.getWidth())
									temp.setXY( gc.getWidth() - 2 * (int) temp.getWidth(), temp.getY(), "restore" );
							}
						else if(temp.getX() + temp.getWidth() >= gc.getWidth())
							temp.setXY( gc.getWidth() - (int) temp.getWidth(), temp.getY(), "restore" );
						
						if(temp.getY() <= 0)
							temp.setXY( temp.getX(), 0, "restore" );						
						
						tempX = mouseX;
						tempY = mouseY;
					}
		}	
}
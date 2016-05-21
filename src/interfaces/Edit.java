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
	private ArrayList<SimpleButton> buttons;
	
	private ArrayList<Sfondo> sfondi;
	
	private int gamer, ball = 0;
	
	private int indexSfondo = 3;
	
	private Image up, down;
	private int widthArrow, heightArrow;
	
	private Image choiseI, baseI;
	private Image cursor;
	
	private boolean showCursor;
	private int indexCursor, indexCursorButton;
	private int widthC, heightC;
	
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

			chooseLevel = new SimpleButton( gc.getWidth()/15, gc.getHeight()*24/25, "SCEGLI LIVELLO", Color.orange );
			back = new SimpleButton( 0, gc.getHeight()*24/25, "INDIETRO", Color.orange );
			saveLevel = new SimpleButton( gc.getWidth()*3/4, gc.getHeight()*24/25, "SALVA LIVELLO", Color.orange );
			
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
			cursor = new Image( "./data/Image/cursore.png" );
			
			widthC = 45;
			heightC = 25;
			
			widthChoise = gc.getWidth()/8;
			heightChoise = gc.getHeight()/30;
			widthBase = (int) (gc.getWidth()/1.11);
			heightBase = (int) (gc.getHeight()/1.04);
			choise = new Rectangle( gc.getWidth()/2 - widthChoise/2, gc.getHeight() - heightChoise, widthChoise, heightChoise );
			base = new Rectangle( gc.getWidth()/2 - widthBase/2, gc.getHeight()/24, widthBase, heightBase );
			
			insertEditor = false;
			indexCursor = -1;
			indexCursorButton = -1;
			
			buttons = new ArrayList<SimpleButton>();
			
			buttons.add( chooseLevel );
			buttons.add( back );
			buttons.add( saveLevel );
		}
	
	public void draw( GameContainer gc, Graphics g ) throws SlickException
		{		
			sfondi.get( indexSfondo ).draw( gc );
						
			for(int i = 0; i < ostacoli.size(); i++)
				ostacoli.get( i ).draw( g );
			
			if(temp != null)
				temp.draw( g );
			
			for(int i = 0; i < buttons.size(); i++)
				buttons.get( i ).draw( g );
			
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
			
			if(showCursor)
				if(insertEditor)
					cursor.draw( items.get( indexCursor ).getX() - widthC, items.get( indexCursor ).getY(), widthC, heightC );	
				else if(indexCursorButton >= 0)
					cursor.draw( buttons.get( indexCursorButton ).getX() - widthC, buttons.get( indexCursorButton ).getY(), widthC, heightC );
				else if(ostacoli.size() >= 0)
					cursor.draw( ostacoli.get( indexCursor ).getX() - widthC, ostacoli.get( indexCursor ).getY(), widthC, heightC );
		}
	
	public void setChoise( GameContainer gc )
		{
			if(insertEditor)
				choise.setLocation( choise.getX(), base.getY() - heightChoise + gc.getWidth()/150 );
			else
				choise.setLocation( choise.getX(), gc.getHeight() - heightChoise );
		}
	
	public boolean checkPressed( int x, int y, GameContainer gc, String type ) throws SlickException
		{
			if(insertEditor)
				{
					if(type.equals( "keyboard" ))
						{
							temp = items.get( indexCursor ).clone();
							if(temp.ID.startsWith( "player" ))
								gamer++;
							else if(temp.ID.equals( "bolla" ))
								ball++;
							temp.setInsert( true, true );
							
							indexCursor = -1;
							indexCursorButton = -1;
							showCursor = false;
							insertEditor = false;
							
							tempX = gc.getInput().getMouseX();
							tempY = gc.getInput().getMouseY();
							
							return true;
						}
					else
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

										indexCursor = -1;
										indexCursorButton = -1;
										showCursor = false;
										insertEditor = false;
										
										return true;
									}
							}
				}
			else
				{
					if(type.equals( "keyboard" ))
						{
							temp = ostacoli.get( indexCursor );
							ostacoli.remove( indexCursor );
							temp.setInsert( true, true );
							
							tempX = gc.getInput().getMouseX();
							tempY = gc.getInput().getMouseY();
						}
					else
						for(int i = 0; i < ostacoli.size(); i++)
							{
								if(ostacoli.get( i ).contains( x, y ))
									{
										temp = ostacoli.get( i );
										ostacoli.remove( i );
										temp.setInsert( true, true );
										
										tempX = x;
										tempY = y;
									}
							}
				}
			
			indexCursor = -1;
			indexCursorButton = -1;
			
			showCursor = false;
			
			return false;
		}
	
	public void update( GameContainer gc )throws SlickException
		{
			Input input = gc.getInput();
			int mouseX = input.getMouseX();
			int mouseY = input.getMouseY();
			int move = 2;
			
			boolean collide = false, fall = false;
			int stay = -1;			

			if((indexCursorButton == 1 && input.isKeyPressed( Input.KEY_ENTER )) || (back.checkClick( mouseX, mouseY ) && input.isMousePressed( Input.MOUSE_LEFT_BUTTON )))
				{
					indexCursor = -1;
					indexCursorButton = -1;
					showCursor = false;
					ostacoli.clear();
					temp = null;
					Start.editGame = 0;
					Start.recoverPreviousStats();
				}
			else if(input.isKeyPressed( Input.KEY_ESCAPE ))
				{
					indexCursor = -1;
					indexCursorButton = -1;
					showCursor = false;
					ostacoli.clear();
					temp = null;
					Start.editGame = 0;
					Start.begin = 1;
				}
			
			if(temp != null)
				{
					if((temp.ID.equals( "bolla" ) && temp.getY() + temp.getHeight()*2 > sfondi.get( indexSfondo ).getMaxHeight())
					|| (!temp.ID.equals( "bolla" ) && temp.getY() + temp.getHeight() > sfondi.get( indexSfondo ).getMaxHeight()))
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

					/*controlla che il personaggio non sia posizionato a mezz'aria*/
					if(temp.ID.startsWith( "player" ))
						if(temp.getY() + temp.getHeight() < sfondi.get( indexSfondo ).getMaxHeight() - 1)
							for(int i = 0; i < ostacoli.size(); i++)
								if(temp.component( "rect" ).intersects( ostacoli.get( i ).component( "rect" ) ))
									{
										stay = i;
										break;
									}
					/*posizionamento degli oggetti nel gioco*/
					if(input.isKeyDown( Input.KEY_RIGHT ))
						temp.setXY( move, 0, "move" );
					if(input.isKeyDown( Input.KEY_LEFT ))
						temp.setXY( -move, 0, "move" );
					if(stay == -1 || (!temp.component( "rect" ).intersects( ostacoli.get( stay ).component( "rect" ) ) && temp.ID.startsWith( "player" )))
						fall = true;
					if(input.isKeyPressed( Input.KEY_UP ) && temp.ID.startsWith( "player" ))
						{
							int tmp = gc.getHeight(), win = -1;
							for(int i = 0; i < ostacoli.size(); i++)
								if(ostacoli.get( i ).getY() < temp.getY())
									if(!(temp.getX() > ostacoli.get( i ).getMaxX() || temp.getMaxX() < ostacoli.get( i ).getX()))
										if(temp.getY() - ostacoli.get( i ).getY() < tmp)
											{
												tmp = temp.getY() - ostacoli.get( i ).getY();
												win = i;
											}							
							if(win >= 0)
								temp.setXY( temp.getX(), ostacoli.get( win ).getY() - (int) temp.getHeight(), "restore" );
						}
					else if(input.isKeyDown( Input.KEY_UP ))
						if(!temp.ID.startsWith( "player" ))								
							temp.setXY( 0, -move, "move" );
					if((input.isKeyPressed( Input.KEY_DOWN ) || fall) && temp.ID.startsWith( "player" ))
						{
							int tmp = gc.getHeight(), win = -1;
							for(int i = 0; i < ostacoli.size(); i++)
								if(i != stay)
									if(ostacoli.get( i ).getY() > temp.getY())
										if(!(temp.getX() > ostacoli.get( i ).getMaxX() || temp.getMaxX() < ostacoli.get( i ).getX()))
											if(Math.abs(temp.getY() - ostacoli.get( i ).getY()) < tmp)
												{
													tmp = Math.abs(temp.getY() - ostacoli.get( i ).getY());
													win = i;
												}							
							if(win >= 0)
								temp.setXY( temp.getX(), ostacoli.get( win ).getY() - (int) temp.getHeight(), "restore" );
							else
								temp.setXY( temp.getX(), (int) (sfondi.get( indexSfondo ).getMaxHeight() - temp.getHeight()), "restore" );
						}
					else if(input.isKeyDown( Input.KEY_DOWN ))
						if(!temp.ID.startsWith( "player" ))
							temp.setXY( 0, move, "move" );
					/*spostamento oggetto tramite mouse*/
					if(mouseX != tempX || mouseY != tempY)	
						{
							showCursor = false;
							temp.setXY( mouseX - (int) temp.getWidth()/2, mouseY - (int) temp.getHeight()/2, "restore" );		
							if(temp.ID.startsWith( "player" ))
								{
									double tmp = gc.getHeight();
									int winner = -1;
									for(int i = 0; i < ostacoli.size(); i++)
										if(mouseY < ostacoli.get( i ).getY())
											if(!(mouseX + temp.getWidth()/2 < ostacoli.get( i ).getX() || mouseX - temp.getWidth()/2 > ostacoli.get( i ).getMaxX()))
												if(Math.abs( mouseY - ostacoli.get( i ).getY() ) < tmp)
													{
														tmp = Math.abs( mouseY - ostacoli.get( i ).getY() );
														winner = i;
													}
									
									if(winner == -1)
										temp.setXY( mouseX - (int) temp.getWidth()/2, (int) (sfondi.get( indexSfondo ).getMaxHeight() - temp.getHeight()), "restore" );
									else
										temp.setXY( mouseX - (int) temp.getWidth()/2, (int) (ostacoli.get( winner ).getY() - temp.getHeight()), "restore" );
								}
						}
					
					/*controllo estremi dello schermo*/
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
					
					/*cancellazione oggetti del gioco*/
					if(input.isMousePressed( Input.MOUSE_RIGHT_BUTTON ) || input.isKeyPressed( Input.KEY_DELETE ))
						{
							if(temp.ID.equals( "bolla" ))
								ball = Math.max( ball - 1, 0);
							else if(temp.ID.startsWith( "player" ))
								gamer = Math.max( gamer - 1, 0 );
							ostacoli.remove( temp );
							
							temp = null;
						}
					/*inserimento oggetto nel gioco*/
					else if(input.isMousePressed( Input.MOUSE_LEFT_BUTTON )|| input.isKeyPressed( Input.KEY_ENTER ))
						{
							if(!collide)
								{
									indexCursor = -1;
									temp.setInsert( true, true );
									ostacoli.add( temp );
									temp = null;
								}
						}
				}
			
			else if(temp == null)
				{				
					if(showCursor && input.isKeyPressed( Input.KEY_RIGHT ) && (indexCursor >= 0 || indexCursorButton >= 0))
						{
							if(insertEditor)
								indexCursor = (++indexCursor)%items.size();							
							else
								{
									if(indexCursor >= 0)
										{
											if(++indexCursor == ostacoli.size())
												{
													indexCursor = -1;
													indexCursorButton = 0;
												}
										}
									else if(++indexCursorButton == buttons.size())
										if(ostacoli.size() > 0)
											{
												indexCursor = 0;
												indexCursorButton = -1;
											}
										else
											indexCursorButton = 0;
								}
							
						}
					if(showCursor && input.isKeyPressed( Input.KEY_LEFT ) && (indexCursor >= 0 || indexCursorButton >= 0))
						{
							if(insertEditor)
								{
									if(--indexCursor < 0)
										indexCursor = items.size() - 1;
								}
							else
								{
									if(indexCursor >= 0)
										{
											if(--indexCursor < 0)
												indexCursorButton = buttons.size() - 1;
										}
									else if(--indexCursorButton < 0)
										if(ostacoli.size() > 0)
											indexCursor = ostacoli.size() - 1;
										else
											indexCursorButton = buttons.size() - 1;
								}							
						}
					else if((choise.contains( mouseX, mouseY ) && !insertEditor && input.isMousePressed( Input.MOUSE_LEFT_BUTTON )) || input.isKeyPressed( Input.KEY_UP ))
						{
							indexCursor = -1;
							indexCursorButton = -1;
							showCursor = false;
							insertEditor = true;
							setChoise( gc );
						}
					else if(!showCursor && (input.isKeyPressed( Input.KEY_LEFT ) || input.isKeyPressed( Input.KEY_UP ) || input.isKeyPressed( Input.KEY_DOWN ) || input.isKeyPressed( Input.KEY_RIGHT )))
						{
							showCursor = true;
							if(insertEditor)
								indexCursor = 0;
							else if(ostacoli.size() > 0)
								{
									indexCursor = 0;
									indexCursorButton = -1;
								}
							else
								{
									indexCursor = -1;
									indexCursorButton = 0;
								}
						}
					else if(input.isKeyPressed( Input.KEY_DOWN ) || (choise.contains( mouseX, mouseY ) && input.isMousePressed( Input.MOUSE_LEFT_BUTTON )))
						{
							indexCursor = -1;
							indexCursorButton = -1;
							showCursor = false;
							insertEditor = false;					
							setChoise( gc );
						}
					else if((indexCursorButton == 2 && input.isKeyPressed( Input.KEY_ENTER )) || (saveLevel.checkClick( mouseX, mouseY ) && input.isMousePressed( Input.MOUSE_LEFT_BUTTON )))
						{
							if(!insertEditor)
								if(gamer > 0 && ball > 0)
									{
										Begin.livelli.add( new Livello( ostacoli, sfondi.get( indexSfondo ) ) );
										gamer = 0;
										ball = 0;
										
										ostacoli.clear();
									}
						}
					else if((indexCursorButton == 0 && input.isKeyPressed( Input.KEY_ENTER )) || (chooseLevel.checkClick( mouseX, mouseY ) && input.isMousePressed( Input.MOUSE_LEFT_BUTTON )))
						{
							if(!insertEditor)
								if(Begin.livelli.size() > 0 && temp == null)
									{
										indexCursor = -1;
										indexCursorButton = -1;
										showCursor = false;
										ostacoli.clear();
										Start.editGame = 0;
										Start.chooseLevel = 1;
										Start.setPreviuosStats( "editGame" );
									}
						}
					else if(input.isMousePressed( Input.MOUSE_LEFT_BUTTON ))
						{
							if(checkPressed( mouseX, mouseY, gc, "mouse" ))
								choise.setLocation( choise.getX(), gc.getHeight() - heightChoise );
						}
					else if(input.isKeyPressed( Input.KEY_ENTER ))
						if(checkPressed( mouseX, mouseY, gc, "keyboard" ))
							choise.setLocation( choise.getX(), gc.getHeight() - heightChoise );
				}
		}
}
package interfaces;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.jdom2.Comment;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
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
	
	private SimpleButton saveLevel, back;
	//bottoni di inserimento elementi
	//private SimpleButton obstacles, players, spheres;
	
	//il raggio delle sfere
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
	
	//indice relativo alla posizione del cursore
	private int indexCursor, indexCursorButton, indexCursorSfondi;
	private int widthC, heightC;
	
	private boolean insertEditor, insertItem;
	/**base -> la finestra di selezione sfondo/elemento*/
	private Rectangle choise, base;
	private int widthChoise, heightChoise;
	private int widthBase, heightBase;
	
	private int minHighEditor;
	
	//elementi riguardanti la scrittura su file .xml
	private Element livello;
	private Document document;
	
	//determina se stiamo inserendo una NUOVA coppia di tubi
	private boolean nuovaCoppiaTubi, nuovoTubo1;
	
	//salva il valore del tubo rimanente
	private int indiceTuboRimasto;
	
	public Edit( GameContainer gc ) throws SlickException
		{		
			//TODO voglio provare a caricare tutti questi dati tramite file .xml (se mi riesce e ho tempo, senno' pazienza e lascio cosi')
		
			double maxH = gc.getHeight()/(1.04), maxW = gc.getWidth();
			sfondi = new ArrayList<Sfondo>();
			sfondi.add( new Sfondo( new Image( "./data/Image/sfondo.png" ), maxH, maxW, gc.getWidth()/8, gc.getHeight()/2, gc.getHeight()/10, gc.getWidth()/20 ) );
			sfondi.add( new Sfondo( new Image( "./data/Image/sfondo2.png" ), maxH, maxW, gc.getWidth()*29/100, gc.getHeight()/2, gc.getHeight()/10, gc.getWidth()/20 ) );
			sfondi.add( new Sfondo( new Image( "./data/Image/sfondo3.jpg" ), maxH, maxW, gc.getWidth()*46/100, gc.getHeight()/2, gc.getHeight()/10, gc.getWidth()/20 ) );
			sfondi.add( new Sfondo( new Image( "./data/Image/sfondo4.jpg" ), maxH, maxW, gc.getWidth()*63/100, gc.getHeight()/2, gc.getHeight()/10, gc.getWidth()/20 ) );
			sfondi.add( new Sfondo( new Image( "./data/Image/sfondo6.jpg" ), maxH, maxW, gc.getWidth()*8/10, gc.getHeight()/2, gc.getHeight()/10, gc.getWidth()/20 ) );
			
			up = new Image( "./data/Image/up.png" );
			down = new Image( "./data/Image/down.png" );
			widthArrow = gc.getWidth()/15;
			heightArrow = gc.getHeight()/40;

			back = new SimpleButton( gc.getWidth()/15, gc.getHeight()*24/25, "INDIETRO", Color.orange );
			saveLevel = new SimpleButton( gc.getWidth()*3/4, gc.getHeight()*24/25, "SALVA LIVELLO", Color.orange );
			
			/*obstacles = new SimpleButton( gc.getWidth()*8/9, gc.getHeight()/25, "Ostacoli", Color.green );
			players = new SimpleButton( gc.getWidth()*7/8, gc.getHeight()/11, "Giocatori", Color.green );
			spheres = new SimpleButton( gc.getWidth()*11/12, gc.getHeight()*10/71, "Sfere", Color.green );*/
			
			temp = null;
			
			items = new ArrayList<Ostacolo>();
			items.add( new Sbarra( gc.getWidth()/9, gc.getHeight()*78/100, "hor" ) );
			items.add( new Tubo( gc.getWidth()*2/7, gc.getHeight()*3/4, "sx" ) );
			items.add( new Player( gc.getWidth()*4/7, gc.getHeight()*3/4, 0 ) );
			items.add( new Player( gc.getWidth()*5/7, gc.getHeight()*3/4, 1 ) );
			items.add( new Bubble( gc.getWidth()*6/7, gc.getHeight()*3/4, ray, maxW ) );
			
			ostacoli = new ArrayList<Ostacolo>();

			choiseI = new Image( "./data/Image/choise.png" );
			baseI = new Image( "./data/Image/Window.png" );
			cursor = new Image( "./data/Image/cursore.png" );
			
			//lunghezza e altezza del cursore
			widthC = 45;
			heightC = 25;
			
			widthChoise = gc.getWidth()/8;
			heightChoise = gc.getHeight()/30;
			//lunghezza e altezza della base di selezione elementi
			widthBase = (int) (gc.getWidth()/1.11);
			heightBase = 0;
			choise = new Rectangle( gc.getWidth()/2 - widthChoise/2, gc.getHeight() - heightChoise, widthChoise, heightChoise );
			
			base = new Rectangle( gc.getWidth()/2 - widthBase/2, gc.getHeight(), widthBase, heightBase );
			
			insertEditor = false;
			indexCursor = -1;
			indexCursorButton = -1;
			indexCursorSfondi = -1;
			
			insertItem = false;
			
			buttons = new ArrayList<SimpleButton>();
			
			buttons.add( back );
			buttons.add( saveLevel );
			/*buttons.add( obstacles );
			buttons.add( players );
			buttons.add( spheres );*/
			
			minHighEditor = gc.getHeight() - (int) (gc.getHeight()/1.34);
			
			nuovaCoppiaTubi = false;
			nuovoTubo1 = false;
			indiceTuboRimasto = -1;
		}
	
	public void draw( GameContainer gc, Graphics g ) throws SlickException
		{		
			sfondi.get( indexSfondo ).draw( gc );
						
			for(int i = 0; i < ostacoli.size(); i++)
				{
					ostacoli.get( i ).draw( g );
					if(ostacoli.get( i ).getID().equals( "tubo" ))
						{
							Ostacolo tube = ostacoli.get( i );
							
							if(tube.getUnion() == -1)
								g.drawGradientLine( tube.getMidArea().getX(), tube.getMidArea().getY(), Color.red, temp.getMidArea().getX(), temp.getMidArea().getY(), Color.red );
							else
								g.drawGradientLine( tube.getMidArea().getX(), tube.getMidArea().getY(), Color.red, ostacoli.get( tube.getUnion() ).getMidArea().getX(), ostacoli.get( tube.getUnion() ).getMidArea().getY(), Color.red );
						}
				}
			
			for(int i = 0; i < buttons.size(); i++)
				buttons.get( i ).draw( g );

			baseI.draw( base.getX(), base.getY(), base.getWidth(), heightBase );

			if(insertItem)
				for(int i = 0; i < items.size(); i++)
					{
						items.get( i ).draw( g );
						
						for(int j = 0; j < sfondi.size(); j++)
							sfondi.get( j ).draw();
					}
			
			choiseI.draw( choise.getX(), choise.getY(), choise.getWidth(), choise.getHeight() );
			
			if(insertEditor)
				down.draw( choise.getX() + widthChoise/2 - widthArrow/2, choise.getY() + gc.getHeight()/200, widthArrow, heightArrow );
			else
				up.draw( choise.getX() + widthChoise/2 - widthArrow/2, choise.getY() + gc.getHeight()/200, widthArrow, heightArrow );
			
			if(indexCursor >= 0 || indexCursorButton >= 0)
				if(insertEditor)
					cursor.draw( items.get( indexCursor ).getX() - widthC, items.get( indexCursor ).getY(), widthC, heightC );	
				else if(indexCursorButton >= 0)
					cursor.draw( buttons.get( indexCursorButton ).getX() - widthC, buttons.get( indexCursorButton ).getY(), widthC, heightC );
				else if(ostacoli.size() >= 0)
					cursor.draw( ostacoli.get( indexCursor ).getX() - widthC, ostacoli.get( indexCursor ).getY(), widthC, heightC );
			
			if(indexCursorSfondi >= 0)
				cursor.draw( sfondi.get( indexCursorSfondi ).getX() - widthC, sfondi.get( indexCursorSfondi ).getY(), widthC, heightC );
			
			if(temp != null)
				temp.draw( g );
		}
	
	public void setChoise( GameContainer gc )
		{ choise.setLocation( choise.getX(), base.getY() - heightChoise + gc.getWidth()/150 ); }
	
	public void aggiornaIndiciTubi( int i )
		{
			for(int j = 0; j < ostacoli.size(); j++)
				if(ostacoli.get( j ).getID().equals( "tubo" ) && ostacoli.get( j ).getUnion() > i)
					ostacoli.get( j ).setUnion( ostacoli.get( j ).getUnion() - 1 );
		}
	/**resetta indexCursor, indexCursorButton e indexCursorSfondi*/
	public void resetIndexCursor()
		{
			indexCursor = -1;
			indexCursorButton = -1;
			indexCursorSfondi = -1;
		}
	
	public boolean checkPressed( int x, int y, GameContainer gc, String type ) throws SlickException
		{
			// TODO IMPLEMENTARE SCELTA DI UNO SFONDO
		
			//se e' stato scelto un elemento nuovo da inserire
			if(insertEditor)
				{
					//inserimento da tastiera
					if(type.equals( "keyboard" ))
						{
							if(indexCursor >= 0)
								{
									temp = items.get( indexCursor ).clone();
									if(temp.getID().equals( "tubo" ))
										{
											nuovaCoppiaTubi = true;
											nuovoTubo1 = true;
										}
									if(temp.getID().startsWith( "player" ))
										gamer++;
									else if(temp.getID().equals( "bolla" ))
										ball++;
									temp.setInsert( true, true );
									
									indexCursor = -1;
									indexCursorButton = -1;
									insertEditor = false;									

									tempX = gc.getInput().getMouseX();
									tempY = gc.getInput().getMouseY();
									
									return true;
								}
							else if(indexCursorSfondi >= 0)
								{
									indexSfondo = indexCursorSfondi;
								
									/*tempX = gc.getInput().getMouseX();
									tempY = gc.getInput().getMouseY();*/
								
									return true;
								}
						}
					//inserimento tramite click del mouse
					else
						{
							System.out.println( "ecchime" );
							for(int i = 0; i < items.size(); i++)
								{
									Ostacolo item = items.get( i );
									if(item.contains( x, y ))
										{
											temp = item.clone();
											//sto inserendo una nuova coppia di tubi
											if(temp.getID().equals( "tubo" ))
												{
													nuovaCoppiaTubi = true;
													nuovoTubo1 = true;
												}
											
											if(temp.getID().startsWith( "player" ))
												gamer++;
											else if(temp.getID().equals( "bolla" ))
												ball++;
											temp.setInsert( true, true );
											
											tempX = x;
											tempY = y;
											
											resetIndexCursor();
											insertEditor = false;
											
											return true;
										}
								}
							for(int i = 0; i < sfondi.size(); i++)
								{
									if(sfondi.get( i ).contains( x, y ))
										{
											indexSfondo = i;
											
											return true;
										}
								}
						}
				}
			//se e' stato scelto un elemento gia' presente nel gioco
			else
				{
					if(type.equals( "keyboard" ) && indexCursor >= 0)
						{
							temp = ostacoli.get( indexCursor );
							//modifica la posizione di un tubo gia' esistente
							if(temp.getID().equals( "tubo" ))
								{											
									ostacoli.get( temp.getUnion() ).setUnion( - 1 );
									
									if(temp.getUnion() > indexCursor)											
										indiceTuboRimasto = temp.getUnion() - 1;
									else
										indiceTuboRimasto = temp.getUnion();												
								}
							//sistema gli indici dei tubi puntati
							aggiornaIndiciTubi( indexCursor );
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
										//modifica la posizione di un tubo gia' esistente
										if(temp.getID().equals( "tubo" ))
											{											
												ostacoli.get( temp.getUnion() ).setUnion( - 1 );
												
												if(temp.getUnion() > i)											
													indiceTuboRimasto = temp.getUnion() - 1;
												else
													indiceTuboRimasto = temp.getUnion();												
											}
										//sistema gli indici dei tubi puntati
										aggiornaIndiciTubi( i );
													
										ostacoli.remove( i );
										temp.setInsert( true, true );
										
										tempX = x;
										tempY = y;
									}
							}
				}
			
			indexCursor = -1;
			indexCursorButton = -1;
			
			return false;
		}
	
	//resetta tutte le variabili e il vettore degi ostacoli del livello
	public void resetStatus()
		{
			ostacoli.clear();
			indexCursor = -1;
			indexCursorButton = -1;
			insertEditor = false;
			temp = null;
		}
	
	private void addNewLevel()
		{	    
			// TODO AGGIUNGERE TEXTBOX PER DECIDERE IL NOME DEL LIVELLO
		
			try
			{
			    livello = new Element( "level" );
			    document = new Document( livello );
			    
			    XMLOutputter outputter = new XMLOutputter();
				// imposta un bel formato all'outputter 
				outputter.setFormat( Format.getPrettyFormat() );
				// creazione del file xml con il nome scelto
	
				Element item;
			    livello.addContent( new Comment( "Objects" ) );
			    for(int i = 0; i < ostacoli.size(); i++)
			    	{									    			
	    				item = new Element( "ostacolo" );
	    				item.setAttribute( "x", ostacoli.get( i ).getX() + "" );
	    				item.setAttribute( "y", ostacoli.get( i ).getY() + "" );
	    				if(ostacoli.get( i ).getOrienting() != null)
	    				    item.setAttribute( "type", ostacoli.get( i ).getOrienting() );
	    				else
	    				    item.setAttribute( "type", "null" );
	    				item.setAttribute( "ID", ostacoli.get( i ).getID() );
	    				livello.addContent( item );
			    	}
				
	    		item = new Element( "sfondo" );
	    		item.setAttribute( "index", indexSfondo + "" );
	    		livello.addContent( item );
	    		
	    		outputter.output( document, new FileOutputStream( "data/livelli/livello10.xml" ) );
			}
			catch( IOException e ){
				System.err.println( "Error while creating the level" );
				e.printStackTrace(); 
			}
		
			Begin.livelli.add( new Livello( ostacoli, sfondi.get( indexSfondo ) ) );
		}
	
	public void setEditor( int delta, GameContainer gc )
	    {
    	    if(insertEditor)
                if(base.getY() - delta/2 > minHighEditor)
                    {
                        base.setY( base.getY() - delta/2 );
                        heightBase = heightBase + delta/2;
                    }
                else
                    {
                        insertItem = true;
                        base.setY( minHighEditor );
                        heightBase = gc.getHeight() - minHighEditor;
                    }
            else
                {
                    insertItem = false;
                    base.setY( gc.getHeight() );
                    heightBase = 0;
                }
            setChoise( gc );
	    }
	
	public void update( GameContainer gc, int delta )throws SlickException
		{
			Input input = gc.getInput();
			int mouseX = input.getMouseX();
			int mouseY = input.getMouseY();
			int move = 2;
			
			boolean collide = false, fall = false;
			//determina se il personaggio "tocca" un oggetto del livello o il pavimento
			int stay = -1;
			
			// aggiornamento altezza editor
			setEditor( delta, gc );

			if((indexCursorButton == 1 && input.isKeyPressed( Input.KEY_ENTER )) || (back.checkClick( mouseX, mouseY ) && input.isMousePressed( Input.MOUSE_LEFT_BUTTON )))
				{
					resetStatus();
					Start.editGame = 0;
					Start.recoverPreviousStats();
				}
			else if(input.isKeyPressed( Input.KEY_ESCAPE ))
				{
					resetStatus();
					Start.editGame = 0;
					Start.begin = 1;
				}
			
			if(temp != null)
				{
			        //controlla se un l'oggetto da inserire non superi i confini dello schermo di gioco
					if((temp.getID().equals( "bolla" ) && temp.getY() + temp.getHeight()*2 > sfondi.get( indexSfondo ).getMaxHeight())
					|| (!temp.getID().equals( "bolla" ) && temp.getY() + temp.getHeight() > sfondi.get( indexSfondo ).getMaxHeight()))
						collide = true;
					else
						for(int i = 0; i < ostacoli.size(); i++)
						    if(temp.getID().startsWith( "player" ))
						        {
						            if(!ostacoli.get( i ).getID().equals( "sbarra" ) && !ostacoli.get( i ).getID().equals( "tubo" ))
						                {
    						                if(temp.component( "rect" ).intersects( ostacoli.get( i ).component( "rect" ) ))
    						                    collide = true;
						                }
						            else if(temp.component( "rect" ).intersects( ostacoli.get( i ).component( "latoGiu" ) ))
                                        collide = true;
						        }
						    else if(temp.component( "rect" ).intersects( ostacoli.get( i ).component( "rect" ) ))
						        collide = true;
					
					if(collide)
						temp.setInsert( false, false );
					else
						temp.setInsert( true, false );

					if(!temp.getID().equals( "bolla" ) && !temp.getID().startsWith( "player" ))
					    if(input.isKeyPressed( Input.KEY_SPACE ))
					        temp.setOrienting();

					/*controlla che il personaggio non sia posizionato a mezz'aria*/
					if(temp.getID().startsWith( "player" ))
						if(temp.getY() + temp.getHeight() < sfondi.get( indexSfondo ).getMaxHeight() - 1)
							for(int i = 0; i < ostacoli.size(); i++)
								if(temp.component( "rect" ).intersects( ostacoli.get( i ).component( "rect" ) ))
									{
										stay = i;
										break;
									}
					/*posizionamento e spostamento degli oggetti nel gioco*/
					if(input.isKeyDown( Input.KEY_RIGHT ))
						temp.setXY( move, 0, "move" );
					if(input.isKeyDown( Input.KEY_LEFT ))
						temp.setXY( -move, 0, "move" );
					if(stay == -1 || (!temp.component( "rect" ).intersects( ostacoli.get( stay ).component( "rect" ) ) && temp.getID().startsWith( "player" )))
						fall = true;
					if(input.isKeyPressed( Input.KEY_UP ) && temp.getID().startsWith( "player" ))
						{
							float tmp = gc.getHeight();
							int win = -1;
							for(int i = 0; i < ostacoli.size(); i++)
								if(ostacoli.get( i ).getY() < temp.getY())
									if(!(temp.getX() > ostacoli.get( i ).getMaxX() || temp.getMaxX() < ostacoli.get( i ).getX()))
										if(temp.getY() - ostacoli.get( i ).getY() < tmp)
											{
												tmp = temp.getY() - ostacoli.get( i ).getY();
												win = i;
											}							
							if(win >= 0)
								temp.setXY( temp.getX(), ostacoli.get( win ).getY() - temp.getHeight(), "restore" );
						}
					else if(input.isKeyDown( Input.KEY_UP ))
						if(!temp.getID().startsWith( "player" ))								
							temp.setXY( 0, -move, "move" );
					if((input.isKeyPressed( Input.KEY_DOWN ) || fall) && temp.getID().startsWith( "player" ))
						{
							float tmp = gc.getHeight();
							int win = -1;
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
								temp.setXY( temp.getX(), ostacoli.get( win ).getY() - temp.getHeight(), "restore" );
							else
								temp.setXY( temp.getX(), (int) (sfondi.get( indexSfondo ).getMaxHeight() - temp.getHeight()), "restore" );
						}
					else if(input.isKeyDown( Input.KEY_DOWN ))
						if(!temp.getID().startsWith( "player" ))
							temp.setXY( 0, move, "move" );
					/*spostamento oggetto tramite mouse*/
					if(mouseX != tempX || mouseY != tempY)	
						{
							indexCursor = -1;
							indexCursorButton = -1;
							indexCursorSfondi = -1;
							temp.setXY( mouseX - (int) temp.getWidth()/2, mouseY - (int) temp.getHeight()/2, "restore" );		
							if(temp.getID().startsWith( "player" ))
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
					else if(temp.getID().equals( "bolla" ))
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
							if(temp.getID().equals( "bolla" ))
								ball = Math.max( ball - 1, 0);
							else if(temp.getID().startsWith( "player" ))
								gamer = Math.max( gamer - 1, 0 );
							
							//se il tubo ha un'altro tubo collegato, elimina anche il collegamento
							for(int i = 0; i < ostacoli.size(); i++)
								if(ostacoli.get( i ).getID().equals( "tubo"))
									if(ostacoli.get( i ).getUnion() == -1)
										{
											aggiornaIndiciTubi( i );
											ostacoli.remove( i );
										}

							
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
									temp.setSpigoli();

									if(temp.getID().equals( "tubo" ))
									    {
											//inserisce una nuova coppia di tubi
											if(nuovaCoppiaTubi)
												{
													if(nuovoTubo1)
														{
															Ostacolo temp2 = temp.clone();
															
															temp = temp2;
															nuovoTubo1 = false;
														}
													else
														{
															//setto i nuovi indici dei tubi puntati
															ostacoli.get( ostacoli.size() - 2 ).setUnion( ostacoli.size() - 1 );
															ostacoli.get( ostacoli.size() - 1 ).setUnion( ostacoli.size() - 2 );
															temp = null;
															nuovaCoppiaTubi = false;
														}
												}
											//inserisco un elemento gia' esistente
											else
												{
													ostacoli.get( indiceTuboRimasto ).setUnion( ostacoli.size() - 1 );
													ostacoli.get( ostacoli.size() - 1 ).setUnion( indiceTuboRimasto );
												
													temp = null;
												}
									    }
									else
										temp = null;
								}
						}
				}
			//se NON ho cliccato su un elemento da inserire
			else if(temp == null)
				{				
					//sposta di una posizione a destra il cursore
					if(input.isKeyPressed( Input.KEY_RIGHT ))
						{
							if(insertEditor)
								{
									if(indexCursor < 0 && indexCursorSfondi < 0)
										indexCursorSfondi++;
									else if(indexCursor < 0)
										{
											if(++indexCursorSfondi == sfondi.size())
												{
													indexCursor++;
													indexCursorSfondi = -1;
												}
										}
									else if(++indexCursor == items.size())
										{
											indexCursor = -1;
											indexCursorSfondi++;
										}
								}
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
									else if(indexCursorButton >= 0)
										{
												if(++indexCursorButton == buttons.size())
													if(ostacoli.size() > 0)
														{
															indexCursor = 0;
															indexCursorButton = -1;
														}
													else
														indexCursorButton = 0;
										}
									else if(ostacoli.size() > 0)
										indexCursor = 0;
									else
										indexCursorButton = 0;
								}							
						}
					//sposta di una posizione a sinistra il cursore
					else if(input.isKeyPressed( Input.KEY_LEFT ))
						{
							if(insertEditor)
								{
									if(indexCursor < 0 && indexCursorSfondi < 0)
										indexCursorSfondi = sfondi.size() - 1;
									else if(indexCursor < 0)
										{
											if(--indexCursorSfondi < 0)
												indexCursor = items.size() - 1;
										}
									else if(--indexCursor < 0)
										indexCursorSfondi = sfondi.size() - 1;
								}
							else
								{
									if(indexCursor >= 0)
										{
											if(--indexCursor < 0)
												{
													indexCursor = -1;
													indexCursorButton = buttons.size() - 1;
												}
										}
									else if(indexCursorButton >= 0)
										{
												if(--indexCursorButton < 0)
													if(ostacoli.size() > 0)
														{
															indexCursor = ostacoli.size() - 1;
															indexCursorButton = -1;
														}
													else
														indexCursorButton = buttons.size() - 1;
										}
									else if(ostacoli.size() > 0)
										indexCursor = ostacoli.size() - 1;
									else
										indexCursorButton = buttons.size() - 1;
								}							
						}
					//abbassa la schermata di selezione elemento
					else if((insertEditor && choise.contains( mouseX, mouseY ) && input.isMousePressed( Input.MOUSE_LEFT_BUTTON )) || input.isKeyPressed( Input.KEY_DOWN ))
						{
							insertEditor = false;
		                    base.setY( gc.getHeight() );
		                    heightBase = 0;
		                    setChoise( gc );
		                    resetIndexCursor();
						}
					else if(insertEditor && indexCursor < 0 && input.isKeyPressed( Input.KEY_UP ))
						indexCursor = 0;
					//innalza la schermata di selezione elemento
					else if((choise.contains( mouseX, mouseY ) && input.isMousePressed( Input.MOUSE_LEFT_BUTTON )) || input.isKeyPressed( Input.KEY_UP ))
						{
							insertEditor = true;
							resetIndexCursor();
						}
					//inserimento nuovo livello
					else if((indexCursorButton == 2 && input.isKeyPressed( Input.KEY_ENTER )) || (saveLevel.checkClick( mouseX, mouseY ) && input.isMousePressed( Input.MOUSE_LEFT_BUTTON )))
						{
							if(!insertEditor)
								if(gamer > 0 && ball > 0)
									{
										addNewLevel();
										gamer = 0;
										ball = 0;
										
										resetStatus();
									}
						}
					//torna alla schermata precedente
					else if(input.isKeyPressed( Input.KEY_BACK ))
						{
							resetStatus();
							Start.editGame = 0;
							Start.recoverPreviousStats();
						}
					//controlla selezione sfondo/elemento tramite tastiera
					else if(input.isKeyPressed( Input.KEY_ENTER ))
						{
							if(checkPressed( mouseX, mouseY, gc, "keyboard" ))
								choise.setLocation( choise.getX(), gc.getHeight() - heightChoise );
						}
					else if(input.isMousePressed( Input.MOUSE_LEFT_BUTTON ))
						if(checkPressed( mouseX, mouseY, gc, "mouse" ))
							choise.setLocation( choise.getX(), gc.getHeight() - heightChoise );
				}
		}
}
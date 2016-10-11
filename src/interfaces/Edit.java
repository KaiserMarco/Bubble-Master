package interfaces;

import java.io.File;
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
import org.newdawn.slick.geom.Shape;

import Utils.Elements;
import Utils.Global;
import Utils.Sfondo;
import Utils.TextBox;
import bubbleMaster.Start;
import dataButton.Button;
import dataButton.SimpleButton;
import dataObstacles.Base;
import dataObstacles.Bubble;
import dataObstacles.Enter;
import dataObstacles.Ostacolo;
import dataObstacles.Player;
import dataObstacles.Tubo;

public class Edit
{
	private Ostacolo temp;
	private int tempX, tempY;
	
	private SimpleButton saveLevel, back;
		
	private ArrayList<Ostacolo> items;	
	private ArrayList<Ostacolo> ostacoli;
	private ArrayList<SimpleButton> buttons;
	
	private ArrayList<Sfondo> sfondi;
	
	private int gamer, ball = 0;
	
	private int indexSfondo = 3;
	
	private Image up, down;
	private int widthArrow, heightArrow;
	
	// baseI = la schermatona di selezione elementi e sfondi
	// choiseI = il pulsante per "tirare su" la schermatona 
	private Image choiseI, baseI;
	private Image cursor;
	
	//indice relativo alla posizione del cursore
	private int indexCursor, indexCursorButton, indexCursorSfondi;
	private int widthC, heightC;
	
	private boolean insertEditor, insertItem, insertNameLvl;
	/**base -> la finestra di selezione sfondo/elemento*/
	private Rectangle choise, base;
	private float widthChoise, heightChoise;
	private float widthBase, heightBase;
	
	private int minHighEditor;
	
	//elementi riguardanti la scrittura su file .xml
	private Element livello;
	private Document document;
	
	//determina se stiamo inserendo una NUOVA coppia di tubi
	private boolean nuovaCoppiaTubi, nuovoTubo1;
	
	//salva il valore del tubo rimanente
	private int indiceTuboRimasto;
	
	//l'oggetto Elements per recuperare le informazioni di gioco
	private Elements elem;
	
	/**il nome del livello
	 * null = inserimento nuovo livello - !null = modifica livello esistente*/
	private String nameLvl;
	
	//determina l'indice del livello
	private int index = -1;
	
	private final static String SAVE = "SALVA LIVELLO", BACK = "INDIETRO";
	
	private boolean mouseDown = false;
	
	private float rappX = 0, rappY = 0;
	
	private TextBox tBox;
	
    public Edit( GameContainer gc ) throws SlickException
		{
			elem = new Elements( gc );
			
			sfondi = elem.getSfondi();			
			items = elem.getItems();
			ostacoli = new ArrayList<Ostacolo>();
			
			((Player) items.get( 2 )).setDrawLifes( false );
			((Player) items.get( 3 )).setDrawLifes( false );
			
			up = new Image( "./data/Image/up.png" );
			down = new Image( "./data/Image/down.png" );
			widthArrow = gc.getWidth()/15;
			heightArrow = gc.getHeight()/40;

			back = new SimpleButton( gc.getWidth()/15, gc.getHeight()*24/25, BACK, Color.orange );
			saveLevel = new SimpleButton( gc.getWidth()*3/4, gc.getHeight()*24/25, SAVE, Color.orange );

			choiseI = new Image( "./data/Image/choise.png" );
			baseI = new Image( "./data/Image/Window.png" );
			cursor = new Image( "./data/Image/cursore.png" );
			
			//lunghezza e altezza del cursore
			widthC = gc.getHeight()*10/133;
			heightC = gc.getHeight()/24;
			
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
			insertNameLvl = false;
			
			buttons = new ArrayList<SimpleButton>();
			
			buttons.add( back );
			buttons.add( saveLevel );
			
			minHighEditor = gc.getHeight() - (int) (gc.getHeight()/1.34);
			
			nuovaCoppiaTubi = false;
			nuovoTubo1 = false;
			indiceTuboRimasto = -1;
			
			temp = null;
			
			tBox = new TextBox( gc );
		}
	
	public void draw( GameContainer gc, Graphics g ) throws SlickException
		{		
			float currRatioW = Global.W/Global.Width, currRatioH = Global.H/Global.Height;
		
			sfondi.get( indexSfondo ).draw( gc );
						
			for(Ostacolo obs: ostacoli)
				{
					obs.draw( g );
					if(obs.getID().equals( "tubo" ))
						{							
							if(obs.getUnion() == -1)
								g.drawGradientLine( obs.getMidArea().getX(), obs.getMidArea().getY(), Color.red, temp.getMidArea().getX(), temp.getMidArea().getY(), Color.red );
							else
								g.drawGradientLine( obs.getMidArea().getX(), obs.getMidArea().getY(), Color.red, ostacoli.get( obs.getUnion() ).getMidArea().getX(), ostacoli.get( obs.getUnion() ).getMidArea().getY(), Color.red );
						}
				}
			
			for(int i = 0; i < buttons.size(); i++)
				buttons.get( i ).draw( g );

			baseI.draw( base.getX(), base.getY(), base.getWidth(), heightBase );

			if(insertItem)
				{
					for(Ostacolo item: items)
						item.draw( g );

					for(Sfondo sfondo: sfondi)
						sfondo.draw();
				}
			
			choiseI.draw( choise.getX(), choise.getY(), choise.getWidth(), choise.getHeight() );
			
			if(insertEditor)
				down.draw( choise.getX() + (widthChoise/2 - widthArrow/2) * currRatioW, choise.getY() + gc.getHeight()/200, widthArrow * currRatioW, heightArrow * currRatioH );
			else
				up.draw( choise.getX() + (widthChoise/2 - widthArrow/2) * currRatioW, choise.getY() + gc.getHeight()/200, widthArrow * currRatioW, heightArrow * currRatioH );
			
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
			
			tBox.render( gc, g );
		}
	
	public void setIndex( int index )
		{ this.index = index; }
	
	public void resetData()
		{
			indexSfondo = 3;
			ostacoli.clear();
			nameLvl = null;
			index = -1;
		}
	
	/**setta gli elementi base di modifica livello*/
	public void setElements( ArrayList<Ostacolo> ostacoli, ArrayList<Ostacolo> giocatori, String nameLvl, int index, Sfondo sfondo )
		{
			for(int i = 0; i < sfondi.size(); i++)
				if(sfondi.get( i ).getName().equals( sfondo.getName() ))
					indexSfondo = i;
		
			for(Ostacolo obs: ostacoli)
				{
					this.ostacoli.add( obs );
					if(obs.getID().equals( "bolla" ))
						ball++;
				}
			
			for(Ostacolo player: giocatori)
				this.ostacoli.add( player );
			
			gamer = giocatori.size();
			
			this.nameLvl = nameLvl;
			this.index = index;
		}
	
	public void updateStats( GameContainer gc )
		{
			float currRatioW = Global.ratioW, currRatioH = Global.ratioH;
			
			if(rappX != currRatioW || rappY != currRatioH)
				{
					widthChoise = gc.getWidth()/8 * Global.ratioH;
					heightChoise = gc.getHeight()/30* Global.ratioW;
					widthBase = widthBase * Global.ratioW;
					
					base = new Rectangle( base.getX() * Global.ratioW, base.getY() * Global.ratioH, widthBase, Global.H );		
					choise = new Rectangle( choise.getX() * Global.ratioW, Global.H - heightChoise*Global.ratioH, widthChoise, heightChoise );
					
					for(SimpleButton button: buttons)
						{
							button.setX( button.getX() * Global.ratioW );
							button.setY( button.getY() * Global.ratioH );
						}
					
					// TODO TUTTO OK, MA IL TUBO NON SI ADATTA COME IL RESTO (DEVO CAPIR IL PERCHE)
					// FORSE HANNO QUALCOSA A CHE FARE BASE ED ENTER, MA NON PENSO, MA MAI DIRE MAI
					for(Ostacolo item: items)
						{
							item.setXY( item.getX() * Global.ratioW, item.getY() * Global.ratioH, "restore" );
							if(item.getID().equals( "bolla" ))
								{
									item.setWidth( (int) (item.getWidth() * Global.ratioH) );
									((Bubble) item).setMaxWidth( (float) (item.getMaxWidth() * Global.ratioW) );
								}
							else
								{
									item.setWidth( item.getWidth() * Global.ratioW );
									if(item.getID().startsWith( "player" ))
										((Player) item).setWidthI( ((Player) item).getWidthI() * Global.ratioW );
									item.setHeight( item.getHeight() * Global.ratioH );
								}
							
							item.setArea( gc );
						}
					
					for(Sfondo sfondo: sfondi)
						{
							sfondo.setX( sfondo.getX() * Global.ratioW );
							sfondo.setY( sfondo.getY() * Global.ratioH );
						
							sfondo.setMaxWidth( sfondo.getMaxWidth() * Global.ratioW );
							sfondo.setMaxHeight( sfondo.getMaxHeight() * Global.ratioH );

							sfondo.setWidth( sfondo.getWidth() * Global.ratioW );
							sfondo.setHeight( sfondo.getHeight() * Global.ratioH );
						}
					
					rappX = currRatioW;
					rappY = currRatioH;
				}
		}
	
	public void setChoise( GameContainer gc )
		{ choise.setLocation( choise.getX(), base.getY() - heightChoise + gc.getWidth()/150 ); }
	
	public void aggiornaIndiciTubi( int i, int difference )
		{
			for(Ostacolo obs: ostacoli)
				if(obs.getID().equals( "tubo" ) && obs.getUnion() > i)
					obs.setUnion( obs.getUnion() - difference );				
				else if(obs.getID().equals( "base" ) && ((Base) obs).getIndexTube() > i)
					((Base) obs).setIndexTube( ((Base) obs).getIndexTube() - difference );
				else if(obs.getID().equals( "enter" ) && ((Enter) obs).getIndexTube() > i)
					((Enter) obs).setIndexTube( ((Enter) obs).getIndexTube() - difference );
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
			//se e' stato scelto un elemento nuovo da inserire
			if(insertEditor)
				{
					//inserimento da tastiera
					if(type.equals( "keyboard" ))
						{
							if(indexCursor >= 0)
								{
									temp = items.get( indexCursor ).clone( gc );
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
								
									return true;
								}
						}
					//inserimento tramite click del mouse
					else
						{
							for(Ostacolo item: items)
								{
									if(item.contains( x, y ))
										{
											temp = item.clone( gc );
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
							temp = ostacoli.get( indexCursor ).clone( gc );
							//modifica la posizione di un tubo gia' esistente
							if(temp.getID().equals( "tubo" ))
								{
									ostacoli.get( temp.getUnion() ).setUnion( - 1 );
									
									if(temp.getUnion() > indexCursor)											
										indiceTuboRimasto = temp.getUnion() - 3;
									else
										indiceTuboRimasto = temp.getUnion();
									
									//sistema gli indici dei tubi puntati
									aggiornaIndiciTubi( indexCursor, 3 );
												
									ostacoli.remove( indexCursor + 2 );
									ostacoli.remove( indexCursor + 1 );
									ostacoli.remove( indexCursor );
								}
							else
								{
									//sistema gli indici dei tubi puntati
									aggiornaIndiciTubi( indexCursor, 1 );									
									ostacoli.remove( indexCursor );
								}
							
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
													indiceTuboRimasto = temp.getUnion() - 3;
												else
													indiceTuboRimasto = temp.getUnion();
												
												//sistema gli indici dei tubi puntati
												aggiornaIndiciTubi( i, 3 );
															
												ostacoli.remove( i + 2 );
												ostacoli.remove( i + 1 );
												ostacoli.remove( i );
											}
										else
											{
												//sistema gli indici dei tubi puntati
												aggiornaIndiciTubi( i, 1 );
												ostacoli.remove( i );
											}
										
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
	
	private void setTubeInArray( GameContainer gc )
		{
			for(int i = ostacoli.size() - 1; i >= 0; i--)
				if(ostacoli.get( i ).getID().equals( "base" ) || ostacoli.get( i ).getID().equals( "enter" ))
					{
						aggiornaIndiciTubi( i, 1 );
						ostacoli.remove( i );
					}
		
			int j = 0;
			for(int i = 0; i < ostacoli.size(); i++)
				{
					if(!ostacoli.get( i ).getID().equals( "tubo" ))
						{
							for(j = i; j < ostacoli.size(); j++)
								if(ostacoli.get( j ).getID().equals( "tubo" ))
									{
										Ostacolo tmp = ostacoli.get( i ).clone( gc );
										
										ostacoli.remove( i );
										ostacoli.add( i, ostacoli.get( j - 1 ).clone( gc ) );
										ostacoli.get( i ).setUnion( ostacoli.get( j ).getUnion() );
										ostacoli.get( i ).setSpigoli();
										
										ostacoli.remove( j );
										ostacoli.add( j, tmp );
										ostacoli.get( j ).setSpigoli();
										
										ostacoli.get( ostacoli.get( i ).getUnion() ).setUnion( i );
									
										break;
									}
						}
					if(j == ostacoli.size())
						break;
				}
		}
	
	public void removeFile()
		{
			System.out.println( "rimosso = " + Begin.livelli.get( index ).getName() + ".xml" );
			File levels = new File( "data/livelli/" + Begin.livelli.get( index ).getName() + ".xml" );
			if(levels.delete())
				System.out.println( "file eliminato" );
		}
	
	private void addNewLevel( GameContainer gc, String name ) throws SlickException
		{
			//posiziona i tubi all'inizio dell'array
			setTubeInArray( gc );
		
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
			    
			    item = new Element( "livello" );
			    item.setAttribute( "nome", name );
			    livello.addContent( item );
			    for(Ostacolo obs: ostacoli)
			    	{									    			
	    				item = new Element( "ostacolo" );
	    				item.setAttribute( "x", obs.getX() + "" );
	    				item.setAttribute( "y", obs.getY() + "" );
	    				item.setAttribute( "union", obs.getUnion() + "" );
	    				if(obs.getOrienting() != null)
	    				    item.setAttribute( "type", obs.getOrienting() );
	    				else
	    				    item.setAttribute( "type", "null" );
	    				item.setAttribute( "ID", obs.getID() );
	    				livello.addContent( item );
			    	}
				
	    		item = new Element( "sfondo" );
	    		item.setAttribute( "x", "0" );
	    		item.setAttribute( "y", "0" );
	    		item.setAttribute( "name", sfondi.get( indexSfondo ).getName() );
	    		livello.addContent( item );
	    		
	    		item = new Element( "risoluzione" );
	    		item.setAttribute( "w", Global.W + "" );
	    		item.setAttribute( "h", Global.H + "" );
	    		livello.addContent( item );
	    		
	    		if(nameLvl != null)
	    			{
	    				removeFile();
	    				Begin.livelli.remove( index );
	    				Begin.livelli.add( index, new Livello( ostacoli, sfondi.get( indexSfondo ), name ) );

	    	    		outputter.output( document, new FileOutputStream( "data/livelli/" + name + ".xml" ) );
	    	    		Start.cl.updateNameLvl();
	    			}
	    		else
	    			{
						Begin.livelli.add( new Livello( ostacoli, sfondi.get( indexSfondo ), name ) );
			    		outputter.output( document, new FileOutputStream( "data/livelli/" + name + ".xml" ) );
	    			}
	    		
	    		System.out.println( "livello " + name + ".xml salvato" );

				nameLvl = null;
				index = -1;
			}
			catch( IOException e ){
				System.err.println( "Error while creating the level" );
				e.printStackTrace();
			}
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
	
	private int checkButton( Button button, Input input, int i )
		{
			if(button.isPressed())
				return 1;
			else if(indexCursor >= 0 && indexCursor == i)
				if(input.isKeyPressed( Input.KEY_ENTER ))
					return 2;
		
			return 0;
		}
	
	public void update( GameContainer gc, int delta )throws SlickException
		{
			Input input = gc.getInput();
			int mouseX = input.getMouseX();
			int mouseY = input.getMouseY();
			int move = (int) (gc.getHeight()/300 * Global.Height/Global.H);
			
			boolean collide = false, fall = false;
			//determina se il personaggio "tocca" un oggetto o il pavimento
			int stay = -1;
			
			// aggiornamento altezza editor
			setEditor( delta, gc );

			if(input.isKeyPressed( Input.KEY_ESCAPE ))
				{
					resetStatus();
					Start.editGame = 0;
					Start.chooseLevel = 1;
				}
			// se HO cliccato su un elemento da inserire
			if(temp != null)
				{
			        //controlla se un l'oggetto da inserire non superi i confini dello schermo di gioco
					if((temp.getID().equals( "bolla" ) && temp.getY() + temp.getHeight()*2 > sfondi.get( indexSfondo ).getMaxHeight())
					|| (!temp.getID().equals( "bolla" ) && temp.getY() + temp.getHeight() > sfondi.get( indexSfondo ).getMaxHeight()))
						collide = true;
					else
						for(Ostacolo ost: ostacoli)
							{
							    if(temp.getID().startsWith( "player" ))
							        {						    	
							            if(!ost.getID().equals( "sbarra" ) && !ost.getID().equals( "tubo" )
					            		&& !ost.getID().equals( "base" ) && !ost.getID().equals( "enter" ))
							                {
	    						                if(temp.component( "rect" ).intersects( ost.component( "rect" ) ))
	    						                    collide = true;
							                }
							            else if(temp.component( "rect" ).intersects( ost.component( "latoGiu" ) ))
	                                        collide = true;
							        }
							    else if(!ost.getID().equals( "base" ) && !ost.getID().equals( "enter" ))
							    	if(temp.component( "rect" ).intersects( ost.component( "rect" ) ))
							    		collide = true;
							}
					
					if(collide)
						temp.setInsert( false, false );
					else
						temp.setInsert( true, false );

					if(!temp.getID().equals( "bolla" ) && !temp.getID().startsWith( "player" ))
					    if(input.isKeyPressed( Input.KEY_SPACE ))
					    	{
					        	temp.setOrienting( null );
					        	if(temp.equals( "tubo" ))
					        		{
					        			((Base) ((Tubo) temp).getBase()).setDirection( ((Tubo) temp).getDirection());
					        			((Enter) ((Tubo) temp).getBase()).setDirection( ((Tubo) temp).getDirection());
					        		}
					    	}

					/*controlla che il personaggio non sia posizionato a mezz'aria*/
					if(temp.getID().startsWith( "player" ))
						if(temp.getY() + temp.getHeight() < sfondi.get( indexSfondo ).getMaxHeight() - 1)
							{
								Shape area = temp.component( "rect" );
								for(int i = 0; i < ostacoli.size(); i++)									
									if(area.intersects( ostacoli.get( i ).component( "rect" ) ))
										{
											stay = i;
											break;
										}
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
								if(!ostacoli.get( i ).getID().equals( "tubo" ))
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
								if(!ostacoli.get( i ).getID().equals( "tubo" ))
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
										if(!ostacoli.get( i ).getID().equals( "tubo" ))
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
							if(temp.getID().equals( "tubo" ))
								{
									if(indiceTuboRimasto >= 0)
										{
											aggiornaIndiciTubi( indiceTuboRimasto, 3 );
											ostacoli.remove( indiceTuboRimasto + 2 );
											ostacoli.remove( indiceTuboRimasto + 1 );
											ostacoli.remove( indiceTuboRimasto );
											
											indiceTuboRimasto = -1;
										}
								}
							
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
											// INSERISCO BASE E ENTER PER USARLI IN GIOCO PER LE COLLISIONI SFERE/PLAYER - TUBO
											((Tubo) ostacoli.get( ostacoli.size() - 1 )).setSpace( gc );
											// inserisco base e enter del tubo
											ostacoli.add( ((Tubo) temp).getBase() );
											ostacoli.add( ((Tubo) temp).getEnter() );
											
											ostacoli.get( ostacoli.size() - 2 ).setArea( gc );
											ostacoli.get( ostacoli.size() - 1 ).setArea( gc );
											
											// setto l'indice del tubo a base e enter (PER RIMUOVERLI QUALORA RIMUOVESSI IL TUBO RELATIVO)
											((Base) ostacoli.get( ostacoli.size() - 2 )).setIndexTube( ostacoli.size() - 3 );
											((Enter) ostacoli.get( ostacoli.size() - 1 )).setIndexTube( ostacoli.size() - 3 );
											
											//inserisce una nuova coppia di tubi
											if(nuovaCoppiaTubi)
												{
													if(nuovoTubo1)
														{
															Ostacolo temp2 = temp.clone( gc );
															
															temp = temp2;
															nuovoTubo1 = false;
														}
													else
														{
															//setto i nuovi indici dei tubi puntati
															ostacoli.get( ostacoli.size() - 6 ).setUnion( ostacoli.size() - 3 );
															ostacoli.get( ostacoli.size() - 3 ).setUnion( ostacoli.size() - 6 );
															temp = null;
															nuovaCoppiaTubi = false;
														}
												}
											//inserisce un tubo gia esistente
											else
												{
													ostacoli.get( indiceTuboRimasto ).setUnion( ostacoli.size() - 3 );
													ostacoli.get( ostacoli.size() - 3 ).setUnion( indiceTuboRimasto );
													
													indiceTuboRimasto = -1;
												
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
					if(!insertNameLvl && input.isKeyPressed( Input.KEY_RIGHT ))
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
					else if(!insertNameLvl && input.isKeyPressed( Input.KEY_LEFT ))
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
	              //se e' stato scelto uno sfondo o un oggetto da inserire nel livello tramite mouse
					else if(input.isMousePressed( Input.MOUSE_LEFT_BUTTON ))
						if(checkPressed( mouseX, mouseY, gc, "mouse" ))
							choise.setLocation( choise.getX(), gc.getHeight() - heightChoise );
					
					if(input.isMouseButtonDown( Input.MOUSE_LEFT_BUTTON ))
						{
			                if(!mouseDown)
				                {
				                    mouseDown = true;
				                    
				                    for(SimpleButton button : buttons)
				                        if(button.checkClick( mouseX, mouseY, input ))
				                        	if(!button.isPressed())
			                            		button.setPressed();
				                }
			            }
		            else
			            {
			                if(mouseDown || checkKeyPressed( input ))
				                {
				                    mouseDown = false;
				                    
				                    for(int i = 0; i < buttons.size(); i++)
				                    	{
				                    		int value = checkButton( buttons.get( i ), input, i );
				                        	boolean pressed = true;
				                        	// se e' stato premuto il tasto
				                    		if(value > 0)
				                    			{
					                                for(SimpleButton button: buttons)
					                                	if(button.isPressed())
					                                		button.setPressed();
					                                pressed = buttons.get( i ).checkClick( mouseX, mouseY, input );
						                            // pressed tramite mouse || value==2 tramite tastiera
						                            if(pressed || value == 2)
							                            {
						                            		if(buttons.get( i ).getName().equals( BACK ))
							                            		{
    						                            		    Start.editGame = 0;
    	                                                            indexCursor = -1;
						                            				nameLvl = null;
						            								resetStatus();
						            								Start.chooseLevel = 1;
							                            		}
						                            		else if(buttons.get( i ).getName().equals( SAVE ))
						                            			{
						                            				if(!insertEditor)
							                            				{
							                            					if(gamer > 0 && ball > 0)
								                            					{
							                            							for(Ostacolo obs: ostacoli)
							                            								if(obs.getID().startsWith( "player" ))
							                            									((Player) obs).setDrawLifes( true );
							                            					        // apre la textBox
							                            					        tBox.setOpen( true );
								                            					    // setta il nome del livello
							                            					        if(index >= 0)
							                            					        	tBox.setText( Begin.livelli.get( index ).getName() );
								                            					}
							                            				}
						                            			}
						                            		
								                            break;
							                            }
				                    			}
				                    	}
				                }
			              //se e' stato scelto uno sfondo o un oggetto da inserire nel livello tramite mouse
							else if(input.isKeyPressed( Input.KEY_ENTER ))
								{
									if(checkPressed( mouseX, mouseY, gc, "keyboard" ))
										choise.setLocation( choise.getX(), gc.getHeight() - heightChoise );
								}
			            }
				}
			
			// gestione della textbox per il nome del livello
			if(index >= 0)
				tBox.update( input, Begin.livelli.get( index ) );
			else
				tBox.update( input, null );
			if(!tBox.isOpen())
				{
					insertNameLvl = false;
				    String name = tBox.getText();
				    if(name != null && !name.isEmpty())
					    {
					        tBox.setText( "" );
					        addNewLevel( gc, name );
		                    
		                    resetStatus();
		                    Start.editGame = 0;
		                    indexCursor = -1;
		                    gamer = 0;
		                    ball = 0;
		                
		                    Start.chooseLevel = 1;
					    }
				}
			else
				insertNameLvl = true;
		}
	
	private boolean checkKeyPressed( final Input input )
    	{ return input.isKeyDown( Input.KEY_ENTER ); }
}
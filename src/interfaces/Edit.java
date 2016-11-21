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
import Utils.Livello;
import Utils.Sfondo;
import Utils.TextBox;
import bubbleMaster.Start;
import dataButton.Button;
import dataButton.SimpleButton;
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
	
	private int indexSfondo = 0;
	
	private Image up, down;
	private float widthArrow, heightArrow;
	
	// baseI = la schermatona di selezione elementi e sfondi
	// choiseI = il pulsante per "tirare su" la schermatona 
	private Image choiseI, baseI;
	
	//indice relativo alla posizione del cursore
	private int indexCursor;
	
	private boolean insertEditor, insertItem;
	/**base -> la finestra di selezione sfondo/elemento*/
	private Rectangle choise, base;
	private float widthChoise, heightChoise;
	private float widthBase, heightBase;
	
	// l'altezza di arrivo della schermata di selezione sfondo/personaggio
	private float minHighEditor;
	
	//elementi riguardanti la scrittura su file .xml
	private Element livello;
	private Document document;
	
	// determina se si sta inserendo una nuova coppia di tubi
	private boolean nuovoTubo;
	//salva il valore del tubo rimanente
	private int indexFirstTube;
	
	//l'oggetto Elements per recuperare le informazioni di gioco
	private Elements elem;
	
	/**il nome del livello
	 * null = inserimento nuovo livello - !null = modifica livello esistente*/
	private String nameLvl;
	
	//determina l'indice del livello
	private int index = -1;
	
	private final static String SAVE = "SALVA LIVELLO", BACK = "INDIETRO",
								SX = "sx", DX = "dx";
	
	private boolean mouseDown = false;
	
	private TextBox tBox;
	
	private boolean moveEditor;
	
	private float posY;
	
	// determina l'altezza massima inseribile dell'oggetto
	private float maxHeight;
	
	// la posizione del mouse
	private int mouseX, mouseY;
	
    public Edit( GameContainer gc ) throws SlickException
		{
			elem = new Elements( gc );
			
			sfondi = elem.getSfondi();
			items = elem.getItems();
			ostacoli = new ArrayList<Ostacolo>();
			
			maxHeight = sfondi.get( indexSfondo ).getMaxHeight();
			
			for(int i = 3; i < 7; i++)
				((Player) items.get( i )).setDrawLifes( false );
			
			up = new Image( "./data/Image/up.png" );
			down = new Image( "./data/Image/down.png" );
			widthArrow = Global.Width/15;
			heightArrow = Global.Height/40;

			back = new SimpleButton( Global.Width/15, Global.Height*24/25, BACK, Color.orange, 0 );
			saveLevel = new SimpleButton( Global.Width*3/4, Global.Height*24/25, SAVE, Color.orange, 1 );

			choiseI = new Image( "./data/Image/choise.png" );
			baseI = new Image( "./data/Image/Window.png" );
			
			widthChoise = Global.Width/8;
			heightChoise = Global.Height/30;
			
			//lunghezza e altezza della base di selezione elementi
			widthBase = Global.Width/1.11f;
			heightBase = 0;
			
			choise = new Rectangle( Global.Width/2 - widthChoise/2, Global.Height - heightChoise, widthChoise, heightChoise );			
			base = new Rectangle( Global.Width/2 - widthBase/2, Global.Height, widthBase, heightBase );
			
			insertEditor = false;
			indexCursor = -1;
			
			insertItem = false;
			
			buttons = new ArrayList<SimpleButton>();
			
			buttons.add( back );
			buttons.add( saveLevel );
			
			minHighEditor = Global.Height/4;
			
			nuovoTubo = false;
			indexFirstTube = -1;
			
			temp = null;
			
			tBox = new TextBox( gc );
			
			moveEditor = false;
		}
	
	public void draw( GameContainer gc, Graphics g ) throws SlickException
		{
			sfondi.get( indexSfondo ).draw( gc );
						
			for(Ostacolo obs: ostacoli)
				{
					if(temp != null)
						{
							if(!temp.getID().equals( Global.TUBO ) && !temp.contains( obs.getArea() ))
								obs.draw( g );
							else if(temp.getID().equals( Global.TUBO ))
								if(!(((Tubo) temp).getBase().contains( obs.getArea() ) && ((Tubo) temp).getEnter().contains( obs.getArea() )))
									obs.draw( g );
						}
					else
						obs.draw( g );
					
					if(obs.getID().equals( Global.TUBO ))
						{
							if(obs.getUnion() == -1)
								g.drawGradientLine( obs.getMidArea()[0], obs.getMidArea()[1], Color.red, temp.getMidArea()[0], temp.getMidArea()[1], Color.red );
							else
								g.drawGradientLine( obs.getMidArea()[0], obs.getMidArea()[1], Color.red, ostacoli.get( obs.getUnion() ).getMidArea()[0], ostacoli.get( obs.getUnion() ).getMidArea()[1], Color.red );
						}
				}
			
			for(SimpleButton button: buttons)
				button.draw( g );

			baseI.draw( base.getX(), base.getY(), widthBase, heightBase );

			if(insertItem)
				{
					for(Ostacolo item: items)
						item.draw( g );

					for(Sfondo sfondo: sfondi)
						sfondo.draw();
				}
			
			choiseI.draw( choise.getX(), choise.getY(), widthChoise, heightChoise );
			
			if(insertEditor)
				down.draw( choise.getX() + (widthChoise/2 - widthArrow/2), choise.getY() + Global.Height/200, widthArrow, heightArrow );
			else
				up.draw( choise.getX() + (widthChoise/2 - widthArrow/2), choise.getY() + Global.Height/200, widthArrow, heightArrow );

			if(temp != null)
				temp.draw( g );
			
			tBox.render( gc, g );
			
			Global.drawScreenBrightness( g );
		}
	
	public void setIndex( int index )
		{ this.index = index; }
	
	/**setta gli elementi base di modifica livello*/
	public void setElements( ArrayList<Ostacolo> ostacoli, String nameLvl, int index, Sfondo sfondo, GameContainer gc ) throws SlickException
		{
			indexSfondo = sfondo.getIndex();
			maxHeight = sfondi.get( indexSfondo ).getMaxHeight();
		
			for(Ostacolo obs: ostacoli)
				{
					if(!obs.getID().equals( Global.BASE ) && !obs.getID().equals( Global.ENTER ))
						{
							this.ostacoli.add( obs.clone( gc ) );
							Ostacolo ost = this.ostacoli.get( this.ostacoli.size() - 1 );
							ost.setSpigoli();
							if(ost.getID().equals( Global.TUBO ))
								((Tubo) ost).setUnion( ((Tubo) obs).getUnion() );
							else if(obs.getID().equals( Global.PLAYER ))
								updateItemPlayer( (Player) obs, false );
						}
				}
			
			this.nameLvl = nameLvl;
			this.index = index;
		}
	
	public void aggiornaIndiciTubi( int i )
		{
			for(Ostacolo obs: ostacoli)
				if(obs.getID().equals( Global.TUBO ) && obs.getUnion() > i)
					obs.setUnion( obs.getUnion() - 1 );
		}
	/**resetta indexCursor, indexCursorButton e indexCursorSfondi*/
	public void resetIndexCursor()
		{ indexCursor = -1; }
	
	/** controlla se e' stato cliccato su un qualche elemento del livello */
	public boolean checkPressed( int x, int y, GameContainer gc ) throws SlickException
		{
			if(choise.contains( x, y ))
				return true;
		
			//se e' stato scelto un elemento nuovo da inserire
			if(insertEditor)
				{
					for(Ostacolo item: items)
						{
							if(item.contains( x, y ))
								{
									// controlla se posso inserire un nuovo player
									if(item.getID().equals( Global.PLAYER ))
										{
											if(!((Player) item).isSelectable())
												return false;
											else
												{
													temp = item.clone( gc );
													setPlayer();
												}
										}
									else temp = item.clone( gc );
									
									//sto inserendo una nuova coppia di tubi
									if(temp.getID().equals( Global.TUBO ))
										nuovoTubo = true;

									temp.setInsert( checkCollision( temp.getArea() ), true );
									
									tempX = x;
									tempY = y;
									
									resetIndexCursor();
									
									return true;
								}
						}
					for(Sfondo sfondo: sfondi)
						if(sfondo.contains( x, y ))
							{
								indexSfondo = sfondo.getIndex();
								return true;
							}
				}
			//se e' stato scelto un elemento gia' presente nel gioco
			else
				{
					for(int i = 0; i < ostacoli.size(); i++)
						{
							if(ostacoli.get( i ).contains( x, y ))
								{
									temp = ostacoli.get( i );
									//modifica la posizione di un tubo gia' esistente
									if(temp.getID().equals( Global.TUBO ))
										{								
											ostacoli.get( temp.getUnion() ).setUnion( - 1 );

											indexFirstTube = temp.getUnion();
											if(temp.getUnion() > i)											
												indexFirstTube--;
										}
										
									//sistema gli indici dei tubi puntati
									aggiornaIndiciTubi( i );
									ostacoli.remove( i );
									
									temp.setInsert( checkCollision( temp.getArea() ), true );
									
									tempX = x;
									tempY = y;
									
									return false;
								}
						}
				}
			return false;
		}
	
	//resetta tutte le variabili e il vettore degli oggetti del livello
	public void resetStatus()
		{
			// reimposta tutti i player in modalita' selezionabile
			for(Ostacolo item: items)
				if(item.getID().equals( Global.PLAYER ))
					((Player) item).setSelectable( true );
			
			tBox.setText( "" );
			tBox.setOpen( false );
		
			ostacoli.clear();
			nameLvl = null;
			index = -1;
			indexSfondo = 0;
			maxHeight = sfondi.get( indexSfondo ).getMaxHeight();
			insertEditor = false;
			moveEditor = true;
			temp = null;

			indexCursor = -1;
			
            Start.editGame = 0;
			Start.chooseLevel = 1;
		}
	
	private void setTubeInArray( GameContainer gc ) throws SlickException
		{
			int j = 0;
			for(int i = 0; i < ostacoli.size(); i++)
				{
					if(!ostacoli.get( i ).getID().equals( Global.TUBO ))
						{
							for(j = i; j < ostacoli.size(); j++)
								if(ostacoli.get( j ).getID().equals( Global.TUBO ))
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
			
			int num = 1;
			// setta in maniera opportuna il numPlayer dei giocatori
			for(Ostacolo obs: ostacoli)
				if(obs.getID().equals( Global.PLAYER ))
					((Player) obs).setNumPlayer( num++ );
			
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
		    				if(obs.getID().equals( Global.PLAYER ))
		    					{
		    						item.setAttribute( "number", ((Player) obs).getNumPlayer() + "" );
		    						item.setAttribute( "color", ((Player) obs).getColor() );
		    					}
		    				item.setAttribute( "ID", obs.getID() );
		    				livello.addContent( item );
				    	}
					
		    		item = new Element( "sfondo" );
		    		item.setAttribute( "name", sfondi.get( indexSfondo ).getName() );
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

		            resetStatus();
				}
			catch( IOException e )
				{
					System.err.println( "Error while creating the level" );
					e.printStackTrace();
				}
		}
	
	/** setta la posizione dell'editor */
	public void setEditor( int delta, GameContainer gc )
	    {
    	    if(insertEditor)
	    	    {
	                if(base.getY() - delta/2 > minHighEditor)
	                    {
	                        base.setY( base.getY() - delta*6/5 );
	                        heightBase = heightBase + delta*6/5;
	                    }
	                else
	                    {
	                        insertItem = true;
	                        base.setY( minHighEditor );
	                        heightBase = Global.Height - minHighEditor;
	                        moveEditor = false;
	                    }
	    	    }
            else
                {
                    insertItem = false;
                    base.setY( Global.Height );
                    heightBase = 0;
                    moveEditor = false;
                }
    	    
			choise.setY( base.getY() - heightChoise );
	    }
	
	private int checkButton( Button button, Input input, int i )
		{
			if(button.isPressed())
				return 1;
			else if(indexCursor == i)
				if(input.isKeyPressed( Input.KEY_ENTER ))
					return 2;
		
			return 0;
		}
	
	public void controllaPosizione( Ostacolo ost )
		{
			if(mouseY < ost.getY() && ost.getY() < posY)
				if(!(temp.getMaxX() < ost.getX() || temp.getX() > ost.getMaxX()))
					posY = ost.getY();
		}
	
	/** determina la posizione del player rispetto agli ostacoli in fase di inserimento */
	private void checkPosition( Ostacolo ost )
		{
			if(ost.getID().equals( Global.TUBO ))
				{
					controllaPosizione( ((Tubo) ost).getBase() );
					controllaPosizione( ((Tubo) ost).getEnter() );
				}
			else
				controllaPosizione( ost );
		}
	
	/** controlla la collisione fra i vari oggetti
	 * return false = collide
	 * return true = non collide */
	private boolean checkCollision( Shape areaTemp )
		{
			for(Ostacolo ost: ostacoli)
				{
					Shape areaObs = ost.getArea();
				
				    if(temp.getID().equals( Global.PLAYER ))
				        {						    	
				            if(ost.getID().equals( Global.BOLLA ))
				                {
					                if(areaTemp.intersects( areaObs ))
					                    return false;
				                }
				            if(ost.getID().equals( Global.TUBO ))
			            		{
			            			Shape areaBase = ((Tubo) ost).getBase().getArea();
			            			Shape areaEnter = ((Tubo) ost).getEnter().getArea();
			            			
			            			if(areaTemp.intersects( areaEnter ))
		            					if(((Tubo) ost).getDirection().equals( SX ) || ((Tubo) ost).getDirection().equals( DX ))
		            						if(areaTemp.getMaxY() > areaEnter.getY())
		            							return false;
		            						else
		            							return true;
			            				
			            			if(areaTemp.intersects( areaBase ))
		            					if(areaTemp.intersects( areaEnter ) || temp.getMaxY() > areaBase.getY())
		            						return false;
		            					else
		            						return true;
	            				}
				            if(areaTemp.intersects( ost.component( Global.LATOGIU ) ))
		                        return false;
				        }
				    if(areaTemp.intersects( ost.getArea() ))
			    		return false;
				}
		    
		    return true;
		}
	
	/** muove il player attraverso gli ostacoli */
	public void setPlayer()
		{
			posY = maxHeight;
			for(Ostacolo obs: ostacoli)
				if(!obs.getID().equals( Global.PLAYER ) && !obs.getID().equals( Global.BOLLA ))
					checkPosition( obs );
			
			temp.setXY( temp.getX(), posY - temp.getHeight(), Global.RESTORE );
		}
	
	public void update( GameContainer gc, int delta, Input input )throws SlickException
		{
			mouseX = input.getMouseX();
			mouseY = input.getMouseY();
			
			// aggiornamento altezza editor
			if(moveEditor)
				setEditor( delta, gc );

			if(input.isKeyPressed( Input.KEY_ESCAPE ))
				resetStatus();
			
			// se HO un elemento da inserire
			if(temp != null)
				{
					// se e' stato mosso il mouse
					if(mouseX != tempX || mouseY != tempY)
						{
							// spostamento dell'oggetto in relazione alla posizione del mouse
							temp.setXY( mouseX - tempX, mouseY - tempY, Global.MOVE );
							
							// posizionamento player sopra gli ostacoli		
							if(temp.getID().equals( Global.PLAYER ))
								setPlayer();

							// setta il colore dell'oggetto in fase di inserimento
							temp.setInsert( checkCollision( temp.getArea() ), true );

							// controlla se l'oggetto da inserire non supera i confini dello schermo di gioco					
							if(temp.getX() <= 0)
								temp.setXY( 0, temp.getY(), Global.RESTORE );
							else if(temp.getMaxX() >= Global.Width)
								temp.setXY( Global.Width - temp.getWidth(), temp.getY(), Global.RESTORE );
							if(temp.getY() <= 0)
								temp.setXY( temp.getX(), 0, Global.RESTORE );
							else if(temp.getMaxY() > maxHeight)
								temp.setXY( temp.getX(), maxHeight - temp.getHeight(), Global.RESTORE );
							
							tempX = mouseX;
							tempY = mouseY;
						}

				    if(input.isKeyPressed( Input.KEY_SPACE ))
						if(!(temp.getID().equals( Global.BOLLA ) || temp.getID().equals( Global.PLAYER )))
					    	temp.setOrienting( gc );
					
					/*cancellazione oggetti del gioco*/
					if(input.isMousePressed( Input.MOUSE_RIGHT_BUTTON ) || input.isKeyPressed( Input.KEY_DELETE ))
						{
							//se il tubo ha un'altro tubo collegato, elimina anche il collegamento
							if(temp.getID().equals( Global.TUBO ))
								{
									if(indexFirstTube >= 0)
										{
											aggiornaIndiciTubi( indexFirstTube );
											ostacoli.remove( indexFirstTube );
											
											indexFirstTube = -1;
										}
									else if(!nuovoTubo)
										ostacoli.remove( ostacoli.size() - 1 );
								}
							else if(temp.getID().equals( Global.PLAYER ))
								updateItemPlayer( (Player) temp, true );
							
							temp = null;
						}
					/*inserimento oggetto nel gioco*/
					else if(input.isMousePressed( Input.MOUSE_LEFT_BUTTON ) && temp.getInsert())
						{
							temp.setInsert( true, false );
							if(!(temp.getID().equals( Global.PLAYER ) && temp.getID().equals( Global.BOLLA )))
								temp.setSpigoli();
							ostacoli.add( temp );
							
							if(temp.getID().equals( Global.PLAYER ))
								updateItemPlayer( (Player) temp, false );

							if(temp.getID().equals( Global.TUBO ))
							    {
									// inserisce una coppia nuova di tubi
									if(nuovoTubo)
										{
											temp = ostacoli.get( ostacoli.size() - 1 ).clone( gc );
											temp.setInsert( checkCollision( temp.getArea() ), true );
											nuovoTubo = false;
											indexFirstTube = ostacoli.size() - 1;
										}
									else
										{
											ostacoli.get( ostacoli.size() - 1 ).setUnion( indexFirstTube );
											ostacoli.get( indexFirstTube ).setUnion( ostacoli.size() - 1 );
											
											temp = null;
										}
							    }
							else
								temp = null;
						}
				}
			//se NON ho ancora un elemento da inserire
			else if(temp == null)
				{
					if(input.isKeyPressed( Input.KEY_DOWN ))
						{
							insertEditor = false;
							moveEditor = true;
						}
					else if(input.isKeyPressed( Input.KEY_UP ))
						{
							insertEditor = true;
							moveEditor = true;
						}
					else if(input.isMousePressed( Input.MOUSE_LEFT_BUTTON ) && checkPressed( mouseX, mouseY, gc ))
						{
							insertEditor = !insertEditor;
							moveEditor = true;
						}
					
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
		            else if(mouseDown || checkKeyPressed( input ))
		                {
		                    mouseDown = false;
		                    if(!insertEditor)
		                    	{
				                    for(SimpleButton button: buttons)
				                    	{
				                    		int value = checkButton( button, input, button.getIndex() );
				                        	// se e' stato premuto il tasto
				                    		if(value > 0)
				                    			{
					                                for(SimpleButton bottone: buttons)
					                                	if(bottone.isPressed())
					                                		bottone.setPressed();
					                                
						                            // pressed tramite mouse || value==2 tramite tastiera
						                            if(button.checkClick( mouseX, mouseY, input ) || value == 2)
							                            {
						                            		if(button.getName().equals( BACK ))
							                            		resetStatus();
						                            		else if(button.getName().equals( SAVE ))
		                            					        tBox.setOpen( true );
								                            return;
							                            }
				                    			}
				                    	}
		                    	}
		                }
				}
			
			// gestione della textbox per il nome del livello
			if(tBox.isOpen())
				if(tBox.update( input, index ))
					addNewLevel( gc, tBox.getText() );
		}
	
	/** aggiorna l'inseribilita' di quello specifico player
	 * 
	 *  @param ost - il giocatore in questione
	 *  @param select - true = diventa inseribile
	 *  				false = diventa non inseribile*/
	private void updateItemPlayer( Player ost, boolean select )
		{
			for(Ostacolo item: items)
				if(item.getID().equals( Global.PLAYER ))
					if(((Player) item).getColor().equals( ost.getColor() ))
						{
							((Player) item).setSelectable( select );
							break;
						}
		}
	
	private boolean checkKeyPressed( final Input input )
    	{ return input.isKeyDown( Input.KEY_ENTER ); }
}
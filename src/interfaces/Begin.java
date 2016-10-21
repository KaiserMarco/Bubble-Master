package interfaces;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import Utils.Global;
import Utils.Livello;
import Utils.Sfondo;
import bubbleMaster.Start;
import dataButton.Button;
import dataButton.SimpleButton;
import dataObstacles.Bubble;
import dataObstacles.Ostacolo;
import dataObstacles.Player;
import dataObstacles.Sbarra;
import dataObstacles.Tubo;

public class Begin
{
	public SimpleButton levels, tasto, options;
	
	/**array contente tutti i livelli creati*/
	public static ArrayList<Livello> livelli;
	/*sfondo del gioco*/
	Sfondo sfondo;
	/*ostacoli del gioco*/
	ArrayList<Ostacolo> ost;
	/*immagine del cursore*/
	private Image cursor;
	/**array contenente i bottoni della schermata*/
	private ArrayList<SimpleButton> buttons;
	/*posizione del cursore*/
	private int indexCursor;
	/*dimensioni del cursore*/
	private int widthC, heightC;
	
	private DocumentBuilderFactory documentFactory;
	private DocumentBuilder builder;
	private Document document;
	
	/*gli elementi del livello (ostacoli, giocatori e sfondo)*/
	private ArrayList<Ostacolo> elements;
	
	//determina se visualizzare o meno i tasti di selezione
	private boolean insertButton;
	
	//deermina il punto in cui i bottoni dovranno fermarsi
	private float xFinale, numFinale1, numFinale2, countNumFinale;
	
	//immagine schermata iniziale
	private Sfondo pang;
	
	private float checkRatioW, checkRatioH;
	
	private boolean mouseDown = false;
	
	private static final String OPTIONS = "OPZIONI", LEVELS = "LIVELLI", BEGIN = "Premi un tasto qualsiasi per iniziare";
	
	private boolean showBegin;
	private int timeShowBegin;
	private final int timeLimitBegin = 65;
	
	public Begin( GameContainer gc ) throws SlickException
		{
	        insertButton = false;
	    
			livelli = new ArrayList<Livello>();
			
			Color color = Color.orange;
			options = new SimpleButton( 0, gc.getHeight()*5/6, OPTIONS, color );
			levels = new SimpleButton( gc.getWidth(), gc.getHeight()*5/6, LEVELS, color );

	        xFinale = gc.getWidth()/3;
	        countNumFinale = 25;
	        numFinale1 = (xFinale - options.getLungh()/2)/countNumFinale;
	        numFinale2 = (xFinale + levels.getLungh()/2)/countNumFinale;
			
			elements = new ArrayList<Ostacolo>();
			
			pang = new Sfondo( new Image( "/data/Image/pang.png" ), 0, 0, 0, 0, gc.getWidth(), gc.getHeight(), "pang" );
			
			try {
				documentFactory = DocumentBuilderFactory.newInstance();
	 
				builder = documentFactory.newDocumentBuilder();
				
				/* LETTURA FILE CONFIGURAZIONE TASTI */
				File levels = new File( "data/Configuration" );
				String[] files = levels.list();
				
				for(int j = 0; j < files.length; j++)
					{
						document = builder.parse( new File( "data/Configuration/" + files[j] ) );

						NodeList button = document.getElementsByTagName( "key" );
						
						Node node = button.item( 0 );
						Element ogg = (Element) node;
						
						int shoot = Integer.parseInt( ogg.getAttribute( "sparo" ) );
						int jump = Integer.parseInt( ogg.getAttribute( "salto" ) );
						int left = Integer.parseInt( ogg.getAttribute( "left" ) );
						int right = Integer.parseInt( ogg.getAttribute( "right" ) );
						Global.setMapInteger( j, jump, shoot, left, right );
						
						System.out.println( "tasti " + files[j] + " caricati" );
					}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
			//caricamento livelli da file .xml
			try {
				documentFactory = DocumentBuilderFactory.newInstance();
	 
				builder = documentFactory.newDocumentBuilder();
				
				/*	LETTURA LIVELLI DI GIOCO */
				File levels = new File( "data/livelli" );
				String[] files = levels.list();
				
				for(int j = 0; j < files.length; j++)
					{				
						//resetto il vettore e lo sfondo
						elements.clear();
						sfondo = null;
						
						document = builder.parse( new File( "data/livelli/" + files[j] ) );
			 
						NodeList name = document.getElementsByTagName( "livello" );
						NodeList ostacoli = document.getElementsByTagName( "ostacolo" );
						NodeList back = document.getElementsByTagName( "sfondo" );
						NodeList res = document.getElementsByTagName( "risoluzione" );
						Sfondo sfondo;
			 
						String tmp;
						for(int i = 0; i < ostacoli.getLength(); i++)
							{								
								Node nodo = ostacoli.item( i );
								
								Element obs = (Element) nodo;
								
								tmp = obs.getAttribute( "x" );
								int x = Integer.parseInt( tmp.substring( 0, tmp.length() - 2 ) );
								tmp = obs.getAttribute( "y" );
								int y = Integer.parseInt( tmp.substring( 0, tmp.length() - 2 ) );
								tmp = obs.getAttribute( "union" );
								int union = Integer.parseInt( tmp.substring( 0, tmp.length() ) );
								String type = obs.getAttribute( "ID" );
								String orienting = obs.getAttribute( "type" );
								String numPlayer = null;
								Color colour = null;
								if(type.equals( Global.PLAYER ))
									{
										numPlayer = obs.getAttribute( "number" );
										String c = obs.getAttribute( "color" );
										if(c.equals( "red" ))
											colour = Color.red;
										else if(c.equals( "blue" ))
											colour = Color.blue;
										else if(c.equals( "yellow" ))
											colour = Color.yellow;
										else
											colour = Color.green;
									}
								
								float ratioW = Global.ratioW;
								float ratioH = Global.ratioH;
								
								if(type.equals( Global.BOLLA ))
								    elements.add( new Bubble( (int) (x * ratioW), (int) (y * ratioH), gc.getWidth()/32, gc.getWidth(), gc ) );
								else if(type.equals( Global.SBARRA ))
								    {
									    elements.add( new Sbarra( (int) (x * ratioW), (int) (y * ratioH), orienting, gc ) );
                                        elements.get( elements.size() - 1 ).setSpigoli();
								    }
								else if(type.equals( Global.TUBO ))
                                    {
                                        elements.add( new Tubo( (int) (x * ratioW), (int) (y * ratioH), orienting, gc ) );
                                        ((Tubo) elements.get( elements.size() - 1 )).setSpace( gc );
                                        elements.get( elements.size() - 1 ).setUnion( union );
                                    }
								else if(type.equals( Global.PLAYER ))
									elements.add( new Player( (int) (x * ratioW), (int) (y * ratioH), Integer.parseInt( numPlayer ), gc, colour ) );
							}
						
						Node nodo = back.item( 0 );
						Element img = (Element) nodo;
						tmp = img.getAttribute( "name" );
						sfondo = new Sfondo( new Image( "./data/Image/" + tmp + ".jpg" ), gc.getHeight()*100/104, gc.getWidth(), 0, 0, gc.getWidth(), gc.getHeight(), tmp );

						Node var = name.item( 0 );
						Element node = (Element) var;
						tmp = node.getAttribute( "nome" );
						
						Node resolution = res.item( 0 );
						Element w = (Element) resolution;
						String lungh = w.getAttribute( "w" );
						
						Element h = (Element) resolution;
						String alt = h.getAttribute( "h" );
						
						if(Integer.parseInt( lungh ) != Global.W || Integer.parseInt( alt ) != Global.H)
							cambiaProporzioni( Integer.parseInt( lungh ), Integer.parseInt( alt ), gc );
						
						livelli.add( new Livello( elements, sfondo, tmp ) );
						
						System.out.println( "livello " + files[j] + " caricato" );
					}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
			cursor = new Image( "./data/Image/cursore.png" );
			
			buttons = new ArrayList<SimpleButton>();
			buttons.add( levels );
			buttons.add( options );

			widthC = gc.getWidth()*100/1777;
			heightC = gc.getHeight()/24;
			
			indexCursor = -1;
			
			checkRatioW = Global.ratioW;
			checkRatioH = Global.ratioH;
			
			showBegin = false;
			timeShowBegin = timeLimitBegin - 1;
		}
	
	public void cambiaProporzioni( float w, float h, GameContainer gc ) throws SlickException
		{
			float rappW = Global.Width/w, rappH = Global.Height/h;
		
			for(Ostacolo elem: elements)
				{
					elem.setXY( elem.getX() * rappW, elem.getY() * rappH, "restore" );
					if(elem.getID().equals( Global.TUBO ))
						((Tubo) elem).setSpace( gc );
					else
						elem.setArea( gc );
				}
		}

	public void draw( GameContainer gc ) throws SlickException
		{
			Graphics g = gc.getGraphics();
		
			pang.draw( gc );
		
	        if(insertButton)
    			for(SimpleButton button: buttons)
    				button.draw( g );
	        else
	        	{
	        		if(timeShowBegin == timeLimitBegin - 1)
	        			showBegin = !showBegin;
	        		if(showBegin)
	        			{
	        				g.setColor( Color.red );
        					g.drawString( BEGIN, gc.getWidth()*5/17, gc.getHeight()*5/6 );
        					g.setColor( Color.transparent );
	        			}

					timeShowBegin = (timeShowBegin + 1)%timeLimitBegin;
	        	}
			
			if(indexCursor >= 0)
				cursor.draw( buttons.get( indexCursor ).getX() - widthC, buttons.get( indexCursor ).getY(), widthC, heightC );
			
			Global.drawScreenBrightness( g );
		}
	
	// aggiorna le dimensioni al variare della risoluzione
	public void setStats() throws SlickException
		{
			for(SimpleButton button: buttons)
				button.buildButton( button.getX() * Global.ratioW, button.getY() * Global.ratioH );
			
			checkRatioW = Global.ratioW;
			checkRatioH = Global.ratioH;
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

	public void update(GameContainer gc, int delta) throws SlickException 
		{
			Input input = gc.getInput();
			int mouseX = input.getMouseX();
			int mouseY = input.getMouseY();
			
			if(checkRatioW != Global.ratioW || checkRatioH != Global.ratioH)
				setStats();
			
			//schermata iniziale del "premi un tasto qualsiasi"
			if(!insertButton)
    			for(int i = 0; i < 256; i++)
    			    if(input.isKeyPressed( i ))
                    	insertButton = true;
		
			if(!insertButton)
				if(input.isMousePressed( Input.MOUSE_LEFT_BUTTON ) || input.isMousePressed( Input.MOUSE_RIGHT_BUTTON ))
					insertButton = true;
			
			if(insertButton)
				{
					if(countNumFinale > 0)
						{
							options.setX( options.getX() + numFinale1 );
							levels.setX( levels.getX() - numFinale2 );
							
							countNumFinale--;
						}

					if(indexCursor < 0 &&((input.isKeyPressed( Input.KEY_UP ) || input.isKeyPressed( Input.KEY_DOWN )
					|| input.isKeyPressed( Input.KEY_LEFT ) || input.isKeyPressed( Input.KEY_RIGHT ))))
						indexCursor = 0;
					else if(input.isKeyPressed( Input.KEY_LEFT ))
						{
							if(--indexCursor < 0)
								indexCursor = buttons.size() - 1;
						}
					else if(input.isKeyPressed( Input.KEY_RIGHT ))
		            	indexCursor = (indexCursor + 1)%(buttons.size() - 1);
					
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
				                                			Start.begin = 0;
						                                	indexCursor = -1;
						                            		if(buttons.get( i ).getName().equals( OPTIONS ))
						                            			{
							                            			Start.settings = 1;
							                            			Start.chooseLevel = 0;
						                            			}
						                            		else if(buttons.get( i ).getName().equals( LEVELS ))
						                                        Start.chooseLevel = 1;
						                            		
								                            break;
							                            }
				                    			}
				                    	}
				                }
			            }
				}
		}
	
	private boolean checkKeyPressed( final Input input )
    	{ return input.isKeyDown( Input.KEY_ENTER ); }
}
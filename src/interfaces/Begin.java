package interfaces;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import Utils.Global;
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
	public SimpleButton editor, tasto, options;
	
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
	private int xFinale1, xFinale2;
	
	//immagine schermata iniziale
	private Sfondo pang;
	
	private float checkRatioW, checkRatioH;
	
	private boolean mouseDown = false;
	
	private static final String OPTIONS = "OPZIONI", LEVELS = "LIVELLI";
	
	public Begin( GameContainer gc ) throws SlickException
		{
	        insertButton = false;

	        xFinale1 = gc.getWidth()*10/25;
	        xFinale2 = gc.getWidth()*10/22;
	    
			livelli = new ArrayList<Livello>();
			
			Color color = Color.orange;
			options = new SimpleButton( 0, gc.getHeight()/4, OPTIONS, color );
			editor = new SimpleButton( gc.getWidth(), gc.getHeight()/2, LEVELS, color );
			
			elements = new ArrayList<Ostacolo>();
			
			pang = new Sfondo( new Image( "/data/Image/pang.png" ), 0, 0, 0, 0, gc.getWidth(), gc.getHeight(), "pang" );
			
			//caricamento livelli da file .xml
			try {
				documentFactory = DocumentBuilderFactory.newInstance();
	 
				builder = documentFactory.newDocumentBuilder();
				
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
								
								float ratioW = Global.ratioW;
								float ratioH = Global.ratioH;
								
								if(type.equals( "bolla" ))
								    elements.add( new Bubble( (int) (x * ratioW), (int) (y * ratioH), gc.getWidth()/32, gc.getWidth(), gc ) );
								else if(type.equals( "sbarra" ))
								    {
									    elements.add( new Sbarra( (int) (x * ratioW), (int) (y * ratioH), orienting, gc ) );
                                        elements.get( elements.size() - 1 ).setSpigoli();
								    }
								else if(type.equals( "tubo" ))
                                    {
                                        elements.add( new Tubo( (int) (x * ratioW), (int) (y * ratioH), orienting, gc ) );
                                        elements.get( elements.size() - 1 ).setSpigoli();
                                        elements.get( elements.size() - 1 ).setUnion( union );
                                    }
								else if(type.startsWith( "player" ))
									elements.add( new Player( (int) (x * ratioW), (int) (y * ratioH), Integer.parseInt( type.substring( type.length() - 1, type.length() ) ), gc ) );
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
						
						if(Integer.parseInt( lungh.substring( 0, lungh.length() ) ) != Global.currentW || Integer.parseInt( alt.substring( 0, alt.length() ) ) != Global.currentH)
							cambiaProporzioni( Integer.parseInt( lungh.substring( 0, lungh.length() ) ), Integer.parseInt( alt.substring( 0, alt.length() ) ) );
						
						livelli.add( new Livello( elements, sfondo, tmp ) );
						
						for(int i  = 0; i < livelli.get( livelli.size() - 1 ).getElements().size(); i++)
							System.out.println( "tipo ostacolo = " +  livelli.get( livelli.size() - 1 ).getElements().get( i ).getID() );
						
						System.out.println( "livello " + files[j] + " caricato" );
					}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
			cursor = new Image( "./data/Image/cursore.png" );
			
			buttons = new ArrayList<SimpleButton>();
			buttons.add( editor );
			buttons.add( options );

			widthC = gc.getWidth()*100/1777;
			heightC = gc.getHeight()/24;
			
			indexCursor = -1;
			
			checkRatioW = Global.ratioW;
			checkRatioH = Global.ratioH;
		}
	
	public void cambiaProporzioni( float w, float h )
		{
			float rappW = Global.Width/w, rappH = Global.Height/h;
		
			for(int i = 0; i < elements.size(); i++)
				{
					if(elements.get( i ).getID().equals( "bolla" ))
						elements.get( i ).setWidth( elements.get( i ).getWidth() * rappW );
					else
						{
							elements.get( i ).setWidth( elements.get( i ).getWidth() * rappW );
							elements.get( i ).setHeight( elements.get( i ).getHeight() * rappH );
						}
					
					elements.get( i ).setXY( elements.get( i ).getX() * rappW, elements.get( i ).getY() * rappH, "restore" );
				}
		}

	public void draw( GameContainer gc ) throws SlickException
		{
			pang.draw( gc );
		
	        if(insertButton)
    			for(int i = 0; i < buttons.size(); i++)
    				buttons.get( i ).draw( gc.getGraphics() );
			
			if(indexCursor >= 0)
				cursor.draw( buttons.get( indexCursor ).getX() - widthC, buttons.get( indexCursor ).getY(), widthC, heightC );
		}
	
	public void setStats()
		{
			for(int i = 0; i < buttons.size(); i++)
				{
					buttons.get( i ).setX( buttons.get( i ).getX() * Global.ratioW );
					buttons.get( i ).setY( buttons.get( i ).getY() * Global.ratioH );
				}

			xFinale1 = (int) (xFinale1 * Global.ratioW);
			xFinale2 = (int) (xFinale2 * Global.ratioH);
			
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
					float move = delta/2 * Global.ratioW;
					if(options.getX() + move < xFinale1)
						options.setX( options.getX() + move );
					else
						options.setX( xFinale1 );
					if(editor.getX() - move*3/2 > xFinale2)
						editor.setX( editor.getX() - move*3/2 );
					else
						editor.setX( xFinale2 );					

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
				                                    		Start.setPreviuosStats( "begin" ); 
						                            		if(buttons.get( i ).getName().equals( OPTIONS ))
							                            		Start.settings = 1;
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
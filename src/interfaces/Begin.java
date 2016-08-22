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

import DataEntites.Sfondo;
import bubbleMaster.Start;
import dataButton.SimpleButton;
import dataObstacles.Bubble;
import dataObstacles.Ostacolo;
import dataObstacles.Player;
import dataObstacles.Sbarra;
import dataObstacles.Tubo;

public class Begin 
{
	public SimpleButton editor, tasto, choose;
	
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
	
	public Begin( GameContainer gc ) throws SlickException
		{
	        insertButton = false;
	    
			livelli = new ArrayList<Livello>();
			
			editor = new SimpleButton( (int) (gc.getWidth()/(2.2)), (int) (gc.getWidth()/(2.2)), "EDIT", Color.orange );
			choose = new SimpleButton( (int) (gc.getWidth()/(2.5)), gc.getHeight()/4, "SCEGLI LIVELLO", Color.orange );
			
			elements = new ArrayList<Ostacolo>();
			
			//caricamento livelli da file .xml
			try {
				documentFactory = DocumentBuilderFactory.newInstance();
	 
				builder = documentFactory.newDocumentBuilder();
				
				File levels = new File( "data/livelli" );
				String[] files = levels.list();
				
				for(int j = 0; j < files.length; j++)
					{				
						document = builder.parse( new File( "data/livelli/" + files[j] ) );
			 
						NodeList ostacoli = document.getElementsByTagName( "ostacolo" );
						NodeList back = document.getElementsByTagName( "sfondo" );
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
								String type = obs.getAttribute( "ID" );	
								String orienting = obs.getAttribute( "type" );
								
								if(type.equals( "bolla" ))
								    elements.add( new Bubble( x, y, 25, gc.getWidth() ) );
								else if(type.equals( "sbarra" ))
								    {
									    elements.add( new Sbarra( x, y, orienting ) );
                                        elements.get( elements.size() - 1 ).setSpigoli();
								    }
								else if(type.equals( "tubo" ))
                                    {
                                        elements.add( new Tubo( x, y, orienting ) );
                                        elements.get( elements.size() - 1 ).setSpigoli();
                                    }
								else if(type.startsWith( "player" ))
									elements.add( new Player( x, y, 1 ) );
							}
						
						Node nodo = back.item( 0 );
						Element img = (Element) nodo;
						tmp = img.getAttribute( "index" );
						System.out.println( "./data/Image/sfondo" + (Integer.parseInt( tmp ) + 1) + ".jpg" );
						sfondo = new Sfondo( new Image( "./data/Image/sfondo" + (Integer.parseInt( tmp ) + 1) + ".jpg" ), gc.getHeight()/(1.04), gc.getWidth() );
						
						Begin.livelli.add( new Livello( elements, sfondo ) );
						
						System.out.println( "livello " + files[j] + " caricato" );
					}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
			cursor = new Image( "./data/Image/cursore.png" );
			
			buttons = new ArrayList<SimpleButton>();
			buttons.add( choose );
			buttons.add( editor );			

			widthC = 45;
			heightC = 25;
			
			indexCursor = -1;
		}

	public void draw( Graphics g ) throws SlickException
		{
	        if(insertButton)
    			for(int i = 0; i < buttons.size(); i++)
    				buttons.get( i ).draw( g );
			
			if(indexCursor >= 0)
				cursor.draw( buttons.get( indexCursor ).getX() - widthC, buttons.get( indexCursor ).getY(), widthC, heightC );
		}

	public void update(GameContainer gc, int delta) throws SlickException 
		{
			Input input = gc.getInput();
			int mouseX = input.getMouseX();
			int mouseY = input.getMouseY();
			
			//schermata iniziale del "premi un tasto qualsiasi"
			if(!insertButton)
    			for(int i = 0; i < 256; i++)
    			    if(input.isKeyPressed( i ))
                        insertButton = true;
			
			if((input.isKeyPressed( Input.KEY_UP ) || input.isKeyPressed( Input.KEY_DOWN ) || input.isKeyPressed( Input.KEY_LEFT ) || input.isKeyPressed( Input.KEY_RIGHT )))
				{
					if(indexCursor < 0)
						indexCursor = 0;
					else if(indexCursor == 0)
						indexCursor = 1;
					else
						indexCursor = 0;
				}

			if((editor.checkClick( mouseX, mouseY ) && input.isMousePressed( Input.MOUSE_LEFT_BUTTON )) || (indexCursor == 1 && input.isKeyPressed( Input.KEY_ENTER )))
				{
					indexCursor = -1;
					Start.begin = 0;
					Start.editGame = 1;
					Start.setPreviuosStats( "begin" );
				}
			else if((choose.checkClick( mouseX, mouseY ) && input.isMousePressed( Input.MOUSE_LEFT_BUTTON )) || (indexCursor == 0 && input.isKeyPressed( Input.KEY_ENTER )))
				{
					if(livelli.size() > 0)
						{
							indexCursor = -1;
							Start.begin = 0;
							Start.chooseLevel = 1;
							Start.setPreviuosStats( "begin" );
						}
				}
		}
}
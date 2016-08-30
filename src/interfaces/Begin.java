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
import dataButton.SimpleButton;
import dataObstacles.Bubble;
import dataObstacles.Ostacolo;
import dataObstacles.Player;
import dataObstacles.Sbarra;
import dataObstacles.Tubo;

public class Begin 
{
	public SimpleButton editor, tasto, choose, newLvl;
	
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
	private int xFinale1, xFinale2, yFinale3;
	
	//immagine schermata iniziale
	private Sfondo pang;
	
	public Begin( GameContainer gc ) throws SlickException
		{
	        insertButton = false;

	        xFinale1 = gc.getWidth()*10/25;
	        xFinale2 = gc.getWidth()*10/22;
	        yFinale3 = gc.getHeight()*3/4;
	    
			livelli = new ArrayList<Livello>();
			
			Color color = Color.orange;
			choose = new SimpleButton( 0, gc.getHeight()/4, "LIVELLI", color );
			editor = new SimpleButton( gc.getWidth(), gc.getHeight()/2, "OPZIONI", color );
			newLvl = new SimpleButton( gc.getWidth()*10/24, gc.getHeight(), "CREA LIVELLO", color );
			
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
								else if(type.equals( "player1" ))
									elements.add( new Player( (int) (x * ratioW), (int) (y * ratioH), 1, gc ) );
								else if(type.equals( "player2" ))
									elements.add( new Player( (int) (x * ratioW), (int) (y * ratioH), 2, gc ) );
							}
						
						Node nodo = back.item( 0 );
						Element img = (Element) nodo;
						tmp = img.getAttribute( "name" );
						sfondo = new Sfondo( new Image( "./data/Image/" + tmp + ".jpg" ), gc.getHeight()/(1.04), gc.getWidth(), 0, 0, gc.getWidth(), gc.getHeight(), tmp );

						Node var = name.item( 0 );
						Element node = (Element) var;
						tmp = node.getAttribute( "nome" );
						
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
			buttons.add( choose );
			buttons.add( editor );	
			//buttons.add( newLvl );

			widthC = gc.getWidth()*100/1777;
			heightC = gc.getHeight()/24;
			
			indexCursor = -1;
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
		
			if(!insertButton)
				if(input.isMousePressed( Input.MOUSE_LEFT_BUTTON ) || input.isMousePressed( Input.MOUSE_RIGHT_BUTTON ))
					insertButton = true;
			
			if(insertButton)
				{
					float move = delta/2 * Global.ratioW;
					if(choose.getX() + move < xFinale1)
						choose.setX( choose.getX() + move );
					else
						choose.setX( xFinale1 );
					if(editor.getX() - move*3/2 > xFinale2)
						editor.setX( editor.getX() - move*3/2 );
					else
						editor.setX( xFinale2 );
					if(newLvl.getY() - move/2 > yFinale3)
						newLvl.setY( newLvl.getY() - move/2 );
					else
						newLvl.setY( yFinale3 );
				}
			
			if(insertButton)
				{
					if((input.isKeyPressed( Input.KEY_UP ) || input.isKeyPressed( Input.KEY_DOWN ) || input.isKeyPressed( Input.KEY_LEFT ) || input.isKeyPressed( Input.KEY_RIGHT )))
						{
							if(indexCursor < 0)
								indexCursor = 0;
							else if(indexCursor == 0)
								indexCursor = 1;
							else
								indexCursor = 0;
						}
		
					if((editor.checkClick( mouseX, mouseY ) && input.isMousePressed( Input.MOUSE_LEFT_BUTTON )) || (indexCursor >= 0 && buttons.get( indexCursor ).getName().equals( "EDITOR" ) && input.isKeyPressed( Input.KEY_ENTER )))
						{
							indexCursor = -1;
							Start.begin = 0;
							Start.editGame = 1;
							Start.setPreviuosStats( "begin" );
						}
					else if((choose.checkClick( mouseX, mouseY ) && input.isMousePressed( Input.MOUSE_LEFT_BUTTON )) || (indexCursor >= 0 && buttons.get( indexCursor ).getName().equals( "SCEGLI LIVELLO" ) && input.isKeyPressed( Input.KEY_ENTER )))
						{
							if(livelli.size() > 0)
								{
									indexCursor = -1;
									Start.begin = 0;
									Start.chooseLevel = 1;
									Start.setPreviuosStats( "begin" );
								}
						}
					else if((newLvl.checkClick( mouseX, mouseY ) && input.isMousePressed( Input.MOUSE_LEFT_BUTTON )) || (indexCursor >= 0 && buttons.get( indexCursor ).getName().equals( "CREA LIVELLO" ) && input.isKeyPressed( Input.KEY_ENTER )))
						{
							indexCursor = -1;
							Start.begin = 0;
							//Start.creaLvl = 1;
							Start.setPreviuosStats( "begin" );
						}
				}
		}
}
package interfaces;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

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

import Utils.Global;
import Utils.KeyButton;
import bubbleMaster.Start;
import dataButton.ArrowButton;
import dataButton.Button;
import dataButton.SimpleButton;

public class Configurations
{
	// bottoni e vettore bottoni
	private ArrayList<SimpleButton> buttons;
	private SimpleButton back, apply;
	private ArrayList<ArrowButton> arrows;
	private ArrowButton left, right;
	
	// lo sfondo
	Image img;
	
	// determina se e' possibile effettuare i cambiamenti
	private boolean setChanging;
	
	// determina se e' stato effettuato un click
	private boolean mouseDown = false;
	
	// i nomi dei bottoni
	private static final String BACK = "INDIETRO", APPLY = "APPLICA";
	
	// nome dei tasti in game
	private static final String SALTO = "Salto:", SPARO = "Sparo:", LEFT = "Sinistra:", RIGHT = "Destra:";
	
	/*indice di posizionamento del cursore*/
	private int indexCursor;
	/*dimensioni del cursore*/
	private int widthC, heightC;
	/*immagine del cursore*/
	private Image cursor;
	/*l'indice del player configurato*/
	private int numPlayer;
	
	private ArrayList<KeyButton> keys;
	private KeyButton kSalto, kSparo, kSx, kDx;
	
	//elementi riguardanti la scrittura su file .xml
	private Element livello;
	private Document document;
	
	// coordinata x e y in cui scrivere nome e lettera bindata
	float xString = Global.W*10/45, yString = Global.H/5;
	
	// le mappe dei tasti
	private ArrayList<Map<String, Integer>> maps;

	public Configurations() throws SlickException
		{
			img = new Image( "data/Image/binding.png" );
		
			Color color = Color.orange;
			back = new SimpleButton( Global.W/5, Global.H*8/9, BACK, color );
			apply = new SimpleButton( Global.W*2/3, Global.H*8/9, APPLY, color );
		
			buttons = new ArrayList<SimpleButton>();
			buttons.add( back );
			buttons.add( apply );
			
			setChanging = false;
			
			indexCursor = -1;
			widthC = Global.W*100/1777;
			heightC = Global.H/24;			
			cursor = new Image( "./data/Image/cursore.png" );
			
			numPlayer = 1;
			
			int width = Global.W/20, height = Global.H/50;			
			left = new ArrowButton( LEFT, ArrowButton.LEFT, new float[]{ Global.W*10/32, Global.H/40 + height/2, Global.W*10/32 + width, Global.H/40, Global.W*10/32 + width, Global.H/40 + height }, Color.white );
			right = new ArrowButton( RIGHT, ArrowButton.RIGHT, new float[]{ Global.W*10/16, Global.H/40, Global.W*10/16, Global.H/40 + height, Global.W*10/16 + width, Global.H/40 + height/2 },Color.white );
			
			arrows = new ArrayList<ArrowButton>();
			arrows.add( left );
			arrows.add( right );

			float widthK = Global.W/20;
			kSalto = new KeyButton( xString + Global.W/10, yString, widthK );
			kSparo = new KeyButton( xString + Global.W/3 + Global.W*10/98, yString, widthK );
			yString = Global.H/2;
			kSx = new KeyButton( xString + Global.W/8, yString, widthK );
			kDx = new KeyButton( xString + Global.W/3 + Global.W*10/94, yString, widthK );
			
			keys = new ArrayList<KeyButton>();
			keys.add( kSalto );
			keys.add( kSparo );
			keys.add( kSx );
			keys.add( kDx );
			
			maps = Global.getMapButton();
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
	
	private int checkArrow( ArrowButton button, Input input, int i )
		{
			if(button.isPressed())
				return 1;
			else if(indexCursor >= 0 && indexCursor == i)
				if(input.isKeyPressed( Input.KEY_ENTER ))
					return 2;
		
			return 0;
		}
	
	/** aggiorna il file relativo alle configurazioni tasti
	 * e aggiorna anche Global.mapsButton */
	public void updateFileConfig( int index )
		{
			try
				{
				    livello = new Element( "level" );
				    document = new Document( livello );
				    
				    XMLOutputter outputter = new XMLOutputter();
					// imposta un bel formato all'outputter 
					outputter.setFormat( Format.getPrettyFormat() );
					// creazione del file xml con il nome scelto
		
					File levels = new File( "data/Configuration" );
					String[] files = levels.list();
					
					File conf = new File( "data/Configuration/" + files[0] );
					if(conf.delete())
						System.out.println( "file eliminato" );
	
					Element item;
				    livello.addContent( new Comment( "Objects" ) );
					
				    for(int i = 0; i < maps.size(); i++)
				    	{
							Map<String, Integer> tmp = maps.get( i );
							
							item = new Element( "key" );
							item.setAttribute( "sparo", tmp.get( "Sparo" ) + "" );
							item.setAttribute( "salto", tmp.get( "Salto" ) + "" );
							item.setAttribute( "left", tmp.get( "Sx" ) + "" );
							item.setAttribute( "right", tmp.get( "Dx" ) + "" );
							livello.addContent( item );
				    	}
	
		    		outputter.output( document, new FileOutputStream( "data/Configuration/keyButton.xml" ) );
		    		
		    		System.out.println( "tasti " + "player" + (index+1) + ".xml salvati" );
				}
			catch( IOException e )
				{
					System.err.println( "Error while creating the level" );
					e.printStackTrace();
				}

			for(int i = 0; i < maps.size(); i++)
				{
					Global.mapButtons.get( i ).put( "Salto", maps.get( i ).get( "Salto" ) );
					Global.mapButtons.get( i ).put( "Sparo", maps.get( i ).get( "Sparo" ) );
					Global.mapButtons.get( i ).put( "Sx", maps.get( i ).get( "Sx" ) );
					Global.mapButtons.get( i ).put( "Dx", maps.get( i ).get( "Dx" ) );
				}
		}
	
	/** controlla gli input ricevuti e lo assegna al bottone selezionato
	 * aggiornando maps */
	public void checkInput( Input in, int index )
		{
			for(int i = 0; i < 255; i++)
				if(in.isKeyPressed( i ) && checkUniqueValue( i ))
					{
						keys.get( index ).setKey( Input.getKeyName( i ) );
						if(index == 0)
							maps.get( index ).put( "Salto", i );
						else if(index == 1)
							maps.get( index ).put( "Sparo", i );
						else if(index == 2)
							maps.get( index ).put( "Sx", i );
						else
							maps.get( index ).put( "Dx", i );
									
						resetSelected();
						return;
					}
		}
	
	/** resetta il bottone selezionato */
	public void resetSelected()
		{
			for(KeyButton key: keys)
	        	if(key.isSelected())
	        		key.setSelected();
		}
	
	/** aggiorna i tasti del giocatore caricato */
	public void updateKeys( int index, Input input )
		{
			keys.get( 0 ).setKey( Input.getKeyName( ( maps.get( index ).get( "Salto" ) ) ) );
			keys.get( 1 ).setKey( Input.getKeyName( ( maps.get( index ).get( "Sparo" ) ) ) );
			keys.get( 2 ).setKey( Input.getKeyName( ( maps.get( index ).get( "Sx" ) ) ) );
			keys.get( 3 ).setKey( Input.getKeyName( ( maps.get( index ).get( "Dx" ) ) ) );
		}
	
	/** controlla se il nuovo tasto configurato non sia gia' stato bindato */
	public boolean checkUniqueValue( int code )
		{
			for(int j = 0; j < maps.size(); j++)
				if(maps.get( j ).get( "Sparo" ) == code || maps.get( j ).get( "Sx" ) == code || 
				   maps.get( j ).get( "Salto" ) == code || maps.get( j ).get( "Dx" ) == code)
					return false;
		
			return true;
		}
	
	/** controlla se e' stato modificato un qualunque bind */
	public boolean checkDifference()
		{
			for(int i = 0; i < maps.size(); i++)
				{
					if(maps.get( i ).get( "Sparo" ) != Global.mapButtons.get( i ).get( "Sparo" )
					|| maps.get( i ).get( "Salto" ) != Global.mapButtons.get( i ).get( "Salto" )
					|| maps.get( i ).get( "Sx" ) != Global.mapButtons.get( i ).get( "Sx" )
					|| maps.get( i ).get( "Dx" ) != Global.mapButtons.get( i ).get( "Dx" ))
						return true;
				}
			
			return false;
		}
	
	/** aggiorna gli oggetti alle nuove proporzioni */
	public void updateDates() throws SlickException
		{	
			for(SimpleButton button: buttons)
				button.buildButton( button.getX() * Global.ratioW, button.getY() * Global.ratioH );
			
			for(ArrowButton arrow: arrows)
				arrow.translate( Global.ratioW, Global.ratioH );
			
			for(KeyButton key: keys)
				key.updateDates();
		}
	
	public void update( GameContainer gc )
		{
			Input input = gc.getInput();
			int mouseX = input.getMouseX();
			int mouseY = input.getMouseY();

			// se e' stato modificato un qualunque bind viene permesso di applicare tale cambiamenti
			if(checkDifference())
				{
					setChanging = true;
					buttons.get( 1 ).setColor( Color.orange );
				}
			else
				{
					setChanging = false;
					buttons.get( 1 ).setColor( Color.gray );
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

			for(int i = 0; i < keys.size(); i++)
				if(keys.get( i ).isSelected())
					checkInput( input, i );
			
			if(input.isMouseButtonDown( Input.MOUSE_LEFT_BUTTON ))
				{
	                if(!mouseDown)
		                {
		                    mouseDown = true;
		                    
		                    for(KeyButton key: keys)
		                    	if(key.contains( mouseX, mouseY ))
		                    		{
		                    			resetSelected();
		                    			key.setSelected();
		                    			break;
		                    		}
		                    
		                    for(SimpleButton button : buttons)
		                        if(button.checkClick( mouseX, mouseY, input ))
		                        	if(button.isClickable() && !button.isPressed())
	                            		button.setPressed();
		                    
		                    for(ArrowButton arrow: arrows)
		                    	if(arrow.contains( mouseX, mouseY, input ))
		                    		if(!arrow.isPressed())
		                    			arrow.setPressed();
		                }
	            }
	        else
	            {
	                if(mouseDown || checkKeyPressed( input ))
		                {
		                    mouseDown = false;
		                    int i = 0;
		                    for(i = 0; i < buttons.size(); i++)
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
					                            			resetSelected();
					                                		indexCursor = -1;
		                            						Start.configuration = 0;
		                            						Start.settings = 1;
				                            			}
				                            		else if(buttons.get( i ).getName().equals( APPLY ))
				                            			{
				                            				if(setChanging)
			                            						{
				                            						setChanging = false;

				                            						for(int j = 0; j < maps.size(); j++)
				                            							updateFileConfig( j );
				                            						
				                            						resetSelected();
				                            			        
				                            						indexCursor = -1;
				                            						Start.configuration = 0;
				                            						Start.settings = 1;
				                            					}
				                            			}
				                            		
						                            break;
					                            }
		                    			}
		                    	}
		                    if(i == buttons.size())
			                    // se non e' stato premuto un bottone controllo le frecce
		                    	for(i = 0; i < arrows.size(); i++)
		                    		{
			                    		int value = checkArrow( arrows.get( i ), input, i );
			                        	boolean pressed = true;
			                        	// se e' stato premuto il tasto
			                    		if(value > 0)
			                    			{
				                                for(ArrowButton arrow: arrows)
				                                	if(arrow.isPressed())
				                                		arrow.setPressed();
				                                pressed = arrows.get( i ).contains( mouseX, mouseY, input );
					                            // pressed tramite mouse || value==2 tramite tastiera
					                            if(pressed || value == 2)
						                            {
			                                    		// premuta freccia sinistra
					                            		if(arrows.get( i ).getDirection() == ArrowButton.LEFT)
					                            			{
					                            				int oldNum = numPlayer;
					                            				numPlayer = Math.max( 1, --numPlayer );
					                            				if(oldNum != numPlayer)
					                            					updateKeys( numPlayer-1, input );
					                            			}
					                            		// premuta freccia destra
					                            		else if(arrows.get( i ).getDirection() == ArrowButton.RIGHT)
					                            			{
					                            				int oldNum = numPlayer;
					                            				numPlayer = Math.min( 4, ++numPlayer );
					                            				if(oldNum != numPlayer)
					                            					updateKeys( numPlayer-1, input );
					                            			}
					                            		
							                            break;
						                            }
			                    			}
		                    		}
		                }
	            }
		}
	
	private boolean checkKeyPressed( final Input input )
	    {
	        return input.isKeyDown( Input.KEY_ENTER ) ||
	               input.isKeyDown( Input.KEY_RIGHT ) ||
	               input.isKeyDown( Input.KEY_LEFT );
	    }
	
	public void draw( GameContainer gc )
		{
			Graphics g = gc.getGraphics();
			img.draw( 0, 0, Global.W, Global.H );
			
			g.setColor( Color.gray );
			for(SimpleButton button: buttons)
				button.draw( g );
			for(ArrowButton arrow: arrows)
				arrow.draw( g );
			
			g.setColor( Color.white );
			g.scale( Global.W/Global.Width, Global.H/Global.Height );
			g.drawString( "Player " + numPlayer, Global.W*10/22*Global.Width/Global.W, Global.H/40*Global.Height/Global.H );
			g.resetTransform();
			
			if(indexCursor >= 0)
				cursor.draw( buttons.get( indexCursor ).getX() - widthC, buttons.get( indexCursor ).getY(), widthC, heightC );
			
			yString = Global.H/5;
			g.scale( Global.W/Global.Width, Global.H/Global.Height );
			g.drawString( SALTO, xString*Global.Width/Global.W, yString*Global.Height/Global.H );
			g.drawString( SPARO, (xString + Global.W/3)*Global.Width/Global.W, yString*Global.Height/Global.H );			

			yString = Global.H/2;
			g.drawString( LEFT, xString*Global.Width/Global.W, yString*Global.Height/Global.H );
			g.drawString( RIGHT, (xString + Global.W/3)*Global.Width/Global.W, yString*Global.Height/Global.H );
			g.resetTransform();

			for(KeyButton key: keys)
				key.draw( g );
		}
}

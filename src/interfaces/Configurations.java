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
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import Utils.Global;
import Utils.KeyButton;
import dataButton.ArrowButton;

public class Configurations
{
	// bottoni e vettore bottoni
	private ArrayList<ArrowButton> arrows;
	private ArrowButton left, right;
	
	// lo sfondo
	//Image img;
	
	private boolean isChanged = false;
	
	// determina se e' stato effettuato un click
	private boolean mouseDown = false;
	
	// nome dei tasti in game
	private static final String SALTO = "Salto:", SPARO = "Sparo:", LEFT = "Sinistra:", RIGHT = "Destra:";
	
	/*indice di posizionamento del cursore*/
	private int indexCursor;
	/*l'indice del player configurato*/
	private int numPlayer;
	
	private ArrayList<KeyButton> keys;
	private KeyButton kSalto, kSparo, kSx, kDx;
	
	//elementi riguardanti la scrittura su file .xml
	private Element livello;
	private Document document;
	
	// coordinata x e y in cui scrivere nome e lettera bindata
	float xString = Global.W*10/45, yString = Global.H/9 + 4*Global.H*10/75;
	int sum = Global.H/90;
	
	// le mappe dei tasti
	private ArrayList<Map<String, Integer>> maps;

	public Configurations() throws SlickException
		{
			indexCursor = -1;
			
			numPlayer = 0;
			
			// TODO SETTARE CORRETTAMENTE LE POSIZIONI DI BOTTONI, FRECCE E KEYS
			
			int width = Global.W/20, height = Global.H/50;
			float yStart = Global.H/9 + 4*Global.H*10/75;
			left = new ArrowButton( LEFT, ArrowButton.LEFT, new float[]{ Global.W*10/32, yStart + height/2, Global.W*10/32 + width, yStart, Global.W*10/32 + width, yStart + height }, Color.white );
			right = new ArrowButton( RIGHT, ArrowButton.RIGHT, new float[]{ Global.W*10/16, yStart, Global.W*10/16, yStart + height, Global.W*10/16 + width, yStart + height/2 },Color.white );
			
			arrows = new ArrayList<ArrowButton>();
			arrows.add( left );
			arrows.add( right );

			float widthK = Global.W/20;
			kSalto = new KeyButton( xString + Global.W/10, yString + 5*sum, widthK );
			kSparo = new KeyButton( xString + Global.W/3 + Global.W*10/98, yString + 5*sum, widthK );

			kSx = new KeyButton( xString + Global.W/10, yString + 13*sum, widthK );
			kDx = new KeyButton( xString + Global.W/3 + Global.W*10/94, yString + 13*sum, widthK );
			
			keys = new ArrayList<KeyButton>();
			keys.add( kSalto );
			keys.add( kSparo );
			keys.add( kSx );
			keys.add( kDx );
			
			maps = Global.getMapButton();
		}
	
	public void resetInterface( Input input )
		{
			numPlayer = 0;
			updateKeys( 0, input );
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
	public void updateFileConfig()
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
		    		
		    		System.out.println( "tasti salvati in keyButton.xml" );
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
	
	/** controlla a quale altro player e' stato assegnato quel tasto e assegna quello vecchio. */
	private void checkDuplicatedKey( int code, int oldCode, int index )
		{
			for(int j = 0; j < maps.size(); j++) {
				Map<String, Integer> map = maps.get( j );
				if(map.get( "Sparo" ) == code) { map.put( "Sparo", oldCode ); if(index == j) keys.get( 0 ).setKey( Input.getKeyName( oldCode ) ); }
				if(map.get( "Salto" ) == code) { map.put( "Salto", oldCode ); if(index == j) keys.get( 1 ).setKey( Input.getKeyName( oldCode ) ); }
				if(map.get( "Sx" ) == code) { map.put( "Sx", oldCode ); if(index == j) keys.get( 2 ).setKey( Input.getKeyName( oldCode ) ); }
				if(map.get( "Dx" ) == code) { map.put( "Dx", oldCode ); if(index == j) keys.get( 3 ).setKey( Input.getKeyName( oldCode ) ); }
			}
		}
	
	/** controlla gli input ricevuti e lo assegna al bottone selezionato
	 * aggiornando maps */
	public void checkInput( Input in, int index )
		{
			for(int i = 0; i < 255; i++)
				if(in.isKeyPressed( i ))
					{
						int oldCode;
						if(index == 0) oldCode = maps.get( numPlayer ).get( "Salto" );
						else if(index == 1) oldCode = maps.get( numPlayer ).get( "Sparo" );
						else if(index == 2) oldCode = maps.get( numPlayer ).get( "Sx" );
						else oldCode = maps.get( numPlayer ).get( "Dx" );
						
						checkDuplicatedKey( i, oldCode, numPlayer );
						
						// Assegna il valore al giocatore selezionato.
						keys.get( index ).setKey( Input.getKeyName( i ) );
						
						if(index == 0) maps.get( numPlayer ).put( "Salto", i );
						else if(index == 1) maps.get( numPlayer ).put( "Sparo", i );
						else if(index == 2) maps.get( numPlayer ).put( "Sx", i );
						else maps.get( numPlayer ).put( "Dx", i );
						
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
	
	/** controlla se e' stato modificato un bind */
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
			for(ArrowButton arrow: arrows)
				arrow.translate( Global.ratioW, Global.ratioH );
			
			for(KeyButton key: keys)
				key.updateDates();
		}
	
	public boolean isChanged() { return isChanged; }
	
	public void update( Input input, int mouseX, int mouseY )
		{
			isChanged = checkDifference();
			
			if(indexCursor < 0 &&((input.isKeyPressed( Input.KEY_UP ) || input.isKeyPressed( Input.KEY_DOWN )
			|| input.isKeyPressed( Input.KEY_LEFT ) || input.isKeyPressed( Input.KEY_RIGHT ))))
				indexCursor = 0;

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
		                    
		                    for(ArrowButton arrow: arrows)
		                    	if(arrow.contains( mouseX, mouseY, input )) {
		                    		if(!arrow.isPressed())
		                    			arrow.setPressed();
		                    	}
		                }
	            }
	        else
	            {
	                if(mouseDown || checkKeyPressed( input ))
		                {
	                		mouseDown = false;
		                    int i = 0;
		                    
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
				                            				numPlayer = Math.max( 0, --numPlayer );
				                            				if(oldNum != numPlayer)
				                            					updateKeys( numPlayer, input );
				                            			}
				                            		// premuta freccia destra
				                            		else if(arrows.get( i ).getDirection() == ArrowButton.RIGHT)
				                            			{
				                            				int oldNum = numPlayer;
				                            				numPlayer = Math.min( 3, ++numPlayer );
				                            				if(oldNum != numPlayer)
				                            					updateKeys( numPlayer, input );
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
			//img.draw( 0, 0, Global.W, Global.H );
			
			g.setColor( Color.gray );
			for(ArrowButton arrow: arrows)
				arrow.draw( g );
			
			g.setColor( Color.white );
			g.scale( Global.W/Global.Width, Global.H/Global.Height );
			g.drawString( "Player " + (numPlayer+1), left.getMaxX() + (right.getX() - left.getMaxX())/2 - Global.W/30, left.getY() - left.getHeight()/2 );
			g.resetTransform();

			g.scale( Global.W/Global.Width, Global.H/Global.Height );
			g.drawString( SALTO, xString*Global.Width/Global.W, kSalto.getY()*Global.Height/Global.H );
			g.drawString( SPARO, (xString + Global.W/3)*Global.Width/Global.W, kSparo.getY()*Global.Height/Global.H );

			g.drawString( LEFT, xString*Global.Width/Global.W, kSx.getY()*Global.Height/Global.H );
			g.drawString( RIGHT, (xString + Global.W/3)*Global.Width/Global.W, kDx.getY()*Global.Height/Global.H );
			g.resetTransform();

			for(KeyButton key: keys)
				key.draw( g );
		}
}

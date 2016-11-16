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
	
	// determina se e' stato effettuato un click
	private boolean mouseDown = false;
	
	// nome dei tasti in game
	private static final String SALTO = "Salto:", SPARO = "Sparo:", LEFT = "Sinistra:", RIGHT = "Destra:";
	private static final String JUMP = "Salto", SHOT = "Sparo", SX = "Sx", DX = "Dx";
	
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
	private final float xString = Global.Width*10/45;
	//private final float yString = Global.Height/9 + 4*Global.Height*10/75;
	private final float sum = Global.Height/90;
	
	// le mappe dei tasti
	private ArrayList<Map<String, Integer>> maps;

	public Configurations() throws SlickException
		{
			numPlayer = 0;
			
			float width = Global.Width/20, height = Global.Height/50;
			float dist = Global.Height*10/75, yStart = Global.Height/9 + 7*dist/2;
			left = new ArrowButton( LEFT, ArrowButton.LEFT, new float[]{ Global.Width*10/32, yStart + height/2, Global.Width*10/32 + width, yStart, Global.Width*10/32 + width, yStart + height }, Color.white, 0 );
			right = new ArrowButton( RIGHT, ArrowButton.RIGHT, new float[]{ Global.Width*10/16, yStart, Global.Width*10/16, yStart + height, Global.Width*10/16 + width, yStart + height/2 },Color.white, 1 );
			
			arrows = new ArrayList<ArrowButton>();
			arrows.add( left );
			arrows.add( right );
			
			//System.out.println( "W: " + widthK + ", OLD: " + (Global.Width/20) );
			final float heightK = Global.Width/25;
			kSalto = new KeyButton( xString + Global.Width/10, left.getY() + 8*sum, heightK, JUMP );
			kSparo = new KeyButton( xString + Global.Width/3 + Global.Width*10/98, left.getY() + 8*sum, heightK, SHOT );
			
			kSx = new KeyButton( xString + Global.Width/10, left.getY() + 16*sum, heightK, SX );
			kDx = new KeyButton( xString + Global.Width/3 + Global.Width*10/98, left.getY() + 16*sum, heightK, DX );
			
			keys = new ArrayList<KeyButton>();
			keys.add( kSalto );
			keys.add( kSparo );
			keys.add( kSx );
			keys.add( kDx );
			
			maps = Global.getMapButton();
		}
	
	public void resetInterface()
		{
			numPlayer = 0;
			resetKeys( 0 );
			resetSelected();
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
					
				    for(Map<String, Integer> map: maps)
				    	{							
							item = new Element( "key" );
							item.setAttribute( "sparo", map.get( "Sparo" ) + "" );
							item.setAttribute( "salto", map.get( "Salto" ) + "" );
							item.setAttribute( "left", map.get( "Sx" ) + "" );
							item.setAttribute( "right", map.get( "Dx" ) + "" );
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
					Global.mapButtons.get( i ).put( JUMP, maps.get( i ).get( JUMP ) );
					Global.mapButtons.get( i ).put( SHOT, maps.get( i ).get( SHOT ) );
					Global.mapButtons.get( i ).put( SX, maps.get( i ).get( SX ) );
					Global.mapButtons.get( i ).put( DX, maps.get( i ).get( DX ) );
				}
		}
	
	/** controlla a quale altro player e' stato assegnato quel tasto e effettua con esso lo scambio */
	private void checkDuplicatedKey( int code, int oldCode, KeyButton key )
		{
			for(Map<String, Integer> map: maps)
				{
					if(map.get( SHOT ) == code) { map.put( SHOT, oldCode ); key.setKey( Input.getKeyName( oldCode ) ); break; }
					if(map.get( JUMP ) == code) { map.put( JUMP, oldCode ); key.setKey( Input.getKeyName( oldCode ) ); break; }
					if(map.get( SX ) == code) { map.put( SX, oldCode ); key.setKey( Input.getKeyName( oldCode ) ); break; }
					if(map.get( DX ) == code) { map.put( DX, oldCode ); key.setKey( Input.getKeyName( oldCode ) ); break; }
				}
		}
	
	/** controlla gli input ricevuti e lo assegna al bottone selezionato
	 * aggiornando maps */
	public boolean checkInput( Input in, KeyButton key )
		{
			for(int i = 0; i < 255; i++)
				if(in.isKeyPressed( i ))
					{
						checkDuplicatedKey( i, maps.get( numPlayer ).get( key.getName() ), key );
						
						// Assegna il valore al giocatore selezionato
						key.setKey( Input.getKeyName( i ) );
						
						maps.get( numPlayer ).put( key.getName(), i );
						
						updateKeys( numPlayer, in );
						
						resetSelected();
						return true;
					}
			return false;
		}
	
	/** resetta il bottone selezionato */
	public void resetSelected()
		{
			for(KeyButton key: keys)
	        	if(key.isSelected())
	        		key.setSelected();
		}
	
	/** resetta i tasti all'ultima configurazione salvata */
	public void resetKeys( int index )
		{
			for(int i = 0; i < maps.size(); i++)
				{
					maps.get( i ).put( JUMP, Global.mapButtons.get( i ).get( JUMP ) );
					maps.get( i ).put( SHOT, Global.mapButtons.get( i ).get( SHOT ) );
					maps.get( i ).put( SX, Global.mapButtons.get( i ).get( SX ) );
					maps.get( i ).put( DX, Global.mapButtons.get( i ).get( DX ) );
				}

			keys.get( 0 ).setKey( Input.getKeyName( ( maps.get( index ).get( JUMP ) ) ) );
			keys.get( 1 ).setKey( Input.getKeyName( ( maps.get( index ).get( SHOT ) ) ) );
			keys.get( 2 ).setKey( Input.getKeyName( ( maps.get( index ).get( SX ) ) ) );
			keys.get( 3 ).setKey( Input.getKeyName( ( maps.get( index ).get( DX ) ) ) );
		}
	
	/** aggiorna i tasti del giocatore caricato */
	public void updateKeys( int index, Input input )
		{
			keys.get( 0 ).setKey( Input.getKeyName( ( maps.get( index ).get( JUMP ) ) ) );
			keys.get( 1 ).setKey( Input.getKeyName( ( maps.get( index ).get( SHOT ) ) ) );
			keys.get( 2 ).setKey( Input.getKeyName( ( maps.get( index ).get( SX ) ) ) );
			keys.get( 3 ).setKey( Input.getKeyName( ( maps.get( index ).get( DX ) ) ) );
		}
	
	/** controlla se e' stato modificato un bind dei tasti */
	public boolean checkDifference()
		{
			for(int i = 0; i < maps.size(); i++)
				if(!maps.get( i ).equals( Global.mapButtons.get( i ) ))
					return true;
			
			return false;
		}
	
	public boolean update( Input input, int mouseX, int mouseY )
		{
			for(KeyButton key: keys)
				if(key.isSelected())
					if(checkInput( input, key ))
						return checkDifference();
			
			if(input.isMouseButtonDown( Input.MOUSE_LEFT_BUTTON ))
				{
	                if(!mouseDown)
		                {
		                    mouseDown = true;
		                    
		                    for(KeyButton key: keys)
		                    	if(key.contains( mouseX, mouseY ) || key.isSelected())
		                    		key.setSelected();
		                    
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
	                    	// se non e' stato premuto un bottone controllo le frecce
	                    	for(ArrowButton arrow: arrows)
	                    		{
		                        	// se e' stato premuto il tasto
		                    		if(checkArrow( arrow, input, arrow.getIndex() ) == 1)
		                    			{
			                                for(ArrowButton freccia: arrows)
			                                	if(freccia.isPressed())
			                                		freccia.setPressed();

                                    		// premuta freccia sinistra
		                            		if(arrow.getDirection() == ArrowButton.LEFT && numPlayer > 0)
		                            			{
		                            				int oldNum = numPlayer;
		                            				numPlayer = Math.max( 0, --numPlayer );
		                            				if(oldNum != numPlayer)
		                            					updateKeys( numPlayer, input );
		                            			}
		                            		// premuta freccia destra
		                            		else if(arrow.getDirection() == ArrowButton.RIGHT && numPlayer < 4)
		                            			{
		                            				int oldNum = numPlayer;
		                            				numPlayer = Math.min( 3, ++numPlayer );
		                            				if(oldNum != numPlayer)
		                            					updateKeys( numPlayer, input );
		                            			}
		                            		
				                            return checkDifference();
		                    			}
	                    		}
		                }
	            }
			return false;
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
			
			g.setColor( Color.gray );
			for(ArrowButton arrow: arrows)
				arrow.draw( g );
			
			g.setColor( Color.white );
			g.drawString( "Player " + (numPlayer+1), left.getMaxX() + (right.getX() - left.getMaxX())/2 - Global.Width/25, left.getY() - left.getHeight()/2 );
			g.resetTransform();

			g.drawString( SALTO, xString, kSalto.getY() );
			g.drawString( SPARO, xString + Global.Width/3, kSparo.getY() );

			g.drawString( LEFT, xString, kSx.getY() );
			g.drawString( RIGHT, xString + Global.Width/3, kDx.getY() );
			g.resetTransform();

			for(KeyButton key: keys)
				key.draw( g );
		}
}

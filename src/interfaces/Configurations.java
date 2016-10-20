package interfaces;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import Utils.Global;
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
	
	private ArrayList<Rectangle> keys;
	private Rectangle kSalto, kSparo, kSx, kDx;
	
	public Configurations() throws SlickException
		{
			// TODO INSERIRE UNO SFONDO ADATTO

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
			left = new ArrowButton( LEFT, ArrowButton.LEFT, new float[]{ Global.W*10/32, Global.H/3 + height/2, Global.W*10/32 + width, Global.H/3, Global.W*10/32 + width, Global.H/3 + height }, Color.white );
			right = new ArrowButton( RIGHT, ArrowButton.RIGHT, new float[]{ Global.W*52/100, Global.H/3, Global.W*52/100, Global.H/3 + height, Global.W*52/100 + width, Global.H/3 + height/2 },Color.white );
			
			arrows = new ArrayList<ArrowButton>();
			arrows.add( left );
			arrows.add( right );
			
			
			// TODO INSERIRE RIQUADRI PER MODIFICARE I TASTI
			float widthK = Global.W;
			xSalto = new Rectangle( , , widthK, widthK );
			xSparo = new Rectangle( , , widthK, widthK );
			xSx = new Rectangle( , , widthK, widthK );
			xDx = new Rectangle( , , widthK, widthK );
			
			keys = new ArrayList<Rectangle>();
			keys.add( kSalto );
			keys.add( kSparo );
			keys.add( kSx );
			keys.add( kDx );
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
	
	public void changeFileConfig()
		{
			
		}
	
	public void update( GameContainer gc )
		{
			Input input = gc.getInput();
			int mouseX = input.getMouseX();
			int mouseY = input.getMouseY();
			
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
					                                		indexCursor = -1;
		                            						Start.configuration = 0;
		                            						Start.settings = 1;
				                            			}
				                            		else if(buttons.get( i ).getName().equals( APPLY ))
				                            			{
				                            				if(setChanging)
			                            						{
				                            						setChanging = false;

						                            				// TODO DA IMPLEMENTARE
				                            						changeFileConfig();
				                            			        
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
					                            				// TODO IMPLEMENTARE IL CAMBIO DI TASTI CONFIGURATI
					                            				numPlayer = Math.max( 0, --numPlayer );
					                            			}
					                            		// premuta freccia destra
					                            		else if(arrows.get( i ).getDirection() == ArrowButton.RIGHT)
					                            			{
				                            					// TODO IMPLEMENTARE IL CAMBIO DI TASTI CONFIGURATI
					                            				numPlayer = Math.min( 4, ++numPlayer );
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
			g.setColor( Color.black );
			
			g.setColor( Color.gray );
			for(SimpleButton button: buttons)
				button.draw( g );
			for(ArrowButton arrow: arrows)
				arrow.draw( g );
			
			g.setColor( Color.white );
			g.drawString( "Player " + numPlayer, Global.W*10/22, Global.H/40 );
			
			if(indexCursor >= 0)
				cursor.draw( buttons.get( indexCursor ).getX() - widthC, buttons.get( indexCursor ).getY(), widthC, heightC );
			
			float xString = Global.W*10/33, yString = Global.H/5;
			g.drawString( SALTO, xString, yString );
			g.drawString( SPARO, xString + Global.W/3, yString );
			

			yString = Global.H/2;
			g.drawString( LEFT, xString, yString );
			g.drawString( RIGHT, xString + Global.W/3, yString );
			
			// TODO INSERIRE I RIQUADRI PER LA CONFIGURAZIONE TASTI
			
			for(Rectangle key: keys)
				g.draw( key );
		}
}

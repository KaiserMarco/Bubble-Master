package interfaces;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import Utils.Global;
import bubbleMaster.Start;
import dataButton.ArrowButton;
import dataButton.Button;
import dataButton.SimpleButton;

public class Configurations
{
	// bottoni e vettore bottoni
	ArrayList<SimpleButton> buttons;
	SimpleButton back, apply;
	
	Image img;
	
	// determina se e' possibile effettuare i cambiamenti
	private boolean setChanging;
	
	// determina se e' stato effettuato un click
	private boolean mouseDown = false;
	
	// i nomi dei bottoni
	private static final String BACK = "INDIETRO", APPLY = "APPLICA";
	
	// nome dei tasti in game
	private static final String SALTO = "Salto", SPARO = "Sparo", LEFT = "Sinistra", RIGHT = "Destra";
	
	/*indice di posizionamento del cursore*/
	private int indexCursor;
	/*dimensioni del cursore*/
	private int widthC, heightC;
	/*immagine del cursore*/
	private Image cursor;
	
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
	
	public void update( GameContainer gc )
		{
			Input input = gc.getInput();
			int mouseX = input.getMouseX();
			int mouseY = input.getMouseY();
			
			
			// TODO INSERIRE RIQUADRI PER MODIFICARE I TASTI
			
			
			
			
			
			// GENERARE FILE PER SALVARE QUESTE CONFIGURAZIONI
			
			
			
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
			                                				Start.settings = 0;
					                            			Start.begin = 1;
				                            			}
				                            		else if(buttons.get( i ).getName().equals( APPLY ))
				                            			{
				                            				if(setChanging)
			                            						{
				                            						setChanging = false;
				                            			        
				                            						indexCursor = -1;
				                            						Start.settings = 0;
				                            						Start.begin = 1;
				                            					}
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
	
	public void draw( Graphics g )
		{
			g.setColor( Color.black );
			
			g.setColor( Color.gray );
			for(SimpleButton button: buttons)
				button.draw( g );
			
			if(indexCursor >= 0)
				cursor.draw( buttons.get( indexCursor ).getX() - widthC, buttons.get( indexCursor ).getY(), widthC, heightC );
		}
}

package interfaces;

import java.io.File;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import Utils.Global;
import Utils.Sfondo;
import bubbleMaster.Start;
import dataButton.ArrowButton;
import dataButton.Button;
import dataButton.SimpleButton;
import dataObstacles.Ostacolo;

public class ChooseLevel
{	
	private int pos = 0;
	
	private SimpleButton start, back, edit, newLvl, nameLvl, canc;
	private ArrowButton right, left;
	private ArrayList<SimpleButton> buttons;
	private ArrayList<ArrowButton> arrows;
	
	private Sfondo sfondo;
	
	//lunghezza e altezza dello schermo
	private float width, height;	
	// altezza e lunghezza dei bottoni
	private float lungh, alt;
	// valore dell'ordinata dei bottoni
	private float buttonY;
	
	private boolean mouseDown = false;
	
	private int indexCursor;
	/*dimensioni del cursore*/
	private int widthC, heightC;
	/*immagine del cursore*/
	private Image cursor;
	
	private final static String BACK = "Indietro", START = "Gioca", CANC = "Elimina",
								EDIT = "Modifica", NEW = "Nuovo Livello";
	
	public ChooseLevel( GameContainer gc ) throws SlickException
		{
			width = Global.Width; 
			height = Global.Height;
			
			lungh = width/15;
			alt = width/40;
			
			right = new ArrowButton( "", ArrowButton.RIGHT, new float[]{height, height*4/5, height, height*4/5 + alt, height + lungh, height*4/5 + alt/2}, Color.orange, 0 );
			left = new ArrowButton( "", ArrowButton.LEFT, new float[]{width/4 - width/15, height*4/5 + alt/2, width/4 - width/15 + lungh, height*4/5, width/4 - width/15 + lungh, height*4/5 + alt}, Color.orange, 1 );
			
			buttonY = height*6/7;			
			back = new SimpleButton( width*10/108, buttonY, BACK, Color.orange, 0 );
			start = new SimpleButton( width*10/33, buttonY, START, Color.orange, 1 );
			edit = new SimpleButton( width/2, buttonY, EDIT, Color.orange, 2 );
			newLvl = new SimpleButton( width*3/4, buttonY, NEW, Color.orange, 3 );
			canc = new SimpleButton( width/2 - width/20, buttonY + height/15, CANC, Color.orange, 4 );
			if(Begin.livelli.size() > 0)
				nameLvl = new SimpleButton( 0, 0, Begin.livelli.get( pos ).getName(), Color.white, 5 );
			else
				nameLvl = new SimpleButton( 0, 0, "", Color.white, 5 );
			
			buttons = new ArrayList<SimpleButton>();
			buttons.add( back );
			buttons.add( start );
			buttons.add( edit );
			buttons.add( newLvl );
			buttons.add( canc );
			buttons.add( nameLvl );
			
			arrows = new ArrayList<ArrowButton>();
			arrows.add( right );
			arrows.add( left );
			
			indexCursor = -1;

			widthC = Global.Width*100/1777;
			heightC = Global.Height/24;
			
			cursor = new Image( "./data/Image/cursore.png" );
		}
	
	public void setPos( int val )
		{ pos = val; }
	
	public void draw( GameContainer gc ) throws SlickException
		{
			Graphics g = gc.getGraphics();

    		g.setBackground( Color.blue );
			if(pos >= 0)
				{
					sfondo = Begin.livelli.get( pos ).getImage();
				
					ArrayList<Ostacolo> obs = Begin.livelli.get( pos ).getElements();
		    		
					float scale = 0.7f;
					
		    		g.translate( width/2 - width*scale/2, width/25 );
		    		g.scale( scale, scale );

					sfondo.draw( gc );
					
					if(pos >= 0)
						for(int i = 0; i < obs.size(); i++)
							obs.get( i ).draw( g );
				}
    		
			g.setColor( Color.black );
			g.drawRect( 0, 0, width, height );
			
			g.resetTransform();
			
			for(SimpleButton button: buttons)
				button.draw( g );
			
			for(ArrowButton arrow: arrows)
				arrow.draw( g );
			
			if(indexCursor >= 0)
				cursor.draw( buttons.get( indexCursor ).getX() - widthC, buttons.get( indexCursor ).getY(), widthC, heightC );
			
			Global.drawScreenBrightness( g );
		}
	
	public int getIndexLevel()
		{ return pos; }
	
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
	
	public void removeLevel( int pos )
		{
			File levels = new File( "data/livelli" );
			String[] files = levels.list();
			for(int i = 0; i < files.length; i++)
				if(files[i].equals( Begin.livelli.get( pos ).getName() + ".xml" ))
					{
						File rem = new File( "data/livelli/" + files[i] );
						rem.delete();
						break;
					}

			Begin.livelli.remove( pos );
			System.out.println( "rimosso il livello = " + Begin.livelli.size() );
		}
	
	public void update( GameContainer gc, Edit editor, Input input ) throws SlickException
		{
			if(buttons.get( buttons.size() - 1 ).getX() == 0)
				{
					buttons.get( buttons.size() - 1 ).setX( width/2 - buttons.get( buttons.size() - 1 ).getLungh()/2 );
					buttons.get( buttons.size() - 1 ).setY( arrows.get( 0 ).getY() + arrows.get( 0 ).getHeight()/2 - buttons.get( buttons.size() - 1 ).getAlt()/2 );
				}
			
			if(input.isKeyPressed( Input.KEY_ESCAPE ) || input.isKeyPressed( Input.KEY_BACK ))
				{
					indexCursor = -1;
					Start.chooseLevel = 0;
					Start.begin = 1;
				}
			
			if(indexCursor < 0 &&((input.isKeyPressed( Input.KEY_UP ) || input.isKeyPressed( Input.KEY_DOWN )
			|| input.isKeyPressed( Input.KEY_LEFT ) || input.isKeyPressed( Input.KEY_RIGHT ))))
				indexCursor = 0;
			else if(input.isKeyPressed( Input.KEY_LEFT ))
				{
					if(--indexCursor < 0)
						indexCursor = buttons.size() - 2;
				}
			else if(input.isKeyPressed( Input.KEY_RIGHT ))
            	indexCursor = (indexCursor + 1)%(buttons.size() - 1);
			
			if(input.isMouseButtonDown( Input.MOUSE_LEFT_BUTTON ))
				{
	                if(!mouseDown)
		                {
		                    mouseDown = true;
		                    
		                    for(SimpleButton button : buttons)
		                        if(button.checkClick( input.getMouseX(), input.getMouseY(), input ))
		                        	if(!button.isPressed())
	                            		button.setPressed();
		                    
		                    for(ArrowButton arrow: arrows)
		                    	if(arrow.contains( input.getMouseX(), input.getMouseY(), input ))
		                    		if(!arrow.isPressed())
		                    			arrow.setPressed();
		                }
	            }
	        else
	            {
	                if(mouseDown || checkKeyPressed( input ))
		                {
		                    mouseDown = false;
		                    for(SimpleButton button: buttons)
		                    	{
		                    		int value = checkButton( button, input, button.getIndex() );
		                        	boolean pressed = true;
		                        	// se e' stato premuto il tasto
		                    		if(value > 0)
		                    			{
			                                for(SimpleButton bottone: buttons)
			                                	if(bottone.isPressed())
			                                		bottone.setPressed();
			                                pressed = button.checkClick( input.getMouseX(), input.getMouseY(), input );
				                            // pressed tramite mouse || value==2 tramite tastiera
				                            if(pressed || value == 2)
					                            {
				                            		if(button.getName().equals( BACK ))
				                            			{
			                                				indexCursor = -1;
			                                				Start.chooseLevel = 0;
					                            			Start.begin = 1;
				                            			}
				                            		else if(button.getName().equals( START ))
				                                        {
					                            			Start.ig.addOstacoli( Begin.livelli.get( pos ).getElements(), Begin.livelli.get( pos ).getImage(), gc );
					                                        
					                                        Start.stats.startTempo();
					                                        Global.drawCountdown = true;
					                                        Global.inGame = true;
					                                        
				                                			indexCursor = -1;

			                                				Start.chooseLevel = 0;
					                                        Start.startGame = 1;
				                                        }
				                            		else if(button.getName().equals( EDIT ))
				                            			{
					                            			editor.setElements( Begin.livelli.get( pos ).getElements(), Begin.livelli.get( pos ).getName(), pos, Begin.livelli.get( pos ).getImage(), gc );

				                                			indexCursor = -1;
				                                			
			                                				Start.chooseLevel = 0;
					                                        Start.editGame = 1;
				                            			}
				                            		else if(button.getName().equals( NEW ))
				                            			{
			                                				indexCursor = -1;
			                                				
		                                					Start.chooseLevel = 0;
				                            				Start.editGame = 1;
				                            			}
				                            		else if(button.getName().equals( CANC ))
				                            			{
				                            				removeLevel( pos );
				                		    				pos = Math.max( 0, --pos );
				                		    				updateNameLvl();
				                            			}
				                            		
						                            return;
					                            }
		                    			}
		                    	}
		                    // se non e' stato premuto un bottone controllo le frecce
	                    	for(ArrowButton arrow: arrows)
	                    		{
		                        	// se e' stato premuto il tasto
		                    		if(checkArrow( arrow, input, arrow.getIndex() ) > 0)
		                    			{
			                                for(ArrowButton freccia: arrows)
			                                	if(freccia.isPressed())
			                                		freccia.setPressed();
	
	                                		// premuta freccia destra
		                            		if(arrow.getDirection() == ArrowButton.RIGHT && pos < Begin.livelli.size() - 1)
		                            			{
		                            				pos++;
		                            				updateNameLvl();
		                            			}
		                            		// premuta freccia sinistra
		                            		else if(arrow.getDirection() == ArrowButton.LEFT && pos > 0)
		                            			{
		                            				pos--;
		                            				updateNameLvl();
		                            			}
	                                        
				                            return;
		                    			}
	                    		}
		                }
	            }
		}

	public void updateNameLvl() throws SlickException
		{
			buttons.remove( buttons.size() - 1 );
			nameLvl = new SimpleButton( 0, 0, Begin.livelli.get( pos ).getName(), Color.white, 5 );
			buttons.add( nameLvl );
            
            buttons.get( buttons.size() - 1 ).setX( width/2 - buttons.get( buttons.size() - 1 ).getLungh()/2 );
            buttons.get( buttons.size() - 1 ).setY( height*4/5 + width/80 - buttons.get( buttons.size() - 1 ).getAlt()/2 );
		}
	
	private boolean checkKeyPressed( final Input input )
		{
		    return input.isKeyDown( Input.KEY_ENTER ) ||
		           input.isKeyDown( Input.KEY_RIGHT ) ||
		           input.isKeyDown( Input.KEY_LEFT );
		}
}
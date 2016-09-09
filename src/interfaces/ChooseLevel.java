package interfaces;

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
	
	private SimpleButton start, back, edit, newLvl, nameLvl;
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
	
	private final static String BACK = "Indietro", START = "Gioca", EDIT = "Modifica", NEW = "Nuovo Livello";
	
	public ChooseLevel( GameContainer gc ) throws SlickException
		{
			width = gc.getWidth(); 
			height = gc.getHeight();
			
			lungh = width/15;
			alt = width/40;
			
			right = new ArrowButton( ArrowButton.RIGHT, new float[]{height, height*4/5, height, height*4/5 + alt, height + lungh, height*4/5 + alt/2}, Color.orange );
			left = new ArrowButton( ArrowButton.LEFT, new float[]{width/4 - width/15, height*4/5 + alt/2, width/4 - width/15 + lungh, height*4/5, width/4 - width/15 + lungh, height*4/5 + alt}, Color.orange);
			
			buttonY = height*8/9;
			
			back = new SimpleButton( width*10/108, buttonY, BACK, Color.orange );
			start = new SimpleButton( width*10/33, buttonY, START, Color.orange );
			edit = new SimpleButton( width/2, buttonY, EDIT, Color.orange );
			newLvl = new SimpleButton( width*3/4, buttonY, NEW, Color.orange );
			nameLvl = new SimpleButton( 0, 0, Begin.livelli.get( pos ).getName(), Color.white );
			
			buttons = new ArrayList<SimpleButton>();
			buttons.add( back );
			buttons.add( start );
			buttons.add( edit );
			buttons.add( newLvl );
			buttons.add( nameLvl );
			
			arrows = new ArrayList<ArrowButton>();
			arrows.add( right );
			arrows.add( left );
			
			indexCursor = -1;

			widthC = gc.getWidth()*100/1777;
			heightC = gc.getHeight()/24;
			
			cursor = new Image( "./data/Image/cursore.png" );
		}
	
	public void draw( GameContainer gc ) throws SlickException
		{		
			sfondo = Begin.livelli.get( pos ).getImage();
		
			ArrayList<Ostacolo> obs = Begin.livelli.get( pos ).getElements();
			
			Graphics g = gc.getGraphics();
    		
			float scale = 0.7f;
			
    		g.translate( width/2 - width*scale/2, width/25 );
    		g.scale( scale, scale );
    		
    		g.setBackground( Color.blue );
			sfondo.draw( gc );
			g.setColor( Color.black );
			g.drawRect( 0, 0, width, height );
			
			for(int i = 0; i < obs.size(); i++)
				obs.get( i ).draw( g );
			
			g.resetTransform();
			
			for(int i = 0; i < buttons.size(); i++)
				buttons.get( i ).draw( g );
			
			for(int i  = 0; i < arrows.size(); i++)
				arrows.get( i ).draw( g );
			
			if(indexCursor >= 0)
				cursor.draw( buttons.get( indexCursor ).getX() - widthC, buttons.get( indexCursor ).getY(), widthC, heightC );
		}
	
	public int getIndexLevel()
		{ return pos; }
	
	/**setta i valori degli oggetti in proporzione alla variazione di risoluzione*/
	public void setUpdates()
		{
			width = width * Global.ratioW;
			height = height * Global.ratioH;
			
			lungh = lungh * Global.ratioW;
			alt = alt * Global.ratioH;
			
			buttonY = buttonY * Global.ratioH;
			
			for(int i = 0; i < arrows.size(); i++)
				arrows.get( i ).translate( Global.ratioW, Global.ratioH );

			for(int i  = 0; i < buttons.size(); i++)
				{
					if(i != buttons.size() - 1)
						{
							buttons.get( i ).setX( buttons.get( i ).getX() * Global.ratioW );
							buttons.get( i ).setY( buttonY );
						}
					else
						{
							buttons.get( buttons.size() - 1 ).setX( width/2 - buttons.get( buttons.size() - 1 ).getLungh()/2 );
							buttons.get( buttons.size() - 1 ).setY( arrows.get( 0 ).getY() + arrows.get( 0 ).getHeight()/2 - buttons.get( buttons.size() - 1 ).getAlt()/2 );
						}
				}
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
	
	public void update( GameContainer gc, Edit editor ) throws SlickException
		{
			Input input = gc.getInput();
			int mouseX = input.getMouseX();
			int mouseY = input.getMouseY();
			
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
		                        if(button.checkClick( mouseX, mouseY, input ))
		                        	if(!button.isPressed())
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
		                                			indexCursor = -1;
				                            		if(buttons.get( i ).getName().equals( BACK ))
				                            			{
			                                				Start.chooseLevel = 0;
					                            			Start.recoverPreviousStats();
				                            			}
				                            		else if(buttons.get( i ).getName().equals( START ))
				                                        {
					                            			Start.ig.addOstacoli( Begin.livelli.get( pos ).getElements(), Begin.livelli.get( pos ).getImage(), gc );
					                                        
					                                        Start.stats.startTempo();
					                                        Global.drawCountdown = true;
					                                        Global.inGame = true;
					                                        
					                                        Start.startGame = 1;
					                                        Start.setPreviuosStats( "chooseLevel" );
				                                        }
				                            		else if(buttons.get( i ).getName().equals( EDIT ))
				                            			{
					                            			Start.ig.addOstacoli( Begin.livelli.get( pos ).getElements(), Begin.livelli.get( pos ).getImage(), gc );
					                                        editor.setElements( InGame.ostacoli, InGame.players, Begin.livelli.get( pos ).getName(), pos, Begin.livelli.get( pos ).getImage() );
					                                        editor.updateStats();
					                                    
					                                        Start.editGame = 1;
					                                        Start.setPreviuosStats( "chooseLevel" );
				                            			}
				                            		else if(buttons.get( i ).getName().equals( NEW ))
				                            			{
				                            				Start.editGame = 1;
				                            				Start.setPreviuosStats( "chooseLevel" );
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
				                                for(ArrowButton button: arrows)
				                                	if(button.isPressed())
				                                		button.setPressed();
				                                pressed = arrows.get( i ).contains( mouseX, mouseY, input );
					                            // pressed tramite mouse || value==2 tramite tastiera
					                            if(pressed || value == 2)
						                            {
			                                    		// premuta freccia destra
					                            		if(arrows.get( i ).getDirection() == ArrowButton.RIGHT)
					                            			{
						                            			nameLvl = new SimpleButton( 0, 0, Begin.livelli.get( ++pos ).getName(), Color.white );                    
						                                        buttons.remove( buttons.size() - 1 );
						                                        buttons.add( nameLvl );
						                                        nameLvl.setName( Begin.livelli.get( pos ).getName() );
					                            			}
					                            		// premuta freccia sinistra
					                            		else if(arrows.get( i ).getDirection() == ArrowButton.LEFT)
					                            			{
						                            			nameLvl = new SimpleButton( 0, 0, Begin.livelli.get( ++pos ).getName(), Color.white );                    
						                                        buttons.remove( buttons.size() - 1 );
						                                        buttons.add( nameLvl );
						                                        nameLvl.setName( Begin.livelli.get( pos ).getName() );
					                            			}
				                                        
				                                        buttons.get( buttons.size() - 1 ).setX( width/2 - buttons.get( buttons.size() - 1 ).getLungh()/2 );
				                                        buttons.get( buttons.size() - 1 ).setY( height*4/5 + width/80 - buttons.get( buttons.size() - 1 ).getAlt()/2 );
				                                        
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
}













package interfaces;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import Utils.Global;
import Utils.Livello;
import Utils.SlideBar;
import bubbleMaster.Start;
import dataButton.ArrowButton;
import dataButton.Button;
import dataButton.SimpleButton;
import dataObstacles.Ostacolo;
import dataObstacles.Player;

public class Settings
{
	private ArrayList<SimpleButton> buttons;
	private SimpleButton saveChanges, back;
	private ArrayList<ArrowButton> arrows;
	private ArrowButton leftLife, rightLife, leftDrop, rightDrop;
	
	private final String lifes = "VITE", drop = "DROP", bright = "LUMINOSITA'";
	
	private Image sfondo;
	
	// l'ordinata, l'ascissa, la lunghezza e l'altezza dei bottoni risoluzione
	private float xRes;
	
	// determina se e' stato effettuato un click
	private boolean mouseDown = false;
	
	// le vite del personaggio
	private int vite;
	// lunghezza e altezza dei cuori
	private float widthH, heightH;
	// le immagini del cuore
	private Image heart, halfHeart, noHeart;
	
	private static final String BACK = "INDIETRO", APPLY = "APPLICA";
	
	private int dropRate;
	
	private Configurations config;
	
	/** la barra della luminosita' */
	private SlideBar bar;
	// il valore di lumonisita' dopo l'ultima modifica
	private float valBright;
	
	// determina se e' possibile effettuare i cambiamenti
	private boolean setChanging;
	float sum = Global.Height*10/75, yStart = Global.Height/9;
	
	public Settings( GameContainer gc ) throws SlickException
		{
			Color color = Color.orange;
			back = new SimpleButton( Global.Width/5, Global.Height*8/9, BACK, color, 0 );
			saveChanges = new SimpleButton( Global.Width*2/3, Global.Height*8/9, APPLY, color, 1 );
			color = new Color( 34, 139, 34 );
			
			float width = Global.Width/20, height = Global.Height/50;
			leftLife = new ArrowButton( lifes, ArrowButton.LEFT, new float[]{ Global.Width*10/32, yStart + height/2, Global.Width*10/32 + width, yStart, Global.Width*10/32 + width, yStart + height }, Color.white );
			rightLife = new ArrowButton( lifes, ArrowButton.RIGHT, new float[]{ Global.Width*52/100, yStart, Global.Width*52/100, yStart + height, Global.Width*52/100 + width, yStart + height/2 },Color.white );

			leftDrop = new ArrowButton( drop, ArrowButton.LEFT, new float[]{ Global.Width*10/32, yStart + sum + height/2, Global.Width*10/32 + width, yStart + sum, Global.Width*10/32 + width, yStart + sum + height }, Color.white );
			rightDrop = new ArrowButton( drop, ArrowButton.RIGHT, new float[]{ Global.Width*52/100, yStart + sum, Global.Width*52/100, yStart + sum + height, Global.Width*52/100 + width, yStart + sum + height/2 },Color.white );
			
			arrows = new ArrayList<ArrowButton>();
			arrows.add( leftLife );
			arrows.add( rightLife );
			arrows.add( leftDrop );
			arrows.add( rightDrop );
			
			buttons = new ArrayList<SimpleButton>();
			buttons.add( back );
			buttons.add( saveChanges );
			
			sfondo = new Image( "./data/Image/settings.png" );
			
			vite = Global.lifes;
			
			heart = new Image( "./data/Image/heartRed.png" );
			halfHeart = new Image( "./data/Image/halfHeartRed.png" );
			noHeart = new Image( "./data/Image/noHeart.png" );
			widthH = Global.Width/40; heightH = Global.Height/30;
			
			dropRate = (int)(Global.dropRate * 100);
			
			xRes = Global.Width*10/26;
			bar = new SlideBar( xRes, yStart + 2*sum, "", 255.f - Global.brightness, 150.f, 255.f );
			
			Global.init();
			
			valBright = bar.getValue();
			
			setChanging = false;
			
			config = new Configurations();
			config.updateKeys( 0, gc.getInput() );
		}
	
	public void draw( GameContainer gc )
		{
			Graphics g = gc.getGraphics();
			
			sfondo.draw( 0, 0, Global.Width, Global.Height );
		
			for(int i = 0; i < buttons.size(); i++)
				buttons.get( i ).draw( g );
		
			g.setColor( Color.red );
			g.drawString( lifes, Global.Width/5, leftLife.getY() );
			g.drawString( drop, Global.Width/5, leftDrop.getY() );
			g.drawString( bright, Global.Width/5, bar.getY() );
			g.resetTransform();
			
			leftLife.draw( g );
			rightLife.draw( g );
			leftDrop.draw( g );
			rightDrop.draw( g );

			int j;
			float startX = leftLife.getMaxX() + (rightLife.getX() - leftLife.getMaxX())/2 - widthH*2;
			float startY = leftLife.getY() + leftLife.getHeight()/2 - heightH/2;
			for(j = 0; j < vite/2; j++)
				heart.draw( startX + widthH*j, startY, widthH, heightH );
			if(vite%2 == 1)
				halfHeart.draw( startX + widthH*(j++), startY, widthH, heightH );
			for(;j < 4; j++)
				noHeart.draw( startX + widthH*j, startY, widthH, heightH );
			
			g.setColor( Color.black );
			float xDrop = leftDrop.getMaxX() + (rightDrop.getX() - leftDrop.getMaxX())/2 - Global.Width/40, yDrop = leftDrop.getY() - Global.Height/200;
			float scale = 1.1f;
			g.scale( scale, scale );
			g.drawString( dropRate + " %" , xDrop/scale, yDrop/scale );
			g.resetTransform();
			
			bar.render( g );
			
			config.draw( gc );
			
			Global.drawScreenBrightness( g );
		}
	
	private int checkButton( Button button, Input input, int i )
		{
			if(button.isPressed())
				return 1;
		
			return 0;
		}
	
	private int checkArrow( ArrowButton button, Input input, int i )
		{
			if(button.isPressed())
				return 1;
		
			return 0;
		}
	
	private void applicaCambiamenti( Edit editor, GameContainer gc, End end, Configurations config ) throws SlickException
		{
			if(valBright != bar.getValue())
				valBright = bar.getValue();
		
			if(Global.lifes != vite)
				{
					Global.lifes = vite;
					for(Livello levels: Begin.livelli)
						for(Ostacolo elem: levels.getElements())
							if(elem.getID().startsWith( "player" ))
								((Player) elem).setLifes( vite );
				}
			
			Global.dropRate = (double) dropRate/100;
		}
	
	public void update( GameContainer gc, Edit editor, End end ) throws SlickException
		{
			boolean isClicked = false;
		
			Input input = gc.getInput();
			int mouseX = input.getMouseX();
			int mouseY = input.getMouseY();
			
			// controlla se il bottone APPLICA e' cliccabile oppure no
			if(config.isChanged() || Global.lifes != vite || Global.dropRate != (double) dropRate/100
			|| bar.getValue() != valBright)
				{
					setChanging = true;
					buttons.get( 1 ).setColor( Color.orange );
				}
			else
				{
					setChanging = false;
					buttons.get( 1 ).setColor( Color.gray );
				}
			
			if(input.isKeyPressed( Input.KEY_ESCAPE ) || input.isKeyPressed( Input.KEY_BACK ))
				{
					Start.settings = 0;
					Start.begin = 1;
					config.resetInterface( input );
				}
			
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
		                    
		                    if(bar.contains( mouseX, mouseY ))
		                    	if(!bar.isPressed())
		                    		bar.setPressed();
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
		                        	// se e' stato premuto il tasto
		                    		if(value == 1)
		                    			{
		                    				isClicked = true;
			                                for(SimpleButton button: buttons)
			                                	if(button.isPressed())
			                                		button.setPressed();
			                               
			                                if(buttons.get( i ).getName().equals( BACK ))
		                            			{
		                            				if(valBright != bar.getValue())
		                            					{
		                            						bar.setX( valBright );
		                            						Global.brightness = valBright;
		                            					}
		                            				
		                            				config.resetInterface( input );
	                                				Start.settings = 0;
			                            			Start.begin = 1;
		                            			}
		                            		else if(buttons.get( i ).getName().equals( APPLY ))
		                            			{
		                            	        	if(setChanging)
	                            						{
		                            						setChanging = false;
		                            						applicaCambiamenti( editor, gc, end, config );
		                            						
		                            						config.updateFileConfig();
		                            						config.resetInterface( input );

		                            						Start.settings = 0;
		                            						Start.begin = 1;
		                            					}
		                            			}
		                            		
				                            break;
		                    			}
		                    	}
		                    if(i == buttons.size())
			                    // se non e' stato premuto un bottone controllo le frecce
		                    	for(i = 0; i < arrows.size(); i++)
		                    		{
			                    		int value = checkArrow( arrows.get( i ), input, i );
			                        	// se e' stato premuto il tasto
			                    		if(value == 1)
			                    			{
			                    				isClicked = true;
				                                for(ArrowButton button: arrows)
				                                	if(button.isPressed())
				                                		button.setPressed();
					                            // premuta freccia sinistra
			                            		if(arrows.get( i ).getDirection() == ArrowButton.LEFT)
			                            			if(arrows.get( i ).getName().equals( lifes ))
			                            				vite = Math.max( 1, --vite );
			                            			else
			                            				dropRate = Math.max( 0, dropRate - 10 );
			                            		// premuta freccia destra
			                            		else if(arrows.get( i ).getDirection() == ArrowButton.RIGHT)
			                            			if(arrows.get( i ).getName().equals( lifes ))
			                            				vite = Math.min( ++vite, 8 );
			                            			else
			                            				dropRate = Math.min( 100, dropRate + 10 );
			                            		
					                            break;
			                    			}
		                    		}
		                    if(bar.isPressed())
		                    	bar.setPressed();
		                }
	            }
			
			bar.update( mouseX );
			Global.brightness = 255.f - bar.getValue();
			
			// TODO CAPIRE PERCHE AL PRIMO GIRO IL LEFT, IL RIGHT, L'UP E IL DOWN NON VENGONO "SENTITI"
			if(!isClicked)
				config.update( input, mouseX, mouseY );
			else
				config.resetSelected();
		}
	
	private boolean checkKeyPressed( final Input input )
	    {
	        return input.isKeyDown( Input.KEY_ENTER ) ||
	               input.isKeyDown( Input.KEY_RIGHT ) ||
	               input.isKeyDown( Input.KEY_LEFT );
	    }
}
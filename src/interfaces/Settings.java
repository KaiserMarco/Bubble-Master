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
import Utils.Livello;
import Utils.Sfondo;
import Utils.SlideBar;
import bubbleMaster.Start;
import dataButton.ArrowButton;
import dataButton.Button;
import dataButton.SimpleButton;
import dataObstacles.Ostacolo;
import dataObstacles.Player;
import dataObstacles.Tubo;

public class Settings
{
	private ArrayList<SimpleButton> buttons;
	private SimpleButton saveChanges, back;
	private ArrayList<ArrowButton> arrows;
	private ArrowButton leftLife, rightLife, leftDrop, rightDrop;
	
	private final String resolution = "RISOLUZIONE", lifes = "VITE", drop = "DROP", bright = "LUMINOSITA'";
	
	/** il vettore DELLE dimensioni */
	private ArrayList<String> dimensions;
	/** il vettore di caselle PER le dimensioni */
	private ArrayList<Rectangle> dimensioni;
	
	private String risoluzione, widthP, heightP;
	
	private Image sfondo;
	
	private boolean drawChoiseRes;
	
	// l'ordinata, l'ascissa, la lunghezza e l'altezza dei bottoni risoluzione
	private float xRes, yRes, wRes, hRes;
	
	private boolean mouseDown = false;
	
	// le vite del personaggio
	private int vite;
	// lunghezza e altezza dei cuori
	private int widthH, heightH;
	// le immagini del cuore
	private Image heart, halfHeart, noHeart;
	
	private static final String BACK = "INDIETRO", APPLY = "APPLICA";
	
	private int indexCursor;
	/*dimensioni del cursore*/
	private int widthC, heightC;
	/*immagine del cursore*/
	private Image cursor;
	
	private int dropRate;
	
	/** la barra della luminosita' */
	private SlideBar bar;
	
	private float valBright;
	
	public Settings( GameContainer gc ) throws SlickException
		{
			Color color = Color.orange;
			back = new SimpleButton( gc.getWidth()/5, gc.getHeight()*8/9, BACK, color );
			saveChanges = new SimpleButton( gc.getWidth()*2/3, gc.getHeight()*8/9, APPLY, color );
			
			int width = Global.W/20, height = Global.H/50;
			
			leftLife = new ArrowButton( lifes, ArrowButton.LEFT, new float[]{ Global.W*10/32, Global.H/3 + height/2, Global.W*10/32 + width, Global.H/3, Global.W*10/32 + width, Global.H/3 + height }, Color.white );
			rightLife = new ArrowButton( lifes, ArrowButton.RIGHT, new float[]{ Global.W*52/100, Global.H/3, Global.W*52/100, Global.H/3 + height, Global.W*52/100 + width, Global.H/3 + height/2 },Color.white );

			leftDrop = new ArrowButton( drop, ArrowButton.LEFT, new float[]{ Global.W*10/32, Global.H*100/214 + height/2, Global.W*10/32 + width, Global.H*100/214, Global.W*10/32 + width, Global.H*100/214 + height }, Color.white );
			rightDrop = new ArrowButton( drop, ArrowButton.RIGHT, new float[]{ Global.W*52/100, Global.H*100/214, Global.W*52/100, Global.H*100/214 + height, Global.W*52/100 + width, Global.H*100/214 + height/2 },Color.white );
			
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
			
			dimensioni = new ArrayList<Rectangle>();
			
			xRes = Global.W*10/26;
			yRes = Global.H/5;
			wRes = Global.W/10;
			hRes = Global.H/30;
			
			heart = new Image( "./data/Image/heart.png" );
			halfHeart = new Image( "./data/Image/halfHeart.png" );
			noHeart = new Image( "./data/Image/noHeart.png" );
			widthH = gc.getWidth()/40; heightH = gc.getHeight()/30;
			
			dimensioni.add( new Rectangle( xRes, yRes, wRes, hRes ) );
			dimensioni.add( new Rectangle( dimensioni.get( dimensioni.size() - 1 ).getMaxX(), yRes, Global.W/100, dimensioni.get( 0 ).getHeight() ) );
			dimensioni.add( new Rectangle( xRes, dimensioni.get( dimensioni.size() - 1 ).getMaxY(), wRes, hRes ) );
			dimensioni.add( new Rectangle( xRes, dimensioni.get( dimensioni.size() - 1 ).getMaxY(), wRes, hRes ) );
			dimensioni.add( new Rectangle( xRes, dimensioni.get( dimensioni.size() - 1 ).getMaxY(), wRes, hRes ) );
			
			dimensions = new ArrayList<String>();
			
			dimensions.add( "800x600" );
			dimensions.add( "1200x900" );
			dimensions.add( "1280x720" );
			
			widthP = "800";
			heightP = "600";
			risoluzione = widthP + "x" + heightP;
			
			drawChoiseRes = false;
			
			indexCursor = -1;

			widthC = gc.getWidth()*100/1777;
			heightC = gc.getHeight()/24;
			
			cursor = new Image( "./data/Image/cursore.png" );
			
			dropRate = (int)(Global.dropRate * 100);
			
			bar = new SlideBar( xRes, (float) Global.H*100/166, "", 255.f - Global.brightness, 150.f, 255.f );
			
			Global.init();
			
			valBright = bar.getValue();
		}
	
	public void draw( GameContainer gc )
		{		
			Graphics g = gc.getGraphics();
			
			sfondo.draw( 0, 0, Global.W, Global.H );
		
			for(int i = 0; i < buttons.size(); i++)
				buttons.get( i ).draw( g );
			
			g.setColor( Color.red );
			g.scale( Global.W/Global.Width, Global.H/Global.Height );
			g.drawString( resolution, Global.Width/5, Global.Height/5 );
			g.drawString( lifes, Global.Width/5, Global.Height/3 );
			g.drawString( drop, Global.Width/5, Global.Height*100/214 );
			g.drawString( bright, Global.Width/5, Global.Height*100/166 );
			g.resetTransform();
			
			leftLife.draw( g );
			rightLife.draw( g );
			leftDrop.draw( g );
			rightDrop.draw( g );

			int j;
			int startX = (int) (leftLife.getMaxX() + (rightLife.getX() - leftLife.getMaxX())/2 - widthH*2);
			int startY = (int)(leftLife.getY() + leftLife.getHeight()/2 - heightH/2);
			for(j = 0; j < vite/2; j++)
				heart.draw( startX + widthH*j, startY, widthH, heightH );
			if(vite%2 == 1)
				halfHeart.draw( startX + widthH*(j++), startY, widthH, heightH );
			for(;j < 4; j++)
				noHeart.draw( startX + widthH*j, startY, widthH, heightH );
			
			g.setColor( Color.black );
			g.drawString( dropRate + " %" , leftDrop.getMaxX() + (rightDrop.getX() - leftDrop.getMaxX())/2 - Global.W/40, Global.H*100/214 - Global.H/200 );
			
			int i;
			for(i = 0; i < 2; i++)
				{
					g.setColor( Color.gray );
					g.fill( dimensioni.get( i ) );
					g.setColor( Color.black );
					g.draw( dimensioni.get( i ) );
				}
			
			g.scale( Global.W/Global.Width, Global.H/Global.Height );
			g.drawString( risoluzione, dimensioni.get( 0 ).getX()*Global.Width/Global.W, dimensioni.get( 0 ).getY()*Global.Height/Global.H );
			g.resetTransform();
			if(drawChoiseRes)
				{
					for(; i < dimensioni.size(); i++)
						{
							g.setColor( Color.white );
							g.fill( dimensioni.get( i ) );
							g.setColor( Color.black );
							g.draw( dimensioni.get( i ) );

							g.scale( Global.W/Global.Width, Global.H/Global.Height );
							g.drawString( dimensions.get( i - 2 ), dimensioni.get( i ).getX()*Global.Width/Global.W, dimensioni.get( i ).getY()*Global.Height/Global.H );
							g.resetTransform();
						}
				}
			
			if(indexCursor >= 0)
				cursor.draw( buttons.get( indexCursor ).getX() - widthC, buttons.get( indexCursor ).getY(), widthC, heightC );
			
			bar.render( g );
			
			Global.drawScreenBrightness( g );
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
	
	private void applicaCambiamenti( Edit editor, GameContainer gc ) throws SlickException
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
			
			Global.dropRate = dropRate/100;
			
	        if(Global.ratioW != 1 || Global.ratioH != 1)
	            {
	        		// TODO SETTARE LE NUOVE DIMENSIONI ALLA BAR	        	
                	editor.updateStats( gc );
	                for(Livello levels: Begin.livelli)
	                    {
	                        for(Ostacolo elem: levels.getElements())
	                        	{
		                        	if(elem.getID().equals( "tubo" ))
		                        		{
		                        			((Tubo) elem).updateValues( gc );
	                        				((Tubo) elem).setSpace( gc );
		                        		}
		                        	else
		                        		{
		                        			elem.updateStats( gc );
	                        				elem.setArea( gc );
		                        			if(elem.getID().startsWith( "player" ))
		                        				((Player) elem).checkPosition( levels.getElements() );
		                        		}
		                        }
	                            
	                        Sfondo img = levels.getImage();
	                        img.setMaxHeight( img.getMaxHeight() * Global.ratioH );
	                        img.setHeight( img.getHeight() * Global.ratioH );
	                        img.setMaxWidth( img.getMaxWidth() * Global.ratioW );
	                        img.setWidth( img.getWidth() * Global.ratioW );
	                    }

        			for(SimpleButton button: buttons)
        				button.buildButton( button.getX() * Global.ratioW, button.getY() * Global.ratioH );
	        			
	                Start.cl.setUpdates();
	                
	                xRes = xRes * Global.ratioW;
	                yRes = yRes * Global.ratioH;
	                wRes = wRes * Global.ratioW;
	                hRes = hRes * Global.ratioH;
	                
	                for(Rectangle dim: dimensioni)
                		dim.setBounds( dim.getX() * Global.ratioW, dim.getY() * Global.ratioH, dim.getWidth() * Global.ratioW, dim.getHeight() * Global.ratioH);
	
	                leftLife.translate( Global.ratioW, Global.ratioH );
	                rightLife.translate( Global.ratioW, Global.ratioH );
	                leftDrop.translate( Global.ratioW, Global.ratioH );
	                rightDrop.translate( Global.ratioW, Global.ratioH );
			        
			        Start.setAppDisplay();
	            }
		}
	
	public void update( GameContainer gc, Edit editor ) throws SlickException
		{
			Input input = gc.getInput();
			int mouseX = input.getMouseX();
			int mouseY = input.getMouseY();
			
			if(input.isKeyPressed( Input.KEY_ESCAPE ) || input.isKeyPressed( Input.KEY_BACK ))
				{
					indexCursor = -1;
					Start.settings = 0;
					Start.begin = 1;
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
				                            				if(valBright != bar.getValue())
				                            					{
				                            						bar.setX( valBright );
				                            						Global.brightness = valBright;
				                            					}
				                            				
					                                		indexCursor = -1;
			                                				Start.settings = 0;
					                            			Start.begin = 1;
				                            			}
				                            		else if(buttons.get( i ).getName().equals( APPLY ))
				                            			{
				                            	        	Global.computeRatio( Integer.parseInt( widthP ), Integer.parseInt( heightP ) );
				                            				if(Global.lifes != vite || Global.dropRate != (double) dropRate/100 || Global.ratioW != 1 || Global.ratioH != 1 || bar.getValue() != valBright)
				                            					{
				                            						applicaCambiamenti( editor, gc );
				                            			        
				                            						indexCursor = -1;
				                            						Start.settings = 0;
				                            						Start.begin = 1;
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
				                                for(ArrowButton button: arrows)
				                                	if(button.isPressed())
				                                		button.setPressed();
				                                pressed = arrows.get( i ).contains( mouseX, mouseY, input );
					                            // pressed tramite mouse || value==2 tramite tastiera
					                            if(pressed || value == 2)
						                            {
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
		                    		}
		                    if(i == arrows.size())
		                    	if(bar.isPressed())
		                    		bar.setPressed();
		                }
	            }
		
			if(dimensioni.get( 1 ).contains( mouseX, mouseY ) && input.isMousePressed( Input.MOUSE_LEFT_BUTTON ))
				drawChoiseRes = !drawChoiseRes;
			else if(drawChoiseRes)
					{
						if(dimensioni.get( 2 ).contains( mouseX, mouseY ) && input.isMousePressed( Input.MOUSE_LEFT_BUTTON ))
							{
								widthP = "800";
								heightP = "600";
								drawChoiseRes = false;
							}
						else if(dimensioni.get( 3 ).contains( mouseX, mouseY ) && input.isMousePressed( Input.MOUSE_LEFT_BUTTON ))
							{
								widthP = "1200";
								heightP = "900";
								drawChoiseRes = false;
							}
						else if(dimensioni.get( 4 ).contains( mouseX, mouseY ) && input.isMousePressed( Input.MOUSE_LEFT_BUTTON ))
							{
								widthP = "1280";
								heightP = "720";
								drawChoiseRes = false;
							}
						risoluzione = widthP + "x" + heightP;
					}
			
			bar.update( mouseX );
			Global.brightness = 255.f - bar.getValue();
		}
	
	private boolean checkKeyPressed( final Input input )
	    {
	        return input.isKeyDown( Input.KEY_ENTER ) ||
	               input.isKeyDown( Input.KEY_RIGHT ) ||
	               input.isKeyDown( Input.KEY_LEFT );
	    }
}
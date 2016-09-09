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
import dataObstacles.Player;

public class Settings
{
	private ArrayList<SimpleButton> buttons;
	private SimpleButton saveChanges, back;
	private ArrayList<ArrowButton> arrows;
	private ArrowButton left, right;
	
	private String resolution, lifes;
	
	private ArrayList<String> dimensions;
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
	
	public Settings( GameContainer gc ) throws SlickException
		{
			Color color = Color.orange;
			back = new SimpleButton( gc.getWidth()/5, gc.getHeight()*8/9, BACK, color );
			saveChanges = new SimpleButton( gc.getWidth()*2/3, gc.getHeight()*8/9, APPLY, color );
			
			int width = Global.W/20, height = Global.H/50;
			
			left = new ArrowButton( ArrowButton.LEFT, new float[]{ Global.W*10/32, Global.H/3 + height/2, Global.W*10/32 + width, Global.H/3, Global.W*10/32 + width, Global.H/3 + height }, Color.white );
			right = new ArrowButton( ArrowButton.RIGHT, new float[]{ Global.W*52/100, Global.H/3, Global.W*52/100, Global.H/3 + height, Global.W*52/100 + width, Global.H/3 + height/2 },Color.white );
			
			arrows = new ArrayList<ArrowButton>();
			arrows.add( left );
			arrows.add( right );
			
			buttons = new ArrayList<SimpleButton>();
			buttons.add( back );
			buttons.add( saveChanges );
			
			sfondo = new Image( "./data/Image/settings.png" );
			
			resolution = "RISOLUZIONE = ";
			lifes = "VITE = ";
			
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
		}
	
	public void draw( GameContainer gc )
		{		
			Graphics g = gc.getGraphics();
			
			sfondo.draw( 0, 0, Global.W, Global.H );
		
			for(int i = 0; i < buttons.size(); i++)
				buttons.get( i ).draw( g );
			
			g.setColor( Color.red );
			
			g.drawString( resolution, Global.W/5, Global.H/5 );
			g.drawString( lifes, Global.W/5, Global.H/3 );
			
			left.draw( g );
			right.draw( g );

			int j, startX = (int) (left.getMaxX() + (right.getX() - left.getMaxX())/2 - widthH*2), startY = (int)(left.getY() + left.getHeight()/2 - heightH/2);
			for(j = 0; j < vite/2; j++)
				heart.draw( startX + widthH*j, startY, widthH, heightH );
			if(vite%2 == 1)
				halfHeart.draw( startX + widthH*(j++), startY, widthH, heightH );
			for(;j < 4; j++)
				noHeart.draw( startX + widthH*j, startY, widthH, heightH );
			
			int i;
			for(i = 0; i < 2; i++)
				{
					g.setColor( Color.gray );
					g.fill( dimensioni.get( i ) );
					g.setColor( Color.black );
					g.draw( dimensioni.get( i ) );
				}
			
			g.drawString( risoluzione, dimensioni.get( 0 ).getX(), dimensioni.get( 0 ).getY() );
			
			if(drawChoiseRes)
				{
					for(; i < dimensioni.size(); i++)
						{
							g.setColor( Color.white );
							g.fill( dimensioni.get( i ) );
							g.setColor( Color.black );
							g.draw( dimensioni.get( i ) );
							g.drawString( dimensions.get( i - 2 ), dimensioni.get( i ).getX(), dimensioni.get( i ).getY() );
						}
				}
			
			if(indexCursor >= 0)
					cursor.draw( buttons.get( indexCursor ).getX() - widthC, buttons.get( indexCursor ).getY(), widthC, heightC );
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
	
	private void applicaCambiamenti() throws SlickException
		{
			Global.lifes = vite;
	        
	        Global.W = Integer.parseInt( widthP );
	        Global.H = Integer.parseInt( heightP );
	        Global.computeRatio( Global.W, Global.H );
	        if(Global.ratioW != 1 || Global.ratioH != 1)
	            {
	                for(int i = 0; i < Begin.livelli.size(); i++)
	                    {
	                        for(int j = 0; j < Begin.livelli.get( i ).getElements().size(); j++)
	                            {
	                                Begin.livelli.get( i ).getElements().get( j ).updateStats();
	                                if(vite != Global.lifes)
	                                    if(Begin.livelli.get( i ).getElements().get( j ).getID().startsWith( "player" ))
	                                        ((Player) Begin.livelli.get( i ).getElements().get( j )).setLifes();
	                            }
	                            
	                        Begin.livelli.get( i ).getImage().setMaxHeight( Begin.livelli.get( i ).getImage().getMaxHeight() * Global.ratioH );
	                        Begin.livelli.get( i ).getImage().setHeight( Begin.livelli.get( i ).getImage().getHeight() * Global.ratioH );
	                        Begin.livelli.get( i ).getImage().setMaxWidth( Begin.livelli.get( i ).getImage().getMaxWidth() * Global.ratioW );
	                        Begin.livelli.get( i ).getImage().setWidth( Begin.livelli.get( i ).getImage().getWidth() * Global.ratioW );
	                    }
	            
	                for(SimpleButton button: buttons)
	                    {
	                        button.setX( button.getX() * Global.ratioW );
	                        button.setY( button.getY() * Global.ratioH );
	                    }
	                Start.cl.setUpdates();
	                
	                xRes = xRes * Global.ratioW;
	                yRes = yRes * Global.ratioH;
	                wRes = wRes * Global.ratioW;
	                hRes = hRes * Global.ratioH;
	                
	                dimensioni.clear();
	                dimensioni.add( new Rectangle( xRes, yRes, wRes, hRes ) );
	                dimensioni.add( new Rectangle( dimensioni.get( dimensioni.size() - 1 ).getMaxX(), yRes, Global.W/100, dimensioni.get( 0 ).getHeight() ) );
	                dimensioni.add( new Rectangle( xRes, dimensioni.get( dimensioni.size() - 1 ).getMaxY(), wRes, hRes ) );
	                dimensioni.add( new Rectangle( xRes, dimensioni.get( dimensioni.size() - 1 ).getMaxY(), wRes, hRes ) );
	                dimensioni.add( new Rectangle( xRes, dimensioni.get( dimensioni.size() - 1 ).getMaxY(), wRes, hRes ) );
	
	                left.translate( Global.ratioW, Global.ratioH );
	                right.translate( Global.ratioW, Global.ratioH );
	            }
	        
	        Start.setAppDisplay();
		}
	
	public void update( GameContainer gc ) throws SlickException
		{
			Input input = gc.getInput();
			int mouseX = input.getMouseX();
			int mouseY = input.getMouseY();

			if((input.isKeyPressed( Input.KEY_UP ) || input.isKeyPressed( Input.KEY_DOWN )
			|| input.isKeyPressed( Input.KEY_LEFT ) || input.isKeyPressed( Input.KEY_RIGHT )))
				{
					if(indexCursor < 0)
						indexCursor = 0;
					else if(indexCursor == 0)
						indexCursor = 1;
					else
						indexCursor = 0;
				}
			
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
				                            		if(buttons.get( i ).getName().equals( BACK ))
				                            			{
			                                    			Start.setPreviuosStats( "begin" ); 
					                                		indexCursor = -1;
			                                				Start.settings = 0;
					                            			Start.begin = 1;
				                            			}
				                            		else if(buttons.get( i ).getName().equals( APPLY ))
				                                        applicaCambiamenti();
				                            		
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
					                                        vite = Math.max( 1, --vite );
					                            		// premuta freccia destra
					                            		else if(arrows.get( i ).getDirection() == ArrowButton.RIGHT)
					                                        vite = Math.min( ++vite, 8 );
					                            		
							                            break;
						                            }
			                    			}
		                    		}
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
		}
	
	private boolean checkKeyPressed( final Input input )
	    {
	        return input.isKeyDown( Input.KEY_ENTER ) ||
	               input.isKeyDown( Input.KEY_RIGHT ) ||
	               input.isKeyDown( Input.KEY_LEFT );
	    }
}
















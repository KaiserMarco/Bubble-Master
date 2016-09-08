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
import dataButton.SimpleButton;
import dataObstacles.Player;

public class Settings
{
	private ArrayList<SimpleButton> buttons;
	private SimpleButton saveChanges, back;
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
	
	public Settings( GameContainer gc ) throws SlickException
		{
			Color color = Color.orange;
			back = new SimpleButton( gc.getWidth()/5, gc.getHeight()*8/9, "INDIETRO", color );
			saveChanges = new SimpleButton( gc.getWidth()*2/3, gc.getHeight()*8/9, "APPLICA", color );
			
			int width = Global.W/20, height = Global.H/50;
			
			left = new ArrowButton( ArrowButton.LEFT, new float[]{ Global.W*10/32, Global.H/3 + height/2, Global.W*10/32 + width, Global.H/3, Global.W*10/32 + width, Global.H/3 + height }, Color.white );
			right = new ArrowButton( ArrowButton.RIGHT, new float[]{ Global.W*52/100, Global.H/3, Global.W*52/100, Global.H/3 + height, Global.W*52/100 + width, Global.H/3 + height/2 },Color.white );
			
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
		}
	
	public void update( GameContainer gc ) throws SlickException
		{
			Input input = gc.getInput();
			int mouseX = input.getMouseX();
			int mouseY = input.getMouseY();
			
			if(input.isMouseButtonDown( Input.MOUSE_LEFT_BUTTON )) {
                if(!mouseDown) {
                    mouseDown = true;
                    
                    if(back.checkClick( mouseX, mouseY, input )) {
                        if(!back.isPressed())
                            back.setPressed();
                    }
                    else if(right.contains( mouseX, mouseY, input )) {
                        if(!right.isPressed())
                            right.setPressed();
                    }
                    else if(left.contains( mouseX, mouseY, input )) {
                        if(!left.isPressed())
                            left.setPressed();
                    }
                    else if(saveChanges.contains( mouseX, mouseY )) {
                        if(!saveChanges.isPressed())
                            saveChanges.setPressed();
                    }
                }
            }
            else {
                if(mouseDown || checkKeyPressed( input )) {
                    mouseDown = false;
                    
                    if(left.isPressed()) {
                        boolean pressed = true;
                        
                        if(left.isPressed()) {
                            left.setPressed();
                            pressed = left.contains( mouseX, mouseY, input );
                        }
                        
                        if(pressed)
                            vite = Math.max( 1, --vite );
                    }
                    else if(right.isPressed()) {
                        boolean pressed = true;
                        
                        if(right.isPressed()) {
                            right.setPressed();
                            pressed = right.contains( mouseX, mouseY, input );
                        }
                        
                        if(pressed)
                            vite = Math.min( ++vite, 8 );
                    }
                    else if(back.isPressed()) {
                        boolean pressed = true;
                        
                        if(back.isPressed()) {
                            back.setPressed();
                            pressed = back.checkClick( mouseX, mouseY, input );
                        }
                        
                        if(pressed) {
                            Start.settings = 0;
                            Start.recoverPreviousStats();
                        }
                    }
                    else if(saveChanges.isPressed() || input.isKeyPressed( Input.KEY_BACK )) {
                        boolean pressed = true;
                        
                        if(saveChanges.isPressed()) {
                            saveChanges.setPressed();
                            pressed = saveChanges.checkClick( mouseX, mouseY, input );
                        }
                        
                        if(pressed) {
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
                                
                                    for(int i  = 0; i < buttons.size(); i++)
                                        {
                                            buttons.get( i ).setX( buttons.get( i ).getX() * Global.ratioW );
                                            buttons.get( i ).setY( buttons.get( i ).getY() * Global.ratioH );
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
















package interfaces;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import Utils.Global;
import Utils.Sfondo;
import bubbleMaster.Start;
import dataButton.ArrowButton;
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
	
	public ChooseLevel( GameContainer gc ) throws SlickException
		{
			width = gc.getWidth(); 
			height = gc.getHeight();
			
			lungh = width/15;
			alt = width/40;
			
			right = new ArrowButton( ArrowButton.RIGHT, new float[]{height, height*4/5, height, height*4/5 + alt, height + lungh, height*4/5 + alt/2}, Color.orange );
			left = new ArrowButton( ArrowButton.LEFT, new float[]{width/4 - width/15, height*4/5 + alt/2, width/4 - width/15 + lungh, height*4/5, width/4 - width/15 + lungh, height*4/5 + alt}, Color.orange);
			
			buttonY = height*8/9;
			
			back = new SimpleButton( width*10/108, buttonY, "Indietro", Color.orange );
			start = new SimpleButton( width*10/33, buttonY, "Gioca", Color.orange );
			edit = new SimpleButton( width/2, buttonY, "Modifica", Color.orange );
			newLvl = new SimpleButton( width*3/4, buttonY, "Nuovo livello", Color.orange );
			nameLvl = new SimpleButton( 0, 0, Begin.livelli.get( pos ).getName(), Color.white );
			
			buttons = new ArrayList<SimpleButton>();
			buttons.add( back );
			buttons.add( edit );
			buttons.add( start );
			buttons.add( newLvl );
			buttons.add( nameLvl );
			
			arrows = new ArrayList<ArrowButton>();
			arrows.add( right );
			arrows.add( left );
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
			
			if(input.isMouseButtonDown( Input.MOUSE_LEFT_BUTTON )) {
			    if(!mouseDown) {
                    mouseDown = true;
                    
                    for(SimpleButton button : buttons) {
                        if(button.checkClick( mouseX, mouseY, input )) {
                            if(!button.isPressed())
                                button.setPressed();
                        }
                    }
                    
                    if(left.contains( mouseX, mouseY, input )) {
                        if(!left.isPressed())
                            left.setPressed();
                    }
                    else if(right.contains( mouseX, mouseY, input )) {
                        if(!right.isPressed())
                            right.setPressed();
                    }
			    }
			}
			else {
			    if(mouseDown || checkKeyPressed( input )) {
			        mouseDown = false;
			        
			        if(left.isPressed() || input.isKeyPressed( Input.KEY_LEFT )) {
                        boolean pressed = true;
                        
                        if(left.isPressed()) {
                            left.setPressed();
                            pressed = left.contains( mouseX, mouseY, input );
                        }
                        
                        if(pressed && pos > 0) {
                            nameLvl = new SimpleButton( 0, 0, Begin.livelli.get( --pos ).getName(), Color.white );                    
                            buttons.remove( buttons.size() - 1 );
                            buttons.add( nameLvl );
                            nameLvl.setName( Begin.livelli.get( pos ).getName() );
                        }
                    }
			        else if(right.isPressed() || input.isKeyPressed( Input.KEY_RIGHT )) {
                        boolean pressed = true;
                        
                        if(right.isPressed()) {
                            right.setPressed();
                            pressed = right.contains( mouseX, mouseY, input );
                        }
                        
                        if(pressed && pos < Begin.livelli.size() - 1) {
                            nameLvl = new SimpleButton( 0, 0, Begin.livelli.get( ++pos ).getName(), Color.white );                    
                            buttons.remove( buttons.size() - 1 );
                            buttons.add( nameLvl );
                            nameLvl.setName( Begin.livelli.get( pos ).getName() );
                            
                            buttons.get( buttons.size() - 1 ).setX( width/2 - buttons.get( buttons.size() - 1 ).getLungh()/2 );
                            buttons.get( buttons.size() - 1 ).setY( height*4/5 + width/80 - buttons.get( buttons.size() - 1 ).getAlt()/2 );
                        }
                    }
			        else if(back.isPressed()) {
                        boolean pressed = true;
                        
                        if(back.isPressed()) {
                            back.setPressed();
                            pressed = back.checkClick( mouseX, mouseY, input );
                        }
                        
                        if(pressed) {
                            Start.chooseLevel = 0;
                            Start.recoverPreviousStats();
                        }
                    }
			        else if(start.isPressed() || input.isKeyPressed( Input.KEY_ENTER )) {
                        boolean pressed = true;
                        
                        if(start.isPressed()) {
                            start.setPressed();
                            pressed = start.checkClick( mouseX, mouseY, input );
                        }
                        
                        if(pressed) {
                            Start.ig.addOstacoli( Begin.livelli.get( pos ).getElements(), Begin.livelli.get( pos ).getImage(), gc );
                            
                            Start.stats.startTempo();
                            Global.drawCountdown = true;
                            Global.inGame = true;
                            
                            Start.chooseLevel = 0;
                            Start.startGame = 1;
                            Start.setPreviuosStats( "chooseLevel" );
                        }
                    }
			        else if(edit.isPressed()) {
                        boolean pressed = true;
                        
                        if(edit.isPressed()) {
                            edit.setPressed();
                            pressed = edit.checkClick( mouseX, mouseY, input );
                        }
                        
                        if(pressed) {
                            Start.ig.addOstacoli( Begin.livelli.get( pos ).getElements(), Begin.livelli.get( pos ).getImage(), gc );
                            editor.setElements( InGame.ostacoli, InGame.players, Begin.livelli.get( pos ).getName(), pos, Begin.livelli.get( pos ).getImage() );
                        
                            Start.chooseLevel = 0;
                            Start.editGame = 1;
                            Start.setPreviuosStats( "chooseLevel" );
                        }
                    }
			        else if(newLvl.isPressed()) {
                        boolean pressed = true;
                        
                        if(newLvl.isPressed()) {
                            newLvl.setPressed();
                            pressed = newLvl.checkClick( mouseX, mouseY, input );
                        }
                        
                        if(pressed) {
                            editor.setIndex( Begin.livelli.size() + 1 );
                            
                            Start.chooseLevel = 0;
                            Start.editGame = 1;
                            Start.setPreviuosStats( "chooseLevel" );
                        }
                    }
			    }
			}
			
			if(input.isKeyPressed( Input.KEY_ESCAPE ))
				{
					Start.chooseLevel = 0;
					Start.begin = 1;
				}
		}
	
	private boolean checkKeyPressed( final Input input )
	{
	    return input.isKeyDown( Input.KEY_ENTER ) ||
	           input.isKeyDown( Input.KEY_RIGHT ) ||
	           input.isKeyDown( Input.KEY_LEFT );
	}
}













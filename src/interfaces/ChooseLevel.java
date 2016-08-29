package interfaces;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import DataEntites.Sfondo;
import bubbleMaster.Start;
import dataButton.SimpleButton;
import dataObstacles.Ostacolo;

public class ChooseLevel
{	
	private int pos = 0;
	
	private SimpleButton left, right, start, back, edit, newLvl;
	private ArrayList<SimpleButton> buttons;
	
	private Sfondo sfondo;
	
	//lunghezza e altezza dello schermo
	int width;
	int height;
	
	private Image lvlDx, lvlSx;
	
	public ChooseLevel( GameContainer gc ) throws SlickException
		{	
			width = gc.getWidth(); 
			height = gc.getHeight();
			
			lvlDx = new Image( "data/Image/lvlDx.png" );
			lvlSx = new Image( "data/Image/lvlSx.png" );
			
			left = new SimpleButton( width/4 - width/20, height*4/5, "Left", width/20, width/40, lvlSx );
			right = new SimpleButton( height, height*4/5, "Right", width/20, width/40, lvlDx );
			back = new SimpleButton( width*10/108, height*8/9, "Indietro", Color.orange );
			start = new SimpleButton( width*10/33, height*8/9, "Gioca", Color.orange );
			edit = new SimpleButton( width/2, height*8/9, "Modifica", Color.orange );
			newLvl = new SimpleButton( width*3/4, height*8/9, "Nuovo livello", Color.orange );
			
			buttons = new ArrayList<SimpleButton>();
			buttons.add( left );
			buttons.add( right );
			buttons.add( back );
			buttons.add( edit );
			buttons.add( start );
			buttons.add( newLvl );
		}
	
	public void draw( GameContainer gc ) throws SlickException
		{		
			sfondo = Begin.livelli.get( pos ).getImage();
		
			ArrayList<Ostacolo> obs = Begin.livelli.get( pos ).getElements();
			
			Graphics g = gc.getGraphics();
    		
			float scale = 0.7f;
			
    		g.translate( width/2 - width*scale/2, width/25 );
    		g.scale( scale, scale );

			/*System.out.println( height );
			System.out.println( width );*/
    		
    		g.setBackground( Color.blue );
			sfondo.draw( gc );
			g.setColor( Color.black );
			g.drawRect( 0, 0, width, height );
			
			for(int i = 0; i < obs.size(); i++)
				obs.get( i ).draw( g );
			
			g.resetTransform();
			
			for(int i = 0; i < buttons.size(); i++)
				buttons.get( i ).draw( g );
		}
	
	public int getIndexLevel()
		{ return pos; }
	
	public void update( GameContainer gc, Edit editor ) throws SlickException
		{
			Input input = gc.getInput();
			int mouseX = input.getMouseX();
			int mouseY = input.getMouseY();
			
			if((left.checkClick( mouseX, mouseY ) && input.isMousePressed( Input.MOUSE_LEFT_BUTTON )) || input.isKeyPressed( Input.KEY_LEFT ))
				pos = Math.max( pos - 1, 0 );
			else if((right.checkClick( mouseX, mouseY ) && input.isMousePressed( Input.MOUSE_LEFT_BUTTON )) || input.isKeyPressed( Input.KEY_RIGHT ))
				pos = Math.min( pos + 1, Begin.livelli.size() - 1);
			else if((back.checkClick( mouseX, mouseY ) && input.isMousePressed( Input.MOUSE_LEFT_BUTTON )) || input.isKeyPressed( Input.KEY_BACK ))
				{
					Start.chooseLevel = 0;
					Start.recoverPreviousStats();
				}
			else if((start.checkClick( mouseX, mouseY ) && input.isMousePressed( Input.MOUSE_LEFT_BUTTON )) || input.isKeyPressed( Input.KEY_ENTER ))
				{
					Start.ig.addOstacoli( Begin.livelli.get( pos ).getElements(), Begin.livelli.get( pos ).getImage() );
					
					Start.chooseLevel = 0;
					Start.startGame = 1;
					Start.setPreviuosStats( "chooseLevel" );
				}
			else if((edit.checkClick( mouseX, mouseY ) && input.isMousePressed( Input.MOUSE_LEFT_BUTTON )) || input.isKeyPressed( Input.KEY_BACKSLASH ))
				{
					Start.ig.addOstacoli( Begin.livelli.get( pos ).getElements(), Begin.livelli.get( pos ).getImage() );
					editor.setElements( InGame.ostacoli, InGame.players, Begin.livelli.get( pos ).getName(), pos );
				
					Start.chooseLevel = 0;
					Start.editGame = 1;
					Start.setPreviuosStats( "chooseGame" );
				}
			else if((newLvl.checkClick( mouseX, mouseY ) && input.isMousePressed( Input.MOUSE_LEFT_BUTTON )) || input.isKeyPressed( Input.KEY_BACKSLASH ))
				{
					editor.setIndex( Begin.livelli.size() + 1 );
				
					Start.chooseLevel = 0;
					Start.editGame = 1;
					Start.setPreviuosStats( "chooseLevel" );
				}
			else if(input.isKeyPressed( Input.KEY_ESCAPE ))
				{
					Start.chooseLevel = 0;
					Start.begin = 1;
				}
		}
}













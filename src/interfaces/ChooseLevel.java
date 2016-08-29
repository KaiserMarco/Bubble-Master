package interfaces;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
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
	
	public ChooseLevel( GameContainer gc ) throws SlickException
		{
			left = new SimpleButton( gc.getWidth()/400, gc.getHeight()/2, "left", Color.orange );
			right = new SimpleButton( gc.getWidth() - 60, gc.getHeight()/2, "right", Color.orange );
			start = new SimpleButton( gc.getWidth()/2 - 20, gc.getHeight()*23/24, "start", Color.orange );
			back = new SimpleButton( 0, gc.getHeight()*23/24, "INDIETRO", Color.orange );
			edit = new SimpleButton( gc.getWidth() - 95, gc.getHeight()*23/24, "modifica", Color.orange );
			newLvl = new SimpleButton(gc.getWidth()/4 - 20, gc.getHeight()*23/24, "nuovo livello", Color.orange );
			
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
			sfondo.draw( gc );
		
			ArrayList<Ostacolo> obs = Begin.livelli.get( pos ).getElements();
			for(int i = 0; i < obs.size(); i++)
				obs.get( i ).draw( gc.getGraphics() );
			
			for(int i = 0; i < buttons.size(); i++)
				buttons.get( i ).draw( gc.getGraphics() );
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
					editor.setIndex( Begin.livelli.size() );
				
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













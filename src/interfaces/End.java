package interfaces;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import bubbleMaster.Start;
import dataButton.SimpleButton;

public class End 
{
	private SimpleButton replay, vittoria, begin;
	private boolean balls;
	/*immagine del cursore*/
	private Image cursor;
	/**array contenente i bottoni della schermata*/
	private ArrayList<SimpleButton> buttons;
	/*posizione del cursore*/
	private int indexCursor;
	/*dimensioni del cursore*/
	private int widthC, heightC;
	
	public End() throws SlickException
		{		
			cursor = new Image( "./data/Image/cursore.png" );
			
			buttons = new ArrayList<SimpleButton>();			
	
			widthC = 45;
			heightC = 25;
			
			indexCursor = -1;
		}
	
	public void draw( GameContainer gc ) throws SlickException
		{
			balls = true;
			buttons.clear();
		
			for(int i = 0; i < InGame.ostacoli.size(); i++)
				if(InGame.ostacoli.get( i ).ID.equals( "bolla" ))
					balls = false;
			
			if(!balls)
				{
					replay = new SimpleButton( (int) (gc.getWidth()/(2.2)), (int) (gc.getWidth()/(2.2)), "RITENTA", Color.orange );
					begin = new SimpleButton( gc.getWidth()/3, gc.getHeight()/4, "TORNA ALLA SCHERMATA PRINCIPALE", Color.orange );
					
					buttons.add( replay );
					buttons.add( begin );
				}
			else
				{
					vittoria = new SimpleButton( gc.getWidth()/3, gc.getWidth()/4, "COMPLIMENTI PER LA VITTORIA", Color.orange );
					replay = new SimpleButton( (int) (gc.getWidth()/(2.2)), (int) (gc.getWidth()/(2.2)), "RIGIOCA", Color.orange );
					begin = new SimpleButton( gc.getWidth()/3, gc.getHeight()/4, "TORNA ALLA SCHERMATA PRINCIPALE", Color.orange );
					
					buttons.add( replay );
					buttons.add( begin );
					buttons.add( vittoria );
				}
			
			for(int i = 0; i < buttons.size(); i++)
				buttons.get( i ).draw( gc.getGraphics() );
			
			if(indexCursor >= 0)
				cursor.draw( buttons.get( indexCursor ).getX() - widthC, buttons.get( indexCursor ).getY(), widthC, heightC );
		}

	public void update(GameContainer gc) throws SlickException
		{
			Input input = gc.getInput();
			int mouseX = input.getMouseX();
			int mouseY = input.getMouseY();
			
			if((input.isKeyPressed( Input.KEY_UP ) || input.isKeyPressed( Input.KEY_DOWN ) || input.isKeyPressed( Input.KEY_LEFT ) || input.isKeyPressed( Input.KEY_RIGHT )))
				{
					if(indexCursor < 0)
						indexCursor = 0;
					else if(indexCursor == 0)
						indexCursor = 1;
					else
						indexCursor = 0;
				}

			if((input.isMousePressed( Input.MOUSE_LEFT_BUTTON ) && replay.checkClick( mouseX, mouseY )) || (indexCursor == 0 && input.isKeyPressed( Input.KEY_ENTER )))
				{
					indexCursor = -1;
					Start.ig.addOstacoli( Begin.livelli.get( Start.cl.getIndexLevel() ).getElements(), Begin.livelli.get( Start.cl.getIndexLevel() ).getImage() );					
					Start.endGame = 0;
					Start.startGame = 1;
				}
			
			else if((input.isMousePressed( Input.MOUSE_LEFT_BUTTON ) && begin.checkClick( mouseX, mouseY )) || (balls && indexCursor == 1 && input.isKeyPressed( Input.KEY_ENTER )))
				{
					indexCursor = -1;
					Start.endGame = 0;
					Start.begin = 1;
				}
		}
}

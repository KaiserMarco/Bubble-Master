package interfaces;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import bubbleMaster.Start;
import dataButton.SimpleButton;

public class Settings
{
	private ArrayList<SimpleButton> buttons;
	private SimpleButton saveChanges, back;
	
	// TODO IMPLEMENTARE TUTTO
	
	public Settings( GameContainer gc ) throws SlickException
		{
			Color color = Color.orange;
			back = new SimpleButton( gc.getWidth()/4, gc.getHeight()*8/9, "INDIETRO", color );
			saveChanges = new SimpleButton( gc.getWidth()*3/4, gc.getHeight()*8/9, "APPLICA", color );
			
			buttons = new ArrayList<SimpleButton>();
			buttons.add( back );
			buttons.add( saveChanges );
		}
	
	public void draw( GameContainer gc )
		{		
			for(int i = 0; i < buttons.size(); i++)
				buttons.get( i ).draw( gc.getGraphics() );
		}
	
	public void update( GameContainer gc )
		{
			Input input = gc.getInput();
			int mouseX = input.getMouseX();
			int mouseY = input.getMouseY();
		
			if((back.checkClick( mouseX, mouseY ) && input.isMousePressed( Input.MOUSE_LEFT_BUTTON )) || input.isKeyPressed( Input.KEY_BACK ))
				{
					Start.settings = 0;
					Start.recoverPreviousStats();
				}
		
			else if((saveChanges.checkClick( mouseX, mouseY ) && input.isMousePressed( Input.MOUSE_LEFT_BUTTON )) || input.isKeyPressed( Input.KEY_BACK ))
				{
					// TODO IMPLEMENTARE
				}
		}
}
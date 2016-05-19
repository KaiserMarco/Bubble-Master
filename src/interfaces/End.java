package interfaces;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import bubbleMaster.Start;
import dataButton.SimpleButton;

public class End 
{
	private SimpleButton replay, vittoria, begin, tasto;
	private boolean balls;
	
	public void draw( GameContainer gc ) throws SlickException
		{
			balls = true;
		
			for(int i = 0; i < InGame.ostacoli.size(); i++)
				if(InGame.ostacoli.get( i ).ID.equals( "bolla" ))
					balls = false;
			
			if(!balls)
				{
					replay = new SimpleButton( (int) (gc.getWidth()/(2.2)), (int) (gc.getWidth()/(2.2)), "RITENTA", Color.orange );
					begin = new SimpleButton( gc.getWidth()/3, gc.getHeight()/4, "TORNA ALLA SCHERMATA PRINCIPALE", Color.orange );
					
					replay.draw( gc.getGraphics() );
					begin.draw( gc.getGraphics() );
				}
			else
				{
					vittoria = new SimpleButton( gc.getWidth()/3, gc.getWidth()/4, "COMPLIMENTI PER LA VITTORIA", Color.orange );
					replay = new SimpleButton( (int) (gc.getWidth()/(1.4)), (float) (gc.getHeight()/1.6), "RIGIOCA", Color.orange );
					begin = new SimpleButton( gc.getWidth()/9, (float) (gc.getHeight()/1.6), "TORNA ALLA SCHERMATA PRINCIPALE", Color.orange );

					replay.draw( gc.getGraphics() );
					vittoria.draw( gc.getGraphics() );
					begin.draw( gc.getGraphics() );
				}
					
			if(tasto != null)
				tasto.draw( gc.getGraphics() );
		}

	public void update(GameContainer gc) throws SlickException
		{
			Input input = gc.getInput();
			int mouseX = input.getMouseX();
			int mouseY = input.getMouseY();

			if((input.isMousePressed( Input.MOUSE_LEFT_BUTTON ) && replay.checkClick( mouseX, mouseY )) || (balls && input.isKeyPressed( Input.KEY_RIGHT )) || (!balls && input.isKeyPressed( Input.KEY_DOWN )))
				{
					Start.ig.addOstacoli( Begin.livelli.get( Start.cl.getIndexLevel() ).getElements(), Begin.livelli.get( Start.cl.getIndexLevel() ).getImage() );					
					Start.endGame = 0;
					Start.recoverPreviousStats();
					Start.setPreviuosStats( "endGame" );
				}
			
			else if((input.isMousePressed( Input.MOUSE_LEFT_BUTTON ) && begin.checkClick( mouseX, mouseY )) || (balls && input.isKeyPressed( Input.KEY_LEFT )) || (!balls && input.isKeyPressed( Input.KEY_UP )))
				{
					Start.endGame = 0;
					Start.begin = 1;
					Start.setPreviuosStats( "endGame" );
				}
		}
}

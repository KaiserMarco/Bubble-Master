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
					replay = new SimpleButton( 330, 330, "RITENTA", Color.orange );
					begin = new SimpleButton( 210, gc.getHeight()/4, "TORNA ALLA SCHERMATA PRINCIPALE", Color.orange );
					
					replay.draw( gc.getGraphics() );
					begin.draw( gc.getGraphics() );
				}
			else
				{
					vittoria = new SimpleButton( 250, gc.getHeight()/4, "COMPLIMENTI PER LA VITTORIA", Color.orange );
					replay = new SimpleButton( 330, 330, "RIGIOCA", Color.orange );
					begin = new SimpleButton( 220, 360, "TORNA ALLA SCHERMATA PRINCIPALE", Color.orange );					

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
			
			if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON))
				{
					int mouseX = input.getMouseX();
					int mouseY = input.getMouseY();
		
					if(replay.checkClick( mouseX, mouseY ))
						{
							Start.ig.addOstacoli( Begin.livelli.get( Start.cl.getIndexLevel() ).getElements(), Begin.livelli.get( Start.cl.getIndexLevel() ).getImage() );
							Start.endGame = 0;
							Start.startGame = 1;
						}
					
					else if(begin.checkClick( mouseX, mouseY ))
						{
							Start.endGame = 0;
							Start.chooseLevel = 0;
							Start.begin = 1;
						}
				}
		}
}

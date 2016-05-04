package interfaces;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import bubbleMaster.Start;
import dataButton.SimpleButton;

public class Begin 
{
	public SimpleButton editor, tasto, choose;
	
	/**array contente tutti i livelli creati*/
	public static ArrayList<Livello> livelli;
	
	public Begin( GameContainer gc ) throws SlickException
		{
			livelli = new ArrayList<Livello>();
			
			editor = new SimpleButton( (int) (gc.getWidth()/(2.2)), (int) (gc.getWidth()/(2.2)), "EDIT", Color.orange );
			choose = new SimpleButton( (int) (gc.getWidth()/(2.5)), gc.getHeight()/4, "SCEGLI LIVELLO", Color.orange );
		}

	public void draw( Graphics g ) throws SlickException
		{
			editor.draw( g );
			if(tasto != null)
				tasto.draw( g );
			
			choose.draw( g );
			if(tasto != null)
				tasto.draw( g );
		}

	public void update(GameContainer gc, int delta) throws SlickException 
		{
			Input input = gc.getInput();
			if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON))
				{
					int mouseX = input.getMouseX();
					int mouseY = input.getMouseY();

					if(editor.checkClick( mouseX, mouseY ))
						{
							Start.begin = 0;
							Start.editGame = 1;
						}
					else if(choose.checkClick( mouseX, mouseY ))
						{
							if(livelli.size() > 0)
								{
									Start.begin = 0;
									Start.chooseLevel = 1;
								}
						}
				}
		}
}
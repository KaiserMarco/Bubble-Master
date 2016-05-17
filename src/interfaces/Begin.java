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
import dataObstacles.Bubble;
import dataObstacles.Ostacolo;
import dataObstacles.Player;
import dataObstacles.Sbarra;

public class Begin 
{
	public SimpleButton editor, tasto, choose;
	
	/**array contente tutti i livelli creati*/
	public static ArrayList<Livello> livelli;
	/*sfondo del gioco*/
	Sfondo sfondo;
	/*ostacoli del gioco*/
	ArrayList<Ostacolo> ost;
	
	public Begin( GameContainer gc ) throws SlickException
		{
			livelli = new ArrayList<Livello>();
			
			editor = new SimpleButton( (int) (gc.getWidth()/(2.2)), (int) (gc.getWidth()/(2.2)), "EDIT", Color.orange );
			choose = new SimpleButton( (int) (gc.getWidth()/(2.5)), gc.getHeight()/4, "SCEGLI LIVELLO", Color.orange );
			
			ost = new ArrayList<Ostacolo>();
			sfondo = new Sfondo( new Image( "./data/Image/sfondo4.jpg" ), gc.getHeight()/(1.04), gc.getWidth() );
			
			/*livello 1*/
			ost.add( new Sbarra( 100, 50 ) );
			ost.add( new Bubble( 170, 90, 25, (int) sfondo.getMaxWidth() ) );
			ost.add( new Player( 150, (int)sfondo.getMaxHeight() - 70, 0 ) );
			
			livelli.add( new Livello( ost, sfondo ) );
			
			/*livello 2*/
			ost.clear();
			ost.add( new Sbarra( 100, 50 ) );
			ost.add( new Sbarra( 245, 300 ) );
			ost.add( new Bubble( 170, 90, 25, gc.getWidth() ) );
			ost.add( new Bubble( 315, 90, 25, gc.getWidth() ) );
			ost.add( new Bubble( 522, 480, 25, gc.getWidth() ) );
			ost.add( new Player( 150, (int) sfondo.getMaxHeight() - 70, 0 ) );
			
			livelli.add( new Livello( ost, sfondo ) );
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
			int mouseX = input.getMouseX();
			int mouseY = input.getMouseY();

			if((editor.checkClick( mouseX, mouseY ) && input.isMousePressed( Input.MOUSE_LEFT_BUTTON )) || input.isKeyPressed( Input.KEY_UP ))
				{
					Start.begin = 0;
					Start.editGame = 1;
				}
			else if((choose.checkClick( mouseX, mouseY ) && input.isMousePressed( Input.MOUSE_LEFT_BUTTON )) || input.isKeyPressed( Input.KEY_DOWN ))
				{
					if(livelli.size() > 0)
						{
							Start.begin = 0;
							Start.chooseLevel = 1;
						}
				}
		}
}
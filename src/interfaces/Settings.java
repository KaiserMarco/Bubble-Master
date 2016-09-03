package interfaces;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import Utils.Global;
import bubbleMaster.Start;
import dataObstacles.Player;
import dataButton.ArrowButton;
import dataButton.SimpleButton;

public class Settings
{
	private ArrayList<SimpleButton> buttons;
	private SimpleButton saveChanges, back;
	private ArrowButton left, right;
	
	private String resolution, lifes;
	
	private int vite;
	
	private Image sfondo;
	
	// TODO IMPLEMENTARE TUTTO
	
	public Settings( GameContainer gc ) throws SlickException
		{
			Color color = Color.orange;
			back = new SimpleButton( gc.getWidth()/5, gc.getHeight()*8/9, "INDIETRO", color );
			saveChanges = new SimpleButton( gc.getWidth()*2/3, gc.getHeight()*8/9, "APPLICA", color );
			
			int width = Global.W/20, height = Global.H/50;
			
			left = new ArrowButton( ArrowButton.LEFT, new float[]{ Global.W*10/32, Global.H/3 + height/2, Global.W*10/32 + width, Global.H/3, Global.W*10/32 + width, Global.H/3 + height }, Color.white );
			right = new ArrowButton( ArrowButton.RIGHT, new float[]{ Global.W/2, Global.H/3, Global.W/2, Global.H/3 + height, Global.W/2 + width, Global.H/3 + height/2 },Color.white );
			
			buttons = new ArrayList<SimpleButton>();
			buttons.add( back );
			buttons.add( saveChanges );
			
			sfondo = new Image( "./data/Image/settings.png" );
			
			resolution = "RISOLUZIONE = ";
			lifes = "VITE = ";
			
			vite = Global.lifes;
		}
	
	public void draw( GameContainer gc )
		{		
			Graphics g = gc.getGraphics();
			
			sfondo.draw( 0, 0, Global.W, Global.H );
		
			for(int i = 0; i < buttons.size(); i++)
				buttons.get( i ).draw( g );
			
			g.drawString( resolution, Global.W/5, Global.H/5 );
			g.drawString( lifes, Global.W/5, Global.H/3 );
			
			left.draw( g );
			right.draw( g );
			
			g.drawString( "" + vite, Global.W*10/32 + (Global.W/2 - Global.W*10/32)/2, Global.H/3 );
		}
	
	public void update( GameContainer gc )
		{
			Input input = gc.getInput();
			int mouseX = input.getMouseX();
			int mouseY = input.getMouseY();
		
			if(back.checkClick( mouseX, mouseY, input ) || input.isKeyPressed( Input.KEY_BACK ))
				{
					Start.settings = 0;
					Start.recoverPreviousStats();
				}
			
			else if(right.contains( mouseX, mouseY , input ))
				vite = Math.min( ++vite, 8 );
			
			else if(left.contains( mouseX, mouseY, input ))
				vite = Math.max( 1, --vite );
		
			else if(saveChanges.checkClick( mouseX, mouseY, input ) || input.isKeyPressed( Input.KEY_BACK ))
				{
					// TODO IMPLEMENTARE
				
					Global.lifes = vite;
					for(int i = 0; i < Begin.livelli.size(); i++)
						for(int j = 0; j < Begin.livelli.get( i ).getElements().size(); j++)
							for(int k = 0; k < Begin.livelli.get( i ).getElements().size(); k++)
								if(Begin.livelli.get( i ).getElements().get( k ).getID().startsWith( "player" ))
									((Player) Begin.livelli.get( i ).getElements().get( k )).setLifes();
				
				
				
				
				}
		}
}
















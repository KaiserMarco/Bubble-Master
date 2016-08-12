package interfaces;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.jdom2.Comment;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
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
	/*immagine del cursore*/
	private Image cursor;
	/**array contenente i bottoni della schermata*/
	private ArrayList<SimpleButton> buttons;
	/*posizione del cursore*/
	private int indexCursor;
	/*dimensioni del cursore*/
	private int widthC, heightC;
	
	private Element livello;
	private Document document;
	
	public Begin( GameContainer gc ) throws SlickException
		{
			livelli = new ArrayList<Livello>();
			
			editor = new SimpleButton( (int) (gc.getWidth()/(2.2)), (int) (gc.getWidth()/(2.2)), "EDIT", Color.orange );
			choose = new SimpleButton( (int) (gc.getWidth()/(2.5)), gc.getHeight()/4, "SCEGLI LIVELLO", Color.orange );
			
			ost = new ArrayList<Ostacolo>();
			sfondo = new Sfondo( new Image( "./data/Image/sfondo4.jpg" ), gc.getHeight()/(1.04), gc.getWidth() );
						
			/*livello 1*/
			ost.add( new Sbarra( 205, 155 ) );
			ost.add( new Bubble( 140, 90, 25, (int) sfondo.getMaxWidth() ) );
			ost.add( new Player( 250, (int)sfondo.getMaxHeight() - 70, 0 ) );
			
			livelli.add( new Livello( ost, sfondo ) );
			
			/*livello 2*/
			ost.clear();
			ost.add( new Sbarra( 205, 155 ) );
			ost.add( new Bubble( 400, 240, 25, (int) sfondo.getMaxWidth() ) );
			ost.add( new Player( 250, (int)sfondo.getMaxHeight() - 70, 0 ) );
			
			livelli.add( new Livello( ost, sfondo ) );
			
			/*livello 3*/
			ost.clear();
			ost.add( new Sbarra( 205, 155 ) );
			ost.add( new Bubble( 360, 90, 25, (int) sfondo.getMaxWidth() ) );
			ost.add( new Player( 250, (int)sfondo.getMaxHeight() - 70, 0 ) );
			
			livelli.add( new Livello( ost, sfondo ) );
			
			/*livello 4*/
			ost.clear();
			ost.add( new Sbarra( 100, (int) sfondo.getMaxHeight() - 50 ) );
			ost.add( new Bubble( 170, 90, 25, (int) sfondo.getMaxWidth() ) );
			ost.add( new Player( 250, (int)sfondo.getMaxHeight() - 70, 0 ) );
			
			livelli.add( new Livello( ost, sfondo ) );
			
			/*livello 5*/
			ost.clear();
			ost.add( new Sbarra( 100, 50 ) );
			ost.add( new Sbarra( 245, 300 ) );
			ost.add( new Bubble( 170, 90, 25, gc.getWidth() ) );
			ost.add( new Bubble( 315, 90, 25, gc.getWidth() ) );
			ost.add( new Bubble( 522, 480, 25, gc.getWidth() ) );
			ost.add( new Player( 150, (int) sfondo.getMaxHeight() - 70, 0 ) );
			
			livelli.add( new Livello( ost, sfondo ) );
			
			cursor = new Image( "./data/Image/cursore.png" );
			
			buttons = new ArrayList<SimpleButton>();
			buttons.add( choose );
			buttons.add( editor );			

			widthC = 45;
			heightC = 25;
			
			indexCursor = -1;
		}

	public void draw( Graphics g ) throws SlickException
		{
			for(int i = 0; i < buttons.size(); i++)
				buttons.get( i ).draw( g );
			
			if(indexCursor >= 0)
				cursor.draw( buttons.get( indexCursor ).getX() - widthC, buttons.get( indexCursor ).getY(), widthC, heightC );
		}

	public void update(GameContainer gc, int delta) throws SlickException 
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

			if((editor.checkClick( mouseX, mouseY ) && input.isMousePressed( Input.MOUSE_LEFT_BUTTON )) || (indexCursor == 1 && input.isKeyPressed( Input.KEY_ENTER )))
				{
					indexCursor = -1;
					Start.begin = 0;
					Start.editGame = 1;
					Start.setPreviuosStats( "begin" );
				}
			else if((choose.checkClick( mouseX, mouseY ) && input.isMousePressed( Input.MOUSE_LEFT_BUTTON )) || (indexCursor == 0 && input.isKeyPressed( Input.KEY_ENTER )))
				{
					if(livelli.size() > 0)
						{
							indexCursor = -1;
							Start.begin = 0;
							Start.chooseLevel = 1;
							Start.setPreviuosStats( "begin" );
						}
				}
		}
}
package Utils;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import dataObstacles.Bubble;
import dataObstacles.Ostacolo;
import dataObstacles.Player;
import dataObstacles.Sbarra;
import dataObstacles.Tubo;

public class Elements
{
	private ArrayList<Sfondo> sfondi;
	private ArrayList<Ostacolo> items;
	
	//il raggio delle sfere
	private int ray;
	
	private float maxH, maxW;
	private int width, height;
	
	public Elements( GameContainer gc ) throws SlickException
		{
			maxH = gc.getHeight()*100/104;
			maxW = gc.getWidth();
			width = gc.getHeight()/10;
			height = gc.getWidth()/20;
			
			ray = gc.getWidth()/32;
		
			sfondi = new ArrayList<Sfondo>();
			sfondi.add( new Sfondo( new Image( "./data/Image/sfondo.png" ), maxH, maxW, gc.getWidth()/8, gc.getHeight()/2, width, height, "sfondo" ) );
			sfondi.add( new Sfondo( new Image( "./data/Image/sfondo2.png" ), maxH, maxW, gc.getWidth()*29/100, gc.getHeight()/2, width, height, "sfondo2" ) );
			sfondi.add( new Sfondo( new Image( "./data/Image/sfondo3.jpg" ), maxH, maxW, gc.getWidth()*46/100, gc.getHeight()/2, width, height, "sfondo3" ) );
			sfondi.add( new Sfondo( new Image( "./data/Image/sfondo4.jpg" ), maxH, maxW, gc.getWidth()*63/100, gc.getHeight()/2, width, height, "sfondo4" ) );
			sfondi.add( new Sfondo( new Image( "./data/Image/sfondo5.jpg" ), maxH, maxW, gc.getWidth()*8/10, gc.getHeight()/2, width, height, "sfondo5" ) );			

			items = new ArrayList<Ostacolo>();
			items.add( new Sbarra( gc.getWidth()/9, gc.getHeight()*78/100, "hor", gc ) );
			items.add( new Tubo( gc.getWidth()*2/7, gc.getHeight()*3/4, "sx", gc ) );
			items.add( new Player( gc.getWidth()*4/7, gc.getHeight()*3/4, 1, gc ) );
			items.add( new Player( gc.getWidth()*5/7, gc.getHeight()*3/4, 2, gc ) );
			items.add( new Bubble( gc.getWidth()*6/7, gc.getHeight()*3/4, ray, maxW, gc ) );
		}
	
	public ArrayList<Sfondo> getSfondi()
		{ return sfondi; }
	
	public ArrayList<Ostacolo> getItems()
		{ return items; }
}

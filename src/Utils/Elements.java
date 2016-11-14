package Utils;

import java.util.ArrayList;

import org.newdawn.slick.Color;
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
			maxW = Global.Width; maxH = Global.Height*100/104;
			width = Global.Width*10/133; height = Global.Height/15;
			
			ray = Global.Width/32;
			
			int index = 0;
			int heightS = Global.Height*10/24;
			sfondi = new ArrayList<Sfondo>();
			// TODO REINSERIRE SFONDO1 AL POSTO DI WHITE UNA VOLTA TERMINATO
			//sfondi.add( new Sfondo( new Image( "./data/Image/white.png" ), maxH, maxW, Global.Width/8, heightS, width, height, "sfondo1" ) );
			sfondi.add( new Sfondo( new Image( "./data/Image/sfondo1.png" ), maxH, maxW, Global.Width/8, heightS, width, height, "sfondo1", index++ ) );
			sfondi.add( new Sfondo( new Image( "./data/Image/sfondo8.png" ), maxH, maxW, Global.Width*29/100, heightS, width, height, "sfondo8", index++ ) );
			sfondi.add( new Sfondo( new Image( "./data/Image/sfondo7.png" ), maxH, maxW, Global.Width*46/100, heightS, width, height, "sfondo7", index++ ) );
			sfondi.add( new Sfondo( new Image( "./data/Image/sfondo4.png" ), maxH, maxW, Global.Width*63/100, heightS, width, height, "sfondo4", index++ ) );
			sfondi.add( new Sfondo( new Image( "./data/Image/sfondo5.png" ), maxH, maxW, Global.Width*8/10, heightS, width, height, "sfondo5", index++ ) );			

			items = new ArrayList<Ostacolo>();
			int heightObs = Global.Height*10/17;
			items.add( new Sbarra( Global.Width/5, heightObs + Global.Height/20 - Global.Height/60, "hor", gc ) );
			items.add( new Tubo( Global.Width/2 - Global.Height/20, heightObs, "sx", gc ) );
			items.add( new Bubble( Global.Width*2/3 + Global.Height/20 - Global.Height/48, heightObs, ray, maxW, gc ) );
			int heightP = Global.Height*3/4; float xItem = Global.Width*2/10;
			items.add( new Player( xItem, heightP, 1, gc, Color.red ) );
			items.add( new Player( 2*xItem, heightP, 2, gc, Color.blue ) );
			items.add( new Player( 3*xItem, heightP, 3, gc, Color.yellow ) );
			items.add( new Player( 4*xItem, heightP, 4, gc, Color.green ) );
		}
	
	public ArrayList<Sfondo> getSfondi()
		{ return sfondi; }
	
	public ArrayList<Ostacolo> getItems()
		{ return items; }
}

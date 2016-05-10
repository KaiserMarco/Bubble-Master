package bubbleMaster;

import interfaces.Begin;
import interfaces.ChooseLevel;
import interfaces.Edit;
import interfaces.End;
import interfaces.InGame;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Start extends BasicGame
{	
	public static InGame ig;
	private Begin b;
	private End e;
	private Edit edit;
	public static ChooseLevel cl;
	
	public static int chooseLevel = 0, startGame = 0, editGame = 0, endGame = 0;
	public static int begin = 1;
	
	public Start( String title )
		{
			super( title );
		}

	public static void main( String[] args ) throws SlickException
		{
			AppGameContainer app = new AppGameContainer( new Start( "Bubble Master" ) );
		 
			app.setTargetFrameRate( 90 );
			app.setDisplayMode(800, 600, false);
			app.start();
		}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException 
		{		
			if(begin == 1)
				b.draw( g );
			else if(startGame == 1)
				ig.draw( gc, g );
			else if(editGame == 1)
				edit.draw( gc, g );
			else if(endGame == 1)
				e.draw( gc );
			else if(chooseLevel == 1)
				cl.draw( gc );
		}

	@Override
	public void init(GameContainer gc) throws SlickException 
		{
			b = new Begin( gc );
			ig = new InGame();
			e = new End();
			edit = new Edit( gc );
			cl = new ChooseLevel( gc );
		}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException 
		{
			if(begin == 1)
				b.update( gc, delta );
			else if(startGame == 1)
				ig.update( gc, delta );
			else if(editGame == 1)
				edit.update( gc );
			else if(endGame == 1)
				e.update( gc );
			else if(chooseLevel == 1)
				cl.update( gc );
		}
}
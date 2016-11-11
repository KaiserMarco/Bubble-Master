package bubbleMaster;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import Utils.Global;
import Utils.Statistics;
import interfaces.Begin;
import interfaces.ChooseLevel;
import interfaces.Edit;
import interfaces.End;
import interfaces.InGame;
import interfaces.Settings;

public class Start extends BasicGame
{	
	public static InGame ig;
	private Begin b;
	private End e;
	private Edit edit;
	private Settings opt;
	public static ChooseLevel cl;
	public static Statistics stats;
	
	public static int chooseLevel, startGame, endGame, editGame, creaLvl, settings, configuration;
	public static int begin = 1;
	
	private static AppGameContainer app;
	
	public Start( String title )
		{
			super( title );
			
			stats = new Statistics();
		}

	public static void main( String[] args ) throws SlickException
		{
			app = new AppGameContainer( new Start( "Bubble Master" ) );
		 
			app.setTargetFrameRate( Global.FRAME );
			app.setDisplayMode( Global.Width, Global.Height, false );
			
			Global.initMap();
			
			app.start();
		}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException 
		{		
			if(begin == 1)
				b.draw( gc );
			else if(startGame == 1)
				ig.draw( gc, g );
			else if(editGame == 1)
				edit.draw( gc, g );
			else if(endGame == 1)
				e.draw( gc );
			else if(chooseLevel == 1)
				cl.draw( gc );
			else if(settings == 1)
				opt.draw( gc );
		}

	@Override
	public void init(GameContainer gc) throws SlickException 
		{
			b = new Begin( gc );
			ig = new InGame();
			e = new End();
			edit = new Edit( gc );
			cl = new ChooseLevel( gc );
			opt = new Settings( gc );
		}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException 
		{
			if(begin == 1)
				b.update( gc, delta, gc.getInput() );
			else if(startGame == 1)
				ig.update( gc, delta, e, gc.getInput() );
			else if(editGame == 1)
				edit.update( gc, delta, gc.getInput() );
			else if(endGame == 1)
				e.update( gc, gc.getInput() );
			else if(chooseLevel == 1)
				cl.update( gc, edit, gc.getInput() );
			else if(settings == 1)
				opt.update( gc, edit, e, gc.getInput() );
			
			gc.getInput().clearKeyPressedRecord();
			gc.getInput().clearMousePressedRecord();
			gc.getInput().clearControlPressedRecord();
		}
}
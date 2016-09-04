package bubbleMaster;

import interfaces.Begin;
import interfaces.ChooseLevel;
import interfaces.Edit;
import interfaces.End;
import interfaces.InGame;
import interfaces.Settings;

import java.util.ArrayList;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import Utils.Global;
import Utils.Statistics;

public class Start extends BasicGame
{	
	public static InGame ig;
	private Begin b;
	private End e;
	private Edit edit;
	private Settings opt;
	public static ChooseLevel cl;
	public static Statistics stats;
	
	public static int chooseLevel, startGame, endGame, editGame, creaLvl, settings;
	public static int begin = 1;
	
	private static ArrayList<String> previous;
	
	private static AppGameContainer app;
	
	public Start( String title )
		{
			super( title );
			
			previous = new ArrayList<String>();
			
			stats = new Statistics();
		}

	public static void main( String[] args ) throws SlickException
		{
			app = new AppGameContainer( new Start( "Bubble Master" ) );
		 
			app.setTargetFrameRate( Global.FRAME );
			app.setDisplayMode( Global.W, Global.H, false );
			
			Global.computeRatio( app.getWidth(), app.getHeight() );
			
			app.start();
		}
	
	public static void setAppDisplay() throws SlickException
		{ app.setDisplayMode( Global.W, Global.H, false); }
	
	/*ricorda i precedenti valori delle interfacce*/
	public static void setPreviuosStats( String prev ) throws SlickException
		{ previous.add( 0, prev ); }
	
	/*setta la precedente interfaccia*/
	public static void recoverPreviousStats()
		{
			if(previous.get( 0 ).equals( "chooseLevel" ))
				chooseLevel = 1;
			else if(previous.get( 0 ).equals( "startGame" ))
				startGame = 1;
			else if(previous.get( 0 ).equals( "editGame" ))
				editGame = 1;
			else if(previous.get( 0 ).equals( "endGame" ))
				endGame = 1;
			else if(previous.get( 0 ).equals( "creaLvl" ))
				creaLvl = 1;
			else if(previous.get( 0 ).equals( "settings" ))
				settings = 1;
			else
				begin = 1;
			
			previous.remove( 0 );
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
				b.update( gc, delta );
			else if(startGame == 1)
				ig.update( gc, delta );
			else if(editGame == 1)
				edit.update( gc, delta );
			else if(endGame == 1)
				e.update( gc );
			else if(chooseLevel == 1)
				cl.update( gc, edit );
			else if(settings == 1)
				opt.update( gc );
			
			gc.getInput().clearKeyPressedRecord();
			gc.getInput().clearMousePressedRecord();
			gc.getInput().clearControlPressedRecord();
		}
}
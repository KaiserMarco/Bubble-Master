package Utils;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;


public class Global
{
	/** lunghezza di base */
	public static final float Width = 800;
	/** altezza di base */
	public static final float Height = 600;
	
	/** lunghezza attuale */
	public static int W = 800;
	/** altezza attuale */
	public static int H = 600;
	
	// numero di frame da eseguire
	public static final int FRAME = 90;
	
	// il rapporto fra altezza e lunghezza attuali con quelli di default
	public static float ratioW, ratioH;
	
	// determina se disegnare il countdown iniziale
	public static boolean drawCountdown;
	
	// numero di vite del personaggio
	public static int lifes = 6;
	
	// determina se il gioco e' in una partita
	public static boolean inGame = false;
	
	// il numero di giocatori della partita
	public static Sfondo sfondo;
	
	public static double dropRate = 0.6;
	
	/** la luminosita' dello schermo */
	public static float brightness = 0;
	
	/** dimensione della barra della luminosita' */
	public static float sizewBox = W/17, sizehBox = H/17;
	
	/** font dei caratteri e della finestra di stato */
	public static UnicodeFont stateFont;
	
	/** gli ID degli oggetti del gioco */
	public static final String TUBO = "tubo", BASE = "base", ENTER = "enter", PLAYER = "player", BOLLA = "bolla", SBARRA = "sbarra";
	
	public static Map<String, String> player1 = new HashMap<>(), player2 = new HashMap<>();
	public static Map<String, String> player3 = new HashMap<>(), player4 = new HashMap<>();
	
	/** inserisce la luminosita' dello schermo
	 * @param g - il contesto grafico
	*/
	public static void drawScreenBrightness( Graphics g )
		{
			if(brightness > 0)
				{
					g.setColor( new Color( 0, 0, 0, (int) brightness ) );
					g.fillRect( 0, 0, W, H );
				}
		}
	
	// calcola il rapporto fra altezza e lunghezza attuali con quelli di default
	public static void computeRatio( float width, float height )
		{
			ratioW = width/W;
			ratioH = height/H;
			
			W = (int) width;
			H = (int) height;
		}
	
	/**inizializza i dati di gioco
	 * @param gameContainer - il contenitore del gioco
	*/
	@SuppressWarnings( "unchecked" )
	public static void init() throws SlickException
		{
			stateFont = new UnicodeFont( "./data/fonts/prstart.ttf", (int)(12.f), false, true );
			stateFont.addAsciiGlyphs();
			stateFont.addGlyphs( 400, 600 );
			stateFont.getEffects().add( new ColorEffect( java.awt.Color.WHITE ) );
			stateFont.loadGlyphs();
		}
	
	public static void setMap( String name, String salto, String sparo, String sx, String dx )
		{
			if(name.equals( "player1" ))
				{
					player1.put( "Salto", salto );
					player1.put( "Sparo", sparo );
					player1.put( "Sx", sx );
					player1.put( "Dx", dx );
				}
			else if(name.equals( "player2" ))
				{
					player2.put( "Salto", salto );
					player2.put( "Sparo", sparo );
					player2.put( "Sx", sx );
					player2.put( "Dx", dx );
				}
			else if(name.equals( "player3" ))
				{
					player3.put( "Salto", salto );
					player3.put( "Sparo", sparo );
					player3.put( "Sx", sx );
					player3.put( "Dx", dx );
				}
			else
				{
					player4.put( "Salto", salto );
					player4.put( "Sparo", sparo );
					player4.put( "Sx", sx );
					player4.put( "Dx", dx );
				}
		}
}

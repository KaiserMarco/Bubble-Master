package Utils;

import java.util.ArrayList;
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
	public static final int Width = 800;
	/** altezza di base */
	public static final int Height = 600;
	
	// numero di frame da eseguire
	public static final int FRAME = 90;
	
	// determina se disegnare il countdown iniziale
	public static boolean drawCountdown;
	
	// numero di vite del personaggio
	public static int lifes = 8;
	
	// determina se il gioco e' in una partita
	public static boolean inGame = false;
	
	// il numero di giocatori della partita
	public static Sfondo sfondo;
	
	public static double dropRate = 0.6;
	
	/** la luminosita' dello schermo */
	public static float brightness = 0;
	
	/** dimensione della barra della luminosita' */
	public static float sizewBox = Width/17, sizehBox = Height/17;
	
	/** font dei caratteri e della finestra di stato */
	public static UnicodeFont stateFont;
	
	/** gli ID dei powerUp */
	public static final String AMMO = "ammo", LIFE = "life", COIN = "coin", INVINCIBLE = "invincible";
	/** gli ID degli oggetti del gioco */
	public static final String TUBO = "tubo", BASE = "base", ENTER = "enter", PLAYER = "player", BOLLA = "bolla", SBARRA = "sbarra";
	/** gli ID delle azioni del player */
	public static final String SPARO = "Sparo", SALTO = "Salto", SX = "Left", DX = "Right";
	/** gli ID dei segmenti dell'oggetto */
	public static final String SPIGASX = "spigASx", SPIGADX = "spigADx", SPIGBSX = "spigBSx", SPIGBDX = "spigBDx",
							   LATOSU = "latoSu", LATOGIU = "latoGiu", LATOSX = "latoSx", LATODX = "latoDx", RECT = "rect";
	
	/** stringhe di movimento oggetti */
	public static final String MOVE = "move", RESTORE = "restore";
	
	/** l'altezza del pavimento dei livelli */
	public static final float maxHeight = Global.Height*100/104;
	
	/** hashMap contente i vettori di configurazione tasti */
	public static ArrayList<Map<String, Integer>> mapButtons;
	public static Map<String, Integer> play1 = new HashMap<>(), play2 = new HashMap<>();
	public static Map<String, Integer> play3 = new HashMap<>(), play4 = new HashMap<>();
	
	/** inserisce la luminosita' dello schermo
	 * @param g - il contesto grafico
	*/
	public static void drawScreenBrightness( Graphics g )
		{
			if(brightness > 0)
				{
					g.setColor( new Color( 0, 0, 0, (int) brightness ) );
					g.fillRect( 0, 0, Width, Height );
				}
		}
	
	/** ritorna il vettore dei tasti (la clone non funzionava) */
	public static ArrayList<Map<String, Integer>> getMapButton()
		{
			ArrayList<Map<String, Integer>> tmp = new ArrayList<Map<String, Integer>>();
			
			for(int i = 0; i < mapButtons.size(); i++)
				{
					Map<String, Integer> play = new HashMap<>();
					play.put( "Salto", Global.mapButtons.get( i ).get( "Salto" ) );
					play.put( "Sparo", Global.mapButtons.get( i ).get( "Sparo" ) );
					play.put( "Sx", Global.mapButtons.get( i ).get( "Sx" ) );
					play.put( "Dx", Global.mapButtons.get( i ).get( "Dx" ) );
					tmp.add( play );
				}
		
			return tmp;
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

	/**inizializza le configurazioni */
	public static void initMap()
		{
			mapButtons = new ArrayList<Map<String, Integer>>();
			mapButtons.add( play1 );
			mapButtons.add( play2 );
			mapButtons.add( play3 );
			mapButtons.add( play4 );
		}

	/**aggiorna le mappe dei tasti */
	public static void setMap( int index, int salto, int sparo, int sx, int dx )
		{
			mapButtons.get( index ).put( "Salto", salto );
			mapButtons.get( index ).put( "Sparo", sparo );
			mapButtons.get( index ).put( "Sx", sx );
			mapButtons.get( index ).put( "Dx", dx );
		}
}

package Utils;


public class Global
{
	// determinano la lunghezza e l'altezza dello schermo di default
	public static final float Width = 800;
	public static final float Height = 600;
	
	// lunghezza e altezza dello schermo di default
	public static int W = 800;
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
	
	public static float currentW = Width, currentH = Height;
	
	// calcola il rapporto fra altezza e lunghezza attuali con quelli di default
	public static void computeRatio( float width, float height )
		{
			ratioW = width/currentW;
			ratioH = height/currentH;
			
			currentW = width;
			currentH = height;
			System.out.println( currentW + " " + currentH );
		}
}

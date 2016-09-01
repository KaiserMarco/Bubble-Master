package Utils;

public class Global
{
	// determinano la lunghezza e l'altezza dello schermo di default
	private static final float Width = 800;
	private static final float Height = 600;
	
	// lunghezza e altezza dello schermo attuale
	public static final int W = 800;
	public static final int H = 600;
	
	public static final int FRAME = 90;
	
	public static float ratioW, ratioH;
	
	public static boolean drawCountdown;
	
	public static int lifes = 6;
	
	public static void computeRatio( float width, float height )
		{
			ratioW = width/Width;
			ratioH = height/Height;
		}
}

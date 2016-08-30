package Utils;

public class Global
{
	// determinano la lunghezza e l'altezza dello schermo di default
	private static final float Width = 800;
	private static final float Height = 600;
	
	public static final int W = 1200;
	public static final int H = 900;
	
	public static final int FRAME = 90;
	
	public static float ratioW, ratioH;
	
	public static void computeRatio( float width, float height )
		{
			ratioW = width/Width;
			ratioH = height/Height;
		}
}

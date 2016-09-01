package Utils;

public class Statistics
{
	// determina il tempo impiegato a terminare il livello (vittoria o sconfitta)
	private long tempo = 0;
	
	public Statistics()
		{ }
	
	public void startTempo()
		{ tempo = System.currentTimeMillis(); }
	
	public void stopTempo()
		{ tempo = System.currentTimeMillis() - tempo; }
	
	public void resetTempo()
		{ tempo = 0; }
	
	public long getTempo()
		{ return tempo; }
}

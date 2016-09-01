package Utils;

public class Statistics
{
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

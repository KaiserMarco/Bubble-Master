package interfaces;

import java.util.ArrayList;

import DataEntites.Sfondo;
import dataObstacles.Ostacolo;

public class Livello
{
	private ArrayList<Ostacolo> ostacoli;
	private Sfondo sfondo;
	
	public Livello( ArrayList<Ostacolo> ostacoli, Sfondo sfondo ) 
		{
			this.ostacoli = new ArrayList<Ostacolo>(ostacoli);
			this.sfondo = sfondo;
		}
	
	public ArrayList<Ostacolo> getElements()
		{ return new ArrayList<Ostacolo>(ostacoli); }
	
	public Sfondo getImage()
		{ return sfondo; }
}
package Utils;

import java.util.ArrayList;

import dataObstacles.Ostacolo;

public class Livello
{
	private ArrayList<Ostacolo> ostacoli;
	private Sfondo sfondo;
	//il nome del livello
	private String name;
	
	public Livello( ArrayList<Ostacolo> ostacoli, Sfondo sfondo, String name ) 
		{
			this.ostacoli = new ArrayList<Ostacolo>(ostacoli);
			this.sfondo = sfondo;
			this.name = name;
		}
	
	public ArrayList<Ostacolo> getElements()
		{ return new ArrayList<Ostacolo>(ostacoli); }
	
	public Sfondo getImage()
		{ return sfondo; }
	
	public String getName()
		{ return name; }
}
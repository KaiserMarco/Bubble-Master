package interfaces;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import DataEntites.Sfondo;
import bubbleMaster.Start;
import dataButton.SimpleButton;
import dataObstacles.Ostacolo;

public class ChooseLevel
{	
	private int pos = 0;
	
	private SimpleButton left, right, start, back;
	
	private Sfondo sfondo;
	
	private DocumentBuilderFactory documentFactory;
	private DocumentBuilder builder;
	private Document document;
	
	public ChooseLevel( GameContainer gc ) throws SlickException
		{
			left = new SimpleButton( gc.getWidth()/400, gc.getHeight()/2, "left", Color.orange );
			right = new SimpleButton( gc.getWidth() - 60, gc.getHeight()/2, "right", Color.orange );
			start = new SimpleButton( gc.getWidth()/2 - 20, gc.getHeight()*23/24, "start", Color.orange );
			back = new SimpleButton( 0, gc.getHeight()*23/24, "INDIETRO", Color.orange );
			
			// LETTURA DA FILE .XML			
			try {
				documentFactory = DocumentBuilderFactory.newInstance();
	 
				builder = documentFactory.newDocumentBuilder();
				document = builder.parse( new File( "data/livello1.xml" ) );
	 
				NodeList persone = document.getElementsByTagName("persona");
	 
				System.out.println("Totale persone: " + persone.getLength());
	 
				for(int i=0; i<persone.getLength(); i++) {
					Node nodo = persone.item(i);
	 
					if(nodo.getNodeType() == Node.ELEMENT_NODE) {
						Element persona = (Element)nodo;
	 
						String nome = persona.getElementsByTagName("nome").item(0).getFirstChild().getNodeValue();
						String cognome = persona.getElementsByTagName("cognome").item(0).getFirstChild().getNodeValue();
						String telefono = persona.getElementsByTagName("telefono").item(0).getFirstChild().getNodeValue();
	 
						System.out.println("Nome: " + nome);
						System.out.println("Cognome: " + cognome);
						System.out.println("Telefono: " + telefono);
					}
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	
	public void draw( GameContainer gc ) throws SlickException
		{
			sfondo = Begin.livelli.get( pos ).getImage();
			sfondo.draw( gc );
		
			ArrayList<Ostacolo> obs = Begin.livelli.get( pos ).getElements();
			for(int i = 0; i < obs.size(); i++)
				obs.get( i ).draw( gc.getGraphics() );
			
			left.draw( gc.getGraphics() );
			right.draw( gc.getGraphics() );
			start.draw( gc.getGraphics() );
			back.draw( gc.getGraphics() );
		}
	
	public int getIndexLevel()
		{ return pos; }
	
	public void update( GameContainer gc ) throws SlickException
		{
			Input input = gc.getInput();
			int mouseX = input.getMouseX();
			int mouseY = input.getMouseY();
			
			if((left.checkClick( mouseX, mouseY ) && input.isMousePressed( Input.MOUSE_LEFT_BUTTON )) || input.isKeyPressed( Input.KEY_LEFT ))
				pos = Math.max( pos - 1, 0 );
			else if((right.checkClick( mouseX, mouseY ) && input.isMousePressed( Input.MOUSE_LEFT_BUTTON )) || input.isKeyPressed( Input.KEY_RIGHT ))
				pos = Math.min( pos + 1, Begin.livelli.size() - 1);
			else if((back.checkClick( mouseX, mouseY ) && input.isMousePressed( Input.MOUSE_LEFT_BUTTON )) || input.isKeyPressed( Input.KEY_BACK ))
				{
					Start.chooseLevel = 0;
					Start.recoverPreviousStats();
				}
			else if((start.checkClick( mouseX, mouseY ) && input.isMousePressed( Input.MOUSE_LEFT_BUTTON )) || input.isKeyPressed( Input.KEY_ENTER ))
				{
					Start.ig.addOstacoli( Begin.livelli.get( pos ).getElements(), Begin.livelli.get( pos ).getImage() );
					
					Start.chooseLevel = 0;
					Start.startGame = 1;
					Start.setPreviuosStats( "chooseLevel" );
				}
			else if(input.isKeyPressed( Input.KEY_ESCAPE ))
				{
					Start.chooseLevel = 0;
					Start.begin = 1;
				}
		}
}













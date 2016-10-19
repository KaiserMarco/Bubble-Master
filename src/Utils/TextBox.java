
package Utils;

import java.io.File;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.gui.TextField;

import dataButton.SimpleButton;

public class TextBox
{
	/* immagine della finestra di dialogo */
	private Image img;
	/* casella di testo */
	private TextField text;
	/* font della casella di testo */
	private Font font;
	/* area della finestra */
	private Rectangle area;
	/* determina se e' attivo */
	private boolean isOpen = false;
	/* bottoni per la scelta */
	private SimpleButton buttons[];
	/* nomi dei bottoni */
	private String data[] = { "OK", "CANCEL" };
	/* determina se e' stato premuto un pulsante */
	private boolean pressed = false;
	/* determina se ho modificato il nome originale del livello */
	//private boolean modified = false;
	
	@SuppressWarnings("unchecked")
    public TextBox( final GameContainer gc ) throws SlickException
	{
	    UnicodeFont font = new UnicodeFont( "./data/fonts/prstart.ttf", 10, false, true );
        font.addAsciiGlyphs();
        font.addGlyphs( 600, 400 );
        font.getEffects().add( new ColorEffect( java.awt.Color.WHITE ) );
        font.loadGlyphs();
        
        createWindow( gc, font );
    }

	public TextBox( final GameContainer gc, final Font font ) throws SlickException
	{
	    createWindow( gc, font );
	}
	
	/**
	 * Crea la finestra.
	 * 
	 * @param gc       il contenitore del gioco
	 * @param font     il font da assegnare alla casella di testo
	*/
	private void createWindow( final GameContainer gc, final Font font ) throws SlickException
	{
	    area = new Rectangle( Global.W/2 - Global.W*10/53, Global.H/2 - Global.H/6, Global.W*10/26, Global.H/3 );
        text = new TextField( gc, font, (int)area.getCenterX() - Global.W/8, (int)area.getCenterY() - 35, Global.W/4, Global.H/20 );
        text.setBackgroundColor( Color.black );
        text.setMaxLength( 15 );

        buttons = new SimpleButton[data.length];

        img = new Image( "data/Image/Window.png" );

        this.font = font;
        //int x = text.getX() + (int) Global.sizewBox / 4, y = text.getY() + (int) (Global.sizehBox * 3/2);
        int x = text.getX() + Global.W/40, y = text.getY() + Global.H/30;
        for(int i = 0; i < data.length; i++)
	        {
	            buttons[i] = new SimpleButton( x + Global.W/40*i, y + Global.H/30, data[i], new Color( 20, 35, 120 ) );
	            x = x + (int)buttons[i].getRect().getWidth() + Global.W/40;
	        }
	}

	/**
	 * Modifica il valore di visibilita' della finestra.<br>
	 * Aggiorna automaticamente il focus alla textbox.
	*/
	public void setOpen( final boolean isOpen )
	{
		this.isOpen = isOpen;
		text.setFocus( isOpen );
	}

	/**
	 * Restituisce il valore di attivazione della finestra
	 * 
	 * @return TRUE se e' attivo, FALSE altrimenti
	*/
	public boolean isOpen()
	{
		return isOpen;
	}

	/**
	 * Restituisce il testo inserito
	 * 
	 * @return text - il testo
	*/
	public String getText()
	{
		return text.getText();
	}
	
	/** setta il vecchio nome del livello (se ne aveva uno) */
	public void setText( final String name )
	{
	    text.setText( name );
	    text.setCursorPos( text.getText().length() );
	}

	/** assegna il focus alla casella di testo */
	public void setFocus()
	{
		text.setFocus( true );
	}

	/** controlla se il nome inserito e' corretto
	 * @param txt - il nome da testare
	 * 
	 * @return TRUE se il nome scelto e' corretto, FALSE altrimenti
	*/
	private boolean checkName( String txt, Livello level )
	{
		String name = txt + ".xml";
		if(level != null)
			if(level.getName().equals( txt ))
				return true;
		File directory = new File( "data/livelli" );
		for(File f: directory.listFiles()) {
			if(name.equals( f.getName() ))
				//TODO ErrorWindow.setOpen( ErrorWindow.NAME_ALREADY_EXISTS );
				return false;
		}

		return true;
	}

	/** aggiorna la finestra di dialogo
	 * @param input - il gestore degli input
 	 * @param level - il livello corrente
	*/
	public void update( Input input, Livello level )
	{
		if(!isOpen)
			return;

		//if(StateWindow.isOpen() && text.hasFocus())
			//text.setFocus( false );

		//ErrorWindow.update( input );
		//if(ErrorWindow.isOpen())
			//return;
		
		if(input.isKeyPressed( Input.KEY_ENTER ))
			{
				if(text.getText().length() > 0 && checkName( text.getText(), level ))
					{
						//TODO CreateLevel.saveLevel();
						setOpen( false );
					}
			}

		int x = input.getMouseX(), y = input.getMouseY();
		if(input.isMouseButtonDown( Input.MOUSE_LEFT_BUTTON ))
			{
				if(!pressed)
					{
						for(int i = 0; i < buttons.length; i++)
							{
								if(buttons[i].getRect().contains( x, y ))
									{
										buttons[i].setPressed();
										pressed = true;
										break;
									}
							}
					}
			}
		else
			{
				pressed = false;
	
				for(int i = 0; i < buttons.length; i++)
					{
						if(buttons[i].isPressed())
							{
								buttons[i].setPressed();
								if(buttons[i].getRect().contains( x, y ))
									{
										if(buttons[i].getName().equals( data[0] ))
											{
												// premuto tasto OK: salva la mappa se non ci sono problemi					
												if(text.getText().length() > 0 && checkName( text.getText(), level ))
													{
														//TODO CreateLevel.saveLevel();
														setOpen( false );
													}
											}
										else
											{
												// premuto tasto CANCEL: chiude la finestra
									        	text.setText( "" );
										        setOpen( false );
											}
										break;
									}
							}
					}
			}
	}

	/** disegna la finestra di dialogo
	 * @param gc - il contenitore del gioco
	 * @param g - il contesto grafico
	*/
	public void render( GameContainer gc, Graphics g )
	{
		if(isOpen){
			img.draw( area.getX(), area.getY(), area.getWidth(), area.getHeight() );

			for(int i = 0; i < buttons.length; i++)
				buttons[i].draw( g );

			g.setColor( Color.white );
			text.render( gc, g );

			String txt = "INSERIRE NOME LIVELLO";
			float x = area.getCenterX() - font.getWidth( txt ) / 2;
			float y = text.getY() - (text.getY() - area.getY()) / 2 - font.getHeight( txt ) / 2;

			g.scale( Global.W/Global.Width, Global.H/Global.Height );
			font.drawString( x * Global.Width/Global.W, y * Global.Height/Global.H, txt, Color.red );
			g.resetTransform();

			//ErrorWindow.draw( g );
		}
	}
}
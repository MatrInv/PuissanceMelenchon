package plateau;
import java.awt.BorderLayout;

import javax.swing.JFrame;

import modele.Modele;

public class Puissance4 extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Puissance4() {		
		super("Puissance 4");
		this.setSize(600,500);
		
		Modele m = new Modele();
		PlateauJeu plateau = new PlateauJeu(m);
		Notification notif = new Notification(m);
		
		this.add(BorderLayout.NORTH, notif);
		this.add(BorderLayout.CENTER, plateau);
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		new Puissance4();
	}

}

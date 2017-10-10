package plateau;
import javax.swing.JFrame;

import modele.Modele;

public class Puissance4 extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Puissance4() {		
		super("Puissance 4");
		this.setSize(500,400);
		Modele m = new Modele();
		PlateauJeu plateau = new PlateauJeu(m);
		this.add(plateau);
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		new Puissance4();
	}

}

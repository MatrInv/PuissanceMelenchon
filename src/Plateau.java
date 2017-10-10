import javax.swing.JFrame;

import plateau.PlateauJeu;

public class Plateau extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Plateau() {		
		super("Puissance 4");
		this.setSize(500,400);
		int[][] jeu = { {0,1,2,2},{1,2,0,2}, {1,2,0,2}, {1,2,0,2} };
		PlateauJeu plateau = new PlateauJeu(jeu);
		this.add(plateau);
		this.setVisible(true);
		
	}
	
	public static void main(String[] args) {
		new Plateau();
	}

}

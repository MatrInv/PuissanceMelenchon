package modele;

import java.util.Observable;

public class Modele extends Observable {

	private int hauteur = 6;
	private int largeur = 7;
	private MCTS mcts;
	private int joueurActu;
	// rajouter etat a la place de plateau et �ventuellement le mettre dans mcts
	private Etat etatActu;

	public Modele() {
		// creer etat vide
		etatActu = new Etat(largeur, hauteur);
		joueurActu = 1;
	}

	public int getCase(int x, int y) {
		return etatActu.getCase(x, y);
	}

	public void jouerJeton(int x) {
		
			if (etatActu.colJouable(x)) {
				etatActu.jouerCol(x, joueurActu);
				if(etatActu.estFinal(joueurActu,x)){
					System.out.println("Gagné pour le gros fils de chien !");
				}
				System.out.println(x+","+etatActu.hauteurDernierJeton(x));
				changerJoueur();
				miseAJour();
			}
	}

	public String tabToString() {
		StringBuilder sb = new StringBuilder();
		for (int y = 0; y < hauteur; y++) {
			for (int x = 0; x < largeur; x++) {
				sb.append(etatActu.getCase(x, y) + " ");
			}
			sb.append("\n");
		}
		sb.append("\n");
		return sb.toString();
	}

	public void miseAJour() {
		this.setChanged();
		this.notifyObservers();
	}

	public void changerJoueur() {
		if (joueurActu == 1) {
			joueurActu = 2;
		} else {
			joueurActu = 1;
		}
	}

	public int getHauteur() {
		return hauteur;
	}

	public void setHauteur(int hauteur) {
		this.hauteur = hauteur;
	}

	public int getLargeur() {
		return largeur;
	}

	public void setLargeur(int largeur) {
		this.largeur = largeur;
	}

	public int getJoueurActu() {
		return joueurActu;
	}

}

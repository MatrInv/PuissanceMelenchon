package modele;

import java.util.Iterator;
import java.util.Observable;

public class Modele extends Observable {

	private int hauteur = 6;
	private int largeur = 7;
	private MCTS mcts;
	private int joueurActu;
	private Etat etatActu;
	public final static int JOUEUR = -1;
	public final static int MACHINE = 1;

	public Modele() {
		joueurActu = -1;
		etatActu = new Etat(largeur, hauteur, joueurActu);
		mcts = new MCTS(etatActu, largeur, hauteur);
	}

	public int getCase(int x, int y) {
		return etatActu.getCase(x, y);
	}

	public void jouerJeton(int x) {
		
			if (etatActu.colJouable(x)) {
				etatActu.jouerCol(x, joueurActu);
				changerJoueur();
				miseAJour();
			}
	}

	public void miseAJour() {
		this.setChanged();
		this.notifyObservers();
	}

	public void changerJoueur() {
		joueurActu *= -1;
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

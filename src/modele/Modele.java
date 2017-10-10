package modele;

import java.util.Observable;

public class Modele extends Observable {

	private int hauteur = 6;
	private int largeur = 7;
	private MCTS mcts;
	private int joueurActu;
	//rajouter etat a la place de plateau et éventuellement le mettre dans mcts
	private int[][] plateau;

	public Modele() {
		// creer etat vide
		joueurActu = 1;
		plateau = new int[largeur][hauteur];
	}

	public int getCase(int x, int y) {
		return plateau[x][y];
	}

	public void jouerJeton(int x) {
		int y = hauteur - 1;
		while (plateau[x][y] != 0 & y > 0) {
			y--;
		}
		plateau[x][y] = joueurActu;
		changerJoueur();
		miseAJour();
	}

	public String tabToString() {
		StringBuilder sb = new StringBuilder();
		for (int y = 0; y < hauteur; y++) {
			for (int x = 0; x < largeur; x++) {
				sb.append(plateau[x][y] + " ");
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

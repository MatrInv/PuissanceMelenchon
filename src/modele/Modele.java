package modele;

import java.util.Observable;

public class Modele extends Observable {

	private int hauteur = 6;
	private int largeur = 7;
	private MCTS mcts;
	private Etat etatActu;
	public final static int JOUEUR = -1;
	public final static int MACHINE = 1;
	private final static int C = 1000;

	public Modele() {
		etatActu = new Etat(largeur, hauteur, JOUEUR);
	}

	public void jouerJeton(int x) {		
			if(etatActu.getJoueur() == JOUEUR) {
				if (etatActu.colJouable(x)) {
					etatActu.jouerCol(x, etatActu.getJoueur());
				}
			}else {
				etatActu.changerJoueur();
				mcts = new MCTS(etatActu, largeur, hauteur, C);
				etatActu = mcts.jouer();
				}		
			if(etatActu.estFinal()) {
				if(etatActu.getJoueur()==-1) {
					System.out.println("Le joueur a gagné !");
				}else {
					System.out.println("La machine a gagné !");
				}
			}
			miseAJour();
			etatActu.changerJoueur();
	}

	public void miseAJour() {
		this.setChanged();
		this.notifyObservers();
	}
	
	public int getCase(int x, int y) {
		return etatActu.getCase(x, y);
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
		return etatActu.getJoueur();
	}
	
	public int fin() {
		if(etatActu.estFinal()) {
			return etatActu.getJoueur();
		}
		return 0;
	}
	
	public void reset() {
		etatActu=new Etat(largeur,hauteur,JOUEUR);
		miseAJour();
	}
	
}

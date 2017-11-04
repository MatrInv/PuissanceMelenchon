package modele;

import java.util.Observable;

public class Modele extends Observable {

	private int hauteur = 6;
	private int largeur = 7;
	private MCTS mcts;
	private Etat etatActu; 
	public final static int JOUEUR = -1;
	public final static int MACHINE = 1;
	private int tpsMCTS = 1000;
	private boolean fini = false;

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
			mcts = new MCTS(etatActu, largeur, hauteur, tpsMCTS);
			etatActu = mcts.jouer();
			}		
 		miseAJour();
		etatActu.changerJoueur();
		if(etatActu.estFinal())
			fini = true;
	}

	public void jouerMCTS() {
			mcts = new MCTS(etatActu, largeur, hauteur, tpsMCTS);
			etatActu = mcts.jouer();
			miseAJour();
	}

	public void miseAJour() {
		setChanged();
		notifyObservers();
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

	public boolean estFini() {
		return fini;
	}
	
	public int gagnant() {
		if (etatActu.estFinal()) {
			return etatActu.getJoueur();
		}
		return 0;
	}

	public void reset() {
		etatActu = new Etat(largeur, hauteur, JOUEUR);
		etatActu.changerJoueur();
		fini = false;
	}
	
	public int nbSimulations() {
		return mcts.nbSimulations();
	}
	
	public float estimation() {
		return mcts.estimation();
	}

}

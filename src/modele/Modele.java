package modele;

import java.util.Observable;

public class Modele extends Observable {

	private int hauteur = 6;
	private int largeur = 7;
	private int critere;
	public final static int CRITMAX = 1;
	public final static int CRITROBUSTE = 0;
	private MCTS mcts;
	private Etat etatActu;
	private int joueurActu;
	public final static int JOUEUR = -1;
	public final static int MACHINE = 1;
	private int tpsMCTS = 1000;
	private boolean fini = false;

	public Modele() {
		etatActu = new Etat(largeur, hauteur, JOUEUR);
		joueurActu = JOUEUR;
		critere=CRITROBUSTE;
	}

	public void jouerJeton(int x) {

		if (joueurActu == JOUEUR) {
			if (etatActu.colJouable(x)) {
				etatActu.jouerCol(x, etatActu.getJoueur());
				joueurActu *= -1;
			}
		} else {
			mcts = new MCTS(etatActu, largeur, hauteur, tpsMCTS);
			etatActu = mcts.jouer();
			joueurActu *= -1;
			etatActu.changerJoueur();
		}
		miseAJour();

		if (etatActu.estFinal())
			fini = true;
	}

	public void jouerMCTS() {
		mcts = new MCTS(etatActu, largeur, hauteur, tpsMCTS);
		mcts.setCrit(critere);
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

	public int getCritere() {
		return critere;
	}
	
	public void setCritere(int c) {
		critere=c;
	}

	public int getJoueurActu() {
		return joueurActu;
	}

	public boolean estFini() {
		return fini;
	}

	public void reset() {
		etatActu = new Etat(largeur, hauteur, JOUEUR);
		joueurActu = JOUEUR;
		fini = false;
	}

	public int nbSimulations() {
		return mcts.nbSimulations();
	}

	public float estimation() {
		return mcts.estimation();
	}

}

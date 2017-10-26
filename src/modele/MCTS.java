package modele;

import java.util.Iterator;
import java.util.Random;

public class MCTS {

	private Etat etatActu;
	private Etat racine;

	public MCTS(Etat etat, int largeur, int hauteur) {
		racine = new Etat(largeur, hauteur, etat.getJoueur());
		etatActu = etat;
	}
	
	/**
	 * choisit un fils al�atoire parmi les fils d�velopp�s d'un �tat e
	 * @param e
	 * @return
	 */

	public Etat choixFilsAlea(Etat e) {
		Random r = new Random();
		// chiffre aleatoire de 1 � nombre de fils possible
		int i = r.nextInt(e.getNbFils()) + 1;
		Iterator<Etat> it = e.getFils();
		Etat fils = null;
		// selection du i�me fils
		while (it.hasNext() && i >= 0) {
			fils = (Etat) it.next();
			i--;
		}
		return fils;
	}

	/**
	 * joue une partie jusqu'� un �tat final en partant de l'�tat e
	 * @param e
	 * @return
	 */
	
	public int marcheAleatoire(Etat e) {
		Etat etatFils = e;
		//teste si les fils ont bien �t� cr��s dans e, sinon les cr�e si e n'est pas etat final
		if(e.getNbFils() != 0)
			etatFils = choixFilsAlea(e);
		else if(!etatFils.estFinal())
			etatFils.calculFils(etatFils.getJoueur());
		//boucle qui calcule de nouveaux fils, et joue jusqu a un etat final
		while (!etatFils.estFinal()) {
			etatFils.calculFils(etatFils.getJoueur());
			etatFils = choixFilsAlea(etatFils);
		}
		//retourne le joueur qui a declanche l etat final 
		return etatFils.getJoueur() == Modele.MACHINE ? 1 : 0;
	}

	/**
	 * met � jour les valeurs N(i) et mu(i) contenues dans un �tat e avec la r�compense r
	 * @param e
	 * @param r
	 */
	
	public void btUpdate(Etat e, int r) {

		int ni = e.getN();
		int newN = ni + 1;
		e.setN(newN);

		float mui = e.getMu();
		float newMu = (ni * mui + r) / newN;
		e.setMu(newMu);

		if (e.getPere() != null) {
			btUpdate(e.getPere(), r);
		}
	}

	public static void main(String[] args) {
		MCTS m = new MCTS(new Etat(7, 6, -1), 7, 6);
		m.etatActu.calculFils(1);
		System.out.println(m.marcheAleatoire(m.etatActu));
	}
}

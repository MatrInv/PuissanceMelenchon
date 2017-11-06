package modele;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class MCTS {

	private Etat racine;
	private int nbParties;
	private float estimationProbaVictoire;
	private float tps;
	private Random r;
	public final static double C = Math.sqrt(2) ;

	public MCTS(Etat etat, int largeur, int hauteur, float tps) {
		racine = etat.cloneEtat();
		nbParties = 0;
		estimationProbaVictoire = 0;
		this.tps = tps;
	}

	public Etat jouer() {
		long tempsDebut = System.currentTimeMillis();
		racine.calculFils(racine.getJoueur());
		int recompense = 0;
		Etat e = racine;
		Etat filsAlea = racine;
		
		while (System.currentTimeMillis() - tempsDebut <= tps) {
			nbParties++;
			e = rechercheBValMax(racine);
			filsAlea = e;
			if (filsNonDeveloppes(e).size() > 0) {
				filsAlea = choixFilsAlea(e);
				filsAlea.calculFils(filsAlea.getJoueur());
			}
			recompense = marcheAleatoire(filsAlea);
			btUpdate(filsAlea, recompense);
		}
		e = meilleureMoyDsFils(racine).cloneEtat();
		estimationProbaVictoire = e.getMu();
		return e;
	}

	public Etat meilleureMoyDsFils(Etat e) {
		Iterator<Etat> it = e.getFils();
		Etat etatPlusGrandeMoy = null;
		while (it.hasNext()) {
			Etat etatActu = it.next();
			//condition qui retourne directement un �tat si celui ci est final
			if(etatActu.estFinal())
				return etatActu;
			if (etatPlusGrandeMoy == null || etatActu.getMu() >= etatPlusGrandeMoy.getMu()) {
				etatPlusGrandeMoy = etatActu;
			}
		}
		return etatPlusGrandeMoy;
	}

	public Etat meilleureBValDsFils(Etat e) {
		Iterator<Etat> it = e.getFils();
		Etat etatActu = null;
		float bValeurEtatActu;
		Etat etatPlusGrandeBValeur = null;
		float plusGrandeBVal = Float.MIN_VALUE;
		while (it.hasNext()) {
			etatActu = it.next();
			bValeurEtatActu = etatActu.bVal();
			if (bValeurEtatActu >= plusGrandeBVal) {
				etatPlusGrandeBValeur = etatActu;
				plusGrandeBVal = bValeurEtatActu;
			}
		}

		return etatPlusGrandeBValeur;
	}

	/**
	 * retourne le noeud avec la plus grande bvaleur et dont les fils ne sont pas
	 * tous developpes
	 * 
	 * @param e
	 * @param bValeur
	 * @return
	 */
	private Etat rechercheBValMax(Etat e) {
		if (e.getNbFils() > 0 && !e.estFinal()) {
			if (filsNonDeveloppes(e).size() > 0) {
				return e;
			}

			Etat etatPlusGrandeBValeur = meilleureBValDsFils(e);
			if (etatPlusGrandeBValeur != null) {
				e = rechercheBValMax(etatPlusGrandeBValeur);
			}
		}
		return e;
	}

	/**
	 * choisit un fils al�atoire parmi les fils d�velopp�s d'un �tat e
	 * 
	 * @param e
	 * @return
	 */

	private Etat choixFilsAlea(Etat e) {
		r = new Random();
		// selectionne tout les fils non developpes
		Set<Etat> listeFilsNonDev = filsNonDeveloppes(e);
		// chiffre aleatoire de 1 � nombre de fils possible
		int i = r.nextInt(listeFilsNonDev.size()) + 1;
		Iterator<Etat> it = listeFilsNonDev.iterator();
		Etat fils = null;
		// selection du i�me fils
		while (it.hasNext() && i >= 0) {
			fils = (Etat) it.next();
			i--;
		}

		return fils;
	}

	/**
	 * selectionne tout les fils non developpes d'un etat e
	 * 
	 * @param e
	 * @return
	 */
	private Set<Etat> filsNonDeveloppes(Etat e) {
		Set<Etat> listeEtat = new HashSet<Etat>(e.getNbFils());
		if (e.getNbFils() > 0) {
			Iterator<Etat> it = e.getFils();
			while (it.hasNext()) {
				Etat etat = it.next();
				if (etat.getNbFils() == 0) {
					listeEtat.add(etat);
				}
			}
		}
		return listeEtat;
	}

	/**
	 * joue une partie jusqu'� un �tat final en partant de l'�tat e
	 * 
	 * @param e
	 * @return
	 */

	private int marcheAleatoire(Etat e) {
		Etat etatFils = e;
		// teste si les fils ont bien �t� cr��s dans e, sinon les cr�e si e n'est pas un
		// etat final
		if (e.getNbFils() != 0 && !etatFils.estFinal() && filsNonDeveloppes(e).size() > 0)
			etatFils = choixFilsAlea(e);
		// boucle qui calcule de nouveaux fils, et joue jusqu a un etat final
		while (!etatFils.estFinal()) {
			etatFils.calculFils(etatFils.getJoueur());
			etatFils = choixFilsAlea(etatFils);
		}
		// retourne le joueur qui a declanche l etat final
		return etatFils.getJoueur() == Modele.MACHINE ? 1 : 0;
	}

	/**
	 * met � jour les valeurs N(i) et mu(i) contenues dans un �tat e avec la
	 * r�compense r
	 * 
	 * @param e
	 * @param r
	 */

	private void btUpdate(Etat e, int r) {

		int ni = e.getN();
		int newN = ni + 1;
		e.setN(newN);

		float mui = e.getMu();
		float newMu = ((float) ni * mui + (float) r) / (float) newN;
		e.setMu(newMu);

		if (e.getPere() != null) {
			btUpdate(e.getPere(), r);
		}
	}
	
	public int nbSimulations() {
		return nbParties;
	}

	public float estimation() {
		return estimationProbaVictoire;
	}

	public void test() {

		racine.calculFils(racine.getJoueur());
		Iterator<Etat> it = racine.getFils();
		Etat etat = null;
		while (it.hasNext()) {
			etat = it.next();
		}
		etat = rechercheBValMax(racine);

		etat = choixFilsAlea(etat);
		int recompense = marcheAleatoire(etat);
		btUpdate(etat, recompense);

		// deuxieme it

		etat = rechercheBValMax(racine);

		etat = choixFilsAlea(etat);
	}

	public static void main(String[] args) {
		Etat e = new Etat(7, 6, -1);
		MCTS m = new MCTS(e, 7, 6, 2);
		m.test();
	}

	
}

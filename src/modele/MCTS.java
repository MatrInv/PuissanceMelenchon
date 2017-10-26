package modele;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class MCTS {

	private Etat etatActu;
	private Etat racine;

	public MCTS(Etat etat, int largeur, int hauteur) {
		racine = new Etat(largeur, hauteur, etat.getJoueur());
		etatActu = etat.cloneEtat();
	}
	
	/**
	 * retourne le noeud avec la plus grande bvaleur et dont les fils ne sont pas tous developpes
	 * @param e
	 * @param bValeur
	 * @return
	 */
	private Etat noeudRec(Etat e, float bValeur) {
		if(racine.getNbFils() > 0) {
			 if(filsNonDeveloppes(e).size() > 0 || e.estFinal()) {
				 return e;
			 }
			 Iterator<Etat> it = e.getFils();
			 Etat etatActu = null;
			 while(it.hasNext()) {
				 float bValeurEtatActu = etatActu.bVal();
				 if(bValeurEtatActu > e.bVal()) {
					 noeudRec(etatActu, bValeurEtatActu);
				 }
			 }
		}
		return e;
	}
	
	/**
	 * choisit un fils aléatoire parmi les fils développés d'un état e
	 * @param e
	 * @return
	 */

	private Etat choixFilsAlea(Etat e) {
		Random r = new Random();
		//selectionne tout les fils non developpes
		Set<Etat> listeFilsNonDev = filsNonDeveloppes(e);
		// chiffre aleatoire de 1 ï¿½ nombre de fils possible
		int i = r.nextInt(listeFilsNonDev.size()) + 1;
		Iterator<Etat> it = listeFilsNonDev.iterator();
		Etat fils = null;
		// selection du iï¿½me fils
		while (it.hasNext() && i >= 0) {
			fils = (Etat) it.next();
			i--;
		}
		return fils;
	}
	
	/**
	 * selectionne tout les fils non developpes d'un etat e
	 * @param e
	 * @return
	 */
	private Set<Etat> filsNonDeveloppes(Etat e){
		Set<Etat> listeEtat = new HashSet<Etat>(e.getNbFils());
		if(e.getNbFils() > 0) {
			Iterator<Etat> it = e.getFils();
			while(it.hasNext()) {
				Etat etat = it.next();
				if(etat.getNbFils() == 0) {
					listeEtat.add(etat);
				}
			}
		}
		return listeEtat;
	}

	/**
	 * joue une partie jusqu'à un état final en partant de l'état e
	 * @param e
	 * @return
	 */
	
	private int marcheAleatoire(Etat e) {
		Etat etatFils = e;
		//teste si les fils ont bien été créés dans e, sinon les crée si e n'est pas un etat final
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
	 * met à jour les valeurs N(i) et mu(i) contenues dans un état e avec la récompense r
	 * @param e
	 * @param r
	 */
	
	private void btUpdate(Etat e, int r) {

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

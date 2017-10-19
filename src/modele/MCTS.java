package modele;

import java.util.Iterator;
import java.util.Random;

public class MCTS {

	private Etat etatActu;	
	private Etat racine;
	
	public MCTS (Etat etat, int largeur, int hauteur) {
		racine = new Etat(largeur, hauteur);
		etatActu = etat;
	}
	
	public Etat choixFilsAlea(Etat e) {
		Random r = new Random();
		//chiffre aleatoire de 1 à nombre de fils possible
		int i = r.nextInt(e.getNbFils()) + 1;
		Iterator<Etat> it = e.getFils();
		Etat fils = null;
		//selection du ième fils
		while (it.hasNext() && i>=0) { 
			fils = (Etat) it.next();
			i--;
		}
		return fils;
	}
	
	public static void main(String[] args) {
		MCTS m = new MCTS(new Etat(5,5), 5,5);
		System.out.println(m.etatActu.tabToString());
		m.etatActu.calculFils(1);
		Etat e = m.choixFilsAlea(m.etatActu);
		System.out.println(e.tabToString());
		e.calculFils(-1);
		e = m.choixFilsAlea(e);
		System.out.println(e.tabToString());
	}
}

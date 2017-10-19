package modele;

import java.util.Iterator;
import java.util.Random;

public class MCTS {

	private Etat etatActu;	
	private Etat racine;
	
	public MCTS (Etat etat, int largeur, int hauteur) {
		racine = new Etat(largeur, hauteur, etat.getJoueur());
		etatActu = etat;
	}
	
	public Etat choixFilsAlea(Etat e) {
		Random r = new Random();
		//chiffre aleatoire de 1 � nombre de fils possible
		int i = r.nextInt(e.getNbFils()) + 1;
		Iterator<Etat> it = e.getFils();
		Etat fils = null;
		//selection du i�me fils
		while (it.hasNext() && i>=0) { 
			fils = (Etat) it.next();
			i--;
		}
		return fils;
	}
	
	public void btUpdate(Etat e, int r){
		
		int ni=e.getN();
		int newN=ni+1;
		e.setN(newN);
		
		float mui=e.getMu();
		float newMu=(ni*mui+r)/newN;
		e.setMu(newMu);
		
		if(e.getPere()!=null){
				btUpdate(e.getPere(),r);
		}
		
	}
	
	public static void main(String[] args) {
		MCTS m = new MCTS(new Etat(5,5, -1), 5,5);
		System.out.println(m.etatActu.tabToString());
		m.etatActu.calculFils(1);
		Etat e = m.choixFilsAlea(m.etatActu);
		System.out.println(e.tabToString());
		e.calculFils(-1);
		e = m.choixFilsAlea(e);
		System.out.println(e.tabToString());
	}
}

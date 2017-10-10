package modele;

import java.util.Set;

public class Etat {
	
	private int taille;
	private int[][] plateau;
	
	private Etat pere;
	private Set<Etat> fils;
	
	
	public Etat(int t){
		taille=t;
		//plateau=new int[][]();
	}
 
}

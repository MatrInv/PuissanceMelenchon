package modele;

import java.util.Set;

public class Etat {
	
	private MCTS mcts;
	
	//private int hauteur;
	//private int largeur;
	private int[][] plateau;
	
	private Etat pere;
	private Set<Etat> fils;
	
	
	public Etat(int h, int l){
		//hauteur=h;
		//largeur=l;
		
		plateau=new int[h][l];
		for(int i=0;i<=h;i++){
			for(int j=0;j<=l;j++){
				plateau[i][j]=0;
			}
		}
	}	
}

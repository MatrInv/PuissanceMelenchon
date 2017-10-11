package modele;

import java.util.Set;

public class Etat {
	
	private MCTS mcts;
	
	//private int hauteur;
	//private int largeur;
	private int[][] plateau;
	
	private Etat pere;
	private Set<Etat> fils;
	
	
	public Etat(int l, int h){
		//hauteur=h;
		//largeur=l;
		
		plateau=new int[l][h];
		/*for(int i=0;i<=l;i++){
			for(int j=0;j<=h;j++){
				plateau[i][j]=0;
			}
		}*/
	}

	public int getCase(int x, int y) {
		return plateau[x][y];
	}
	
	
	
	private int getNbCol(){
		return plateau.length;
	}
	
	private int getNbLig(){
		return plateau[0].length;
	}
	
	public void jouerCol(int noCol, int player){
			int next=0;
			while(getNbLig()>next && plateau[noCol][next]==0){
				next++;
			}
			plateau[noCol][next-1]=player;
		
	}
	
	public boolean colJouable(int col){
		return (col<getNbCol() && plateau[col][0]==0);
	}
	
	private boolean estFinal(){
		
	}
	
}

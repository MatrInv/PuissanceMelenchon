package modele;

import java.util.Set;

public class Etat {

	private MCTS mcts;

	// private int hauteur;
	// private int largeur;
	private int[][] plateau;

	private Etat pere;
	private Set<Etat> fils;

	public Etat(int l, int h) {
		// hauteur=h;
		// largeur=l;

		plateau = new int[l][h];
		/*
		 * for(int i=0;i<=l;i++){ for(int j=0;j<=h;j++){ plateau[i][j]=0; } }
		 */
	}

	public void nouvellePartie() {
		plateau = new int [getNbCol()][getNbLig()];
	}
	
	public int getCase(int x, int y) {
		return plateau[x][y];
	}

	private int getNbCol() {
		return plateau.length;
	}

	private int getNbLig() {
		return plateau[0].length;
	}

	public void jouerCol(int noCol, int player) {
		int next = 0;
		while (getNbLig() > next && plateau[noCol][next] == 0) {
			next++;
		}
		plateau[noCol][next - 1] = player;

	}

	public boolean colJouable(int col) {
		return (col < getNbCol() && plateau[col][0] == 0);
	}
	
	public boolean estFinal(int player, int x ){
		return (estPlein() || existeAlign(player,x));
	}

	private boolean estPlein() {
		int i = 0;
		while (i < getNbCol()) {
			if (plateau[i][0] == 0)
				return false;
			i++;
		}
		return true;
	}
	
	private boolean existeAlign(int player, int x, int y){
		int k=0;
		int horiz=0, verti=0, diag1=0, diag2=0;
		boolean nord=true, sud=true, est=true, ouest=true, 
				nordest=true, nordouest=true, sudest=true, sudouest=true; //ces var sont là pour indiquer si on est tombé sur un jeton non-player en parcourant les diagonales
		
		while(k<=3 && (sud || nord || est || ouest || sudouest || sudest || nordest || nordouest)){
			k++;
			
			if(est && x+k<getNbCol() && getCase(x+k,y)==player){
				horiz++;
			}else{
				est=false;
			}
			
			if(ouest && x-k>=0 && getCase(x-k,y)==player){
				horiz++;
			}else{
				ouest=false;
			}
			
			if(nord && y+k<getNbLig() && getCase(x,y+k)==player){
				verti++;
			}else{
				nord=false;
			}
			
			if(sud && y-k>=0 && getCase(x,y-k)==player){
				verti++;
			}else{
				sud=false;
			}
			
			if(nordest && x+k<getNbCol() && y+k<getNbLig() && getCase(x+k,y+k)==player){
				diag1++;
			}else{
				nordest=false;
			}
			
			if(sudouest && y-k>=0 && x-k>=0 && getCase(x-k,y-k)==player){
				diag1++;
			}else{
				sudouest=false;
			}
			
			if(sudest && y-k>=0 && x+k<getNbCol() && getCase(x+k,y-k)==player){
				diag2++;
			}else{
				sudest=false;
			}
			
			if(nordouest && y+k<getNbLig() && x-k>=0 && getCase(x-k,y+k)==player){
				diag2++;
			}else{
				nordouest=false;
			}
			
			if(horiz>=3 || verti>=3 || diag1>=3 || diag2>=3){
				return true;
			}
		}
		
		return false;
	}
	
	public boolean existeAlign(int player, int x){
		return existeAlign(player, x, hauteurDernierJeton(x));
	}
	
	public int hauteurDernierJeton(int x){
		int h=0;
		while(h<getNbLig() && getCase(x,h)==0){
			h++;
		}
		return h;
	}

}

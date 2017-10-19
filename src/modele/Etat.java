package modele;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Etat{

	private int[][] plateau;
	private Etat pere;
	private Set<Etat> fils;
	
	private int joueur;
	private boolean estFinal;
	
	private float mu;
	private int n;

	public Etat(int l, int h, int premierJoueur) {
		plateau = new int[l][h];
		fils = new HashSet<Etat>();
		
		joueur=premierJoueur;
		estFinal=false;
		
		mu=0;
		n=0;
	}
	
	private Etat cloneEtat(){
		Etat nvEtat = new Etat(getNbCol(), getNbLig(), getJoueur());
		nvEtat.setMu(getMu());
		nvEtat.setN(getN());
		nvEtat.setEstFinal(estFinal);
		for(int col = 0; col<getNbCol(); col++) {
			for(int lig = 0; lig<getNbLig(); lig++) {
				nvEtat.setCase(col, lig, getCase(col, lig));
			}
		}
		return nvEtat;
	}
	
	private void setEstFinal(boolean b){
		estFinal=b;
	}
	
	private void setPere(Etat e) {
		pere = e;
	}
	
	private void setCase(int x, int y, int val) {
		plateau[x][y] = val;
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

	private boolean estPlein(){
		for(int i=0;i<getNbCol();i++){
			if(getCase(i,0)==0){
				return false;
			}
		}
		return true;
	}

	public Etat getPere() {
		return pere;
	}
	
	public void calculFils(int joueur) {
		for(int col=0; col< getNbCol() ; col++) {
			if(colJouable(col)) {
				Etat e = cloneEtat();
				e.jouerCol(col, joueur*-1);
				if(e.estFinal(joueur,col)){
					e.setEstFinal(true);
				}
				e.setPere(this);
				fils.add(e);
			}
		}
	}
	
	public Iterator<Etat> getFils(){
		return  fils.iterator();	
	}
	
	public boolean estFinal() {
		return estPlein() || estFinal;
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


	public String tabToString() {
		StringBuilder sb = new StringBuilder();
		for (int y = 0; y < getNbLig(); y++) {
			for (int x = 0; x < getNbCol(); x++) {
				sb.append(plateau[x][y] + "   ");
			}
			sb.append("\n");
		}
		sb.append("\n");
		return sb.toString();
	}
	
	
	//Fonction
	
	public void setMu(float u){
		mu=u;
	}
	
	public float getMu(){
		return mu;
	}
	
	public void setN(int m){
		n=m;
	}
	
	public int getN(){
		return n;
	}
	
	public float bVal(){
		return (float) (joueur*mu+Math.sqrt(2*Math.log((double)pere.getN())/getN()));
	}
	
	public int getJoueur(){
		return joueur;
	}

	public int getNbFils() {
		return fils.size();
	}

}

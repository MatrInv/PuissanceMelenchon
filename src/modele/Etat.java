package modele;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Etat{

	private MCTS mcts;
	private int[][] plateau;
	private Etat pere;
	private Set<Etat> fils;

	public Etat(int l, int h) {
		plateau = new int[l][h];
		fils = new HashSet<Etat>();
	}
	
	private Etat cloneEtat(Etat e){
		Etat nvEtat = new Etat(e.getNbCol(), e.getNbLig());
		for(int col = 0; col<e.getNbCol(); col++) {
			for(int lig = 0; lig<e.getNbLig(); lig++) {
				nvEtat.setCase(col, lig, e.getCase(col, lig));
			}
		}
		return nvEtat;
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

	public Etat getPere() {
		return pere;
	}
	
	private void calculFils(int joueur) {
		for(int col=0; col< getNbCol() ; col++) {
			if(colJouable(col)) {
				Etat e = cloneEtat(this);
				e.jouerCol(col, joueur);
				e.setPere(this);
				fils.add(e);
			}
		}
	}
	
	public Iterator<Etat> getFils(int joueur){
		calculFils(joueur);
		return  fils.iterator();	
	}
	
	public boolean estFinal() {
		int i = 0;
		while (i < getNbCol()) {
			if (plateau[i][0] == 0)
				return false;
			i++;
		}
		return true;
	}
	
	public String tabToString() {
		StringBuilder sb = new StringBuilder();
		for (int y = 0; y < getNbLig(); y++) {
			for (int x = 0; x < getNbCol(); x++) {
				sb.append(plateau[x][y] + " ");
			}
			sb.append("\n");
		}
		sb.append("\n");
		return sb.toString();
	}
}

package plateau;

import java.awt.Font;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;

import modele.Modele;

public class Notification extends JPanel implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel message;
	private JLabel estimation;
	private JLabel simulation;
	private Modele m;

	public Notification(Modele mod) {
		super();

		m = mod;
		m.addObserver(this);
		this.setLayout(new GridLayout(2, 3));

		message = new JLabel("A vous de jouer");
		message.setFont(new Font("Arial", Font.BOLD, 20));

		estimation = new JLabel("");
		estimation.setFont(new Font("Arial", Font.BOLD, 20));

		simulation = new JLabel("");
		simulation.setFont(new Font("Arial", Font.BOLD, 20));

		add(message);
		add(new JLabel(""));
		add(estimation);
		add(simulation);
	}

	public void resetDonnees() {
		estimation.setText("");
		simulation.setText("");
	}

	@Override
	public void update(Observable o, Object arg) {

		if (m.getJoueurActu() == m.MACHINE) {
			message.setText("A vous de jouer");
			estimation.setText("Estimation Victoire : " + m.estimation() * 100 + "%");
			simulation.setText("Simulations : " + m.nbSimulations());
		} else {
			message.setText("La machine joue");
			resetDonnees();
		}
		if (m.estFini()) {
			if (m.gagnant() == 1)
				message.setText("La machine a gagn� !");
			if (m.gagnant() == -1)
				message.setText("Vous avez gagn� !");
			resetDonnees();

		}
	}
}

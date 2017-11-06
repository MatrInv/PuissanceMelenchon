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

		if (m.getJoueurActu() == m.JOUEUR) {
			message.setText("A vous de jouer");
			estimation.setText("Estimation Victoire : " + m.estimation() * 100 + "%");
			simulation.setText("Simulations : " + m.nbSimulations());
		} else {
			message.setText("Tour de la machine, cliquez pour la faire jouer");
			resetDonnees();
		}
		if (m.estFini()) {
			if (m.getGagnant() == m.MACHINE)
				message.setText("La machine a gagne !");
			if (m.getGagnant() == m.JOUEUR)
				message.setText("Vous avez gagne !");
			else
				message.setText("Egalite !");
			resetDonnees();

		}
	}
}

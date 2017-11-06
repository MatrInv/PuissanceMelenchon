package plateau;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
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
	private JButton crit;
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

		crit=new JButton();
		crit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				m.changerCritere();
				if(m.getCritere()==Modele.CRITMAX){
					crit.setText("Max");
				}
				else{
					crit.setText("Robuste");
				}
				
			}
			
		});
		
		if(m.getCritere()==Modele.CRITMAX){
			crit.setText("Max");
		}
		else{
			crit.setText("Robuste");
		}
		
		add(message);
		add(crit);
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
			crit.enable();
			message.setText("A vous de jouer");
			estimation.setText("Estimation Victoire : " + m.estimation() * 100 + "%");
			simulation.setText("Simulations : " + m.nbSimulations());
		} else {
			message.setText("Tour de la machine, cliquez pour la faire jouer");
			crit.disable();
			resetDonnees();
		}
		if (m.estFini()) {
			if (m.getJoueurActu() == m.MACHINE)
				message.setText("La machine a gagne !");
			if (m.getJoueurActu() == m.JOUEUR)
				message.setText("Vous avez gagne !");
			resetDonnees();

		}
	}
}

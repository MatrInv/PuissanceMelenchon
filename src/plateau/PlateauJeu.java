package plateau;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import modele.Modele;

public class PlateauJeu extends JPanel implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ImageIcon[] images;
	private JButton[][] plateau;
	private Modele m;

	public PlateauJeu(Modele m) {
		super();

		m.addObserver(this);
		this.m = m;

		this.setBackground(Color.WHITE);
		this.setLayout(new GridBagLayout());
		GridBagConstraints g = new GridBagConstraints();
		g.gridx = 0;
		g.gridy = 0;

		images = new ImageIcon[3];
		images[0] = new ImageIcon(getClass().getResource("/ressources/blanc.png"));
		images[1] = new ImageIcon(getClass().getResource("/ressources/rouge.png"));
		images[2] = new ImageIcon(getClass().getResource("/ressources/vert.png"));
		plateau = new JButton[m.getLargeur()][m.getHauteur()];
		remplirPlateau(g);
	}

	public void remplirPlateau(GridBagConstraints g) {
		for (int lig = 0; lig < m.getHauteur(); lig++) {
			g.gridy = lig;
			for (int col = 0; col < m.getLargeur(); col++) {
				g.gridx = col;
				JButton c = new JButton(images[0]);
				c.setBackground(Color.WHITE);
				c.setPreferredSize(new Dimension(60, 60));
				c.setBorderPainted(false);
				c.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// prevenir modele
						for (int lig = 0; lig < m.getHauteur(); lig++) {
							for (int col = 0; col < m.getLargeur(); col++) {
								if (e.getSource() == plateau[col][lig]) {
									m.jouerJeton(col);
								}
							}
						}
					}

				});
				plateau[col][lig] = c;
				this.add(c, g);
			}
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		for (int y = 0; y < m.getHauteur(); y++) {
			for (int x = 0; x < m.getLargeur(); x++) {
				if(m.getCase(x, y) == Modele.JOUEUR)
					plateau[x][y].setIcon(images[2]);
				else if(m.getCase(x, y) == Modele.MACHINE)
					plateau[x][y].setIcon(images[1]);
			}
		}
	}
}

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

public class PlateauJeu extends JPanel implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ImageIcon[] images;
	private JButton[][] plateau;

	public PlateauJeu(int[][] tab) {
		//ajouter ds liste modele
		super();
		this.setBackground(Color.WHITE);
		this.setLayout(new GridBagLayout());
		GridBagConstraints g = new GridBagConstraints();
		g.gridx = 0;
		g.gridy = 0;

		images = new ImageIcon[3];
		images[0] = new ImageIcon(getClass().getResource("/jetons/blanc.png"));
		images[1] = new ImageIcon(getClass().getResource("/jetons/rouge.png"));
		images[2] = new ImageIcon(getClass().getResource("/jetons/vert.png"));
		plateau = new JButton[tab.length][tab[0].length];
		remplirPlateau(tab, g);
	}

	public void remplirPlateau(int[][] tab, GridBagConstraints g) {
		for (int lig = 0; lig < tab[0].length; lig++) {
			g.gridx = lig;
			for (int col = 0; col < tab.length; col++) {
				g.gridy = col;
				JButton c = new JButton(images[0]);
				c.setBackground(Color.WHITE);
				c.setPreferredSize(new Dimension(60, 60));
				c.setBorderPainted(false);
				c.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// prevenir modele
						for (int col = 0; col < tab[0].length; col++) {
							for (int lig = 0; lig < tab.length; lig++) {
								if (e.getSource() == plateau[lig][col]) {
									// prevenir modele + maj
									poserJeton(col, 1);
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

	public void poserJeton(int col, int image) {
		int lig = plateau.length - 1;
		System.out.println("lig" + lig + "col" + col);
		while (plateau[lig][col].getIcon() != images[0] & lig > 0) {
			lig--;
		}
		plateau[lig][col].setIcon(images[image]);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// a faire quand modele existera...
	}
}

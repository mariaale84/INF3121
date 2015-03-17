/**mariaale Oblig5 spring2013 */
/**main method is in class Oblig5 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.util.Scanner;
import java.io.IOException;
import java.io.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class SudokuGUI extends JFrame  {

    private final int RUTE_STRELSE = 50;	/** Storrelsen paa hver rute */
    private final int PLASS_TOPP = 50;	/** Plass pa toppen av brettet */

    private JTextField[][] brett;   /** for aa tegne ut alle rutene */
    private int dimensjon;		/** storrelsen paa brettet (n) */
    private int vertikalAntall;	/** antall ruter i en boks loddrett */
    private int horisontalAntall;	/** antall ruter i en boks vannrett */
    public SudokuBeholder sudokubeholder;

    JPanel knappePanel;
    JPanel brettPanel;
    Brett losningsBrett;
    int teller = 1;
    int numberLosninger;
    String fileName;

    /** Lager et brett med knapper langs toppen. */
    public SudokuGUI(int dim, int hd, int br,String navn) {

	dimensjon = dim;
	vertikalAntall = hd;
	horisontalAntall = br;

	fileName = navn;

	brett = new JTextField[dimensjon][dimensjon];

	setPreferredSize(new Dimension(dimensjon * RUTE_STRELSE,
				       dimensjon  * RUTE_STRELSE + PLASS_TOPP));
	setTitle("Sudoku " + dimensjon +" x "+ dimensjon);
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	setLayout(new BorderLayout());

	JPanel knappePanel = lagKnapper();
	JPanel brettPanel = lagBrettet();


	getContentPane().add(knappePanel,BorderLayout.NORTH);
	getContentPane().add(brettPanel,BorderLayout.CENTER);
	pack();
	setVisible(true);
    }
    public void fillUp(String[][] kommendeBr){
	for(int i = 0; i < dimensjon; i++) {

	    for(int j = 0; j < dimensjon; j++) {

		brett[i][j].setText(kommendeBr[i][j]);
	    }
	}

    }
    /**
     * Lager et panel med alle rutene.
     * @return en peker til panelet som er laget.
     */

    private JPanel lagBrettet() {
	int topp, venstre;
	JPanel brettPanel = new JPanel();
	brettPanel.setLayout(new GridLayout(dimensjon,dimensjon));
	brettPanel.setAlignmentX(CENTER_ALIGNMENT);
	brettPanel.setAlignmentY(CENTER_ALIGNMENT);
	setPreferredSize(new Dimension(new Dimension(dimensjon * RUTE_STRELSE,
						     dimensjon * RUTE_STRELSE)));

	for(int i = 0; i < dimensjon; i++) {
	    /** finn ut om denne raden trenger en tykker linje paa toppen: */
	    topp = (i % vertikalAntall == 0 && i != 0) ? 4 : 1;
	    for(int j = 0; j < dimensjon; j++) {
		/** finn ut om denne ruten er en del av en kolonne
		    som skal ha en tykkere linje til venstre:       */
		venstre = (j % horisontalAntall == 0 && j != 0) ? 4 : 1;

		JTextField ruten = new JTextField();
		ruten.setBorder(BorderFactory.createMatteBorder
				(topp,venstre,1,1, Color.black));
		ruten.setHorizontalAlignment(SwingConstants.CENTER);
		ruten.setPreferredSize(new Dimension(RUTE_STRELSE, RUTE_STRELSE));

		ruten.setText("A");

		brett[i][j] = ruten;
		brettPanel.add(ruten);
	    }
	}
	return brettPanel;
    }

    /**
     * Lager et panel med noen knapper.
     * @return en peker til panelet som er laget.
     */
    private JPanel lagKnapper() {
	JPanel knappPanel = new JPanel();
	knappPanel.setLayout(new BoxLayout(knappPanel, BoxLayout.X_AXIS));
	JButton finnSvarKnapp = new JButton("Finn losning(er)");
	finnSvarKnapp.addActionListener(new FindSolution());
	JButton nesteKnapp = new JButton("Neste losning");
	nesteKnapp.addActionListener(new NesteKnapp());
	knappPanel.add(finnSvarKnapp);
	knappPanel.add(nesteKnapp);
	return knappPanel;
    }


    class NesteKnapp  implements ActionListener{
	public void actionPerformed(ActionEvent e){
	    numberLosninger = losningsBrett.beh.getSolutionCount();

	    if(teller < numberLosninger){
		fillUp(losningsBrett.beh.get(teller));
		System.out.println(" Solution  "+(teller+1));
		teller++;
	    } else {
		System.out.println(" there're no more solutions, the game should be finished ");
		return;
	    }
	}
    }

    class FindSolution  implements ActionListener{
	public void actionPerformed(ActionEvent e){

	    fillUp(losningsBrett.beh.get(0));
	    System.out.println(" Solution  1 ");

	}
    }


}

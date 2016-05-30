package Interfaz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by Sebastian Patiño Barrientos.
 */
public class MoverTropas extends JFrame implements ActionListener{
    private int idA;
    private int tropasA;
    private int idB;
    private int tropasB;
    private Risk risk;
    private JSpinner cantidadTropas;
    private JButton seleccionar;

    public MoverTropas(int idA, int idB, Risk risk){
        setTitle("Atacando...");
        setSize(300, 200);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.risk = risk;

        this.idA = idA;
        tropasA = risk.getTropasDpto(idA);
        this.idB = idB;
        tropasB = risk.getTropasDpto(idB);
        risk.setEnabled(false);
        SpinnerNumberModel tropas = new SpinnerNumberModel(1, 1, tropasA - 1, 1);
        cantidadTropas = new JSpinner(tropas);
        seleccionar = new JButton("Seleccionar");
        seleccionar.setActionCommand("select");
        seleccionar.addActionListener(this);

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel a = new JLabel("Cantidad de tropas a mover");
        c.gridy = 0;
        add(a, c);

        c.gridx = 0;
        c.gridy = 1;
        add(cantidadTropas, c);

        c.gridx = 1;
        add(seleccionar, c);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int cantidad = (int) cantidadTropas.getValue();
        risk.reduceTropasDpto(idA, cantidad);
        risk.addTropasDpto(idB, cantidad);
        risk.testUpdate();
        risk.setEnabled(true);
        risk.requestFocus();
        this.dispose();
    }

}

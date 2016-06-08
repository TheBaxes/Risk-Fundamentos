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

    private JButton menos;
    Timer reducir;
    private JButton mas;
    Timer adicionar;
    private int tropas;
    private JLabel cantidadDeTropas;

    public MoverTropas(int idA, int idB, Risk risk){
        setTitle("Mover tropas");
        setSize(300, 200);
        setResizable(false);
        setLocationRelativeTo(risk);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.risk = risk;

        this.idA = idA;
        tropasA = risk.getTropasDpto(idA);
        this.idB = idB;
        tropasB = risk.getTropasDpto(idB);
        risk.setEnabled(false);

        menos = new JButton("<");
        menos.addActionListener(this);
        menos.setActionCommand("menos");
        reducir = new Timer(100, this);
        reducir.setActionCommand("reducir");
        menos.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                reducir.start();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                reducir.stop();
            }
        });
        mas = new JButton(">");
        mas.addActionListener(this);
        mas.setActionCommand("mas");
        adicionar = new Timer(100, this);
        adicionar.setActionCommand("adicionar");
        mas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                adicionar.start();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                adicionar.stop();
            }
        });
        tropas = tropasA - 1;
        cantidadDeTropas = new JLabel(tropas + "");
        cantidadDeTropas.setFont(new Font("Arial", Font.PLAIN, 14));
        risk.moverTropas(idA, idB, tropas);
        risk.update(idA, idB);

        seleccionar = new JButton("Seleccionar");
        seleccionar.setActionCommand("select");
        seleccionar.addActionListener(this);

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel a = new JLabel("Cantidad de tropas a mover");
        c.gridy = 0;
        c.gridwidth = 4;
        c.insets = new Insets(0, 0, 40, 0);
        add(a, c);

        c.insets = new Insets(0, 0, 0, 5);
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 1;
        add(menos, c);

        c.insets = new Insets(0, 5, 0, 5);
        c.gridx = 1;
        add(cantidadDeTropas, c);

        c.insets = new Insets(0, 5, 0, 0);
        c.gridx = 2;
        add(mas, c);

        c.gridx = 3;
        c.insets = new Insets(0, 70, 0, 0);
        add(seleccionar, c);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command){
            case "menos":
                if (tropas > 1) {
                    tropas--;
                    risk.moverTropas(idB, idA, 1);
                }
                break;
            case "reducir":
                actionPerformed(new ActionEvent(this, 0, "menos"));
                break;
            case "mas":
                if (tropas < tropasA - 1) {
                    tropas++;
                    risk.moverTropas(idA, idB, 1);
                }
                break;
            case "adicionar":
                actionPerformed(new ActionEvent(this, 0, "mas"));
                break;
            case "select":
                risk.setEnabled(true);
                risk.requestFocus();
                this.dispose();
                String plural = "";
                if(tropas > 1) plural += "s";
                risk.print("El jugador " + (risk.getJugadorDpto(idA) + 1) + " ha movido " + tropas
                + " tropa" + plural + " desde " + risk.getNombreDpto(idA) + " hasta " + risk.getNombreDpto(idB));
                risk.update(idA, idB, false);
                break;
        }
        cantidadDeTropas.setText(tropas + "");
        risk.update(idA, idB);
    }

}

package Interfaz;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Clase Msgbox
 * @author Sebastian Patiño Barrientos.
 */
public class Msgbox extends JPanel{
    public JScrollPane scroll;
    public JTextPane logs;
    public String text;

    /**
     * Crea un cuadro de registros
     */
    public Msgbox() {
        setLayout(new GridBagLayout());
        setBackground(Color.white);

        text = "";
        logs = new JTextPane();
        logs.setEditable(false);
        scroll = new JScrollPane(logs);
        scroll.setAutoscrolls(true);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setPreferredSize(new Dimension(310,128));
        scroll.setMinimumSize(new Dimension(310, 128));

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        add(scroll, c);

    }

    /**
     * Método que muestra un mensaje en el cuadro de registros
     * @param message Mensaje
     */
    public void print(String message){
        text += "-" + message + "\n";
        logs.setText(text);
    }
}

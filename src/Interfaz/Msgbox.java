package Interfaz;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Created by Sebastian Patiño Barrientos.
 */
public class Msgbox extends JPanel{
    public JTextArea msgbox;
    public JScrollPane scroll;
    public Msgbox() {
        setLayout(new GridBagLayout());
        setBackground(Color.white);

        msgbox = new JTextArea(7, 27);
        msgbox.setEditable(false);
        scroll = new JScrollPane(msgbox);
        scroll.setAutoscrolls(true);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        add(scroll, c);
    }

    public void print(String message){
        msgbox.append(message + "\n");
        msgbox.setCaretPosition(msgbox.getDocument().getLength());
    }
}

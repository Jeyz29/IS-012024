import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

class ICentro extends JFrame {
    private JTextField descField, ctField, muField, fechaField, nfField, ciField;
    private JButton addButton, saveButton;
    private JTextArea outputArea;
    private List<Equipo> equipos;

    public ICentro() {
        equipos = new ArrayList<>();
        setTitle("Registro de Equipos");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(8, 2));

        descField = new JTextField();
        ctField = new JTextField();
        muField = new JTextField();
        fechaField = new JTextField();
        nfField = new JTextField();
        ciField = new JTextField();
        outputArea = new JTextArea();
        addButton = new JButton("Agregar");
        saveButton = new JButton("Guardar");

        add(new JLabel("Descripción:"));
        add(descField);
        add(new JLabel("Cantidad:"));
        add(ctField);
        add(new JLabel("Costo Unitario:"));
        add(muField);
        add(new JLabel("Fecha (dd/mm/aaaa):"));
        add(fechaField);
        add(new JLabel("Número de Factura:"));
        add(nfField);
        add(new JLabel("CI Responsable:"));
        add(ciField);
        add(addButton);
        add(saveButton);
        add(new JScrollPane(outputArea));

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarEquipo();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarEquipos();
            }
        });

        setVisible(true);
    }

    private void agregarEquipo() {
        String desc = descField.getText();
        int ct = Integer.parseInt(ctField.getText());
        double mu = Double.parseDouble(muField.getText());
        String fecha = fechaField.getText();
        String nf = nfField.getText();
        String ci = ciField.getText();
        Equipo equipo = new Equipo(desc, ct, mu, fecha, nf, ci);
        equipos.add(equipo);
        outputArea.append(equipo.toString() + "\n");
    }

    private void guardarEquipos() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("inventario.txt"))) {
            for (Equipo equipo : equipos) {
                writer.write(equipo.toString());
                writer.newLine();
            }
            JOptionPane.showMessageDialog(this, "Equipos guardados en inventario.txt");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar en el archivo: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new ICentro();
    }
}


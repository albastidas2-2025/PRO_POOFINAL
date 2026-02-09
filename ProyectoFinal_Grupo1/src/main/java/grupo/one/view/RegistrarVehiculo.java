package grupo.one.view;

import grupo.one.controller.VehiculoController;
import grupo.one.factory.VehiculoFactory;
import javax.swing.*;
import java.awt.*;

public class RegistrarVehiculo extends JDialog {
    private JComboBox<String> comboTipo;
    private JTextField txtPlaca, txtMarca, txtModelo, txtPrecio, txtParametro;
    private JLabel lblParametro;
    private VehiculoController vehiculoController;

    public RegistrarVehiculo(JFrame parent, VehiculoController controller) {
        super(parent, "Registrar Nuevo Vehículo", true);
        this.vehiculoController = controller;

        setSize(400, 350);
        setLocationRelativeTo(parent);

        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Tipo de vehículo
        panel.add(new JLabel("Tipo:"));
        comboTipo = new JComboBox<>(new String[]{"AUTO", "MOTO"});
        comboTipo.addActionListener(e -> actualizarEtiquetaParametro());
        panel.add(comboTipo);

        // Placa
        panel.add(new JLabel("Placa:"));
        txtPlaca = new JTextField();
        panel.add(txtPlaca);

        // Marca
        panel.add(new JLabel("Marca:"));
        txtMarca = new JTextField();
        panel.add(txtMarca);

        // Modelo
        panel.add(new JLabel("Modelo:"));
        txtModelo = new JTextField();
        panel.add(txtModelo);

        // Precio
        panel.add(new JLabel("Precio/día:"));
        txtPrecio = new JTextField();
        panel.add(txtPrecio);

        // Parámetro específico
        lblParametro = new JLabel("Puertas:");
        panel.add(lblParametro);
        txtParametro = new JTextField("4");
        panel.add(txtParametro);

        // Botones
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");

        btnGuardar.addActionListener(e -> guardarVehiculo());
        btnCancelar.addActionListener(e -> dispose());

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.add(btnCancelar);
        panelBotones.add(btnGuardar);

        add(panel, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        actualizarEtiquetaParametro();
    }

    private void actualizarEtiquetaParametro() {
        String tipo = (String) comboTipo.getSelectedItem();
        if ("AUTO".equals(tipo)) {
            lblParametro.setText("Puertas:");
            txtParametro.setText("4");
        } else {
            lblParametro.setText("Cilindrada:");
            txtParametro.setText("150");
        }
    }

    private void guardarVehiculo() {
        try {
            String tipo = (String) comboTipo.getSelectedItem();
            String placa = txtPlaca.getText().trim();
            String marca = txtMarca.getText().trim();
            String modelo = txtModelo.getText().trim();
            String precioStr = txtPrecio.getText().trim();
            String paramStr = txtParametro.getText().trim();
            if (placa.isEmpty() || marca.isEmpty() || modelo.isEmpty() ||
                    precioStr.isEmpty() || paramStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios");
                return;
            }
            double precio = Double.parseDouble(precioStr);
            int parametro = Integer.parseInt(paramStr);
            var vehiculo = VehiculoFactory.crearVehiculo(tipo, placa, marca, modelo, precio, parametro);
            if (vehiculoController.registrarVehiculo(vehiculo)) {
                JOptionPane.showMessageDialog(this,
                        "Vehículo registrado exitosamente\n\n" +
                                "Placa: " + placa + "\n" +
                                "Tipo: " + tipo + "\n" +
                                "Marca: " + marca + "\n" +
                                "Modelo: " + modelo + "\n" +
                                "Precio: $" + precio + "/día\n" +
                                (tipo.equals("AUTO") ? "Puertas: " : "Cilindrada: ") + parametro,
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                dispose(); // Cerrar ventana
            } else {
                JOptionPane.showMessageDialog(this,
                        "Error al registrar vehículo (¿placa duplicada?)",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Precio y parámetro deben ser números válidos",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

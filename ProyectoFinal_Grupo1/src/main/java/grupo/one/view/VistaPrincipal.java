package grupo.one.view;

import grupo.one.controller.VehiculoController;
import grupo.one.controller.AlquilerController;
import grupo.one.factory.VehiculoFactory;
import grupo.one.model.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class VistaPrincipal extends JFrame {
    // Controllers
    private final VehiculoController vehiculoController;
    private final AlquilerController alquilerController;

    // Panel de Registro de Alquiler
    private JComboBox<String> comboVehiculos;
    private JTextField txtDias;
    private JButton btnRegistrarAlquiler;
    private JLabel lblTotalCalculado;

    // Panel de Búsqueda
    private JTextField txtBuscarPlaca;
    private JButton btnBuscar;
    private JTextArea txtResultadoBusqueda;

    // Panel de Información
    private JTextArea txtAreaInfo;

    public VistaPrincipal(VehiculoController vehiculoController, AlquilerController alquilerController) {
        this.vehiculoController = vehiculoController;
        this.alquilerController = alquilerController;

        configurarVentana();
        inicializarComponentes();
        cargarDatosIniciales();
    }

    private void configurarVentana() {
        setTitle("DevRental - Sistema de Alquiler de Vehículos");
        setSize(800, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(240, 240, 240));
    }

    private void inicializarComponentes() {
        // Panel principal con pestañas
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));

        // Pestaña 1: Registrar Alquiler
        tabbedPane.addTab("Registrar Alquiler", crearPanelRegistro());

        // Pestaña 2: Buscar Vehículo
        tabbedPane.addTab("Buscar Vehículo", crearPanelBusqueda());

        // Pestaña 3: Información del Sistema
        tabbedPane.addTab("Información", crearPanelInformacion());

        // Pestaña 4: Registrar Vehículo (NUEVA)
        tabbedPane.addTab("Registrar Vehículo", crearPanelRegistroVehiculo());

        add(tabbedPane);
    }

    private JPanel crearPanelRegistro() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título
        JLabel titulo = new JLabel("REGISTRO DE ALQUILER");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setForeground(new Color(0, 102, 204));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titulo, gbc);

        // Separador
        JSeparator separator = new JSeparator();
        gbc.gridy = 1;
        panel.add(separator, gbc);

        // Vehículo
        JLabel lblVehiculo = new JLabel("Seleccionar Vehículo:");
        lblVehiculo.setFont(new Font("Arial", Font.PLAIN, 14));

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panel.add(lblVehiculo, gbc);

        comboVehiculos = new JComboBox<>();
        comboVehiculos.setFont(new Font("Arial", Font.PLAIN, 14));
        comboVehiculos.setBackground(Color.WHITE);

        gbc.gridx = 1;
        panel.add(comboVehiculos, gbc);

        // Días de alquiler
        JLabel lblDias = new JLabel("Días de Alquiler:");
        lblDias.setFont(new Font("Arial", Font.PLAIN, 14));

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(lblDias, gbc);

        txtDias = new JTextField(10);
        txtDias.setFont(new Font("Arial", Font.PLAIN, 14));
        txtDias.setHorizontalAlignment(JTextField.RIGHT);

        gbc.gridx = 1;
        panel.add(txtDias, gbc);

        // Botón para calcular
        JButton btnCalcular = new JButton("Calcular Total");
        btnCalcular.setFont(new Font("Arial", Font.BOLD, 12));
        btnCalcular.setBackground(new Color(70, 130, 180));
        btnCalcular.setForeground(Color.WHITE);
        btnCalcular.setFocusPainted(false);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(btnCalcular, gbc);

        // Total calculado
        lblTotalCalculado = new JLabel("Total: $0.00");
        lblTotalCalculado.setFont(new Font("Arial", Font.BOLD, 16));
        lblTotalCalculado.setForeground(new Color(0, 100, 0));
        lblTotalCalculado.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridy = 5;
        panel.add(lblTotalCalculado, gbc);

        // Botón Registrar
        btnRegistrarAlquiler = new JButton("REGISTRAR ALQUILER");
        btnRegistrarAlquiler.setFont(new Font("Arial", Font.BOLD, 14));
        btnRegistrarAlquiler.setBackground(new Color(34, 139, 34));
        btnRegistrarAlquiler.setForeground(Color.WHITE);
        btnRegistrarAlquiler.setFocusPainted(false);
        btnRegistrarAlquiler.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        gbc.gridy = 6;
        panel.add(btnRegistrarAlquiler, gbc);

        // Configurar eventos
        btnCalcular.addActionListener(e -> calcularTotal());
        btnRegistrarAlquiler.addActionListener(e -> registrarAlquiler());
        txtDias.addActionListener(e -> calcularTotal());

        return panel;
    }

    private JPanel crearPanelBusqueda() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Panel superior de búsqueda
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBusqueda.setBackground(Color.WHITE);

        JLabel lblBuscar = new JLabel("Placa del Vehículo:");
        lblBuscar.setFont(new Font("Arial", Font.PLAIN, 14));

        txtBuscarPlaca = new JTextField(15);
        txtBuscarPlaca.setFont(new Font("Arial", Font.PLAIN, 14));

        btnBuscar = new JButton("Buscar");
        btnBuscar.setFont(new Font("Arial", Font.BOLD, 12));
        btnBuscar.setBackground(new Color(70, 130, 180));
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setFocusPainted(false);

        panelBusqueda.add(lblBuscar);
        panelBusqueda.add(txtBuscarPlaca);
        panelBusqueda.add(btnBuscar);

        // Área de resultados
        txtResultadoBusqueda = new JTextArea(15, 40);
        txtResultadoBusqueda.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txtResultadoBusqueda.setEditable(false);
        txtResultadoBusqueda.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JScrollPane scrollResultados = new JScrollPane(txtResultadoBusqueda);
        scrollResultados.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
                "Resultados de Búsqueda",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 12),
                new Color(70, 130, 180)
        ));

        // Agregar componentes
        panel.add(panelBusqueda, BorderLayout.NORTH);
        panel.add(scrollResultados, BorderLayout.CENTER);

        // Configurar evento
        btnBuscar.addActionListener(e -> buscarVehiculo());
        txtBuscarPlaca.addActionListener(e -> buscarVehiculo());

        return panel;
    }

    private JPanel crearPanelInformacion() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotones.setBackground(Color.WHITE);

        JButton btnListarVehiculos = new JButton("Listar Vehículos");
        JButton btnEstadisticas = new JButton("Ver Estadísticas");
        JButton btnLimpiar = new JButton("Limpiar");

        // Estilizar botones
        for (JButton btn : new JButton[]{btnListarVehiculos, btnEstadisticas, btnLimpiar}) {
            btn.setFont(new Font("Arial", Font.BOLD, 12));
            btn.setBackground(new Color(70, 130, 180));
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        }

        panelBotones.add(btnListarVehiculos);
        panelBotones.add(btnEstadisticas);
        panelBotones.add(btnLimpiar);

        // Área de información
        txtAreaInfo = new JTextArea(20, 50);
        txtAreaInfo.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txtAreaInfo.setEditable(false);
        txtAreaInfo.setBackground(new Color(248, 248, 248));

        JScrollPane scrollInfo = new JScrollPane(txtAreaInfo);
        scrollInfo.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
                "Información del Sistema",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 12),
                new Color(70, 130, 180)
        ));

        // Agregar componentes
        panel.add(panelBotones, BorderLayout.NORTH);
        panel.add(scrollInfo, BorderLayout.CENTER);

        // Configurar eventos
        btnListarVehiculos.addActionListener(e -> listarVehiculos());
        btnEstadisticas.addActionListener(e -> mostrarEstadisticas());
        btnLimpiar.addActionListener(e -> txtAreaInfo.setText(""));

        return panel;
    }

    // NUEVO: Panel para registrar vehículos
    private JPanel crearPanelRegistroVehiculo() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título
        JLabel titulo = new JLabel("REGISTRAR NUEVO VEHÍCULO");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setForeground(new Color(0, 102, 204));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titulo, gbc);

        // Separador
        JSeparator separator = new JSeparator();
        gbc.gridy = 1;
        panel.add(separator, gbc);

        // Tipo de vehículo
        JLabel lblTipo = new JLabel("Tipo de Vehículo:");
        lblTipo.setFont(new Font("Arial", Font.PLAIN, 14));

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panel.add(lblTipo, gbc);

        JComboBox<String> comboTipo = new JComboBox<>(new String[]{"AUTO", "MOTO"});
        comboTipo.setFont(new Font("Arial", Font.PLAIN, 14));

        gbc.gridx = 1;
        panel.add(comboTipo, gbc);

        // Placa
        JLabel lblPlaca = new JLabel("Placa:");
        lblPlaca.setFont(new Font("Arial", Font.PLAIN, 14));

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(lblPlaca, gbc);

        JTextField txtPlaca = new JTextField();
        txtPlaca.setFont(new Font("Arial", Font.PLAIN, 14));

        gbc.gridx = 1;
        panel.add(txtPlaca, gbc);

        // Marca
        JLabel lblMarca = new JLabel("Marca:");
        lblMarca.setFont(new Font("Arial", Font.PLAIN, 14));

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(lblMarca, gbc);

        JTextField txtMarca = new JTextField();
        txtMarca.setFont(new Font("Arial", Font.PLAIN, 14));

        gbc.gridx = 1;
        panel.add(txtMarca, gbc);

        // Modelo
        JLabel lblModelo = new JLabel("Modelo:");
        lblModelo.setFont(new Font("Arial", Font.PLAIN, 14));

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(lblModelo, gbc);

        JTextField txtModelo = new JTextField();
        txtModelo.setFont(new Font("Arial", Font.PLAIN, 14));

        gbc.gridx = 1;
        panel.add(txtModelo, gbc);

        // Precio por día
        JLabel lblPrecio = new JLabel("Precio por día:");
        lblPrecio.setFont(new Font("Arial", Font.PLAIN, 14));

        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(lblPrecio, gbc);

        JTextField txtPrecio = new JTextField();
        txtPrecio.setFont(new Font("Arial", Font.PLAIN, 14));

        gbc.gridx = 1;
        panel.add(txtPrecio, gbc);

        // Parámetro específico
        JLabel lblParametro = new JLabel("Puertas (Auto) / Cilindrada (Moto):");
        lblParametro.setFont(new Font("Arial", Font.PLAIN, 14));

        gbc.gridx = 0;
        gbc.gridy = 7;
        panel.add(lblParametro, gbc);

        JTextField txtParametro = new JTextField("4");
        txtParametro.setFont(new Font("Arial", Font.PLAIN, 14));

        gbc.gridx = 1;
        panel.add(txtParametro, gbc);

        // Actualizar etiqueta según tipo
        comboTipo.addActionListener(e -> {
            String tipo = (String) comboTipo.getSelectedItem();
            if ("AUTO".equals(tipo)) {
                lblParametro.setText("Puertas:");
                txtParametro.setText("4");
            } else {
                lblParametro.setText("Cilindrada (cc):");
                txtParametro.setText("150");
            }
        });

        // Botón Registrar
        JButton btnRegistrarVehiculo = new JButton("REGISTRAR VEHÍCULO");
        btnRegistrarVehiculo.setFont(new Font("Arial", Font.BOLD, 14));
        btnRegistrarVehiculo.setBackground(new Color(70, 130, 180));
        btnRegistrarVehiculo.setForeground(Color.WHITE);
        btnRegistrarVehiculo.setFocusPainted(false);
        btnRegistrarVehiculo.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(btnRegistrarVehiculo, gbc);

        // Configurar evento del botón
        btnRegistrarVehiculo.addActionListener(e -> registrarVehiculo(
                comboTipo, txtPlaca, txtMarca, txtModelo, txtPrecio, txtParametro));

        return panel;
    }

    //  Método para registrar vehículo
    private void registrarVehiculo(JComboBox<String> comboTipo, JTextField txtPlaca,
                                   JTextField txtMarca, JTextField txtModelo,
                                   JTextField txtPrecio, JTextField txtParametro) {
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

            // Crear vehículo usando factory
            Vehiculo vehiculo = VehiculoFactory.crearVehiculo(tipo, placa, marca, modelo, precio, parametro);

            // Usar controller para guardar
            boolean registrado = vehiculoController.registrarVehiculo(vehiculo);

            if (registrado) {
                JOptionPane.showMessageDialog(this,
                        "Vehículo registrado exitosamente\n\n" +
                                "Placa: " + placa + "\n" +
                                "Tipo: " + tipo + "\n" +
                                "Precio: $" + precio + "/día",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);

                // Limpiar campos
                txtPlaca.setText("");
                txtMarca.setText("");
                txtModelo.setText("");
                txtPrecio.setText("");
                txtParametro.setText("4");
                comboTipo.setSelectedIndex(0);

                // Actualizar combo de vehículos en la pestaña de alquiler
                cargarComboVehiculos();
                agregarMensajeInfo("Nuevo vehículo registrado: " + placa);
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

    private void cargarDatosIniciales() {
        agregarMensajeInfo("=== SISTEMA DEV RENTAL INICIADO ===");
        agregarMensajeInfo("Fecha: " + obtenerFechaActual());
        agregarMensajeInfo("");

        // Cargar vehículos en el combo
        cargarComboVehiculos();
    }

    private void cargarComboVehiculos() {
        try {
            comboVehiculos.removeAllItems();
            java.util.List<org.bson.Document> vehiculos = vehiculoController.listarTodosVehiculos();

            if (vehiculos != null && !vehiculos.isEmpty()) {
                for (org.bson.Document doc : vehiculos) {
                    String placa = doc.getString("placa");
                    String marca = doc.getString("marca");
                    String modelo = doc.getString("modelo");
                    double precio = doc.getDouble("precioPorDia");
                    String tipo = doc.getString("tipo");

                    String display = String.format("%s - %s %s ($%.2f/día) [%s]",
                            placa, marca, modelo, precio, tipo);
                    comboVehiculos.addItem(display);
                }
                agregarMensajeInfo("Vehículos cargados: " + vehiculos.size());
            } else {
                comboVehiculos.addItem("-- No hay vehículos disponibles --");
                agregarMensajeInfo("No hay vehículos registrados en el sistema");
            }
        } catch (Exception e) {
            agregarMensajeError("Error al cargar vehículos: " + e.getMessage());
        }
    }

    private void calcularTotal() {
        try {
            String seleccion = (String) comboVehiculos.getSelectedItem();

            if (seleccion == null || seleccion.trim().isEmpty() ||
                    seleccion.contains("No hay") || seleccion.startsWith("--")) {
                lblTotalCalculado.setText("Total: $0.00");
                return;
            }

            String diasTexto = txtDias.getText().trim();
            if (diasTexto.isEmpty()) {
                lblTotalCalculado.setText("Ingrese días");
                return;
            }

            if (!diasTexto.matches("\\d+")) {
                lblTotalCalculado.setText("Días deben ser número entero");
                return;
            }

            int dias = Integer.parseInt(diasTexto);

            // Validar rango de días
            if (dias <= 0) {
                lblTotalCalculado.setText("Días deben ser > 0");
                return;
            }

            if (dias > 365) {
                lblTotalCalculado.setText("Máximo 365 días");
                return;
            }

            double precioPorDia = 0.0;

            // Método 1: Usando split por "$"
            String[] partesPorDolar = seleccion.split("\\$");
            if (partesPorDolar.length > 1) {
                // Tomar la parte después del "$"
                String parteConPrecio = partesPorDolar[1];
                // Extraer solo los números y punto decimal
                StringBuilder precioBuilder = new StringBuilder();
                for (char c : parteConPrecio.toCharArray()) {
                    if (Character.isDigit(c) || c == '.') {
                        precioBuilder.append(c);
                    } else {
                        // Detener al encontrar un carácter no numérico
                        break;
                    }
                }
                String precioStr = precioBuilder.toString();
                if (!precioStr.isEmpty()) {
                    precioPorDia = Double.parseDouble(precioStr);
                }
            }

            // Método alternativo si el anterior falla
            if (precioPorDia == 0.0) {
                // Buscar directamente con indexOf
                int inicio = seleccion.indexOf("$");
                if (inicio != -1) {
                    String desdeDolar = seleccion.substring(inicio + 1);
                    int fin = desdeDolar.indexOf("/");
                    if (fin == -1) {
                        fin = desdeDolar.indexOf(" ");
                    }
                    if (fin == -1) {
                        fin = desdeDolar.length();
                    }
                    String precioStr = desdeDolar.substring(0, fin).trim();
                    try {
                        precioPorDia = Double.parseDouble(precioStr);
                    } catch (NumberFormatException e) {
                        lblTotalCalculado.setText("Error en formato de precio");
                        return;
                    }
                }
            }

            // Si aún no se pudo extraer el precio
            if (precioPorDia == 0.0) {
                lblTotalCalculado.setText("No se pudo obtener precio");
                return;
            }

            // Calcular total
            double total = precioPorDia * dias;
            lblTotalCalculado.setText(String.format("Total: $%.2f", total));

        } catch (NumberFormatException e) {
            lblTotalCalculado.setText("Error en formato numérico");
        } catch (Exception e) {
            lblTotalCalculado.setText("Error en cálculo: " + e.getMessage());
            // Para debugging
            System.err.println("Error en calcularTotal: " + e.getClass().getName() + ": " + e.getMessage());
        }
    }

    private void registrarAlquiler() {
        try {
            String seleccion = (String) comboVehiculos.getSelectedItem();
            if (seleccion == null || seleccion.startsWith("--")) {
                JOptionPane.showMessageDialog(this,
                        "Seleccione un vehículo válido",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String diasTexto = txtDias.getText().trim();
            if (diasTexto.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Ingrese la cantidad de días",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int dias = Integer.parseInt(diasTexto);
            if (dias <= 0 || dias > 365) {
                JOptionPane.showMessageDialog(this,
                        "Los días deben estar entre 1 y 365",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String placa = seleccion.split(" - ")[0];

            org.bson.Document vehiculoDoc = vehiculoController.buscarVehiculo(placa);
            if (vehiculoDoc == null) {
                JOptionPane.showMessageDialog(this,
                        "Vehículo no encontrado en la base de datos",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Vehiculo vehiculo = crearVehiculoDesdeDocument(vehiculoDoc);

            String idAlquiler = "ALQ-" + System.currentTimeMillis();
            Alquiler alquiler = alquilerController.crearYRegistrarAlquiler(vehiculo, dias, idAlquiler);

            if (alquiler != null) {
                String mensaje = String.format(
                        "ALQUILER REGISTRADO EXITOSAMENTE\n\n" +
                                "ID Alquiler: %s\n" +
                                "Vehículo: %s %s (%s)\n" +
                                "Días: %d\n" +
                                "Costo Total: $%.2f\n\n" +
                                "El alquiler ha sido guardado en la base de datos.",
                        alquiler.getIdAlquiler(),
                        vehiculo.getMarca(),
                        vehiculo.getModelo(),
                        vehiculo.getPlaca(),
                        dias,
                        alquiler.getCostoTotal()
                );

                JOptionPane.showMessageDialog(this, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);

                txtDias.setText("");
                lblTotalCalculado.setText("Total: $0.00");
                agregarMensajeInfo("Nuevo alquiler registrado: " + idAlquiler);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Los días deben ser un número válido",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarVehiculo() {
        try {
            String placa = txtBuscarPlaca.getText().trim();
            if (placa.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Ingrese una placa para buscar",
                        "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            org.bson.Document vehiculoDoc = vehiculoController.buscarVehiculo(placa);
            txtResultadoBusqueda.setText("");

            if (vehiculoDoc != null) {
                String info = vehiculoController.formatearVehiculoParaMostrar(vehiculoDoc);
                txtResultadoBusqueda.append("=== INFORMACIÓN DEL VEHÍCULO ===\n\n");
                txtResultadoBusqueda.append(info);

                agregarMensajeInfo("Búsqueda realizada: " + placa);
            } else {
                txtResultadoBusqueda.append("VEHÍCULO NO ENCONTRADO\n\n");
                txtResultadoBusqueda.append("No existe ningún vehículo con la placa: " + placa);
            }

        } catch (Exception e) {
            txtResultadoBusqueda.setText("Error en la búsqueda: " + e.getMessage());
        }
    }

    private void listarVehiculos() {
        try {
            java.util.List<org.bson.Document> vehiculos = vehiculoController.listarTodosVehiculos();
            txtAreaInfo.append("\n=== LISTA DE VEHÍCULOS ===\n");
            txtAreaInfo.append("Fecha: " + obtenerFechaActual() + "\n\n");

            if (vehiculos != null && !vehiculos.isEmpty()) {
                int contador = 1;
                for (org.bson.Document doc : vehiculos) {
                    txtAreaInfo.append(String.format("%d. %s | %s %s | $%.2f/día | %s\n",
                            contador++,
                            doc.getString("placa"),
                            doc.getString("marca"),
                            doc.getString("modelo"),
                            doc.getDouble("precioPorDia"),
                            doc.getString("tipo")
                    ));
                }
                txtAreaInfo.append("\nTotal: " + vehiculos.size() + " vehículos\n");
            } else {
                txtAreaInfo.append("No hay vehículos registrados en el sistema.\n");
            }
            txtAreaInfo.append("==================================================\n");

        } catch (Exception e) {
            txtAreaInfo.append("Error al listar vehículos: " + e.getMessage() + "\n");
        }
    }

    private void mostrarEstadisticas() {
        try {
            String statsAlquileres = alquilerController.obtenerEstadisticasAlquileres();
            String statsVehiculos = vehiculoController.obtenerEstadisticas();

            txtAreaInfo.append("\n=== ESTADÍSTICAS DEL SISTEMA ===\n");
            txtAreaInfo.append("Fecha: " + obtenerFechaActual() + "\n\n");
            txtAreaInfo.append("ALQUILERES:\n");
            txtAreaInfo.append(statsAlquileres + "\n\n");
            txtAreaInfo.append("VEHÍCULOS:\n");
            txtAreaInfo.append(statsVehiculos + "\n");
            txtAreaInfo.append("==================================================\n");

        } catch (Exception e) {
            txtAreaInfo.append("Error al obtener estadísticas: " + e.getMessage() + "\n");
        }
    }

    private Vehiculo crearVehiculoDesdeDocument(org.bson.Document doc) {
        String tipo = doc.getString("tipo");
        String placa = doc.getString("placa");
        String marca = doc.getString("marca");
        String modelo = doc.getString("modelo");
        double precio = doc.getDouble("precioPorDia");

        if ("Automovil".equals(tipo)) {
            int puertas = doc.getInteger("numPuertas", 4);
            return new Automovil(placa, marca, modelo, precio, puertas);
        } else if ("Motocicleta".equals(tipo)) {
            int cilindrada = doc.getInteger("cilindrada", 150);
            return new Motocicleta(placa, marca, modelo, precio, cilindrada);
        }

        return new Automovil(placa, marca, modelo, precio, 4);
    }

    private String obtenerFechaActual() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }

    private void agregarMensajeInfo(String mensaje) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        txtAreaInfo.append("[" + timestamp + "] " + mensaje + "\n");
        txtAreaInfo.setCaretPosition(txtAreaInfo.getDocument().getLength());
    }

    private void agregarMensajeError(String mensaje) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        txtAreaInfo.append("[" + timestamp + "] ERROR: " + mensaje + "\n");
        txtAreaInfo.setCaretPosition(txtAreaInfo.getDocument().getLength());
    }
}
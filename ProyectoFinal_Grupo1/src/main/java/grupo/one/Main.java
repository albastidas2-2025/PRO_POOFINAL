package grupo.one;

import grupo.one.controller.VehiculoController;
import grupo.one.controller.AlquilerController;
import grupo.one.service.VehiculoService;
import grupo.one.service.AlquilerService;
import grupo.one.repository.VehiculoDAO;
import grupo.one.repository.AlquilerDAO;
import grupo.one.db.ConexionMongoDB;
import grupo.one.view.VistaPrincipal;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            SwingUtilities.invokeLater(() -> {
                try {
                    System.out.println("Iniciando DevRental...");

                    ConexionMongoDB db = ConexionMongoDB.getInstance();

                    VehiculoDAO vehiculoDAO = new VehiculoDAO(db.getDatabase());
                    AlquilerDAO alquilerDAO = new AlquilerDAO(db.getDatabase());

                    VehiculoService vehiculoService = new VehiculoService(vehiculoDAO);
                    AlquilerService alquilerService = new AlquilerService(alquilerDAO);

                    VehiculoController vehiculoController = new VehiculoController(vehiculoService);
                    AlquilerController alquilerController = new AlquilerController(alquilerService);

                    VistaPrincipal vista = new VistaPrincipal(vehiculoController, alquilerController);
                    vista.setVisible(true);

                    System.out.println("Aplicación iniciada");

                } catch (Exception e) {
                    System.err.println("Error: " + e.getMessage());
                    e.printStackTrace();

                    JOptionPane.showMessageDialog(null,
                            "Error: " + e.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            });
    }
}
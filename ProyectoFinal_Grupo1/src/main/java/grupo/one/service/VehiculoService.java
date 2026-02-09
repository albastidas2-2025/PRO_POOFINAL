package grupo.one.service;

import grupo.one.repository.VehiculoDAO;
import grupo.one.model.Vehiculo;
import grupo.one.model.Automovil;
import grupo.one.model.Motocicleta;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;

public class VehiculoService {
    private final VehiculoDAO vehiculoDAO;

    public VehiculoService(VehiculoDAO vehiculoDAO) {
        this.vehiculoDAO = vehiculoDAO;
    }

    public boolean registrarVehiculo(Vehiculo vehiculo) {
        try {
            if (vehiculo == null) {
                throw new IllegalArgumentException("El vehículo no puede ser nulo");
            }

            if (vehiculo.getPlaca() == null || vehiculo.getPlaca().trim().isEmpty()) {
                throw new IllegalArgumentException("La placa no puede estar vacía");
            }

            if (vehiculo.getMarca() == null || vehiculo.getMarca().trim().isEmpty()) {
                throw new IllegalArgumentException("La marca no puede estar vacía");
            }

            if (vehiculo.getModelo() == null || vehiculo.getModelo().trim().isEmpty()) {
                throw new IllegalArgumentException("El modelo no puede estar vacía");
            }

            if (vehiculo.getPrecioDia() <= 0) {
                throw new IllegalArgumentException("El precio por día debe ser mayor a 0");
            }

            if (vehiculoDAO.existe(vehiculo.getPlaca())) {
                throw new IllegalArgumentException("Ya existe un vehículo con la placa: " + vehiculo.getPlaca());
            }

            vehiculoDAO.guardar(vehiculo);

            System.out.println("Vehículo registrado: " + vehiculo.getPlaca());
            return true;

        } catch (Exception e) {
            System.err.println("Error en VehiculoService.registrarVehiculo: " + e.getMessage());
            throw new RuntimeException("Error al registrar vehículo: " + e.getMessage(), e);
        }
    }

    public Document buscarVehiculoPorPlaca(String placa) {
        try {
            if (placa == null || placa.trim().isEmpty()) {
                throw new IllegalArgumentException("La placa no puede ser nula o vacía");
            }

            Vehiculo vehiculo = vehiculoDAO.buscarPorPlaca(placa);

            if (vehiculo == null) {
                System.out.println("Vehículo no encontrado: " + placa);
                return null;
            }

            return convertirVehiculoADocument(vehiculo);

        } catch (Exception e) {
            throw new RuntimeException("Error al buscar vehículo: " + e.getMessage(), e);
        }
    }

    public List<Document> listarTodosVehiculos() {
        try {
            List<Vehiculo> vehiculos = vehiculoDAO.listarTodos();
            List<Document> documentos = new ArrayList<>();

            for (Vehiculo vehiculo : vehiculos) {
                documentos.add(convertirVehiculoADocument(vehiculo));
            }

            return documentos;
        } catch (Exception e) {
            throw new RuntimeException("Error al listar vehículos: " + e.getMessage(), e);
        }
    }

    public boolean actualizarVehiculo(Vehiculo vehiculo) {
        try {
            if (vehiculo == null) {
                throw new IllegalArgumentException("El vehículo no puede ser nulo");
            }

            if (vehiculo.getPlaca() == null || vehiculo.getPlaca().trim().isEmpty()) {
                throw new IllegalArgumentException("La placa no puede estar vacía");
            }

            if (!vehiculoDAO.existe(vehiculo.getPlaca())) {
                throw new IllegalArgumentException("No existe un vehículo con la placa: " + vehiculo.getPlaca());
            }

            return vehiculoDAO.actualizar(vehiculo);

        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar vehículo: " + e.getMessage(), e);
        }
    }

    public boolean eliminarVehiculo(String placa) {
        try {
            if (placa == null || placa.trim().isEmpty()) {
                throw new IllegalArgumentException("La placa no puede ser nula o vacía");
            }

            return vehiculoDAO.eliminar(placa);

        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar vehículo: " + e.getMessage(), e);
        }
    }

    public long contarVehiculos() {
        try {
            return vehiculoDAO.listarTodos().size();
        } catch (Exception e) {
            throw new RuntimeException("Error al contar vehículos: " + e.getMessage(), e);
        }
    }

    public boolean existeVehiculo(String placa) {
        try {
            if (placa == null || placa.trim().isEmpty()) {
                throw new IllegalArgumentException("La placa no puede ser nula o vacía");
            }
            return vehiculoDAO.existe(placa);

        } catch (Exception e) {
            throw new RuntimeException("Error al verificar vehículo: " + e.getMessage(), e);
        }
    }

    public List<Document> obtenerVehiculosDisponibles() {
        try {
            List<Vehiculo> vehiculos = vehiculoDAO.listarTodos();
            List<Document> documentos = new ArrayList<>();

            for (Vehiculo vehiculo : vehiculos) {
                documentos.add(convertirVehiculoADocument(vehiculo));
            }

            return documentos;
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener vehículos disponibles: " + e.getMessage(), e);
        }
    }

    public String obtenerEstadisticasVehiculos() {
        try {
            long total = contarVehiculos();
            return String.format("Total vehículos: %d", total);
        } catch (Exception e) {
            return "Error al obtener estadísticas: " + e.getMessage();
        }
    }

    private Document convertirVehiculoADocument(Vehiculo vehiculo) {
        Document doc = new Document()
                .append("placa", vehiculo.getPlaca())
                .append("marca", vehiculo.getMarca())
                .append("modelo", vehiculo.getModelo())
                .append("precioPorDia", vehiculo.getPrecioDia())
                .append("tipo", vehiculo.getClass().getSimpleName());

        if (vehiculo instanceof Automovil auto) {
            doc.append("numPuertas", auto.getNumPuertas());
        } else if (vehiculo instanceof Motocicleta moto) {
            doc.append("cilindrada", moto.getCilindrada());
        }

        return doc;
    }
}
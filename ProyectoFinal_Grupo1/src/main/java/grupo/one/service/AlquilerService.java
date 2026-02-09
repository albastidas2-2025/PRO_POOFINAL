package grupo.one.service;

import grupo.one.repository.AlquilerDAO;
import grupo.one.model.Alquiler;
import grupo.one.model.Vehiculo;
import org.bson.Document;

import java.util.List;
import java.util.UUID;

public class AlquilerService {
    private final AlquilerDAO alquilerDAO;

    public AlquilerService(AlquilerDAO alquilerDAO) {
        this.alquilerDAO = alquilerDAO;
    }

    public boolean registrarAlquiler(Alquiler alquiler) {
        try {
            if (alquiler == null) {
                throw new IllegalArgumentException("El alquiler no puede ser nulo");
            }
            if (alquiler.getVehiculo() == null) {
                throw new IllegalArgumentException("El alquiler debe tener un vehículo");
            }
            if (alquiler.getDias() <= 0) {
                throw new IllegalArgumentException("Los días deben ser mayores a 0");
            }
            if (alquiler.getIdAlquiler() == null || alquiler.getIdAlquiler().isEmpty()) {
            }
            alquilerDAO.crear(alquiler);
            System.out.println("Alquiler registrado: " + alquiler.getIdAlquiler());
            return true;
        } catch (Exception e) {
            System.err.println("Error en AlquilerService.registrarAlquiler: " + e.getMessage());
            throw new RuntimeException("Error al registrar alquiler: " + e.getMessage(), e);
        }
    }

    public Alquiler crearYRegistrarAlquiler(Vehiculo vehiculo, int dias) {
        try {
            if (vehiculo == null) {
                throw new IllegalArgumentException("El vehículo no puede ser nulo");
            }

            if (dias <= 0) {
                throw new IllegalArgumentException("Los días deben ser mayores a 0");
            }

            if (dias > 365) {
                throw new IllegalArgumentException("No se puede alquilar por más de 1 año");
            }
            String id = UUID.randomUUID().toString();

            Alquiler alquiler = new Alquiler(id, vehiculo, dias);

            aplicarDescuentos(alquiler, dias);

            alquilerDAO.crear(alquiler);

            return alquiler;

        } catch (Exception e) {
            throw new RuntimeException("Error al crear alquiler: " + e.getMessage(), e);
        }
    }
    private void aplicarDescuentos(Alquiler alquiler, int dias) {
        //PARA APLICAR DESCUENTOS
        if (dias >= 30) {
            System.out.println("Aplicando 15% de descuento por alquiler de 30+ días");
        } else if (dias >= 7) {
            System.out.println("Aplicando 10% de descuento por alquiler de 7+ días");
        }
    }

    public Document buscarAlquilerPorId(String id) {
        try {
            if (id == null || id.trim().isEmpty()) {
                throw new IllegalArgumentException("El ID no puede ser nulo o vacío");
            }

            Document alquiler = alquilerDAO.buscarPorId(id);

            if (alquiler == null) {
                System.out.println("Alquiler no encontrado: " + id);
            }

            return alquiler;

        } catch (Exception e) {
            throw new RuntimeException("Error al buscar alquiler: " + e.getMessage(), e);
        }
    }

    public List<Document> listarTodosAlquileres() {
        try {
            return alquilerDAO.listarTodos();
        } catch (Exception e) {
            throw new RuntimeException("Error al listar alquileres: " + e.getMessage(), e);
        }
    }


    public List<Document> buscarAlquileresPorVehiculo(String placa) {
        try {
            if (placa == null || placa.trim().isEmpty()) {
                throw new IllegalArgumentException("La placa no puede ser nula o vacía");
            }

            return alquilerDAO.buscarPorVehiculo(placa);

        } catch (Exception e) {
            throw new RuntimeException("Error al buscar alquileres por vehículo: " + e.getMessage(), e);
        }
    }

    public boolean actualizarAlquiler(Alquiler alquiler) {
        try {
            if (alquiler == null) {
                throw new IllegalArgumentException("El alquiler no puede ser nulo");
            }

            return alquilerDAO.actualizar(alquiler);

        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar alquiler: " + e.getMessage(), e);
        }
    }

    public boolean eliminarAlquiler(String id) {
        try {
            if (id == null || id.trim().isEmpty()) {
                throw new IllegalArgumentException("El ID no puede ser nulo o vacío");
            }

            return alquilerDAO.eliminar(id);

        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar alquiler: " + e.getMessage(), e);
        }
    }

    public long contarAlquileres() {
        try {
            return alquilerDAO.contar();
        } catch (Exception e) {
            throw new RuntimeException("Error al contar alquileres: " + e.getMessage(), e);
        }
    }

    public boolean existeAlquiler(String id) {
        try {
            if (id == null || id.trim().isEmpty()) {
                throw new IllegalArgumentException("El ID no puede ser nulo o vacío");
            }

            return alquilerDAO.existe(id);

        } catch (Exception e) {
            throw new RuntimeException("Error al verificar alquiler: " + e.getMessage(), e);
        }
    }

    public double calcularIngresosTotales() {
        try {
            return alquilerDAO.calcularIngresosTotales();
        } catch (Exception e) {
            throw new RuntimeException("Error al calcular ingresos: " + e.getMessage(), e);
        }
    }

    public String obtenerEstadisticas() {
        try {
            long total = contarAlquileres();
            double ingresos = calcularIngresosTotales();

            return String.format("Total alquileres: %d\nIngresos totales: $%.2f", total, ingresos);

        } catch (Exception e) {
            return "Error al obtener estadísticas: " + e.getMessage();
        }
    }
}

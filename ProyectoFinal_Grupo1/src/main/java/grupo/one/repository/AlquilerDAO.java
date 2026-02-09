package grupo.one.repository;

import grupo.one.model.Alquiler;
import grupo.one.model.Vehiculo;
import grupo.one.model.Automovil;
import grupo.one.model.Motocicleta;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class AlquilerDAO {
    private final MongoCollection<Document> collection;

    public AlquilerDAO(MongoDatabase database) {
        this.collection = database.getCollection("alquileres");
    }

    public void crear(Alquiler alquiler) {
        Document doc = convertirAAlquilerDocument(alquiler);
        collection.insertOne(doc);
    }
    //LEER POR ID
    public Document buscarPorId(String id) {
        return collection.find(eq("idAlquiler", id)).first();
    }

    // LEER - Todos
    public List<Document> listarTodos() {
        List<Document> resultados = new ArrayList<>();
        collection.find().into(resultados);
        return resultados;
    }

    // LEER POR VEHICULO
    public List<Document> buscarPorVehiculo(String placa) {
        List<Document> resultados = new ArrayList<>();
        collection.find(eq("vehiculo.placa", placa)).into(resultados);
        return resultados;
    }

    // ACTUALIZAR
    public boolean actualizar(Alquiler alquiler) {
        Document filtro = (Document) eq("idAlquiler", alquiler.getIdAlquiler());
        Document actualizacion = new Document("$set", convertirAAlquilerDocument(alquiler));
        return collection.updateOne(filtro, actualizacion).getModifiedCount() > 0;
    }

    public boolean eliminar(String id) {
        return collection.deleteOne(eq("idAlquiler", id)).getDeletedCount() > 0;
    }

    public long contar() {
        return collection.countDocuments();
    }

    public boolean existe(String id) {
        return collection.countDocuments(eq("idAlquiler", id)) > 0;
    }

    public double calcularIngresosTotales() {
        double total = 0;
        for (Document doc : collection.find()) {
            Double costo = doc.getDouble("costoTotal");
            if (costo != null) {
                total += costo;
            }
        }
        return total;
    }

    private Document convertirAAlquilerDocument(Alquiler alquiler) {
        Document doc = new Document()
                .append("idAlquiler", alquiler.getIdAlquiler())
                .append("dias", alquiler.getDias())
                .append("costoTotal", alquiler.getCostoTotal())
                .append("estado", alquiler.getEstado());

        Vehiculo vehiculo = alquiler.getVehiculo();
        Document docVehiculo = new Document()
                .append("placa", vehiculo.getPlaca())
                .append("marca", vehiculo.getMarca())
                .append("modelo", vehiculo.getModelo())
                .append("precioPorDia", vehiculo.getPrecioDia())
                .append("tipo", vehiculo.getClass().getSimpleName());

        doc.append("vehiculo", docVehiculo);
        return doc;
    }
}
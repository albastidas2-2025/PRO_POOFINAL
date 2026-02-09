package grupo.one.repository;

import grupo.one.model.Vehiculo;
import grupo.one.model.Automovil;
import grupo.one.model.Motocicleta;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;


public class VehiculoDAO {
    private final MongoCollection<Document> vehiculosCollection;
    public VehiculoDAO(MongoDatabase database) {
        this.vehiculosCollection = database.getCollection("vehiculos");
    }

    public void guardar(Vehiculo vehiculo) {
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
        vehiculosCollection.insertOne(doc);
    }

    public Vehiculo buscarPorPlaca(String placa) {
        Document doc = vehiculosCollection.find(eq("placa", placa)).first();
        if (doc == null) return null;
        return convertirDocumentoAVehiculo(doc);
    }

    public List<Vehiculo> listarTodos() {
        List<Vehiculo> vehiculos = new ArrayList<>();
        for (Document doc : vehiculosCollection.find()) {
            vehiculos.add(convertirDocumentoAVehiculo(doc));
        }
        return vehiculos;
    }

    public boolean actualizar(Vehiculo vehiculo) {
        Document filtro = new Document("placa", vehiculo.getPlaca());
        Document actualizacion = new Document("$set",
                new Document("precioPorDia", vehiculo.getPrecioDia())
                        .append("marca", vehiculo.getMarca())
                        .append("modelo", vehiculo.getModelo()));
        return vehiculosCollection.updateOne(filtro, actualizacion).getModifiedCount() > 0;
    }

    public boolean eliminar(String placa) {
        return vehiculosCollection.deleteOne(eq("placa", placa)).getDeletedCount() > 0;
    }

    public boolean existe(String placa) {
        return vehiculosCollection.countDocuments(eq("placa", placa)) > 0;
    }

    private Vehiculo convertirDocumentoAVehiculo(Document doc) {
        String tipo = doc.getString("tipo");
        String placa = doc.getString("placa");
        String marca = doc.getString("marca");
        String modelo = doc.getString("modelo");
        double precioPorDia = doc.getDouble("precioPorDia");

        if ("Automovil".equals(tipo)) {
            int numPuertas = doc.getInteger("numPuertas", 4);
            return new Automovil(placa, marca, modelo, precioPorDia, numPuertas);
        } else if ("Motocicleta".equals(tipo)) {
            int cilindrada = doc.getInteger("cilindrada", 150);
            return new Motocicleta(placa, marca, modelo, precioPorDia, cilindrada);
        }

        throw new IllegalArgumentException("Tipo de vehículo desconocido: " + tipo);
    }

}

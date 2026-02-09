package grupo.one.db;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class ConexionMongoDB {
    private static volatile ConexionMongoDB instancia;

    private MongoClient mongoClient;
    private MongoDatabase database;

    private static final String CONNECTION_STRING =
            "mongodb+srv://albastidas2_db_user:alexiS_2006@cluster0.qletf6c.mongodb.net/?appName=Cluster0";
    private static final String DATABASE_NAME = "devrental";

    private ConexionMongoDB() {
        conectar();
    }

    public static ConexionMongoDB getInstance() {
        if (instancia == null) {
            synchronized (ConexionMongoDB.class) {
                if (instancia == null) {
                    instancia = new ConexionMongoDB();
                }
            }
        }
        return instancia;
    }

    private void conectar() {
        try {
            System.out.println(" Conectando a MongoDB Atlas...");

            mongoClient = MongoClients.create(CONNECTION_STRING);

            database = mongoClient.getDatabase(DATABASE_NAME);

            database.listCollectionNames().first();

            System.out.println(" Conexión Singleton establecida a MongoDB Atlas");
            System.out.println(" Base de datos: " + DATABASE_NAME);

        } catch (Exception e) {
            System.err.println(" Error al conectar con MongoDB Atlas: " + e.getMessage());
            throw new RuntimeException("Error de conexión a la base de datos", e);
        }
    }

    public MongoDatabase getDatabase() {
        if (database == null) {
            throw new IllegalStateException("La conexión no está inicializada");
        }
        return database;
    }

    public boolean probarConexion() {
        try {
            database.listCollectionNames().first();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void cerrarConexion() {
        if (mongoClient != null) {
            mongoClient.close();
            System.out.println(" Conexión a MongoDB cerrada");
            instancia = null; // Permite recrear si es necesario
        }
    }

    public static void reiniciar() {
        synchronized (ConexionMongoDB.class) {
            if (instancia != null) {
                instancia.cerrarConexion();
            }
            instancia = null;
        }
    }
}

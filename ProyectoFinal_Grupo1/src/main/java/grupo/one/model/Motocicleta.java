package grupo.one.model;

public class Motocicleta extends Vehiculo {

    private int cilindrada;

    public Motocicleta() {
        super();
        this.cilindrada = 150;
    }

    public Motocicleta(String placa, String marca, String modelo,
                       double precioDia, int cilindrada) {

        super(placa, marca, modelo, precioDia);
        this.cilindrada = cilindrada > 0 ? cilindrada : 150;
    }

    public int getCilindrada() {
        return cilindrada;
    }

    public void setCilindrada(int cilindrada) {
        if (cilindrada > 0) {
            this.cilindrada = cilindrada;
        }
    }

    @Override
    public String getInfoDetallada() {
        return "Motocicleta | Placa: " + getPlaca() +
                " | Marca: " + getMarca() +
                " | Modelo: " + getModelo() +
                " | Cilindrada: " + cilindrada + "cc" +
                " | Precio/día: " + getPrecioDia();
    }

    @Override
    public String toString() {
        return getPlaca() + " - " + getMarca() + " " + getModelo();
    }
}

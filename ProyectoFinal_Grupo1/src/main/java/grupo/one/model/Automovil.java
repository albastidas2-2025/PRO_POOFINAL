package grupo.one.model;

public class Automovil extends Vehiculo {
    private int numPuertas;

    public Automovil() {
        super();
        this.numPuertas = 4;
    }

    public Automovil(String placa, String marca, String modelo,
                     double precioDia, int numPuertas) {

        super(placa, marca, modelo, precioDia);
        this.numPuertas = numPuertas > 0 ? numPuertas : 4;
    }

    public int getNumPuertas() {
        return numPuertas;
    }

    public void setNumPuertas(int numPuertas) {
        if (numPuertas > 0) {
            this.numPuertas = numPuertas;
        }
    }

    @Override
    public String getInfoDetallada() {
        return "Coche | Placa: " + getPlaca() +
                " | Marca: " + getMarca() +
                " | Modelo: " + getModelo() +
                " | Puertas: " + numPuertas +
                " | Precio/día: " + getPrecioDia();
    }

    @Override
    public String toString() {
        return getPlaca() + " - " + getMarca() + " " + getModelo();
    }
}

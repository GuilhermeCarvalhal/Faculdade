package modelos;

public class Circulo extends Ponto {
    protected double raio;

    public Circulo(double raio, float x, float y, int numProtegido) {
        super(x, y, numProtegido);
        this.raio = raio;
    }

    public double getRaio() {
        return raio;
    }

    public void setRaio(double raio) {
        if (raio > 0)
            this.raio = raio;
        else
            this.raio = 0;
    }

    public double area() {
        return Math.PI * this.raio * this.raio;
    }

    @Override
    public String toString() {
        return super.toString() + "Raio = " + this.raio;
    }

}

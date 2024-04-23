package modelos;

public class Ponto {
    private float x;
    private float y;
    protected int numProtegido;

    public Ponto(float x, float y, int numProtegido) {
        this.x = x;
        this.y = y;
        this.numProtegido = numProtegido;
    }

    public void setPonto(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public int getNumProtegido() {
        return this.numProtegido;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void move(float dx, float dy) {
        y += dy;
        x += dx;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + " : x = " + this.x + "y = " + this.y;
    }

}

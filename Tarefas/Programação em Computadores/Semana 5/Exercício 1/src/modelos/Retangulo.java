package modelos;

public class Retangulo {

    // Atributos
    private double Width;
    private double Height;

    public Retangulo() {
    }

    public double getWidth() {
        return Width;
    }

    public void setWidth(double width) {
        this.Width = width;
    }

    public double getHeight() {
        return Height;
    }

    public void setHeight(double height) {
        this.Height = height;
    }

    public double Area() {
        return this.Height * this.Width;
    }

    public double Perimeter() {
        return (this.Height + this.Width) * 2;
    }

    public double Diagonal() {
        return Math.sqrt((this.Height * this.Height) + (this.Width * this.Width));
    }
}

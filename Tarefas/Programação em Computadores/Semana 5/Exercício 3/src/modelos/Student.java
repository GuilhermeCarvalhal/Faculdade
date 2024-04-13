package modelos;

public class Student {

    // Atributos
    private String name;
    private float t1;
    private float t2;
    private float t3;

    public Student() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getT1() {
        return t1;
    }

    public void setT1(float t1) {
        this.t1 = t1;
    }

    public float getT2() {
        return t2;
    }

    public void setT2(float t2) {
        this.t2 = t2;
    }

    public float getT3() {
        return t3;
    }

    public void setT3(float t3) {
        this.t3 = t3;
    }

    public float getFinalGrade() {
        return (this.t1 + this.t2 + this.t3);
    }

    public boolean boolApproval(float f) {
        if (f >= 60) {
            return true;
        } else {
            return false;
        }
    }
}

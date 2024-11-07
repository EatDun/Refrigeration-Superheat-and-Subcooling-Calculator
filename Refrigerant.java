import java.util.Scanner;
import java.lang.Math;
import java.util.function.Function;

class Refrigerant {
    String name;
    int id;
    double maxPress;
    double maxTemp;
    double satRLo;
    double satRHi;
    Function<Double, Double> ptEquation;

    public Refrigerant(String refName, int refId, double refMaxPress, double refMaxTemp, double refSatRLo, double refSatRHi, Function<Double, Double> ptEquation) {
        this.name = refName;
        this.id = refId;
        this.maxPress = refMaxPress;
        this.maxTemp = refMaxTemp;
        this.satRLo = refSatRLo;
        this.satRHi = refSatRHi;
        this.ptEquation = ptEquation;
    }

    // Apply PT equation to refrigerant
    public double calculatePTEquation(double temp) {
        return ptEquation.apply(temp);
    }

    @Override
    public String toString() {
        return "Refrigerant{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", maxPress=" + maxPress +
                ", maxTemp=" + maxTemp +
                ", satRLo=" + satRLo +
                ", satRHi=" + satRHi +
                '}';
    }
}

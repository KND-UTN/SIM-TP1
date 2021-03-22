package metodos;
import java.util.ArrayList;

public class CongruencialMultiplicativo implements IMetodoGeneracion
{
    final int m;
    final int a;
    final int xo;
    final int cant;
    double xi;
    double random;

    ArrayList<Double> numeros;

    public CongruencialMultiplicativo(int m, int a, int xo, int cant) {
        numeros = new ArrayList<>();
        this.m = m;
        this.a = a;
        this.xo = xo;
        this.xi = xo;
        this.cant = cant;
    }

    public ArrayList<Double> generarValores()
    {
        for (int i = 0; i < cant; i++)
        {
            double aXi = (this.a * xi);
            double xi1 = aXi%this.m;
            random = xi1 / (m-1);
            numeros.add(random);
            xi = xi1;
        }
        return numeros;
    }

    public double generarValorExtra()
    {
        double aXi = (this.a * xi);
        double xi1 = aXi%this.m;
        random = xi1 / (m-1);
        numeros.add(random);
        xi = xi1;
        return random;
    }

    public ArrayList<Double> getValores()
    {
        return numeros;
    }
}

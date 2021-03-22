package metodos;
import java.util.ArrayList;

public class CongruencialMixto implements IMetodoGeneracion
{
    final int m;
    final int a;
    final int xo;
    final int cant;
    final int c;
    double xi;
    double random;
    ArrayList<Double> numeros;

    public CongruencialMixto(int m, int a, int xo, int c, int cant)
    {
        numeros = new ArrayList<>();
        this.m = m;
        this.a = a;
        this.c = c;
        this.xo = xo;
        this.xi = xo;
        this.cant = cant;
    }

    public ArrayList<Double> generarValores()
    {
        for (int i = 0; i < cant; i++)
        {
            double aXic = ((this.a * xi)+c);
            double xi1 = aXic%this.m;
            random = xi1 / (m-1);
            numeros.add(random);
            xi = xi1;
        }
        return numeros;
    }

    public double generarValorExtra()
    {
        double aXic = ((this.a * xi)+c);
        double xi1 = aXic%this.m;
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
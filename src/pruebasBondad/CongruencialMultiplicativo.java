package pruebasBondad;

import java.util.ArrayList;

public class CongruencialMultiplicativo {
    final int m;
    final int a;
    final int xo;
    final int cant;
    int xi;
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

    public void generarValores()
    {
        for (int i = 0; i < cant; i++)
        {
            xi = (this.a * xi) % this.m;
            random = (double) xi / (this.m - 1);
            numeros.add(random);
        }
    }

    public Double generarValorExtra()
    {
        xi = (this.a * xi) % this.m;
        random = (double) xi / (this.m - 1);
        numeros.add(random);
        return random;
    }

    public ArrayList<Double> getNumeros()
    {
        return numeros;
    }
}

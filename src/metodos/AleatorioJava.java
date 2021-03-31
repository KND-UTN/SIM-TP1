package metodos;

import java.util.ArrayList;
import java.util.Random;

public class AleatorioJava implements IMetodoGeneracion
{

    final Random random = new Random();
    final int cantidad;
    ArrayList<Double> numeros = new ArrayList<Double>();


    public AleatorioJava(int cant){
        this.cantidad = cant;
    }

    @Override
    public ArrayList<Double> generarValores()
    {
        for (int i = 0; i < cantidad; i++)
        {
            numeros.add(random.nextDouble());

        }
        return numeros;

    }

    @Override
    public double generarValorExtra()
    {
        double num = random.nextDouble();
        numeros.add(num);
        return num;
    }

    @Override
    public ArrayList<Double> getValores()
    {
        return numeros;
    }
}

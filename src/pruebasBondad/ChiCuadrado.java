package pruebasBondad;

import org.apache.commons.math3.distribution.ChiSquaredDistribution;
import java.util.ArrayList;

public class ChiCuadrado {
    double salto;
    double[] intervalos;
    double[] fo;            // Frecuencias observadas
    double[] fe;            // Frecuencias esperadas
    double[] c;             // Estadistico
    double cac;             // Estadistico de prueba (C acumulado)
    double valorCritico;    // Si cac (C acumulado) es mayor al valor critico, es posible rechazar la hipotesis nula
    boolean rechazada;

    public ChiCuadrado(int cantIntervalos)
    {
        // Inicializamos las variables
        double anterior = 0;
        intervalos = new double[cantIntervalos];
        fo = new double[cantIntervalos];
        fe = new double[cantIntervalos];
        c = new double[cantIntervalos];
        salto = 1.0 / cantIntervalos;

        // Generamos los intervalos
        for (int i = 0 ; i < cantIntervalos ; i++)
        {
            anterior += salto;
            intervalos[i] = anterior;
        }
    }

    public void procesar(ArrayList<Double> numeros)
    {
        // Primero calculamos las frecuencias observadas
        calcularFO(numeros);
        // Calculamos las frecuencias esperadas
        calcularFE(numeros);
        // Obtenemos el estadistico acumulado
        calcularEstadisticoPrueba();
        // Verificamos si se rechaza la hipotesis nula
        testHipotesis();
    }

    // Calcular Frecuencias Observadas
    private void calcularFO(ArrayList<Double> numeros)
    {
        // Vamos metiendo cada numero en el intervalo que corresponde
        for (double numero : numeros) {
            for (int i = 0; i < this.intervalos.length; i++) {
                if (this.intervalos[i] > numero && numero > (this.intervalos[i] - this.salto))
                {
                    fo[i]++;
                    continue;
                }
            }
        }
    }

    // Calcular Frecuencias Esperadas
    private void calcularFE(ArrayList<Double> numeros)
    {
        for (int i = 0 ; i < this.fe.length ; i++)
        {
            this.fe[i] = (double) numeros.size() / this.intervalos.length;
        }
    }

    private void calcularEstadisticoPrueba()
    {
        for (int i = 0 ; i < this.fe.length ; i++)
        {
            this.c[i] = ((this.fe[i] - this.fo[i]) * (this.fe[i] - this.fo[i])) / this.fe[i];
            cac += c[i];
        }
    }

    // Este metodo verifica si se puede rechazar o no la hipotesis nula
    private void testHipotesis()
    {
        int gradosLibertad = intervalos.length - 1;
        // Con este alpha definimos que existe un riesgo de 5% de concluir que la muestra no se ajusta a la
        // distribución propuesta, cuando en realidad si lo hace.
        double alpha = 0.05;

        // Esta clase proviene de una libreria de Apache,
        // uno le pasa al constructor los grados de libertad como parametro
        ChiSquaredDistribution distribucionChi = new ChiSquaredDistribution( gradosLibertad );
        // Luego para obtener el valor critico, tenemos que llamar a esta funcion y pasarle como parametro el alpha
        valorCritico = distribucionChi.inverseCumulativeProbability(1 - alpha);

        // Guardamos en la variable si es posible rechazar o no la hipotesis
        rechazada = !(valorCritico > cac);
    }

    /**
     * -------------------------------------------------- GETTERS --------------------------------------------------
     */
    public double[] getFrecuenciasObservadas() {
        return fo;
    }

    public double[] getFrecuenciasEsperadas() {
        return fe;
    }

    public double[] getIntervalos() {
        return intervalos;
    }

    public double[] getEstadisticos() {
        return c;
    }

    public double getEstadisticoPrueba() {
        return cac;
    }

    public boolean isRechazada() {
        return rechazada;
    }

    public double getValorCritico() {
        return valorCritico;
    }
}

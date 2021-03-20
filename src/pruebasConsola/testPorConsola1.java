package pruebasConsola;

import metodos.ChiCuadrado;
import pruebasBondad.CongruencialMultiplicativo;

import java.util.Arrays;

public class testPorConsola1 {
    public static void main(String[] args) {
        int m = 32;
        int a = 19;
        int xo = 17;
        int cantValores = 20;       // Cantidad de valores a generar
        int cantIntervalos = 5;     // Cantidad de intervalos

        CongruencialMultiplicativo numerosAleatorios = new CongruencialMultiplicativo(m,a,xo,cantValores);
        numerosAleatorios.generarValores();

        ChiCuadrado chi = new ChiCuadrado(cantIntervalos);
        chi.procesar(numerosAleatorios.getNumeros());

        //System.out.println("Numeros generados: " + numerosAleatorios.getNumeros());
        System.out.println("Intervalos: " + Arrays.toString(chi.getIntervalos()));
        System.out.println("Frecuencias Esperadas: " + Arrays.toString(chi.getFrecuenciasEsperadas()));
        System.out.println("Frecuencias Observadas: " + Arrays.toString(chi.getFrecuenciasObservadas()));
        System.out.println("Estadisticos C (No acumulados): " + Arrays.toString(chi.getEstadisticos()));
        System.out.println("Estadistico de prueba C(AC): " + chi.getEstadisticoPrueba());
        System.out.println("Valor critico (Tabla): " + chi.getValorCritico());
        System.out.println("Es posible rechazar la hipotesis nula: " + chi.isRechazada());
    }
}

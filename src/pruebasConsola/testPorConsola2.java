package pruebasConsola;

import pruebasBondad.ChiCuadrado;

import java.util.ArrayList;
import java.util.Arrays;

public class testPorConsola2 {
    public static void main(String[] args) {
        ArrayList<Double> numerosAleatorios = new ArrayList<>();
        numerosAleatorios.add(0.15);
        numerosAleatorios.add(0.22);
        numerosAleatorios.add(0.41);
        numerosAleatorios.add(0.65);
        numerosAleatorios.add(0.84);
        numerosAleatorios.add(0.81);
        numerosAleatorios.add(0.62);
        numerosAleatorios.add(0.45);
        numerosAleatorios.add(0.32);
        numerosAleatorios.add(0.07);
        numerosAleatorios.add(0.11);
        numerosAleatorios.add(0.29);
        numerosAleatorios.add(0.58);
        numerosAleatorios.add(0.73);
        numerosAleatorios.add(0.93);
        numerosAleatorios.add(0.97);
        numerosAleatorios.add(0.79);
        numerosAleatorios.add(0.55);
        numerosAleatorios.add(0.35);
        numerosAleatorios.add(0.09);
        numerosAleatorios.add(0.99);
        numerosAleatorios.add(0.51);
        numerosAleatorios.add(0.35);
        numerosAleatorios.add(0.02);
        numerosAleatorios.add(0.19);
        numerosAleatorios.add(0.24);
        numerosAleatorios.add(0.98);
        numerosAleatorios.add(0.10);
        numerosAleatorios.add(0.31);
        numerosAleatorios.add(0.17);

        ChiCuadrado chi = new ChiCuadrado(5);
        chi.procesar(numerosAleatorios);

        System.out.println("Numeros generados: " + numerosAleatorios);
        System.out.println("Intervalos: " + Arrays.toString(chi.getIntervalos()));
        System.out.println("Frecuencias Esperadas: " + Arrays.toString(chi.getFrecuenciasEsperadas()));
        System.out.println("Frecuencias Observadas: " + Arrays.toString(chi.getFrecuenciasObservadas()));
        System.out.println("Estadisticos C (No acumulados): " + Arrays.toString(chi.getEstadisticos()));
        System.out.println("Estadistico de prueba C(AC): " + chi.getEstadisticoPrueba());
        System.out.println("Valor critico (Tabla): " + chi.getValorCritico());
        System.out.println("Es posible rechazar la hipotesis nula: " + chi.isRechazada());
    }
}

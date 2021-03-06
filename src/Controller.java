// package

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import metodos.AleatorioJava;
import metodos.CongruencialMixto;
import metodos.CongruencialMultiplicativo;
import metodos.IMetodoGeneracion;
import pruebasBondad.ChiCuadrado;
import tablas.NumChi;
import tablas.NumRandom;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;

public class Controller {

    /**
     * -------------------------------------------- Variables de Interfaz --------------------------------------------
     */
    @FXML private Label lblParam1;
    @FXML private Label lblParam2;
    @FXML private Label lblResultadoTest;
    @FXML private Spinner<Integer> spnSeed;
    @FXML private Spinner<Integer> spnC;
    @FXML private Spinner<Integer> spnMG;
    @FXML private Spinner<Integer> spnAK;
    @FXML private Spinner<Integer> spnCant;
    @FXML private Spinner<Integer> spnIntervalos;
    @FXML private Spinner<Double>  spnConfianza;
    @FXML private Button btnGenerar;
    @FXML private Button btnAdd;
    @FXML private Button btnSwitch;
    @FXML private Button btnChi;
    @FXML private Button btnHistograma;
    // Tabla de numeros aleatorios
    @FXML private TableView tblRandoms;
    // Columnas de la tabla de numeros aleatorios
    @FXML private TableColumn itNumero;
    @FXML private TableColumn itRandom;
    // Tabla de intervalos para Test de Chi Cuadrado
    @FXML private TableView tablaChi;
    // Columnas de la tabla de Test de Chi Cuadrado
    @FXML private TableColumn itIntervalo;
    @FXML private TableColumn itFo;
    @FXML private TableColumn itFe;
    @FXML private TableColumn itC;

    /**
     * ----------------------------------------- Variables del Controlador -----------------------------------------
     */
    boolean parametro;                              // Este booleano nos dice si se va a usar m y a, o k y g
    IMetodoGeneracion metodoGeneracion;             // Esta variable va a guardar la abstraccion del metodo
                                                    // de generacion de numeros aleatorios
    int metodoGeneracionNum;                        // 0: Mixto - 1: Multiplicativo - 2: Lenguaje
    int cantidadDecimales = 4;
    int cantidadRandoms = 0;
    ObservableList<String> listaRandoms = FXCollections.observableArrayList();  // Lista de valores para la tabla de numeros aleatorios
    ObservableList<String> listaDatos = FXCollections.observableArrayList(); // Lista de valores para la tabla de Test de Chi Cuadrado
    ChiCuadrado chi;

    /**
     * -------------------------------------------- M??todos --------------------------------------------
     */
    // El initialize es lo que se efect??a cuando se abre la pantalla por primera vez
    public void initialize() {
        parametro = true;                          // true: m y a    -    false: k y g
        // Definimos que los spinners solo puedan tener valores enteros y sus limites
        spnSeed.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE));
        spnC.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE));
        spnMG.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE));
        spnAK.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE));
        spnCant.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE));
        spnIntervalos.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE));
        spnConfianza.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 1, 0.95, 0.01));

        // Definimos los parametros que van a tener las columnas de la primera tabla
        tblRandoms.setItems(listaRandoms);
        itNumero.setCellValueFactory(new PropertyValueFactory<>("i"));
        itRandom.setCellValueFactory(new PropertyValueFactory<>("num"));
        // Definimos los parametros que van a tener las columnas de la segunda tabla
        tablaChi.setItems(listaDatos);
        itIntervalo.setCellValueFactory(new PropertyValueFactory<>("inter"));
        itFo.setCellValueFactory(new PropertyValueFactory<>("fo"));
        itFe.setCellValueFactory(new PropertyValueFactory<>("fe"));
        itC.setCellValueFactory(new PropertyValueFactory<>("c"));
    }

    public void botonGenerarPresionado(ActionEvent actionEvent) {
        spnSeed.setDisable(true);
        spnC.setDisable(true);
        spnMG.setDisable(true);
        spnAK.setDisable(true);
        spnCant.setDisable(true);
        btnGenerar.setDisable(true);
        btnAdd.setDisable(false);
        btnSwitch.setDisable(true);
        spnIntervalos.setDisable(false);
        spnConfianza.setDisable(false);
        btnChi.setDisable(false);
        lblResultadoTest.setVisible(true);

        // Cuando se presiona el boton de generar, primero instanciamos la clase correspondiente
        if(metodoGeneracionNum == 0)
        {
            int m;
            int a;
            if(parametro) {m = spnMG.getValue(); a = spnAK.getValue();}
            else {m = (int) Math.pow(2,spnMG.getValue()); a = 1 + (4 * spnAK.getValue());}
            int c = spnC.getValue();
            int xo = spnSeed.getValue();
            int cant = spnCant.getValue();
            metodoGeneracion = new CongruencialMixto(m, a, xo, c, cant);
        }
        if(metodoGeneracionNum == 1)
        {
            int m;
            int a;
            if(parametro) {m = spnMG.getValue(); a = spnAK.getValue();}
            else {m = (int) Math.pow(2,spnMG.getValue()); a = 3 + (8 * spnAK.getValue());}    // o 5 + 8*k
            int xo = spnSeed.getValue();
            int cant = spnCant.getValue();
            metodoGeneracion = new CongruencialMultiplicativo(m, a, xo, cant);
        }
        if(metodoGeneracionNum == 2) metodoGeneracion = new AleatorioJava(spnCant.getValue());

        // Despues generamos los valores y los mostramos en la tabla
        ArrayList<Double> valores = metodoGeneracion.generarValores();
        int i = 0;
        for(Double valor: valores)
        {
            // Esta conversion rara la hacemos para que trunque con los decimales requeridos
            // El profe no quiere que redondeemos
            tblRandoms.getItems().add(new NumRandom(i, String.valueOf(((int) (valor * Math.pow(10,cantidadDecimales))) / (double) Math.pow(10,cantidadDecimales))));
            i++;
        }
        cantidadRandoms = i;
    }

    public void botonProximoPresionado(ActionEvent actionEvent) {
        Double nuevoValor = metodoGeneracion.generarValorExtra();
        tblRandoms.getItems().add(new NumRandom(cantidadRandoms, String.valueOf(((int) (nuevoValor * Math.pow(10,cantidadDecimales))) / (double) Math.pow(10,cantidadDecimales))));
        cantidadRandoms++;
    }

    public void botonTestChiPresionado(ActionEvent actionEvent) {
        btnChi.setDisable(true);
        btnHistograma.setDisable(false);
        lblResultadoTest.setVisible(true);

        chi = new ChiCuadrado(spnIntervalos.getValue());
        chi.procesar(metodoGeneracion.getValores());

        double[] intervalos = chi.getIntervalos();
        double [] frecuenciasO = chi.getFrecuenciasObservadas();
        double [] frecuenciasE = chi.getFrecuenciasEsperadas();
        double [] estadisticos = chi.getEstadisticos();

        for (int i = 0; i <spnIntervalos.getValue() ; i++)
        {
            NumChi chicuadrado;
            if(i == 0)
            {
                chicuadrado = new NumChi("0 - " +
                        String.valueOf(((int) (intervalos[i]* Math.pow(10,cantidadDecimales))) / (double) Math.pow(10,cantidadDecimales))
                        ,String.valueOf((int) frecuenciasO[i])
                        ,String.valueOf(((int) (frecuenciasE[i]* Math.pow(10,cantidadDecimales))) / (double) Math.pow(10,cantidadDecimales))
                        ,String.valueOf(((int) (estadisticos[i]* Math.pow(10,cantidadDecimales))) / (double) Math.pow(10,cantidadDecimales)));
            }
            else
            {
                chicuadrado = new NumChi(String.valueOf(((int) (intervalos[i - 1]* Math.pow(10,cantidadDecimales))) / (double) Math.pow(10,cantidadDecimales)) +
                        " - " +
                        String.valueOf(((int) (intervalos[i]* Math.pow(10,cantidadDecimales))) / (double) Math.pow(10,cantidadDecimales))
                        ,String.valueOf((int) frecuenciasO[i])
                        ,String.valueOf(((int) (frecuenciasE[i]* Math.pow(10,cantidadDecimales))) / (double) Math.pow(10,cantidadDecimales))
                        ,String.valueOf(((int) (estadisticos[i]* Math.pow(10,cantidadDecimales))) / (double) Math.pow(10,cantidadDecimales)));
            }
            tablaChi.getItems().add(chicuadrado);
        }

        Integer gradosLibertad = chi.getIntervalos().length - 1;
        Double valorCritico = chi.getValorCritico();                // Valor critico = valor tabulado
        Double valorCalculado = chi.getEstadisticoPrueba();
        String mensaje = "Resultado del Test: \n";
        if (chi.isRechazada())
        {
            mensaje = mensaje + "Con " + gradosLibertad.toString() + " grados de libertad, ";
            mensaje = mensaje + " se obtuvo un valor calculado de " + valorCalculado.toString() + ", \nel cual es mayor ";
            mensaje = mensaje + " al valor tabulado (" + valorCritico.toString() + "), \npor lo que ";
            mensaje = mensaje + " se rechaza la hipotesis nula de que los valores aleatorios tienen una distribucion uniforme.";
            lblResultadoTest.setText(mensaje);
        }
        else
        {
            mensaje = mensaje + "Con " + gradosLibertad.toString() + " grados de libertad, ";
            mensaje = mensaje + " se obtuvo un valor calculado de " + valorCalculado.toString() + ", \nel cual es menor ";
            mensaje = mensaje + " al valor tabulado (" + valorCritico.toString() + "), \npor lo que ";
            mensaje = mensaje + " no se rechaza la hipotesis nula de que los valores aleatorios tienen una distribucion uniforme.";
            lblResultadoTest.setText(mensaje);
        }
    }

    public void botonReiniciarPresionado(ActionEvent actionEvent) throws IOException {
        Stage nuevaVentana = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("pantallaPrincipal.fxml"));
        nuevaVentana.setTitle("UTN FRC - Simulaci??n - TP1");
        nuevaVentana.setScene(new Scene(root, 700, 750));
        nuevaVentana.show();
        Main.stage.close();
        Main.stage = nuevaVentana;
    }

    public void btnMetodoCongruencialMixtoPresionado(ActionEvent actionEvent) {
        spnSeed.setDisable(false);
        spnC.setDisable(false);
        spnMG.setDisable(false);
        spnAK.setDisable(false);
        spnCant.setDisable(false);
        btnGenerar.setDisable(false);
        btnAdd.setDisable(true);
        btnSwitch.setDisable(false);
        metodoGeneracionNum = 0;
    }

    public void btnMetodoCongruencialMultiplicativoPresionado(ActionEvent actionEvent) {
        spnSeed.setDisable(false);
        spnC.setDisable(true);
        spnMG.setDisable(false);
        spnAK.setDisable(false);
        spnCant.setDisable(false);
        btnGenerar.setDisable(false);
        btnAdd.setDisable(true);
        btnSwitch.setDisable(false);
        metodoGeneracionNum = 1;
    }

    public void btnMetodoDelLenguajePresionado(ActionEvent actionEvent) {
        spnSeed.setDisable(true);
        spnC.setDisable(true);
        spnMG.setDisable(true);
        spnAK.setDisable(true);
        spnCant.setDisable(false);
        btnGenerar.setDisable(false);
        btnAdd.setDisable(true);
        btnSwitch.setDisable(true);
        metodoGeneracionNum = 2;
    }

    // Este metodo cambia los textos de g y k - m y a cuando se aprieta el boton de switch
    public void switchParametros(ActionEvent actionEvent) {
        if (parametro) {
            parametro = false;
            lblParam1.setText("g:");
            lblParam2.setText("k:");
        } else {
            parametro = true;
            lblParam1.setText("m:");
            lblParam2.setText("a:");
        }
    }

    public void mostrarHistograma(ActionEvent actionEvent)
    {
        Stage grafico = new Stage();

        // Definici??n de los ejes
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Intervalos");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Frecuencias");

        //Definici??n de la gr??fica
        BarChart barChart = new BarChart(xAxis, yAxis);

        XYChart.Series dataSeries1 = new XYChart.Series();
        dataSeries1.setName("Fo");



        barChart.getData().add(dataSeries1);

        XYChart.Series dataSeries2 = new XYChart.Series();
        dataSeries2.setName("Fe");

        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.FLOOR);
        double extremoIzquierdo;
        double extremoDerecho;
        for ( int i = 0 ; i < chi.getIntervalos().length ; i++ )
        {
            if (i == 0)
            {
                extremoDerecho = new Double(df.format(chi.getIntervalos()[i]));
                dataSeries1.getData().add(new XYChart.Data("[ 0.0, " + extremoDerecho + "]", chi.getFrecuenciasObservadas()[i]));
                dataSeries2.getData().add(new XYChart.Data("[ 0.0, " + extremoDerecho + "]", chi.getFrecuenciasEsperadas()[i]));
            }
            else
            {
                extremoIzquierdo = new Double(df.format(chi.getIntervalos()[i - 1]));
                extremoDerecho = new Double(df.format(chi.getIntervalos()[i]));
                dataSeries1.getData().add(new XYChart.Data("[ " + extremoIzquierdo + ", " + extremoDerecho + "]", chi.getFrecuenciasObservadas()[i]));
                dataSeries2.getData().add(new XYChart.Data("[ " + extremoIzquierdo + ", " + extremoDerecho + "]", chi.getFrecuenciasEsperadas()[i]));
            }
        }

        barChart.getData().add(dataSeries2);

        VBox vbox = new VBox(barChart);
        grafico.setTitle("Histograma");
        grafico.setScene(new Scene(vbox, 600, 400));
        grafico.show();
    }
}

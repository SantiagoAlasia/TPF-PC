package app;

import Segmentos.SegmentoA;
import Segmentos.SegmentoB;
import Segmentos.SegmentoC;
import Segmentos.SegmentoD;
import Segmentos.SegmentoE;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Main {

    public static void main(String[] args) {
        LoggerTP.iniciar();  // INICIO DEL LOG
        try {
            RdP rdp = new RdP(); // Instanciacion de la red de petri que modela el sistema
            int m = rdp.cantidadTransiciones();

            PoliticaInterface politica = new PoliticaRandom();  // Eleccion de la politica para la ejecucion, solo una puede estar sin comentar a la vez
            // app.PoliticaInterface politica = new PoliticaPrioritaria();

            Colas colas = new Colas(m); // Cola con las variables de condicion (Transiciones)

            Monitor monitor = new Monitor(rdp, politica, colas);  // Instanciacion del monitor con la red y la politica utilizada

            // Creacion de un executor para el manejo adecuado de los hilos
            ThreadPoolExecutor executor;
            executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

            // Lanzo los hilos

            executor.execute(new SegmentoA(monitor));
            executor.execute(new SegmentoB(monitor));
            executor.execute(new SegmentoC(monitor));
            executor.execute(new SegmentoD(monitor));
            executor.execute(new SegmentoE(monitor));

            // Logica para el disparo de 200 invariantes
            // Esperar ejecución (simulación de 200 datos o cierta duración)
            try {
                Thread.sleep(25000); // o lógica más precisa basada en tokens/invariantes
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Finalizar ejecucion
            executor.shutdownNow();

            System.out.println("Ejecucion Finalizada");
        } finally {
            LoggerTP.finalizar(); // CIERRE DEL LOG
        }
    }

}
package Segmentos;

import app.MonitorInterface;
import app.LoggerTP;
import app.PoliticaPriorizada;
import app.PoliticaInterface;

import java.util.ArrayList;
import java.util.List;

public class SegmentoC implements Runnable {

    private final MonitorInterface monitor;
    private final PoliticaInterface politica;
    private final int[] transiciones = {5, 6}; // T5 (inicio simple), T6 (fin simple)

    public SegmentoC(MonitorInterface monitor) {
        this.monitor = monitor;
        this.politica = new PoliticaPriorizada();
    }

    @Override
    public void run() {
        String nombre = Thread.currentThread().getName();

        while (!Thread.currentThread().isInterrupted()) {
            List<Integer> habilitadas = new ArrayList<>();
            for (int t : transiciones) {
                if (monitor.getHabilitadas().contains(t)) {
                    habilitadas.add(t);
                }
            }

            int tElegida = -1;
            if (!habilitadas.isEmpty()) {
                tElegida = politica.cualTransicionDisparar(habilitadas);
            }

            if (tElegida != -1 && monitor.fireTransition(tElegida)) {
                String msg = nombre + " disparó T" + tElegida;
                System.out.println(msg);
                LoggerTP.registrarTransicion(tElegida);
            }

            synchronized (monitor) {
                try {
                    monitor.wait(50); // configurable
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }
}
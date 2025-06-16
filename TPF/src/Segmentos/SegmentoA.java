package Segmentos;

import app.LoggerTP;
import app.MonitorInterface;

public class SegmentoA implements Runnable {

    private final MonitorInterface monitor;
    private int t = 0; // Contador de transiciones disparadas

    public SegmentoA(MonitorInterface monitor) {
        this.monitor = monitor;
    }

    @Override
    public void run() {
        String nombre = Thread.currentThread().getName();
        while (!Thread.currentThread().isInterrupted()) {
            // Usar getHabilitadas() porque estaHabilitada es privado en Monitor
            if (monitor.getHabilitadas().contains(t)) {
                if (monitor.fireTransition(t)) {
                    String msg = nombre + " disparó T" + t;
                    System.out.println(msg);
                    LoggerTP.registrarTransicion(t);
                }
            }

            t = (t + 1) % 2; // alterna entre 0 y 1

            synchronized (monitor) {
                try {
                    monitor.wait(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }
}


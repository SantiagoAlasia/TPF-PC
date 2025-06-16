package Segmentos;

import app.LoggerTP;
import app.MonitorInterface;

public class SegmentoD implements Runnable {
    private final MonitorInterface monitor;
    private int t = 8; // Comienza con T8

    public SegmentoD(MonitorInterface monitor) {
        this.monitor = monitor;
    }

    @Override
    public void run() {
        String nombre = Thread.currentThread().getName();

        while (!Thread.currentThread().isInterrupted()) {
            if (monitor.getHabilitadas().contains(t)) {
                if (monitor.fireTransition(t)) {
                    String msg = nombre + " disparó T" + t;
                    System.out.println(msg);
                    LoggerTP.registrarTransicion(t);
                }
            }

            // Avanza al siguiente paso del camino complejo: T8 → T9 → T10 → T8 ...
            t++;
            if (t > 10) {
                t = 8;
            }

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
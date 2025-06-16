package Segmentos;

import app.LoggerTP;
import app.MonitorInterface;

public class SegmentoB implements Runnable {

    private final MonitorInterface monitor;
    private int t = 2; // Comienza con T2 (inicio camino medio)

    public SegmentoB(MonitorInterface monitor) {
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

            // Avanza al siguiente paso del camino medio: T2 → T3 → T4 → T2 ...
            t++;
            if (t > 4) {
                t = 2;
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

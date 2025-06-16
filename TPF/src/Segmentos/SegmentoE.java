package Segmentos;

import app.LoggerTP;
import app.MonitorInterface;

public class SegmentoE implements Runnable {

    private final MonitorInterface monitor;
    private final int t = 11; // T11 (índice 11)

    public SegmentoE(MonitorInterface monitor) {
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

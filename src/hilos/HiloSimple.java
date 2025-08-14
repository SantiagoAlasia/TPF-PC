package hilos;

import monitor.MonitorInterface;

public class HiloSimple implements Runnable {
    private final MonitorInterface monitor;

    public HiloSimple(MonitorInterface monitor) {
        this.monitor = monitor;
    }

     @Override
    public void run() {
        while (!Thread.interrupted()) {
            if (monitor.fireTransition(7)) {  // T7: P7 -> P6+P11
            }
        }
    }
}
package hilos;

import monitor.MonitorInterface;

public class HiloMedio implements Runnable {
    private final MonitorInterface monitor;

    public HiloMedio(MonitorInterface monitor) {
        this.monitor = monitor;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            if (monitor.fireTransition(4)) {
                monitor.fireTransition(5);
            }
        }
    }
}



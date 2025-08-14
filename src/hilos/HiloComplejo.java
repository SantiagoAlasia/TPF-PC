package hilos;

import monitor.MonitorInterface;

public class HiloComplejo implements Runnable {
    private final MonitorInterface monitor;

    public HiloComplejo(MonitorInterface monitor) {
        this.monitor = monitor;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            if (monitor.fireTransition(9)) {  // T9: P8 + P6 → P9
                if (monitor.fireTransition(10)) {  // T10: P9 + P6 → P10
                    monitor.fireTransition(11);    // T11: P10 → P6 + P11
                }
            }
        }
    }

}

package hilos;

import monitor.MonitorInterface;

public class HiloComplejo extends Thread {
    private final MonitorInterface monitor;

    public HiloComplejo(MonitorInterface monitor) {
        this.monitor = monitor;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            if (monitor.fireTransition(9)) {  // T9: P8 + P6 → P9
                dormir(120);
                if (monitor.fireTransition(10)) {  // T10: P9 + P6 → P10
                    dormir(120);
                    monitor.fireTransition(11);    // T11: P10 → P6 + P11
                }
            } else {
                dormir(100);
            }
        }
    }

    private void dormir(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

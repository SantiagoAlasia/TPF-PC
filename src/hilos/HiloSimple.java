package hilos;

import monitor.MonitorInterface;

public class HiloSimple extends Thread {
    private final MonitorInterface monitor;

    public HiloSimple(MonitorInterface monitor) {
        this.monitor = monitor;
    }

     @Override
    public void run() {
        while (!Thread.interrupted()) {
            if (monitor.fireTransition(7)) {  // T7: P7 -> P6+P11
                dormir(200);
            } else {
                dormir(100);
            }
        }
    }


    private void dormir(int milisegundos) {
        try {
            Thread.sleep(milisegundos);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restaurar el estado de interrupción
        }
    }
}
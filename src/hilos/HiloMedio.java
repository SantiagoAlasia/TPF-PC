package hilos;

import monitor.MonitorInterface;

public class HiloMedio extends Thread {
    private final MonitorInterface monitor;

    public HiloMedio(MonitorInterface monitor) {
        this.monitor = monitor;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
           if (monitor.fireTransition(4)) {  // T4: P4+P6 -> P5
                    dormir(150); // Si se pudo disparar T4, esperar un poco
                    monitor.fireTransition(5);  // T5: P5 -> P6+P11
                }
            else {
                dormir(100); // Si no se pudo disparar T3, esperar un poco
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
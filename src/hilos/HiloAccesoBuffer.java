package hilos;

import monitor.MonitorInterface;

public class HiloAccesoBuffer extends Thread {
    private final MonitorInterface monitor;

    public HiloAccesoBuffer(MonitorInterface monitor) {
        this.monitor = monitor;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
           boolean exito1 = monitor.fireTransition(1);  // T1: P0+P2 -> P1
              if (exito1) {
                monitor.fireTransition(2);  // T2: P1 -> P3+P2
              }
              else {
                dormir(100); // Si no se pudo disparar T1, esperar un poco
         }
        }
    }

    private void dormir(int milisegundos) {
        try {
            Thread.sleep(milisegundos);
        }   catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restaurar el estado de interrupción
        }
    }
}
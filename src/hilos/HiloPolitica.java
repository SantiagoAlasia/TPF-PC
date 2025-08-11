package hilos;
import monitor.MonitorInterface;
import politica.Politica;

public class HiloPolitica extends Thread {
    private final MonitorInterface monitor;
    private final Politica politica;

    public HiloPolitica(MonitorInterface monitor, Politica politica) {
        this.monitor = monitor;
        this.politica = politica;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            int modo = politica.elegirModo();

            boolean exito = false;

            switch (modo) {
                case 0 -> // Modo simple
                    exito = monitor.fireTransition(6);
                case 1 -> // Modo medio
                    exito = monitor.fireTransition(3);
                case 2 -> // Modo complejo
                    exito = monitor.fireTransition(8);
            }

            if (!exito) {
                dormir (100); // Espera 100 ms antes de intentar de nuevo
            }
        }
    }

    private void dormir(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restaurar el estado de interrupción
        }
    }

    
    
}

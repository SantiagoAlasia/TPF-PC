import hilos.*;
import monitor.Monitor;
import monitor.MonitorInterface;
import politica.Politica;

public class Main {
    public static void main(String[] args) {
        // 1. Crear monitor concurrente
        MonitorInterface monitor = new Monitor();

        // 2. Elegir política: true = prioriza modo simple, false = aleatoria
        Politica politica = new Politica(false); // podés cambiar esto

        // 3. Crear todos los hilos del sistema
        HiloAccesoBuffer hab = new HiloAccesoBuffer(monitor);        // T1 y T2
        HiloPolitica hp = new HiloPolitica(monitor, politica);       // T3 o T6 o T8
        HiloSimple hs = new HiloSimple(monitor);                     // T7
        HiloMedio hm = new HiloMedio(monitor);                       // T4, T5
        HiloComplejo hc = new HiloComplejo(monitor);                 // T9, T10, T11

        // 4. Iniciar todos los hilos
        hab.start();
        hp.start();
        hs.start();
        hm.start();
        hc.start();

        // 5. Ejecutar durante un tiempo definido (ej: 30 segundos)
        try {
            Thread.sleep(30000); // podés ajustar el tiempo total de ejecución
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 6. Detener todos los hilos de manera segura
        hab.interrupt();
        hp.interrupt();
        hs.interrupt();
        hm.interrupt();
        hc.interrupt();

        System.out.println("Programa finalizado.");
    }
}

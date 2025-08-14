package analisis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class AnalizadorLog {
    private final String rutaLog = "transiciones.log";

    // Contadores
    private int totalTransiciones = 0;
    private int datosFinalizados = 0;
    private int caminosSimples = 0;
    private int caminosMedios = 0;
    private int caminosComplejos = 0;

    // Para contar T1 a T11
    private final HashMap<Integer, Integer> transiciones = new HashMap<>();

    public void analizar() {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaLog))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (!linea.contains("| T")) continue;

                totalTransiciones++;

                // Obtener el número de transición
                String[] partes = linea.split("\\|");
                String trans = partes[1].trim(); // T1, T2, etc.
                int t = Integer.parseInt(trans.substring(1)); // número sin la T

                // Contador por transición
                transiciones.put(t, transiciones.getOrDefault(t, 0) + 1);

                // Contadores por camino
                switch (t) {
                    case 7 -> caminosSimples++;
                    case 5 -> caminosMedios++;
                    case 11 -> caminosComplejos++;
                }

                // Datos finalizados: todos los caminos terminan en T11, T7 o T5
                if (t == 5 || t == 7 || t == 11) {
                    datosFinalizados++;
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el log: " + e.getMessage());
            return;
        }

        // Mostrar resultados
        imprimirEstadisticas();
    }

    private void imprimirEstadisticas() {
        System.out.println("======= ESTADISTICAS DEL LOG =======");
        System.out.println("Total de transiciones disparadas: " + totalTransiciones);
        System.out.println("Total de datos finalizados (P11): " + datosFinalizados);
        System.out.println("Total de caminos simples (T6→T7): " + caminosSimples);
        System.out.println("Total de caminos medios  (T3→T4→T5): " + caminosMedios);
        System.out.println("Total de caminos complejos (T8→T9→T10→T11): " + caminosComplejos);
        System.out.println();
        System.out.println("Transiciones individuales:");
        for (int i = 1; i <= 11; i++) {
            int count = transiciones.getOrDefault(i, 0);
            System.out.printf("  T%d: %d veces\n", i, count);
        }
        System.out.println("====================================");
    }
}
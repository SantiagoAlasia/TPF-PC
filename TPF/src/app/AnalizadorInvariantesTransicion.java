package app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnalizadorInvariantesTransicion {
    public static void main(String[] args) {
        int transiciones = 12; // T0 a T11
        int[] conteo = new int[transiciones];
        Pattern patron = Pattern.compile("transición T(\\d+)");

        // Leer el log y contar disparos de cada transición
        try (BufferedReader br = new BufferedReader(new FileReader("log.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                Matcher m = patron.matcher(linea);
                if (m.find()) {
                    int t = Integer.parseInt(m.group(1));
                    if (t >= 0 && t < transiciones) {
                        conteo[t]++;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error leyendo log.txt: " + e.getMessage());
            return;
        }

        // --- Verificación de invariantes cuando hay mezcla de caminos ---

        // Conteos esperados de cada tipo de invariante
        int simple = conteo[5]; // T5
        int medio = conteo[2];  // T2
        int complejo = conteo[7]; // T7

        boolean ok = true;

        // Invariante SIMPLE: T5, T6
        if (simple != conteo[6]) {
            System.out.println("[INVARIANTE SIMPLE] T5 != T6");
            ok = false;
        }

        // Invariante MEDIA: T2, T3, T4
        if (!(medio == conteo[3] && medio == conteo[4])) {
            System.out.println("[INVARIANTE MEDIA] T2, T3, T4 no son iguales");
            ok = false;
        }

        // Invariante COMPLEJA: T7, T8, T9, T10
        if (!(complejo == conteo[8] && complejo == conteo[9] && complejo == conteo[10])) {
            System.out.println("[INVARIANTE COMPLEJA] T7, T8, T9, T10 no son iguales");
            ok = false;
        }

        // Verificación total
        int totalInvariantes = simple + medio + complejo;

        if (conteo[0] != totalInvariantes) {
            System.out.println("[T0] No coincide con la suma total de invariantes");
            ok = false;
        }

        if (conteo[1] != totalInvariantes) {
            System.out.println("[T1] No coincide con la suma total de invariantes");
            ok = false;
        }

        if (conteo[11] != totalInvariantes) {
            System.out.println("[T11] No coincide con la suma total de invariantes");
            ok = false;
        }

        if (ok) {
            System.out.println("Todos los invariantes de transición se cumplen correctamente.");
        } else {
            System.out.println("Se encontraron errores en los invariantes de transición.");
        }
    }
}

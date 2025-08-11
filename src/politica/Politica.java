package politica;

import java.util.Random;

public class Politica {
    private final Random random = new Random();
    private final boolean priorizarSimple; 

    public Politica(boolean priorizarSimple) {
        this.priorizarSimple = priorizarSimple;
    }

    public int elegirModo() {
        if (priorizarSimple) {
            return 0; // Modo simple
        } else {            
            return random.nextInt(3) ; // 0 = simple, 1 = medio, 2 = complejo
        }
    }
    
}

package app;

import java.util.Random;

public class PoliticaRandom implements PoliticaInterface {
    @Override
    public int cualTransicionDisparar() {
        int indice = 0;
        Random rand = new Random();

        switch(rand.nextInt(3)){
            case 0:
                indice = 3; // T2
                break;
            case 1:
                indice = 6; // T5
                break;
            case 2:
                indice = 8; // T7
                break;
            default:
                break;
        }
        return indice;
    }
}

package app;

import java.util.List;
import java.util.Random;

public class PoliticaRandom implements PoliticaInterface {

    private final Random rand = new Random();

    @Override
    public int cualTransicionDisparar(List<Integer> habilitadas) {
        if (habilitadas == null || habilitadas.isEmpty()) return -1;
        return habilitadas.get(rand.nextInt(habilitadas.size()));
    }
}



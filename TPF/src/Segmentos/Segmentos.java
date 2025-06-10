package Segmentos;

import app.Monitor;

public abstract class Segmentos implements Runnable {
    protected Monitor monitor;

    public Segmentos(Monitor monitor) {
        this.monitor = monitor;
    }

    @Override
    public void run() {
    }
}
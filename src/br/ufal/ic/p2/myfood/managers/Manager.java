package br.ufal.ic.p2.myfood.managers;

import java.io.IOException;

public abstract class Manager {
    abstract void cleanRepository() throws IOException;
    abstract void saveRepository() throws IOException;
}

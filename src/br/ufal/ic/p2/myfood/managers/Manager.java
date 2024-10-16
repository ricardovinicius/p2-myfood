package br.ufal.ic.p2.myfood.managers;

import java.io.IOException;

public abstract class Manager {
    void cleanRepository() throws IOException {}
    void saveRepository() throws IOException {}
}

package ua.nix.module.service;

import java.util.List;
import java.util.Random;

public interface AbstractService<T> {
    Random RANDOM = new Random();

    void create(T obj);

    List<T> getAll();

    T generate();
}

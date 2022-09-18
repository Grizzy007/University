package ua.nix.module.repository;

import java.util.List;
import java.util.Optional;

public interface AbstractRepository<T> {
    List<T> get();

    Optional<T> getById(String id);

    void create(T object);

    T update(T object);

    T delete(String id);
}

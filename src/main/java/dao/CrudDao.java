package dao;

import java.util.List;

public interface CrudDao<T> {

    int create(T t);

    T get(int id);

    T update(int id, T t);

    void delete(int id);

    List<T> getAll ();
}

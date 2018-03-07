public interface CrudDao<T> {

    void create(T t);

    T get(int id);

    T update(T t);

    void delete(int id);
}

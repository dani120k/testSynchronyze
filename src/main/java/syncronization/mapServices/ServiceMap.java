package syncronization.mapServices;

public interface ServiceMap<T extends Object> {
    void update(T newObject);

    void add(T newObject);

    void delete(T object);
}

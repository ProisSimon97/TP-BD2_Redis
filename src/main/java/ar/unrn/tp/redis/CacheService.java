package ar.unrn.tp.redis;

public interface CacheService {
    void store(String key, String value);

    String retrieve(String key);

    void clear(String key);

    void clearAll();
}

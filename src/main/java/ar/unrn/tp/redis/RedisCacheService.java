package ar.unrn.tp.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
@RequiredArgsConstructor
public class RedisCacheService implements CacheService {
    private final JedisPool jedisPool;

    @Value("${redis.sessiondata.ttl}")
    private int ttl;

    private void closeConnection(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

    @Override
    public void store(String key, String value) {
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();

            jedis.set(key, value);
            jedis.expire(key, ttl);

        } catch (Exception e) {
            closeConnection(jedis);
            throw new RuntimeException(e);

        } finally {
            closeConnection(jedis);
        }
    }

    @Override
    public String retrieve(String key) {
        Jedis jedis = null;
        String json;

        try {
            jedis = jedisPool.getResource();
            json = jedis.get(key);

        } catch (Exception e) {
            closeConnection(jedis);
            throw new RuntimeException(e);

        } finally {
            closeConnection(jedis);
        }

        return json;
    }

    @Override
    public void clear(String key) {
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
            jedis.del(key);

        } catch (Exception e) {
            closeConnection(jedis);
            throw new RuntimeException(e);

        } finally {
            closeConnection(jedis);
        }
    }

    @Override
    public void clearAll() {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.flushAll();

        } catch (Exception e) {
            closeConnection(jedis);
            throw new RuntimeException(e);

        } finally {
            closeConnection(jedis);
        }
    }
}

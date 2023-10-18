package ar.unrn.tp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableAutoConfiguration(exclude = {
        JmxAutoConfiguration.class
})
public class JedisCacheConfig {

    @Value("${redis.host}")
    private String redisHost;

    @Value("${redis.port}")
    private Integer redisPort;

    @Value("${redis.timeout}")
    private Integer redisTimeout;

    @Value("${redis.maximumActiveConnectionCount}")
    private Integer redisMaximumActiveConnectionCount;

    @Value("${redis.user}")
    private String redisUser;

    @Value("${redis.password}")
    private String redisPassword;

    @Bean
    public JedisPool pool() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(redisMaximumActiveConnectionCount);

        return new JedisPool(poolConfig, redisHost, redisPort, redisTimeout, redisUser, redisPassword);
    }
}

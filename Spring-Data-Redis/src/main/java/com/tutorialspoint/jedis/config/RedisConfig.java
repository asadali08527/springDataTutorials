package com.tutorialspoint.jedis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.tutorialspoint.jedis.entity.Customer;

@Configuration
public class RedisConfig {
	// Define a connection factory
	@Bean
	JedisConnectionFactory jedisConnectionFactory() {
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration("localhost", 6379);
		return new JedisConnectionFactory(redisStandaloneConfiguration);
	}

	// Define a RedisTemplate using JedisConnection Factory
	@Bean
	public RedisTemplate<String, Customer> redisTemplate() {
		RedisSerializer<String> redisSerializer = new StringRedisSerializer();
		RedisTemplate<String, Customer> template = new RedisTemplate<>();
		template.setConnectionFactory(jedisConnectionFactory());
		template.setKeySerializer(redisSerializer);
	    template.afterPropertiesSet();
		return template;
	}

}

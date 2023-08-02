package com.syllabus.config;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.lang.Nullable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConfig {

	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(connectionFactory);
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new JsonRedisSerializer());

		return template;
	}

	static class JsonRedisSerializer implements RedisSerializer<Object> {

		private final ObjectMapper objectMapper;

		public JsonRedisSerializer() {
			this.objectMapper = new ObjectMapper();
		}

		@Override
		public byte[] serialize(@Nullable Object object) throws SerializationException {
			try {
				return objectMapper.writeValueAsBytes(object);
			} catch (JsonProcessingException ex) {
				throw new SerializationException(ex.getMessage(), ex);
			}
		}

		@Override
		public Object deserialize(@Nullable byte[] bytes) throws SerializationException {

			if (bytes == null) {
				return null;
			}

			try {
				return objectMapper.readValue(bytes, Object.class);
			} catch (Exception ex) {
				throw new SerializationException(ex.getMessage(), ex);
			}
		}
	}
}
package com.syllabus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;

import static io.r2dbc.pool.PoolingConnectionFactoryProvider.MAX_SIZE;
import static io.r2dbc.spi.ConnectionFactoryOptions.DATABASE;
import static io.r2dbc.spi.ConnectionFactoryOptions.DRIVER;
import static io.r2dbc.spi.ConnectionFactoryOptions.HOST;
import static io.r2dbc.spi.ConnectionFactoryOptions.PASSWORD;
import static io.r2dbc.spi.ConnectionFactoryOptions.PORT;
import static io.r2dbc.spi.ConnectionFactoryOptions.USER;

@Configuration
public class DBConfig {

	@Bean
	ConnectionFactory connectionFactory() {
		return ConnectionFactories.get(ConnectionFactoryOptions.builder().option(DRIVER, "postgresql")
				.option(HOST, "localhost").option(PORT, 5432).option(USER, "br93").option(PASSWORD, "root1234")
				.option(DATABASE, "auth_db").option(MAX_SIZE, 40).build());
	}

}

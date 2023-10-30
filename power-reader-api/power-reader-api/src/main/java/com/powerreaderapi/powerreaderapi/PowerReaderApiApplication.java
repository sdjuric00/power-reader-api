package com.powerreaderapi.powerreaderapi;

import com.powerreaderapi.powerreaderapi.config.RsaKeyConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(RsaKeyConfigProperties.class)
@SpringBootApplication
public class PowerReaderApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PowerReaderApiApplication.class, args);
	}

}

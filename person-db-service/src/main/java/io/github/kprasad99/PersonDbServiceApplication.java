package io.github.kprasad99;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import reactor.blockhound.BlockHound;

@SpringBootApplication
public class PersonDbServiceApplication {

	public static void main(String[] args) {
	    BlockHound.builder().install();
		SpringApplication.run(PersonDbServiceApplication.class, args);
	}

}

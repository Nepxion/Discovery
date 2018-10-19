package com.nepxion.discovery.scconfig.server.example;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @author Ankeway
 * @version 1.0
 */

@SpringBootApplication
@EnableConfigServer
public class SCConfigServerApplication {
	public static void main(String[] args) {
		new SpringApplicationBuilder(SCConfigServerApplication.class).run(args);
	}
}
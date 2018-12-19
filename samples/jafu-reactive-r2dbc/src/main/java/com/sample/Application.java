package com.sample;

import static org.springframework.fu.jafu.Jafu.*;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.fu.jafu.JafuApplication;

public abstract class Application {

	public static JafuApplication app = webApplication(app ->
		app.enable(Configurations.dataConfig)
		   .enable(Configurations.webConfig)
		   .listener(ApplicationReadyEvent.class, e -> app.ref(UserRepository.class).init())
	);

	public static void main (String[] args) {
		if (System.getProperty("org.graalvm.nativeimage.imagecode") != null) {
			System.setProperty("org.springframework.boot.logging.LoggingSystem", "org.springframework.boot.logging.java.JavaLoggingSystem");
		}
		app.run(args);
	}
}
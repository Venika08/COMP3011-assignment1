package comp3011.assignment1.config;

import java.time.Clock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Provides application-wide time configuration.
 */
@Configuration
public class TimeConfiguration {
	
	/**
	 * Provides a clock that always uses UTC
	 * 
	 * @return the application UTC clock
	 */
	@Bean
	Clock utcClock() {
		return Clock.systemUTC();
	}
}

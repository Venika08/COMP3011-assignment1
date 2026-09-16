package comp3011.assignment1.service;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;

import org.springframework.stereotype.Service;
import comp3011.assignment1.dto.UptimeResponse;

/*
 * Calculates server lifecycle and uptime information.
 */

@Service
public class ServerUptimeService {
	private final Clock clock;
	private final Instant serverStart;
	
	/**
	 * Creates the service and records its UTC start time. 
	 * 
	 * @param clock application clock by spring
	 */
	public ServerUptimeService(Clock clock) {
		this.clock = clock;
		this.serverStart = clock.instant();
	}
	
	/**
	 * 
	 * @return current server uptime information
	 */
	public UptimeResponse getUptime() {
		Instant utcNow = clock.instant();
		Duration uptime = Duration.between(serverStart, utcNow);
		double uptimeSeconds = uptime.toNanos() / 1_000_000_000.0;
		
		return new UptimeResponse(serverStart, utcNow, uptimeSeconds);
	}
}

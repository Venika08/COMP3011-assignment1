package comp3011.assignment1.dto;

import java.time.Instant;

/**
* Represents server up time information by the administration API. 
*
* @param utcServerStart server start timestamp in UTC
* @param utcNow current timestamp in UTC
* @param serverUptimeSeconds elapsed server uptime in seconds
*/


public record UptimeResponse(
		Instant utcServerStart, 
		Instant utcNow, 
		double serverUptimeSeconds) {

}

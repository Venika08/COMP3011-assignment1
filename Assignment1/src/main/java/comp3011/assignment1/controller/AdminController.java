package comp3011.assignment1.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import comp3011.assignment1.dto.UptimeResponse;
import comp3011.assignment1.service.ServerUptimeService;

/**
 * Server administration endpoints.
 */
@RestController
@RequestMapping("/api/v1/admin")

public class AdminController {
	
	private final ServerUptimeService serverUptimeService;
	
	/**
	 * Creates the controller with its required service
	 * 
	 * @param serverUptimeService service providing server uptime information
	 * 
	 */
	public AdminController(ServerUptimeService serverUptimeService) {
		this.serverUptimeService = serverUptimeService;
	}
	
	/**
	 * Returns the server start time, its current time and elapsed uptime. 
	 * 
	 * @return current server uptime information
	 */
	@GetMapping(value = "/uptime", produces = MediaType.APPLICATION_JSON_VALUE)
	public UptimeResponse getServerUptime() {
		return serverUptimeService.getUptime();
	}
}

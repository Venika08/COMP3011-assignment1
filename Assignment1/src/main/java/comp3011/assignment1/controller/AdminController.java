package comp3011.assignment1.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.Instant;
import org.springframework.web.bind.annotation.ExceptionHandler;
import jakarta.servlet.http.HttpServletRequest;

import comp3011.assignment1.dto.UptimeResponse;
import comp3011.assignment1.service.ServerUptimeService;
import comp3011.assignment1.service.ShutdownService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import comp3011.assignment1.dto.ErrorResponse;
import comp3011.assignment1.exception.ShutdownException;

import comp3011.assignment1.dto.ShutdownResponse;
/**
 * Server administration endpoints.
 */
@RestController
@RequestMapping("/api/v1/admin")

public class AdminController {
	
	private final ServerUptimeService serverUptimeService;
	private final ShutdownService shutdownService;
	/**
	 * Creates the controller with its required service
	 * 
	 * @param serverUptimeService service providing server uptime information
	 * 
	 */
	public AdminController(ServerUptimeService serverUptimeService, ShutdownService shutdownService) {
		this.serverUptimeService = serverUptimeService;
		this.shutdownService = shutdownService;
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
		
	/**
	 * Requests server shutdown
	 * @return confirmation that shutdown was actioned. 
	 */
	@PostMapping(value = "/shutdown", produces = MediaType.APPLICATION_JSON_VALUE)
	
	public ResponseEntity<ShutdownResponse> shutdownServer() {
		shutdownService.requestShutdown();
		
		ShutdownResponse response = new ShutdownResponse("Graceful shutdown requested.");
		
		return ResponseEntity.accepted().body(response);
	}
	
	@ExceptionHandler(ShutdownException.class)
	public ResponseEntity<ErrorResponse> handleShutdownConflict(
			ShutdownException exception, 
			HttpServletRequest request) {
		
		ErrorResponse errorResponse = new ErrorResponse(
				Instant.now(),
				409, 
				"Conflict", 
				exception.getMessage(), 
				request.getRequestURI());
		
		return ResponseEntity.status(409).body(errorResponse);
	}
	
	
	}
	





package comp3011.assignment1.service;

import org.springframework.stereotype.Service;

import comp3011.assignment1.dto.GlobalStatsResponse;

/**
 * Global speech-to-text token usage for current server process. 
 * 
 */
@Service

public class GlobalStatsService {
	
	private long inputTokens;
	private long outputTokens;
	
	/**
	 * Adds token usage from one completed time. 
	 * 
	 * @param additionalInputTokens input tokens consumed by the request 
	 * @param additionalOnputTokens output tokens produced by the request
	 */
	public synchronized void recordUsage(
			long additionalInputTokens, 
			long additionalOutputTokens) {
		
		if (additionalInputTokens < 0 || additionalOutputTokens < 0) {
			throw new IllegalArgumentException(
					"Token usage values must not be negative.");
		}
		
		inputTokens = Math.addExact(inputTokens, additionalInputTokens);
		outputTokens = Math.addExact(outputTokens, additionalOutputTokens);
		
	}
	
	/**
	 * Returns a consistent snapshot of the global token counters. 
	 * 
	 * @return token usage
	 */
	public synchronized GlobalStatsResponse getGlobalStats() {
		return new GlobalStatsResponse(inputTokens, outputTokens);
	}

}

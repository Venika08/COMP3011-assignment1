package comp3011.assignment1.dto;

/**
 * Represents cumulative speech-to-text token usage since server startup.
 * 
 * @param inputTokens total input tokens consumed 
 * @param outputTokens total output tokens produced 
 */

public record GlobalStatsResponse(
		long inputTokens, 
		long outputTokens) {

}

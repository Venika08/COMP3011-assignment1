package comp3011.assignment1.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;
import comp3011.assignment1.dto.TranscriptionResponse;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.http.MediaType;

@Service
public class SpeechToTextService {
	
	private final RestClient openAiRestClient;
	private final GlobalStatsService globalStatsService;
	
	public SpeechToTextService(RestClient openAiRestClient, GlobalStatsService globalStatsService) {
		
		this.openAiRestClient = openAiRestClient;
		this.globalStatsService = globalStatsService;
	}
	
	public TranscriptionResponse transcribe(MultipartFile audioFile) {
		LinkedMultiValueMap<String, Object> request = new LinkedMultiValueMap<>();
		
		request.add("file", audioFile.getResource());
		request.add("model", "gpt-4o-mini-transcribe");
		OpenAiResult result = openAiRestClient.post()
				.uri("/v1/audio/transcriptions")
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.body(request)
				.retrieve()
				.body(OpenAiResult.class);
		
		globalStatsService.recordUsage(result.usage().input_tokens(), result.usage().output_tokens());
		return new TranscriptionResponse(result.text());
		
		
	}
	
	private record OpenAiResult(String text, TokenUsage usage) {
		
	}
	
	private record TokenUsage(long input_tokens, long output_tokens) {
	}

}


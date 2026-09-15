package comp3011.assignment1.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestParam;

import comp3011.assignment1.dto.TranscriptionResponse;
import comp3011.assignment1.service.SpeechToTextService;

@RestController 
@RequestMapping("/api/v1/transcriptions")

public class TranscriptionController {
	
	private final SpeechToTextService speechToTextService;
	
	public TranscriptionController(SpeechToTextService speechToTextService) {
		this.speechToTextService = speechToTextService;
	
	}
	
	
	@PostMapping
	public ResponseEntity<Object> transcribe(
			@RequestParam("file") MultipartFile audioFile) {
		
		if (audioFile == null || audioFile.isEmpty()) {
			return ResponseEntity.badRequest().body("Please provide an audio file.");
		}
		
		TranscriptionResponse response = 
				speechToTextService.transcribe(audioFile);
		
		return ResponseEntity.ok(response);
	}
	
	
	
	}


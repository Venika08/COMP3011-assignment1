package comp3011.assignment1.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import comp3011.assignment1.service.SpeechToTextService;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.mock.web.MockMultipartFile;
import comp3011.assignment1.dto.TranscriptionResponse;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;


@WebMvcTest(TranscriptionController.class)
class TranscriptionControllerTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockitoBean
	private SpeechToTextService speechToTextService;
	
	@Test
	void audioFileReturnsTranscription() throws Exception {
		MockMultipartFile audioFile = new MockMultipartFile("file", "recording.webm", "audio/webm", "fake test audio".getBytes());
		
		when(speechToTextService.transcribe(any())).thenReturn(new TranscriptionResponse("Hi this is a test."));
		
		mockMvc.perform(multipart("/api/v1/transcriptions").file(audioFile)).andExpect(status().isOk()).andExpect(content().json("{\"text\":\"Hi this is a test.\"}"));
	}

}

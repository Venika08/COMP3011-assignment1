package comp3011.assignment1.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request
        .MockMvcRequestBuilders.multipart;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import comp3011.assignment1.dto.TranscriptionResponse;
import comp3011.assignment1.service.SpeechToTextService;

@WebMvcTest(TranscriptionController.class)
class ConcurrentTranscriptionControllerTests {
	
	private static final Logger logger = LoggerFactory.getLogger(ConcurrentTranscriptionControllerTests.class);
	@Autowired
	private MockMvc mockMvc;
	
	@MockitoBean
	private SpeechToTextService speechToTextService;
	
	private int sendOneRequest() throws Exception {
		MockMultipartFile audioFile = new MockMultipartFile("file", "recording.webm", "audio/webm", "fake audio".getBytes());
		
		int status = mockMvc.perform(multipart("/api/v1/transcriptions").file(audioFile)).andReturn().getResponse().getStatus();
	
		return status;
		
	}
	
	private TranscriptionResponse createFakeTranscription()
			throws InterruptedException {
		
		Thread.sleep(200);
		
		return new TranscriptionResponse("Test transcription");
		
		
	}
	
	@Test
	void BlockingRequestSucceeded() throws Exception {
		when(speechToTextService.transcribe(any())).thenAnswer(request -> createFakeTranscription());
		
		int status = sendOneRequest();
		
		assertEquals(200, status);		
		
	}
	
	@Test 
	void oneVirtualThreadRequest()
		throws InterruptedException {
		
	when(speechToTextService.transcribe(any())).thenAnswer(request -> createFakeTranscription());
	
	int[] result = new int[1];
	
	Thread thread = Thread.ofVirtual().start(() -> {
		try {
			result[0] = sendOneRequest();
		} catch (Exception e) {
			result[0] = 0;
		}
	});
	
	thread.join();
	
	assertEquals(200, result[0]);
}
	
	@Test
	void handles201Requests()
		 throws InterruptedException {
		
		when(speechToTextService.transcribe(any())).thenAnswer(request -> createFakeTranscription());
		
		int requestCount = 201;
		
		int[] results = new int[requestCount];
		Thread[] threads = new Thread[requestCount];
		
		for (int i = 0; i < requestCount; i++) {
			int requestNumber = i;
			
			threads[i] = Thread.ofVirtual().unstarted(() -> {
				try {
					results[requestNumber] = sendOneRequest();
				} catch (Exception e) {
					results[requestNumber] = 0;
				}
			});
		}
		
		long startTime = System.currentTimeMillis();
		
		for (Thread thread : threads) {
			thread.start();
		}
		
		for (Thread thread : threads) {
			thread.join();
		}
		
		long endTime = System.currentTimeMillis();
		long duration = endTime - startTime;
		
		logger.info("Completed {} blocking requests in {} milliseconds", requestCount, duration);
		
		for (int status : results) {
			assertEquals(200, status);
		}
		
		assertTrue(duration < 5000);
	}
}
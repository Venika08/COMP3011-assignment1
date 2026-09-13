package comp3011.assignment1.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import java.time.Instant;
import static org.mockito.Mockito.doThrow;
import comp3011.assignment1.exception.ShutdownException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import comp3011.assignment1.service.ShutdownService;

import comp3011.assignment1.dto.UptimeResponse;
import comp3011.assignment1.service.ServerUptimeService;

@WebMvcTest(AdminController.class)
class AdminControllerTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockitoBean
	private ServerUptimeService serverUptimeService;
	@MockitoBean
	private ShutdownService shutdownService;
	
	@Test
	void getServerUptimeReturnsExpectedJson() throws Exception {
        Instant serverStart = Instant.parse("2026-07-14T01:15:30Z");
        Instant currentTime = Instant.parse("2026-07-14T01:17:00.500Z");

        when(serverUptimeService.getUptime())
                .thenReturn(new UptimeResponse(serverStart, currentTime, 90.5));

        mockMvc.perform(get("/api/v1/admin/uptime"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(
                        MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.utcServerStart")
                        .value("2026-07-14T01:15:30Z"))
                .andExpect(jsonPath("$.utcNow")
                        .value("2026-07-14T01:17:00.500Z"))
                .andExpect(jsonPath("$.serverUptimeSeconds").value(90.5));
	}
	
	@Test 
	void shutdownReturnsAccepted() throws Exception {
		mockMvc.perform(post("/api/v1/admin/shutdown"))
			   .andExpect(status().isAccepted())
			   .andExpect(jsonPath("$.message")
					   .value("Graceful shutdown requested."));
	}
	
	@Test
	void ShutdownReturnsConflict() throws Exception {
		doThrow(new ShutdownException())
			.when(shutdownService)
			.requestShutdown();
		
		mockMvc.perform(post("/api/v1/admin/shutdown"))
			   .andExpect(status().isConflict())
			   .andExpect(jsonPath("$.status").value(409))
			   .andExpect(jsonPath("$.error").value("Conflict"))
			   .andExpect(jsonPath("$.message")
					   .value("Graceful shutdown is already in progress."))
			   .andExpect(jsonPath("$.path")
					   .value("/api/v1/admin/shutdown"));
	}
	
	}

	




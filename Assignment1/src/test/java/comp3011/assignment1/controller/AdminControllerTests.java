package comp3011.assignment1.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import comp3011.assignment1.dto.UptimeResponse;
import comp3011.assignment1.service.ServerUptimeService;

@WebMvcTest(AdminController.class)
class AdminControllerTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockitoBean
	private ServerUptimeService serverUptimeService;
	
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
}
	




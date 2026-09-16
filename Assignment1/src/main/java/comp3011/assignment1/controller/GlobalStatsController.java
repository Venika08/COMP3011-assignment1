package comp3011.assignment1.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import comp3011.assignment1.dto.GlobalStatsResponse;
import comp3011.assignment1.service.GlobalStatsService;

/**
 * Global speech-to-text usage statistics.
 */
@RestController
@RequestMapping("/api/v1/global")
public class GlobalStatsController {

    private final GlobalStatsService globalStatsService;

    /**
     * Creates the controller with its required statistics service.
     *
     * @param globalStatsService service maintaining global token usage
     */
    public GlobalStatsController(GlobalStatsService globalStatsService) {
        this.globalStatsService = globalStatsService;
    }

    /**
     * Returns token usage 
     *
     * @return current global token statistics
     */
    @GetMapping(value = "/stats", produces = MediaType.APPLICATION_JSON_VALUE)
    public GlobalStatsResponse getGlobalStats() {
        return globalStatsService.getGlobalStats();
    }
}
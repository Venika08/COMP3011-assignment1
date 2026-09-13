package comp3011.assignment1.service;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

import comp3011.assignment1.dto.GlobalStatsResponse;

class GlobalStatsServiceTests {

	@Test
	void updates() throws InterruptedException {
		GlobalStatsService services = new GlobalStatsService();
		int numOfThreads = 20;
		int updatesPerThread = 5000;
		Thread[] threads = new Thread[numOfThreads];
		
		for (int i = 0; i < numOfThreads; i++) {
			threads[i] = new Thread(() -> {
				for (int j = 0; j < updatesPerThread; j++) {
					services.recordUsage(2, 1);
				}
			});
		
			threads[i].start();
		}
		
		for (Thread thread : threads) {
			thread.join();
		}
		
		GlobalStatsResponse result = services.getGlobalStats();
		
		assertEquals(200_000, result.inputTokens());
		assertEquals(100_000, result.outputTokens());
	}

}

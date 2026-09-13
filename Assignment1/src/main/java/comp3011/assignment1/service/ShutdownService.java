package comp3011.assignment1.service;

import java.util.concurrent.atomic.AtomicBoolean;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import comp3011.assignment1.exception.ShutdownException;


@Service
public class ShutdownService {
	
	private final ConfigurableApplicationContext applicationContext;
	private final AtomicBoolean shutdownRequested = new AtomicBoolean(false);
	
	
	public ShutdownService(
			ConfigurableApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
	
	public void requestShutdown() {
		if (!shutdownRequested.compareAndSet(false, true)) {
			throw new ShutdownException();
		}
		
		Thread shutdownThread = new Thread(() -> {
			try {
				Thread.sleep(500);
				applicationContext.close();
			} catch (InterruptedException exception) {
				Thread.currentThread().interrupt();
			}
		});
		
		shutdownThread.start();
	}
		
}

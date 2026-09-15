# TESTING 

This document includes regression tests for the REST API Controllers and services. 


# APPLICATION TESTS 
These tests check that the Spring Boot application context loads successfully. 

Expected result: The application starts without any configuration errors. 

Assurance: The application and its spring components are created successfully. 


# ADMIN CONTROLLER TESTS 
AdminControllerTests checks the uptime of the speech-To-Text and graceful shutdown API behaviour.

The endpoints should return the expected HTTP and JSON responses as the expected result. 

# GLOBAL STATISTICS CONTROLLER TEST
This ensures a prepared statistic response that is sent through the GlobalStatsController. Therefore, this will contain the expected input and output token totals. 

The assurance is that the controller correctly returns the statistics. 

# TRANSCRIPTION CONTROLLER TEST 
This uploads fake multipart audio file using a stubbed SpeechToText Service. 

As a result, the transcription text should be returned. The audio uploads should be accepted and transcription responses should be correct. 


# RACE CONDITION TEST 
GlobalStatsServiceTests starts 20 threads. Each thread records 5,000 token updates. 

-EXPECTED:

--Input token total: 200,000
--Output token total: 100,000


# CONCURRENCY TESTS 
The main concurrency test creates 201 virtual threads and sends those HTTP requests through the transcription controller. 

The test log should report the number of completed requests and duration. It should also complete in less than 5 seconds and return HTTP status 200. 

The controller can process more than 200 requests without crashing or causing any delays. 

# API TESTING
An actual API testing requires the OPEN_API_KEY environment variable and API credits. Due to these contraints, there is not transcription as of now. 
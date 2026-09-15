const startButton = document.getElementById("startButton");
const stopButton = document.getElementById("stopButton");
const transcriptionText = document.getElementById("transcription");
const statusText = document.getElementById("status");

let mediaRecorder;
let audioParts = [];

function handleDataInput(event) {
	audioParts.push(event.data);
}

async function uploadRecording() {
	statusText.textContent = "Transcribing...";
	
	const audioFile = new Blob(audioParts, {type: "audio/webm"});
	
	const formData = new FormData();
	formData.append("file", audioFile, "recording.webm");
	
	const response = await fetch("/api/v1/transcriptions", {method: "POST", body: formData});
	
	if (response.ok) {
		const result = await response.json();
		
		transcriptionText.textContent = result.text;
		statusText.textContent = "Transcription complete.";
	} else {
		statusText.textContent = "Transcription failed.";
	}
		
		
	}


async function startRecording() {
	try {
		const constraints = {audio: true};
	
		const stream = await navigator.mediaDevices.getUserMedia(constraints);
	
		mediaRecorder = new MediaRecorder(stream);
		audioParts = [];
		mediaRecorder.ondataavailable = handleDataInput;
		mediaRecorder.onstop = uploadRecording;
		mediaRecorder.start();
	
	
		startButton.disabled = true;
		stopButton.disabled = false;
		statusText.textContent = "Recording...";
	
	} catch (error) {
		startButton.disabled = false;
		stopButton.disabled = true;
		statusText.textContent = "Microphone access was denied.";
	
	}
}


	
function stopRecording() {
	mediaRecorder.stop();
	
	const tracks = mediaRecorder.stream.getTracks();
	
	for (let i = 0; i < tracks.length; i++) {
		tracks[i].stop();
	}
	
	startButton.disabled = false;
	stopButton.disabled = true;
	statusText.textContent = "Recording stopped.";
}

stopButton.addEventListener("click", stopRecording);
startButton.addEventListener("click", startRecording);


	
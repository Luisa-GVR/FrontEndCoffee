let pollInterval = null;
let currentSessionId = null;

async function initQrLogin() {
  if (pollInterval) {
    clearInterval(pollInterval);
    pollInterval = null;
  }

  // Clear old QR
  const qrContainer = document.getElementById("qr");
  qrContainer.innerHTML = "";

  // Update status
  document.querySelector(".status-text").innerText = "Generating new QR...";

  // Fetch new QR session
  const res = await fetch("/login/qr-link");
  const data = await res.json();

  currentSessionId = data.sessionId;

  new QRCode(qrContainer, {
    text: data.url,
    width: 280,
    height: 280
  });

  document.querySelector(".status-text").innerText = "Waiting for login";
  pollLogin(currentSessionId);
}

function pollLogin(sessionId) {
  pollInterval = setInterval(async () => {
    try {
      const res = await fetch(`/login/status?session=${sessionId}`);

      if (res.status === 200) {
        const user = await res.text();
        localStorage.setItem("user", user);

        clearInterval(pollInterval);
        pollInterval = null;

        window.location.href = "/coffee.html";
      }
    } catch (err) {
      console.error("Polling error:", err);
    }
  }, 2000);
}

document.addEventListener("DOMContentLoaded", () => {
  document
    .getElementById("refresh-qr")
    .addEventListener("click", () => {
      initQrLogin();
    });

  initQrLogin();
});

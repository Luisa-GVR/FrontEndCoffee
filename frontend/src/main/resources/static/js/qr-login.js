const messageEl = document.getElementById("message");


document.getElementById("loginForm").addEventListener("submit", async e => {
  e.preventDefault();

  const params = new URLSearchParams(window.location.search);
  const sessionId = params.get("session");

  const email = document.getElementById("email").value;
  const password = document.getElementById("password").value;

  const res = await fetch(`/login/qr-login?session=${sessionId}`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ email, password })
  });

  if (res.ok) {
    messageEl.innerText = "Login successful. You can return to your browser.";
    messageEl.classList.remove("error");
    messageEl.classList.add("success");
  } else {
    messageEl.innerText = "Invalid credentials.";
    messageEl.classList.remove("success");
    messageEl.classList.add("error");
  }
});

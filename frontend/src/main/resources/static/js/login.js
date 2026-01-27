async function initQrLogin() {
  const res = await fetch("/login/qr-link");
  const data = await res.json();


  new QRCode(document.getElementById("qr"), {
    text: data.url,
    width: 280,
    height: 280
  });

  pollLogin(data.sessionId);
}


async function pollLogin(sessionId) {
  const interval = setInterval(async () => {
    const res = await fetch(`/login/status?session=${sessionId}`);

    if (res.status === 200) {
      const user = await res.text();
      localStorage.setItem("user", user);
      clearInterval(interval);
      window.location.href = "/coffee.html";
    }
  }, 2000);
}

initQrLogin();
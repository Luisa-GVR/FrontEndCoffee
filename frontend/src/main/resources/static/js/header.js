function initHeader() {
    const logoutBtn = document.getElementById("logoutBtn");
    if (!logoutBtn) return;

    logoutBtn.addEventListener("click", async () => {
        await fetch("/logout", { method: "POST" });

        localStorage.removeItem("user");
        window.location.href = "/login.html";
    });
}

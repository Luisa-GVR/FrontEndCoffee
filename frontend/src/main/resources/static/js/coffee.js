const allCoffeesList = document.getElementById("all-coffees-list");
const sortSelect = document.getElementById("sortSelect");

const favoriteCoffeesList = document.getElementById("favorites-list");

async function fetchCoffees(sort = "name,asc") {
    const [sortBy, direction] = sort.split(",");
    const res = await fetch(`/coffees/popular?sortBy=${sortBy}&direction=${direction}`);
    const data = await res.json();

    // Time to make the cards
    allCoffeesList.innerHTML = "";
    data.content.forEach(coffee => {
        const card = document.createElement("div");
        card.className = "coffee-card";
        card.innerHTML = `
            <div class="coffee-img">
                <img src="${coffee.imageUrl}" alt="${coffee.name}">
            </div>
            <h3>${coffee.name}</h3>
            <p>$${coffee.price.toFixed(2)}</p>
            <button class="favorite-star active" data-id="${coffee.id}">★</button>

        `;
        allCoffeesList.appendChild(card);
    });

    
}

//Favorite coffee

async function fetchFavoriteCoffees() {
    const user = localStorage.getItem("user");
    if (!user) return;

    const res = await fetch(`/coffees/favorites?email=${encodeURIComponent(user)}`);
    const data = await res.json();

    favoriteCoffeesList.innerHTML = "";

    if (data.length === 0) {
        const message = document.createElement("p");
        message.className = "empty-message";
        message.innerText = "Seems no one is here, try adding a favorite coffee?";
        favoriteCoffeesList.appendChild(message);
        return;
    }
    
    data.forEach(coffee => {
        const card = document.createElement("div");
        card.className = "coffee-card";
        card.innerHTML = `
            <div class="coffee-img">
                <img src="${coffee.imageUrl}" alt="${coffee.name}">
            </div>    
            <h3>${coffee.name}</h3>
            <p>$${coffee.price.toFixed(2)}</p>
            <button class="favorite-star ${coffee.isFavorite ? "active" : ""}"
                data-id="${coffee.id}"
                aria-label="Toggle favorite">★</button>
        `;
        favoriteCoffeesList.appendChild(card);
    });
}


document.addEventListener("DOMContentLoaded", () => {
    fetchCoffees();
    fetchFavoriteCoffees();

    sortSelect.addEventListener("change", () => {
        fetchCoffees(sortSelect.value);
    });
});


document.addEventListener("click", async (e) => {
    if (!e.target.classList.contains("favorite-star")) return;

    const coffeeId = e.target.dataset.id;
    const user = localStorage.getItem("user");

    await fetch(`/coffees/${coffeeId}/favorite?email=${encodeURIComponent(user)}`, {
        method: "POST"
    });

    await fetchCoffees(sortSelect.value);
    await fetchFavoriteCoffees();
});
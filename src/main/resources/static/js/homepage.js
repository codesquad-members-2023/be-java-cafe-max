const fetchCritics = async () => {
    const response = await fetch("static/json-data/movieRecommendation.json");
    const data = await response.json();
    return data;
};

const createCard = (critic) => {
    const card = document.createElement("div");
    card.className = "col-md-4 mb-4";
    card.innerHTML = `
    <div class="card">
      <img src="${critic.image}" class="card-img-top" alt="${critic.title}">
      <div class="card-body">
        <h5 class="card-title">${critic.title}</h5>
        <p class="card-text">${critic.summary}</p>
        <p class="card-text">${getStarRating(critic.rating)}</p>
        <a href="#" class="btn btn-primary">Read Review</a>
      </div>
    </div>
  `;
    return card;
};

const getStarRating = (rating) => {
    const fullStars = Math.round(rating);
    const emptyStars = 5 - fullStars;
    const starRating = `
    <span class="text-warning">
      ${"★".repeat(fullStars)}
      ${"☆".repeat(emptyStars)}
    </span>
  `;
    return starRating;
};


const renderCritics = async () => {
    const critics = await fetchCritics();
    const container = document.getElementById("critics-container");
    critics.forEach((critic) => {
        const card = createCard(critic);
        container.appendChild(card);
    });
};

renderCritics();

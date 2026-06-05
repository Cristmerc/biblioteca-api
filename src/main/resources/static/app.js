const API_URL = "https://onrender.com";
const API_STATS_URL = "https://onrender.com/estadisticas";

// Load books (can receive an optional author filter)
async function loadBooks(authorFilter = "") {
    let url = API_URL;

    // If there is a filter, we add it as a Query Parameter (?autor=...)
    if (authorFilter.trim() !== "") {
        url += `?autor=${encodeURIComponent(authorFilter)}`;
    }

    const response = await fetch(url);
    const books = await response.json();

    const list = document.getElementById("listaLibros");
    list.innerHTML = "";

    if (books.length === 0) {
        list.innerHTML = `<p class="no-resultados">No se encontraron libros para este autor.</p>`;
        return;
    }

    books.forEach(book => {
        list.innerHTML += `
            <div class="libro">
                <h3>${book.titulo}</h3>
                <p><strong>Autor:</strong> ${book.autor}</p>
                <p><strong>Año:</strong> ${book.anio}</p>

                <button class="btn-eliminar"
                        onclick="deleteBook(${book.id})">
                    Eliminar
                </button>
            </div>
        `;
    });
}

// Function that runs every time the user types in the search input
function searchBooks() {
    const searchText = document.getElementById("buscarAutor").value;
    loadBooks(searchText);
}

async function loadStatistics() {
    try {
        const response = await fetch(API_STATS_URL);
        if (!response.ok) throw new Error("Error en estadísticas");

        const data = await response.json();

        document.getElementById("stat-total").textContent = data.totalBooks;
        document.getElementById("stat-promedio").textContent = data.averageYear;
        document.getElementById("stat-autor").textContent = data.mostPopularAuthor;

    } catch (error) {
        console.error("No se pudieron cargar las estadísticas:", error);
    }
}

async function addBook() {
    const title = document.getElementById("titulo").value;
    const author = document.getElementById("autor").value;
    const year = document.getElementById("anio").value;

    if (!title || !author || !year) return alert("Por favor llena todos los campos");

    await fetch(API_URL, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ titulo: title, autor: author, anio: year })
    });

    document.getElementById("titulo").value = "";
    document.getElementById("autor").value = "";
    document.getElementById("anio").value = "";
    document.getElementById("buscarAutor").value = ""; // Clear search input when adding

    await loadBooks();
    await loadStatistics();
}

async function deleteBook(id) {
    if (!confirm("¿Seguro que deseas eliminar este libro?")) return;

    await fetch(`${API_URL}/${id}`, {
        method: "DELETE"
    });

    const searchText = document.getElementById("buscarAutor").value;
    await loadBooks(searchText); // Maintains the current filter if it existed
    await loadStatistics();
}

// Initialize the application by loading everything for the first time
async function initialize() {
    await loadBooks();
    await loadStatistics();
}

initialize();

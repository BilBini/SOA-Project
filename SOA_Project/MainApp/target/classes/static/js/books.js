document.addEventListener('DOMContentLoaded', function() {
    // Handle form submission
    const form = document.getElementById('bookForm');
    form.addEventListener('submit', function(e) {
        e.preventDefault();
        const formData = new FormData(form);
        const book = {
            title: formData.get('title'),
            authorId: parseInt(formData.get('authorId')),
            isbn: formData.get('isbn'),
            publicationYear: parseInt(formData.get('publicationYear')),
            genre: formData.get('genre'),
            description: formData.get('description')
        };

        fetch('http://localhost:8082/api/books', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            credentials: 'include',
            body: JSON.stringify(book)
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            form.reset();
            loadBooks();
            alert('Book created successfully!');
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Failed to create book. Please make sure the BooksAPI is running on port 8082.');
        });
    });

    // Handle delete button clicks
    document.addEventListener('click', function(e) {
        if (e.target.classList.contains('delete-book')) {
            const bookId = e.target.dataset.bookId;
            if (confirm('Are you sure you want to delete this book?')) {
                fetch(`http://localhost:8082/api/books/${bookId}`, {
                    method: 'DELETE',
                    credentials: 'include'
                })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    loadBooks();
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert('Failed to delete book. Please make sure the BooksAPI is running on port 8082.');
                });
            }
        }
    });

    // Load authors for the dropdown
    function loadAuthors() {
        fetch('http://localhost:8081/api/authors', {
            credentials: 'include',
            headers: {
                'Accept': 'application/json'
            }
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(authors => {
            const select = document.getElementById('authorId');
            select.innerHTML = '<option value="">Select Author</option>';
            authors.forEach(author => {
                const option = document.createElement('option');
                option.value = author.id;
                option.textContent = author.name;
                select.appendChild(option);
            });
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Failed to load authors. Please make sure the AuthorsAPI is running on port 8081.');
        });
    }

    // Load books
    function loadBooks() {
        fetch('http://localhost:8082/api/books', {
            credentials: 'include',
            headers: {
                'Accept': 'application/json'
            }
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(books => {
            const tbody = document.querySelector('#booksTable tbody');
            tbody.innerHTML = '';
            books.forEach(book => {
                tbody.innerHTML += `
                    <tr>
                        <td>${book.title}</td>
                        <td>${book.isbn}</td>
                        <td>${book.genre}</td>
                        <td>${book.publicationYear || ''}</td>
                        <td>
                            <button class="btn btn-sm btn-danger delete-book" data-book-id="${book.id}">Delete</button>
                        </td>
                    </tr>
                `;
            });
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Failed to load books. Please make sure the BooksAPI is running on port 8082.');
        });
    }

    // Initial load of authors and books
    loadAuthors();
    loadBooks();

    // Subscribe to author changes
    window.addEventListener('storage', function(e) {
        if (e.key === 'authors-updated') {
            loadAuthors();
        }
    });
});
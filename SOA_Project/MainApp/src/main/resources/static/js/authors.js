document.addEventListener('DOMContentLoaded', function() {
    // Handle form submission
    const form = document.getElementById('authorForm');
    form.addEventListener('submit', function(e) {
        e.preventDefault();
        const formData = new FormData(form);
        const author = {
            name: formData.get('name'),
            email: formData.get('email'),
            bio: formData.get('bio')
        };

        fetch('http://localhost:8081/api/authors', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            credentials: 'include',
            body: JSON.stringify(author)
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            form.reset();
            loadAuthors();
            // Notify other pages about the author update
            localStorage.setItem('authors-updated', Date.now().toString());
            alert('Author created successfully!');
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Failed to create author. Please make sure the AuthorsAPI is running on port 8081.');
        });
    });

    // Handle delete button clicks
    document.addEventListener('click', function(e) {
        if (e.target.classList.contains('delete-author')) {
            const authorId = e.target.dataset.authorId;
            if (confirm('Are you sure you want to delete this author?')) {
                fetch(`http://localhost:8081/api/authors/${authorId}`, {
                    method: 'DELETE',
                    credentials: 'include'
                })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    loadAuthors();
                    // Notify other pages about the author update
                    localStorage.setItem('authors-updated', Date.now().toString());
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert('Failed to delete author. Please make sure the AuthorsAPI is running on port 8081.');
                });
            }
        }
    });

    // Load authors
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
            const tbody = document.querySelector('#authorsTable tbody');
            tbody.innerHTML = '';
            authors.forEach(author => {
                tbody.innerHTML += `
                    <tr>
                        <td>${author.name}</td>
                        <td>${author.email}</td>
                        <td>${author.bio || ''}</td>
                        <td>
                            <button class="btn btn-sm btn-danger delete-author" data-author-id="${author.id}">Delete</button>
                        </td>
                    </tr>
                `;
            });
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Failed to load authors. Please make sure the AuthorsAPI is running on port 8081.');
        });
    }

    // Initial load of authors
    loadAuthors();
});
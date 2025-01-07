// Common utility functions
function handleResponse(response) {
    if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
    }
    return response.json();
}

function showError(message) {
    alert('Error: ' + message);
}

function ensureDataDirectory() {
    fetch('/api/system/ensure-data-directory', { method: 'POST' })
        .then(response => {
            if (!response.ok) {
                console.error('Failed to create data directory');
            }
        })
        .catch(error => console.error('Error:', error));
}
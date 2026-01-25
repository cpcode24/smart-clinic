document.getElementById('patientRegistrationForm').addEventListener('submit', function (event) {
    event.preventDefault(); // Prevent the default form submission

    const formData = new FormData(event.target);
    const jsonData = {};
    formData.forEach((value, key) => {
        jsonData[key] = value;
    });

    fetch('/patient/register', { // Your Spring API endpoint
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(jsonData), // Send the data as a JSON string
    })
    .then(response => {
        if (response.ok) {
            console.log('Form submitted successfully!');
            // Handle success (e.g., show a confirmation message, redirect)
        } else {
            console.error('Form submission failed.');
            // Handle errors
        }
    })
    .catch(error => {
        console.error('Error:', error);
    });
});
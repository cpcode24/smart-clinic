// Client-side validation and AJAX submission for doctor registration

(() => {
  const form = document.getElementById('doctorRegistrationForm');
  const msg = document.getElementById('formMessage');

  function showMessage(text, type = 'error') {
    msg.innerHTML = `<div class="${type === 'error' ? 'error-message' : 'success-message'}">${text}</div>`;
  }

  function validate(values) {
    const emailRe = /^[^@\s]+@[^@\s]+\.[^@\s]+$/;
    if (!values.name || values.name.length < 3) return 'Name must be at least 3 characters';
    if (!emailRe.test(values.email)) return 'Invalid email address';
    if (!/^[0-9]{10,15}$/.test(values.phone.replace(/[\s+-]/g, ''))) return 'Phone must be 10-15 digits';
    if (!values.specialty || values.specialty.length < 3) return 'Specialty is required';
    if (!values.password || values.password.length < 6) return 'Password must be at least 6 characters';
    return null;
  }

  async function postDoctor(data) {
    const payload = {
      name: data.name,
      email: data.email,
      phone: data.phone,
      specialty: data.specialty,
      password: data.password
    };

    try {
      const res = await fetch('/doctor/register-doctor', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
      });

      if (!res.ok) {
        const text = await res.text();
        showMessage('Registration failed: ' + text, 'error');
        return;
      }

      const json = await res.json();
      showMessage('Doctor registered successfully', 'success');
      // Optionally redirect to login
      setTimeout(() => { window.location.href = '/doctor/login'; }, 1200);
    } catch (err) {
      showMessage('Network error: ' + err.message, 'error');
    }
  }

  if (form) {
    form.addEventListener('submit', (e) => {
      e.preventDefault();
      msg.innerHTML = '';
      const values = {
        name: form.name.value.trim(),
        email: form.email.value.trim(),
        phone: form.phone.value.trim(),
        specialty: form.specialty ? form.specialty.value.trim() : form.specialty.value.trim(),
        password: form.password.value
      };

      const validationError = validate(values);
      if (validationError) {
        showMessage(validationError, 'error');
        return;
      }

      postDoctor(values);
    });
  }
})();

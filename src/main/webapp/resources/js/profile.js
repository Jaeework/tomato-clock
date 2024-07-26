window.onload = function() {

    // Event listener for Email Change Modal opening button
    document.getElementById('showEmailChangeModalBtn').addEventListener('click', showEmailChangeModal);


    // Event listener for Password Change Modal opening button
    document.getElementById('showPasswordChangeModalBtn').addEventListener('click', showPasswordChangeModal);


    // Event listener for save changes button
    document.getElementById('saveChangesButton').addEventListener('click', function () {
        const csrfHeaderName = document.querySelector('meta[name="_csrf_header"]').content;
        const csrfTokenValue = document.querySelector('meta[name="_csrf"]').content;

        if (document.getElementById('emailChangeFields').style.display !== 'none') {
            const newEmail = document.getElementById('newEmail').value;
            const currentPassword = document.getElementById('currentPassword').value;
            const params = {
                email : newEmail,
                currentPassword : currentPassword,
            }

            fetch('/api/profile/update/email', {
                method: 'POST',
                headers: {
                    'X-Requested-With': 'XMLHttpRequest',
                    'Content-Type': 'application/json',
                    [csrfHeaderName]: csrfTokenValue
                },
                credentials: 'same-origin',
                body: JSON.stringify(params)
            })
                .then(response => response.text())
                .then(data => {
                    alert(data);
                    location.reload(); // Reload the page to see the changes
                })
                .catch(error => console.error('Error:', error));
        }

        if (document.getElementById('passwordChangeFields').style.display !== 'none') {
            const currentPassword = document.getElementById('currentPassword').value;
            const newPassword = document.getElementById('newPassword').value;
            const confirmPassword = document.getElementById('confirmPassword').value;

            const params = {
                currentPassword: currentPassword,
                newPassword: newPassword,
                confirmPassword: confirmPassword
            }

            fetch('/api/profile/update/password', {
                method: 'POST',
                headers: {
                    'X-Requested-With': 'XMLHttpRequest',
                    'Content-Type': 'application/json',
                    [csrfHeaderName]: csrfTokenValue
                },
                credentials: 'same-origin',
                body: JSON.stringify(params)
            })
                .then(response => response.text())
                .then(data => {
                    alert(data);
                    location.reload(); // Reload the page to see the changes
                })
                .catch(error => console.error('Error:', error));
        }
    });

    function showEmailChangeModal() {
        document.getElementById('modalLabel').innerText = 'Change Email';
        document.getElementById('emailChangeFields').style.display = 'block';
        document.getElementById('passwordChangeFields').style.display = 'none';
    }

    function showPasswordChangeModal() {
        document.getElementById('modalLabel').innerText = 'Change Password';
        document.getElementById('emailChangeFields').style.display = 'none';
        document.getElementById('passwordChangeFields').style.display = 'block';
    }
}

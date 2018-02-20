function validate(input) {
    if (input.value !== document.getElementById("password").value) {
        input.setCustomValidity("Passwords don't match");
    } else {
        input.setCustomValidity("");
    }
}
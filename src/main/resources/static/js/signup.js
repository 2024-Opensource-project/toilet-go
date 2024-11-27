const passwordField = document.getElementById('password');
const confirmPasswordField = document.getElementById('confirmPassword');
const passwordMatchMessage = document.getElementById('passwordMatchMessage');
const strengthMessage = document.getElementById('strengthMessage');
const progressBar = document.getElementById('progressBar');
const userIdField = document.getElementById('userId');
const nameField = document.getElementById('name');
const emailField = document.getElementById('email');
const phoneNumberField = document.getElementById('phoneNumber');

// 비밀번호와 비밀번호 확인을 실시간으로 비교
function validatePasswordMatch() {
    if (confirmPasswordField.value === "") {
        passwordMatchMessage.textContent = "";
        passwordMatchMessage.className = "password-check empty"; // 비밀번호 확인란이 비어있을 때
    } else if (passwordField.value === confirmPasswordField.value) {
        passwordMatchMessage.textContent = "Passwords match.";
        passwordMatchMessage.className = "password-check match"; // 비밀번호 일치
    } else {
        passwordMatchMessage.textContent = "Passwords do not match.";
        passwordMatchMessage.className = "password-check mismatch"; // 비밀번호 불일치
    }
}

// 비밀번호 확인란 변경 시 실시간으로 비교
confirmPasswordField.addEventListener('input', validatePasswordMatch);

// 비밀번호 입력란 변경 시 비밀번호 강도 확인
passwordField.addEventListener('input', () => {
    const password = passwordField.value;
    const strength = checkPasswordStrength(password);

    // 강도에 따른 메시지 변경
    strengthMessage.textContent = `Password strength: ${strength.text}`;
    strengthMessage.className = `strength-check ${strength.level}`;

    // 비밀번호 확인을 다시 확인
    validatePasswordMatch();

    // 게이지 바 업데이트
    updateProgressBar();
});

// 비밀번호 강도 체크 함수
function checkPasswordStrength(password) {
    const lengthCheck = password.length >= 8;
    const uppercaseCheck = /[A-Z]/.test(password);
    const lowercaseCheck = /[a-z]/.test(password);
    const numberCheck = /\d/.test(password);
    const specialCharCheck = /[!@#$%^&*(),.?":{}|<>]/.test(password);

    let strength = {
        level: 'weak',
        text: 'Very Weak'
    };

    if (lengthCheck && uppercaseCheck && lowercaseCheck && numberCheck && specialCharCheck) {
        strength = { level: 'strong', text: 'Strong' };
    } else if (lengthCheck && (uppercaseCheck || lowercaseCheck) && (numberCheck || specialCharCheck)) {
        strength = { level: 'medium', text: 'Medium' };
    }

    return strength;
}

// 실시간으로 진행 상태에 따라 게이지 바를 업데이트하는 함수
function updateProgressBar() {
    let progress = 0;

    // 필드가 비어있지 않으면 진행 상태가 증가
    if (userIdField.value) progress += 10;
    if (passwordField.value) progress += 20;
    if (confirmPasswordField.value) progress += 10;
    if (nameField.value) progress += 10;
    if (emailField.value) progress += 20;
    if (phoneNumberField.value) progress += 10;

    // 비밀번호 강도도 고려하여 진행 상태 추가
    const strength = checkPasswordStrength(passwordField.value);
    if (strength.level === 'strong') {
        progress += 20;
    } else if (strength.level === 'medium') {
        progress += 10;
    }

    // 비밀번호 확인이 일치하지 않으면 100%로 채워지지 않도록 설정
    if (passwordField.value !== confirmPasswordField.value) {
        progress -= 10;  // 비밀번호 확인이 일치하지 않으면 진행 상태를 10% 낮춤
    }

    progressBar.value = progress;
}

// 폼이 로드될 때 진행 상태 업데이트
document.addEventListener('input', updateProgressBar);

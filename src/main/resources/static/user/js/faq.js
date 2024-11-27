const messageElement = document.getElementById("messages");
const successMessage = messageElement.dataset.successMessage;
const errorMessage = messageElement.dataset.errorMessage;

if (successMessage) {
    alert(successMessage); // 성공 팝업 표시
}

if (errorMessage) {
    alert(errorMessage); // 에러 팝업 표시
}

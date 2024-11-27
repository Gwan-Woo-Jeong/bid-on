const messageElement = document.getElementById("messages");
const successMessage = messageElement.dataset.successMessage;
const errorMessage = messageElement.dataset.errorMessage;

if (successMessage) {
    alert(successMessage); // 성공 팝업 표시
}

if (errorMessage) {
    alert(errorMessage); // 에러 팝업 표시
}


async function submitQuestion() {
            const email = document.getElementById("email").value;
            const title = document.getElementById("title").value;
            const contents = document.getElementById("contents").value;

            const response = await fetch('/questions/submit', {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: `email=${encodeURIComponent(email)}&title=${encodeURIComponent(title)}&contents=${encodeURIComponent(contents)}`
            });

            const result = await response.json();
            alert(result.message);

            if (result.success) {
                // 성공 시 입력 값 초기화
                document.getElementById("email").value = '';
                document.getElementById("title").value = '';
                document.getElementById("contents").value = '';
            }
        }
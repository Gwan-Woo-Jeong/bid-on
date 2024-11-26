/*function isValidCardNumber(cardNumber) {
	var sum = 0;
	var shouldDouble = false;
	
	for (var i = cardNumber.length -1; i >=0; i--) {
		var digit = parseInt(cardNumber.charAt(i));
		
		if (shouldDouble) {
			digit *= 2;
			if (digit > 9) {
				digit -= 9;
			}
		}
		sum += digit;
		shouldDouble = !shouldDouble;
	}
	return (sum % 10 ===0 );
}

document.getElementById("cardNumber").addEventListener("input", function() {
	var cardNumber = document.getElementById("cardNumber").value;
	
	//카드 번호에서 숫자만 추출(하이픈 제외한 숫자만)
	var cleanCardNumber = cardNumber.replace(/\D/g,'');
	
	//4자리마다 하이픈 자동 추가
	var formattedCardNumber = cleanCardNumber.replace(/(\d{4})(?=\d)/g, '$1-');
	
	//입력값 필드에 재설정
	document.getElementById("cardNumber").value = formattedCardNumber;
	
	//카드 16자리 제한(하이픈제외)
	var cardNumberRegex = /^\d{16}$/;
	var cardNumberChecker = document.getElementById("CardNumberChecker");
	
	if (cleanCardNumber.length === 16) {
		cardNumberChecker.innerHTML = '<i class="fa-regular fa-circle-chck"></i>';
		cardNumberChecker.style.color = "green";
	} else {
		cardNumberChecker.innerHTML = '';
	}
});

document.getElementById("cvv").addEventListener("input", function() {
	var cvv = document.getElementById("cvv").value;
	var cvvChecker = document.getElementById("cvvChecker");
	
	//cvv 3자리
	var cvvRegex = /^\d{3}$/;
	if (cvv.length === 3) {
		cvvChecker.innerHTML = '<i class="fa-regular fa-circle-check"></i>';
		cvvChecker.style.color = "green";
	} else {
		cvvChecker.innerHTML = '';
	}
	
});*/
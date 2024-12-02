const urlParams = new URLSearchParams(window.location.search);
const itemId = urlParams.get('itemId');
const winningBidId = urlParams.get('winningBidId');
const auctionType = urlParams.get('auctionType');

$('#submitBtn').click((e) => {
    e.preventDefault();

    const form = $('#checkoutForm')[0];

    const formData = new FormData(form);

    const checkoutData = {
        firstName: formData.get('firstName'),
        lastName: formData.get('lastName'),
        email: formData.get('email'),
        tel: formData.get('phone'),
        address: formData.get('address1'),
        detailAddress: formData.get('address2'),
        zipcode: formData.get('zipCode'),
        district: formData.get('district'),
        city: formData.get('city'),
        country: formData.get('country'),
        deliveryMethod: Number(formData.get('deliveryMethod'))
    };

    const fieldTitle = {
        firstName: '이름',
        lastName: '성',
        email: 'Email',
        tel: '전화번호',
        address: '주소1',
        detailAddress: '주소2',
        zipCode: '우편번호',
        district: '구',
        city: '도시',
        country: '국가',
        deliveryMethod: '배송 방법'
    };

    for (const [key, value] of Object.entries(checkoutData)) {
        if (!value || typeof value === 'string' && value.trim() === '') {
            alert(`${fieldTitle[key]}을 입력하세요.`);
            return;
        }
    }

    localStorage.setItem('checkoutData', JSON.stringify(checkoutData));
    window.location.href = '/auction/payment?itemId=' + itemId + '&winningBidId=' + winningBidId + '&auctionType=' + auctionType;
});


const cityDistrict = {
    "서울": ["강남", "강북", "강서", "강동", "광진", "동대문", "중구", "용산", "마포", "서대문", "성북", "성동", "송파", "양천", "영등포", "은평", "종로", "노원", "중랑", "도봉", "구로"],
    "부산": ["부산진구", "동구", "중구", "서구", "남구", "북구", "해운대", "사하", "금정", "연제", "동래", "수영", "기장"],
    "인천": ["부평", "동", "중", "남구", "남동", "서구", "연수", "강화", "옹진"],
    "경기": ["수원", "성남", "부천", "안산", "안양", "고양", "용인", "파주", "의정부", "광명", "광주", "김포", "동두천", "이천", "평택", "하남", "포천", "양주", "여주", "오산", "구리", "양평", "가평", "연천"],
    "대구": ["중구", "서구", "북구", "동구", "남구", "수성", "달서", "달성"],
    "광주": ["북구", "동구", "광산구", "중구", "남구", "서구"],
    "대전": ["동구", "중구", "서구", "유성구", "대덕구"],
    "울산": ["북구", "동구", "중구", "남구", "울주"],
    "강원": ["춘천", "원주", "강릉", "동해", "삼척", "속초", "태백", "정선", "평창", "홍천", "화천", "횡성", "영월", "인제", "고성", "양구", "양양"],
    "충북": ["청주", "충주", "제천", "음성", "진천", "옥천", "영동", "단양", "증평", "보은"],
    "충남": ["아산", "보령", "천안", "공주", "논산", "서산", "당진", "홍성", "예산", "서천", "부여", "청양", "태안", "계룡"],
    "전북": ["전주", "산안", "익산", "정읍", "남원", "김제", "완주", "순창", "임실", "장수", "진안", "무주", "부안", "고창"],
    "전남": ["목포", "순천", "여수", "나주", "광양", "담양", "곡성", "구례", "고흥", "보성", "화순", "장흥", "강진", "해남", "영암", "무안", "함평", "영광", "장성", "완도", "진도", "신안"],
    "경북": ["포항", "경주", "김천", "안동", "구미", "영주", "영천", "상주", "문경", "경산", "위", "의성", "청송", "영양", "영덕", "청도", "고령", "성주", "칠곡", "예천", "봉화", "울진", "울릉"],
    "경남": ["창원", "김해", "진주", "양산", "거제", "통영", "사천", "밀양", "함안", "창녕", "고성", "남해", "하동", "산청", "함양", "거창", "합천"],
    "제주": ["제주", "서귀포"]
};

const districts = Object.keys(cityDistrict);

const cityInput = $('select[name="city"]');
const districtInput = $('select[name="district"]');

districts.forEach(city => {
    const option = $('<option>').text(city).val(city);
    cityInput.append(option);
});

cityInput.val("서울");
districtInput.val("강남");

cityInput.on('change', (e) => {
    const city = e.target.value;
    const districts = cityDistrict[city];
    districtInput.html('');

    if (districts) {
        districts.forEach(district => {
            const option = $('<option>').text(district).val(district);
            districtInput.append(option);
        });
    }
});

//대화명 설정 + 서버 연결
const url = 'ws://localhost:8090/live-bid';
let ws;

const urlParams = new URLSearchParams(window.location.search);
const itemId = urlParams.get('itemId');
const bidButton = $('.bid-button');

// TODO: 로그인 체크
// TODO: 경매 시간 체크
if (!itemId) {
    alertErrorAndClose("잘못된 접근입니다.");
} else if (!itemInfo) {
    alertErrorAndClose("경매 물품이 존재하지 않습니다.");
}

function connect(user) {
    this.userId = user.id;

    ws = new WebSocket(url);
    log('서버에게 연결을 시도합니다.');

    ws.onopen = evt => {
        log('서버와 연결되었습니다.');

        const message = {
            roomId: itemId,
            type: 'ENTER',
            senderId: this.userId,
            text: '',
            payload: user,
            bidPrice: null,
            createTime: dayjs().format('YYYY-MM-DD HH:mm:ss')
        }

        ws.send(JSON.stringify(message));
    };

    ws.onmessage = evt => {
        log('메시지를 수신했습니다.');

        console.log(JSON.parse(evt.data));
        const {type, text, payload, createTime} = JSON.parse(evt.data);

        if (type === 'TALK') {
            printChat(payload.name, payload.profile, text, 'left', createTime);
        } else if (type === 'PARTS') {
            clearUsers();
            payload.forEach(user => {
                printUsers(user.profile, user.name, user.email);
            });
            printUserCount(payload.length)
        } else if (type === 'ALERT') {
            printAlert(text);
        } else if (type === "BID-START") {
            const minBidPrice = setMinBidPrice(payload.highestBidPrice || itemInfo.startPrice);
            if (minBidPrice) {
                bidButton.removeAttr('disabled');
            }
        } else if (type === "BID-OK") {
            setMinBidPrice(payload.highestBidPrice);
        } else if (type === "BID-FAIL") {
            alert(text);
            setMinBidPrice(payload.highestBidPrice);
        }
    };


    window.onbeforeunload = function () {
        disconnect();
    };

    ws.onerror = evt => {
        log('에러가 발생했습니다. ' + evt);
    };
}

function disconnect() {
    const message = {
        roomId: itemId,
        type: 'LEAVE',
        senderId: this.userId,
        text: '',
        payload: null,
        bidPrice: null,
        createTime: dayjs().format('YYYY-MM-DD HH:mm:ss')
    }

    ws.send(JSON.stringify(message));

    log('서버와 연결이 종료되었습니다.');

    ws.close();
}


function clearUsers() {
    $('.chat-users').empty();
}

function printUsers(profileImgName, name, email) {
    const temp = `
                <div class="user">
                    <div class="avatar">
                        <img src="/uploads/profiles/${profileImgName}" alt="User name">
                    </div>
                    <div class="user-info">
                        <div class="name">${name}</div>
                        <div class="mood">${email}</div>
                    </div>
                </div>
                `;

    $('.chat-users').append(temp);
}

function printUserCount(count) {
    $('.user-count').text(count);
}

function printChat(name, profileImgName, text, side, time) {
    const temp = `
                <div class="answer ${side}">
                    <div class="avatar">
                        <img src="/uploads/profiles/${profileImgName}" alt="User name">
                    </div>
                    <div class="name">${name}</div>
                    <div class="text">
                        ${text}
                    </div>
                    <div class="time">${showTime(time)}</div>
                </div>
                `;

    $('.chat-body').append(temp);

    scrollList();
}

function printAlert(text) {
    const temp = `
                <div class="answer center">
                    <div class="avatar">
                        <img src="/user/images/sample/auctioneer-bot.jpg" alt="User name">
                    </div>
                    <div class="name">경매사 봇</div>
                    <div class="text">${text}</div>
                </div>
                `;

    $('.chat-body').append(temp);

    scrollList();
}

function log(msg) {
    console.log(`[${new Date().toLocaleTimeString()}] ${msg}`);
}

function scrollList() {
    const chatList = $('#chat-list');
    chatList.scrollTop(chatList[0].scrollHeight + 1000);
}

function showTime(date) {
    const dayDate = dayjs(date);

    if (dayDate.isToday()) {
        return `Today at ${dayDate.format('HH:mm')}`;
    } else if (dayDate.isYesterday()) {
        return `Yesterday at ${dayDate.format('HH:mm')}`;
    } else if (dayDate.isSame(dayjs(), 'year')) {
        return 'MM-DD HH:mm:ss';
    } else {
        return dayDate.format('YY-MM-DD HH:mm');
    }
}

function setMinBidPrice(highestBidPrice) {
    const minBidPriceUnit = getMinBidUnit(highestBidPrice);
    itemInfo.minBidPrice = highestBidPrice + minBidPriceUnit;
    bidButton.text(itemInfo.minBidPrice + "원 입찰");
    return itemInfo.minBidPrice;
}

function sendMessage() {

}

function alertErrorAndClose(message) {
    alert('ERROR:' + message);
    window.close();
}

//TODO: 메시징 함수
bidButton.click(e => {
    e.preventDefault();

    const message = {
        roomId: itemId,
        type: 'BID',
        senderId: this.userId,
        text: '',
        payload: null,
        bidPrice: itemInfo.minBidPrice,
        createTime: dayjs().format('YYYY-MM-DD HH:mm:ss')
    }

    ws.send(JSON.stringify(message));
});

$('.quit-button').click(e => {
    e.preventDefault();
    if (confirm('정말로 나가시겠습니까?')) {
        window.close();
    }
});

$('#message-input').keydown(evt => {
    if (evt.keyCode === 13) {
        const message = {
            roomId: itemId,
            type: 'TALK',
            senderId: this.userId,
            text: $(evt.target).val(),
            payload: null,
            bidPrice: null,
            createTime: dayjs().format('YYYY-MM-DD HH:mm:ss')
        }

        ws.send(JSON.stringify(message));

        $(evt.target).val('');

        printChat(myInfo.name, myInfo.profile, message.text, 'right', message.createTime);
    }
});




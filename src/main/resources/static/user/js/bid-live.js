//대화명 설정 + 서버 연결
const url = 'ws://localhost:8090/live-bid';
let ws;

const urlParams = new URLSearchParams(window.location.search);
const itemId = urlParams.get('itemId');

if (itemId === null) {
    alert('ERROR: 잘못된 접근입니다. (물품번호가 존재하지 않음)');
    window.close();
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
            createTime: dayjs().format('YYYY-MM-DD HH:mm:ss')
        }

        ws.send(JSON.stringify(message));
    };

    ws.onmessage = evt => {
        log('메시지를 수신했습니다.');

        const message = JSON.parse(evt.data);

        console.log(message);

        if (message.type === 'TALK') {
            const talkUser = message.payload;
            printChat(talkUser.name, talkUser.profile, message.text, 'left', message.createTime);
        } else if (message.type === 'PARTS') {
            const users = message.payload;
            clearUsers();
            users.forEach(user => {
                printUsers(user.profile, user.name, user.email);
            });
            printUserCount(users.length)
        } else if (message.type === 'ALERT') {
            printAlert(message.text);
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
            createTime: dayjs().format('YYYY-MM-DD HH:mm:ss')
        }

        ws.send(JSON.stringify(message));

        $(evt.target).val('');

        printChat(myInfo.name, myInfo.profile, message.text, 'right', message.createTime);
    }
});



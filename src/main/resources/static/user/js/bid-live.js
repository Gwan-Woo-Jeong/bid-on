window.onUnload = function () {
    disconnect();
}

//대화명 설정 + 서버 연결
const url = 'ws://localhost:8090/live-bid';
let ws;

function connect(userId) {

    $('#header small').text(userId);
    this.userId = userId;

    ws = new WebSocket(url);
    log('서버에게 연결을 시도합니다.');

    ws.onopen = evt => {
        log('서버와 연결되었습니다.');

        const message = {
            code: "IN",
            userId,
            content: '',
            regdate: dayjs().format('YYYY-MM-DD HH:mm:ss')
        }


        ws.send(JSON.stringify(message));
    };

    ws.onmessage = evt => {
        log('메시지를 수신했습니다.');

        const message = JSON.parse(evt.data);

        if (message.code === 'IN') {
            print('', `[${message.userId}]님이 들어왔습니다.`, 'left', 'state', message.regdate);
        } else if (message.code === 'OUT') {
            print('', `[${message.userId}]님이 나갔습니다.`, 'left', 'state', message.regdate);
        } else if (message.code === 'TALK') {
            print(message.userId, message.content, 'left', 'msg', message.regdate);
        }
    };

    ws.onclose = evt => {
        log('서버와 연결이 종료되었습니다.');
    };

    ws.onerror = evt => {
        log('에러가 발생했습니다. ' + evt);
    };

}

function disconnect() {
    //소켓 연결 종료
    const message = {
        code: "OUT",
        userId: this.userId,
        content: '',
        regdate: dayjs().format('YYYY-MM-DD HH:mm:ss')
    }

    ws.send(JSON.stringify(message));

    ws.close();
}


function print(userId, msg, side, state, time) {
    const temp = `
                <div class="answer ${side}">
                    <div class="avatar">
                        <img src="https://bootdey.com/img/Content/avatar/avatar1.png" alt="User userId">
                        <div class="status offline"></div>
                    </div>
                    <div class="userId">${userId}</div>
                    <div class="text">
                        ${msg}
                    </div>
                    <div class="time">${showTime(time)}</div>
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

$('#message-input').keydown(evt => {
    if (evt.keyCode === 13) {
        const message = {
            code: 'TALK',
            userId: this.userId,
            content: $(evt.target).val(),
            regdate: dayjs().format('YYYY-MM-DD HH:mm:ss')
        }

        ws.send(JSON.stringify(message));

        $(evt.target).val('');

        print(message.userId, message.content, 'right', 'msg', message.regdate);
    }
});
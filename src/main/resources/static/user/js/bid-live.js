//대화명 설정 + 서버 연결
const url = 'ws://localhost:8090/live-bid';

let ws;

function connect(name) {

    $('#header small').text(name);
    this.name = name;

    ws = new WebSocket(url);
    log('서버에게 연결을 시도합니다.');

    ws.onopen = evt => {
        log('서버와 연결되었습니다.');

        const message = {
            code: 1,
            sender: name,
            receiver: '',
            content: '',
            regdate: dayjs().format('YYYY-MM-DD HH:mm:ss')
        };


        ws.send(JSON.stringify(message));
    };

    ws.onmessage = evt => {
        log('메시지를 수신했습니다.');

        const message = JSON.parse(evt.data);

        if (message.code == '1') {
            print('', `[${message.sender}]님이 들어왔습니다.`, 'other', 'state', message.regdate);
        } else if (message.code == '2') {
            print('', `[${message.sender}]님이 나갔습니다.`, 'other', 'state', message.regdate);
        } else if (message.code == '3') {
            print(message.sender, message.content, 'other', 'msg', message.regdate);
        }
    };

    ws.onclose = evt => {
        log('서버와 연결이 종료되었습니다.');
    };

    ws.onerror = evt => {
        log('에러가 발생했습니다. ' + evt);
    };

}

function log(msg) {
    console.log(`[${new Date().toLocaleTimeString()}] ${msg}`);
}

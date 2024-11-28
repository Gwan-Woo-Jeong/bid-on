// console.log($('#chat-user-id').val());

$("#live-enter-btn").click(function () {
    const child = window.open('/bid-live?liveAuctionItemId=1', '_blank');
    child.moveTo(0, 0);
    child.resizeTo(screen.availWidth, screen.availHeight);

    child.addEventListener('load', () => {
        // child.connect($('#chat-user-id').val());
        child.connect("1번방 손님 (1)");
    })
})

$("#live-enter-btn1").click(function () {
    const child = window.open('/bid-live?liveAuctionItemId=1', '_blank');
    child.moveTo(0, 0);
    child.resizeTo(screen.availWidth, screen.availHeight);

    child.addEventListener('load', () => {
        // child.connect($('#chat-user-id').val());
        child.connect("1번방 손님 (2)");
    })
})

$("#live-enter-btn2").click(function () {
    const child = window.open('/bid-live?liveAuctionItemId=1', '_blank');
    child.moveTo(0, 0);
    child.resizeTo(screen.availWidth, screen.availHeight);

    child.addEventListener('load', () => {
        // child.connect($('#chat-user-id').val());
        child.connect("1번방 손님 (3)");
    })
})

$("#live-enter-btn3").click(function () {
    const child = window.open('/bid-live?liveAuctionItemId=2', '_blank');
    child.moveTo(0, 0);
    child.resizeTo(screen.availWidth, screen.availHeight);

    child.addEventListener('load', () => {
        // child.connect($('#chat-user-id').val());
        child.connect("2번방 손님 (1)");
    })
})

$("#live-enter-btn4").click(function () {
    const child = window.open('/bid-live?liveAuctionItemId=2', '_blank');
    child.moveTo(0, 0);
    child.resizeTo(screen.availWidth, screen.availHeight);

    child.addEventListener('load', () => {
        // child.connect($('#chat-user-id').val());
        child.connect("2번방 손님 (2)");
    })
})

$("#live-enter-btn5").click(function () {
    const child = window.open('/bid-live?liveAuctionItemId=2', '_blank');
    child.moveTo(0, 0);
    child.resizeTo(screen.availWidth, screen.availHeight);

    child.addEventListener('load', () => {
        // child.connect($('#chat-user-id').val());
        child.connect("2번방 손님 (3)");
    })
})


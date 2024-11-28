// console.log($('#chat-user-id').val());

$("#live-enter-btn").click(function () {
    const child = window.open('/bid-live', '_blank');
    child.moveTo(0, 0);
    child.resizeTo(screen.availWidth, screen.availHeight);

    child.addEventListener('load', () => {
        // child.connect($('#chat-user-id').val());
        child.connect("TEST");
    })
})

$("#live-enter-btn1").click(function () {
    const child = window.open('/bid-live', '_blank');
    child.moveTo(0, 0);
    child.resizeTo(screen.availWidth, screen.availHeight);

    child.addEventListener('load', () => {
        // child.connect($('#chat-user-id').val());
        child.connect("TEST1");
    })
})

$("#live-enter-btn2").click(function () {
    const child = window.open('/bid-live', '_blank');
    child.moveTo(0, 0);
    child.resizeTo(screen.availWidth, screen.availHeight);

    child.addEventListener('load', () => {
        // child.connect($('#chat-user-id').val());
        child.connect("TEST2");
    })
})

$("#live-enter-btn3").click(function () {
    const child = window.open('/bid-live', '_blank');
    child.moveTo(0, 0);
    child.resizeTo(screen.availWidth, screen.availHeight);

    child.addEventListener('load', () => {
        // child.connect($('#chat-user-id').val());
        child.connect("TEST3");
    })
})



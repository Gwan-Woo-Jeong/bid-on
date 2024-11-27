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



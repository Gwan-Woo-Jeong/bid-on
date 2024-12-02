const urlParams = new URLSearchParams(window.location.search);
const itemId = urlParams.get('itemId');
const winningBidId = urlParams.get('winningBidId');
const auctionType = urlParams.get('auctionType');

$('#go-payment').click(() => {
    location.href = "/auction/checkout?auctionType=" + auctionType + "&itemId=" + itemId + "&winningBidId=" + winningBidId;
});
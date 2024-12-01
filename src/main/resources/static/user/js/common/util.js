function getMinBidUnit(bidAmount) {
    if (bidAmount <= 5000) {
        return 500;
    } else if (5001 <= bidAmount && bidAmount <= 10000) {
        return 1000;
    } else if (10001 <= bidAmount && bidAmount <= 50000) {
        return 2000;
    } else if (50001 <= bidAmount && bidAmount <= 100000) {
        return 5000;
    } else if (100001 <= bidAmount && bidAmount <= 500000) {
        return 10000;
    } else if (500001 <= bidAmount && bidAmount <= 1000000) {
        return 50000;
    } else if (1000001 <= bidAmount && bidAmount <= 5000000) {
        return 100000;
    } else  {
        return 500000;
    }
}

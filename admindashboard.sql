select * from userinfo;
select * from NormalAuctionItem;
select * from LiveAuctionItem;
select * from WinningBid
wb inner join LiveBidCost lbc
on wb.livebidId = lbc.id;
select * from NormalBidInfo;
select * from LiveBidCost;

select id, userInfoId, liveBidId, normalBidId from WinningBid;
select id, auctionItemId,UserInfoId, bidPrice, bidDate from NormalBidInfo;

select sum(bidprice) from LiveBidCost;
select sum(bidprice) from NormalBidInfo;

select * from MainCategory;

--메인 카테고리 count 세기
select * from normalauctionitem;

--서브 카테고리별 오름차순
select sb.name, count(*) from SubCategory sb inner join NormalAuctionItem nai on sb.id = nai.categorySubId 
group by sb.name, sb.id order by sb.id asc;



--사용자 통계---------------------------------------------------------
--전체 회원 수
select count(*) from userinfo;

--30일 이내 가입한 신규인원
--select * from userinfo where createDate >= to_char(sysdate-30,'yyyy-mm-dd');
select count(*) from userinfo where createDate >= to_char(sysdate-30,'yyyy-mm-dd');

--월별 기존 회원수

--월별 신규 회원수

--경매 참여자 수: 최근 30일 이내에 경매에 참여한 회원 수를 확인 할 수 있다.
SELECT COUNT(*) as BidEnterUserCount FROM (
    SELECT userInfoId FROM NormalAuctionItem
    UNION
    SELECT userInfoId FROM NormalBidInfo
    UNION
    SELECT userInfoId FROM LiveAuctionPart
    UNION
    SELECT userInfoId FROM LiveAuctionItem
);


select * from NormalAuctionItem;
select * from NormalBidInfo;
select * from LiveAuctionPart;
select * from LiveAuctionItem;
--사이트 총 방문자 수

--오늘 방문자 수

--카테고리별 낙찰률(전자제품,스포츠,패션,기타) (낙찰된 경매 수 / 총 경매 수) × 100

--경매 성과 분석---------------------------------------------------------
--(월별)평균 시작 가격
WITH MONTHS AS (
    SELECT TO_CHAR(ADD_MONTHS(TRUNC(SYSDATE, 'YEAR'), LEVEL-1), 'MM') AS MONTH
    FROM DUAL
    CONNECT BY LEVEL <= 12
), AverageStartPrices AS (
    SELECT 
        TO_CHAR(startTime, 'MM') AS month,
        AVG(startPrice) AS avg_price
    FROM (
        SELECT startTime, startPrice FROM LiveAuctionItem
        UNION ALL
        SELECT startTime, startPrice FROM NormalAuctionItem
    )
    GROUP BY TO_CHAR(startTime, 'MM')
)
SELECT 
    M.MONTH,
    COALESCE(A.avg_price, 0) AS average_startprice
FROM 
    MONTHS M
LEFT JOIN 
    AverageStartPrices A ON M.MONTH = A.month
ORDER BY 
    M.MONTH;
    
--월별 평균 낙찰 가격
WITH MONTHS AS (
    SELECT TO_CHAR(ADD_MONTHS(TRUNC(SYSDATE, 'YEAR'), LEVEL-1), 'MM') AS MONTH
    FROM DUAL
    CONNECT BY LEVEL <= 12
), BidPrices AS (
    SELECT 
        TO_CHAR(COALESCE(nbi.bidDate, lbc.bidTime), 'MM') AS month,
        (COALESCE(AVG(nbi.bidprice), 0) + COALESCE(AVG(lbc.bidprice), 0)) * 0.1 AS avg_bid_price
    FROM 
        winningbid wb
    FULL OUTER JOIN 
        NormalBidInfo nbi ON wb.normalBidId = nbi.id
    FULL OUTER JOIN 
        LiveBidCost lbc ON wb.liveBidId = lbc.id
    WHERE 
        nbi.bidDate IS NOT NULL OR lbc.bidTime IS NOT NULL
    GROUP BY 
        TO_CHAR(COALESCE(nbi.bidDate, lbc.bidTime), 'MM')
)
SELECT 
    M.MONTH,
    COALESCE(B.avg_bid_price, 0) AS avg_bid_price
FROM 
    MONTHS M
LEFT JOIN 
    BidPrices B ON M.MONTH = B.month
ORDER BY 
    M.MONTH;

--평균 낙찰 가격(총 낙찰 가격 / 낙찰된 경매 수)
SELECT 
    (COALESCE(avg(nbi.bidprice), 0) + COALESCE(avg(lbc.bidprice), 0)) * 0.1 AS avg_bid_price
FROM 
    winningbid wb
left JOIN 
    NormalBidInfo nbi ON wb.normalBidId = nbi.id
left JOIN 
    LiveBidCost lbc ON wb.liveBidId = lbc.id; 


--경매 물품 분석---------------------------------------------------------
--총 경매 물품수
SELECT 
    (SELECT COUNT(*) FROM NormalAuctionItem) + 
    (SELECT COUNT(*) FROM LiveAuctionItem) AS TotalAuctionCount
FROM dual;






--월별 등록된 물품수------------------------------------------
SELECT 
    COALESCE(n.month, l.month) AS month,
    COALESCE(n.count, 0) + COALESCE(l.count, 0) AS total_count
FROM 
    (SELECT TO_CHAR(startTime, 'MM') AS month, COUNT(*) as count
     FROM NormalAuctionItem 
     GROUP BY TO_CHAR(startTime, 'MM')) n
FULL OUTER JOIN
    (SELECT TO_CHAR(startTime, 'MM') AS month, COUNT(*) as count
     FROM LiveAuctionItem
     GROUP BY TO_CHAR(startTime, 'MM')) l
ON n.month = l.month
ORDER BY month;
    
--월별 진행중인 물품수-----------------------------------------
SELECT 
    COALESCE(n.month, l.month) AS month,
    COALESCE(n.count, 0) + COALESCE(l.count, 0) AS total_ongoing_count
FROM 
    (SELECT TO_CHAR(startTime, 'MM') AS month, COUNT(*) as count
     FROM NormalAuctionItem 
     WHERE status = '진행중'
     GROUP BY TO_CHAR(startTime, 'MM')) n
FULL OUTER JOIN
    (SELECT TO_CHAR(startTime, 'MM') AS month, COUNT(*) as count
     FROM LiveAuctionItem
     WHERE startTime >= SYSDATE AND endTime IS NULL
     GROUP BY TO_CHAR(startTime, 'MM')) l
ON n.month = l.month
ORDER BY month;
    
--월별 종료된 물품수--------------------------------------
SELECT 
    COALESCE(n.month, l.month) AS month,
    COALESCE(n.count, 0) + COALESCE(l.count, 0) AS total_ended_count
FROM 
    (SELECT TO_CHAR(startTime, 'MM') AS month, COUNT(*) as count
     FROM NormalAuctionItem 
     WHERE status = '종료'
     GROUP BY TO_CHAR(startTime, 'MM')) n
FULL OUTER JOIN
    (SELECT TO_CHAR(startTime, 'MM') AS month, COUNT(*) as count
     FROM LiveAuctionItem
     WHERE startTime <= SYSDATE AND endTime IS NOT NULL
     GROUP BY TO_CHAR(startTime, 'MM')) l
ON n.month = l.month
ORDER BY month;
---------------------------------------------------------------------------------------
WITH MONTHS AS (
    SELECT TO_CHAR(ADD_MONTHS(TRUNC(SYSDATE, 'YEAR'), LEVEL-1), 'MM') AS MONTH
    FROM DUAL
    CONNECT BY LEVEL <= 12
)
SELECT 
    M.MONTH,
    COALESCE(R.REGISTERED_COUNT, 0) AS REGISTERED_COUNT,
    COALESCE(O.ONGOING_COUNT, 0) AS ONGOING_COUNT,
    COALESCE(E.ENDED_COUNT, 0) AS ENDED_COUNT
FROM MONTHS M
LEFT JOIN (
    SELECT TO_CHAR(startTime, 'MM') AS MONTH,
           COUNT(*) AS REGISTERED_COUNT
    FROM (
        SELECT startTime FROM NormalAuctionItem
        UNION ALL
        SELECT startTime FROM LiveAuctionItem
    )
    GROUP BY TO_CHAR(startTime, 'MM')
) R ON M.MONTH = R.MONTH
LEFT JOIN (
    SELECT TO_CHAR(startTime, 'MM') AS MONTH,
           COUNT(*) AS ONGOING_COUNT
    FROM (
        SELECT startTime FROM NormalAuctionItem WHERE status = '진행중'
        UNION ALL
        SELECT startTime FROM LiveAuctionItem WHERE startTime >= SYSDATE AND endTime IS NULL
    )
    GROUP BY TO_CHAR(startTime, 'MM')
) O ON M.MONTH = O.MONTH
LEFT JOIN (
    SELECT TO_CHAR(startTime, 'MM') AS MONTH,
           COUNT(*) AS ENDED_COUNT
    FROM (
        SELECT startTime FROM NormalAuctionItem WHERE status = '종료'
        UNION ALL
        SELECT startTime FROM LiveAuctionItem WHERE startTime <= SYSDATE AND endTime IS NOT NULL
    )
    GROUP BY TO_CHAR(startTime, 'MM')
) E ON M.MONTH = E.MONTH
ORDER BY M.MONTH;
---------------------------------------------------------------------------------------
--진행 중인 경매 물품 수
select * from NormalAuctionItem;
select * from LiveAuctionItem where startTime >= sysdate and endTime is null;

select * from LiveAuctionItem where startTime <= sysdate and endTime is not null;



--총 낙찰 물품 수
select count(*) from WinningBid;

-- 총수익률 (winningbod 데이터 추가되면 inner join 해야됨 right innre join 해야될듯..)
SELECT 
    (COALESCE(SUM(nbi.bidprice), 0) + COALESCE(SUM(lbc.bidprice), 0)) *0.1 AS total_bid_price
FROM 
    winningbid wb
left JOIN 
    NormalBidInfo nbi ON wb.normalBidId = nbi.id
left JOIN 
    LiveBidCost lbc ON wb.liveBidId = lbc.id;


--수익 분석---------------------------------------------------------
--일반 경매 총 수익(월별)
SELECT 
    EXTRACT(MONTH FROM nbi.bidDate) AS month,
    SUM(nbi.bidprice)*0.1 AS monthly_revenue
FROM 
    winningbid wb
FULL OUTER JOIN 
    NormalBidInfo nbi ON wb.normalBidId = nbi.id
WHERE 
    nbi.bidDate IS NOT NULL
GROUP BY 
    EXTRACT(MONTH FROM nbi.bidDate)
ORDER BY 
    month;
--실시간 경매 총 수익(월별)
SELECT 
    EXTRACT(MONTH FROM lbc.bidTime) AS month,
    SUM(lbc.bidprice)*0.1 AS monthly_revenue
FROM 
    winningbid wb
FULL OUTER JOIN 
    LiveBidCost lbc ON wb.liveBidId = lbc.id
WHERE 
    lbc.bidTime IS NOT NULL
GROUP BY 
    EXTRACT(MONTH FROM lbc.bidTime)
ORDER BY 
    month;


------------------------------------------------
--월별 수익금액 + 누적 수익 금액
WITH MONTHS AS (
    SELECT TO_CHAR(ADD_MONTHS(TRUNC(SYSDATE, 'YEAR'), LEVEL-1), 'MM') AS MONTH
    FROM DUAL
    CONNECT BY LEVEL <= 12
), NormalRevenue AS (
    SELECT 
        TO_CHAR(nbi.bidDate, 'MM') AS month,
        SUM(nbi.bidprice) * 0.1 AS monthly_revenue
    FROM 
        winningbid wb
    FULL OUTER JOIN 
        NormalBidInfo nbi ON wb.normalBidId = nbi.id
    WHERE 
        nbi.bidDate IS NOT NULL
    GROUP BY 
        TO_CHAR(nbi.bidDate, 'MM')
), LiveRevenue AS (
    SELECT 
        TO_CHAR(lbc.bidTime, 'MM') AS month,
        SUM(lbc.bidprice) * 0.1 AS monthly_revenue
    FROM 
        winningbid wb
    FULL OUTER JOIN 
        LiveBidCost lbc ON wb.liveBidId = lbc.id
    WHERE 
        lbc.bidTime IS NOT NULL
    GROUP BY 
        TO_CHAR(lbc.bidTime, 'MM')
), TotalRevenue AS (
    SELECT 
        M.MONTH,
        NVL(NR.monthly_revenue, 0) + NVL(LR.monthly_revenue, 0) AS monthlyTotalRevenue
    FROM 
        MONTHS M
    LEFT JOIN 
        NormalRevenue NR ON M.MONTH = NR.month
    LEFT JOIN 
        LiveRevenue LR ON M.MONTH = LR.month
)
SELECT 
    MONTH,
    monthlyTotalRevenue,
    SUM(monthlyTotalRevenue) OVER (ORDER BY MONTH) AS cumulativeTotalRevenue
FROM 
    TotalRevenue
ORDER BY 
    MONTH;
------------------------------------------------
--총 수익률 월별 (수수료 10%)


--평균 경매 시간








SELECT 
    wb.id,
    nbi.bidDate,
    nbi.bidPrice,
    lbc.bidTime,
    lbc.bidPrice
FROM 
    winningbid wb
left JOIN 
    NormalBidInfo nbi ON wb.normalBidId = nbi.id
left JOIN 
    LiveBidCost lbc ON wb.liveBidId = lbc.id; 

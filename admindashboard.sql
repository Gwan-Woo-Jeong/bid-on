select * from userinfo;
select * from NormalAuctionItem;
select * from LiveAuctionItem;
select * from WinningBid;
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
--SELECT TO_CHAR(na.startTime, 'MM') AS month,
--    AVG(na.startPrice)+AVG(la.stratPrice) AS average_startprice FROM (
--    SELECT userInfoId FROM NormalAuctionItem na
--    UNION
--    SELECT userInfoId FROM LiveAuctionItem la
--) GROUP BY 
--    TO_CHAR(startTime, 'MM')
--ORDER BY
--    month;

SELECT 
    TO_CHAR(startTime, 'MM') AS month,
    AVG(startPrice) AS average_startprice
FROM 
    LiveAuctionItem
GROUP BY 
    TO_CHAR(startTime, 'MM')
ORDER BY
    month;
    
SELECT 
    TO_CHAR(startTime, 'MM') AS month,
    AVG(startPrice) AS average_startprice
FROM 
    NormalAuctionItem
GROUP BY 
    TO_CHAR(startTime, 'MM')
ORDER BY
    month; 
    
--평균 낙찰 가격
SELECT 
    (COALESCE(avg(nbi.bidprice), 0) + COALESCE(avg(lbc.bidprice), 0)) * 0.1 AS avg_bid_price
FROM 
    winningbid wb
left JOIN 
    NormalBidInfo nbi ON wb.normalBidId = nbi.id
left JOIN 
    LiveBidCost lbc ON wb.liveBidId = lbc.id;

--평균 낙찰 가격(총 낙찰 가격 / 낙찰된 경매 수)



--경매 물품 분석---------------------------------------------------------
--총 경매 물품수
SELECT 
    (SELECT COUNT(*) FROM NormalAuctionItem) + 
    (SELECT COUNT(*) FROM LiveAuctionItem) AS TotalAuctionCount
FROM dual;






--월별 등록된 물품수------------------------------------------
select TO_CHAR(startTime, 'MM') AS month,
    count(*) 
from NormalAuctionItem 
GROUP BY 
    TO_CHAR(startTime, 'MM')
ORDER BY
    month;
    
select TO_CHAR(startTime, 'MM') AS month,
    count(*) 
from LiveAuctionItem
GROUP BY 
    TO_CHAR(startTime, 'MM')
ORDER BY
    month;
    
--월별 진행중인 물품수-----------------------------------------
select TO_CHAR(startTime, 'MM') AS month,
    count(*) 
from NormalAuctionItem where status = '진행중' 
GROUP BY 
    TO_CHAR(startTime, 'MM')
ORDER BY
    month;
    
select TO_CHAR(startTime, 'MM') AS month,
    count(*) 
from LiveAuctionItem 
where startTime >= sysdate and endTime is null
GROUP BY 
    TO_CHAR(startTime, 'MM')
ORDER BY
    month;
    
--월별 종료된 물품수--------------------------------------
select TO_CHAR(startTime, 'MM') AS month,
    count(*) 
from NormalAuctionItem where status = '종료' 
GROUP BY 
    TO_CHAR(startTime, 'MM')
ORDER BY
    month;
    
select TO_CHAR(startTime, 'MM') AS month,
    count(*) 
from LiveAuctionItem
where startTime <= sysdate and endTime is not null
GROUP BY 
    TO_CHAR(startTime, 'MM')
ORDER BY
    month;
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
    SUM(nbi.bidprice) AS monthly_revenue
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
--입찰가격에서 낙찰가격된것만 가져옴

--실시간 경매 총 수익(월별)
SELECT 
    EXTRACT(MONTH FROM lbc.bidTime) AS month,
    SUM(lbc.bidprice) AS monthly_revenue
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

--총 수익률 (수수료 10%)


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

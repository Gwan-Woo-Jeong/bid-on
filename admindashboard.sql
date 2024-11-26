select * from userinfo;
--전체 회원 수
select count(*) from userinfo;

--30일 이내 가입한 신규인원
select * from userinfo where createDate >= to_char(sysdate-30,'yyyy-mm-dd');
select count(*) from userinfo where createDate >= to_char(sysdate-30,'yyyy-mm-dd');


select * from NormalAuctionItem;
select * from LiveAuctionItem;

--총 경매 물품수
SELECT 
    (SELECT COUNT(*) FROM NormalAuctionItem) + 
    (SELECT COUNT(*) FROM LiveAuctionItem) AS TotalAuctionCount
FROM dual;
--진행 중인 경매 물품 수

--총 낙찰 물품 수

--총 수익률





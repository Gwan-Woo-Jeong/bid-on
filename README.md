&nbsp;
&nbsp;

<p align="center">
<img src="https://github.com/Gwan-Woo-Jeong/bid-on/blob/main/readme-images/logo.jpg?raw=true" alt="logo">
</p>

&nbsp;

## 🌟 프로젝트 소개

**비드온**은 일반 경매와 실시간 경매를 모두 지원하는 **웹 기반 경매 플랫폼**입니다. **일반 경매**는 **세부적인 카테고리**와 **고급 검색 기능**을 제공하여 구매자가 상품을 선택할 때 보다 **깊이
있는 정보**를 제공합니다. 반면, **실시간 경매**는 **즉시 판매**를 원하는 상품을 **실시간으로 경매방**을 통해 **빠르게 판매**할 수 있는 기능을 제공합니다. 또한, **관리자 페이지**에서는 **경매
진행, 물품 관리, 수익 분석** 등을 **효율적으로 관리**할 수 있는 **대시보드 기능**을 구현하였습니다.

&nbsp;

## 📅 프로젝트 기간

**2024.11.13 ~ 2024.12.03**

&nbsp;

## 💻 개발 환경

| **항목**   | **세부 내용**                                                        |
|----------|------------------------------------------------------------------|
| **운영체제** | Windows 11, Linux(CentOS), macOS                                 |
| **서버**   | Apache Tomcat, AWS EC2, Oracle                                   |
| **개발 툴** | STS 4, Visual Studio Code, SQL Developer, IntelliJ IDEA          |
| **협업 툴** | GitHub, Notion, Discord, ERD Cloud, Draw.io, Figma, Google Drive |

&nbsp;

## 🛠️ 기술 스택

| 분류           | 기술 스택                                                                                                                              |
|--------------|------------------------------------------------------------------------------------------------------------------------------------|
| **프로그래밍 언어** | Java 21, HTML5, CSS, JavaScript (ES6), SQL                                                                                         |
| **백엔드**      | Spring Boot, Gradle, Spring Web, Spring Boot Security, Spring MVC, Spring Boot Devtools, Lombok, HikariCP, JPA, Query DSL, Jackson |
| **프론트엔드**    | Thymeleaf, jQuery, Ajax                                                                                                            |

&nbsp;

## 🎉 주요 기능

### 경매

**실시간 경매**

|                      <img alt="실시간 경매" src="https://github.com/Gwan-Woo-Jeong/bid-on/blob/main/readme-images/real-time.gif?raw=true" width="640">                       |
|:-----------------------------------------------------------------------------------------------------------------------------------------------------------------------:|
| 각 경매 품목마다 독립적인 경매방이 생성되며, 경매는 경매사 봇의 진행 하에 이루어집니다.<br/>경매가 시작되면 입찰 가능한 시간이 1분 주어지며, 입찰이 이루어질 때마다 시간이 다시 1분으로 초기화됩니다.<br/>만약 1분이 경과할 경우, 마지막으로 입찰한 사람이 해당 경매의 승자로 결정됩니다. |

**낙찰 결제**

|      <img alt="낙찰 결제" src="https://github.com/Gwan-Woo-Jeong/bid-on/blob/main/readme-images/payment.gif?raw=true" width="640">       |
|:------------------------------------------------------------------------------------------------------------------------------------:|
| 경매에서 승리한 참여자는 자동으로 입찰 페이지로 이동하며, 경매 종료 후에는 마이페이지에서 결제를 진행할 수 있습니다.<br/>결제는 72시간 이내에 완료해야 하며, 이 기간 내에 결제를 하지 않을 경우 해당 경매품을 받을 수 없습니다. |

**경매물품 조회**

|                                         <img alt="경매물품 조회" src="https://github.com/Gwan-Woo-Jeong/bid-on/blob/main/readme-images/auction-list.gif?raw=true" width="640">                                         |
|:----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------:|
| 등록된 경매품을 여러 방법으로 상세히 조회할 수 있습니다. 제목 검색을 통해 특정 경매품을 쉽게 찾을 수 있습니다.<br/>또한, 경매 마감 시간이나 가격 순으로 목록을 정렬할 수 있어 사용자가 원하는 기준에 따라 경매품을 확인할 수 있습니다.<br/>가격 구간을 지정하면 해당 가격 범위 내의 경매품만 조회할 수도 있어, 예산에 맞는 물품을 효율적으로 찾을 수 있습니다. |

**경매물품 등록**

| <img alt="경매물품 등록" src="https://github.com/Gwan-Woo-Jeong/bid-on/blob/main/readme-images/auction-add.gif?raw=true" width="640"> |
|:-------------------------------------------------------------------------------------------------------------------------------:|
|   경매품의 정보를 입력한 후, 해당 경매품을 등록할 수 있습니다.<br/>경매품 등록 시 여러 장의 이미지를 업로드할 수 있으며,<br/>대표 이미지를 선택하면 해당 이미지가 게시글의 썸네일로 사용되어 경매글이 등록됩니다.   |

### 게시판

**후기 게시판**

| <img alt="후기 게시판" src="https://github.com/Gwan-Woo-Jeong/bid-on/blob/main/readme-images/review-board.jpg?raw=true" width="640"> |
|:-------------------------------------------------------------------------------------------------------------------------------:|
|                                             사용자가 제품 및 서비스에 대한 리뷰를 작성하고 공유할 수 있습니다.                                              |

**1:1 질문 게시판**

| <img alt="1대1 질문게시판" src="https://github.com/Gwan-Woo-Jeong/bid-on/blob/main/readme-images/one-on-one.jpg?raw=true" width="640"> |
|:--------------------------------------------------------------------------------------------------------------------------------:|
|                                사용자가 관리자에게 개인적인 문의를 남길 수 있는 공간입니다. 관리자는 답변을 작성하거나 문의를 삭제할 수 있습니다.                                 |

**자주 묻는 질문 게시판**

| <img alt="자주 묻는 질문 게시판" src="https://github.com/Gwan-Woo-Jeong/bid-on/blob/main/readme-images/faq.jpg?raw=true" width="640"> |
|:----------------------------------------------------------------------------------------------------------------------------:|
|                                        자주 묻는 질문과 답변을 제공하며, 관리자가 질문을 추가하거나 수정할 수 있습니다.                                        |

### 관리자 페이지

**대시보드**

|             <img alt="대시보드" src="https://github.com/Gwan-Woo-Jeong/bid-on/blob/main/readme-images/dashboard.png?raw=true" width="640">             |
|:--------------------------------------------------------------------------------------------------------------------------------------------------:|
| 경매 관리 현황을 한눈에 파악할 수 있는 관리자용 대시보드입니다.<br/>주요 지표(회원 수, 경매 진행 상황, 수익 등)와 통계 데이터를 시각적으로 제공합니다.<br/>카테고리별 품목, 경매 성과, 수익 분석 등을 통해 효율적으로 상품 관리를 할 수 있습니다. |

**경매 관리**

|              <img alt="경매 관리" src="https://github.com/Gwan-Woo-Jeong/bid-on/blob/main/readme-images/admin-auction-manage.png?raw=true" width="640">              |
|:----------------------------------------------------------------------------------------------------------------------------------------------------------------:|
| 실시간과 일반 경매를 구분하여 현재 진행 중인 경매 품목과 시작 시간, 시작가를 확인할 수 있습니다.<br/>또한, 경매품 등록 현황에서는 판매자가 등록한 품목, 경매 종류, 금액, 예정 일정을 관리할 수 있으며,<br/>회원들이 관심을 표시한 품목과 관련 데이터를 확인할 수 있습니다. |

**회원 관리**

| <img alt="회원 관리" src="https://github.com/Gwan-Woo-Jeong/bid-on/blob/main/readme-images/admin-member-manage.png?raw=true" width="640"> |
|:-------------------------------------------------------------------------------------------------------------------------------------:|
|                       회원 정보를 이름, 가입일, 생년월일, 활성화 상태 등을 기준으로 검색할 수 있는 기능을 제공합니다.<br/>이 정보를 수정하거나 회원을 탈퇴시킬 수 있습니다.                       |

**커뮤니티 관리**

| <img alt="커뮤니티 관리" src="https://github.com/Gwan-Woo-Jeong/bid-on/blob/main/readme-images/admin-community-manage.png?raw=true" width="640"> |
|:------------------------------------------------------------------------------------------------------------------------------------------:|
|          자주 묻는 질문을 작성, 수정, 삭제할 수 있습니다.<br/>1:1 문의를 확인하고 답변을 등록하여 고객 지원이 가능합니다.<br/>또한, 후기 게시판에서는 사용자가 작성한 리뷰를 모니터링하고 관리할 수 있습니다.           |

&nbsp;

## 📁 문서 자료

이미지를 클릭하여 자세히 볼 수 있습니다!

### 요구분석서

<a href="https://docs.google.com/document/d/1ivcJ3ajE61uOmuxwnnH3PnxEPL0GHPSD/edit?usp=sharing&ouid=116211667719513023840&rtpof=true&sd=true">
  <img src="https://github.com/Gwan-Woo-Jeong/bid-on/blob/main/readme-images/analysis.png?raw=true" alt="요구분석서" height="540">
</a>

### ERD

<a href="https://www.erdcloud.com/d/di3qGfGGSXuezE49f">
  <img src="https://github.com/Gwan-Woo-Jeong/bid-on/blob/main/readme-images/erd.jpeg?raw=true" alt="ERD">
</a>

### 순서도

<a href="https://app.diagrams.net/#G1r9ANiHho41-AMlXXzUnr2fnCHKXWpl2w#%7B%22pageId%22%3A%22q3uLW3uWv-MSqWR265aN%22%7D">
  <img src="https://github.com/Gwan-Woo-Jeong/bid-on/blob/main/readme-images/flowchart.jpeg?raw=true" alt="순서도">
</a>

<a href="https://app.diagrams.net/#G1r9ANiHho41-AMlXXzUnr2fnCHKXWpl2w#%7B%22pageId%22%3A%22q3uLW3uWv-MSqWR265aN%22%7D">
  <img src="https://github.com/Gwan-Woo-Jeong/bid-on/blob/main/readme-images/flowchart-admin.jpeg?raw=true" alt="순서도-관리자">
</a>



(function($) {
  'use strict';
  $(function() {
      // 월별 기존 회원수 대비 신규 가입자 수 추이
      // "#performaneLine" 요소가 존재하는지 확인
      if ($("#performaneLine").length) {

          //console.log(monthlyNewUserCounts);
          //console.log(monthlyExistingUserCounts);
          //console.log(monthlyUserCountList);

          // "performaneLine" 캔버리 요소의 2D 컨텍스트를 가져옴
          var graphGradient = document.getElementById("performaneLine").getContext('2d');
          var graphGradient2 = document.getElementById("performaneLine").getContext('2d'); // 동일한 컨텍스트를 다시 가져옴 (중복)

          // 첫 번째 그래디언트 배경 생성
          var saleGradientBg = graphGradient.createLinearGradient(5, 0, 5, 100);
          saleGradientBg.addColorStop(0, 'rgba(26, 115, 232, 0.18)'); // 그래디언트의 시작 색상
          saleGradientBg.addColorStop(1, 'rgba(26, 115, 232, 0.02)'); // 그래디언트의 끝 색상

          // 두 번째 그래디언트 배경 생성
          var saleGradientBg2 = graphGradient2.createLinearGradient(100, 0, 50, 150);
          saleGradientBg2.addColorStop(0, 'rgba(0, 208, 255, 0.19)'); // 그래디언트의 시작 색상
          saleGradientBg2.addColorStop(1, 'rgba(0, 208, 255, 0.03)'); // 그래디언트의 끝 색상

          // 차트 데이터 설정
          var salesTopData = {
              labels: ["JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"], // x축 레이블
              datasets: [{
                  label: '신규 가입자', // 데이터셋 레이블
                  data: monthlyNewUserCounts, // 신규 가입자 수 데이터
                  backgroundColor: saleGradientBg, // 배경색 (그래디언트)
                  borderColor: ['#1F3BB3'], // 테두리 색상
                  borderWidth: 1.5, // 테두리 두께
                  fill: true, // 데이터 포인트 아래를 채움
                  pointBorderWidth: 1, // 포인트 테두리 두께
                  pointRadius: [4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4], // 포인트 반지름
                  pointHoverRadius: [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2], // 포인트 호버 시 반지름
                  pointBackgroundColor: ['#1F3BB3)', '#1F3BB3', '#1F3BB3', '#1F3BB3','#1F3BB3)', '#1F3BB3', '#1F3BB3', '#1F3BB3','#1F3BB3)', '#1F3BB3', '#1F3BB3', '#1F3BB3'], // 포인트 배경색
                  pointBorderColor: ['#fff','#fff','#fff','#fff','#fff','#fff','#fff','#fff','#fff','#fff','#fff','#fff'], // 포인트 테두리 색상
              },{
                  label: '기존 가입자', // 두 번째 데이터셋 레이블
                  data: monthlyExistingUserCounts, // 기존 가입자 수 데이터
                  backgroundColor: saleGradientBg2, // 배경색 (그래디언트)
                  borderColor: ['#52CDFF'], // 테두리 색상
                  borderWidth: 1.5, // 테두리 두께
                  fill: true, // 데이터 포인트 아래를 채움
                  pointBorderWidth: 1, // 포인트 테두리 두께
                  pointRadius: [0, 0, 0, 4, 0], // 포인트 반지름
                  pointHoverRadius: [0, 0, 0, 2, 0], // 포인트 호버 시 반지름
                  pointBackgroundColor: ['#52CDFF)', '#52CDFF', '#52CDFF', '#52CDFF','#52CDFF)', '#52CDFF', '#52CDFF', '#52CDFF','#52CDFF)', '#52CDFF', '#52CDFF', '#52CDFF'], // 포인트 배경색
                  pointBorderColor: ['#fff','#fff','#fff','#fff','#fff','#fff','#fff','#fff','#fff','#fff','#fff'], // 포인트 테두리 색상
              }]
          };

          // 차트 옵션 설정
          var salesTopOptions = {
              responsive: true, // 반응형 차트
              maintainAspectRatio: false, // 비율 유지하지 않음
              scales: {
                  yAxes: [{
                      gridLines: {
                          display: true, // y축 그리드 선 표시
                          drawBorder: false, // y축 테두리 선 그리지 않음
                          color: "#F0F0F0", // 그리드 선 색상
                          zeroLineColor: '#F0F0F0', // 0선 색상
                      },
                      ticks: {
                          beginAtZero: false, // 0에서 시작하지 않음
                          autoSkip: true, // 자동으로 눈금 생략
                          maxTicksLimit: 4, // 최대 눈금 수
                          fontSize: 10, // 글자 크기
                          color: "#6B778C" // 글자 색상
                      }
                  }],
                  xAxes: [{
                      gridLines: {
                          display: false, // x축 그리드 선 표시 안 함
                          drawBorder: false, // x축 테두리 선 그리지 않음
                      },
                      ticks: {
                          beginAtZero: false, // 0에서 시작하지 않음
                          autoSkip: true, // 자동으로 눈금 생략
                          maxTicksLimit: 7, // 최대 눈금 수
                          fontSize: 10, // 글자 크기
                          color: "#6B778C" // 글자 색상
                      }
                  }],
              },
              legend: false, // 범례 표시 안 함
              legendCallback: function (chart) { // 범례 생성 콜백 함수
                  var text = [];
                  text.push('<div class="chartjs-legend"><ul>'); // 범례 HTML 시작
                  for (var i = 0; i < chart.data.datasets.length; i++) { // 각 데이터셋에 대해 반복
                      console.log(chart.data.datasets[i]); // 데이터셋 내용 출력
                      text.push('<li>'); // 리스트 항목 시작
                      text.push('<span style="background-color:' + chart.data.datasets[i].borderColor + '">' + '</span>'); // 색상 스팬 추가
                      text.push(chart.data.datasets[i].label); // 데이터셋 레이블 추가
                      text.push('</li>'); // 리스트 항목 끝
                  }
                  text.push('</ul></div>'); // 범례 HTML 끝
                  return text.join(""); // HTML 문자열 반환
              },
              elements: {
                  line: {
                      tension: 0.4, // 선의 곡률 조정
                  }
              },
              tooltips: {
                  backgroundColor: 'rgba(31, 59, 179, 1)', // 툴팁 배경색
              }
          }

          // 차트 생성
          var salesTop = new Chart(graphGradient, {
              type: 'line', // 차트 타입
              data: salesTopData, // 차트 데이터
              options: salesTopOptions // 차트 옵션
          });

          // 생성된 차트의 범례를 지정된 요소에 추가
          document.getElementById('performance-line-legend').innerHTML = salesTop.generateLegend();
      }

      // "#performaneLine-dark" 요소가 존재하는지 확인
      // if ($("#performaneLine-dark").length) {
      //     // "performaneLine-dark" 캔버리 요소의 2D 컨텍스트를 가져옴
      //     var graphGradient = document.getElementById("performaneLine-dark").getContext('2d');
      //     var graphGradient2 = document.getElementById("performaneLine-dark").getContext('2d'); // 동일한 컨텍스트를 다시 가져옴 (중복)
      //
      //     // 첫 번째 그래디언트 배경 생성
      //     var saleGradientBg = graphGradient.createLinearGradient(5, 0, 5, 100);
      //     saleGradientBg.addColorStop(0, 'rgba(26, 115, 232, 0.18)'); // 그래디언트의 시작 색상
      //     saleGradientBg.addColorStop(1, 'rgba(34, 36, 55, 0.5)'); // 그래디언트의 끝 색상
      //
      //     // 두 번째 그래디언트 배경 생성
      //     var saleGradientBg2 = graphGradient2.createLinearGradient(10, 0, 0, 150);
      //     saleGradientBg2.addColorStop(0, 'rgba(0, 208, 255, 0.19)'); // 그래디언트의 시작 색상
      //     saleGradientBg2.addColorStop(1, 'rgba(34, 36, 55, 0.2)'); // 그래디언트의 끝 색상
      //
      //     // 차트 데이터 설정
      //     var salesTopDataDark = {
      //         labels: ["SUN","sun", "MON", "mon", "TUE","tue", "WED", "wed", "THU", "thu", "FRI", "fri", "SAT"], // x축 레이블
      //         datasets: [{
      //             label: '# of Votes', // 데이터셋 레이블
      //             data: [50, 110, 60, 290, 200, 115, 130, 170, 90, 210, 240, 280, 200], // 데이터 값
      //             backgroundColor: saleGradientBg, // 배경색 (그래디언트)
      //             borderColor: ['#1F3BB3'], // 테두리 색상
      //             borderWidth: 1.5, // 테두리 두께
      //             fill: true, // 데이터 포인트 아래를 채움
      //             pointBorderWidth: 1, // 포인트 테두리 두께
      //             pointRadius: [4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4], // 포인트 반지름
      //             pointHoverRadius: [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2], // 포인트 호버 시 반지름
      //             pointBackgroundColor: ['#1F3BB3)', '#1F3BB3', '#1F3BB3', '#1F3BB3','#1F3BB3)', '#1F3BB3', '#1F3BB3', '#1F3BB3','#1F3BB3)', '#1F3BB3', '#1F3BB3', '#1F3BB3','#1F3BB3)'], // 포인트 배경색
      //             pointBorderColor: ['#222437','#222437','#222437','#222437','#222437','#222437','#222437','#222437','#222437','#222437','#222437','#222437','#222437'], // 포인트 테두리 색상
      //         },{
      //             label: '# of Votes', // 두 번째 데이터셋 레이블
      //             data: [30, 150, 190, 250, 120, 150, 130, 20, 30, 15, 40, 95, 180], // 데이터 값
      //             backgroundColor: saleGradientBg2, // 배경색 (그래디언트)
      //             borderColor: ['#52CDFF'], // 테두리 색상
      //             borderWidth: 1.5, // 테두리 두께
      //             fill: true, // 데이터 포인트 아래를 채움
      //             pointBorderWidth: 1, // 포인트 테두리 두께
      //             pointRadius: [0, 0, 0, 4, 0], // 포인트 반지름
      //             pointHoverRadius: [0, 0, 0, 2, 0], // 포인트 호버 시 반지름
      //             pointBackgroundColor: ['#52CDFF)', '#52CDFF', '#52CDFF', '#52CDFF','#52CDFF)', '#52CDFF', '#52CDFF', '#52CDFF','#52CDFF)', '#52CDFF', '#52CDFF', '#52CDFF','#52CDFF)'], // 포인트 배경색
      //             pointBorderColor: ['#222437','#222437','#222437','#222437','#222437','#222437','#222437','#222437','#222437','#222437','#222437','#222437','#222437'], // 포인트 테두리 색상
      //         }]
      //     };
      //
      //     // 차트 옵션 설정
      //     var salesTopOptionsDark = {
      //         responsive: true, // 반응형 차트
      //         maintainAspectRatio: false, // 비율 유지하지 않음
      //         scales: {
      //             yAxes: [{
      //                 gridLines: {
      //                     display: true, // y축 그리드 선 표시
      //                     drawBorder: false, // y축 테두리 선 그리지 않음
      //                     color: "rgba(255,255,255,.05)", // 그리드 선 색상
      //                     zeroLineColor: "rgba(255,255,255,.05)", // 0선 색상
      //                 },
      //                 ticks: {
      //                     beginAtZero: false, // 0에서 시작하지 않음
      //                     autoSkip: true, // 자동으로 눈금 생략
      //                     maxTicksLimit: 4, // 최대 눈금 수
      //                     fontSize: 10, // 글자 크기
      //                     color: "#6B778C" // 글자 색상
      //                 }
      //             }],
      //             xAxes: [{
      //                 gridLines: {
      //                     display: false, // x축 그리드 선 표시 안 함
      //                     drawBorder: false, // x축 테두리 선 그리지 않음
      //                 },
      //                 ticks: {
      //                     beginAtZero: false, // 0에서 시작하지 않음
      //                     autoSkip: true, // 자동으로 눈금 생략
      //                     maxTicksLimit: 7, // 최대 눈금 수
      //                     fontSize: 10, // 글자 크기
      //                     color: "#6B778C" // 글자 색상
      //                 }
      //             }],
      //         },
      //         legend: false, // 범례 표시 안 함
      //         legendCallback: function (chart) { // 범례 생성 콜백 함수
      //             var text = [];
      //             text.push('<div class="chartjs-legend"><ul>'); // 범례 HTML 시작
      //             for (var i = 0; i < chart.data.datasets.length; i++) { // 각 데이터셋에 대해 반복
      //                 console.log(chart.data.datasets[i]); // 데이터셋 내용 출력
      //                 text.push('<li>'); // 리스트 항목 시작
      //                 text.push('<span style="background-color:' + chart.data.datasets[i].borderColor + '">' + '</span>'); // 색상 스팬 추가
      //                 text.push(chart.data.datasets[i].label); // 데이터셋 레이블 추가
      //                 text.push('</li>'); // 리스트 항목 끝
      //             }
      //             text.push('</ul></div>'); // 범례 HTML 끝
      //             return text.join(""); // HTML 문자열 반환
      //         },
      //         elements: {
      //             line: {
      //                 tension: 0.4, // 선의 곡률 조정
      //             }
      //         },
      //         tooltips: {
      //             backgroundColor: 'rgba(31, 59, 179, 1)', // 툴팁 배경색
      //         }
      //     }
      //
      //     // 차트 생성
      //     var salesTopDark = new Chart(graphGradient, {
      //         type: 'line', // 차트 타입
      //         data: salesTopDataDark, // 차트 데이터
      //         options: salesTopOptionsDark // 차트 옵션
      //     });
      //
      //     // 생성된 차트의 범례를 지정된 요소에 추가
      //     document.getElementById('performance-line-legend-dark').innerHTML = salesTopDark.generateLegend();
      // }

// "#datepicker-popup" 요소가 존재하는지 확인
      if ($("#datepicker-popup").length) {
          // datepicker 초기화
          $('#datepicker-popup').datepicker({
              enableOnReadonly: true, // 읽기 전용에서 활성화
              todayHighlight: true, // 오늘 날짜 강조
          });
          // datepicker의 날짜를 오늘로 설정
          $("#datepicker-popup").datepicker("setDate", "0");
      }




    if ($("#status-summary").length) {
      var statusSummaryChartCanvas = document.getElementById("status-summary").getContext('2d');;
      var statusData = {
          labels: ["SUN", "MON", "TUE", "WED", "THU", "FRI"],
          datasets: [{
              label: '# of Votes',
              data: [50, 68, 70, 10, 12, 80],
              backgroundColor: "#ffcc00",
              borderColor: [
                  '#01B6A0',
              ],
              borderWidth: 2,
              fill: false, // 3: no fill
              pointBorderWidth: 0,
              pointRadius: [0, 0, 0, 0, 0, 0],
              pointHoverRadius: [0, 0, 0, 0, 0, 0],
          }]
      };
  
      var statusOptions = {
        responsive: true,
        maintainAspectRatio: false,
          scales: {
              yAxes: [{
                display:false,
                  gridLines: {
                      display: false,
                      drawBorder: false,
                      color:"#F0F0F0"
                  },
                  ticks: {
                    beginAtZero: false,
                    autoSkip: true,
                    maxTicksLimit: 4,
                    fontSize: 10,
                    color:"#6B778C"
                  }
              }],
              xAxes: [{
                display:false,
                gridLines: {
                    display: false,
                    drawBorder: false,
                },
                ticks: {
                  beginAtZero: false,
                  autoSkip: true,
                  maxTicksLimit: 7,
                  fontSize: 10,
                  color:"#6B778C"
                }
            }],
          },
          legend:false,
          
          elements: {
              line: {
                  tension: 0.4,
              }
          },
          tooltips: {
              backgroundColor: 'rgba(31, 59, 179, 1)',
          }
      }
      var statusSummaryChart = new Chart(statusSummaryChartCanvas, {
          type: 'line',
          data: statusData,
          options: statusOptions
      });
    }
    
    //사이트 방문자
    if ($('#totalVisitors').length) {
      var bar = new ProgressBar.Circle(totalVisitors, {
        color: '#fff',
        // This has to be the same size as the maximum width to
        // prevent clipping
        strokeWidth: 15,
        trailWidth: 15, 
        easing: 'easeInOut',
        duration: 1400,
        text: {
          autoStyleContainer: false
        },
        from: {
          color: '#52CDFF',
          width: 15
        },
        to: {
          color: '#677ae4',
          width: 15
        },
        // Set default step function for all animate calls
        step: function(state, circle) {
          circle.path.setAttribute('stroke', state.color);
          circle.path.setAttribute('stroke-width', state.width);
  
          var value = Math.round(circle.value() * 100);
          if (value === 0) {
            circle.setText('');
          } else {
            circle.setText(value);
          }
  
        }
      });
  
      bar.text.style.fontSize = '0rem';
      bar.animate(.64); // Number from 0.0 to 1.0
    }
    if ($('#visitperday').length) {
      var bar = new ProgressBar.Circle(visitperday, {
        color: '#fff',
        // This has to be the same size as the maximum width to
        // prevent clipping
        strokeWidth: 15,
        trailWidth: 15,
        easing: 'easeInOut',
        duration: 1400,
        text: {
          autoStyleContainer: false
        },
        from: {
          color: '#34B1AA',
          width: 15
        },
        to: {
          color: '#677ae4',
          width: 15
        },
        // Set default step function for all animate calls
        step: function(state, circle) {
          circle.path.setAttribute('stroke', state.color);
          circle.path.setAttribute('stroke-width', state.width);
  
          var value = Math.round(circle.value() * 100);
          if (value === 0) {
            circle.setText('');
          } else {
            circle.setText(value);
          }
  
        }
      });
  
      bar.text.style.fontSize = '0rem';
      bar.animate(.34); // Number from 0.0 to 1.0
    }

    // 경매 성과 분석
    if ($("#marketingOverview").length) {
      var marketingOverviewChart = document.getElementById("marketingOverview").getContext('2d');
      var marketingOverviewData = {
          labels: ["JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"],
          datasets: [{
              label: '평균 낙찰 가격',
              data: monthlyAverageBidPrice,
              backgroundColor: "#52CDFF",
              borderColor: [
                  '#52CDFF',
              ],
              borderWidth: 0,
              fill: true, // 3: no fill
              
          },{
            label: '평균 시작 가격',
            data: monthlyAverageStartPrice,
            backgroundColor: "#1F3BB3",
            borderColor: [
                '#1F3BB3',
            ],
            borderWidth: 0,
            fill: true, // 3: no fill
        }]
      };
  
      var marketingOverviewOptions = {
        responsive: true,
        maintainAspectRatio: false,
          scales: {
              yAxes: [{
                  gridLines: {
                      display: true,
                      drawBorder: false,
                      color:"#F0F0F0",
                      zeroLineColor: '#F0F0F0',
                  },
                  ticks: {
                    beginAtZero: true,
                    autoSkip: true,
                    maxTicksLimit: 5,
                    fontSize: 10,
                    color:"#6B778C"
                  }
              }],
              xAxes: [{
                stacked: true,
                barPercentage: 0.35,
                gridLines: {
                    display: false,
                    drawBorder: false,
                },
                ticks: {
                  beginAtZero: false,
                  autoSkip: true,
                  maxTicksLimit: 12,
                  fontSize: 10,
                  color:"#6B778C"
                }
            }],
          },
          legend:false,
          legendCallback: function (chart) {
            var text = [];
            text.push('<div class="chartjs-legend"><ul>');
            for (var i = 0; i < chart.data.datasets.length; i++) {
              console.log(chart.data.datasets[i]); // see what's inside the obj.
              text.push('<li class="text-muted text-small">');
              text.push('<span style="background-color:' + chart.data.datasets[i].borderColor + '">' + '</span>');
              text.push(chart.data.datasets[i].label);
              text.push('</li>');
            }
            text.push('</ul></div>');
            return text.join("");
          },
          
          elements: {
              line: {
                  tension: 0.4,
              }
          },
          tooltips: {
              backgroundColor: 'rgba(31, 59, 179, 1)',
          }
      }
      var marketingOverview = new Chart(marketingOverviewChart, {
          type: 'bar',
          data: marketingOverviewData,
          options: marketingOverviewOptions
      });
      document.getElementById('marketing-overview-legend').innerHTML = marketingOverview.generateLegend();
    }


    // if ($("#marketingOverview-dark").length) {
    //   var marketingOverviewChartDark = document.getElementById("marketingOverview-dark").getContext('2d');
    //   var marketingOverviewDataDark = {
    //       labels: ["JAN","FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"],
    //       datasets: [{
    //           label: 'Last week',
    //           data: [110, 220, 200, 190, 220, 110, 210, 110, 205, 202, 201, 150],
    //           backgroundColor: "#52CDFF",
    //           borderColor: [
    //               '#52CDFF',
    //           ],
    //           borderWidth: 0,
    //           fill: true, // 3: no fill
    //
    //       },{
    //         label: 'This week',
    //         data: [215, 290, 210, 250, 290, 230, 290, 210, 280, 220, 190, 300],
    //         backgroundColor: "#1F3BB3",
    //         borderColor: [
    //             '#1F3BB3',
    //         ],
    //         borderWidth: 0,
    //         fill: true, // 3: no fill
    //     }]
    //   };
    //
    //   var marketingOverviewOptionsDark = {
    //     responsive: true,
    //     maintainAspectRatio: false,
    //       scales: {
    //           yAxes: [{
    //               gridLines: {
    //                   display: true,
    //                   drawBorder: false,
    //                   color:"rgba(255,255,255,.05)",
    //                   zeroLineColor: "rgba(255,255,255,.05)",
    //               },
    //               ticks: {
    //                 beginAtZero: true,
    //                 autoSkip: true,
    //                 maxTicksLimit: 5,
    //                 fontSize: 10,
    //                 color:"#6B778C"
    //               }
    //           }],
    //           xAxes: [{
    //             stacked: true,
    //             barPercentage: 0.35,
    //             gridLines: {
    //                 display: false,
    //                 drawBorder: false,
    //             },
    //             ticks: {
    //               beginAtZero: false,
    //               autoSkip: true,
    //               maxTicksLimit: 7,
    //               fontSize: 10,
    //               color:"#6B778C"
    //             }
    //         }],
    //       },
    //       legend:false,
    //       legendCallback: function (chart) {
    //         var text = [];
    //         text.push('<div class="chartjs-legend"><ul>');
    //         for (var i = 0; i < chart.data.datasets.length; i++) {
    //           console.log(chart.data.datasets[i]); // see what's inside the obj.
    //           text.push('<li class="text-muted text-small">');
    //           text.push('<span style="background-color:' + chart.data.datasets[i].borderColor + '">' + '</span>');
    //           text.push(chart.data.datasets[i].label);
    //           text.push('</li>');
    //         }
    //         text.push('</ul></div>');
    //         return text.join("");
    //       },
    //
    //       elements: {
    //           line: {
    //               tension: 0.4,
    //           }
    //       },
    //       tooltips: {
    //           backgroundColor: 'rgba(31, 59, 179, 1)',
    //       }
    //   }
    //   var marketingOverviewDark = new Chart(marketingOverviewChartDark, {
    //       type: 'bar',
    //       data: marketingOverviewDataDark,
    //       options: marketingOverviewOptionsDark
    //   });
    //   document.getElementById('marketing-overview-legend').innerHTML = marketingOverviewDark.generateLegend();
    // }

    // 카테고리별 물품분석(작은 도넛 그래프)
    if ($("#doughnutChart").length) {
      var doughnutChartCanvas = $("#doughnutChart").get(0).getContext("2d");
      var doughnutPieData = {
        datasets: [{
          data: categoryCounts,
          backgroundColor: [
            "#1F3BB3",
            "#FDD0C7",
            "#52CDFF",
            "#81DADA"
          ],
          borderColor: [
            "#1F3BB3",
            "#FDD0C7",
            "#52CDFF",
            "#81DADA"
          ],
        }],
  
        // These labels appear in the legend and in the tooltips when hovering different arcs
        labels: categoryNames
      };
      var doughnutPieOptions = {
        cutoutPercentage: 50,
        animationEasing: "easeOutBounce",
        animateRotate: true,
        animateScale: false,
        responsive: true,
        maintainAspectRatio: true,
        showScale: true,
        legend: false,
        legendCallback: function (chart) {
          var text = [];
          text.push('<div class="chartjs-legend"><ul class="justify-content-center">');
          for (var i = 0; i < chart.data.datasets[0].data.length; i++) {
            text.push('<li><span style="background-color:' + chart.data.datasets[0].backgroundColor[i] + '">');
            text.push('</span>');
            if (chart.data.labels[i]) {
              text.push(chart.data.labels[i]);
            }
            text.push('</li>');
          }
          text.push('</div></ul>');
          return text.join("");
        },
        
        layout: {
          padding: {
            left: 0,
            right: 0,
            top: 0,
            bottom: 0
          }
        },
        tooltips: {
          callbacks: {
            title: function(tooltipItem, data) {
              return data['labels'][tooltipItem[0]['index']];
            },
            label: function(tooltipItem, data) {
              return data['datasets'][0]['data'][tooltipItem['index']];
            }
          },
            
          backgroundColor: '#fff',
          titleFontSize: 14,
          titleFontColor: '#0B0F32',
          bodyFontColor: '#737F8B',
          bodyFontSize: 11,
          displayColors: false
        }
      };
      var doughnutChart = new Chart(doughnutChartCanvas, {
        type: 'doughnut',
        data: doughnutPieData,
        options: doughnutPieOptions
      });
      document.getElementById('doughnut-chart-legend').innerHTML = doughnutChart.generateLegend();
    }

    //일반 경매 총 수익
    if ($("#leaveReport").length) {
      var leaveReportChart = document.getElementById("leaveReport").getContext('2d');
      var leaveReportData = {
          labels: ["1분기","2분기", "3분기", "4분기"],
          datasets: [{
              label: 'Last week',
              data: [8, 5, 11, 39],
              backgroundColor: "#52CDFF",
              borderColor: [
                  '#52CDFF',
              ],
              borderWidth: 0,
              fill: true, // 3: no fill

          }]
      };

      var leaveReportOptions = {
        responsive: true,
        maintainAspectRatio: false,
          scales: {
              yAxes: [{
                  gridLines: {
                      display: true,
                      drawBorder: false,
                      color:"rgba(255,255,255,.05)",
                      zeroLineColor: "rgba(255,255,255,.05)",
                  },
                  ticks: {
                    beginAtZero: true,
                    autoSkip: true,
                    maxTicksLimit: 5,
                    fontSize: 10,
                    color:"#6B778C"
                  }
              }],
              xAxes: [{
                barPercentage: 0.5,
                gridLines: {
                    display: false,
                    drawBorder: false,
                },
                ticks: {
                  beginAtZero: false,
                  autoSkip: true,
                  maxTicksLimit: 7,
                  fontSize: 10,
                  color:"#6B778C"
                }
            }],
          },
          legend:false,

          elements: {
              line: {
                  tension: 0.4,
              }
          },
          tooltips: {
              backgroundColor: 'rgba(31, 59, 179, 1)',
          }
      }
      var leaveReport = new Chart(leaveReportChart, {
          type: 'bar',
          data: leaveReportData,
          options: leaveReportOptions
      });
    }
    
    //실시간 경매 총 수익
    if ($("#leaveReport-dark").length) {
      var leaveReportChartDark = document.getElementById("leaveReport-dark").getContext('2d');
      var leaveReportDataDark = {
          labels: ["1분기","2분기", "3분기", "4분기"],
          datasets: [{
              label: 'Last week',
              data: [18, 25, 20, 39],
              backgroundColor: "#1F3BB3",
              borderColor: [
                  '#1F3BB3',
              ],
              borderWidth: 0,
              fill: true, // 3: no fill

          }]
      };

      var leaveReportOptionsDark = {
        responsive: true,
        maintainAspectRatio: false,
          scales: {
              yAxes: [{
                  gridLines: {
                      display: false, // 가로선 표시 여부를 false로 설정
                      drawBorder: false,
                      color:"#383e5d",
                      zeroLineColor: '#383e5d',
                  },
                  ticks: {
                    beginAtZero: true,
                    autoSkip: true,
                    maxTicksLimit: 5,
                    fontSize: 10,
                    color:"#6B778C"
                  }
              }],
              xAxes: [{
                barPercentage: 0.5,
                gridLines: {
                    display: false,
                    drawBorder: false,
                },
                ticks: {
                  beginAtZero: false,
                  autoSkip: true,
                  maxTicksLimit: 7,
                  fontSize: 10,
                  color:"#6B778C"
                }
            }],
          },
          legend:false,

          elements: {
              line: {
                  tension: 0.4,
              }
          },
          tooltips: {
              backgroundColor: 'rgba(31, 59, 179, 1)',
          }
      }
      var leaveReportDark = new Chart(leaveReportChartDark, {
          type: 'bar',
          data: leaveReportDataDark,
          options: leaveReportOptionsDark
      });
    }

  });
// 경매 물품 분석 차트: auction-item-analysis-chart
    const aiacahrt = document.getElementById('auction-item-analysis-chart');

// 데이터셋을 위한 설정
    const DATA_COUNT = 12;
    const labels = ['JAN', 'FEB', 'MAR', 'APR', 'MAY', 'JUN', 'JUL', 'AUG', 'SEP', 'OCT', 'NOV', 'DEC'];
	
/*	console.log(registeredCount);
	console.log(ongoingCount);
	console.log(endCount);
*/
	
// 데이터셋 생성
    const data1 = {
        labels: labels,
        datasets: [

            {
                type: 'line',
                label: '진행중인 경매',
                backgroundColor: 'rgba(31,59,179, 0.5)', // 배경색
                borderColor: 'rgba(31,59,179, 1)', // 테두리 색상
                fill: false,
                data: ongoingCount
            },
            {
                type: 'line',
                label: '종료된 경매',
                backgroundColor: 'rgba(129,218,218, 0.5)', // 배경색
                borderColor: 'rgba(129,218,218, 1)', // 테두리 색상
                fill: false,
                data: endCount
            },
            {
                type: 'bar',
                label: '등록된 경매',
                backgroundColor: 'rgba(82,205,255,0.3)', // 배경색
                borderColor: 'rgb(82,205,255, 0.5)', // 테두리 색상
                borderWidth: 1,
                data: registeredCount
            }
        ]
    };

// 차트 생성
    const auctionChart = new Chart(aiacahrt, {
        type: 'bar', // 기본 타입
        data: data1,
        options: {
			scales: {
				    yAxes: [{
				        gridLines: {
				            display: true,      // y축 그리드 라인 표시 여부
				            drawBorder: false,  // y축 테두리 선 표시 여부
				            color:"#F0F0F0",    // y축 그리드 라인 색상
				            zeroLineColor: '#F0F0F0',  // y축 0 위치의 라인 색상
				        },
				        ticks: {
				            beginAtZero: true,    // y축이 0부터 시작
				            autoSkip: true,       // 눈금 자동 스킵 활성화
				            maxTicksLimit: 5,     // 최대 눈금 개수를 5개로 제한
				            fontSize: 10,         // 눈금 글자 크기
				            color:"#6B778C"       // 눈금 글자 색상
				        }
				    }],
				    xAxes: [{
				        stacked: true,           // x축 데이터 스택(누적) 표시 여부
				        barPercentage: 0.35,     // 막대 차트의 경우 막대 너비 비율
				        gridLines: {
				            display: false,       // x축 그리드 라인 숨김
				            drawBorder: false,    // x축 테두리 선 숨김
				        },
				        ticks: {
				            beginAtZero: false,   // x축은 0부터 시작하지 않음
				            autoSkip: true,       // 눈금 자동 스킵 활성화
				            maxTicksLimit: 12,    // 최대 눈금 개수를 12개로 제한
				            fontSize: 10,         // 눈금 글자 크기
				            color:"#6B778C"       // 눈금 글자 색상
				        }
				    }],
				},
				legend:false,                    // 범례 표시 안 함
			    legendCallback: function (chart) { // 범례 생성 콜백 함수
                var text = [];
                text.push('<div class="chartjs-legend"><ul style="felx-direction: row-reverse;">'); // 범례 HTML 시작  
                for (var i = 0; i < chart.data.datasets.length; i++) { // 각 데이터셋에 대해 반복
                    text.push('<li style="display: flex; align-items: center;">'); // 리스트 항목 시작 
                    text.push('<span style="display: inline-block; width: 10px; height: 10px; border-radius: 50%; background-color:' + chart.data.datasets[i].borderColor + '; margin-right: 5px;"></span>'); // 동그라미 추가
                    text.push(chart.data.datasets[i].label); // 데이터셋 레이블 추가
                    text.push('</li>'); // 리스트 항목 끝
                }
                text.push('</ul></div>'); // 범례 HTML 끝
                return text.join(""); // HTML 문자열 반환
            }
        }
    });

// 범례 HTML 추가
    document.getElementById('auction-item-legend').innerHTML = auctionChart.generateLegend();

// 수익 분석 차트: revenue-analysis-chart
    const revenueChart = document.getElementById('revenue-analysis-chart');

// 데이터셋을 위한 설정
    const labels2 = ['JAN', 'FEB', 'MAR', 'APR', 'MAY', 'JUN', 'JUL', 'AUG', 'SEP', 'OCT', 'NOV', 'DEC'];
    const data2 = {
        labels: labels2,
        datasets: [
            {
                label: '수익 수수료',
                data: totalRevenue,
                borderColor: 'rgba(31,59,179, 1)', // 테두리 색상
                backgroundColor: 'rgba(31,59,179, 0.5)', // 배경색
                fill: true,
                tension: 0.5 // 곡선의 부드러움
            },
            {
                label: '누적 수익 금액',
                data: cumulativeTotalRevenue,
                borderColor: 'rgba(54, 162, 235, 1)', // 테두리 색상
                backgroundColor: 'rgba(54, 162, 235, 0.5)', // 배경색
                fill: true,
                tension: 0.5 // 곡선의 부드러움
            }
        ]
    };

// 차트 생성
    const revenueChartInstance = new Chart(revenueChart, {
        type: 'line', // 차트 타입
        data: data2,
		options: {
			scales: {
				    yAxes: [{
				        gridLines: {
				            display: true,      // y축 그리드 라인 표시 여부
				            drawBorder: false,  // y축 테두리 선 표시 여부
				            color:"#F0F0F0",    // y축 그리드 라인 색상
				            zeroLineColor: '#F0F0F0',  // y축 0 위치의 라인 색상
				        },
				        ticks: {
				            beginAtZero: true,    // y축이 0부터 시작
				            autoSkip: true,       // 눈금 자동 스킵 활성화
				            maxTicksLimit: 5,     // 최대 눈금 개수를 5개로 제한
				            fontSize: 10,         // 눈금 글자 크기
				            color:"#6B778C"       // 눈금 글자 색상
				        }
				    }],
				    xAxes: [{
				        stacked: true,           // x축 데이터 스택(누적) 표시 여부
				        barPercentage: 0.35,     // 막대 차트의 경우 막대 너비 비율
				        gridLines: {
				            display: false,       // x축 그리드 라인 숨김
				            drawBorder: false,    // x축 테두리 선 숨김
				        },
				        ticks: {
				            beginAtZero: false,   // x축은 0부터 시작하지 않음
				            autoSkip: true,       // 눈금 자동 스킵 활성화
				            maxTicksLimit: 12,    // 최대 눈금 개수를 12개로 제한
				            fontSize: 10,         // 눈금 글자 크기
				            color:"#6B778C"       // 눈금 글자 색상
				        }
				    }],
				},
				legend:false,                    // 범례 표시 안 함
			    legendCallback: function (chart) { // 범례 생성 콜백 함수
                var text = [];
                text.push('<div class="chartjs-legend"><ul>'); // 범례 HTML 시작
                for (var i = 0; i < chart.data.datasets.length; i++) { // 각 데이터셋에 대해 반복
                    text.push('<li style="display: flex; align-items: center;">'); // 리스트 항목 시작
                    text.push('<span style="display: inline-block; width: 10px; height: 10px; border-radius: 50%; background-color:' + chart.data.datasets[i].borderColor + '; margin-right: 5px;"></span>'); // 동그라미 추가
                    text.push(chart.data.datasets[i].label); // 데이터셋 레이블 추가
                    text.push('</li>'); // 리스트 항목 끝
                }
                text.push('</ul></div>'); // 범례 HTML 끝
                return text.join(""); // HTML 문자열 반환
            }
        }
    });

// 범례 HTML 추가
    document.getElementById('revenue-legend').innerHTML = revenueChartInstance.generateLegend();


})(jQuery);
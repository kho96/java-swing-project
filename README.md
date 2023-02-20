# 프로젝트 소개
Java Swing을 이용하여 간단한 일기장 GUI를 구현하였습니다. </br>
<!--[![메인 이미지](https://github.com/kho96/android-project/blob/master/readme-img/main-img.png)](#목차)-->
## 목차
1. [프로젝트 선정 이유](#프로젝트-선정-이유)
2. [프로젝트 설명](#프로젝트-설명)
3. [화면 흐름도](#flowchart)
4. [실행 화면](#실행-화면)
5. [개발 후기](#개발-후기)

## 프로젝트 선정 이유
Java Swing을 이용하여 GUI를 구현해 보는 것을 목표로 <br/>
Oracle DB 연결 및 입출력 Stream에 대해 공부하기 위해 일기장 프로젝트를 선정하였습니다. 

## 프로젝트 설명
- 개발 환경 : 이클립스(Java)
- 사용 DB : Oracle DB

<!--|컬럼명|데이터 타입|제약 조건|
|:---:|:---:|:---:|
|goal|varchar(100)|primary key|
|progress_rate|number(3)|check (progress_rate between 0 and 100) default 0|
|detail_goal|varchar(500)|-|-->

- 기타 : Swing(GUI), JDBC

## flowchart

<!--![flowchart](https://github.com/kho96/android-project/blob/master/readme-img/flowchart.png)-->

## 실행 화면
<!--* <strong>앱 실행 시, 메인화면 출력 -> 화면 터치 -> 버킷리스트 화면 전환</strong>
![view01](https://github.com/kho96/android-project/blob/master/readme-img/view01.png)

* <strong>버킷리스트 화면 -> 등록(아이콘) 클릭 -> 다이얼로그 출력 -> 취소 : 다이얼로그 닫기/버킷리스트 화면</strong>
![view02](https://github.com/kho96/android-project/blob/master/readme-img/view02.png)

* <strong>다이얼로그 입력 -> 등록 -> 등록 성공 -> 성공 메세지출력(토스트메세지) -> 버킷리스트 화면</strong><br/>
<strong>❗ 제약조건 위반(goal-varchar(100), 입력한 문자가 100바이트를 초과할 경우) 입력 실패 메세지 출력 -> 버킷리스트 화면</strong>
![view03](https://github.com/kho96/android-project/blob/master/readme-img/view03.png)


* <strong>버킷리스트 항목 클릭 -> 버킷리스트 상세 화면 이동 -> 뒤로가기 : 버킷리스트 화면으로 이동</strong>
![view04](https://github.com/kho96/android-project/blob/master/readme-img/view04.png)

* <strong>추가/수정 클릭 -> 다이얼로그 출력 -> 취소 : 다이얼로그 닫기/버킷리스트 상세 화면</strong>
![view05](https://github.com/kho96/android-project/blob/master/readme-img/view05.png)

* <strong>다이얼로그 입력 -> 등록 -> 등록 성공 -> 수정된 내용이 적용된 버킷리스트 상세 화면/</strong><br/>
**✔ 막대바(progressBar)는 진행률을 나타냄.**<br/>
**❗ 제약조건 위반(제약조건 프로젝트 설명 참조) -> 입력 실패 메세지 출력 -> 버킷리스트 화면**
![view06](https://github.com/kho96/android-project/blob/master/readme-img/view06.png)

* <strong>버킷리스트 상세 페이지 -> 삭제 -> 삭제 처리 -> 삭제 성공 메세지 -> 버킷리스트 화면</strong>
![view07](https://github.com/kho96/android-project/blob/master/readme-img/view07.png)-->


## 개발 후기
😵 어려웠던 점
>컴포넌트들의 화면에 배치하는 부분이 어렵게 느껴졌습니다. 깔끔한 배치를 하려고 하다 보니 생각보다 신경써서 작성해야 할 부분이 많았습니다.<br/>
일기장의 날짜 구현 부분에서 어려움을 겪었습니다.<br/> 
원하는 시간대로 날짜를 이동하는 것과 이동된 날짜에서 다시 버튼에 따라 날짜가 증감되는 부분이 처음에 구현할 때 생각보다 어렵게 느껴졌습니다.<br/>
그림일기를 구현하는 것에 가장 많은 시간과 고민을 들였습니다.<br/>
다양한 색상을 표현할 방법과 곡선을 그리는 방법 그리고 해당 그림을 계속 저장하는 부분 그리고 그 저장된 그림을 어떤 
방식으로 DB에 저장할 것인가에 대해 많은 고민과 어려움을 겪었습니다.

😲 배운 점
>


🤔 아쉬웠던 점
>








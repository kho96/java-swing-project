# 프로젝트 소개
Java Swing을 이용하여 간단한 일기장 GUI를 구현하였습니다. </br>
[![메인 이미지](https://github.com/kho96/java-swing-project/blob/master/img-readme/main-img.PNG)](#목차)
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
- 사용 DB : Oracle DB<br/>
[![ERD](https://github.com/kho96/java-swing-project/blob/master/img-readme/erd.png)](#프로젝트-설명)<br/>


- 기타 : Swing(GUI), JDBC

## flowchart
![flowchart](https://github.com/kho96/java-swing-project/blob/master/img-readme/flowchart.png)

## 실행 화면
❗ 실행 화면의 경우 파일이 많아 gif파일로 만들었습니다.<br/>
실행 화면 pdf : [swing-pdf보기](https://github.com/kho96/java-swing-project/blob/master/swing.pdf)(개발 후기는 readme파일을 참고해주세요.)

* <strong>Login Frame (로그인 화면)</strong>
![view01](https://github.com/kho96/java-swing-project/blob/master/img-readme/LoginFrame.gif)

* <strong>Register Frame (회원가입 화면)</strong>
![view02](https://github.com/kho96/java-swing-project/blob/master/img-readme/RegisterFrame.gif)

* <strong>Menu Frame (메뉴 화면)</strong><br/>
![view03](https://github.com/kho96/java-swing-project/blob/master/img-readme/MenuFrame.gif)

* <strong>Main Frame (메인 화면)</strong>
![view04](https://github.com/kho96/java-swing-project/blob/master/img-readme/MainFrame.gif)

* <strong>Write Frame (글쓰기 화면)</strong>
![view05](https://github.com/kho96/java-swing-project/blob/master/img-readme/WriteFrame.gif)

* <strong>PictureDiaryMain Frame (그림일기 메인 화면)</strong>
![view06](https://github.com/kho96/java-swing-project/blob/master/img-readme/PicDiaryMain.gif)

* <strong>PictureDiaryWrite Frame (그림일기 작성 화면)</strong>
![view07](https://github.com/kho96/java-swing-project/blob/master/img-readme/picWriteFrame.gif)

## 개발 후기
😵 어려웠던 점
>컴포넌트들의 화면에 배치하는 부분이 어렵게 느껴졌습니다. 깔끔한 배치를 하려고 하다 보니 생각보다 신경써서 작성해야 할 부분이 많았습니다.<br/>
일기장의 날짜 구현 부분에서 어려움을 겪었습니다.<br/> 
원하는 시간대로 날짜를 이동하는 것과 이동된 날짜에서 다시 버튼에 따라 날짜가 증감되는 부분이 처음에 구현할 때 생각보다 어렵게 느껴졌습니다.<br/>
그림일기를 구현하는 것에 가장 많은 시간과 고민을 들였습니다.<br/>
다양한 색상을 표현할 방법과 곡선을 그리는 방법 그리고 해당 그림을 계속 저장하는 부분 그리고 그 저장된 그림을 어떤 
방식으로 DB에 저장할 것인가에 대해 많은 고민과 어려움을 겪었습니다.

😲 배운 점
>막히는 부분이 생겼을 때, 어떤식으로 해결을 하면 좋을지에 대해 고민을 많이 해보면서 다양한 문서와 자료들을 접할 수 있었습니다.<br/>
그 과정에서 수업시간에 배우지 못한 다양한 라이브러리들(ex.ColorChooser)을 사용해 볼 수 있었습니다.<br/>
또한 날짜의 이동에 대한 부분을 해결하기 위해 현재 날짜값을 변수로 저장하고, 날짜 이동을 하면 해당 일로 변수값을 재할당하여 날짜 계산을
할 수 있게 하는 등의 해결법을 사용했습니다.<br/>
날짜 선택의 경우에 윤달이나 존재하지 않는 일의 범위를 선택할 경우, Date클래스로 해당 날짜 값이 존재하는지 여부를 판단하여
존재한다면 해당 날짜를 보여주고, 존재하지 않으면 잘못된 입력에 대한 메세지를 출력하였습니다.<br/>
이 방식보다 더 좋은 방식이 있을 수 있지만, 해결하기 위해 이러한 알고리즘을 만들어 구현하면서 많이 배웠습니다.

🤔 아쉬웠던 점
>그림일기 저장 부분을 DB에 너무 복잡하게 저장이 되는 것 같아서, 다음에는 이미지의 값을 다른 방식으로 저장해보고 싶다.<br/>
수업시간 외의 시간을 사용하여 만들다보니 전체적인 기능은 잘 구현되었지만 개인적으로는 디자인 부분이나, 다른 기능들도 추가했으면
하는 아쉬움이 크다.<br/>
다음에 ver2를 만들게 된다면 그림일기의 코드를 좀 더 간편하게 작성하고, 이미지 저장방식을 다르게 하며 다른 유저와의 상호작용 기능도 추가해 보면 좋을 것 같다. 









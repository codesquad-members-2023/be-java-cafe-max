# be-java-cafe
마스터즈 2023 스프링 카페 

## 한 일 - 3/30 (목)
1. 김영한 - spring mvc 강의 듣고 클론코딩 중입니다.
2. be-java-cafe-max 다시 클론 및 설정
    - gradle 설정 (mustache 추가)
    - 전에 작성한 main, test 코드 추가 (김영한 강의)
3. mustache 이용해서 html 반복되는 부분 정리
   - templates/layout에 navbar, header, footer 입력
   - templates 폴더의 모든 html 파일에 mustache 문법 적용해서 layout의 폴더 안의 navbar, header, footer 파일들 적용
4. index.html에서 회원가입 페이지로 get 요청 하면, templates/form.html 과 연결해주는 기능 완성
   - 연결이 안되는 문제가 있었는데, mustache 플러그인을 설치해주니까 해결! (Ape 한테 감사..)

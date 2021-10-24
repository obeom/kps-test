# kps-test

1. 개발 프레임웍크 : Spring boot   
2. JAVA 버전 : jdk1.8.0_301
3. IDE: STS
4. Build: Gradle
5. github URL: https://github.com/obeom/kps-test.git
6. 빌드 및 실행 방법
6-1. https://github.com/obeom/kps-test.git URL 다운로드 및 Git Repositories clone
6-2. Gradle Project import
6-3. /kps-test/build.gradle Refresh Gradle Project
6-4. Boot Dashboard Spring Boot App start(kps-test)
7. Gradle Build 오류 발생 시 문제해결 방법: window > properties > Advanced Options > Java home 정보를 설치된 jdk1.8.0_301(java) 폴더를 선택
8.단위테스트(Unit Test) 실행
8.1. /kps-test/src/test/java/com/kpsec/KpsTestApplicationTests.java 파일 선택 후 마우스 오른쪽 클릭
8.2. Run As > JUnit Test 실행
8.3. Console 확인
9. API URL
9.1. 2018년, 2019년 각 연도별 합계 금액이 가장 많은 고객을 추출하는 API: http://localhost:8080/kps/coustHighSumAmt   
9.2. 2018년 또는 2019년에 거래가 없는 고객을 추출하는 API: http://localhost:8080/kps/customerWithoutTrans   
9.3. 연도별 관리점별 거래금액 합계를 구하고 합계금액이 큰 순서로 출력하는 API: http://localhost:8080/kps/managementTransAmount   
9.4. 지점명을 입력하면 해당지점의 거래금액 합계를 출력하는 API: http://localhost:8080/kps/branchTransAmount?branch_name=잠실점   

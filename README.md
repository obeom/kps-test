# kps-test

1. 개발 프레임웍크 : Spring boot   
2. JAVA 버전 : jdk1.8.0_301
3. IDE: STS
4. Build: Gradle
5. In memory DB : h2
6. 프로젝트 명: kps-test
7. github URL: https://github.com/obeom/kps-test.git
8. 빌드 및 실행 방법   
8-1. https://github.com/obeom/kps-test.git URL 다운로드 및 Git Repositories clone   
8-2. Gradle Project import   
8-3. /kps-test/build.gradle Refresh Gradle Project   
8-4. Boot Dashboard Spring Boot App start(kps-test)   
9. Gradle Build 오류 발생 시 문제해결 방법:   window > properties > Advanced Options > Java home 정보를 설치된 jdk1.8.0_301(java) 폴더를 선택   
10. /kps-test/src/main/java/com/kpsec/test/infra/InitData.java 파일 Text file encoding UTF-8 설정 필수.   
11. 단위테스트(Unit Test) 실행   
11.1. /kps-test/src/test/java/com/kpsec/KpsTestApplicationTests.java 파일 선택 후 마우스 오른쪽 클릭   
11.2. Run As > JUnit Test 실행   
11.3. Console 확인     
12. API URL   
12.1. 2018년, 2019년 각 연도별 합계 금액이 가장 많은 고객을 추출하는 API: http://localhost:8080/kps/coustHighSumAmt   
12.2. 2018년 또는 2019년에 거래가 없는 고객을 추출하는 API: http://localhost:8080/kps/customerWithoutTrans   
12.3. 연도별 관리점별 거래금액 합계를 구하고 합계금액이 큰 순서로 출력하는 API: http://localhost:8080/kps/managementTransAmount   
12.4. 지점명을 입력하면 해당지점의 거래금액 합계를 출력하는 API: http://localhost:8080/kps/branchTransAmount?branch_name=잠실점   

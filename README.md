# TomatoClock
<p align="center">
  <img src="https://github.com/user-attachments/assets/dca9346c-ea0e-4683-8560-22d5c6ce7de0" />
</p>
TomatoClock은 뽀모도로 기법을 활용한 시간 관리 웹 애플리케이션입니다. 직관적인 사용자 인터페이스와 맞춤형 설정을 통해 사용자가 집중 시간을 효과적으로 관리할 수 있도록 지원합니다.


## 주요 기능

- ⏱ **뽀모도로 타이머**: 10분에서 60분까지 원하는 시간을 설정하여 집중 시간을 관리
- 👩🏻‍🔧 **사용자 설정**: 타이머 시간, 배경색, 글자색을 개인 취향에 맞게 설정하고 저장
- 🎨 **배경 이미지 관리**: 사용자가 직접 이미지를 업로드하여 배경화면으로 설정하고 저장
- 📊 **통계 조회**: 캘린더 형태로 날짜별 집중 시간 기록을 조회하고, 해당 일자의 세부 통계를 확인


## 시스템 아키텍처
<p align="center">
  <img src="https://github.com/user-attachments/assets/251a06c6-c755-40d1-9ebf-10a06eaef241" />
</p>

## 기술 스택

- **프론트엔드**: JSP, Bootstrap, FullCalendar
- **백엔드**: JDK17, Spring Legacy Framework 6, MyBatis
- **데이터베이스**: MySQL
- **보안**: Spring Security
- **빌드 도구**: Gradle


## 환경

- Apache Tomcat 10
- AWS EC2
- Amazon RDS (MySQL)
- Amazon S3


## 프로젝트 주요 구현 사항

- 사용자 인증 및 권한 관리 시스템 구현
- MyBatis를 이용한 데이터베이스 모델링 및 ORM 설정
- 사용자 설정 및 통계 조회 기능 개발 (프론트엔드 및 백엔드)
- FullCalendar를 활용한 직관적인 통계 조회 기능 구현
- RESTful API의 구현


## 향후 계획

- Spring Boot로 마이그레이션
- Spring Security JWT를 학습하고 적용하여 보안성 강화
- 다양한 프로그래밍 언어 및 프레임워크를 학습하며 지속적으로 기능 추가 및 개선



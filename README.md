#  2025-winter-team2-BE    [🌍스마트 관광 루트 추천 플랫폼]


## 🔥 주요 기능

*   **📍 사용자 입력 기반 여행 계획**: 여행지, 선호 (ex. 감성 카페, 맛집, 액티비티), 여행 기간 등을 입력합니다.
*   **🤖 AI 기반 장소 추천**: 입력된 정보를 바탕으로 AI 서버에 요청하여 여행 계획에 맞는 장소를 추천받습니다.
*   **🗺️ 추천 경로 안내**: 추천된 장소들을 기반으로 최적의 이동 경로를 제공합니다.
*   **🔐 사용자 인증 및 권한 관리**: JWT (JSON Web Token)를 사용하여 사용자 인증 및 권한 관리를 수행합니다.
*   **📄 API 문서화 (Swagger)**: `http://15.164.120.40:8080/swagger-ui/index.html`에서 API 테스트 가능

## 🏗️ 기술 스택

- **⚙️ Spring Boot (Java)** → RESTful API 서버 개발
- **🛡️ Spring Security & JWT** → 사용자 인증 및 보안
- **📑 Swagger** → API 문서화
- **🗄️ MySQL** → 여행 일정 및 사용자 데이터 저장
- **📡 RestTemplate** → AI 서버와의 HTTP 요청 처리
- **☁️ AWS EC2**: 클라우드 서버 환경


## 🔍 REST API 구현 방식

#### **1. 📝 회원 가입 API (`POST /api/users/register`)**
- 사용자가 회원가입을 진행하며, 이메일, 비밀번호, 생년월일, 성별 정보를 입력합니다.
- 입력된 데이터는 `UserService`를 통해 `UserRepository`에 저장됩니다.
- 비밀번호는 `🔒 BCryptPasswordEncoder`를 사용하여 암호화됩니다.

#### **2. 🔑 로그인 API (`POST /api/users/login`)**
- 사용자는 이메일과 비밀번호를 입력하여 로그인합니다.
- `AuthenticationManager`를 사용하여 인증을 수행하며, 인증 성공 시 **JWT Access Token** 및 **Refresh Token**이 발급됩니다.
- Refresh Token은 MySQL 데이터베이스에 저장됩니다.

#### **3. 🚪 로그아웃 API (`POST /api/users/logout`)**
- 사용자가 로그아웃 요청 시, Refresh Token을 삭제하여 더 이상 사용할 수 없도록 합니다.
- 클라이언트 측에서는 저장된 JWT 토큰을 삭제하여 인증 상태를 초기화합니다.

#### **4. 🆔 프로필 조회 API (`GET /api/users/profile`)**
- 사용자의 프로필 정보를 조회하는 API입니다.
- `UserRepository`에서 사용자 정보를 조회하여 반환합니다.

#### **5. 🗺️ AI 여행 일정 추천 API (`POST /api/recommend`)**
- 사용자가 입력한 여행지, 여행 기간, 선호 카테고리를 기반으로 AI 서버(FastAPI)에 요청을 보냅니다.
- `RestTemplate`을 사용하여 AI 서버에 HTTP 요청을 전송합니다.
- AI 서버는 `🤖 Google Gemini API`를 이용하여 일정 추천 결과를 반환합니다.
- 백엔드는 응답 데이터를 `TravelPlan` 엔터티로 변환하여 데이터베이스에 저장 후 클라이언트에 반환합니다.

#### **6. 📌 사용자 여행 일정 조회 API (`GET /api/plans/{userId}`)**
- 사용자가 자신의 여행 계획을 조회할 수 있습니다.
- `userId`를 기반으로 `TravelPlanRepository`에서 해당 사용자의 가장 최근 여행 일정을 반환합니다.

##  🔍 향후 개선하고 싶은 기능

1. ✏️ **회원가입한 사용자가 자신의 정보를 수정할 수 있는 기능 추가**
2. 🌍 **모든 AI가 생성한 일정을 조회할 수 있는 기능 제공**
3. 🗑️ **사용자가 특정 일정을 삭제할 수 있는 기능 추가**
4. 🔓 **로그인하지 않은 사용자도 AI 추천을 받을 수 있도록 하되, 방금 생성된 일정만 볼 수 있도록 제한하여 회원가입을 유도**
5. ✍️ **AI가 생성한 일정을 사용자가 일부 수정하여 저장할 수 있는 기능 제공**

## 🔍 AWS EC2 배포 과정

<details>
  
## 개요

이 가이드는 AWS EC2를 사용하여 Spring Boot와 FastAPI 애플리케이션을 배포하는 방법을 설명합니다. Spring Boot는 포트 8080에서 실행되며, FastAPI는 포트 8000에서 실행됩니다. 데이터베이스로는 MySQL을 사용합니다.

## 배포 과정

### 1. AWS EC2 인스턴스 생성 및 고정 IP 설정

- AWS EC2 인스턴스를 생성합니다.
- Ubuntu Server 22.04 LTS를 사용합니다.
- 필요한 포트(22, 80, 443, 8080, 8000)를 열어줍니다.
- 탄력적 IP(고정 IP)를 생성하고 EC2 인스턴스에 연결합니다.

### 2. DB 생성 및 설정

- EC2에서 DBMS 설치 및 사용합니다.
- EC2 인스턴스에 SSH로 접속합니다.
- MySQL을 설치하고 보안 설정을 진행합니다.
- 데이터베이스와 사용자를 생성합니다.
- 인바운드 규칙에서 MySQL 포트(3306)를 열어줍니다.

### 3. CORS 설정 및 DB 연결

- **CORS 설정**: 
  - Spring Boot와 FastAPI에서 CORS를 설정하여 다른 출처의 요청을 허용합니다.
  
- **DB 연결**: 
  - Spring Boot에서 MySQL 데이터베이스에 연결합니다.

### 4. JAR 파일 생성 및 전송

- Spring Boot 프로젝트에서 JAR 파일을 생성합니다.
- 생성된 JAR 파일을 EC2 인스턴스로 전송합니다.

### 5. EC2에서 애플리케이션 실행

- Spring Boot 애플리케이션을 포트 8080에서 실행합니다.
- FastAPI 애플리케이션을 포트 8000에서 실행합니다.

## 환경 설정

- **인바운드 규칙**:
  
  - 80번 포트: HTTP 요청
  - 443번 포트: HTTPS 요청
  - 8080번 포트: Spring Boot 애플리케이션
  - 8000번 포트: FastAPI 애플리케이션(AI)
  - 3306번 포트: MySQL 접속
 </details>

## 📂 파일 구조
```
src/
├── main/
│   ├── java/com/example/travel/
│   │   ├── 🛠️ config/ 
│   │   ├── 🚀 controller/ 
│   │   ├── 📦 domain/  
│   │   ├── 📜 dto/ 
│   │   ├── 🗄️ repository/ 
│   │   ├── ⚙️ service/ 
│   ├── 📂 resources/
│   │   ├── ⚙️ application.properties  
│   │   ├── 🎨 templates/ 
│   ├── 🖼️ static/
├── test/
│   ├── 🧪 java/com/example/travel/  
│   ├── 📝 DemoApplicationTests.java
```

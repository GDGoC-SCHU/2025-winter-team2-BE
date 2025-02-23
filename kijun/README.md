# AWS EC2 배포 과정

## 개요

이 가이드는 AWS EC2를 사용하여 Spring Boot와 FastAPI 애플리케이션을 배포하는 방법을 설명합니다. Spring Boot는 포트 8080에서 실행되며, FastAPI는 포트 8000에서 실행됩니다. 데이터베이스로는 MySQL을 사용합니다.

## 배포 과정

### 1. AWS EC2 인스턴스 생성 및 고정 IP 설정

* AWS EC2 인스턴스를 생성.
* Ubuntu Server 22.04 LTS를 사용.
* 필요한 포트(22, 80, 443, 8080, 8000)를 열어줍니다.
* 탄력적 IP(고정 IP)를 생성하고 EC2 인스턴스에 연결합니다.

### 2. DB 생성 및 설정
* EC2 에서 DBMS 설치 및 사용.
* EC2 인스턴스에 SSH로 접속합니다.
* MySQL을 설치하고 보안 설정을 진행합니다.
* 데이터베이스와 사용자를 생성합니다.
* 인바운드 규칙에서 MySQL 포트(3306)를 열어줍니다.

### 3. CORS 설정 및 DB 연결

* **CORS 설정**: 
  + Spring Boot와 FastAPI에서 CORS를 설정하여 다른 출처의 요청을 허용합니다.
* **DB 연결**: 
  + Spring Boot에서 MySQL 데이터베이스에 연결합니다.

### 4. JAR 파일 생성 및 전송

* Spring Boot 프로젝트에서 JAR 파일을 생성합니다.
* 생성된 JAR 파일을 EC2 인스턴스로 전송합니다.

### 5. EC2에서 애플리케이션 실행

* Spring Boot 애플리케이션을 포트 8080에서 실행합니다.
* FastAPI 애플리케이션을 포트 8000에서 실행합니다.

## 사용 기술

* **AWS EC2**: 클라우드 서버 환경
* **Spring Boot**: Java 기반 웹 프레임워크 (포트 8080)
* **FastAPI**: Python 기반 웹 프레임워크(구글 AI) (포트 8000)
* **Uvicorn**: ASGI 서버 (FastAPI 구글 AI 애플리케이션 실행)
* **MySQL**: 데이터베이스
* **가상 환경 (venv)**: Python 프로젝트 의존성 관리
* **genai: Google의 Gemini API를 사용하기 위한 Python SDK

## 환경 설정

* **인바운드 규칙**:
  
  + 80번 포트: HTTP 요청
  + 443번 포트: HTTPS 요청
  + 8080번 포트: Spring Boot 애플리케이션
  + 8000번 포트: FastAPI 애플리케이션(AI)
  + 3306번 포트: MySQL 접속


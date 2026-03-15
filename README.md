# My Select Shop

외부 쇼핑 API로 상품을 검색하고, 관심상품으로 저장한 뒤 희망 가격을 설정하고 관리할 수 있는 웹 애플리케이션입니다.  
Spring Boot, JPA, Spring Security, JWT를 기반으로 구현했으며, 회원별 상품 관리, 폴더 분류, 페이징/정렬, 스케줄러 자동 갱신 기능까지 확장했습니다.

---

## 1. 프로젝트 소개

이 프로젝트는 단순 상품 검색에서 끝나지 않고, 검색한 상품을 직접 저장하고 관리할 수 있도록 만든 서비스입니다.

사용자는 상품을 검색한 뒤 관심상품으로 등록할 수 있고, 희망 가격을 설정할 수 있습니다.  
또한 회원가입과 로그인을 통해 자신의 상품만 관리할 수 있으며, 폴더를 만들어 관심상품을 분류할 수도 있습니다.  
추가로 스케줄러를 사용해 저장된 상품의 최저가를 자동으로 갱신하도록 구현했습니다.

---

## 2. 주요 기능

### 회원 기능
- 회원가입
- 로그인
- JWT 발급 및 검증
- 관리자 / 일반 사용자 권한 분리
- 로그인 사용자 정보 조회

### 상품 기능
- 상품 검색
- 관심상품 등록
- 관심상품 희망 가격 수정
- 회원별 관심상품 조회
- 상품 목록 페이징 및 정렬
- 관리자 권한 기반 전체 조회 구조 반영

### 폴더 기능
- 회원별 폴더 생성
- 회원별 폴더 조회
- 관심상품에 폴더 추가
- 상품 응답에 폴더 정보 포함

### 자동화 기능
- 스케줄러를 통한 관심상품 최저가 자동 갱신

---

## 3. 기술 스택

### Backend
- Java 17
- Spring Boot 3.2.2
- Spring Web
- Spring Data JPA
- Spring Security
- JWT (jjwt)
- Validation
- Thymeleaf

### Database
- MySQL

### Frontend
- HTML
- CSS
- JavaScript
- jQuery
- Pagination.js

### External API
- Naver Shopping Search API

---

## 4. 핵심 구현 포인트

### 4-1. JWT 기반 인증/인가
- 로그인 성공 시 JWT를 발급합니다.
- 이후 요청은 `Authorization` 헤더에 JWT를 담아 인증합니다.
- Spring Security Filter를 커스터마이징해 인증/인가 흐름을 구성했습니다.

### 4-2. JPA Entity 설계
- `User` : 회원 정보
- `Product` : 관심상품 정보
- `Folder` : 회원별 폴더
- `ProductFolder` : 상품과 폴더의 다대다 관계를 풀기 위한 중간 엔티티

### 4-3. 상품 관리 구조
- 상품은 회원에게 속합니다.
- 폴더는 회원에게 속합니다.
- 상품과 폴더는 다대다 관계이므로 `ProductFolder`를 통해 직접 관리합니다.

### 4-4. 조회 기능 확장
- `Pageable`, `Page`를 사용해 페이징과 정렬을 구현했습니다.
- 사용자 권한에 따라 조회 범위를 다르게 처리했습니다.

### 4-5. 스케줄러 자동화
- 매일 새벽 1시, 저장된 관심상품의 최저가를 자동으로 갱신합니다.

---

## 5. 프로젝트 구조

```text
src/main/java/org/example/myselectshop
├── config
│   └── WebSecurityConfig.java
├── controller
│   ├── FolderController.java
│   ├── HomeController.java
│   ├── ProductController.java
│   └── UserController.java
├── dto
│   ├── FolderRequestDto.java
│   ├── FolderResponseDto.java
│   ├── LoginRequestDto.java
│   ├── ProductMypriceRequestDto.java
│   ├── ProductRequestDto.java
│   ├── ProductResponseDto.java
│   ├── SignupRequestDto.java
│   └── UserInfoDto.java
├── entity
│   ├── Folder.java
│   ├── Product.java
│   ├── ProductFolder.java
│   ├── Timestamped.java
│   ├── User.java
│   └── UserRoleEnum.java
├── jwt
│   └── JwtUtil.java
├── naver
│   ├── controller
│   │   └── NaverApiController.java
│   ├── dto
│   │   └── ItemDto.java
│   └── service
│       └── NaverApiService.java
├── repository
│   ├── FolderRepository.java
│   ├── ProductFolderRepository.java
│   ├── ProductRepository.java
│   └── UserRepository.java
├── scheduler
│   └── Scheduler.java
├── security
│   ├── JwtAuthenticationFilter.java
│   ├── JwtAuthorizationFilter.java
│   └── UserDetailsImpl.java
├── service
│   ├── FolderService.java
│   ├── ProductService.java
│   ├── UserDetailsServiceImpl.java
│   └── UserService.java
└── util
    └── TestDataRunner.java

## 7. API 명세

### 7-1. 회원 API

#### 회원가입
- `POST /api/user/signup`

#### 로그인
- `POST /api/user/login`

#### 로그인 사용자 정보 조회
- `GET /api/user-info`

#### 로그인 사용자 폴더 fragment 조회
- `GET /api/user-folder`

---

### 7-2. 상품 API

#### 상품 검색
- `GET /api/search?query=...`

#### 관심상품 등록
- `POST /api/products`

#### 희망 가격 수정
- `PUT /api/products/{id}`

#### 관심상품 조회
- `GET /api/products?page=&size=&sortBy=&isAsc=`

---

### 7-3. 폴더 API

#### 폴더 생성
- `POST /api/folders`

#### 내 폴더 조회
- `GET /api/folders`

#### 관심상품에 폴더 추가
- `POST /api/products/{productId}/folder?folderId=`

---



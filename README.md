# My Select Shop

외부 쇼핑 API를 이용해 상품을 검색하고, 관심상품으로 저장한 뒤 희망 가격과 폴더를 기준으로 관리할 수 있는 웹 애플리케이션입니다.

Spring Boot, Spring Data JPA, Spring Security, JWT 기반으로 인증/인가를 구성했고, 회원별 관심상품 관리, 폴더 분류, 페이징/정렬 조회, 스케줄러 기반 최저가 갱신 기능을 구현했습니다.

---

## 1. 프로젝트 소개

이 프로젝트는 단순 상품 검색에서 끝나지 않고, 검색한 상품을 사용자가 직접 저장하고 관리할 수 있도록 만든 서비스입니다.

사용자는 네이버 쇼핑 API로 상품을 검색한 뒤 관심상품으로 등록할 수 있고, 각 상품별 희망 가격을 설정할 수 있습니다.
또한 회원가입 및 로그인을 통해 자신의 상품만 관리할 수 있으며, 폴더를 생성해 관심상품을 분류할 수 있습니다.
추가로 스케줄러를 통해 저장된 관심상품의 최저가를 주기적으로 갱신하도록 구성했습니다.

---

## 2. 주요 기능

### 회원 기능
- 회원가입
- 로그인
- JWT 발급 및 검증
- 일반 사용자 / 관리자 권한 분리
- 로그인 사용자 정보 조회

### 상품 기능
- 상품 검색
- 관심상품 등록
- 관심상품 희망 가격 수정
- 회원별 관심상품 조회
- 폴더별 관심상품 조회
- 상품 목록 페이징 및 정렬
- 관리자 계정의 전체 상품 조회

### 폴더 기능
- 회원별 폴더 생성
- 회원별 폴더 조회
- 관심상품에 폴더 추가
- 상품 응답에 폴더 정보 포함

### 자동화 기능
- 스케줄러를 통한 관심상품 최저가 자동 갱신

### 부가 기능
- AOP 기반 API 사용 시간 측정 및 누적 저장
- 전역 예외 처리

---

## 3. 기술 스택

### Backend
- Java 17
- Spring Boot 3.x.x
- Spring Web
- Spring Data JPA
- Spring Security
- JWT (jjwt)
- Validation
- Thymeleaf
- AOP

### Database
- MySQL
- H2 (test/runtime)

### Frontend
- HTML
- CSS
- JavaScript
- jQuery
- Pagination.js

### External API
- Naver Shopping Search API
- Kakao Login API (일부 연동 코드 포함)

---

## 4. 핵심 구현 포인트

### 4-1. JWT 기반 인증/인가
- 로그인 성공 시 JWT를 발급합니다.
- 이후 요청은 `Authorization` 헤더의 Bearer 토큰을 기준으로 인증합니다.
- Spring Security Filter를 커스터마이징해 인증/인가 흐름을 구성했습니다.

### 4-2. JPA Entity 설계
- `User` : 회원 정보
- `Product` : 관심상품 정보
- `Folder` : 회원별 폴더
- `ProductFolder` : 상품과 폴더의 다대다 관계를 풀기 위한 중간 엔티티
- `ApiUseTime` : 사용자별 API 사용 시간 누적 정보

### 4-3. 상품 관리 구조
- 상품은 회원에게 속합니다.
- 폴더는 회원에게 속합니다.
- 상품과 폴더는 다대다 관계이므로 `ProductFolder`를 통해 직접 관리합니다.

### 4-4. 조회 기능 확장
- `Pageable`, `Page`를 사용해 페이징과 정렬을 구현했습니다.
- 사용자 권한에 따라 조회 범위를 다르게 처리했습니다.
- 폴더 기준으로 관심상품을 필터링해 조회할 수 있습니다.

### 4-5. 스케줄러 자동화
- 매일 새벽 1시, 저장된 관심상품의 최저가를 네이버 쇼핑 API 조회 결과 기준으로 갱신합니다.

### 4-6. AOP 기반 사용 시간 측정
- 상품, 폴더, 검색 API 호출에 대해 실행 시간을 측정합니다.
- 로그인한 사용자의 누적 API 사용 시간을 DB에 저장합니다.

---

## 5. 프로젝트 구조

```text
src/main/java/org/example/myselectshop
├── MyselectshopApplication.java
├── aop
│   └── UseTimeAop.java
├── config
│   ├── JpaConfig.java
│   ├── RestTemplateConfig.java
│   └── WebSecurityConfig.java
├── controller
│   ├── FolderController.java
│   ├── HomeController.java
│   ├── ProductController.java
│   └── UserController.java
├── dto
│   ├── FolderRequestDto.java
│   ├── FolderResponseDto.java
│   ├── KakaoUserInfoDto.java
│   ├── LoginRequestDto.java
│   ├── ProductMypriceRequestDto.java
│   ├── ProductRequestDto.java
│   ├── ProductResponseDto.java
│   ├── SignupRequestDto.java
│   └── UserInfoDto.java
├── entity
│   ├── ApiUseTime.java
│   ├── Folder.java
│   ├── Product.java
│   ├── ProductFolder.java
│   ├── Timestamped.java
│   ├── User.java
│   └── UserRoleEnum.java
├── exception
│   ├── GlobalExceptionHandler.java
│   ├── ProductNotFoundException.java
│   └── RestApiException.java
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
│   ├── ApiUseTimeRepository.java
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
│   ├── KakaoService.java
│   ├── ProductService.java
│   ├── UserDetailsServiceImpl.java
│   └── UserService.java
└── util
    └── TestDataRunner.java
```

---

## 6. API 명세

### 6-1. 회원 API

#### 회원가입
- `POST /api/user/signup`

#### 로그인
- `POST /api/user/login`

#### 로그인 사용자 정보 조회
- `GET /api/user-info`

#### 로그인 사용자 폴더 fragment 조회
- `GET /api/user-folder`

#### 로그인 페이지
- `GET /api/user/login-page`

#### 회원가입 페이지
- `GET /api/user/signup`

---

### 6-2. 상품 API

#### 상품 검색
- `GET /api/search?query=...`

#### 관심상품 등록
- `POST /api/products`

#### 희망 가격 수정
- `PUT /api/products/{id}`

#### 관심상품 조회
- `GET /api/products?page=&size=&sortBy=&isAsc=`

#### 폴더별 관심상품 조회
- `GET /api/folders/{folderId}/products?page=&size=&sortBy=&isAsc=`

---

### 6-3. 폴더 API

#### 폴더 생성
- `POST /api/folders`

#### 내 폴더 조회
- `GET /api/folders`

#### 관심상품에 폴더 추가
- `POST /api/products/{productId}/folder?folderId=`

---

## 7. 실행 방법

### 7-1. 프로젝트 클론

```bash
git clone <YOUR_REPOSITORY_URL>
cd myselectshop
```

### 7-2. 환경 변수 설정

`src/main/resources/application.properties` 기준으로 아래 값이 필요합니다.

- `DB_URL`
- `DB_USERNAME`
- `DB_PASSWORD`
- `JWT_SECRET_KEY`
- `NAVER_CLIENT_ID`
- `NAVER_CLIENT_SECRET`
- `KAKAO_CLIENT_ID`
- `KAKAO_CLIENT_SECRET`
- `KAKAO_REDIRECT_URI`
- `ADMIN_TOKEN`

예시:

```env
DB_URL=jdbc:mysql://localhost:3306/myselectshop
DB_USERNAME=root
DB_PASSWORD=your_password
JWT_SECRET_KEY=your_base64_encoded_secret_key
NAVER_CLIENT_ID=your_naver_client_id
NAVER_CLIENT_SECRET=your_naver_client_secret
KAKAO_CLIENT_ID=your_kakao_client_id
KAKAO_CLIENT_SECRET=your_kakao_client_secret
KAKAO_REDIRECT_URI=http://localhost:8080/api/user/kkao/callback
ADMIN_TOKEN=your_admin_token
```

### 7-3. 애플리케이션 실행

```bash
./gradlew bootRun
```

---



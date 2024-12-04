
# Toilet-Go 프로젝트
<img width="1132" alt="image" src="https://github.com/user-attachments/assets/774a42b9-28a0-4fda-b024-78e1a121812e">

**Toilet-Go**는 사용자가 주변 화장실 위치를 확인하고 리뷰를 남길 수 있는 오픈소스 웹 애플리케이션입니다. 

---

## 주요 기능
- **화장실 검색**: 지도 API를 활용한 위치 기반 검색 기능.
- **리스트 검색**: 필터링을 통한 정보 검색 기능.
- **즐겨찾기 시스템**: 원하는 화장실 즐겨찾기 추가 및 삭제 기능.
- **리뷰 시스템**: 사용자 리뷰 작성 및 별점 기능.
- **사용자 관리**: 회원가입, 로그인 기능.

---

## 설치 및 실행 가이드

### 1. 프로젝트 클론
```bash
git clone https://github.com/2024-Opensource-project/toilet-go.git
```

### 2. 애플리케이션 실행
k8s로 서버 실행
```bash
kubectl apply -f ./toilet-go/deploy.yaml 
kubectl port-forward pod/toilet-go 54080:54080
```

### 3. 웹 접속
브라우저에서 http://localhost:54080 를 입력하여 애플리케이션에 접속.

---

### + 추가 설명
- 로그인 필요 기능 : 즐겨찾기 및 리뷰 작성
  

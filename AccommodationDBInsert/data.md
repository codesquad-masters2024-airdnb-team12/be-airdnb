### 데이터 설정
#### 주소
- country : KR 고정
- zipcode
- address
- detailedAddress(숙소명)

#### 위도, 경도
다음과 같은 형식으로 저장 ST_GeomFromText('POINT(12.34 126.34)')

#### 설명
개요

#### 이름
숙소명 + 상세정보에서 '객실명:' 제외

#### 랜덤 설정 항목
- 태그 (amenities)
- bed, bedroom, bath, maxGuests -> bedroom 무조건 1 이상, 전부 20 내외 랜덤
- 비용 (00으로 끝나게 랜덤)
- initialDiscountApplied, weeklyDiscountApplied, monthlyDiscountApplied => true, false 랜덤

#### buildingType : 호텔
#### accommodationType : 방

#### 이미지

---
- 회원은 기본 10인 등록하기 ✅
- 편의시설도 기본 등록하기 ✅
- accommodationInfo도 같이 등록하기 => 얘는 생략하자
- accommodationDiscount는 initialDiscountApplied 에 따라 true면 3으로 등록 ✅
- accommodationFacility도 같이 등록하기 ✅
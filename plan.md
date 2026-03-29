# #46 : 카메라 촬영 / 갤러리 사진 선택 구현 계획

## 개요
BookmarkTypeSelectScreen에서 카메라/갤러리 선택 시, Android 플랫폼에서 실제로 사진을 촬영하거나 갤러리에서 이미지를 선택하는 기능을 구현한다.

## 결정 사항
- **이미지 선택**: 단일 선택 (PickVisualMedia)
- **플랫폼**: Android만 구현 (iOS는 no-op stub)
- **OCR**: 미포함 (이미지 표시만)
- **저장 위치**: 앱 캐시 디렉토리 (cacheDir)

## 아키텍처
KMP expect/actual 패턴을 사용하여 commonMain에서 플랫폼 독립적인 인터페이스를 정의하고, androidMain에서 ActivityResultContracts 기반으로 구현한다.

```
commonMain/
  ImagePickerLauncher.kt  (expect)  ← 카메라/갤러리 런처 인터페이스

androidMain/
  ImagePickerLauncher.android.kt (actual) ← ActivityResultContracts 구현

iosMain/
  ImagePickerLauncher.ios.kt (actual) ← No-op stub
```

## 체크리스트

### Phase 1: Android 인프라 설정
- [x] 1-1. `android-app/src/main/AndroidManifest.xml`에 카메라 권한 추가
- [x] 1-2. `android-app/src/main/AndroidManifest.xml`에 FileProvider 등록
- [x] 1-3. `android-app/src/main/res/xml/file_paths.xml` 생성
- [x] 1-4. `feature/bookmark/build.gradle.kts`에 androidMain 의존성 추가

### Phase 2: expect/actual ImagePickerLauncher 구현
- [x] 2-1. commonMain에 `ImagePickerLauncher.kt` 생성 (expect)
- [x] 2-2. androidMain에 `ImagePickerLauncher.android.kt` 생성 (actual)
- [x] 2-3. iosMain에 `ImagePickerLauncher.ios.kt` 생성 (no-op stub)

### Phase 3: AddBookmarkScreen UI 통합
- [x] 3-1. `AddBookmarkScreen.kt` 수정 (PhotoSection + 자동 런처 + AsyncImage)
- [x] 3-2. `AddBookmarkViewModel.kt` 수정 (onPhotoUriChanged 추가)
- [x] 3-3. `AddBookmarkSideEffect.kt` — 불필요 (LaunchedEffect에서 직접 처리)

### Phase 4: 빌드 검증
- [x] 4-1. `./gradlew :android-app:assembleDebug` 빌드 성공
- [x] 4-2. `./gradlew :feature:bookmark:ktlintCheck` 통과

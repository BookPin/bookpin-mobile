# BookPin - Claude Code 가이드

## 프로젝트 개요

BookPin은 **Kotlin Multiplatform (KMP) + Compose Multiplatform (CMP)** 기반의 크로스 플랫폼 앱입니다.
Android와 iOS를 동시에 지원하며, **MVI 패턴**과 **Clean Architecture**를 적용합니다.

## 기술 스택

| 구분 | 기술 |
|------|------|
| Language | Kotlin 2.3.0 |
| UI Framework | Compose Multiplatform 1.10.0 |
| Architecture | MVI + Clean Architecture |
| DI | Koin 4.1.1 |
| Network | Ktor 3.4.0 |
| Local Storage | DataStore |
| Image Loading | Coil 3.3.0 |
| Serialization | KotlinX Serialization 1.10.0 |
| Async | KotlinX Coroutines 1.10.2 |
| Logging | Kermit 2.0.8 |

## 프로젝트 구조

```
BookPin/
├── android-app/          # Android 진입점
├── iosApp/               # iOS 진입점 (Swift)
├── compose-app/          # 공유 UI 모듈, DI 설정, Navigation
├── feature/              # Feature 모듈
│   ├── auth/             # 인증 Feature 모듈
│   ├── home/             # 홈 Feature 모듈
│   ├── search/           # 검색 Feature 모듈
│   ├── bookmark/         # 책 상세/북마크 Feature 모듈
│   └── settings/         # 설정 Feature 모듈
├── core/                 # Core 모듈
│   ├── common/           # 공통 유틸리티 (Snackbar, Extensions)
│   ├── data/             # Data Layer (Repository 구현체)
│   ├── data-api/         # DataSource 인터페이스, DTO
│   ├── data-remote/      # Remote DataSource (Ktor)
│   ├── data-local/       # Local DataSource (DataStore)
│   └── data-auth/        # 소셜 인증 구현체 (Kakao, Apple)
├── designsystem/         # 디자인 시스템 (Color, Typography, Theme)
├── domain/               # Domain Layer (Repository 인터페이스)
├── model/                # 비즈니스 모델
├── build-logic/          # Gradle Convention Plugins
└── gradle/
    └── libs.versions.toml
```

## 모듈 상세

### App 모듈

#### android-app
Android 앱 진입점.
- `BookPinApplication`: Koin 초기화
- `MainActivity`: `BookPinApp()` 호출

#### iosApp
iOS 앱 진입점. Swift에서 `getBookPinViewController()` 호출.

#### compose-app
플랫폼 공통 UI 모듈.
```
src/
├── commonMain/
│   ├── BookPinApp.kt         # 앱 루트 Composable (Scaffold, Snackbar)
│   ├── di/AppModule.kt       # Koin 모듈 통합
│   └── navigation/           # Navigator 구현체
├── androidMain/
└── iosMain/
    └── KoinHelper.kt         # iOS용 Koin 초기화 헬퍼
```

### Feature 모듈 (`feature/`)

#### feature/auth
인증 화면 Feature 모듈.
```
src/commonMain/kotlin/com/phase/bookpin/auth/
├── AuthScreen.kt         # 로그인 UI
├── AuthViewModel.kt      # MVI ViewModel
├── AuthState.kt          # UI State
├── AuthSideEffect.kt     # Side Effects (Snackbar, Navigation)
└── di/AuthModule.kt      # Koin 모듈
```

#### feature/bookmark
책 상세/북마크 Feature 모듈.
```
src/commonMain/kotlin/com/phase/bookpin/bookmark/
├── BookDetailScreen.kt       # 책 상세 UI
├── BookDetailViewModel.kt    # MVI ViewModel
├── BookDetailState.kt        # UI State
├── BookDetailSideEffect.kt   # Side Effects
└── di/BookDetailModule.kt    # Koin 모듈
```

### Core 모듈 (`core/`)

#### core/common
공통 유틸리티 모듈.
```
src/commonMain/kotlin/com/phase/bookpin/common/
├── BaseViewModel.kt              # MVI 기반 ViewModel
├── Platform.kt                   # 플랫폼 구분
├── snackbar/
│   ├── SnackbarHost.kt           # Snackbar 인터페이스
│   ├── NoSnackbarHost.kt         # No-op 구현체
│   └── SnackbarExtensions.kt     # LocalSnackbarHost
└── extensions/
    └── FlowExt.kt                # collectSideEffect 확장 함수
```

#### designsystem
디자인 시스템 모듈.
- `Color.kt`: BookPinColors, LocalBookPinColors
- `Type.kt`: BookPinTypography, LocalBookPinTypography
- `Theme.kt`: BookPinTheme

**주요 색상:**
- Primary: Gold (#C9A87C)
- Secondary: Teal (#9DBEB7)
- Background: Cream (#FFF8F0)
- Text: Brown (#6B5744)

### Domain Layer

#### domain
Repository 인터페이스 및 UseCase.
```
src/commonMain/kotlin/com/phase/bookpin/domain/
├── auth/AuthRepository.kt        # 인증 Repository 인터페이스
└── kakao/KakaoAuth.kt            # 카카오 인증 인터페이스
```

#### model
비즈니스 모델.
```
src/commonMain/kotlin/com/phase/bookpin/model/
└── SocialAuthToken.kt            # 소셜 인증 토큰 모델
```

### Data Layer (`core/`)

#### core/data
Repository 구현체 및 DI.
```
src/commonMain/kotlin/com/phase/bookpin/data/
├── auth/AuthRepositoryImpl.kt    # AuthRepository 구현체
└── di/DataModule.kt              # Koin 모듈
```

#### core/data-api
DataSource 인터페이스 및 DTO.
```
src/commonMain/kotlin/com/phase/bookpin/data/api/
├── auth/
│   ├── AuthRemoteDataSource.kt   # Remote DataSource 인터페이스
│   ├── LoginResponse.kt          # 로그인 응답 DTO
│   ├── RefreshTokenRequest.kt
│   ├── RefreshTokenResponse.kt
│   └── SocialAuthTokenRequest.kt
├── datastore/
│   ├── BookPinPreferenceDataStore.kt  # Local DataSource 인터페이스
│   └── DataStoreKey.kt
└── navigation/
    └── Navigator.kt              # Navigation 인터페이스
```

#### core/data-remote
Remote DataSource 구현체 (Ktor).
```
src/commonMain/kotlin/com/phase/bookpin/data/remote/
├── auth/AuthRemoteDataSourceImpl.kt  # API 호출 구현체
├── client/
│   ├── ClientModule.kt           # HttpClient 설정
│   ├── HttpClientExtensions.kt   # 응답 처리 확장 함수
│   └── RemoteException.kt        # API 에러 클래스
└── di/DataRemoteModule.kt        # Koin 모듈
```

#### core/data-local
Local DataSource 구현체 (DataStore).
```
src/
├── commonMain/
│   ├── datastore/
│   │   ├── BookPinPreferenceDataStoreImpl.kt
│   │   └── DataStoreFactory.kt   # expect 선언
│   └── di/DataLocalModule.kt
├── androidMain/
│   └── datastore/DataStoreFactory.android.kt
└── iosMain/
    └── datastore/DataStoreFactory.ios.kt
```

#### core/data-auth
소셜 인증 구현체.
```
src/androidMain/kotlin/com/phase/bookpin/data/auth/kakao/
├── AndroidKakaoAuth.kt           # 카카오 로그인 구현체
└── KaKaoLoginState.kt            # 로그인 상태 enum
```

## MVI 패턴 구현 가이드

### BaseViewModel 사용
```kotlin
class FeatureViewModel(
    private val repository: FeatureRepository
) : BaseViewModel<FeatureState, FeatureSideEffect>() {

    override fun createInitialState(): FeatureState = FeatureState()

    fun onAction() {
        viewModelScope.launch {
            reduce { copy(isLoading = true) }

            repository.getData()
                .onSuccess { data ->
                    reduce { copy(isLoading = false, data = data) }
                }
                .onFailure { error ->
                    reduce { copy(isLoading = false) }
                    postSideEffect(FeatureSideEffect.ShowSnackbar(error.message))
                }
        }
    }
}
```

### SideEffect 처리
```kotlin
@Composable
fun FeatureScreen(viewModel: FeatureViewModel = koinViewModel()) {
    val state by viewModel.uiState.collectAsState()
    val snackbarHost = LocalSnackbarHost.current

    viewModel.sideEffect.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is FeatureSideEffect.ShowSnackbar -> {
                snackbarHost.showSnackbar(sideEffect.message)
            }
            is FeatureSideEffect.NavigateToHome -> {
                // Navigation 처리
            }
        }
    }

    // UI 구현
}
```

## Convention Plugins

| Plugin ID | 용도 |
|-----------|------|
| `bookpin.android.application` | Android 앱 모듈 설정 |
| `bookpin.kmp.compose` | KMP + Compose 설정 |
| `bookpin.ktlint` | KtLint 코드 스타일 검사 |
| `bookpin.serialization` | KotlinX Serialization |

## 빌드 및 실행

```bash
# Android 빌드
./gradlew :android-app:assembleDebug

# 테스트 실행
./gradlew testDebugUnitTest

# KtLint 검사
./gradlew ktlintCheck

# KtLint 자동 수정
./gradlew ktlintFormat
```

## 코드 컨벤션

### 파일 명명 규칙
- Screen: `FeatureScreen.kt`
- ViewModel: `FeatureViewModel.kt`
- State/SideEffect: `Feature{State|SideEffect}.kt`
- Repository: `FeatureRepository.kt` (인터페이스), `FeatureRepositoryImpl.kt` (구현체)
- DataSource: `FeatureRemoteDataSource.kt` (인터페이스), `FeatureRemoteDataSourceImpl.kt` (구현체)

### 패키지 구조
```
com.phase.bookpin
├── {feature}/              # Feature 모듈 (auth, home, etc.)
│   ├── FeatureScreen.kt
│   ├── FeatureViewModel.kt
│   ├── FeatureState.kt
│   ├── FeatureSideEffect.kt
│   └── di/FeatureModule.kt
├── domain/{feature}/       # Domain 모듈
│   └── FeatureRepository.kt
├── data/{feature}/         # Data 모듈
│   └── FeatureRepositoryImpl.kt
└── data/api/{feature}/     # Data-API 모듈
    ├── FeatureRemoteDataSource.kt
    └── FeatureDto.kt
```

### 도메인 모델 규칙
- `model/` 모듈의 도메인 모델(data class)에는 **디폴트 파라미터 값을 사용하지 않는다**
- DTO(`core/data-api`)에서 매퍼를 통해 변환할 때 모든 값을 명시적으로 전달한다

### KtLint 규칙
- Wildcard import 허용
- @Composable 함수 대문자 시작 허용
- 비활성화된 규칙: `no-wildcard-imports`, `filename`, `function-signature`

## 디자인 시스템 사용법

```kotlin
@Composable
fun MyScreen() {
    BookPinTheme {
        Text(
            text = "Hello",
            style = BookPinTheme.typography.bodyLarge,
            color = BookPinTheme.colors.primary
        )
    }
}
```

## Git 워크플로우

- **Main Branch**: `develop`
- **Feature Branch**: `feature/#이슈번호` 또는 `feature/#이슈번호-기능명`
- **Commit Message**: `#이슈번호 : 작업내용`

## CI/CD

GitHub Actions (`ci.yml`):
1. `assembleDebug` - 빌드 검증
2. `testDebugUnitTest` - 유닛 테스트
3. `ktlintCheck` - 코드 스타일 검사

## 주요 파일 경로

| 구분 | 경로 |
|------|------|
| Android 진입점 | `android-app/src/main/java/.../MainActivity.kt` |
| Android Application | `android-app/src/main/java/.../BookPinApplication.kt` |
| iOS 진입점 | `compose-app/src/iosMain/.../getBookPinViewController.kt` |
| iOS Koin Helper | `compose-app/src/iosMain/.../KoinHelper.kt` |
| 공통 App | `compose-app/src/commonMain/.../BookPinApp.kt` |
| App DI Module | `compose-app/src/commonMain/.../di/AppModule.kt` |
| BaseViewModel | `core/common/src/commonMain/.../BaseViewModel.kt` |
| Snackbar | `core/common/src/commonMain/.../snackbar/` |
| 테마 | `designsystem/src/commonMain/.../Theme.kt` |
| 버전 카탈로그 | `gradle/libs.versions.toml` |

## SDK 버전

- Compile SDK: 36
- Min SDK: 26
- Target SDK: 36
- JVM Target: 17

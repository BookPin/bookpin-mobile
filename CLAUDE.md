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
| Image Loading | Coil 3.3.0 |
| Serialization | KotlinX Serialization 1.10.0 |
| Async | KotlinX Coroutines 1.10.2 |
| Logging | Kermit 2.0.8 |

## 프로젝트 구조

```
BookPin/
├── android-app/          # Android 진입점 (MainActivity)
├── iosApp/               # iOS 진입점 (Swift)
├── compose-app/          # 공유 UI 모듈 (Compose Multiplatform)
├── designsystem/         # 디자인 시스템 (Color, Typography, Theme)
├── build-logic/          # Gradle Convention Plugins
│   └── convention/
└── gradle/
    └── libs.versions.toml  # 의존성 버전 관리
```

## 모듈 상세

### android-app
Android 앱 진입점. MainActivity에서 `BookPinApp()` 호출.

### iosApp
iOS 앱 진입점. Swift에서 `getBookPinViewController()` 호출.

### compose-app
플랫폼 공통 UI 모듈.
```
src/
├── commonMain/     # 공통 코드
├── androidMain/    # Android 구현체
└── iosMain/        # iOS 구현체
```

### designsystem
디자인 시스템 모듈.
- `Color.kt`: BookPinColors, LocalBookPinColors
- `Type.kt`: BookPinTypography, LocalBookPinTypography
- `Theme.kt`: BookPinTheme

**주요 색상:**
- Primary: Gold (#C9A87C)
- Secondary: Teal (#9DBEB7)
- Background: Cream (#FFF8F0)
- Text: Brown (#6B5744)

## Clean Architecture 레이어 구조

### 1. Presentation Layer (compose-app)
```
compose-app/src/commonMain/kotlin/com/phase/bookpin/
├── ui/
│   └── feature/
│       ├── FeatureScreen.kt      # @Composable UI
│       ├── FeatureViewModel.kt   # MVI Intent 처리
│       ├── FeatureState.kt       # UI State
│       ├── FeatureIntent.kt      # User Actions
│       └── FeatureSideEffect.kt  # One-time Events
```

### 2. Domain Layer (신규 모듈: domain)
```
domain/src/commonMain/kotlin/com/phase/bookpin/domain/
├── model/           # 비즈니스 모델
├── repository/      # Repository 인터페이스
└── usecase/         # UseCase (비즈니스 로직)
```

### 3. Data Layer (신규 모듈: data)
```
data/src/commonMain/kotlin/com/phase/bookpin/data/
├── repository/      # Repository 구현체
├── datasource/
│   ├── remote/      # API 호출 (Ktor)
│   └── local/       # 로컬 저장소
├── dto/             # Data Transfer Objects
└── mapper/          # DTO <-> Domain Model 변환
```

## MVI 패턴 구현 가이드

### State
```kotlin
@Immutable
data class FeatureState(
    val isLoading: Boolean = false,
    val data: List<Item> = emptyList(),
    val error: String? = null
)
```

### Intent
```kotlin
sealed interface FeatureIntent {
    data object LoadData : FeatureIntent
    data class SelectItem(val id: String) : FeatureIntent
    data object Refresh : FeatureIntent
}
```

### SideEffect
```kotlin
sealed interface FeatureSideEffect {
    data class ShowToast(val message: String) : FeatureSideEffect
    data class Navigate(val route: String) : FeatureSideEffect
}
```

### ViewModel
```kotlin
class FeatureViewModel(
    private val useCase: FeatureUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(FeatureState())
    val state: StateFlow<FeatureState> = _state.asStateFlow()

    private val _sideEffect = Channel<FeatureSideEffect>()
    val sideEffect: Flow<FeatureSideEffect> = _sideEffect.receiveAsFlow()

    fun onIntent(intent: FeatureIntent) {
        when (intent) {
            is FeatureIntent.LoadData -> loadData()
            is FeatureIntent.SelectItem -> selectItem(intent.id)
            is FeatureIntent.Refresh -> refresh()
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            useCase()
                .onSuccess { data ->
                    _state.update { it.copy(isLoading = false, data = data) }
                }
                .onFailure { error ->
                    _state.update { it.copy(isLoading = false, error = error.message) }
                    _sideEffect.send(FeatureSideEffect.ShowToast(error.message ?: "Error"))
                }
        }
    }
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
- State/Intent/SideEffect: `Feature{State|Intent|SideEffect}.kt`
- UseCase: `GetFeatureUseCase.kt`, `UpdateFeatureUseCase.kt`
- Repository: `FeatureRepository.kt` (인터페이스), `FeatureRepositoryImpl.kt` (구현체)

### 패키지 구조
```
com.phase.bookpin
├── ui.feature.{featureName}   # Presentation
├── domain.{featureName}       # Domain
└── data.{featureName}         # Data
```

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
- **Feature Branch**: `feature/#이슈번호`
- **Commit Message**: `#이슈번호 : 작업내용`

## CI/CD

GitHub Actions (`ci.yml`):
1. `assembleDebug` - 빌드 검증
2. `testDebugUnitTest` - 유닛 테스트
3. `ktlintCheck` - 코드 스타일 검사

## 향후 추가 예정 모듈

```
BookPin/
├── domain/           # Domain Layer (UseCase, Model, Repository Interface)
├── data/             # Data Layer (Repository Impl, DataSource, DTO)
├── core/             # 공통 유틸리티
│   ├── common/       # 공통 확장 함수, 유틸
│   ├── network/      # Ktor 설정, API Client
│   └── database/     # 로컬 DB 설정
└── feature/          # Feature별 모듈 (선택적)
    ├── home/
    ├── search/
    └── profile/
```

## 주요 파일 경로

| 구분 | 경로 |
|------|------|
| Android 진입점 | `android-app/src/main/java/.../MainActivity.kt` |
| iOS 진입점 | `compose-app/src/iosMain/.../getBookPinViewController.kt` |
| 공통 App | `compose-app/src/commonMain/.../BookPinApp.kt` |
| 테마 | `designsystem/src/commonMain/.../Theme.kt` |
| 색상 | `designsystem/src/commonMain/.../Color.kt` |
| 타이포그래피 | `designsystem/src/commonMain/.../Type.kt` |
| 버전 카탈로그 | `gradle/libs.versions.toml` |
| Convention Plugin | `build-logic/convention/src/main/java/com/plugin/convention/` |

## SDK 버전

- Compile SDK: 36
- Min SDK: 26
- Target SDK: 36
- JVM Target: 17
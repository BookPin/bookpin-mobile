# BookPin Design System - Figma to Code Mapping Guide

이 문서는 Figma MCP를 통해 디자인을 가져올 때 참고해야 하는 BookPinTheme 매핑 정보입니다.

## Color Mapping (Figma → BookPinTheme)

### Primary Colors
| Figma Hex | Color Name | Theme Token | 용도 |
|-----------|------------|-------------|------|
| `#C9A87C` | Gold | `colors.primary` | 주요 액션, 진행 바, 강조 |
| `#FFF8F0` | Cream | `colors.onPrimary` | Primary 위의 텍스트 |
| `#B8956B` | MutedGold | `colors.primaryContainer` | Primary 컨테이너 |

### Secondary Colors
| Figma Hex | Color Name | Theme Token | 용도 |
|-----------|------------|-------------|------|
| `#9DBEB7` | Teal | `colors.secondary` | 보조 색상, 책 표지 배경 1 |
| `#6B5744` | Brown | `colors.onSecondary` | 제목 텍스트, 주요 텍스트 |
| `#E8B4A0` | Peach | `colors.secondaryContainer` | 책 표지 배경 2 |

### Surface & Background
| Figma Hex | Color Name | Theme Token | 용도 |
|-----------|------------|-------------|------|
| `#FFF8F0` | Cream | `colors.background` | 앱 배경 |
| `#6B5744` | Brown | `colors.onBackground` | 배경 위 텍스트 |
| `#F5EFE6` | LightBeige | `colors.surface` | 카드 배경, 버튼 배경, 진행 바 배경 |
| `#6B5744` | Brown | `colors.onSurface` | Surface 위 텍스트 |
| `#E8DCC4` | Tan | `colors.surfaceVariant` | 그라데이션 카드, "책 추가" 버튼 |
| `#A0826D` | LightBrown | `colors.onSurfaceVariant` | 부가 텍스트, 저자명 |

### Other Colors
| Figma Hex | Color Name | Theme Token | 용도 |
|-----------|------------|-------------|------|
| `#E8DCC4` | Tan | `colors.outline` | 테두리 |
| `#D4B896` | Placeholder | `colors.outlineVariant` | 플레이스홀더 |

### 자주 사용되는 특수 색상
| Figma Hex | 직접 사용 | 용도 |
|-----------|----------|------|
| `#DCC7A8` | `Color(0xFFDCC7A8)` | 그라데이션 하단 색상 |
| `#8B7355` | `Color(0xFF8B7355)` | 인용문 저자 텍스트 |
| `#FFFFFF` 50% | `Color.White.copy(alpha = 0.5f)` | 반투명 배경 |

---

## Typography Mapping (Figma → BookPinTheme)

### Font Family
- **Figma**: Inter, Noto Sans KR
- **Code**: Apple SD Gothic Neo (`AppleSDGothicNeo`) — Normal, Medium, SemiBold, Bold, ExtraBold

### Size Mapping
| Size | Weight | Theme Token | 용도 |
|------|--------|-------------|------|
| 24sp | ExtraBold | `typography.headlineLarge` | 앱 타이틀, 주요 헤딩 |
| 20sp | Bold | `typography.headlineMedium` | 섹션 제목, 책 제목 |
| 18sp | Medium | `typography.headlineSmall` | 입력 필드, 서브 헤딩 |
| 20sp | Bold | `typography.titleLarge` | (현재 미사용, 예비) |
| 16sp | SemiBold | `typography.titleMedium` | 버튼 텍스트, 링크 |
| 14sp | Medium | `typography.titleSmall` | 부가 정보, 소형 텍스트 |
| 16sp | Bold | `typography.bodyLarge` | 인용문 본문 |
| 14sp | Medium | `typography.bodyMedium` | 서브타이틀, 저자 |
| 12sp | Normal | `typography.bodySmall` | 라벨, 설명 텍스트 |
| 14sp | Bold | `typography.labelLarge` | (현재 미사용, 예비) |
| 12sp | Medium | `typography.labelMedium` | 메타 정보, 수치 |
| 11sp | Normal | `typography.labelSmall` | 페이지 수 등 초소형 |

---

## Common UI Patterns

### 카드 그림자
```kotlin
Modifier.shadow(
    elevation = 2.dp,
    shape = RoundedCornerShape(16.dp),
)
```

### 그라데이션 배경 (Daily Quote Card)
```kotlin
Brush.verticalGradient(
    colors = listOf(
        BookPinTheme.colors.surfaceVariant,  // #E8DCC4
        Color(0xFFDCC7A8),                    // 하단 색상
    ),
)
```

### 원형 버튼/아이콘 배경
```kotlin
Modifier.background(
    color = BookPinTheme.colors.surface,  // #F5EFE6
    shape = CircleShape,
)
```

### 진행 바
```kotlin
// 배경
BookPinTheme.colors.surface  // #F5EFE6
// 채움
BookPinTheme.colors.primary  // #C9A87C
// 높이: 6.dp
// 모양: CircleShape
```

---

## Figma Node ID 참고

화면을 구현할 때 Figma에서 node ID를 추출하여 `get_design_context` 도구로 상세 정보를 가져올 수 있습니다.

URL 형식: `https://figma.com/design/:fileKey/:fileName?node-id=1-2`
- node-id `1-2` → nodeId `1:2`로 변환

---

## 코드 사용 예시

```kotlin
@Composable
fun ExampleCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(2.dp, RoundedCornerShape(16.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        BookPinTheme.colors.surfaceVariant,
                        Color(0xFFDCC7A8),
                    ),
                ),
                shape = RoundedCornerShape(16.dp),
            )
            .padding(24.dp),
    ) {
        Text(
            text = "제목",
            style = BookPinTheme.typography.headlineMedium,
            color = BookPinTheme.colors.onSurface,
        )
    }
}
```

---

## 주의사항

1. **Tailwind CSS 변환 필수**: Figma MCP는 React + Tailwind 코드를 생성하므로, 반드시 Compose Multiplatform + BookPinTheme으로 변환해야 합니다.

2. **색상 직접 사용 금지**: Figma에서 가져온 hex 색상을 직접 사용하지 말고, 위 매핑표를 참고하여 `BookPinTheme.colors.*`를 사용하세요.

3. **`.copy()` 사용 금지**: 가장 가까운 토큰을 선택하세요. fontFamily 변경이 필요한 예외적 경우만 `.copy()` 허용됩니다.

4. **이미지 에셋**: Figma의 localhost URL 이미지는 임시 참고용이며, 실제 구현 시 `composeResources/drawable/`에 에셋을 추가해야 합니다.
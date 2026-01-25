---
description: PR 리뷰를 코멘트로 남기는 커맨드
allowed-tools: Read, Bash, Grep, Glob, WebFetch
---

# Review PR

PR의 변경사항을 리뷰하고 GitHub에 코멘트를 남깁니다.

## 현재 PR 정보

- 브랜치: !`git branch --show-current`
- base 브랜치: develop

## 리뷰 기준

`.claude/skills/android-pr-review/SKILL.md`를 참고하여 리뷰합니다.

## 우선순위 분류

### High Priority
- 메모리 누수 위험
- 크래시 위험
- 멀티플랫폼 위반
- **반드시 수정 필요** - PR 머지 전 해결 필수

### Medium Priority
- 성능 이슈 (불필요한 리컴포지션 등)
- 설계 이슈
- **개선 권장** - 가능하면 이번 PR에서 수정

### Low Priority
- 코드 품질
- 유지보수성
- **참고 사항** - 다음 PR에서 개선 가능

## 실행 절차

1. PR 변경사항 확인
   ```bash
   git diff develop...HEAD --name-only
   git diff develop...HEAD
   ```

2. 변경된 파일 분석
   - 우선순위별로 이슈 분류

3. GitHub 코멘트 작성

   **High Priority 이슈**: 각 이슈별로 개별 라인 코멘트
   ```bash
   gh api repos/{owner}/{repo}/pulls/{pr_number}/comments \
     -f body="<details><summary>:rotating_light: [High] 이슈 제목</summary>

   **문제점**
   설명...

   **권장 수정**
   \`\`\`kotlin
   수정 코드
   \`\`\`
   </details>" \
     -f commit_id="$(git rev-parse HEAD)" \
     -f path="파일경로" \
     -f line=라인번호
   ```

   **Medium/Low Priority 이슈**: 전체 PR 코멘트로 작성
   ```bash
   gh pr comment --body "## PR Review

   ### Medium Priority :warning:

   #### 파일명.kt (L10-15)
   **카테고리**: 성능
   설명...

   ### Low Priority :information_source:

   #### 파일명.kt (L20)
   **카테고리**: 유지보수성
   설명...

   ---
   *리뷰 기준: .claude/skills/android-pr-review/SKILL.md"
   ```

4. 리뷰 완료 후 요약 출력

## 코멘트 형식

### High Priority (개별 코멘트 - 토글)
```markdown
<details>
<summary>:rotating_light: [High] 이슈 제목</summary>

**카테고리**
- 메모리 누수
- 크래시 위험
- 멀티플랫폼 위반

**문제점**
현재 코드에서 발생할 수 있는 문제와 그 영향에 대해 설명합니다.
(예: 특정 조건에서 크래시가 발생할 수 있음, 생명주기 종료 후 객체가 유지됨 등)

**권장 수정**
```kotlin
수정된 코드 예시
```

### Medium/Low Priority (전체 코멘트)
```markdown
## PR Review

### Medium Priority :warning:

#### 파일명.kt (L10-15)
**카테고리**: 성능

**문제점**
현재 코드의 문제점 설명

**권장 수정**
수정 방법 또는 코드 예시

---

### Low Priority :information_source:

#### 파일명.kt (L20)
**카테고리**: 유지보수성

**문제점**
현재 코드의 문제점 설명

---

위 조건을 만족한다면, 해당 구현은 충분히 수용 가능합니다.

*리뷰 기준: .claude/skills/android-pr-review/SKILL.md*
```

## 필수 규칙

**모든 이슈에 반드시 권장 수정 사항을 함께 제공해야 합니다.**

- 문제점만 지적하지 말고, 구체적인 수정 코드나 방법을 제시
- 코드 예시는 실제 적용 가능한 형태로 작성
- 수정 방향이 여러 개인 경우 옵션으로 제시

```markdown
**권장 수정**
```kotlin
// 수정 전
val list = mutableListOf<Item>()

// 수정 후
val list = remember { mutableStateListOf<Item>() }
```
```

## 주의사항

- 개인 취향 기반의 스타일 지적 지양
- 불필요한 아키텍처 제안 지양
- 실제 운영 환경에서 문제가 될 수 있는 지점 우선 검토
- **권장 수정 없이 문제점만 지적하는 코멘트 금지**
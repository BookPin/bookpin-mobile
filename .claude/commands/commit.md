---
description: 현재 변경사항을 작업 단위별로 나눠서 커밋. 이슈 번호 자동 추출
allowed-tools: Read, Write, Edit, Bash, Grep, Glob
---

# Commit

현재 변경사항을 작업 단위별로 나눠서 커밋합니다.

## 현재 상태

- 브랜치: !`git branch --show-current`
- 변경 파일:
!`git status --short`

- 최근 커밋:
!`git log --oneline -3`

## 커밋 메시지 형식

```
#이슈번호 : 제목

- 상세 설명 (선택)

Co-Authored-By: Claude <noreply@anthropic.com>
```

## 규칙

1. **이슈 번호**: 현재 브랜치에서 이슈 번호를 추출 (예: `feature/#2` → `#2`)
2. **제목**: 한글로 작성, 명확하고 간결하게
3. **작업 단위**: 논리적으로 분리된 변경사항은 별도 커밋으로 분리
   - 모듈/설정 변경
   - 리소스 파일 추가/이동
   - 각 기능별 코드 (Color, Type, Theme 등)
   - 의존성 연결 및 적용
4. **Co-Authored-By**: 항상 마지막에 추가

## 실행 절차

### 기본 모드 (단일 브랜치)

1. 위의 현재 상태를 분석
2. 작업 단위별로 `git add` 후 커밋
3. 커밋 후 `git status`로 결과 확인

### 브랜치 분리 모드 (PR 분리)

**사용자가 "PR 분리", "브랜치 나눠서", "분리해서 커밋" 등을 요청한 경우:**

1. **변경사항 분석**: 현재 변경사항을 논리적 단위로 분류
   - 모듈 구조 변경 (settings.gradle.kts, build.gradle.kts, 새 모듈)
   - Feature 구현 (auth, home 등)
   - Core/Common 유틸리티
   - DI 설정 (Koin 모듈)
   - 인프라 (네트워크, 로컬 저장소)

2. **브랜치 명명 규칙**: `feature/#이슈번호-기능명`
   - 예: `feature/#8-clean-arch`, `feature/#8-kakao`, `feature/#8-snackbar`

3. **실행 절차**:
   ```bash
    # 0. 현재 변경사항 전체 stash (untracked 포함)
    git stash push --include-untracked -m "split-pr-base"
    
    # 1. develop 기준으로 첫 번째 브랜치 생성
    git checkout develop
    git checkout -b feature/#이슈번호-첫기능
    
    # 2. 필요한 변경사항만 복원
    git stash pop
    
    # 3. 첫 기능에 해당하는 파일만 커밋
    git add <첫기능 관련 파일>
    git commit -m "#이슈번호 : 첫 기능 설명"
    
    # 4. 남은 변경사항 다시 stash (안전)
    git stash push --include-untracked -m "split-pr-remain"
    
    # 5. 다음 브랜치 생성 (이전 브랜치 기반, 의존성 유지)
    git checkout -b feature/#이슈번호-다음기능
    
    # 6. stash pop 후 해당 기능만 커밋
    git stash pop
    git add <다음기능 관련 파일>
    git commit -m "#이슈번호 : 다음 기능 설명"
    
    # 7. 필요 시 다시 stash 후 반복
    git stash push --include-untracked -m "split-pr-remain"
   ```

4. **브랜치 분리 기준**:
   | 분류 | 브랜치 접미사 | 포함 파일 |
   |------|--------------|----------|
   | Clean Architecture 모듈 | `-clean-arch` | model/, domain/, data/, data-api/, data-remote/, data-local/ |
   | 소셜 로그인 | `-kakao`, `-apple` | data-auth/ |
   | 공통 유틸 | `-common` | common/snackbar/, common/extensions/ |
   | DI 설정 | `-koin` | */di/, BookPinApplication.kt, KoinHelper.kt |
   | Feature | `-auth`, `-home` | auth/, home/ 등 Feature 모듈 |

5. **결과 확인**:
   ```bash
   git branch -a | grep "feature/#이슈번호"
   ```

## 주의사항

- 브랜치 분리 시 의존성 순서 고려 (예: clean-arch → kakao → koin)
- 각 브랜치는 독립적으로 빌드 가능해야 함
- `.claude/settings.local.json` 등 개인 설정 파일은 커밋하지 않음
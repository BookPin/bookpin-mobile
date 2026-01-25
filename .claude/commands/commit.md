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

1. 위의 현재 상태를 분석
2. 작업 단위별로 `git add` 후 커밋
3. 커밋 후 `git status`로 결과 확인
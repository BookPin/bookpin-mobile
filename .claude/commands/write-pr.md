---
description: PR 내용을 템플릿에 맞게 자동 작성. 브랜치 변경사항 분석 후 PR body 생성
allowed-tools: Read, Bash, Grep, Glob
---

# Write PR

PR 템플릿에 맞게 PR 내용을 작성합니다.

## 현재 상태

- 브랜치: !`git branch --show-current`
- Base 브랜치: !`CURRENT=$(git branch --show-current) && BASE=$(git reflog --format='%gs' | grep -m1 "to ${CURRENT}$" | sed 's/checkout: moving from \([^ ]*\) to .*/\1/') && echo "${BASE:-develop}"`

### 변경된 파일
!`CURRENT=$(git branch --show-current) && BASE=$(git reflog --format='%gs' | grep -m1 "to ${CURRENT}$" | sed 's/checkout: moving from \([^ ]*\) to .*/\1/') && BASE="${BASE:-develop}" && git diff "$BASE"...HEAD --stat`

### 커밋 목록
!`CURRENT=$(git branch --show-current) && BASE=$(git reflog --format='%gs' | grep -m1 "to ${CURRENT}$" | sed 's/checkout: moving from \([^ ]*\) to .*/\1/') && BASE="${BASE:-develop}" && git log "$BASE"..HEAD --oneline`

### 상세 변경 내용
!`CURRENT=$(git branch --show-current) && BASE=$(git reflog --format='%gs' | grep -m1 "to ${CURRENT}$" | sed 's/checkout: moving from \([^ ]*\) to .*/\1/') && BASE="${BASE:-develop}" && git diff "$BASE"...HEAD --name-status`

## PR 템플릿

```markdown
## 1. 📄 관련된 이슈 및 소개
- 관련 이슈: #이슈번호 (브랜치명에서 추출)
- 소개: (커밋 내용을 바탕으로 작성)

## 2. 🔥 변경된 점
- (변경된 파일과 커밋 내용을 분석하여 bullet point로 정리)

## 3. ✅ 필수 체크 사항
- [ ] 빌드 확인
- [ ] 테스트 확인
- [ ] 코드 리뷰 요청

## 4. 📸 작업물 사진 공유(선택)
(스크린샷이 필요한 경우 추가)

## 5. 💡 알게된 혹은 궁금한 사항
(특이사항이나 논의 필요한 내용)
```

## 규칙

1. **이슈 번호**: 브랜치명에서 추출 (예: `feature/#2` → `#2`)
2. **변경된 점**: 커밋 메시지와 diff를 분석하여 의미있는 변경사항 정리
3. **체크리스트**: 기본 항목 포함
4. **Base 브랜치**: 위에서 감지된 Base 브랜치를 `gh pr create --base` 옵션에 사용
5. **이슈 연결**: 브랜치명에서 이슈 번호 추출 후 `closes #이슈번호`를 PR body에 포함
6. **Assignee**: PR 생성/수정 시 `--assignee jeongjaino` 옵션 사용
7. **라벨 자동 분류**: 커밋 내용과 변경사항을 분석하여 아래 기준으로 라벨 1개를 선택하고 `--label` 옵션 사용
   - `신규 기능`: 새로운 화면, 기능, API 연동 등 기존에 없던 기능 추가
   - `버그 수정`: 기존 기능의 오류 수정, 크래시 해결
   - `기타 작업`: 리팩토링, 의존성 추가, 설정 변경, 문서 작업 등 기능과 무관한 변경
8. **적용**: `gh pr create --base {BASE_BRANCH} --assignee jeongjaino --label "{라벨명}"` 또는 `gh pr edit [PR번호] --body "$PR_BODY" --add-assignee jeongjaino --add-label "{라벨명}"` 사용

## 실행 절차

1. 위의 현재 상태를 분석
2. PR 템플릿 형식에 맞게 내용 작성
3. `gh pr edit` 또는 `gh pr create`로 적용
4. PR URL 반환
#!/usr/bin/env bash
set -euo pipefail

# KtLint Git pre-commit hook 설치만 수행
SCRIPT_DIR="$(cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &>/dev/null && pwd)"
GRADLEW_PATH="$SCRIPT_DIR/../gradlew"
GIT_HOOK_DIR="$SCRIPT_DIR/../.git/hooks"
KTLINT_HOOK_FILE="$GIT_HOOK_DIR/pre-commit"

# gradlew 존재 확인
if [[ ! -x "$GRADLEW_PATH" ]]; then
  echo "❌ gradlew not found or not executable at: $GRADLEW_PATH"
  exit 1
fi

# 훅 디렉터리 보장
mkdir -p "$GIT_HOOK_DIR"

# KtLint pre-commit 훅 설치 (ktlint-gradle 플러그인의 태스크 사용)
# 참고 태스크: addKtlintCheckGitPreCommitHook
echo "▶ Installing KtLint pre-commit hook via Gradle task..."
"$GRADLEW_PATH" addKtlintCheckGitPreCommitHook

# 실행 권한 부여
if [[ -f "$KTLINT_HOOK_FILE" ]]; then
  chmod +x "$KTLINT_HOOK_FILE"
else
  echo "⚠️ KtLint hook file not found at $KTLINT_HOOK_FILE (task may have different output)."
  exit 1
fi

echo "************************************************"
echo "           KtLint hook installed ✅             "
echo "************************************************"
echo "Install path: $KTLINT_HOOK_FILE"

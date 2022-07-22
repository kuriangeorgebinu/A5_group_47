#!/usr/bin/env bash

mkdir -p ./docker_results

DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" >/dev/null 2>&1 && pwd)"

exit_code=0
for i in {0..35}; do
  echo " ------ Test Case: $i ----"
  tc="00$i"
  if "$DIR/test.sh" ${tc:(-2):2}; then
    echo "PASSED ✓"
  else
    echo "FAILED ✘"
    exit_code=1
  fi
done

exit $exit_code

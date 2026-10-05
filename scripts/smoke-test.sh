#!/usr/bin/env sh
set -eu
echo "Health"
curl -fsS http://localhost:8080/health
echo

echo "Authenticated list"
curl -fsS -u demo:demo123 http://localhost:8080/api/payments
echo

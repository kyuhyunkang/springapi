#!/bin/bash

BASE_URL="http://localhost:8081"

echo "=== Like Post ==="
curl -X POST "$BASE_URL/api/v1/posts/1/like" \
  -b cookies.txt \
  -H "Content-Type: application/json"

echo ""
echo ""

echo "=== Unlike Post ==="
curl -X DELETE "$BASE_URL/api/v1/posts/1/like" \
  -b cookies.txt \
  -H "Content-Type: application/json"

echo ""
echo ""

echo "=== Check Like Status ==="
curl -X GET "$BASE_URL/api/v1/posts/1/like" \
  -b cookies.txt \
  -H "Content-Type: application/json"

echo ""

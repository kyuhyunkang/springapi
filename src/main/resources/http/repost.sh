#!/bin/bash

BASE_URL="http://localhost:8080"

echo "=== Repost API ==="

echo "1. Create Repost"
curl -X POST "$BASE_URL/api/reposts" \
  -H "Content-Type: application/json" \
  -b cookies.txt \
  -d '{"repostId": 1}'
echo -e "\n"

echo "2. Get Repost by ID"
curl -X GET "$BASE_URL/api/reposts/4" \
  -b cookies.txt
echo -e "\n"

echo "3. Get Reposts by Post ID (with pagination)"
curl -X GET "$BASE_URL/api/posts/1/reposts?page=0&size=10" \
  -b cookies.txt
echo -e "\n"

echo "4. Delete Repost"
curl -X DELETE "$BASE_URL/api/reposts/4" \
  -b cookies.txt
echo -e "\n"

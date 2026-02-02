#!/bin/bash

BASE_URL="http://localhost:8080"

echo "=== Reply API ==="

echo "1. Create Reply"
curl -X POST "$BASE_URL/api/replies" \
  -H "Content-Type: application/json" \
  -b cookies.txt \
  -d '{"content": "This is a reply to the post", "parentId": 1}'
echo -e "\n"

echo "2. Get Reply by ID"
curl -X GET "$BASE_URL/api/replies/2" \
  -b cookies.txt
echo -e "\n"

echo "3. Get Replies by Post ID (with pagination)"
curl -X GET "$BASE_URL/api/posts/1/replies?page=0&size=10" \
  -b cookies.txt
echo -e "\n"

echo "4. Update Reply"
curl -X PUT "$BASE_URL/api/replies/2" \
  -H "Content-Type: application/json" \
  -b cookies.txt \
  -d '{"content": "Updated reply content"}'
echo -e "\n"

echo "5. Delete Reply"
curl -X DELETE "$BASE_URL/api/replies/2" \
  -b cookies.txt
echo -e "\n"

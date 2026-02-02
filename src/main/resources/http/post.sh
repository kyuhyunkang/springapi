#!/bin/bash

BASE_URL="http://localhost:8081"

echo "=== Post API ==="

echo "1. Create Post"
curl -X POST "$BASE_URL/api/posts" \
  -H "Content-Type: application/json" \
  -b cookies.txt \
  -d '{"content": "Hello, this is my first post!"}'
echo -e "\n"

echo "2. Get Post by ID"
curl -X GET "$BASE_URL/api/posts/1" \
  -b cookies.txt
echo -e "\n"

echo "3. Get All Posts (with pagination)"
curl -X GET "$BASE_URL/api/posts?page=0&size=10" \
  -b cookies.txt
echo -e "\n"

echo "4. Get Posts by User ID"
curl -X GET "$BASE_URL/api/users/1/posts?page=0&size=10" \
  -b cookies.txt
echo -e "\n"

echo "5. Update Post"
curl -X PUT "$BASE_URL/api/posts/1" \
  -H "Content-Type: application/json" \
  -b cookies.txt \
  -d '{"content": "Updated post content"}'
echo -e "\n"

echo "6. Delete Post"
curl -X DELETE "$BASE_URL/api/posts/1" \
  -b cookies.txt
echo -e "\n"

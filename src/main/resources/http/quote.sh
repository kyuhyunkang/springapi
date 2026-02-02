#!/bin/bash

BASE_URL="http://localhost:8080"

echo "=== Quote API ==="

echo "1. Create Quote"
curl -X POST "$BASE_URL/api/quotes" \
  -H "Content-Type: application/json" \
  -b cookies.txt \
  -d '{"content": "This is my comment on the quoted post", "quoteId": 1}'
echo -e "\n"

echo "2. Get Quote by ID"
curl -X GET "$BASE_URL/api/quotes/3" \
  -b cookies.txt
echo -e "\n"

echo "3. Get Quotes by Post ID (with pagination)"
curl -X GET "$BASE_URL/api/posts/1/quotes?page=0&size=10" \
  -b cookies.txt
echo -e "\n"

echo "4. Update Quote"
curl -X PUT "$BASE_URL/api/quotes/3" \
  -H "Content-Type: application/json" \
  -b cookies.txt \
  -d '{"content": "Updated quote content"}'
echo -e "\n"

echo "5. Delete Quote"
curl -X DELETE "$BASE_URL/api/quotes/3" \
  -b cookies.txt
echo -e "\n"

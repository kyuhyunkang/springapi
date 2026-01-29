#!/bin/bash

BASE_URL="http://localhost:8081"

echo "=== User 1 Signup ==="
curl -X POST "$BASE_URL/api/v1/users/signup" \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser1",
    "password": "password123"
  }'
echo -e "\n"

echo "=== User 2 Signup ==="
curl -X POST "$BASE_URL/api/v1/users/signup" \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser2",
    "password": "password123"
  }'
echo -e "\n"

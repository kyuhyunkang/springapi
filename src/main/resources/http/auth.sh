#!/bin/bash

BASE_URL="http://localhost:8081"

# Login
echo "=== Login ==="
curl -X POST "$BASE_URL/api/v1/auth/login" \
  -H "Content-Type: application/json" \
  -c cookies.txt \
  -d '{
    "username": "testuser",
    "password": "password123"
  }'
echo -e "\n"

# Get current user info (authenticated)
echo "=== Get Current User ==="
curl -X GET "$BASE_URL/api/v1/auth/me" \
  -b cookies.txt
echo -e "\n"

# Logout
echo "=== Logout ==="
curl -X POST "$BASE_URL/api/v1/auth/logout" \
  -b cookies.txt
echo -e "\n"

# Verify logout (should fail with 401)
echo "=== Verify Logout (should fail) ==="
curl -X GET "$BASE_URL/api/v1/auth/me" \
  -b cookies.txt
echo -e "\n"

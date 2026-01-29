#!/bin/bash

BASE_URL="http://localhost:8081"

# Login
echo "=== Login ==="
curl -X POST "$BASE_URL/api/v1/auth/login" \
  -H "Content-Type: application/json" \
  -c cookies.txt \
  -d '{
    "username": "testuser1",
    "password": "password123"
  }' \
  -w "\nHTTP Status: %{http_code}\n"
echo ""

# Get current user info (authenticated)
echo "=== Get Current User ==="
curl -X GET "$BASE_URL/api/v1/auth/me" \
  -b cookies.txt \
  -w "\nHTTP Status: %{http_code}\n"
echo ""

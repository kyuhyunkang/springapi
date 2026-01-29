#!/bin/bash

BASE_URL="http://localhost:8081"

echo "=== Follow API ==="

echo ""
echo "1. Follow user (targetUserId=2 - testuser2)"
echo -n "Response: "
curl -s -X POST "$BASE_URL/api/v1/follow/2" \
  -b cookies.txt \
  -w "\nHTTP Status: %{http_code}\n"
echo ""

echo "2. Get my followees"
echo -n "Response: "
curl -s -X GET "$BASE_URL/api/v1/followees" \
  -b cookies.txt \
  -H "Content-Type: application/json" \
  -w "\nHTTP Status: %{http_code}\n"
echo ""

echo "3. Get my followees count"
echo -n "Response: "
curl -s -X GET "$BASE_URL/api/v1/followees/count" \
  -b cookies.txt \
  -H "Content-Type: application/json" \
  -w "\nHTTP Status: %{http_code}\n"
echo ""

echo "4. Get my followers"
echo -n "Response: "
curl -s -X GET "$BASE_URL/api/v1/followers" \
  -b cookies.txt \
  -H "Content-Type: application/json" \
  -w "\nHTTP Status: %{http_code}\n"
echo ""

echo "5. Get my followers count"
echo -n "Response: "
curl -s -X GET "$BASE_URL/api/v1/followers/count" \
  -b cookies.txt \
  -H "Content-Type: application/json" \
  -w "\nHTTP Status: %{http_code}\n"
echo ""

echo "6. Unfollow user (targetUserId=2 - testuser2)"
echo -n "Response: "
curl -s -X DELETE "$BASE_URL/api/v1/follow/2" \
  -b cookies.txt \
  -w "\nHTTP Status: %{http_code}\n"
echo ""

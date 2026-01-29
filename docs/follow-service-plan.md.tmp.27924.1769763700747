# Follow 서비스 구현 계획

## 1. 엔티티

### Follow (extends BaseEntity)
- `id` (Long): PK
- `follower` (User): 팔로우하는 사용자
- `following` (User): 팔로우 대상 사용자
- 복합 유니크 제약: (follower_id, following_id)
- BaseEntity 상속: `createdAt`, `updatedAt`, `deletedAt` 자동 포함

### FollowCount (extends BaseEntity)
- `id` (Long): PK
- `user` (User): 대상 사용자 (unique)
- `followersCount` (Long): 팔로워 수 (default: 0)
- `followeesCount` (Long): 팔로잉 수 (default: 0)
- BaseEntity 상속: `createdAt`, `updatedAt`, `deletedAt` 자동 포함

## 2. Repository

### FollowRepository
- `existsByFollowerAndFollowing()`: 팔로우 여부 확인
- `findByFollower()`: 팔로잉 목록
- `findByFollowing()`: 팔로워 목록
- `deleteByFollowerAndFollowing()`: 언팔로우

### FollowCountRepository
- `findByUser(User user)`: 사용자의 팔로우 카운트 조회
- `findByUserId(Long userId)`: 사용자 ID로 팔로우 카운트 조회
- `incrementFollowersCount(Long userId)`: 팔로워 수 원자적 증가 (@Query UPDATE)
- `decrementFollowersCount(Long userId)`: 팔로워 수 원자적 감소 (@Query UPDATE)
- `incrementFolloweesCount(Long userId)`: 팔로잉 수 원자적 증가 (@Query UPDATE)
- `decrementFolloweesCount(Long userId)`: 팔로잉 수 원자적 감소 (@Query UPDATE)

## 3. Service
- `follow(followerId, followingId)`: 팔로우 + FollowCount 원자적 증가
- `unfollow(followerId, followingId)`: 언팔로우 + FollowCount 원자적 감소
- `getFollowers(userId)`: 팔로워 목록
- `getFollowees(userId)`: 팔로잉 목록
- `getFollowersCount(userId)`: 팔로워 수 조회
- `getFolloweesCount(userId)`: 팔로잉 수 조회

### 동시성 처리
- 팔로우/언팔로우 시 FollowCount 업데이트는 `@Query`를 사용한 원자적 UPDATE 쿼리로 처리
- 갱신 손실(Lost Update) 문제 방지
- 예시: `UPDATE follow_count SET followers_count = followers_count + 1 WHERE user_id = :userId`

## 4. API (사용자 정보는 쿠키/세션에서 획득)
| Method | URI | 설명 |
|--------|-----|------|
| POST | `/api/v1/follow/{targetUserId}` | 팔로우 |
| DELETE | `/api/v1/follow/{targetUserId}` | 언팔로우 |
| GET | `/api/v1/followers` | 내 팔로워 목록 |
| GET | `/api/v1/followees` | 내 팔로잉 목록 |
| GET | `/api/v1/followers/count` | 내 팔로워 수 |
| GET | `/api/v1/followees/count` | 내 팔로잉 수 |

## 5. DTO (FollowCountResponse)
```json
{
  "followersCount": 100,
  "followeesCount": 50
}
```

## 6. 예외
- 자기 자신 팔로우 불가
- 중복 팔로우 불가
- 존재하지 않는 사용자 팔로우 불가

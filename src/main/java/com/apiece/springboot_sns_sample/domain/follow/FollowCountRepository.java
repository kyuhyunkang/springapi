package com.apiece.springboot_sns_sample.domain.follow;

import com.apiece.springboot_sns_sample.domain.user.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FollowCountRepository extends JpaRepository<FollowCount, Long> {

    Optional<FollowCount> findByUser(User user);

    Optional<FollowCount> findByUserId(Long userId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE FollowCount fc SET fc.followersCount = fc.followersCount + 1 WHERE fc.user.id = :userId")
    void incrementFollowersCount(@Param("userId") Long userId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE FollowCount fc SET fc.followersCount = fc.followersCount - 1 WHERE fc.user.id = :userId")
    void decrementFollowersCount(@Param("userId") Long userId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE FollowCount fc SET fc.followeesCount = fc.followeesCount + 1 WHERE fc.user.id = :userId")
    void incrementFolloweesCount(@Param("userId") Long userId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE FollowCount fc SET fc.followeesCount = fc.followeesCount - 1 WHERE fc.user.id = :userId")
    void decrementFolloweesCount(@Param("userId") Long userId);
}

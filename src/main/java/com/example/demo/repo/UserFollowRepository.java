package com.example.demo.repo;

import com.example.demo.entity.UserFollow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserFollowRepository extends JpaRepository<UserFollow, Long> {
    UserFollow findByFollowerAndFollowee(String follower, String followee);
}
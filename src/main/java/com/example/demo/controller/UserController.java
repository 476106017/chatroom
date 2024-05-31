package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.entity.UserFollow;
import com.example.demo.repo.UserFollowRepository;
import com.example.demo.repo.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor
public class UserController {

    UserRepository userRepository;

    UserFollowRepository userFollowRepository;

    PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ModelAndView registerUser(@RequestParam String username, @RequestParam String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("USER");
        userRepository.save(user);
        return new ModelAndView("redirect:/login");
    }

    @PostMapping("/followUser")
    public void followUser(@RequestParam String followee) {
        final String name = SecurityContextHolder.getContext().getAuthentication().getName();

        UserFollow userFollow = new UserFollow();
        userFollow.setFollower(name);
        userFollow.setFollowee(followee);
        userFollowRepository.save(userFollow);
    }

    @PostMapping("/unfollowUser")
    public void unfollowUser(@RequestParam String followee) {
        final String name = SecurityContextHolder.getContext().getAuthentication().getName();

        UserFollow userFollow = userFollowRepository.findByFollowerAndFollowee(name, followee);
        if (userFollow != null) {
            userFollowRepository.delete(userFollow);
        }
    }
}

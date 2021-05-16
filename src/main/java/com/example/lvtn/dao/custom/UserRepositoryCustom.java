package com.example.lvtn.dao.custom;

import com.example.lvtn.dom.User;

import java.util.List;

public interface UserRepositoryCustom {
    List<User> findUsersByUsername(String username);

    List<User> findUsersByEmail(String email);

    User findUsersById(Long userId);

    List<User> findUsersWithPaging(Long pageIndex, Long pageSize);
}

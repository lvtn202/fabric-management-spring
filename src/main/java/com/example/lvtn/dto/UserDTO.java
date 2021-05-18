package com.example.lvtn.dto;

import com.example.lvtn.dom.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String sex;

    public UserDTO() {
    }

    public UserDTO(Long id, String firstName, String lastName, String email, String sex) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }

    static public UserDTO convertUserToUserDTO(User user){
        return new UserDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getSex()
        );
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

public class user {
    private int userId;
    private String name;
    private String email;
    private String password;
    private String userType; // "Manager" or "Operator"

    // Constructor is private to force the use of the Builder
    private user(UserBuilder builder) {
        this.userId = builder.userId;
        this.name = builder.name;
        this.email = builder.email;
        this.password = builder.password;
        this.userType = builder.userType;
    }

    // Getters only
    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUserType() {
        return userType;
    }

    // 🔧 Builder Class (Design Pattern)
    public static class UserBuilder {
        private int userId;
        private String name;
        private String email;
        private String password;
        private String userType;

        public UserBuilder setUserId(int userId) {
            this.userId = userId;
            return this;
        }

        public UserBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public UserBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder setUserType(String userType) {
            this.userType = userType;
            return this;
        }

        public user build() {
            return new user(this);
        }
    }
}


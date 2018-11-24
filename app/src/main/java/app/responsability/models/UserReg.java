package app.responsability.models;

import app.responsability.models.component.Sex;

public class UserReg {

    private String email;

    private String password;

    private String profilePic;

    private String name;

    private Integer age;

    private Sex sex;

    private String locationName;

    private Double latitude;

    private Double longitude;

    private Double radius;

    public UserReg() {}

    public UserReg(String email, String password, String profilePic, String name, Integer age, Sex sex, String locationName, Double latitude, Double longitude, Double radius) {
        this.email = email;
        this.password = password;
        this.profilePic = profilePic;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.locationName = locationName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getRadius() {
        return radius;
    }

    public void setRadius(Double radius) {
        this.radius = radius;
    }
}

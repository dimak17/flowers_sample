package com.impltech.service.dto;

import com.impltech.domain.Position;

import java.util.List;

/**
 * Created by platon
 */

public class EmployeeDTO {

    private Long id;

    private String firstName;

    private String secondName;

    private String email;

    private String phone;

    private String whatsUp;

    private String skype;


//    @JsonDeserialize(using = PositionListDeserializer.class)
    private List<Position> positions;

    public EmployeeDTO() {
    }

    public EmployeeDTO(Long id, String firstName, String secondName, String email, String phone, String whatsUp, String skype, List<Position> positions) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.phone = phone;
        this.whatsUp = whatsUp;
        this.skype = skype;
        this.positions = positions;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWhatsUp() {
        return whatsUp;
    }

    public void setWhatsUp(String whatsUp) {
        this.whatsUp = whatsUp;
    }

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

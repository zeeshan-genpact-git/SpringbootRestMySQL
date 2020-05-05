package com.example.restservices.RestServicesExample.beans;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@ApiModel(description = "User Information")
@Entity
public class UserEntity {

    @Size(min = 2, message="Name should have at least 3 characters")
    @NotNull(message = "User name can't be null")
    @ApiModelProperty(notes = "Name length should be more than 1", required = true)
    private String name;

    @Id
    @GeneratedValue
    private Integer id;

    @Past(message = "DOB should be less than the current date")
    @NotNull(message = "User DOB can't be null")
    @ApiModelProperty(notes = "DOB should be less than current date", required = true)
    private Date DOB;

    @OneToMany(mappedBy = "user")
    private List<Post> post;

    public UserEntity() {
    }

    public UserEntity(String name, Integer id, Date DOB) {
        this.name = name;
        this.id = id;
        this.DOB = DOB;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDOB() {
        return DOB;
    }

    public void setDOB(Date DOB) {
        this.DOB = DOB;
    }

    public List<Post> getPost() {
        return post;
    }

    public void setPost(List<Post> post) {
        this.post = post;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", DOB=" + DOB +
                ", post=" + post +
                '}';
    }
}

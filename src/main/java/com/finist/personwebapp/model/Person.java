package com.finist.personwebapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "persons")
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
    private Integer id;

    @Column(name = "`name`")
    @Getter @Setter
    private String name;

    @Column(name = "age")
    @Getter @Setter
    private Integer age;

    @Column(name = "address")
    @Getter @Setter
    private String address;

    @Column(name = "work")
    @Getter @Setter
    private String work;

    public Person(PersonRequest requestBody) {
        this.age= requestBody.age;
        this.address = requestBody.address;
        this.work = requestBody.work;
        this.name = requestBody.name;
    }
}

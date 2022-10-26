package com.finist.personwebapp.model;

public class PersonResponse {

    public PersonResponse(Person person){
        this.id = person.getId();
        this.name = person.getName();
        this.address = person.getAddress();
        this.age = person.getAge();
        this.work = person.getWork();
    }

    public Integer id;
    public String name;
    public Integer age;
    public String address;
    public String work;
}

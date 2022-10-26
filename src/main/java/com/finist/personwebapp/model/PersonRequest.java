package com.finist.personwebapp.model;


public class PersonRequest {
    public PersonRequest(){

    }
    public PersonRequest(Person person){
        this.name = person.getName();
        this.address = person.getAddress();
        this.age = person.getAge();
        this.work = person.getWork();
    }

    public String name;
    public Integer age;
    public String address;
    public String work;
}

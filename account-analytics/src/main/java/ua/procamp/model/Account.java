package ua.procamp.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Account {
    private Long id;

    private String firstname;
    private String lastname;
    private String email;
    private LocalDate birthday;
    private String sex;
    private LocalDate creationDate;
    private BigDecimal balance;

    public Account(long id, String firstname, String lastname, String email,
                   LocalDate birthday, String sex, LocalDate creationDate, BigDecimal balance){
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.birthday = birthday;
        this.sex = sex;
        this.creationDate = creationDate;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }
    public String getEmail(){
        return email;
    }
    public BigDecimal getBalance(){
        return balance;
    }
    public LocalDate getCreationDate(){
        return creationDate;
    }
    public LocalDate getBirthday(){
        return birthday;
    }
    public String getSex(){
        return sex;
    }
    public String getFirstname(){
        return firstname;
    }
    public String getLastname(){
        return lastname;
    }
}

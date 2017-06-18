package HW11.common;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Pyltsin on 04.06.2017. Algo8
 */
@Entity
@Table(name = "USERS2")
public class UserDataSet extends DataSet{

    @Column(name = "name")
    private String nameUser;

    @Override
    public String toString() {
        return "UserDataSet{" +
                "nameUser='" + nameUser + '\'' +
                ", ageUser=" + ageUser +
                ", addressDataSet=" + addressDataSet +
                ", phones=" + phones +
                '}';
    }

    @Column(name = "age")
    private int ageUser;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER
            , targetEntity = AddressDataSet.class)
    @JoinColumn(name = "ID_ADDRESS")
    private AddressDataSet addressDataSet;

    @OneToMany(cascade = {CascadeType.ALL, CascadeType.PERSIST})
    @JoinTable(
            name = "users_phone",
            joinColumns = @JoinColumn(name = "ID_USERS"),
            inverseJoinColumns = @JoinColumn(name = "ID_PHONES")
    )
    private Set<PhoneDataSet> phones = new HashSet<>();


    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public int getAgeUser() {
        return ageUser;
    }

    public void setAgeUser(int ageUser) {
        this.ageUser = ageUser;
    }

    public Set<PhoneDataSet> getPhones() {
        return phones;
    }

    public void setPhones(Set<PhoneDataSet> phones) {
        this.phones = phones;
    }

    public UserDataSet(String nameUser, int ageUser, AddressDataSet addressDataSet, Set<PhoneDataSet> phones) {
        this.nameUser = nameUser;
        this.ageUser = ageUser;
        this.addressDataSet = addressDataSet;
        this.phones = phones;
    }

    public AddressDataSet getAddressDataSet() {
        return addressDataSet;
    }

    public void setAddressDataSet(AddressDataSet addressDataSet) {
        this.addressDataSet = addressDataSet;
    }


    public UserDataSet() {
    }


    public String getName() {
        return nameUser;
    }

    public int getAge() {
        return ageUser;
    }

    public void setName(String name) {
        this.nameUser = name;
    }

    public void setAge(int age) {
        this.ageUser = age;
    }
}

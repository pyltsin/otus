package HW11.common;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Pyltsin on 11.06.2017.
 */
@Entity
@Table(name = "ADDRESSES")
public class AddressDataSet extends DataSet {

    @Column(name = "STREET")
    private String street;
    @Column(name = "IND")
    private int ind;

    public AddressDataSet(String street, int ind) {
        this.street = street;
        this.ind = ind;
    }

    public AddressDataSet() {
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getInd() {
        return ind;
    }

    public void setInd(int ind) {
        this.ind = ind;
    }

    @Override
    public String toString() {
        return "AddressDataSet{" +
                "street='" + street + '\'' +
                ", ind=" + ind +
                '}';
    }
}

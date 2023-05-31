package Banks.Entities;

import java.util.UUID;

public class Client {
    private final UUID id;
    private final String firstName;
    private final String lastName;
    private Integer passportNumber;
    private String address;


    /**
     * Create a new client.
     */
    public Client(String firstName, String lastName, String address, Integer passportNumber) {
        this.id = UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.passportNumber = passportNumber;
    }

    public UUID getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getPassportNumber() {
        return passportNumber;
    }

    public String getAddress() {
        return address;
    }

    public void changePassportInformation(int passport) {
        passportNumber = passport;
    }

    public void changeAddressInformation(String address) {
        this.address = address;
    }

    /**
     * Return a string representation of the client.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Client: \n");
        sb.append("  FirstName: ").append(firstName).append("\n");
        sb.append("  LastName: ").append(lastName).append("\n");
        sb.append("  Passport: ").append(passportNumber).append("\n");
        sb.append("  Address: ").append(address).append("\n");
        sb.append("  Id: ").append(id).append("\n");
        return sb.toString();
    }

    /*
     * Builder for Client
     */
    public static class Builder {
        private final String firstName;
        private final String lastName;
        private Integer passportNumber;
        private String address;

        /*
         * Builder constructor
         */
        public Builder(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        /*
         *Build address
         */
        public Builder addAddress(String address) {
            this.address = address;
            return this;
        }

        /*
         *Build Passport Number
         */
        public Builder addPassportNumber(Integer passportNumber) {
            this.passportNumber = passportNumber;
            return this;
        }

        public Banks.Entities.Client build() {
            return new Banks.Entities.Client(firstName, lastName, address, passportNumber);
        }
    }
}

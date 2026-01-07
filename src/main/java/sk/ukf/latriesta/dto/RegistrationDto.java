package sk.ukf.latriesta.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegistrationDto {

    @NotBlank(message = "Meno používateľa je povinné")
    @Size(min = 3, max = 100, message = "Meno musí mať 3-100 znakov")
    private String username;

    @NotBlank(message = "Email je povinný")
    @Email(message = "Neplatný formát emailu")
    private String email;

    @NotBlank(message = "Heslo je povinné")
    @Size(min = 8, message = "Heslo musí mať aspoň 8 znakov")
    private String password;

    @NotBlank(message = "Potvrdenie hesla je povinné")
    private String confirmPassword;

    @NotBlank(message = "Adresa je povinná")
    private String address;

    @NotBlank(message = "Telefón je povinný")
    private String phone;


    public RegistrationDto() {}

    public RegistrationDto(String username, String email, String password, String confirmPassword,
                           String address, String phone) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.address = address;
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
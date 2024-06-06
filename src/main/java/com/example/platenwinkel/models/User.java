package com.example.platenwinkel.models;

import jakarta.persistence.*;

//https://noviuniversity.sharepoint.com/sites/Backend202403/_layouts/15/stream.aspx?id=%2Fsites%2FBackend202403%2FGedeelde%20documenten%2FLessen%20Backend%202024%2002%2FRecordings%2FHuiswerkbegeleiding%20Backend%20%28online%29%2D20240531%5F100654%2DMeeting%20Recording%2Emp4&referrer=StreamWebApp%2EWeb&referrerScenario=AddressBarCopied%2Eview%2E2b612acc%2D6104%2D43a6%2Db827%2Da82c30021255

//19:05
@Entity// dan zeggen we dat deze naar de database moet
@Table(name = "users")
public class User {

   @Id
   @GeneratedValue
  Long id;

    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false, length = 255)
    private String password;
    @Column
    private String email;




    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

////
////    ("shoppingCart")
////    public List<ProductCount> getShoppingCartList() {
////        return shoppingCart.entrySet().stream()
////                .map(entry -> new ProductCount(entry.getKey(), entry.getValue()))
////                .collect(Collectors.toList());
////    }
}

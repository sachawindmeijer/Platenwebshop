package com.example.platenwinkel.repositories;

import com.example.platenwinkel.models.User;
import org.springframework.data.jpa.repository.JpaRepository;


// We gebruiken hier een JpaRepository. Vergeet niet tussen de diamandjes "<>" als eerst het type van je entiteit
// mee te geven en vervolgens het type van het @Id-geannoteerde veld in die entiteit.
public interface UserRepository extends JpaRepository<User, Long> {

//    List<User> getUserIdByUsername(String username);
}

package com.example.platenwinkel.controllers;

//
//import com.example.platenwinkel.dtos.input.CustomerInputDTO;
//import com.example.platenwinkel.dtos.output.CustomerOutputDto;
//import com.example.platenwinkel.models.Customer;
//
//import com.example.platenwinkel.models.User;
//import com.example.platenwinkel.service.CustomerService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/customers")
//public class CustomerController {
//
//    @Autowired
//    private CustomerService customerService;
//
//    // Endpoint om een nieuwe klant aan te maken
//    @PostMapping
//    public ResponseEntity<CustomerOutputDto> createCustomer(
//            @RequestBody CustomerInputDTO customerInputDTO) {
//        User user = new User();
//        user.setUsername(customerInputDTO.getUsername());
//
//        CustomerOutputDto createdCustomer = customerService.createCustomer(customerInputDTO, user);
//        return ResponseEntity.ok(createdCustomer);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
//        Optional<Customer> customer = customerService.getCustomerById(id);
//        return customer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//    @GetMapping
//    public ResponseEntity<List<Customer>> getAllCustomers() {
//        List<Customer> customers = customerService.getAllCustomers();
//        return ResponseEntity.ok(customers);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer updatedCustomer) {
//        Customer customer = customerService.updateCustomer(id, updatedCustomer);
//        if (customer != null) {
//            return ResponseEntity.ok(customer);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
//
//    // Endpoint om een klant te verwijderen
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
//        customerService.deleteCustomer(id);
//        return ResponseEntity.noContent().build();
//    }
//
//    // Hulpmethode om een User op te halen (te vervangen door jouw implementatie)
//    private User findUserByUsername(String username) {
//        // Implementatie om een User te vinden
//        return null; // Deze methode moet verder worden ingevuld
//    }
//}
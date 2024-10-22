package com.example.platenwinkel.service;
//
//import com.example.platenwinkel.dtos.input.CustomerInputDTO;
//import com.example.platenwinkel.dtos.mapper.CustomerMapper;
//import com.example.platenwinkel.dtos.output.CustomerOutputDto;
//import com.example.platenwinkel.models.Customer;
//import com.example.platenwinkel.models.User;
//import com.example.platenwinkel.repositories.CustomerRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class CustomerService{
//
//
//    @Autowired
//    private CustomerRepository customerRepository; // JPA repository voor Customer
//
//    // Methode om een nieuwe klant aan te maken
//    public CustomerOutputDto createCustomer(CustomerInputDTO customerInputDTO, User user) {
//        Customer customer = CustomerMapper.toEntity(customerInputDTO, user);
//        Customer savedCustomer = customerRepository.save(customer);
//        return CustomerMapper.toOutputDto(savedCustomer);
//    }
//
//    // Methode om een klant op te halen op basis van ID
//    public Optional<Customer> getCustomerById(Long id) {
//        return customerRepository.findById(id);
//    }
//
//    // Methode om alle klanten op te halen
//    public List<Customer> getAllCustomers() {
//        return customerRepository.findAll();
//    }
//
//    // Methode om een klant bij te werken
//    public Customer updateCustomer(Long id, Customer updatedCustomer) {
//        if (customerRepository.existsById(id)) {
//            updatedCustomer.setId(id); // Zorg ervoor dat het ID correct is ingesteld
//            return customerRepository.save(updatedCustomer);
//        }
//        return null; // Of gooi een exceptie
//    }
//
//    // Methode om een klant te verwijderen
//    public void deleteCustomer(Long id) {
//        customerRepository.deleteById(id);
//    }
//}
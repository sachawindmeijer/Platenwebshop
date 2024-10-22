package com.example.platenwinkel.dtos.mapper;
//
//import com.example.platenwinkel.dtos.input.CustomerInputDTO;
//import com.example.platenwinkel.dtos.output.CustomerOutputDto;
//import com.example.platenwinkel.models.Customer;
//import com.example.platenwinkel.models.User;
//
//public class CustomerMapper {
//    public static Customer toEntity(CustomerInputDTO dto, User user) {
//
//        Customer customer = new Customer();
//        customer.setUser(user); // Set the user reference
//        customer.setFirstName(dto.firstName);
//        customer.setLastName(dto.lastName);
//        customer.setAddress(dto.address);
//        customer.setCity(dto.city);
//        customer.setCountry(dto.country);
//
//        return customer;
//    }
//
//
//    public static CustomerOutputDto toOutputDto(Customer customer) {
//
//        CustomerOutputDto dto = new CustomerOutputDto();
//        dto.setUsername(customer.getUser().getUsername()); // Assuming User has a getUsername() method
//        dto.setFirstName(customer.getFirstName());
//        dto.setLastName(customer.getLastName());
//        dto.setAddress(customer.getAddress());
//        dto.setCity(customer.getCity());
//        dto.setCountry(customer.getCountry());
//
//        return dto;
//    }
//
//}

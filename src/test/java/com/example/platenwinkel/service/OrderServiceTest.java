package com.example.platenwinkel.service;

import com.example.platenwinkel.dtos.input.OrderInputDto;
import com.example.platenwinkel.dtos.output.LpProductOutputDto;
import com.example.platenwinkel.dtos.output.OrderOutputDto;
import com.example.platenwinkel.enumeration.DeliveryStatus;
import com.example.platenwinkel.enumeration.Genre;
import com.example.platenwinkel.exceptions.RecordNotFoundException;
import com.example.platenwinkel.models.LpProduct;
import com.example.platenwinkel.models.Order;
import com.example.platenwinkel.models.User;
import com.example.platenwinkel.repositories.LpProductRepository;
import com.example.platenwinkel.repositories.OrderRepository;
import com.example.platenwinkel.repositories.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private LpProductRepository lpProductRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    @DisplayName("should return all Orders")
    void testGetAllOrders() {
        // Arrange
        User user1 = new User();
        user1.setUsername("klaas");

        User user2 = new User();
        user2.setUsername("Truus");


        LpProduct lpProduct = new LpProduct();
        lpProduct.setId(3L);
        lpProduct.setArtist("St. Vincent");
        lpProduct.setAlbum("Masseduction");
        lpProduct.setPriceInclVat(30.00);

        Map<LpProduct, Integer> items = Map.of(lpProduct, 2);

        Order order1 = new Order();
        order1.setId(1L);
        order1.setUser(user1);
        order1.setOrderDate(LocalDate.of(2024, 11, 1));
        order1.setPaymentStatus(1);
        order1.setDeliveryStatus(DeliveryStatus.PENDING);
        order1.setShippingAdress("123 Teststraat");
        order1.setItems(items);
        order1.setShippingCost(6.85);

        Order order2 = new Order();
        order2.setId(2L);
        order2.setUser(user2);
        order2.setOrderDate(LocalDate.of(2024, 12, 1));
        order2.setPaymentStatus(0);
        order2.setDeliveryStatus(DeliveryStatus.SHIPPED);
        order2.setShippingAdress("46 Stratenstraat nederland");
        order2.setItems(items);
        order2.setShippingCost(0.0);

        Mockito.when(orderRepository.findAll()).thenReturn(List.of(order1, order2));

        // Act
        List<OrderOutputDto> result = orderService.getAllOrders();

        // Assert
        OrderOutputDto firstOrder = result.get(0);
        assertEquals(1L, firstOrder.getId());
        assertEquals("klaas", firstOrder.getUsername());
        assertEquals(LocalDate.of(2024, 11, 1), firstOrder.getOrderDate());
        assertEquals("123 Teststraat", firstOrder.getShippingAdress());
        assertEquals(6.85, firstOrder.getShippingCost());
        assertEquals(1, firstOrder.getPaymentStatus());
        assertEquals(DeliveryStatus.PENDING, firstOrder.getDeliveryStatus());


        OrderOutputDto secondOrder = result.get(1);
        assertEquals(2L, secondOrder.getId());
        assertEquals("Truus", secondOrder.getUsername());
        assertEquals(LocalDate.of(2024, 12, 1), secondOrder.getOrderDate());
        assertEquals("46 Stratenstraat nederland", secondOrder.getShippingAdress());
        assertEquals(0.0, secondOrder.getShippingCost());
        assertEquals(0, secondOrder.getPaymentStatus());
        assertEquals(DeliveryStatus.SHIPPED, secondOrder.getDeliveryStatus());
    }


    @Test
    @DisplayName("should return Order by id")
    void testGetOrderById_existingOrder() {
        // Arrange
        // Arrange
        Long id = 1L;
        LocalDate orderDate = LocalDate.now();
        String shippingAddress = "123 Teststraat";
        User user = new User();
        user.setUsername("Trus");

        Order order = new Order();
        order.setId(id);
        order.setUser(user);
        order.setOrderDate(orderDate);
        order.setShippingAdress(shippingAddress);
        order.setShippingCost(5.0);
        order.setPaymentStatus(1);
        order.setDeliveryStatus(DeliveryStatus.PENDING);

        Mockito.when(orderRepository.findById(id)).thenReturn(Optional.of(order));
        // Act
        OrderOutputDto result = orderService.getOrderById(id);
        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Trus", result.getUsername());
        assertEquals(shippingAddress, result.getShippingAdress());
        assertEquals(orderDate, result.getOrderDate());
        assertEquals(5.0, result.getShippingCost());
        assertEquals(1, result.getPaymentStatus());
        assertEquals(DeliveryStatus.PENDING, result.getDeliveryStatus());
    }




}

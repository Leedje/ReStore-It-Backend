package com.restoreit.services;

import com.restoreit.dtos.OrderDTO;
import com.restoreit.mappers.OrderMapper;
import com.restoreit.models.Order;
import com.restoreit.models.Product;
import com.restoreit.repository.OrderRepository;
import com.restoreit.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;
    private final ChatRoomService chatRoomService;

    @Autowired
    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper, ChatRoomService chatRoomService, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.orderMapper = orderMapper;
        this.chatRoomService = chatRoomService;
    }

    public UUID CreateOrder(OrderDTO orderDto) {
        Order order = orderMapper.DTOtoOrder(orderDto);

        List<Product> attachedProducts = orderDto.products.stream()
                .map(product -> productRepository.findById(product.id)
                        .orElseThrow(() -> new RuntimeException("Product not found: " + product.id)))
                .collect(Collectors.toList());

        order.setProducts(attachedProducts);
        orderRepository.save(order);

        if(chatRoomService.CreateChatRooms(order)){
            return order.getId();
        }
        return null;
    }

    public OrderDTO GetOrderById(UUID orderId){
        return orderMapper.OrderToDTO(orderRepository.findById(orderId).get());
    }

    public boolean SetOrderAsCompleted(OrderDTO order, UUID userId) {
        Order existingOrder = orderRepository.findById(order.id).orElse(null);
        if (existingOrder != null) {
            existingOrder.setIsComplete(true);
            orderRepository.save(existingOrder);
            return true;
        }
        return false;
    }

    public List<OrderDTO> GetPendingOrdersByUserId(UUID userId){
        return orderRepository.findPendingOrdersByUserId(userId)
                .stream()
                .map(order -> {
                    order.setProducts(order.getProducts().stream()
                            .filter(product -> product.getUser().getId().equals(userId))
                            .collect(Collectors.toList()));
                    return orderMapper.OrderToDTO(order);
                })
                .collect(Collectors.toList());
    }

    public List<OrderDTO> GetCompletedOrdersByUserId(UUID userId) {
        return orderRepository.findCompletedOrdersByUserId(userId)
                .stream()
                .map(order -> {
                    order.setProducts(order.getProducts().stream()
                            .filter(product -> product.getUser().getId().equals(userId))
                            .collect(Collectors.toList()));
                    return orderMapper.OrderToDTO(order);
                })
                .collect(Collectors.toList());
    }
}

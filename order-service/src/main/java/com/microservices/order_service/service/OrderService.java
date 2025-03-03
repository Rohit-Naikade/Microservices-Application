package com.microservices.order_service.service;

import com.microservices.order_service.client.InventoryClient;
import com.microservices.order_service.dto.OrderRequest;
import com.microservices.order_service.model.Order;
import com.microservices.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service

public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;


    private OrderService(OrderRepository orderRepository, InventoryClient inventoryClient){
        this.orderRepository=orderRepository;
        this.inventoryClient=inventoryClient;
    }


    public void placeOrder(OrderRequest orderRequest){

        boolean isProductInStock=inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());

        if(isProductInStock){
            System.out.println(orderRequest);
            Order order=new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setPrice(orderRequest.price());
            order.setSkuCode(orderRequest.skuCode());
            order.setQuantity(orderRequest.quantity());
            orderRepository.save(order);
        }else{
            throw new RuntimeException("Product with SkuCode "+orderRequest.skuCode()+" is not in stock!");
        }

    }
}

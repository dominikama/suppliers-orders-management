package com.stockmanagement.application.services.implementation;

import com.stockmanagement.adapters.messaging.EventPublisher;
import com.stockmanagement.adapters.persistence.OrderRepository;
import com.stockmanagement.adapters.persistence.ProductRepository;
import com.stockmanagement.adapters.persistence.SupplierRepository;
import com.stockmanagement.adapters.persistence.WarehouseRepository;
import com.stockmanagement.application.mappers.OrderMapper;
import com.stockmanagement.application.services.OrderService;
import com.stockmanagement.domain.models.*;
import com.stockmanagement.dtos.OrderDto;
import com.stockmanagement.dtos.OrderProductsDto;
import com.stockmanagement.events.OrderEvent;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class  DefaultOrderService implements OrderService {

    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;
    private final SupplierRepository supplierRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final EventPublisher eventPublisher;


    public DefaultOrderService(ProductRepository productRepository, WarehouseRepository warehouseRepository,
                               SupplierRepository supplierRepository, OrderMapper orderMapper,
                               OrderRepository orderRepository, EventPublisher eventPublisher) {
        this.productRepository = productRepository;
        this.warehouseRepository = warehouseRepository;
        this.orderMapper = orderMapper;
        this.supplierRepository = supplierRepository;
        this.orderRepository = orderRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public OrderDto create(OrderDto orderDTO) {
        Supplier supplier = findSupplierById(orderDTO.getSupplierId());
        Warehouse warehouse = findWarehouseByCode(orderDTO.getWarehouseCode());
        Order order = buildOrder(orderDTO);
        order.setSupplier(supplier);
        order.setWarehouse(warehouse);
        List<OrderProduct> products = fetchProducts(orderDTO.getOrderProductsDto(), order);
        order.setOrderProducts(products);
        OrderDto savedOrderDto = orderMapper.toDto(orderRepository.save(order));
        eventPublisher.publishEvent(createEvent(orderDTO, OrderEvent.EventType.ORDER_CREATED));
        return savedOrderDto;
    }

    @Override
    public List<OrderDto> get() {
        List<OrderDto> orderDtos = new ArrayList<>();
        for (Order order : orderRepository.findAll()) {
            orderDtos.add(orderMapper.toDto(order));
        }
        return orderDtos;
    }

    @Override
    public OrderDto getById(Integer id) {
        return orderRepository.findById(id)
                .map(orderMapper::toDto)
                .orElseThrow(() -> new NoSuchElementException(String.format("Did not find order with id=%s", id)));
    }

    @Override
    public OrderDto update(Integer id, OrderDto updated) {
        Order order = findOrderById(id);
        Supplier supplier = findSupplierById(updated.getSupplierId());
        Warehouse warehouse = findWarehouseByCode(updated.getWarehouseCode());
        order.setSupplier(supplier);
        order.setWarehouse(warehouse);
        order.getOrderProducts().clear();
        List<OrderProduct> products = fetchProducts(updated.getOrderProductsDto(), order);
        order.getOrderProducts().addAll(products);
        order.setStatus(updated.getOrderStatus());
        OrderDto orderDto = orderMapper.toDto(orderRepository.save(order));
        eventPublisher.publishEvent(createEvent(orderDto, OrderEvent.EventType.ORDER_UPDATED));
        return orderDto;
    }

    @Override
    public void delete(Integer id) {
        Order order = findOrderById(id);
        orderRepository.delete(order);
        OrderDto orderDto = orderMapper.toDto(order);
        eventPublisher.publishEvent(createEvent(orderDto, OrderEvent.EventType.ORDER_DELETED));
    }

    @Override
    public void updateStatus(String status, String orderNumber) {
        Order order = orderRepository.findByNumber(orderNumber)
                .orElseThrow(() -> new NoSuchElementException(String.format("Did not find order with orderNumber=%s", orderNumber)));
        order.setStatus(status);
        OrderDto orderDto = orderMapper.toDto(orderRepository.save(order));
        eventPublisher.publishEvent(createEvent(orderDto, OrderEvent.EventType.ORDER_UPDATED));
    }

    private List<OrderProduct> fetchProducts(List<OrderProductsDto> productsDtos, Order order) {
        List<OrderProduct> products = new ArrayList<>();
        for (OrderProductsDto productsDto : productsDtos) {
            Product product = productRepository.findByProductCode(productsDto.getProductCode())
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Product with code=%s, not found",
                            productsDto.getProductCode())));
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setOrder(order);
            orderProduct.setProduct(product);
            orderProduct.setQuantity(productsDto.getQuantity());
            products.add(orderProduct);
        }
        return products;
    }

    private Supplier findSupplierById(Integer supplierId) {
        return supplierRepository.findById(supplierId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Supplier with id=%s, not found", supplierId)));
    }

    private Warehouse findWarehouseByCode(String warehouseCode) {
        return warehouseRepository.findByCode(warehouseCode)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Warehouse with code=%s, not found", warehouseCode)));
    }

    private Order findOrderById(Integer id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("Did not find order with id=%s", id)));
    }

    private Order buildOrder(OrderDto orderDTO) {
        Order order = new Order();
        order.setStatus(OrderEvent.EventType.ORDER_CREATED.toString());
        order.setOrderDateTime(LocalDateTime.now());
        order.setNumber(orderDTO.getOrderNumber());
        return order;
    }
    private OrderEvent createEvent(OrderDto orderDto, OrderEvent.EventType eventType) {
        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setEventType(eventType);
        orderEvent.setOrderDTO(orderDto);
        orderEvent.setOrderNumber(orderDto.getOrderNumber());
        orderEvent.setTimestamp(new Date());
        return orderEvent;
    }
}

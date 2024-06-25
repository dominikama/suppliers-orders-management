package com.stockmanagement.application.mappers;

import com.stockmanagement.domain.models.Order;
import com.stockmanagement.dtos.OrderDto;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    private final OrderProductMapper orderProductMapper;

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public OrderMapper(OrderProductMapper orderProductMapper) {
        this.orderProductMapper = orderProductMapper;
    }

    public OrderDto toDto(Order order) {
        OrderDto dto = new OrderDto();
        dto.setId(order.getId());
        Date date = Date.from(order.getOrderDateTime().toInstant(ZoneOffset.UTC));
        dto.setOrderDate(sdf.format(date));
        dto.setOrderNumber(order.getNumber());
        dto.setSupplierId(order.getSupplier().getId());
        dto.setWarehouseCode(order.getWarehouse().getCode());
        dto.setOrderProductsDto(order.getOrderProducts().stream()
                .map(orderProductMapper::toDto)
                .collect(Collectors.toList()));
        return dto;
    }

}

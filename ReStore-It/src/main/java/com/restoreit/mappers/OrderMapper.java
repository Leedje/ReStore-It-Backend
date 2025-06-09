package com.restoreit.mappers;

import com.restoreit.dtos.OrderDTO;
import com.restoreit.models.Order;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {OrderMapper.class}, componentModel = "spring")
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);
    Order DTOtoOrder(OrderDTO orderDTO);
    OrderDTO OrderToDTO(Order order);
}

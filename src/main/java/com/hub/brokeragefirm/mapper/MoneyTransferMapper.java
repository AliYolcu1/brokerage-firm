package com.hub.brokeragefirm.mapper;

import com.hub.brokeragefirm.dto.response.MoneyTransferResponse;
import com.hub.brokeragefirm.entity.MoneyTransfer;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MoneyTransferMapper {

    @Mapping(target = "transactionId", source = "id")
    MoneyTransferResponse toDto(MoneyTransfer moneyTransfer);

    @Mapping(target = "id", source = "transactionId")
    MoneyTransfer toEntity(MoneyTransferResponse response);

}

package com.hub.brokeragefirm.mapper;

import com.hub.brokeragefirm.dto.response.AssetResponse;
import com.hub.brokeragefirm.entity.Asset;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AssetMapper {
    AssetResponse toDto(Asset asset);
    List<AssetResponse> toDtoList(List<Asset> assets);
}

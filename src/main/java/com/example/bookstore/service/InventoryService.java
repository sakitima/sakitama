package com.example.bookstore.service;

import com.example.bookstore.common.domain.Inventory;
import com.example.bookstore.common.request.InventoryReqDto;
import com.example.bookstore.common.response.ResponseResult;

/**
 * @author chaoluo
 */
public interface InventoryService {
    Inventory queryInventory(InventoryReqDto inventoryReqDto);

    Boolean updateInventory(InventoryReqDto inventoryReqDto);

    ResponseResult checkInventory(InventoryReqDto inventoryReqDto);
}

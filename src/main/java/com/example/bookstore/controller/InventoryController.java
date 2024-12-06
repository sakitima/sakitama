package com.example.bookstore.controller;

import com.example.bookstore.common.domain.Inventory;
import com.example.bookstore.common.request.InventoryReqDto;
import com.example.bookstore.common.response.ResponseResult;
import com.example.bookstore.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chaoluo
 */
@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PostMapping("/queryInventory")
    public ResponseResult queryInventory(@RequestBody InventoryReqDto inventoryReqDto){
        final Inventory inventory = inventoryService.queryInventory(inventoryReqDto);
        return ResponseResult.ok(inventory);
    }

    @PostMapping("/updateInventory")
    public ResponseResult updateInventory(@RequestBody InventoryReqDto inventoryReqDto){
        Boolean aBoolean = inventoryService.updateInventory(inventoryReqDto);
        return ResponseResult.ok(aBoolean);
    }

    @PostMapping("/checkInventory")
    public ResponseResult checkInventory(@RequestBody InventoryReqDto inventoryReqDto){
        return inventoryService.checkInventory(inventoryReqDto);
    }


}

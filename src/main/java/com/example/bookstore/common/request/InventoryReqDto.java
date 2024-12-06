package com.example.bookstore.common.request;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author chaoluo
 */
@Data
public class InventoryReqDto {

    private Integer id;

    @NotNull(message = "图书编号不能为空")
    @Valid
    private Integer bookNo;

    private Integer purchase;
}

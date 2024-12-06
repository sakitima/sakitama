package com.example.bookstore.common.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author chaoluo
 */
@Data
public class OrderReqDto {

    private Integer id;

    private Integer orderNo;

    @NotNull(message = "用户Id不能为空")
    private Integer UserId;

    @NotNull(message = "图书编号不能为空")
    private Integer bookNo;

    @NotNull(message = "购买数量不能为空")
    private Integer purchase;

    @NotNull(message = "图书单价不能为空")
    private BigDecimal unitPrice;

    @NotNull(message = "图书总价不能为空")
    private BigDecimal totalPrice;

    private Integer pageNum;

    private Integer PageSize;
}

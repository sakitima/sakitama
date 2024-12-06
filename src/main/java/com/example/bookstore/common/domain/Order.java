package com.example.bookstore.common.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.math.BigDecimal;
import javax.persistence.*;

/**
 * @author chaoluo
 */
public class Order {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    /**
     * 订单流水号
     */
    @Column(name = "order_no")
    private String orderNo;

    /**
     * 用户userId
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 图书编号
     */
    @Column(name = "book_no")
    private Integer bookNo;

    /**
     * 购买数量
     */
    private Integer purchase;

    /**
     * 购买单价
     */
    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    /**
     * 购买总价
     */
    @Column(name = "total_price")
    private BigDecimal totalPrice;

    /**
     * 0- 初始化 1-成功 2-取消 3-过期
     */
    private Integer status;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取订单流水号
     *
     * @return order_no - 订单流水号
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * 设置订单流水号
     *
     * @param orderNo 订单流水号
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 获取用户userId
     *
     * @return user_id - 用户userId
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 设置用户userId
     *
     * @param userId 用户userId
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 获取图书编号
     *
     * @return book_no - 图书编号
     */
    public Integer getBookNo() {
        return bookNo;
    }

    /**
     * 设置图书编号
     *
     * @param bookNo 图书编号
     */
    public void setBookNo(Integer bookNo) {
        this.bookNo = bookNo;
    }

    /**
     * 获取购买数量
     *
     * @return purchase - 购买数量
     */
    public Integer getPurchase() {
        return purchase;
    }

    /**
     * 设置购买数量
     *
     * @param purchase 购买数量
     */
    public void setPurchase(Integer purchase) {
        this.purchase = purchase;
    }

    /**
     * 获取购买单价
     *
     * @return unit_price - 购买单价
     */
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    /**
     * 设置购买单价
     *
     * @param unitPrice 购买单价
     */
    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * 获取购买总价
     *
     * @return total_price - 购买总价
     */
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    /**
     * 设置购买总价
     *
     * @param totalPrice 购买总价
     */
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * 获取0- 初始化 1-成功 2-取消 3-过期
     *
     * @return status - 0- 初始化 1-成功 2-取消 3-过期
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置0- 初始化 1-成功 2-取消 3-过期
     *
     * @param status 0- 初始化 1-成功 2-取消 3-过期
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}
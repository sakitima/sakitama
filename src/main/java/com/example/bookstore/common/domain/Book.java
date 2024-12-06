package com.example.bookstore.common.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import javax.persistence.*;

/**
 * @author chaoluo
 */
public class Book {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    /**
     * 书本编号
     */
    @Column(name = "book_no")
    private Integer bookNo;

    /**
     * 书本名称
     */
    private String name;

    /**
     * 书本数量
     */
    private Integer total;

    /**
     * 0-下架 1-上架
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
     * 获取书本编号
     *
     * @return book_no - 书本编号
     */
    public Integer getBookNo() {
        return bookNo;
    }

    /**
     * 设置书本编号
     *
     * @param bookNo 书本编号
     */
    public void setBookNo(Integer bookNo) {
        this.bookNo = bookNo;
    }

    /**
     * 获取书本名称
     *
     * @return name - 书本名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置书本名称
     *
     * @param name 书本名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取书本数量
     *
     * @return total - 书本数量
     */
    public Integer getTotal() {
        return total;
    }

    /**
     * 设置书本数量
     *
     * @param total 书本数量
     */
    public void setTotal(Integer total) {
        this.total = total;
    }

    /**
     * 获取0-下架 1-上架
     *
     * @return status - 0-下架 1-上架
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置0-下架 1-上架
     *
     * @param status 0-下架 1-上架
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}
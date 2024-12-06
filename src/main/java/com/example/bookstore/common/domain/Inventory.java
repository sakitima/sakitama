package com.example.bookstore.common.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import javax.persistence.*;

/**
 * @author chaoluo
 */
public class Inventory {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    /**
     * 图书编号
     */
    @Column(name = "book_no")
    private Integer bookNo;

    /**
     * 库存总数
     */
    private Integer total;

    /**
     * 出售数量
     */
    @Column(name = "sale_num")
    private Integer saleNum;

    /**
     * 剩余数量
     */
    private Integer residue;

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
     * 获取库存总数
     *
     * @return total - 库存总数
     */
    public Integer getTotal() {
        return total;
    }

    /**
     * 设置库存总数
     *
     * @param total 库存总数
     */
    public void setTotal(Integer total) {
        this.total = total;
    }

    /**
     * 获取出售数量
     *
     * @return sale_num - 出售数量
     */
    public Integer getSaleNum() {
        return saleNum;
    }

    /**
     * 设置出售数量
     *
     * @param saleNum 出售数量
     */
    public void setSaleNum(Integer saleNum) {
        this.saleNum = saleNum;
    }

    /**
     * 获取剩余数量
     *
     * @return residue - 剩余数量
     */
    public Integer getResidue() {
        return residue;
    }

    /**
     * 设置剩余数量
     *
     * @param residue 剩余数量
     */
    public void setResidue(Integer residue) {
        this.residue = residue;
    }
}
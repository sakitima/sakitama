package com.example.bookstore.common.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author chaoluo
 */
@Data
public class BookReqDto {

    private Integer id;

    @NotNull(message = "书名不能为空")
    private String bookName;

    @NotNull(message = "书本数量不能为空")
    private Integer bookTotal;

    private Integer pageNum;

    private Integer PageSize;

}

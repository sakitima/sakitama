package com.example.bookstore.service;

import com.example.bookstore.common.request.BookReqDto;
import com.example.bookstore.common.request.RegisterReqDto;
import com.example.bookstore.common.response.ResponseResult;

/**
 * @author chaoluo
 */
public interface BookService {
    ResponseResult addBook(BookReqDto bookReqDto);

    ResponseResult updateBook(BookReqDto bookReqDto);

    ResponseResult queryBook(BookReqDto bookReqDto);

    ResponseResult listingBook(BookReqDto bookReqDto);

    ResponseResult deleteBook(BookReqDto bookReqDto);
}

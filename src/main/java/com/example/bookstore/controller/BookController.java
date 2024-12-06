package com.example.bookstore.controller;

import com.example.bookstore.common.request.BookReqDto;
import com.example.bookstore.common.request.RegisterReqDto;
import com.example.bookstore.common.response.ResponseResult;
import com.example.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author chaoluo
 */
@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/addBook")
    public ResponseResult addBook(@RequestBody @Valid BookReqDto bookReqDto){
        return bookService.addBook(bookReqDto);
    }

    @PostMapping("/updateBook")
    public ResponseResult updateBook(@RequestBody BookReqDto bookReqDto){
        return bookService.updateBook(bookReqDto);
    }

    @PostMapping("/queryBook")
    public ResponseResult queryBook(@RequestBody BookReqDto bookReqDto){
        return bookService.queryBook(bookReqDto);
    }

    @PostMapping("/listingBook")
    public ResponseResult listingBook(@RequestBody BookReqDto bookReqDto){
        return bookService.listingBook(bookReqDto);
    }

    @PostMapping("/deleteBook")
    public ResponseResult deleteBook(@RequestBody BookReqDto bookReqDto){
        return bookService.deleteBook(bookReqDto);
    }
}

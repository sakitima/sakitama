package com.example.bookstore.controller;

import com.example.bookstore.common.response.ResponseResult;
import com.example.bookstore.service.BookService;
import jakarta.annotation.Resource;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chaoluo
 */
@RestController
@RequestMapping("/OpenAi")
public class OpenAiController {

    @Resource
    private OpenAiChatModel openAiChatModel;

    @Autowired
    private BookService bookService;

    @GetMapping("/ai/chat")
    public String chat(@RequestParam("msg") String msg) {
        String called = openAiChatModel.call(msg);
        //TODO 可通过openai将获取的书内容调用 ES查询出对应书名，再调用bookService查询对应的图书信息
        return called;
    }
}

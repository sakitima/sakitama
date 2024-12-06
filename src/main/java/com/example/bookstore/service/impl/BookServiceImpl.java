package com.example.bookstore.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.bookstore.common.mapper.BookMapper;
import com.example.bookstore.common.mapper.InventoryMapper;
import com.example.bookstore.common.enums.ErrorCodeEnum;
import com.example.bookstore.common.exception.BusinessException;
import com.example.bookstore.common.domain.Book;
import com.example.bookstore.common.domain.Inventory;
import com.example.bookstore.common.request.BookReqDto;
import com.example.bookstore.common.response.ResponseResult;
import com.example.bookstore.common.util.PageUtil;
import com.example.bookstore.common.util.RedisUtil;
import com.example.bookstore.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author chaoluo
 */

@Slf4j
@Service
public class BookServiceImpl implements BookService {

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private BookMapper bookMapper;

    @Resource
    private InventoryMapper inventoryMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseResult addBook(BookReqDto bookReqDto) {
        log.info("addBook requestParam: {}", JSON.toJSONString(bookReqDto));
        Integer bookNo = Integer.parseInt(String.valueOf(RandomUtil.randomInt(11)));
        Book book = new Book();
        book.setName(bookReqDto.getBookName());
        book.setTotal(bookReqDto.getBookTotal());
        book.setBookNo(bookNo);
        bookMapper.insert(book);
        Inventory inventory = new Inventory();
        inventory.setBookNo(bookNo);
        inventory.setTotal(bookReqDto.getBookTotal());
        inventory.setResidue(bookReqDto.getBookTotal());
        inventory.setSaleNum(0);
        redisUtil.set("book:inventory:" + bookNo,JSON.toJSONString(inventory),7200L);
        inventoryMapper.insert(inventory);
        //TODO
        /**
         * 这个是个小demo，两表是在统一库下，就简单是用 @Transactional 来保证事物一致性，
         * 多数据源可通过配置不通的 DataSourceTransactionManager 来保证事物一致性，粒度相对较细
         */
        return ResponseResult.ok("图书添加成功");

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseResult updateBook(BookReqDto bookReqDto) {
        log.info("updateBook requestParam: {}", JSON.toJSONString(bookReqDto));
            if (bookReqDto.getId() == null){
                throw new BusinessException(-1,"图书id不能为空");
            }
            Book book = bookMapper.selectById(bookReqDto.getId());
            if (book == null){
                return ResponseResult.error(ErrorCodeEnum.NOEXIST,"图书不存在");
            }
            if (StrUtil.isAllNotEmpty(bookReqDto.getBookName())){
                book.setName(bookReqDto.getBookName());
            }
            if (bookReqDto.getBookTotal() != null){
                book.setTotal(bookReqDto.getBookTotal());
                Inventory inventory = inventoryMapper.selectOne(new QueryWrapper<Inventory>()
                        .lambda()
                        .eq(Inventory::getBookNo, book.getBookNo()));
                inventory.setTotal(bookReqDto.getBookTotal());
                inventoryMapper.updateById(inventory);
                redisUtil.set("book:inventory:" + book.getBookNo(),JSON.toJSONString(inventory),7200L);
                //TODO
                /**
                 * 这里实际业务场景中在修改库存时，需要做库存校验的，不能直接更新
                 */
            }
            bookMapper.updateById(book);
            return ResponseResult.ok("图书更新成功");
    }

    @Override
    public ResponseResult queryBook(BookReqDto bookReqDto) {
        log.info("queryBook requestParam: {}", JSON.toJSONString(bookReqDto));
        Page<Book> bookPage = new Page<>();
        if (bookReqDto == null ){
            return ResponseResult.ok(bookPage);
        }
        Page<Book> page = PageUtil.getPage(bookReqDto.getPageNum(), bookReqDto.getPageSize());
        bookPage = bookMapper.selectPage(page, new QueryWrapper<Book>()
                .lambda()
                .like(Book::getName, bookReqDto.getBookName()));
        return ResponseResult.ok(bookPage);
    }

    @Override
    public ResponseResult listingBook(BookReqDto bookReqDto) {
        log.info("listingBook requestParam: {}", JSON.toJSONString(bookReqDto));
        try {
            if (bookReqDto.getId() == null){
                throw new BusinessException(-1,"图书id不能为空");
            }
            Book book = bookMapper.selectById(bookReqDto.getId());
            if (book == null){
                return ResponseResult.error(ErrorCodeEnum.NOEXIST,"图书不存在");
            }
            if (book.getStatus() ==0){
                book.setStatus(1);
                bookMapper.updateById(book);
                return ResponseResult.ok("图书上架成功");
            }else {
                book.setStatus(0);
                bookMapper.updateById(book);
                return ResponseResult.ok("图书下架成功");
            }
        }catch (RuntimeException e){
            log.error("图书上下架失败: {},失败参数: {}",e,JSON.toJSONString(bookReqDto));
            return ResponseResult.systemError();
        }
    }

    @Override
    public ResponseResult deleteBook(BookReqDto bookReqDto) {
        log.info("deleteBook requestParam: {}", JSON.toJSONString(bookReqDto));
        try {
            if (bookReqDto.getId() == null){
                throw new BusinessException(-1,"图书id不能为空");
            }
            Book book = bookMapper.selectById(bookReqDto.getId());
            if (book == null){
                return ResponseResult.error(ErrorCodeEnum.NOEXIST,"图书不存在");
            }
            bookMapper.deleteById(book.getId());
            return ResponseResult.ok("图书删除成功");
        }catch (RuntimeException e){
            log.error("图书删除失败: {},失败参数: {}",e,JSON.toJSONString(bookReqDto));
            return ResponseResult.systemError();
        }
    }
}

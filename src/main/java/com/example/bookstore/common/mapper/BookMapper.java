package com.example.bookstore.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.bookstore.common.domain.Book;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author chaoluo
 */
@Mapper
public interface BookMapper extends BaseMapper<Book> {
}
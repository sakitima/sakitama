package com.example.bookstore.common.util;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public class PageUtil {

    public static Page getPage(Integer pageNum, Integer pageSize) {
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        if (pageSize > 1000) {
            // 最大单页1000
            pageSize = 1000;
        }
        return new Page(pageNum, pageSize);
    }
}

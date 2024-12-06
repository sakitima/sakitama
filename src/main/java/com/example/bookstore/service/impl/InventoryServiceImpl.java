package com.example.bookstore.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.bookstore.common.mapper.InventoryMapper;
import com.example.bookstore.common.enums.ErrorCodeEnum;
import com.example.bookstore.common.exception.BusinessException;
import com.example.bookstore.common.domain.Inventory;
import com.example.bookstore.common.request.InventoryReqDto;
import com.example.bookstore.common.response.ResponseResult;
import com.example.bookstore.common.util.RedisUtil;
import com.example.bookstore.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * @author chaoluo
 */
@Slf4j
@Service
public class InventoryServiceImpl implements InventoryService {

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private Redisson redisson;

    @Resource
    private InventoryMapper inventoryMapper;

    /**
     * 该方法模拟并发情况下DCL方式查询数据库并存入redis
     * @param inventoryReqDto
     * @return
     */
    @Override
    public Inventory queryInventory(InventoryReqDto inventoryReqDto) {
        log.info("queryInventory requestParam: {}", JSON.toJSONString(inventoryReqDto));
        Inventory inventory = this.getInventoryFromCache(inventoryReqDto.getBookNo());
        if (inventory != null){
            return inventory;
        }
        String inventoryLockKey = "book:inventory:lock:" + inventory.getBookNo();
        RLock lock = redisson.getLock(inventoryLockKey);
        try{
            lock.tryLock(3, TimeUnit.SECONDS);
            inventory = this.getInventoryFromCache(inventoryReqDto.getBookNo());
            if (inventory != null){
                return inventory;
            }
            ReadWriteLock readWriteLock = redisson.getReadWriteLock(inventoryLockKey);
            Lock readLock = readWriteLock.readLock();
            try{
                inventory = inventoryMapper.selectOne(new QueryWrapper<Inventory>()
                        .lambda()
                        .eq(Inventory::getBookNo, inventoryReqDto.getBookNo()));
                if (inventory !=null){
                    redisUtil.set("book:inventory:" + inventory.getBookNo(),JSON.toJSONString(inventory),7200L);
                }else {
                    redisUtil.set("book:inventory:" + inventory.getBookNo(),JSON.toJSONString(inventory),10L);
                }
                return inventory;
            }finally {
                readLock.unlock();
            }
        }catch (InterruptedException e) {
            log.error(inventoryLockKey+"锁中断机异常：{}",e);
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        return null;
    }

    /**
     * 采用redisson读锁进行库存扣减
     * @param inventoryReqDto
     * @return
     */
    @Override
    public Boolean updateInventory(InventoryReqDto inventoryReqDto) {
        log.info("updateInventory requestParam: {}", JSON.toJSONString(inventoryReqDto));
        if (inventoryReqDto.getPurchase() == null || inventoryReqDto.getPurchase()<= 0){
            throw new BusinessException(ErrorCodeEnum.NOEXIST.getCode(), "图书购买数量不能为空");
        }
        ReadWriteLock readWriteLock = redisson.getReadWriteLock("book:inventory:lock:" + inventoryReqDto.getBookNo());
        /**
         * 采用读锁是允许多个线程同时持有读锁，进行读取操作。读锁不会阻塞其他线程的读操作，但会阻塞写操作，确保数据的一致性
         */
        Lock readLock = readWriteLock.readLock();
        try {
            Inventory inventory = this.queryInventory(inventoryReqDto);
            if (inventory == null){
                throw new BusinessException(ErrorCodeEnum.NOEXIST.getCode(),"图书库存不存在");
            }
            if (inventory.getResidue().compareTo(inventoryReqDto.getPurchase()) >0){
                throw new BusinessException(ErrorCodeEnum.NOEXIST.getCode(),"图书库存不足");
            }
            inventory.setSaleNum(inventory.getSaleNum() + inventoryReqDto.getPurchase());
            inventory.setResidue(inventory.getResidue() - inventoryReqDto.getPurchase());
            inventoryMapper.updateById(inventory);
            redisUtil.set("book:inventory:" + inventory.getBookNo(),JSON.toJSONString(inventory),7200L);
            return true;
        } finally {
            readLock.unlock();
        }
    }

    private Inventory getInventoryFromCache(Integer bookNo){
        Inventory inventory = new Inventory();
        //DCL第一次检查
        String  inventoryStr = (String)redisUtil.get("book:inventory:" + bookNo);
        if(StringUtils.isEmpty(inventoryStr)){
            return inventory;
        }
        inventory = JSON.parseObject(inventoryStr, Inventory.class);
        return inventory;
    }

    @Override
    public ResponseResult checkInventory(InventoryReqDto inventoryReqDto) {
        log.info("checkInventory requestParam: {}", JSON.toJSONString(inventoryReqDto));
        Inventory inventory = this.queryInventory(inventoryReqDto);
        return ResponseResult.ok(inventory);
    }
}

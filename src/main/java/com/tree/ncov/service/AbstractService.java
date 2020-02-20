package com.tree.ncov.service;

import java.io.IOException;
import java.util.List;

/**
 * @ClassName com.tree.ncov.service
 * Description: <类功能描述>. <br>
 * <p>
 * <使用说明>
 * </p>
 * @Author tree
 * @Date 2020-02-15 12:11
 * @Version 1.0
 */
public abstract class AbstractService {

    /**
     * 两种初始化方式： 第一种为本地初始化
     *
     * 从本地加载文件初始化数据到数据库
     * @throws IOException
     */
    public  void initDataFromLocal() throws IOException{
        //初始化表
        initTable();
        //从本地加载集合对象, 并简单处理
        List list = readFileFromLocal();
        //继续处理该集合对象， 并放入redis
        putDataInRedis(list);
        //放入数据库
        initBatchUpdate(list);
    }

    /**
     * 两种初始化方式： 第二种为远程初始化
     * 从远程加载文件并初始化到数据库
     * @throws Exception
     */
    public  void initDataFromRemote() throws Exception {
        //初始化表
        initTable();
        //从远程加载集合对象，并简单处理
        List list = readFileFromRemote();
        //继续处理该集合对象， 并放入redis
        putDataInRedis(list);
        //放入数据库
        initBatchUpdate(list);
    }

    /**
     * //读取远程数据
     *
     * //读取redis数据
     *
     * //对比
     * @throws Exception
     */
    public void compareAndUpdate() throws Exception{
        //从远程加载集合对象，并简单处理
        List list = readFileFromRemote();

        //load DB data
        Object obj = loadTodayData();

        //对比更新
        executeCompareAndUpdate(list,obj);
    }

    /**
     * 对比和执行
     * @param list
     * @param obj
     * @throws IOException
     */
    protected abstract void executeCompareAndUpdate(List list, Object obj) throws IOException;

    /**
     * 获取今天的数据
     * @return
     */
    protected abstract Object loadTodayData() throws IOException;

    /**
     * 下载文件
     */
    public  void downloadFile2Local() throws IOException{

    }

    /**
     * 读取本地文件
     */
    public abstract List readFileFromLocal() throws IOException;

    /**
     * 从远程读取数据
     */
    public abstract List readFileFromRemote() throws IOException;

    /**
     *
     * @param ncovList
     */
    public abstract void putDataInRedis(List ncovList);

    /**
     * 初始化表， 如truncate table
     */
    public abstract void initTable();

    /**
     * 批量插入数据到数据库
     */
    public abstract void initBatchUpdate(List ncovList);




}

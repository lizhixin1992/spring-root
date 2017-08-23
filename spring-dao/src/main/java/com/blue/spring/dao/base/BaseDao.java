package com.blue.spring.dao.base;

import java.util.List;

/**
 * @description:
 * @email:
 * @author: lizhixin
 * @createDate: 13:54 2017/8/23
 */
public interface BaseDao<T> {
    public Boolean insert(T var);

    public Boolean delete(T var);

    public Boolean update(T var);

    public List<T> select(T var);
}

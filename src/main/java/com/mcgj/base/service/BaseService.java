package com.mcgj.base.service;

/**
 * @author 
 *
 * @param <T> 实体
 * @param <K> id
 */
public interface BaseService<T,K> {
	void delete(K id);

    void insert(T record);
    
    void update(T record);

    T selectById(K id);
}

package com.yz.snews.repository;

import java.io.Serializable;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseDao<M, ID extends Serializable> extends MongoRepository<M, ID> {

//	public List<M> find(Query query, Class<M> clazz);
}

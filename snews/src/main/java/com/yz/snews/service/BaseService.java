package com.yz.snews.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.nutz.castor.Castors;
import org.nutz.lang.Mirror;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springside.modules.utils.Reflections;

import com.yz.snews.repository.BaseDao;
import com.yz.snews.util.BeanUtils;
import com.yz.snews.util.Constants;
import com.yz.snews.util.SearchFilter2;
import com.yz.snews.util.SearchFilter2.Operator;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;


public abstract class BaseService<M, ID extends Serializable> {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	protected BaseDao<M, ID> dao;

	/**
	 * 根据ID获取实体对象.
	 * 
	 * @param id
	 *            记录ID
	 * @return 实体对象
	 */
	public M get(ID id) {
		return dao.findOne(id);
	}

	/**
	 * 根据ID集合获取实体对象集合.
	 * 
	 * @param ids
	 *            ID对象数组
	 * 
	 * @return 实体对象集合
	 */
	public Iterable<M> get(Iterable<ID> ids) {
		return dao.findAll(ids);
	}

	/**
	 * 获取所有实体对象集合.
	 * 
	 * @return 实体对象集合
	 */
	public List<M> getAll() {
		return dao.findAll();
	}

	public List<M> getAllWithoutFakeDel(){
		Query query = new Query();
		Class<M> clazz = Reflections.getClassGenricType(getClass());
		Criteria criteria = Criteria.where("status").ne(Constants.FAKE_DELETE_STATE);
		query.addCriteria(criteria);
		
		return mongoTemplate.find(query, clazz);
	}
	
	/**
	 * 分页获取实体对象集合.
	 * 
	 * @return 实体对象分页集合
	 */
	public Page<M> getAll(Pageable pageable) {
		return dao.findAll(pageable);
	}

	/**
	 * 获取所有实体对象总数.
	 * 
	 * @return 实体对象总数
	 */
	public Long getTotalCount() {
		return dao.count();
	}

	/**
	 * 根据属性名判断数据是否已存在.
	 * 
	 * @param propertyName
	 *            属性名称
	 * @param value
	 *            值
	 * @return boolean
	 */
	public boolean isExist(ID id) {
		return dao.exists(id);
	}

	/**
	 * 保存实体对象.
	 * 
	 * @param entity
	 *            对象
	 * @return M
	 */
	public M save(M entity) {
		return dao.save(entity);
	}
	
	public Iterable<M> save(Iterable<M> entities) {
		return dao.save(entities);
	}

	/**
	 * 更新实体对象.
	 * 
	 * @param id
	 * @param entity
	 * @return M
	 */
	public M update(ID id, M entity) {
		M dest = dao.findOne(id);
		BeanUtils.copyProperties(entity, dest);
		return dao.save(dest);
	}

	/**
	 * 删除实体对象.
	 * 
	 * @param entity
	 *            对象
	 * @return
	 */
	public void delete(M entity) {
		dao.delete(entity);
	}

	/**
	 * 根据ID删除实体对象.
	 * 
	 * @param id
	 *            记录ID
	 */
	public void delete(ID id) {
		dao.delete(id);
	}

	/**
	 * 根据ID集合删除实体对象.
	 * 
	 * @param ids
	 *            ID集合
	 */
	public void delete(Iterable<M> entities) {
		dao.delete(entities);
	}
	
	/**
	 * 分页
	 * @param filters	封装的查询条件
	 * @param pageable
	 * @param withFakeDel 是否查询出假删除的数据,true:查询, false:不查询
	 * @return
	 */
	public Page<M> findPageWithFakeDel(Map<String, SearchFilter2> filters, Pageable pageable, boolean withFakeDel) {
		Query query = new Query();
		Class<M> clazz = Reflections.getClassGenricType(getClass());
		Mirror<M> m = Mirror.me(clazz);
		boolean hasStatusField = false;
		for(Map.Entry<String, SearchFilter2> filter : filters.entrySet()) {
			SearchFilter2 sf = filter.getValue();
			String fieldName = sf.fieldName;
			Object value = sf.value;
			if ("status".equalsIgnoreCase(fieldName)) {
				hasStatusField = true;
			}
			
			Criteria criteria = Criteria.where(fieldName);
			
			try {
				if (sf.operator != Operator.IN && !fieldName.contains(".")) {
					Class<?> fieldClazz = m.getField(fieldName).getType();
					// 如有需要,可以自己添加一个Castor类, 从String->BigIneger
					value = Castors.me().castTo(value, fieldClazz);
				}
			}  catch (NoSuchFieldException e) {
				e.printStackTrace();
				logger.error("{}没有字段:{}", clazz.getName(), fieldName);
			}
			switch(sf.operator){
			case EQ:
				criteria.is(value);
				break;
			case GT:
				criteria.gt(value);
				break;
			case LT:
				criteria.lt(value);
				break;
			case GTE:
				criteria.gte(value);
				break;
			case LTE:
				criteria.lte(value);
				break;
			case LIKE:
				criteria.regex((String) value, "i");
				break;
			case IN:
				if(value instanceof String) {
					String col = (String)value;
					criteria.in(Lists.newArrayList(Splitter.on(",").split(col)));
				}
				if(value instanceof Collection) {
					Collection<?> col = (Collection<?>)value;
					criteria.in(col);
				}
				break;
			case NE:
				criteria.ne(value);
				break;
			default:
				break;
			}
			query.addCriteria(criteria);
		}
		if (!withFakeDel && !hasStatusField) {
			try {
				m.getField("status");
				Criteria criteria = Criteria.where("status").ne(Constants.FAKE_DELETE_STATE);
				query.addCriteria(criteria);
			} catch (NoSuchFieldException e) {
				logger.warn("{}类没有status 字段", clazz.getName());
			}
		}
		
		long count = mongoTemplate.count(query, clazz);
		query.with(pageable);
		query.with(new Sort(Direction.DESC, "modifiedTime"));
		return new PageImpl<M>(mongoTemplate.find(query, clazz), pageable, count);
	}
	
	public Page<M> findPage(Map<String, SearchFilter2> filters, Pageable pageable) {
		return findPageWithFakeDel(filters, pageable, false);
	}
	
	public List<M> findBy(String key, SearchFilter2.Operator operator, Object value) {
		Map<String, SearchFilter2> filters = Maps.newHashMap();
		SearchFilter2 sf = new SearchFilter2(key, operator, value);
		filters.put(key, sf);
		return findBySearchFilter(filters);
	}
	
	public List<M> findBySearchFilter(Map<String, SearchFilter2> filters) {
		Class<M> clazz = Reflections.getClassGenricType(getClass());
		Query query = buildQuery(filters);
		return mongoTemplate.find(query, clazz);
	}
	
	protected Query buildQuery(Map<String, SearchFilter2> filters){
		Query query = new Query();
		Class<M> clazz = Reflections.getClassGenricType(getClass());
		Mirror<M> m = Mirror.me(clazz);
		for(Map.Entry<String, SearchFilter2> filter : filters.entrySet()) {
			SearchFilter2 sf = filter.getValue();
			String fieldName = sf.fieldName;
			Object value = sf.value;
			
			Criteria criteria = Criteria.where(fieldName);
			try {
				if (sf.operator != Operator.IN) {
					Class<?> fieldClazz = m.getField(fieldName).getType();
					// 如有需要,可以自己添加一个Castor类, 从String->BigIneger
					value = Castors.me().castTo(value, fieldClazz);
				}
			}  catch (NoSuchFieldException e) {
				e.printStackTrace();
				logger.error("{}没有字段:{}", clazz.getName(), fieldName);
			}
			switch(sf.operator){
			case EQ:
				criteria.is(value);
				break;
			case GT:
				criteria.gt(value);
				break;
			case LT:
				criteria.lt(value);
				break;
			case GTE:
				criteria.gte(value);
				break;
			case LTE:
				criteria.lte(value);
				break;
			case LIKE:
				criteria.regex((String) value, "i");
				break;
			case IN:
				if(value instanceof String) {
					String col = (String)value;
					criteria.in(Lists.newArrayList(Splitter.on(",").split(col)));
				}
				if(value instanceof Collection) {
					Collection<?> col = (Collection<?>)value;
					criteria.in(col);
				}
				break;
			case NE:
				criteria.ne(value);
				break;
			default:
				break;
			}
			query.addCriteria(criteria);
		}
		Criteria criteria = Criteria.where("status").ne(Constants.FAKE_DELETE_STATE);
		query.addCriteria(criteria);
		return query;
	}
	
	/**
	 * 根据 fieldName=value 获取实体
	 * @param fieldName
	 * @param value
	 * @param withFakeDel	是否查询已删除的实体 true:查询, false:不查询
	 * @return
	 */
	public M getOneWithFakeDel(String fieldName, Object value, boolean withFakeDel) {
		Query query = new Query();
		Class<M> clazz = Reflections.getClassGenricType(getClass());
		query.addCriteria(Criteria.where(fieldName).is(value));
		if (!withFakeDel) {
			Criteria criteria = Criteria.where("status").ne(Constants.FAKE_DELETE_STATE);
			query.addCriteria(criteria);
		}
		return mongoTemplate.findOne(query, clazz);
	}
	
	public M getOneWithFakeDel(String fieldName, Object value, Sort sort, boolean withFakeDel) {
		Query query = new Query();
		Class<M> clazz = Reflections.getClassGenricType(getClass());
		query.addCriteria(Criteria.where(fieldName).is(value));
		query.with(sort);
		if (!withFakeDel) {
			Criteria criteria = Criteria.where("status").ne(Constants.FAKE_DELETE_STATE);
			query.addCriteria(criteria);
		}
		return mongoTemplate.findOne(query, clazz);
	}
	
	public M getOne(String fieldName, Object value) {
		return getOneWithFakeDel(fieldName, value, false);
	}
	
	public M getOne(String fieldName, Object value, Sort sort) {
		return getOneWithFakeDel(fieldName, value, sort, false);
	}
}

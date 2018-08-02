package com.mumu.core.dao;

import com.mumu.core.social.qq.QQBean;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Administrator on 2018/7/25.
 */
public interface QQBeanDao extends CrudRepository<QQBean,Long>{
    /**
     @Query(value = "select name,author,price from Book b where b.name like %:name%")
     @Query(value = "select * from book b where b.name=?1", nativeQuery = true)
     @Query(value = "select name,author,price from Book b where b.name = :name AND b.author=:author AND b.price=:price")
     List<Book> findByNamedParam(@Param("name") String name, @Param("author") String author,@Param("price") long price);
     */
    public QQBean findByUserId(String userId);

    public QQBean findByProviderUserId(String providerUserId);

    public QQBean findByDisplayName(String displayName);
}

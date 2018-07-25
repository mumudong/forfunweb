package com.mumu.dao;

import com.mumu.dto.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * Created by Administrator on 2018/7/25.
 */
public interface UserDao extends CrudRepository<User,Long>{
    /**
     @Query(value = "select name,author,price from Book b where b.name like %:name%")
     @Query(value = "select * from book b where b.name=?1", nativeQuery = true)
     @Query(value = "select name,author,price from Book b where b.name = :name AND b.author=:author AND b.price=:price")
     List<Book> findByNamedParam(@Param("name") String name, @Param("author") String author,@Param("price") long price);
     */
    public User findById(Long id);

    public User findByUsername(String username);

//    @Transactional
}

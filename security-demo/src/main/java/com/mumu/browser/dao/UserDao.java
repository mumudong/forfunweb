package com.mumu.browser.dao;

import com.mumu.demo.bean.UserLogin;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Administrator on 2018/7/25.
 */
public interface UserDao extends CrudRepository<UserLogin,Long>{
    /**
     @Query(value = "select name,author,price from Book b where b.name like %:name%")
     @Query(value = "select * from book b where b.name=?1", nativeQuery = true)
     @Query(value = "select name,author,price from Book b where b.name = :name AND b.author=:author AND b.price=:price")
     List<Book> findByNamedParam(@Param("name") String name, @Param("author") String author,@Param("price") long price);
     */
    public UserLogin findById(Long id);

    public UserLogin findByUsername(String username);

    public UserLogin findByPhone(String phone);
}

package com.mumu.bean;

import com.fasterxml.jackson.annotation.JsonView;
import com.mumu.validator.MyConstraint;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Past;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2018/7/25.
 */
@Entity
@Table(name = "user")
public class UserLogin implements Serializable{
    //定义视图接口
    public interface UserSimpleView{}
    public interface UserDetailView extends UserSimpleView{}
    @Id
    @GeneratedValue
    private Long id;
    @MyConstraint(message = "这是自定义的验证器")
    @Column(unique=true)
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;
    @Past(message = "生日必须是过去的时间")

//    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date birthday;
    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = new Date().getYear() - birthday.getYear();
    }
//    @ManyToMany(fetch= FetchType.EAGER)//立即从数据库中进行加载数据;
//    @JoinTable(name = "SysUserRole", joinColumns = { @JoinColumn(name = "uid") }, inverseJoinColumns ={@JoinColumn(name = "roleId") })
//    private List<SysRole> roleList;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @JsonView(UserSimpleView.class)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    @JsonView(UserDetailView.class)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @JsonView(UserSimpleView.class)
//    @JsonSerialize(using = DateJsonSerializer.class)
    public Date getBirthday() {
        return birthday;
    }

//    @JsonDeserialize(using = DateJsonDeserializer.class)
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
        this.age = new Date().getYear() - birthday.getYear();
    }

    @Override
    public String toString() {
        return "UserLoggin{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", birthday=" + birthday +
                ", age=" + age +
                '}';
    }
}

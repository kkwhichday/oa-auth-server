package com.oa.auth.repositories;



import com.oa.auth.model.User;
import com.oa.auth.vo.UserVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.transaction.annotation.Transactional;


/**
 * @description: 用户信息
 * @author: kk
 * @create: 2018-12-22
 **/
@Transactional
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User>, QueryDslPredicateExecutor<User> {
    User findUserByUsername(String userName);
    User findUserByUsernameAndPhone(String userName,String phone);
    User findUserByUserId(Integer id);
    User findUserByWorkCode(String workCode);


}

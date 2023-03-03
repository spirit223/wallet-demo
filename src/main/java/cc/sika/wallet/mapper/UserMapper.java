package cc.sika.order.mapper;

import cc.sika.order.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author 吴畅
 * @创建时间 2023/3/2 - 14:34
 */
@Mapper
public interface UserMapper {

    int addUser(User user);

    int deleteUser(@Param("userId") int userId);

    int modifyUser(User user);

    User getUserById(@Param("userId") int userId);
}

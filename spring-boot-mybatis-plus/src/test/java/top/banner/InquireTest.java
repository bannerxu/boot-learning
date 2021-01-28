package top.banner;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.interfaces.Compare;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import top.banner.entity.User;
import top.banner.mapper.UserMapper;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 查询测试
 *
 * @author XGL
 */
public class InquireTest extends BaseTest {
    @Resource
    private UserMapper userMapper;

    /**
     * 条件构造器
     */
    @Test
    public void selectByWrapper() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", "张")
                .ne("age", 10)
                .isNotNull("email")
                .orderByDesc("age");

        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    /**
     * 创建时间是2020-10-09并且上级是王姓的
     */
    @Test
    public void selectByWrapper1() {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.apply("date_format(create_time,'%Y-%m-%d') = {0}", "2020-10-09")
                .inSql("manager_id", "select id from user where name like '王%'");
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    /**
     * 王姓 并且 （年龄小于40 或 邮箱不能为空）
     * SELECT id,name,age,email,manager_id,create_time FROM user WHERE (name LIKE ? AND (age < ? OR email IS NOT NULL))
     */
    @Test
    public void selectByWrapper2() {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.likeRight("name", "王")
                .and(qw -> qw.lt("age", 40).or().isNotNull("email"));
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    /**
     * 王姓 或者 （年龄小于40 大于20  邮箱不能为空）
     * SELECT id,name,age,email,manager_id,create_time FROM user WHERE (name LIKE ? OR (age < ? AND age > ? AND email IS NOT NULL))
     */
    @Test
    public void selectByWrapper3() {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper.likeRight("name", "王")
                .or(qw -> qw.lt("age", 40).gt("age", 20).isNotNull("email"));
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    /**
     * （年龄小于40 或者 邮箱不能为空） 王姓
     * SELECT id,name,age,email,manager_id,create_time FROM user WHERE ((age < ? OR email IS NOT NULL) AND name LIKE ?)
     */
    @Test
    public void selectByWrapper4() {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper.nested(qw -> qw.lt("age", 40).or().isNotNull("email"))
                .likeRight("name", "王");
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }


    /**
     * 年龄为 30 31 34 35
     * SELECT id,name,age,email,manager_id,create_time FROM user WHERE (age IN (?,?,?,?))
     */
    @Test
    public void selectByWrapper5() {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper.in("age", Arrays.asList(30, 31, 34, 35));

        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }


    /**
     * 只返回一条数据
     * SELECT id,name,age,email,manager_id,create_time FROM user limit 1
     */
    @Test
    public void selectByWrapper6() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper.last("limit 1");

        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }


    /**
     * 名字中包含雨并且年龄小于40,只查询id,name
     * SELECT id,name FROM user WHERE (name LIKE ? AND age < ?)
     */
    @Test
    public void selectByWrapperSupper() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper.select("id", "name")
                .like("name", "雨")
                .lt("age", 40);

        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }


    /**
     * 名字中包含雨并且年龄小于40,不查询create_time,manager_id
     * SELECT id,name,age,email FROM user WHERE (name LIKE ? AND age < ?)
     */
    @Test
    public void selectByWrapperSupper2() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper.like("name", "雨")
                .lt("age", 40)
                .select(User.class, info -> !info.getColumn().equals("create_time") &&
                        !info.getColumn().equals("manager_id"));

        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    /**
     * condition条件为true,才拼接SQL
     * {@link com.baomidou.mybatisplus.core.conditions.interfaces.Compare#like(Object, Object)}
     * {@link com.baomidou.mybatisplus.core.conditions.interfaces.Compare#like(boolean, Object, Object)}
     */
    @Test
    public void condition() {
        //SELECT id,name,age,email,manager_id,create_time FROM user WHERE (email LIKE ?)
        condition(null, "qq.com");
        //SELECT id,name,age,email,manager_id,create_time FROM user WHERE (name LIKE ?)
        condition("王", null);
    }

    private void condition(String name, String email) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        if (StringUtils.isNotBlank(name)) {
//            queryWrapper.like("name", name);
//        }
//        if (StringUtils.isNotBlank(email)) {
//            queryWrapper.like("email", email);
//        }

        queryWrapper.like(StringUtils.isNotBlank(name), "name", name);
        queryWrapper.like(StringUtils.isNotBlank(email), "email", email);
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);

    }


    /**
     * 实体作为查询条件，和之前的方式重复，二选一即可
     * SELECT id,name,age,email,manager_id,create_time FROM user WHERE name=? AND age=?
     * <p>
     * <p>
     * 增加    @TableField(value = "name",condition = SqlCondition.LIKE)
     * SELECT id,name,age,email,manager_id,create_time FROM user WHERE name LIKE CONCAT('%',?,'%') AND age=?
     */
    @Test
    public void selectByWrapperEntity() {
        User user = new User();
        user.setName("刘红雨");
        user.setAge(32);
        user.setCreateTime(null);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(user);
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }


    /**
     * {@link Compare#allEq(Map)}
     * <p>
     * params.put("name", "王天风");
     * params.put("age", 25);
     * <p>
     * SELECT id,name,age,email,manager_id,create_time FROM user WHERE (name = ? AND age = ?)
     * =======
     * <p>
     * params.put("name", "王天风");
     * params.put("age", null);
     * <p>
     * SELECT id,name,age,email,manager_id,create_time FROM user WHERE (name = ? AND age IS NULL)
     * =======
     * <p>
     * params.put("name", "王天风");
     * params.put("age", null);
     * <p>
     * queryWrapper.allEq((k, v) -> !k.equals("name"), params);
     * <p>
     * SELECT id,name,age,email,manager_id,create_time FROM user WHERE (age IS NULL)
     */
    @Test
    public void selectByWrapperAllEq() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        Map<String, Object> params = new HashMap<>();
        params.put("name", "王天风");
//        params.put("age", 25);
        params.put("age", null);
//        queryWrapper.allEq(params);
        queryWrapper.allEq((k, v) -> !k.equals("name"), params);
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }


    /**
     * {@link BaseMapper#selectMaps(Wrapper)}
     */
    @Test
    public void selectByWrapperMaps() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", "张")
                .ne("age", 10)
                .isNotNull("email")
                .orderByDesc("age");

        List<Map<String, Object>> maps = userMapper.selectMaps(queryWrapper);
        maps.forEach(System.out::println);
    }


    /**
     * 按照上级分组，查询每组的平均年龄，最大年龄，最小年龄，并只取年龄小于500的组
     * SELECT manager_id,avg(age) avg_age,min(age) min_age,max(age) max_age FROM user GROUP BY manager_id HAVING sum(age) < ?
     */
    @Test
    public void selectByWrapperMaps2() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("manager_id", "avg(age) avg_age", "min(age) min_age", "max(age) max_age")
                .groupBy("manager_id").having("sum(age) < {0}", 500);

        List<Map<String, Object>> maps = userMapper.selectMaps(queryWrapper);
        maps.forEach(System.out::println);
    }

    /**
     * {@link BaseMapper#selectObjs(Wrapper)}
     * 只返回第一列，其他的舍弃
     */
    @Test
    public void selectByWrapperObjs() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id", "name");
        List<Object> list = userMapper.selectObjs(queryWrapper);
        list.forEach(System.out::println);
    }


    /**
     * {@link BaseMapper#selectCount(Wrapper)}
     */
    @Test
    public void selectByWrapperCount() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        Integer count = userMapper.selectCount(queryWrapper);
        System.out.println("总记录数 count:" + count);
    }

    /**
     * {@link BaseMapper#selectOne(Wrapper)}
     */
    @Test
    public void selectByWrapperOne() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.like("name", "雨").lt("age", 40); TooManyResultsException
        queryWrapper.like("name", "刘红雨").lt("age", 40);
        User user = userMapper.selectOne(queryWrapper);
        System.out.println("user：" + user);
    }

    @Test
    public void selectLambda() {
//        LambdaQueryWrapper<User> lambda = new QueryWrapper<User>().lambda();
//        LambdaQueryWrapper<User> lambda1 = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<User> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.like(User::getName, "雨")
                .lt(User::getAge, 40);
        List<User> users = userMapper.selectList(lambdaQuery);
        users.forEach(System.out::println);

    }

    /**
     * 王姓 并且 （年龄小于40 或 邮箱不能为空）
     * SELECT id,name,age,email,manager_id,create_time FROM user WHERE (name LIKE ? AND (age < ? OR email IS NOT NULL))
     */
    @Test
    public void selectLambda2() {

        LambdaQueryWrapper<User> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.likeRight(User::getName, "王")
                .and(lq -> lq.lt(User::getAge, 40).or()
                        .isNotNull(User::getEmail));
        List<User> users = userMapper.selectList(lambdaQuery);
        users.forEach(System.out::println);

    }

    /**
     * {@link LambdaQueryChainWrapper}
     * SELECT id,name,age,email,manager_id,create_time FROM user WHERE (name LIKE ? AND age >= ?)
     */
    @Test
    public void selectLambda3() {

        List<User> list = new LambdaQueryChainWrapper<>(userMapper)
                .like(User::getName, "雨")
                .ge(User::getAge, 40).list();
        list.forEach(System.out::println);

    }


    /**
     * 自定义方法
     * select * from user WHERE (name LIKE ? AND (age < ? OR email IS NOT NULL))
     */
    @Test
    public void selectMy() {
        LambdaQueryWrapper<User> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.likeRight(User::getName, "王")
                .and(lq -> lq.lt(User::getAge, 40).or()
                        .isNotNull(User::getEmail));
        List<User> users = userMapper.selectAll(lambdaQuery);
        users.forEach(System.out::println);
    }

    /**
     * 分页
     * SELECT COUNT(1) FROM user
     * SELECT id,name,age,email,manager_id,create_time FROM user LIMIT ?
     */
    @Test
    public void selectPage() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        Page<User> page = new Page<>(1, 2);
        //是否查询总记录数
        //Page<User> page = new Page<>(1, 2,false);

        Page<User> userPage = userMapper.selectPage(page, queryWrapper);
        System.out.println(userPage.getRecords());

    }

    /**
     * 自定义分页查询
     */
    @Test
    public void selectMyPage() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        Page<User> page = new Page<>(1, 2);

        IPage<User> userIPage = userMapper.selectUserPage(page, queryWrapper);
        System.out.println(userIPage.getRecords());
    }
}

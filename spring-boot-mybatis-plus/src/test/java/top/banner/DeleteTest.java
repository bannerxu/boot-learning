package top.banner;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.junit.Test;
import top.banner.entity.User;
import top.banner.mapper.UserMapper;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author XGL
 */
public class DeleteTest extends BaseTest {
    @Resource
    private UserMapper userMapper;


    /**
     * DELETE FROM user WHERE id=?
     */
    @Test
    public void deleteById() {
        int count = userMapper.deleteById(1314383083163705346L);
        System.out.println("删除条数：" + count);
    }

    /**
     * {@link com.baomidou.mybatisplus.core.mapper.BaseMapper#deleteByMap(Map)}
     */
    @Test
    public void deleteByMap() {
        Map<String, Object> columnMaps = new HashMap<>();
        columnMaps.put("name", "李四");
        columnMaps.put("age", "26");
        int count = userMapper.deleteByMap(columnMaps);
        System.out.println("删除条数：" + count);

    }


    /**
     * DELETE FROM user WHERE id IN ( ? , ? , ? )
     */
    @Test
    public void deleteBatchIds() {
        int count = userMapper.deleteBatchIds(Arrays.asList(1314382578609872898L,
                1314383847420379137L,
                1314383932715761666L
        ));
        System.out.println("删除条数：" + count);
    }

    /**
     * DELETE FROM user WHERE (age = ? OR age > ?)
     */
    @Test
    public void deleteByWrapper() {
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(User::getAge, 30).or().gt(User::getAge, 40);
        int count = userMapper.delete(wrapper);
        System.out.println("删除条数：" + count);

    }

}

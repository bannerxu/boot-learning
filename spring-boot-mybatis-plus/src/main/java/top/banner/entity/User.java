package top.banner.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("user")
public class User {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 姓名
     */
//    @TableField(value = "name", condition = SqlCondition.LIKE)
    private String name;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 上级id
     */
    private Long managerId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;


    /**
     * 备注
     */
    //不参与序列化
    //private transient String remake;
    //静态方法
    //private static String remake;
    @TableField(exist = false)
    private String remake;
}
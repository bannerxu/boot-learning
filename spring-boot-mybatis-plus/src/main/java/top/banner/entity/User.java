package top.banner.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("user")
public class User {

    @TableId
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
     * 直属上级id
     */
    private Long managerId;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 版本
     * 仅支持 updateById(id) 与 update(entity, wrapper) 方法
     */
    @Version
    private Integer version;

    /**
     * 逻辑删除标识(0.未删除,1.已删除)
     */
    @TableLogic
    @TableField(select = false) //查询的时候不显示该字段
    private Integer deleted;


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
package top.banner.utils;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author XGL
 */
public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T> {
}

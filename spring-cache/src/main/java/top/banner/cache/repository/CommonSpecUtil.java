package top.banner.cache.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.JoinType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;

@Component
public class CommonSpecUtil<T> {


    /**
     * 精确匹配(equal)
     *
     * @param srcName        字段名
     * @param targetProperty 匹配内容
     */
    public Specification<T> equal(String srcName, Object targetProperty) {
        if (targetProperty == null) {
            return null;
        }
        return (root, query, cb) -> cb.equal(root.get(srcName), targetProperty);
    }

    /**
     * 精确匹配(notEqual)
     *
     * @param srcName        字段名
     * @param targetProperty 匹配内容
     */
    public Specification<T> notEqual(String srcName, Object targetProperty) {
        if (targetProperty == null) {
            return null;
        }
        return (root, query, cb) -> cb.notEqual(root.get(srcName), targetProperty);
    }

    /**
     * 模糊匹配(like)
     *
     * @param srcName        字段名
     * @param targetProperty 匹配内容
     */
    public Specification<T> like(String srcName, String targetProperty) {
        if (targetProperty == null) {
            return null;
        }
        return (root, query, cb) -> cb.like(root.get(srcName), "%" + targetProperty + "%");
    }


    /**
     * 日期范围匹配(timeBetween)
     *
     * @param srcName   字段名
     * @param startTime 开始时间
     * @param endTime   结束时间
     */
    public Specification<T> timeBetween(String srcName, Date startTime, Date endTime) {
        if (startTime == null) {
            return null;
        }
        if (endTime == null) {
            return null;
        }
        return (root, query, cb) -> cb.between(root.get(srcName), startTime, endTime);
    }

//    /**
//     * 日期范围匹配(timeBetween)
//     *
//     * @param srcName   字段名
//     * @param startTime 开始时间
//     * @param endTime   结束时间
//     */
//    public Specification<T> timeBetweenLocalDate(String srcName, LocalDateTime startTime, LocalDateTime endTime) {
//        if (startTime == null) {
//            startTime = DateUtils.minLocalDateTime();
//        }
//        if (endTime == null) {
//            endTime = LocalDateTime.now();
//        }
//        LocalDateTime finalStartTime = startTime;
//        LocalDateTime finalEndTime = endTime;
//        return (root, query, cb) -> cb.between(root.get(srcName), finalStartTime, finalEndTime);
//    }

//    /**
//     * 日期范围匹配(timeBetween)
//     *
//     * @param srcName      字段名
//     * @param startTimeStr 开始时间
//     * @param endTimeStr   结束时间
//     */
//    public Specification<T> timeBetweenLocalDate(String srcName, String startTimeStr, String endTimeStr) {
//        if (StringUtils.isBlank(startTimeStr)) {
//            return null;
//        }
//        if (StringUtils.isBlank(endTimeStr)) {
//            return null;
//        }
//        LocalDateTime startTime = DateUtils.getLocalDateTime(startTimeStr, DateUtils.DATE_FORMAT_19);
//        LocalDateTime endTime = DateUtils.getLocalDateTime(endTimeStr, DateUtils.DATE_FORMAT_19);
//
//        return (root, query, cb) -> cb.between(root.get(srcName), startTime, endTime);
//    }


    /**
     * 数值范围匹配(between)
     *
     * @param srcName 字段名
     * @param start   开始
     * @param end     结束
     */
    public Specification<T> between(String srcName, double start, double end) {
        if (org.springframework.util.StringUtils.isEmpty(start)) {
            return null;
        }
        if (org.springframework.util.StringUtils.isEmpty(end)) {
            return null;
        }
        return (root, query, cb) -> cb.between(root.get(srcName), start, end);
    }


    public Specification<T> bigDecimalBetween(String srcName, BigDecimal start, BigDecimal end) {
        if (start == null) {
            return null;
        }
        if (end == null) {
            return null;
        }
        return (root, query, cb) -> cb.between(root.get(srcName), start.doubleValue(), end.doubleValue());
    }

    /**
     * 大于等于(greaterThanOrEqualTo)
     *
     * @param srcName 字段名
     * @param value   数值
     */
    public Specification<T> greaterThanOrEqualTo(String srcName, Long value) {
        if (value == null) {
            return null;
        }
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get(srcName), value);
    }


    /**
     * 大于等于(greaterThanOrEqualTo)
     *
     * @param srcName 字段名
     * @param value   数值
     */
    public Specification<T> greaterThanOrEqualTo(String srcName, LocalDateTime value) {
        if (value == null) {
            return null;
        }
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get(srcName), value);
    }

    /**
     * 小于等于(lessThanOrEqualTo)
     *
     * @param srcName 字段名
     * @param value   数值
     */
    public Specification<T> lessThanOrEqualTo(String srcName, LocalDateTime value) {
        if (value == null) {
            return null;
        }
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get(srcName), value);
    }


    /**
     * 小于等于(lessThanOrEqualTo)
     *
     * @param srcName 字段名
     * @param value   数值
     */
    public Specification<T> lessThanOrEqualTo(String srcName, Long value) {
        if (value == null) {
            return null;
        }
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get(srcName), value);
    }

    /**
     * 小于等于(dateLessThanOrEqualTo)
     *
     * @param srcName 字段名
     * @param value   数值
     */
    public Specification<T> dateLessThanOrEqualTo(String srcName, Date value) {
        if (value == null) {
            return null;
        }
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get(srcName), value);
    }

    /**
     * 大于等于(dateLessThanOrEqualTo)
     *
     * @param srcName 字段名
     * @param value   数值
     */
    public Specification<T> dateGreaterThanOrEqualTo(String srcName, Date value) {
        if (value == null) {
            return null;
        }
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get(srcName), value);
    }

    /**
     * in条件帅选(in)
     *
     * @param srcName 字段名
     * @param list    集合
     */
    public <A> Specification<T> in(String srcName, Collection<A> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        return (root, query, cb) -> cb.and(root.get(srcName).in(list));
    }

    /**
     * 不为空(isNotNull)
     *
     * @param srcName 字段名
     */
    public Specification<T> isNotNull(String srcName) {
        return (root, query, cb) -> cb.isNotNull(root.get(srcName));
    }

    /**
     * 倒序(desc)
     *
     * @param srcName 字段名
     */
    public Specification<T> desc(String srcName) {
        return (root, query, cb) -> query.orderBy(cb.desc(root.get(srcName).as(Integer.class))).getRestriction();
    }

    /**
     * 升序(asc)
     *
     * @param srcName 字段名
     */
    public Specification<T> asc(String srcName) {
        return (root, query, cb) -> query.orderBy(cb.asc(root.get(srcName).as(Integer.class))).getRestriction();
    }


    /**
     * left join
     */
    public Specification<T> leftJoin(String joinName, String attribute, Object value) {
        if (value == null) {
            return null;
        }
        return (root, query, cb) -> cb.equal(root.join(joinName, JoinType.LEFT).get(attribute), value);
    }

    /**
     * left join in
     */
    public Specification<T> leftJoinIn(String srcName, Collection<Long> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }

        return (root, query, cb) -> cb.and(root.join(srcName, JoinType.LEFT).get("id").in(list));
    }


}
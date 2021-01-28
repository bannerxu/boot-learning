package top.banner;

import org.junit.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import top.banner.entity.User;
import top.banner.repository.UserRepository;
import top.banner.utils.CommonSpecUtil;

import javax.annotation.Resource;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author XGL
 */
public class UserTest extends BaseTest {
    @Resource
    private UserRepository userRepository;
    @Resource
    private CommonSpecUtil<User> commonSpecUtil;

    @Test
    public void all() {
        userRepository.findAll().forEach(System.out::println);
    }

    @Test
    public void customSQL() {
        userRepository.groupBy().forEach(System.out::println);
    }


    @Test
    public void spec() {
        Specification<User> where = Specification.where(null);
        userRepository.findAll(where, PageRequest.of(0, 10, Sort.by(Sort.Order.desc("id"))))
                .getContent()
                .forEach(System.out::println);


        System.out.println("=====");
        Specification<User> idCondition = commonSpecUtil.greaterThanOrEqualTo("id", 3L);
        idCondition.and(commonSpecUtil.equal("username", "达志"));

        Specification<User> and = Specification.where(idCondition).and(commonSpecUtil.equal("username", "达志"));
        userRepository.findAll(and)
                .forEach(System.out::println);

    }

    @Test
//    @Repeat(10)
    public void add() {
        long l = System.currentTimeMillis();
        Random random = new Random();

        List<User> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            User user = new User();
            user.setGender(random.nextInt(2));

            int i1 = random.nextInt(23);
            int i2 = random.nextInt(23);
            if (i1 < i2) {
                user.setStartTime(LocalDateTime.of(2020, 11, 2, i1, 0, 0));
                user.setEndTime(LocalDateTime.of(2020, 11, 2, i2, 0, 0));
            } else {
                user.setStartTime(LocalDateTime.of(2020, 11, 2, i1, 0, 0));
                user.setEndTime(LocalDateTime.of(2020, 11, 3, i2, 0, 0));
            }

//            userRepository.save(user);

            list.add(user);
            if (list.size() == 100) {
                userRepository.saveAll(list);
                list.clear();
            }
        }
        userRepository.saveAll(list);


        System.out.println("插入耗时：" + (System.currentTimeMillis() - l));
    }

    //插入耗时：1888插入耗时：1532插入耗时：1607
    //插入耗时：2859插入耗时：3178插入耗时：2906
    @Test
    public void inOperationConditionTest() {
        Specification<User> where = Specification.where(commonSpecUtil.greaterThanOrEqualTo("id", 1L))
                .and(inOperationCondition());


        long count = userRepository.count(where);
        System.out.println(count);
    }


    /**
     * select count(*)
     * from user
     * where (
     * # 同一天
     * DATE_FORMAT(end_time, '%Y-%m-%d') = DATE_FORMAT(start_time, '%Y-%m-%d') and
     * CURTIME() between DATE_FORMAT(start_time, '%T') and DATE_FORMAT(end_time, '%T')
     * )
     * or (
     * # 跨天
     * DATE_FORMAT(end_time, '%Y-%m-%d') > DATE_FORMAT(start_time, '%Y-%m-%d') and
     * (DATE_FORMAT(start_time, '%T') <= CURTIME() or
     * curtime() <= DATE_FORMAT(end_time, '%T'))
     * );
     */
    public Specification<User> inOperationCondition() {
        return (root, query, cb) -> {

            Expression<Date> endTime = cb.function("DATE_FORMAT", Date.class, root.get("endTime"), cb.literal("'%Y-%m-%d'"));
            Expression<Date> startTime = cb.function("DATE_FORMAT", Date.class, root.get("startTime"), cb.literal("'%Y-%m-%d'"));

            Expression<Date> endTime5 = cb.function("DATE_FORMAT", Date.class, root.get("endTime"), cb.literal("'%T'"));
            Expression<Date> startTime5 = cb.function("DATE_FORMAT", Date.class, root.get("startTime"), cb.literal("'%T'"));

            Expression<Date> now = cb.function("CURTIME", Date.class);

            Predicate p = cb.conjunction();

//            p = cb.and(p,
//                    cb.equal(endTime, startTime),
//                    cb.between(now, startTime5, endTime5)
//
//            );
            p = cb.and(p,
                    cb.and(
                            cb.lessThan(endTime, startTime)
                    )
//                    ,
//                    cb.or(
//                            cb.greaterThan(now, startTime5),
//                            cb.lessThan(now, endTime5)
//                    )
            );
            return p;

        };
    }


    /**
     * select *
     * from user
     * where (10 > id and id < 15);
     */
    @Test
    public void test1() {
        Specification<User> where = (root, query, cb) -> {
            Predicate p = cb.conjunction();

            p = cb.and(p,
                    cb.greaterThan(root.get("id"), 10),
                    cb.lessThan(root.get("id"), 15)
            );
            return p;
        };

        userRepository.findAll(where).forEach(System.out::println);
    }


    /**
     * select *
     * from user
     * where (10 > id and id < 15)
     * or id < 5;
     */
    @Test
    public void test2() {
        Specification<User> where = (root, query, cb) -> {
            Predicate p = cb.conjunction();

            p = cb.and(p,
                    cb.greaterThan(root.get("id"), 10),
                    cb.lessThan(root.get("id"), 15)
            );
            p = cb.or(p,
                    cb.lessThan(root.get("id"), 5)
            );
            return p;
        };

        userRepository.findAll(where).forEach(System.out::println);
    }

    /**
     * select *
     * from user
     * where (10 > id and id < 15)
     * or (20 < id and id < 25);
     */
    @Test
    public void test3() {
        Specification<User> where = (root, query, cb) -> {
            Predicate p = cb.conjunction();
            p = cb.and(p, cb.and(
                    cb.greaterThan(root.get("id"), 10),
                    cb.lessThan(root.get("id"), 15)
                    )

            );
            p = cb.or(p, cb.and(
                    cb.greaterThan(root.get("id"), 20),
                    cb.lessThan(root.get("id"), 25)
                    ), cb.or(cb.equal(root.get("id"), 18))

            );
            return p;
        };

        userRepository.findAll(where).forEach(System.out::println);
    }
}
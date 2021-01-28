package top.banner.security.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.HashSet;
import java.util.Set;

public class BeanUtils extends org.springframework.beans.BeanUtils {
    private static final Logger log = LoggerFactory.getLogger(BeanUtils.class);

    public static <F, T> T copy(F from, Class<T> toClass) {
        final T to;
        try {
            to = toClass.newInstance();
        } catch (Exception e) {
            log.warn("", e);
            return null;
        }
        copyProperties(from, to);
        return to;
    }

    /**
     * copy
     *
     * @param source 源对象
     * @param target 被copy的对象
     */
    public static void copyNonNullProperties(Object source, Object target) {
        org.springframework.beans.BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }

    private static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    public static String createGetMethodNameByFieldName(String fieldName) {
        return "get" +
                fieldName.substring(0, 1).toUpperCase() +
                fieldName.substring(1);

    }

    public static String createSetMethodNameByFieldName(String fieldName) {
        return "set" +
                fieldName.substring(0, 1).toUpperCase() +
                fieldName.substring(1);
    }
}

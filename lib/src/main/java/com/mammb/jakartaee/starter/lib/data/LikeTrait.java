package com.mammb.jakartaee.starter.lib.data;

import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.metamodel.SingularAttribute;

import java.util.Objects;
import java.util.regex.Pattern;

public interface LikeTrait<T> extends CriteriaContext<T> {

    default Predicate like(String value, SingularAttribute<? super T, String> attr) {
        return isEmpty(value) ? null : builder().like(root().get(attr), likeEscape(value) + "%", '\\');
    }
    default <T1> Predicate like(String value, SingularAttribute<? super T, T1> attr, SingularAttribute<T1, String> attr1) {
        return isEmpty(value) ? null : builder().like(root().get(attr).get(attr1), likeEscape(value) + "%", '\\');
    }
    default <T1, T2> Predicate like(String value, SingularAttribute<? super T, T1> attr, SingularAttribute<T1, T2> attr1, SingularAttribute<T2, String> attr2) {
        return isEmpty(value) ? null : builder().like(root().get(attr).get(attr1).get(attr2), likeEscape(value) + "%", '\\');
    }
    default <T1, T2, T3> Predicate like(String value, SingularAttribute<? super T, T1> attr, SingularAttribute<T1, T2> attr1, SingularAttribute<T2, T3> attr2, SingularAttribute<T3, String> attr3) {
        return isEmpty(value) ? null : builder().like(root().get(attr).get(attr1).get(attr2).get(attr3), likeEscape(value) + "%", '\\');
    }
    default <T1, T2, T3, T4> Predicate like(String value, SingularAttribute<? super T, T1> attr, SingularAttribute<T1, T2> attr1, SingularAttribute<T2, T3> attr2, SingularAttribute<T3, T4> attr3, SingularAttribute<T4, String> attr4) {
        return isEmpty(value) ? null : builder().like(root().get(attr).get(attr1).get(attr2).get(attr3).get(attr4), likeEscape(value) + "%", '\\');
    }

    Pattern ESCAPE_PATTERN = Pattern.compile("([%_\\\\])");
    private static String likeEscape(String str) {
        return ESCAPE_PATTERN.matcher(str).replaceAll("\\\\$1");
    }

    private static boolean isEmpty(String value) {
        return Objects.isNull(value) || value.isEmpty();
    }

}

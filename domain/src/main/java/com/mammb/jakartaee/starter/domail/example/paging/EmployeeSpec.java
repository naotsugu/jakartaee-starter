package com.mammb.jakartaee.starter.domail.example.paging;

import com.mammb.jakartaee.starter.lib.page.SortSpec;
import com.mammb.jakartaee.starter.lib.criteria.Specification;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Root;

public abstract class EmployeeSpec {

    public static SortSpec<Employee> idAsc() {
        return ctx -> ctx.asc(root -> on(root).getId());
    }
    public static SortSpec<Employee> idDesc() {
        return ctx -> ctx.desc(root -> on(root).getId());
    }

    public static SortSpec<Employee> nameAsc() {
        return ctx -> ctx.asc(root -> on(root).getName());
    }
    public static SortSpec<Employee> nameDesc() {
        return ctx -> ctx.desc(root -> on(root).getName());
    }

    public static Specification<Employee> nameEq(final String name) {
        return ctx -> ctx.eq(root -> on(root).getName(), name);
    }

    public static Specification<Employee> nameLike(final String name) {
        return ctx -> ctx.like(root -> on(root).getName(), name);
    }

    public static Specification<Employee> deptNameLike(final String name) {
        return ctx -> ctx.like(root -> on(root).getDepartment().getName(), name);
    }

    private static Employee_Root_ on(Root<Employee> root) {
        return new Employee_Root_(root);
    }

    // standard way
    public static Specification<Employee> deptNameEqualsTo(final String name) {
        return ctx -> {
            Root<Employee> root = ctx.root();
            CriteriaBuilder cb = ctx.builder();
            return cb.equal(root.get(Employee_.department).get(Department_.name), name);
        };
    }

}

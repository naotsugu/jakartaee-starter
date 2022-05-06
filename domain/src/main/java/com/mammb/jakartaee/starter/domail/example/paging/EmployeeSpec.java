package com.mammb.jakartaee.starter.domail.example.paging;

import com.mammb.jakartaee.starter.lib.sort.SortSpec;
import com.mammb.jakartaee.starter.lib.criteria.Specification;
import com.mammb.jakartaee.starter.domail.example.paging.Employee_.Root_;
import jakarta.persistence.criteria.Root;

public class EmployeeSpec {

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


    public static Specification<Employee> nameEq(String name) {
        return ctx -> ctx.eq(root -> on(root).getName(), name);
    }

    public static Specification<Employee> nameLike(String name) {
        return ctx -> ctx.like(root -> on(root).getName(), name);
    }

    public static Specification<Employee> deptNameLike(String name) {
        return ctx -> ctx.like(root -> on(root).getDepartment().getName(), name);
    }

    private static Root_ on(Root<Employee> root) {
        return new Root_(root);
    }

}

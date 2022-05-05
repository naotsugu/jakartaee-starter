package com.mammb.jakartaee.starter.domail.example.paging;

import com.mammb.jakartaee.starter.lib.sort.SortSpec;
import com.mammb.jakartaee.starter.lib.criteria.Specification;
import com.mammb.jakartaee.starter.domail.example.paging.Employee_.Root_;

public class EmployeeSpec {

    public static SortSpec<Employee> idAsc() {
        return ctx -> ctx.asc(Employee_.id);
    }
    public static SortSpec<Employee> idDesc() {
        return ctx -> ctx.desc(Employee_.id);
    }

    public static SortSpec<Employee> nameAsc() {
        return ctx -> ctx.asc(Employee_.name);
    }
    public static SortSpec<Employee> nameDesc() {
        return ctx -> ctx.desc(Employee_.name);
    }


    public static Specification<Employee> nameEq(String name) {
        return ctx -> ctx.eq(ctx.on(Root_.class).getName(), name);
    }

    public static Specification<Employee> nameLike(String name) {
        return ctx -> ctx.like(ctx.on(Root_.class).getName(), name);
    }

    public static Specification<Employee> deptNameLike(String name) {
        return ctx -> ctx.like(ctx.on(Root_.class).joinDepartment().getName(), name);
    }

}

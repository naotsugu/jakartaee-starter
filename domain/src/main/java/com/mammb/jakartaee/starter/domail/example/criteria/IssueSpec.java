package com.mammb.jakartaee.starter.domail.example.criteria;

import com.mammb.jakartaee.starter.lib.criteria.Specification;
import jakarta.persistence.criteria.Root;

import java.time.LocalDateTime;

public abstract class IssueSpec {

    public static Specification<Issue> titlePartialLike(final String title) {
        return ctx -> ctx.partialLike(root -> on(root).getTitle(), title);
    }

    public static Specification<Issue> projectNameEq(final Project project) {
        return ctx -> ctx.eq(root -> on(root).getProject().get(), project);
    }


    public static Specification<Issue> projectNameEq(final String name) {
        return ctx -> ctx.eq(root -> on(root).getProject().getName(), name);
    }

    public static Specification<Issue> recentlyPosted(LocalDateTime baseDate) {
        return ctx -> ctx.exists(c -> c.gt(root -> on(root).joinJournals().getPostedOn(), baseDate));
    }


    private static Issue_Root_ on(Root<Issue> root) {
        return new Issue_Root_(root);
    }

}

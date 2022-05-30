package com.mammb.jakartaee.starter.domail.example.criteria;

import com.mammb.jakartaee.starter.domail.Root_;
import com.mammb.jakartaee.starter.lib.criteria.Specification;
import jakarta.persistence.criteria.Root;

import java.time.LocalDateTime;

public abstract class IssueSpec {

    public static Specification<Issue> titlePartialLike(final String title) {
        return ctx -> ctx.partialLike(ctx.on(Root_::issue).getTitle(), title);
    }

    public static Specification<Issue> projectNameEq(final Project project) {
        return ctx -> ctx.eq(ctx.on(Root_::issue).getProject().get(), project);
    }


    public static Specification<Issue> projectNameEq(final String name) {
        return ctx -> ctx.eq(ctx.on(Root_::issue).getProject().getName(), name);
    }

    public static Specification<Issue> recentlyPosted(LocalDateTime baseDate) {
        return ctx -> ctx.exists(c -> c.gt(ctx.on(Root_::issue).joinJournals().getPostedOn(), baseDate));
    }

}

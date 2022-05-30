package com.mammb.jakartaee.starter.domail.example.criteria;

import com.mammb.jakartaee.starter.domail.Root_;
import com.mammb.jakartaee.starter.lib.criteria.Specification;
import jakarta.persistence.criteria.Root;
import java.util.List;

public abstract class ProjectSpec {

    public static Specification<Project> stateIn(final List<ProjectState> states) {
        return ctx -> ctx.on(Root_::project).getState().in(states);
    }

    public static Specification<Project> issueTitleContains(final String title) {
        return ctx -> ctx.exists(Issue.class, sub -> {
            var issue = Root_.issue(sub);
            return ctx.and(
                ctx.eq(issue.getProject().get(), ctx.root()),
                ctx.eq(issue.getTitle(), title));
        });
    }

}

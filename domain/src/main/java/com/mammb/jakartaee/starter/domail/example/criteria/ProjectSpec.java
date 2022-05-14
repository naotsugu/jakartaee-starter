package com.mammb.jakartaee.starter.domail.example.criteria;

import com.mammb.jakartaee.starter.lib.criteria.Specification;
import jakarta.persistence.criteria.Root;
import java.util.List;

public abstract class ProjectSpec {

    public static Specification<Project> stateIn(final List<ProjectState> states) {
        return ctx -> on(ctx.root()).getState().in(states);
    }

    public static Specification<Project> issueTitleContains(final String title) {
        return ctx -> ctx.exists(Issue.class, sub -> {
            var issue = new Issue_Root_(sub);
            return ctx.and(
                ctx.eq(issue.getProject().get(), ctx.root()),
                ctx.eq(issue.getTitle(), title));
        });
    }

    private static Project_Root_ on(Root<Project> root) {
        return new Project_Root_(root);
    }

}

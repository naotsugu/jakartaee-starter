package com.mammb.jakartaee.starter.view.example.paging;

import com.mammb.jakartaee.starter.app.example.paging.EmployeeQueryRepository;
import com.mammb.jakartaee.starter.domail.example.paging.Department_;
import com.mammb.jakartaee.starter.domail.example.paging.Employee;
import com.mammb.jakartaee.starter.domail.example.paging.Employee_;
import com.mammb.jakartaee.starter.lib.page.Slice;
import com.mammb.jakartaee.starter.lib.viewmodel.PageDataModel;
import com.mammb.jakartaee.starter.lib.viewmodel.PageRequestModel;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;

@Named
@ViewScoped
public class EmployeeModel implements Serializable {

    @Inject
    private EmployeeQueryRepository repository;

    private PageRequestModel<Employee> request;
    private PageDataModel<Employee> page;

    private String name;
    private String deptName;

    @PostConstruct
    public void postConstruct() {
        request = PageRequestModel.of();
        page = PageDataModel.of(this::find);
    }

    Slice<Employee> find(int page) {
        request.setNumber(page);
        return repository.findPage(request, ctx ->
            ctx.and(ctx.eq(ctx.root().get(Employee_.name), name),
            ctx.like(ctx.root().get(Employee_.department).get(Department_.name), deptName)));
    }

    public void find() {
        page.load(0);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public PageRequestModel<Employee> getRequest() {
        return request;
    }

    public PageDataModel<Employee> getPage() {
        return page;
    }
}

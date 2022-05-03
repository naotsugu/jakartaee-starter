package com.mammb.jakartaee.starter.view.example.paging;

import com.mammb.jakartaee.starter.app.example.paging.EmployeeQueryRepository;
import com.mammb.jakartaee.starter.domail.example.paging.Employee;
import com.mammb.jakartaee.starter.domail.example.paging.EmployeeSpec;
import com.mammb.jakartaee.starter.lib.page.Slice;
import com.mammb.jakartaee.starter.lib.model.PageDataModel;
import com.mammb.jakartaee.starter.lib.model.PageRequestModel;
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
        request = PageRequestModel.of(EmployeeSpec.nameAsc().and(EmployeeSpec.idAsc()));
        page = PageDataModel.of(this::find);
    }

    Slice<Employee> find(int page) {
        request.setNumber(page);
        return repository.findPage(request,
            EmployeeSpec.nameLike(name).and(EmployeeSpec.deptNameLike(deptName)));
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

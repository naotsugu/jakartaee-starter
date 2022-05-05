package com.mammb.jakartaee.starter.domail.example.criteria;

import com.mammb.jakartaee.starter.lib.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToOne;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Entity
public class RootItem extends BasicEntity<RootItem> {

    private String name;
    private String nameN;

    @OneToOne
    private ChileItem chileItem;

    @Enumerated(EnumType.STRING)
    private ItemState state;

    private List<ChileItem> children;

    private Set<ChileItem> childrenSet;

    private Map<String, ChileItem> childrenMap;

    public String getName() {
        return name;
    }

    public ItemState getState() {
        return state;
    }

    public List<ChileItem> getChildren() {
        return children;
    }

    public Set<ChileItem> getChildrenSet() {
        return childrenSet;
    }

    public Map<String, ChileItem> getChildrenMap() {
        return childrenMap;
    }

    public String getNameN() {
        return nameN;
    }

    public ChileItem getChileItem() {
        return chileItem;
    }
}

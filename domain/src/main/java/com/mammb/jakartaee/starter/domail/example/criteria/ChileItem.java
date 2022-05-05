package com.mammb.jakartaee.starter.domail.example.criteria;

import com.mammb.jakartaee.starter.lib.entity.BasicEntity;
import jakarta.persistence.Entity;
import java.time.LocalDate;

@Entity
public class ChileItem extends BasicEntity<ChileItem> {

    private String name;

    private LocalDate date;

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }
}

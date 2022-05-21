package com.mammb.code.jpa.modelgen.fluent;

import com.mammb.code.jpa.modelgen.fluent.data.RootEntity_Root_;
import com.mammb.code.jpa.modelgen.fluent.data.RootImpl;
import org.junit.jupiter.api.Test;

class JpaMetaModelEnhanceProcessorTest {

    @Test
    void test() {
        var root = new RootEntity_Root_(new RootImpl<>());
        root.getName();
        root.joinChildrenList();
        root.getChildrenList();
        root.joinChildrenSet();
        root.getChildrenSet();
        root.joinChildrenMap();
        root.getChildrenMap();
    }

}

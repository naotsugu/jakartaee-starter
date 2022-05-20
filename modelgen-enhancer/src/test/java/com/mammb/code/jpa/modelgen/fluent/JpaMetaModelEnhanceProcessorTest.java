package com.mammb.code.jpa.modelgen.fluent;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JpaMetaModelEnhanceProcessorTest {

    @Test
    void test() {
        new RootEntity_Root_(new RootImpl<>()).joinChildren();
    }

}

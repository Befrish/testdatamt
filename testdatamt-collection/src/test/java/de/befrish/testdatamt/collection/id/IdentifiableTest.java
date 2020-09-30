/*
 * Created: 23.07.2013
 * Copyright (c) 2005-2013 saxess ag. All rights reserved.
 */
package de.befrish.testdatamt.collection.id;

import de.befrish.testdatamt.collection.id.util.DummyIdentifiable;
import de.befrish.testdatamt.collection.id.util.TestUtils;
import org.junit.Test;

/**
 * @author Benno Müller
 */
public class IdentifiableTest {

    private static final AbstractIdentifiable<String> IDENT_1_1 = new DummyIdentifiable("1");
    private static final AbstractIdentifiable<String> IDENT_1_2 = new DummyIdentifiable("1");
    private static final AbstractIdentifiable<String> IDENT_2 = new DummyIdentifiable("2");

    @Test
    public void testeEquals() {
        TestUtils.assertEquals(IDENT_1_1, IDENT_1_2, Integer.valueOf(0), IDENT_2);
    }

    @Test
    public void testeHashCode() {
        TestUtils.assertHashCode(IDENT_1_1, IDENT_1_2, IDENT_2);
    }

}

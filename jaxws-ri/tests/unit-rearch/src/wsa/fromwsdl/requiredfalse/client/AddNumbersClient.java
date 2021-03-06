/*
 * Copyright (c) 2013, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package wsa.fromwsdl.requiredfalse.client;

import junit.framework.TestCase;

/**
 * @author Arun Gupta
 */
public class AddNumbersClient extends TestCase {
    public AddNumbersClient(String name) {
        super(name);
    }

    private AddNumbersPortType createStub() throws Exception {
        return new AddNumbersService().getAddNumbersPort();
    }

    public void testDefaultActions() throws Exception {
        int result = createStub().addNumbers(10, 10);
        assertTrue(result == 20);
    }

    public void testActionWithExplicitNames() throws Exception {
        int result = createStub().addNumbers2(10, 10);
        assertEquals(20, result);
    }

    public void testActionWithInputNameOnly() throws Exception {
        int result = createStub().addNumbers3(10, 10);
        assertEquals(20, result);
    }

    public void testActionWithOutputNameOnly() throws Exception {
        int result = createStub().addNumbers4(10, 10);
        assertEquals(20, result);
    }

    public void testExplicitActionsBoth() throws Exception {
        int result = createStub().addNumbers5(10, 10);
        assertEquals(20, result);
    }

    public void testExplicitActionsInputOnly() throws Exception {
        int result = createStub().addNumbers6(10, 10);
        assertEquals(20, result);
    }

    public void testExplicitActionsOutputOnly() throws Exception {
        int result = createStub().addNumbers7(10, 10);
        assertEquals(20, result);
    }

    public void testEmptyActions() throws Exception {
        int result = createStub().addNumbers8(10, 10);
        assertEquals(20, result);
    }
}

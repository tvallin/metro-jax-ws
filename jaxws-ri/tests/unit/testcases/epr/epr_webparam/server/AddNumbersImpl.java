/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package epr.epr_webparam.server;

import jakarta.jws.WebService;
import jakarta.annotation.Resource;
import jakarta.xml.ws.WebServiceContext;
import jakarta.xml.ws.wsaddressing.W3CEndpointReference;

@WebService(name="AddNumbers",
    portName="AddNumbersPort",
    targetNamespace="http://foobar.org/",
    serviceName="AddNumbersService")
public class AddNumbersImpl {
@Resource
WebServiceContext wsContext;
    public int addNumbers(int number1, int number2) {
        return number1 + number2;
    }

    public W3CEndpointReference getW3CEPR() {
        return (W3CEndpointReference) wsContext.getEndpointReference();
    }

}

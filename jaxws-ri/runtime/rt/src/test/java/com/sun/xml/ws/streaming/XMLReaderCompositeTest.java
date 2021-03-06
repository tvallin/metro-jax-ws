/*
 * Copyright (c) 2013, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.streaming;

//import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import junit.framework.TestCase;

//import org.junit.Test;

import com.sun.xml.ws.encoding.TagInfoset;
import com.sun.xml.ws.util.xml.XMLReaderComposite;
import com.sun.xml.ws.util.xml.XMLReaderComposite.ElemInfo;

public class XMLReaderCompositeTest extends TestCase {
    static XMLInputFactory xifac;
//    @Test
    public void testComposite() throws Exception {
        XMLStreamReader r = r("<root xmlns='ns0' xmlns:p1='ns1' att1='11' xmlns:p2='ns2' xmlns:p3='ns3'/>");
        XMLStreamReader x = r("<p1:x xmlns:p2='ns2' xmlns:p1='ns1' att2='22' p2:att2='11'/>");
        XMLStreamReader a = r("<a xmlns='ns0' att1='11'/>");
        XMLStreamReader b = r("<p1:b xmlns='ns0' att1='11' xmlns:p3='ns3' xmlns:p1='ns1'/>");
        TagInfoset rTag = new TagInfoset(r);
        ElemInfo rElem =  new ElemInfo(rTag, null);
        TagInfoset xTag = new TagInfoset(x);
        ElemInfo xElem =  new ElemInfo(xTag, rElem);
        XMLStreamReader[] kids = {a,b};
        XMLReaderComposite xrc = new XMLReaderComposite(xElem,kids);
        assertTrue(xrc.isStartElement());
        assertEquals(new QName("ns1","x"),xrc.getName());
        assertEquals(2,xrc.getAttributeCount());
        assertEquals(2,xrc.getNamespaceCount());
        assertEquals("ns0", xrc.getNamespaceURI(""));
        assertEquals("ns3", xrc.getNamespaceURI("p3"));
        
        xrc.next();
        assertTrue(xrc.isStartElement());
        assertEquals(new QName("ns0","a"),xrc.getName());
        assertEquals(1,xrc.getAttributeCount());
        xrc.next();
        assertTrue(xrc.isEndElement());
        
        xrc.next();
        assertTrue(xrc.isStartElement());
        assertEquals(new QName("ns1","b"),xrc.getName());
        assertEquals(1,xrc.getAttributeCount());
        xrc.next();
        assertTrue(xrc.isEndElement());

        xrc.next();
        assertTrue(xrc.isEndElement());
    }

    public void testInvalidXML() throws Exception {
        XMLInputFactory fac = XMLInputFactory.newFactory();
        ByteArrayInputStream in = new ByteArrayInputStream("test".getBytes());
        XMLStreamReader r = fac.createXMLStreamReader(in);
        try {
            XMLStreamReaderUtil.readRest(r);
            fail("Exception should be thrown");
        } catch (Exception e) {
            assertEquals("XML reader error: Unexpected character 't' (code 116) in prolog; expected '<'\n" + 
                    " at [row,col {unknown-source}]: [1,1]", e.getMessage());
        }

    }

    private XMLStreamReader r(String msg) throws XMLStreamException {
//      if (xifac == null) xifac = XMLInputFactory.newFactory("com.ctc.wstx.stax.WstxInputFactory", getClass().getClassLoader());
        if (xifac == null) xifac = XMLInputFactory.newFactory();
        ByteArrayInputStream in = new ByteArrayInputStream(msg.getBytes());
        XMLStreamReader r = xifac.createXMLStreamReader(in);
        if (r.getEventType() == XMLStreamReader.START_DOCUMENT) r.next();
        return r;
    }
}

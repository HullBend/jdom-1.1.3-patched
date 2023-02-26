/*-- 

 $Id: JAXPDOMAdapter.java,v 1.13 2007/11/10 05:28:59 jhunter Exp $

 Copyright (C) 2000-2007 Jason Hunter & Brett McLaughlin.
 All rights reserved.
 
 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions
 are met:
 
 1. Redistributions of source code must retain the above copyright
    notice, this list of conditions, and the following disclaimer.
 
 2. Redistributions in binary form must reproduce the above copyright
    notice, this list of conditions, and the disclaimer that follows 
    these conditions in the documentation and/or other materials 
    provided with the distribution.

 3. The name "JDOM" must not be used to endorse or promote products
    derived from this software without prior written permission.  For
    written permission, please contact <request_AT_jdom_DOT_org>.
 
 4. Products derived from this software may not be called "JDOM", nor
    may "JDOM" appear in their name, without prior written permission
    from the JDOM Project Management <request_AT_jdom_DOT_org>.
 
 In addition, we request (but do not require) that you include in the 
 end-user documentation provided with the redistribution and/or in the 
 software itself an acknowledgement equivalent to the following:
     "This product includes software developed by the
      JDOM Project (http://www.jdom.org/)."
 Alternatively, the acknowledgment may be graphical using the logos 
 available at http://www.jdom.org/images/logos.

 THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 DISCLAIMED.  IN NO EVENT SHALL THE JDOM AUTHORS OR THE PROJECT
 CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 SUCH DAMAGE.

 This software consists of voluntary contributions made by many 
 individuals on behalf of the JDOM Project and was originally 
 created by Jason Hunter <jhunter_AT_jdom_DOT_org> and
 Brett McLaughlin <brett_AT_jdom_DOT_org>.  For more information
 on the JDOM Project, please see <http://www.jdom.org/>.
 
 */

package org.jdom.adapters;

import java.io.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.jdom.*;
import org.jdom.input.*;
import org.w3c.dom.Document;

/**
 * An adapter for any parser supporting the Sun JAXP APIs.
 * 
 * @version $Revision: 1.13 $, $Date: 2007/11/10 05:28:59 $
 * @author  Jason Hunter
 */
public class JAXPDOMAdapter extends AbstractDOMAdapter {

    /**
     * This creates a new <code>{@link Document}</code> from an
     * existing <code>InputStream</code> by letting a JAXP
     * parser handle parsing using the supplied stream.
     *
     * @param in <code>InputStream</code> to parse.
     * @param validate <code>boolean</code> to indicate if validation 
     *        should occur.
     * @return <code>Document</code> - instance ready for use.
     * @throws IOException when I/O error occurs.
     * @throws JDOMException when errors occur in parsing.
      */
    public Document getDocument(InputStream in, boolean validate)
        throws JDOMException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(validate);
            factory.setNamespaceAware(true);

            DocumentBuilder jaxpParser = factory.newDocumentBuilder();
            jaxpParser.setErrorHandler(new BuilderErrorHandler());

            return jaxpParser.parse(in);
        } catch (Exception e) {
            throw new JDOMException("Reflection failed while parsing a document with JAXP", e); 
        }

        // Allow all exceptions to pass through
    }

    /**
     * This creates an empty <code>Document</code> object based
     * on a specific parser implementation.
     *
     * @return <code>Document</code> - created DOM Document.
     * @throws JDOMException when errors occur in parsing.
      */
    public Document createDocument() 
        throws JDOMException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder newDocBuilder = factory.newDocumentBuilder();
            return newDocBuilder.newDocument();
        } catch (Exception e) {
            throw new JDOMException("Reflection failed while creating new JAXP document", e); 
        }
    }
}

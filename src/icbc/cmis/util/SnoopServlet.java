package icbc.cmis.util;
/*
 * @(#)SnoopServlet.java	1.00 99/03/15
 *
 * Copyright (c) 1999 International Business Machines. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of IBM.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered
 * into with IBM.
 *
 * IBM MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE
 * SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE, OR NON-INFRINGEMENT. IBM SHALL NOT BE LIABLE FOR ANY DAMAGES
 * SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING
 * THIS SOFTWARE OR ITS DERIVATIVES.
 *
 * CopyrightVersion 1.0
 *
 *
 */

import java.io.*;
import java.util.*;
import java.security.cert.*;
import javax.servlet.*;
import javax.servlet.http.*;


/**
 * Snoop Servlet returns information about the request. This servlet is
 * useful for checking the request parameters from a particular client.
 * SnoopServlet also returns information of existing sessions, application
 * attributes, and request attributes.
 *
 * @version 	1.0
 */
public class SnoopServlet extends HttpServlet
{
    public void doPost (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
      doGet(req,res);
    }
    public void doGet (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        PrintWriter out;

        res.setContentType("text/html");
        out = res.getWriter ();

        out.println("<HTML><HEAD><TITLE>Snoop Servlet</TITLE></HEAD><BODY BGCOLOR=\"#FFFFEE\">");
        out.println("<h1>Snoop Servlet - Request/Client Information</h1>");
        out.println("<h2>Requested URL:</h2>");
        out.println("<TABLE Border=\"2\" WIDTH=\"65%\" BGCOLOR=\"#DDDDFF\">");
        out.println("<tr><td>" + HttpUtils.getRequestURL(req).toString() + "</td></tr></table><BR><BR>");

        out.println("<h2>Servlet Name:</h2>");
        out.println("<TABLE Border=\"2\" WIDTH=\"65%\" BGCOLOR=\"#DDDDFF\">");
        out.println("<tr><td>" + getServletConfig().getServletName() + "</td></tr></table><BR><BR>");

        Enumeration enum = getServletConfig().getInitParameterNames();
        if ( enum != null )
        {
            boolean first = true;
            while ( enum.hasMoreElements() )
            {
                if ( first )
                {
                    out.println("<h2>Servlet Initialization Parameters</h2>");
                    out.println("<TABLE Border=\"2\" WIDTH=\"65%\" BGCOLOR=\"#DDDDFF\">");
                    first = false;
                }
                String param = (String) enum.nextElement();
                out.println("<tr><td>" + param + "</td><td>" + getInitParameter(param) + "</td></tr>");
            }
            out.println("</table><BR><BR>");
        }

        enum = getServletConfig().getServletContext().getInitParameterNames();
        if ( enum != null )
        {
            boolean first = true;
            while ( enum.hasMoreElements() )
            {
                if ( first )
                {
                    out.println("<h2>Servlet Context Initialization Parameters</h2>");
                    out.println("<TABLE Border=\"2\" WIDTH=\"65%\" BGCOLOR=\"#DDDDFF\">");
                    first = false;
                }
                String param = (String) enum.nextElement();
                out.println("<tr><td>" + param + "</td><td>" + getServletConfig().getServletContext().getInitParameter(param) + "</td></tr>");
            }
            out.println("</table><BR><BR>");
        }

        out.println("<h2>Request Information:</h2>");
        out.println("<TABLE Border=\"2\" WIDTH=\"65%\" BGCOLOR=\"#DDDDFF\">");
        print(out, "Request method", req.getMethod());
        print(out, "Request URI", req.getRequestURI());
        print(out, "Request protocol", req.getProtocol());
        print(out, "Servlet path", req.getServletPath());
        print(out, "Path info", req.getPathInfo());
        print(out, "Path translated", req.getPathTranslated());
        print(out, "Character encoding", req.getCharacterEncoding());
        print(out, "Query string", req.getQueryString());
        print(out, "Content length", req.getContentLength());
        print(out, "Content type", req.getContentType());
        print(out, "Server name", req.getServerName());
        print(out, "Server port", req.getServerPort());
        print(out, "Remote user", req.getRemoteUser());
        print(out, "Remote address", req.getRemoteAddr());
        print(out, "Remote host", req.getRemoteHost());
        print(out, "Authorization scheme", req.getAuthType());
        if (req.getLocale() != null)
        {
            print(out, "Preferred Client Locale", req.getLocale().toString());
        }
        else
        {
            print(out, "Preferred Client Locale", "none");
        }
        Enumeration ee = req.getLocales();
        while (ee.hasMoreElements())
        {
            Locale cLocale = (Locale)ee.nextElement();
            if (cLocale != null)
            {
                print(out, "All Client Locales", cLocale.toString());
            }
            else
            {
                print(out, "All Client Locales", "none");
            }
        }
        print(out, "Context Path", req.getContextPath());
        if (req.getUserPrincipal() != null)
        {
            print(out, "User Principal", req.getUserPrincipal().toString());
        }
        else
        {
            print(out, "User Principal", "none");
        }
        out.println("</table><BR><BR>");

        Enumeration e = req.getHeaderNames();
        if ( e.hasMoreElements() )
        {
            out.println("<h2>Request headers:</h2>");
            out.println("<TABLE Border=\"2\" WIDTH=\"65%\" BGCOLOR=\"#DDDDFF\">");
            while ( e.hasMoreElements() )
            {
                String name = (String)e.nextElement();
                out.println("<tr><td>" + name + "</td><td>" + req.getHeader(name) + "</td></tr>");
            }
            out.println("</table><BR><BR>");
        }

        e = req.getParameterNames();
        if ( e.hasMoreElements() )
        {
            out.println("<h2>Servlet parameters (Single Value style):</h2>");
            out.println("<TABLE Border=\"2\" WIDTH=\"65%\" BGCOLOR=\"#DDDDFF\">");
            while ( e.hasMoreElements() )
            {
                String name = (String)e.nextElement();
                out.println("<tr><td>" + name + "</td><td>" + req.getParameter(name) + "</td></tr>");
            }
            out.println("</table><BR><BR>");
        }

        e = req.getParameterNames();
        if ( e.hasMoreElements() )
        {
            out.println("<h2>Servlet parameters (Multiple Value style):</h2>");
            out.println("<TABLE Border=\"2\" WIDTH=\"65%\" BGCOLOR=\"#DDDDFF\">");
            while ( e.hasMoreElements() )
            {
                String name = (String)e.nextElement();
                String vals[] = (String []) req.getParameterValues(name);
                if ( vals != null )
                {
                    out.print("<tr><td>" + name + "</td><td>");
                    out.print(vals[0]);
                    for ( int i = 1; i<vals.length; i++ )
                        out.print(", " + vals[i]);
                    out.println("</td></tr>");
                }
            }
            out.println("</table><BR><BR>");
        }

        String  cipherSuite = (String)req.getAttribute ("javax.net.ssl.cipher_suite");
        if ( cipherSuite != null )
        {
            X509Certificate certChain [] = (X509Certificate [])req.getAttribute ("javax.net.ssl.peer_certificates");

            out.println ("<h2>HTTPS Information:</h2>");
            out.println("<TABLE Border=\"2\" WIDTH=\"65%\" BGCOLOR=\"#DDDDFF\">");
            out.println ("<tr><td>Cipher Suite</td><td>" + cipherSuite + "</td></tr>");

            if ( certChain != null )
            {
                for ( int i = 0; i < certChain.length; i++ )
                {
                    out.println ("client cert chain [" + i + "] = " + certChain [i].toString ());
                }
            }
            out.println("</table><BR><BR>");
        }

        Cookie[] cookies = req.getCookies();
        if ( cookies != null && cookies.length > 0 )
        {
            out.println("<H2>Client cookies</H2>");
            out.println("<TABLE Border=\"2\" WIDTH=\"65%\" BGCOLOR=\"#DDDDFF\">");
            for ( int i=0; i<cookies.length; i++ )
            {
                out.println("<tr><td>" + cookies[i].getName() + "</td><td>" + cookies[i].getValue() + "</td></tr>");
            }
            out.println("</table><BR><BR>");
        }

        e = req.getAttributeNames();
        if ( e.hasMoreElements() )
        {
            out.println("<h2>Request attributes:</h2>");
            out.println("<TABLE Border=\"2\" WIDTH=\"65%\" BGCOLOR=\"#DDDDFF\">");
            while ( e.hasMoreElements() )
            {
                String name = (String)e.nextElement();
                out.println("<tr><td>" + name + "</td><td>" + req.getAttribute(name) + "</td></tr>");
            }
            out.println("</table><BR><BR>");
        }

        e = getServletContext().getAttributeNames();
        if ( e.hasMoreElements() )
        {
            out.println("<h2>ServletContext attributes:</h2>");
            out.println("<TABLE Border=\"2\" WIDTH=\"65%\" BGCOLOR=\"#DDDDFF\">");
            while ( e.hasMoreElements() )
            {
                String name = (String)e.nextElement();
                out.println("<tr><td>" + name + "</td><td>" + getServletContext().getAttribute(name) + "</td></tr>");
            }
            out.println("</table><BR><BR>");
        }

        HttpSession session = req.getSession(false);
        if ( session != null )
        {
            out.println("<h2>Session information:</h2>");
            out.println("<TABLE Border=\"2\" WIDTH=\"65%\" BGCOLOR=\"#DDDDFF\">");
            print(out, "Session ID", session.getId());
            print(out, "Last accessed time", new Date(session.getLastAccessedTime()).toString());
            print(out, "Creation time", new Date(session.getCreationTime()).toString());
            String mechanism = "unknown";
            if ( req.isRequestedSessionIdFromCookie() )
            {
                mechanism = "cookie";
            }
            else if ( req.isRequestedSessionIdFromURL() )
            {
                mechanism = "url-encoding";
            }
            print(out, "Session-tracking mechanism", mechanism);
            out.println("</table><BR><BR>");

            String[] vals = session.getValueNames();
            if ( vals != null )
            {
                out.println("<h2>Session values</h2>");
                out.println("<TABLE Border=\"2\" WIDTH=\"65%\" BGCOLOR=\"#DDDDFF\">");
                for ( int i=0; i<vals.length; i++ )
                {
                    String name = vals[i];
                    out.println("<tr><td>" + name + "</td><td>" + session.getValue(name) + "</td></tr>");
                }
                out.println("</table><BR><BR>");
            }
        }

        out.println("</body></html>");
    }

    private void print (PrintWriter out, String name, String value)
    {
        out.println("<tr><td>" + name + "</td><td>" + (value == null ? "&lt;none&gt;" : value) + "</td></tr>");
    }

    private void print (PrintWriter out, String name, int value)
    {
        out.print("<tr><td>" + name + "</td><td>");
        if ( value == -1 )
        {
            out.print("&lt;none&gt;");
        }
        else
        {
            out.print(value);
        }
        out.println("</td></tr>");
    }
}

package by.chyrkun.training.controller;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;

public class RequestContent {
    private HashMap<String, Object> requestAttributes;
    private HashMap<String, String[]> requestParameters;
    private HashMap<String, Object> sessionAttributes;
//    private HashMap<String, String> headers;

    public RequestContent() {
        requestParameters = new HashMap<>();
        requestAttributes = new HashMap<>();
        sessionAttributes = new HashMap<>();
    }

    public void extractValues(HttpServletRequest req) {
        requestParameters.putAll(req.getParameterMap());
        Enumeration<?> reqAttributeNames = req.getAttributeNames();
        while (reqAttributeNames.hasMoreElements()) {
            String name = (String) reqAttributeNames.nextElement();
            Object value = req.getAttribute(name);
            requestAttributes.put(name, value);
        }
        Enumeration<?> sessionAttributeNames = req.getSession().getAttributeNames();
        while (sessionAttributeNames.hasMoreElements()) {
            String name = (String) sessionAttributeNames.nextElement();
            Object value = req.getSession().getAttribute(name);
            sessionAttributes.put(name, value);
        }
    /*    Enumeration<String> headerNames = req.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            String value = req.getHeader(name);
            headers.put(name, value);
        }*/
    }

    public void insertRequestAttributes(HttpServletRequest req) {
        requestAttributes.forEach((key, value) -> req.setAttribute(key, value));
    }

    public void insertSessionAttributes(HttpServletRequest req) {
        sessionAttributes.forEach((key, value) -> req.getSession().setAttribute(key, value));
    }

    public HashMap<String, String[]> getRequestParameters(){
        return requestParameters;
    }

    public HashMap<String, Object> getRequestAttributes() {
        return requestAttributes;
    }

    public HashMap<String, Object> getSessionAttributes(){
        return sessionAttributes;
    }

    public void setRequestAttribute(String name, Object value) {
        requestAttributes.put(name, value);
    }

    public void setSessionAttribute(String name, Object value){
        sessionAttributes.put(name, value);
    }

    public void setRequestParameter(String name, String[] value) {
        requestParameters.put(name, value);
    }
}

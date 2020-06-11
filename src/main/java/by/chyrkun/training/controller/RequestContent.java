package by.chyrkun.training.controller;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;

public class RequestContent {
    private HashMap<String, Object> requestAttributes;
    private HashMap<String, String[]> requestParameters;
    private HashMap<String, Object> sessionAttributes;

    public void extractValues(HttpServletRequest req){
        requestParameters = new HashMap<>(req.getParameterMap());
        requestAttributes = new HashMap<>();
        sessionAttributes = new HashMap<>();
        Enumeration<?> reqAttributeNames = req.getAttributeNames();
        while (reqAttributeNames.hasMoreElements() == true) {
            String name = (String) reqAttributeNames.nextElement();
            Object value = req.getAttribute(name);
            requestAttributes.put(name, value);
        }
        Enumeration<?> sessionAttributeNames = req.getSession().getAttributeNames();
        while (sessionAttributeNames.hasMoreElements() == true) {
            String name = (String) sessionAttributeNames.nextElement();
            Object value = req.getSession().getAttribute(name);
            sessionAttributes.put(name, value);
        }
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

    public void setRequestAttribute(String name, Object value){
        requestAttributes.put(name, value);
    }

    public void setSessionAttribute(String name, Object value){
        sessionAttributes.put(name, value);
    }
}
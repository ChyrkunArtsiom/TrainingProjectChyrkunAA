package by.chyrkun.training.controller;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;

/**
 * This class is a storage for all attributes and parameters of HttpServletRequest object.
 */
public class RequestContent {
    /** Attributes of request. */
    private HashMap<String, Object> requestAttributes;

    /** Parameters of request. */
    private HashMap<String, String[]> requestParameters;

    /** Attributes of session. */
    private HashMap<String, Object> sessionAttributes;

    /**
     * Instantiates a new Request content.
     */
    public RequestContent() {
        requestParameters = new HashMap<>();
        requestAttributes = new HashMap<>();
        sessionAttributes = new HashMap<>();
    }

    /**
     * Extracts values from request.
     *
     * @param req the HttpServletRequest object
     */
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
    }

    /**
     * Inserts request attributes into HttpServletRequest object.
     *
     * @param req the HttpServletRequest object
     */
    public void insertRequestAttributes(HttpServletRequest req) {
        requestAttributes.forEach((key, value) -> req.setAttribute(key, value));
    }

    /**
     * Inserts session attributes into HttpServletRequest object.
     *
     * @param req the HttpServletRequest object
     */
    public void insertSessionAttributes(HttpServletRequest req) {
        sessionAttributes.forEach((key, value) -> req.getSession().setAttribute(key, value));
    }

    /**
     * Gets request parameters.
     *
     * @return the hash map of request parameters
     */
    public HashMap<String, String[]> getRequestParameters(){
        return requestParameters;
    }

    /**
     * Gets request attributes.
     *
     * @return the hash map of request attributes
     */
    public HashMap<String, Object> getRequestAttributes() {
        return requestAttributes;
    }

    /**
     * Gets session attributes.
     *
     * @return the hash map of session attributes
     */
    public HashMap<String, Object> getSessionAttributes(){
        return sessionAttributes;
    }

    /**
     * Sets request attribute.
     *
     * @param name  the name
     * @param value the value
     */
    public void setRequestAttribute(String name, Object value) {
        requestAttributes.put(name, value);
    }

    /**
     * Sets session attribute.
     *
     * @param name  the name
     * @param value the value
     */
    public void setSessionAttribute(String name, Object value){
        sessionAttributes.put(name, value);
    }

    /**
     * Deletes session attribute.
     *
     * @param name the name
     * @return the value
     */
    public Object deleteSessionAttribute(String name) {
        return sessionAttributes.remove(name);
    }

    /**
     * Sets request parameter.
     *
     * @param name  the name
     * @param value the value
     */
    public void setRequestParameter(String name, String[] value) {
        requestParameters.put(name, value);
    }
}

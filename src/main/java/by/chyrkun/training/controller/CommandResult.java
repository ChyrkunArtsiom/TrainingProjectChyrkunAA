package by.chyrkun.training.controller;

/**
 * The class for result of execution of {@link by.chyrkun.training.service.command.Command} objects.
 */
public class CommandResult {
    /**
     * Enumeration that shows which type of action will be done with request, response and session.
     */
    public enum ResponseType {
        /** Request will be forwarded. */
        FORWARD,

        /** Response will be redirected. */
        REDIRECT,

        /** Session will be invalidated. */
        SESSION_INVALIDATE
    }
    private ResponseType responseType;

    /** The string for page that request/response will be forwarded/redirected to. */
    private String page;

    /**
     * Instantiates a new Command result with ResponseType.FORWARD.
     */
    public CommandResult(){
        responseType = ResponseType.FORWARD;
    }

    /**
     * Instantiates a new Command result with responsetype and page.
     *
     * @param responseType the {@link ResponseType} object
     * @param page         the page
     */
    public CommandResult(ResponseType responseType, String page){
        this.responseType = responseType;
        this.page = page;
    }

    /**
     * Gets response type.
     *
     * @return the {@link ResponseType} object
     */
    public ResponseType getResponseType() {
        return responseType;
    }

    /**
     * Gets page string.
     *
     * @return the string
     */
    public String getPage(){
        return page;
    }

    /**
     * Sets response type.
     *
     * @param responseType the {@link ResponseType} object
     */
    public void setResponseType(ResponseType responseType) {
        if (responseType == null) {
            this.responseType = ResponseType.FORWARD;
        }
        this.responseType = responseType;
    }

    /**
     * Sets page.
     *
     * @param page the page string
     */
    public void setPage(String page){
        this.page = page;
    }
}

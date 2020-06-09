package by.chyrkun.training.controller;

public class CommandResult {
    public enum ResponseType {
        FORWARD,
        REDIRECT,
        SESSION_INVALIDATE
    }
    private ResponseType responseType;
    private String page;

    public CommandResult(){
        responseType = ResponseType.FORWARD;
    }

    public CommandResult(ResponseType responseType, String page){
        this.responseType = responseType;
        this.page = page;
    }

    public ResponseType getResponseType() {
        return responseType;
    }

    public String getPage(){
        return page;
    }

    public void setResponseType(ResponseType responseType) {
        if (responseType == null){
            this.responseType = ResponseType.FORWARD;
        }
        this.responseType = responseType;
    }

    public void setPage(String page){
        this.page = page;
    }
}

package com.company.productname.dto;

/**
 * Created by darrell-shofstall on 10/2/17.
 */
public class ResourceNotFoundExceptionDTO extends RuntimeException {

    private String resourceId;

    public ResourceNotFoundExceptionDTO(String resourceId, String message) {
        super(message);
        this.resourceId = resourceId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }
}

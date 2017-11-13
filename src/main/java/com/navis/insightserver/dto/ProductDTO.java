package com.navis.insightserver.dto;

import java.util.LinkedHashMap;

/**
 * Created by darrell-shofstall on 8/11/17.
 */
public class ProductDTO extends BaseDTO{

    private static final long serialVersionUID = 1L;

private String productName;
private String productUrl;

    public String getProductName() {
        return productName;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public ProductDTO() {
    }

    public ProductDTO(Object product) {
        LinkedHashMap entry = (LinkedHashMap) product;
        setProductName((String) entry.get("ProductName"));
        setProductUrl((String) entry.get("ProductUrl"));
    }
}

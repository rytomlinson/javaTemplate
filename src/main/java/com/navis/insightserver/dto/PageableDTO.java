package com.navis.insightserver.dto;

import org.springframework.data.domain.Page;

/**
 * Created by darrell-shofstall on 8/11/17.
 */
public class PageableDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    private long totalElements;
    private int totalPages;
    private boolean isFirstPage;
    private boolean isLastPage;
    private boolean hasNextPage;
    private boolean hasPreviousPage;
    private int numberOfElements;
    private int pageSize;
    private int currentPageNumber;

    public long getTotalElements() {
        return totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public boolean isFirstPage() {
        return isFirstPage;
    }

    public boolean isLastPage() {
        return isLastPage;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public boolean isHasPreviousPage() {
        return hasPreviousPage;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getCurrentPageNumber() {
        return currentPageNumber;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public void setFirstPage(boolean firstPage) {
        isFirstPage = firstPage;
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public void setHasPreviousPage(boolean hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setCurrentPageNumber(int currentPageNumber) {
        this.currentPageNumber = currentPageNumber + 1;
    }

    public PageableDTO() {
    }

    public PageableDTO(Page page) {
        this.setCurrentPageNumber(page.getNumber());
        this.setTotalElements(page.getTotalElements());
        this.setTotalPages(page.getTotalPages());
        this.setHasNextPage(page.hasNext());
        this.setHasPreviousPage(page.hasPrevious());
        this.setFirstPage(page.isFirst());

        this.setLastPage(page.isLast());
        this.setNumberOfElements(page.getNumberOfElements());
        this.setPageSize(page.getSize());
    }
}

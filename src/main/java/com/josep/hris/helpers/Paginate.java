package com.josep.hris.helpers;

public class Paginate {
    private int buttonsToShow = 7;
    private int startPage;
    private int endPage;
    private int currentPage;
    private int totalPage;
    private long totalResult;
    private boolean buttonFirst;
    private boolean buttonPrev;
    private boolean buttonNext;
    private boolean buttonLast;

    public Paginate(int currentPage, int totalPage, long totalResult) {
        this.currentPage = currentPage;
        this.totalPage = totalPage;
        this.totalResult = totalResult;

        int halfPagesToShow = getButtonsToShow() / 2;

        if (getTotalPage() <= getButtonsToShow()) {
            setStartPage(1);
            setEndPage(getTotalPage());

        } else if (currentPage - halfPagesToShow <= 0) {
            setStartPage(1);
            if (getTotalPage() >= getButtonsToShow())
                setEndPage(getButtonsToShow());
            else
                setEndPage(getTotalPage());

        } else if (currentPage + halfPagesToShow == getTotalPage()) {
            setStartPage(currentPage - halfPagesToShow);
            setEndPage(getTotalPage());
        } else if (currentPage + halfPagesToShow > getTotalPage()) {
            setStartPage(getTotalPage() - getButtonsToShow() + 1);
            setEndPage(getTotalPage());

        } else {
            setStartPage(currentPage - halfPagesToShow);
            setEndPage(currentPage + halfPagesToShow);

        }

        if (getCurrentPage() == 1) {
            setButtonFirst(false);
            setButtonPrev(false);
            setButtonNext(true);
            setButtonLast(true);
        } else if (getCurrentPage() == getTotalPage()) {
            setButtonFirst(true);
            setButtonPrev(true);
            setButtonNext(false);
            setButtonLast(false);
        } else {
            setButtonFirst(true);
            setButtonPrev(true);
            setButtonNext(true);
            setButtonLast(true);
        }
    }

    public int getButtonsToShow() {
        return buttonsToShow;
    }

    public void setButtonsToShow(int buttonsToShow) {
        this.buttonsToShow = buttonsToShow;
    }

    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public long getTotalResult() {
        return totalResult;
    }

    public void setTotalResult(long totalResult) {
        this.totalResult = totalResult;
    }

    public boolean isButtonFirst() {
        return buttonFirst;
    }

    public void setButtonFirst(boolean buttonFirst) {
        this.buttonFirst = buttonFirst;
    }

    public boolean isButtonPrev() {
        return buttonPrev;
    }

    public void setButtonPrev(boolean buttonPrev) {
        this.buttonPrev = buttonPrev;
    }

    public boolean isButtonNext() {
        return buttonNext;
    }

    public void setButtonNext(boolean buttonNext) {
        this.buttonNext = buttonNext;
    }

    public boolean isButtonLast() {
        return buttonLast;
    }

    public void setButtonLast(boolean buttonLast) {
        this.buttonLast = buttonLast;
    }
}

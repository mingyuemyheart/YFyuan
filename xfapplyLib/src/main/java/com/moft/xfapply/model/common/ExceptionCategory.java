package com.moft.xfapply.model.common;

import java.io.Serializable;
import java.util.List;

public class ExceptionCategory implements Serializable {
    private String name;
    private List<String> exceptions;

    public ExceptionCategory(String name, List<String> exceptions) {
        this.name = name;
        this.exceptions = exceptions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getExceptions() {
        return exceptions;
    }

    public void setExceptions(List<String> exceptions) {
        this.exceptions = exceptions;
    }
}

package com.moft.xfapply.model.external.dto;

import java.util.Date;
import java.util.List;

public class ExportFileContentDTO extends RestDTO{
    private Date queryDate;
    private List<ExportIncSqlDTO> incSqlDTOs;

    public Date getQueryDate() {
        return queryDate;
    }

    public void setQueryDate(Date queryDate) {
        this.queryDate = queryDate;
    }

    public List<ExportIncSqlDTO> getIncSqlDTOs() {
        return incSqlDTOs;
    }

    public void setIncSqlDTOs(List<ExportIncSqlDTO> incSqlDTOs) {
        this.incSqlDTOs = incSqlDTOs;
    }
}

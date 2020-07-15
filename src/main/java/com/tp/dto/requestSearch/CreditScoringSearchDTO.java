package com.tp.dto.requestSearch;

/**
 * Created by Dinh Nguyen on 06/09/2018.
 */
public class CreditScoringSearchDTO extends RequestSearchDTO {

    private String programCode;
    private String isdn;
    private String creditScoring;

    public CreditScoringSearchDTO(String programCode, String isdn, String creditScoring) {
        this.programCode = programCode;
        this.isdn = isdn;
        this.creditScoring = creditScoring;
    }

    public CreditScoringSearchDTO() {
    }

    public String getProgramCode() {
        return programCode;
    }

    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }

    public String getIsdn() {
        return isdn;
    }

    public void setIsdn(String isdn) {
        this.isdn = isdn;
    }

    public String getCreditScoring() {
        return creditScoring;
    }

    public void setCreditScoring(String creditScoring) {
        this.creditScoring = creditScoring;
    }
}

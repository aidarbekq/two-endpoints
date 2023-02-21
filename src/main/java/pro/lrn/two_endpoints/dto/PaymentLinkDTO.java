package pro.lrn.two_endpoints.dto;


public class PaymentLinkDTO {
    private String standardVersion;
    private String typeOfPaymentLink;
    private InformationAboutServiceProviderDTO informationAboutServiceProviderDTO;
    private String mcc;
    private String currencyCode;
    private String paymentAmount;
    private String providerName;
    private String dataChecksum;

    public String getCurrencyCode() {
        return currencyCode;
    }

    public String getDataChecksum() {
        return dataChecksum;
    }

    public InformationAboutServiceProviderDTO getInformationAboutServiceProviderDTO() {
        return informationAboutServiceProviderDTO;
    }

    public String getMcc() {
        return mcc;
    }

    public String getPaymentAmount() {
        return paymentAmount;
    }

    public String getProviderName() {
        return providerName;
    }

    public String getStandardVersion() {
        return standardVersion;
    }

    public String getTypeOfPaymentLink() {
        return typeOfPaymentLink;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public void setDataChecksum(String dataChecksum) {
        this.dataChecksum = dataChecksum;
    }

    public void setInformationAboutServiceProviderDTO(InformationAboutServiceProviderDTO informationAboutServiceProviderDTO) {
        this.informationAboutServiceProviderDTO = informationAboutServiceProviderDTO;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    public void setPaymentAmount(String paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public void setTypeOfPaymentLink(String typeOfPaymentLink) {
        this.typeOfPaymentLink = typeOfPaymentLink;
    }

    public void setStandardVersion(String standardVersion) {
        this.standardVersion = standardVersion;
    }

    public void setError(String s) {
    }
}

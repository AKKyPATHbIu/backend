package embasa.crypto;

import java.util.Date;

public class CertificateInfo {

    private String issuer;
    private String issuerCN;
    private String serial;
    private String subject;
    private String subjCN;
    private String subjOrg;
    private String subjOrgUnit;
    private String subjTitle;
    private String subjState;
    private String subjLocality;
    private String subjFullName;
    private String subjAddress;
    private String subjPhone;
    private String subjEmail;
    private String subjDNS;
    private String subjEDRPOUCode;
    private String subjDRFOCode;
    private Date pkBeginTime;
    private Date pkEndTime;
    private Date certBeginTime;
    private Date certEndTime;

    /** Конструктор за замовчанням. */
    public CertificateInfo() { }

    public String getIssuer() {
        return issuer;
    }

    public CertificateInfo setIssuer(String issuer) {
        this.issuer = issuer;
        return this;
    }

    public String getIssuerCN() {
        return issuerCN;
    }

    public CertificateInfo setIssuerCN(String issuerCN) {
        this.issuerCN = issuerCN;
        return this;
    }

    public String getSerial() {
        return serial;
    }

    public CertificateInfo setSerial(String serial) {
        this.serial = serial;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public CertificateInfo setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getSubjCN() {
        return subjCN;
    }

    public CertificateInfo setSubjCN(String subjCN) {
        this.subjCN = subjCN;
        return this;
    }

    public String getSubjOrg() {
        return subjOrg;
    }

    public CertificateInfo setSubjOrg(String subjOrg) {
        this.subjOrg = subjOrg;
        return this;
    }

    public String getSubjOrgUnit() {
        return subjOrgUnit;
    }

    public CertificateInfo setSubjOrgUnit(String subjOrgUnit) {
        this.subjOrgUnit = subjOrgUnit;
        return this;
    }

    public String getSubjTitle() {
        return subjTitle;
    }

    public CertificateInfo setSubjTitle(String subjTitle) {
        this.subjTitle = subjTitle;
        return this;
    }

    public String getSubjState() {
        return subjState;
    }

    public CertificateInfo setSubjState(String subjState) {
        this.subjState = subjState;
        return this;
    }

    public String getSubjLocality() {
        return subjLocality;
    }

    public CertificateInfo setSubjLocality(String subjLocality) {
        this.subjLocality = subjLocality;
        return this;
    }

    public String getSubjFullName() {
        return subjFullName;
    }

    public CertificateInfo setSubjFullName(String subjFullName) {
        this.subjFullName = subjFullName;
        return this;
    }

    public String getSubjAddress() {
        return subjAddress;
    }

    public CertificateInfo setSubjAddress(String subjAddress) {
        this.subjAddress = subjAddress;
        return this;
    }

    public String getSubjPhone() {
        return subjPhone;
    }

    public CertificateInfo setSubjPhone(String subjPhone) {
        this.subjPhone = subjPhone;
        return this;
    }

    public String getSubjEmail() {
        return subjEmail;
    }

    public CertificateInfo setSubjEmail(String subjEmail) {
        this.subjEmail = subjEmail;
        return this;
    }

    public String getSubjDNS() {
        return subjDNS;
    }

    public CertificateInfo setSubjDNS(String subjDNS) {
        this.subjDNS = subjDNS;
        return this;
    }

    public String getSubjEDRPOUCode() {
        return subjEDRPOUCode;
    }

    public CertificateInfo setSubjEDRPOUCode(String subjEDRPOUCode) {
        this.subjEDRPOUCode = subjEDRPOUCode;
        return this;
    }

    public String getSubjDRFOCode() {
        return subjDRFOCode;
    }

    public CertificateInfo setSubjDRFOCode(String subjDRFOCode) {
        this.subjDRFOCode = subjDRFOCode;
        return this;
    }

    public Date getPkBeginTime() {
        return pkBeginTime;
    }

    public CertificateInfo setPkBeginTime(Date pkBeginTime) {
        this.pkBeginTime = pkBeginTime;
        return this;
    }

    public Date getPkEndTime() {
        return pkEndTime;
    }

    public CertificateInfo setPkEndTime(Date pkEndTime) {
        this.pkEndTime = pkEndTime;
        return this;
    }

    public Date getCertBeginTime() {
        return certBeginTime;
    }

    public CertificateInfo setCertBeginTime(Date certBeginTime) {
        this.certBeginTime = certBeginTime;
        return this;
    }

    public Date getCertEndTime() {
        return certEndTime;
    }

    public CertificateInfo setCertEndTime(Date certEndTime) {
        this.certEndTime = certEndTime;
        return this;
    }

    public boolean isExpired() {
        Date now = new Date();
        return now.after(pkEndTime) || now.before(pkBeginTime);
    }
}

package beans;

/**
 * @Author: Lulu
 * @Description: TODO
 * @DateTime: 2022/8/21 9:48
 **/
public class ExcelDataBean extends BaseBean{
    private String caseno;
    private boolean run;
    private String desc; // 接口描述
    private String url;
    private String method;
    private String param;
    private boolean contains;
    private int status;
    private String verify;
    private String save;
    private String preParam;
    private int sleep;
    private String contenttype;

    public void setCaseno(String caseno) {this.caseno=caseno;}

    public String getCaseno() {return caseno;}

    public boolean isRun() {
        return run;
    }

    public void setRun(boolean run) {
        this.run = run;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public boolean isContains() {
        return contains;
    }

    public void setContains(boolean contains) {
        this.contains = contains;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getVerify() {
        return verify;
    }

    public void setVerify(String verify) {
        this.verify = verify;
    }

    public String getSave() {
        return save;
    }

    public void setSave(String save) {
        this.save = save;
    }

    public String getPreParam() {
        return preParam;
    }

    public void setPreParam(String preParam) {
        this.preParam = preParam;
    }

    public int getSleep() {
        return sleep;
    }

    public void setSleep(int sleep) {
        this.sleep = sleep;
    }

    public void setContentType(String contenttype) {this.contenttype = contenttype;}

    public String getContentType() {return contenttype;}

    @Override
    public String toString() {
        return String.format("desc:%s,method:%s,url:%s,param:%s", this.desc,
                this.method, this.url, this.param);
    }

}

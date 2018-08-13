package cn.missbe.missbe_www.dto;

import java.io.Serializable;

/**
 * @author lyg
 * @date 2016年7月25日 下午8:29:58
 */
public class JsonBaseResult implements Serializable {
    private Object result;
    private boolean success;

    public JsonBaseResult(Object result, boolean success) {
        super();
        this.result = result;
        this.success = success;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        if (!this.success) {
            return result.toString();
        } else {
            return "正常";
        }
    }

}

package cool.sodo.auth.exception;

import cool.sodo.common.entity.ResultEnum;
import cool.sodo.common.exception.SoDoException;

import java.io.Serializable;

public class WeChatException extends SoDoException {

    private String desc;
    private WeChatError weChatError;

    public WeChatException(String desc, WeChatError weChatError) {
        super(ResultEnum.SERVER_ERROR, desc, weChatError);
        this.desc = desc;
        this.weChatError = weChatError;
    }

    public String getDesc() {
        return desc;
    }

    public WeChatError getWeChatError() {
        return weChatError;
    }

    public static class WeChatError implements Serializable {

        private int errcode;
        private String errmsg;

        public int getErrcode() {
            return errcode;
        }

        public void setErrcode(int errcode) {
            this.errcode = errcode;
        }

        public String getErrmsg() {
            return errmsg;
        }

        public void setErrmsg(String errmsg) {
            this.errmsg = errmsg;
        }

        @Override
        public String toString() {
            return "WeChatError{" +
                    "errcode=" + errcode +
                    ", errmsg='" + errmsg + '\'' +
                    '}';
        }
    }
}

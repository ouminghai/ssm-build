package com.ssm.promotion.core.common;

/**
 * 响应结果生成工具
 */
public class ResultGenerator {
    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";
    private static final String DEFAULT_FAIL_MESSAGE = "FAIL";

    /**
     * 获取成功的结果
     * @return
     */
    public static Result genSuccessResult() {
        Result result = new Result();
        result.setResultCode(Constants.RESULT_CODE_SUCCESS);
        result.setMessage(DEFAULT_SUCCESS_MESSAGE);
        return result;
    }

    /**
     *
     * @param data
     * @return
     */
    public static Result genSuccessResult(Object data) {
        Result result = new Result();
        result.setResultCode(Constants.RESULT_CODE_SUCCESS);
        result.setMessage(DEFAULT_SUCCESS_MESSAGE);
        result.setData(data);
        return result;
    }

    /**
     *
     * @param message
     * @return
     */
    public static Result genFailResult(String message) {
        Result result = new Result();
        result.setResultCode(Constants.RESULT_CODE_SERVER_ERROR);
        if (message == null || message.length() < 1) {
            message = DEFAULT_FAIL_MESSAGE;
        }
        result.setMessage(message);
        return result;
    }

}

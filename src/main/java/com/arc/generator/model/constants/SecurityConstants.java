package com.arc.generator.model.constants;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 叶超
 * @since 2019/5/22 22:33
 */
@Setter
@Getter
public class SecurityConstants {

    public static final String VERIFY_CODE = "/verify/code";

    private static final String VERIFY_IMAGE_PREFIX = "VERIFY_IMAGE";

    public static final String DEFAULT_PARAMETER_NAME_CODE_IMAGE = "imageCode";
    public static final String DEFAULT_PARAMETER_NAME_CODE_SMS = "smsCode";
    public static final String DEFAULT_LOGIN_PROCESSING_URL_FORM = "/authentication/form";
    public static final String DEFAULT_LOGIN_PROCESSING_URL_MOBILE = "/authentication/mobile";

}

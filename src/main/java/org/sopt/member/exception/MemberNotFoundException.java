package org.sopt.member.exception;

import org.sopt.global.exception.BusinessException;
import org.sopt.member.code.MemberErrorCode;

public class MemberNotFoundException extends BusinessException {
    public MemberNotFoundException() {
        super(MemberErrorCode.MEMBER_NOT_FOUND);
    }
}

package org.sopt.domain.member.exception;

import org.sopt.global.exception.BusinessException;
import org.sopt.domain.member.code.MemberErrorCode;

public class MemberNotFoundException extends BusinessException {
    public MemberNotFoundException() {
        super(MemberErrorCode.MEMBER_NOT_FOUND);
    }
}

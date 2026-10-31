package org.sopt.domain.member.exception;

import org.sopt.domain.member.code.MemberErrorCode;
import org.sopt.global.exception.BusinessException;

public class MemberWithdrawnException extends BusinessException {
    public MemberWithdrawnException() {
        super(MemberErrorCode.MEMBER_WITHDRAWN);
    }
}

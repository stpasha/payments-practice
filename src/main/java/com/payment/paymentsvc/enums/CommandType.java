package com.payment.paymentsvc.enums;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum CommandType {
    CREATE,
    REFUND,
    UNKNOWN;

    public static CommandType fromString(String strCommand) {
        try {
            return CommandType.valueOf(strCommand);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return UNKNOWN;
        }

    }
}

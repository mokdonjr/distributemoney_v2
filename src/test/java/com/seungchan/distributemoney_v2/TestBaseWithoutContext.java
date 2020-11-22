package com.seungchan.distributemoney_v2;

import com.seungchan.distributemoney_v2.account.data.enums.BankType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestBaseWithoutContext {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected String defaultRoomId = "XXX_ROOM_ID_123";
    protected String defaultUserId = "XXX_USER_ID_123";
    protected String defaultRoomName = "TEST_ROOM_NAME";
    protected String defaultUserName = "TEST_USER_NAME";
    protected int defaultBankId = BankType.NONGHYUP.getBankId();
    protected String defaultBankAccountNumber = "123456-78-91011";
    protected String defaultToken = "!B)";
}

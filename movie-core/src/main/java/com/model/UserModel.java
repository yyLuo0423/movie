package com.model;

import com.model.generated.Tables;
import com.utils.time.Clock;
import com.model.base.BaseModel;
import com.model.generated.tables.User;
import com.model.generated.tables.records.UserRecord;
import org.springframework.stereotype.Component;

@Component
public class UserModel extends BaseModel {
  private static User table = Tables.USER;

  public UserRecord init(String mobile, String name, String pwd) {
    UserRecord record = create().newRecord(table);
    record.setName(name);
    record.setMobile(mobile);
    record.setPassword(pwd);
    record.setTimeCreated(Clock.now());
    record.setTimeUpdated(Clock.now());
    record.insert();
    return record;
  }

  public UserRecord findByMobileNumber(String mobile) {
    return create().selectFrom(table)
        .where(table.MOBILE.eq(mobile))
        .fetchOne();
  }
}


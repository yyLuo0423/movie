package com.movie.service.vo;

import lombok.Data;
import com.model.generated.tables.records.UserRecord;

@Data
public class UserVO {
  public Long id;
  public String mobile;
  public String name;
  public Long timeCreated;
  public Long timeUpdated;

  public static UserVO from(UserRecord userRecord) {
    if (userRecord == null) {
      return null;
    }
    UserVO userVO = new UserVO();
    userVO.id = userRecord.getId();
    userVO.mobile = userRecord.getMobile();
    userVO.name = userRecord.getName();
    userVO.timeCreated = userRecord.getTimeCreated();
    userVO.timeUpdated = userRecord.getTimeUpdated();
    return userVO;
  }
}

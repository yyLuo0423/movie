package com.service.user;

import com.service.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import com.model.UserModel;
import com.model.generated.tables.records.UserRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {
  @Autowired
  private UserModel userModel;

  public UserVO register(String mobile, String name) {
    return UserVO.from(userModel.init(mobile, name));
  }

  public UserVO findByMobile(String mobile) {
    log.info("find mobile = {}", mobile);
    UserRecord userRecord = userModel.findByMobileNumber(mobile);
    if(userRecord == null){
      log.error("user is null");
    }
    return UserVO.from(userRecord);
  }
}

package com.terminus.service;

import com.terminus.model.Credentials;
import com.terminus.model.Failure;
import com.terminus.model.Result;
import com.terminus.model.User;

public interface ILoginService {

	Result<User, Failure> login(Credentials credentials);
}

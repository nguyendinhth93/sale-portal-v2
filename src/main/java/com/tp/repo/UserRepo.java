package com.tp.repo;

import java.util.List;

import com.tp.model.admin.User;

/**
 * Created by user on 21/08/2017.
 */
public interface UserRepo extends BaseRepository<User,Long>,UserRepoCustom {

}

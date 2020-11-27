package kw.kd.dss.dao.application;


import kw.kd.dss.entity.application.LinkisUser;

/**
 * Created by chaogefeng on 2019/11/29.
 */
public interface LinkisUserMapper {
    LinkisUser getUserByName(String username);

    void registerLinkisUser(LinkisUser userDb);
}

package org.team10424102.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.team10424102.daos.GroupRepo;

@Service
public class GroupServiceImpl implements GroupService {
    @Autowired GroupRepo groupRepo;

    @Override
    public boolean isUserInGroup(int uid, long gid) {
        return groupRepo.isUserInGroup(uid, gid);
    }
}

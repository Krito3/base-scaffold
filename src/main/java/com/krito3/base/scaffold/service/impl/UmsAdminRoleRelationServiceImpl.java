package com.krito3.base.scaffold.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.krito3.base.scaffold.mapper.UmsAdminRoleRelationMapper;
import com.krito3.base.scaffold.module.entity.UmsAdminRoleRelation;
import com.krito3.base.scaffold.service.UmsAdminRoleRelationService;
import org.springframework.stereotype.Service;

/**
 * 管理员角色关系管理Service实现类
 * Created by macro on 2020/8/21.
 */
@Service
public class UmsAdminRoleRelationServiceImpl extends ServiceImpl<UmsAdminRoleRelationMapper, UmsAdminRoleRelation> implements UmsAdminRoleRelationService {
}

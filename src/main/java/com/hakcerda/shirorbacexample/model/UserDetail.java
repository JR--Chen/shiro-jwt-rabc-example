package com.hakcerda.shirorbacexample.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Set;

/**
 * @author JR Chan
 */
@Data
@Accessors(chain = true)
public class UserDetail {
    private String account;
    private String userName;
    private Set<Role> roleSet;
    private Set<Permission> permissionSet;

}

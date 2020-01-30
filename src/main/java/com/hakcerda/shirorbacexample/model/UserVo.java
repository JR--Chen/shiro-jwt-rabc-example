package com.hakcerda.shirorbacexample.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Set;

/**
 * @author JR Chan
 */
@Data
@Accessors(chain = true)
public class UserVo {
    private String userName;
    private String token;
    private Set<Role> roleSet;
    private Set<Permission> permissionSet;
}

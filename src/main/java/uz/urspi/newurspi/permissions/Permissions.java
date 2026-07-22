package uz.urspi.newurspi.permissions;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import uz.urspi.newurspi.roles.Role;
import uz.urspi.newurspi.utils.Action;
import uz.urspi.newurspi.utils.BaseEntity;
import uz.urspi.newurspi.utils.Resource;
import uz.urspi.newurspi.utils.TableName;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = TableName.PERMISSIONS)
public class Permissions extends BaseEntity {
    @Enumerated(EnumType.STRING)
    private Resource resource;

    @Enumerated(EnumType.STRING)
    private Action action;

    @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles = new HashSet<>();
}

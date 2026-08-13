package uz.urspi.newurspi.users;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.urspi.newurspi.roles.RoleResponse;
import uz.urspi.newurspi.utils.Status;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "User response")
public class UserResponse {
    private Long id;
    private String username;
    private String fullName;
    private String phone;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
    private Set<RoleResponse> role;
    private String photoLink;
}

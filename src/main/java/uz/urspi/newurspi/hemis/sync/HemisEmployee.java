package uz.urspi.newurspi.hemis.sync;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLRestriction;
import uz.urspi.newurspi.utils.BaseEntity;
import uz.urspi.newurspi.utils.TableName;

@Entity
@Table(name = TableName.HEMIS_EMPLOYEES)
@SQLRestriction("status <> 'DELETED' ")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class HemisEmployee extends BaseEntity {

    @Column(nullable = false, unique = true)
    private Long externalId;

    @Column(nullable = false)
    private String fullName;

    private String employeeIdNumber;

    private Long departmentId;

    private String departmentName;

    private String positionName;

    private String academicDegreeName;
}

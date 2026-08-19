package uz.urspi.newurspi.employee;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLRestriction;
import uz.urspi.newurspi.center.Center;
import uz.urspi.newurspi.utils.BaseEntity;
import uz.urspi.newurspi.utils.TableName;

@Entity
@Table(name = TableName.EMPLOYEES)
@SQLRestriction("status <> 'DELETED' ")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Employee extends BaseEntity {
    @Column(nullable = false)
    private String fullNameUz;
    private String fullNameRu;
    private String fullNameEn;
    private String phoneNumber;

    @Column(unique = true)
    private String email;

    private String photoLink;
    private String cvLink;

    @Column(nullable = false)
    private String positionTitleUz;
    private String positionTitleRu;
    private String positionTitleEn;

    private Integer sortOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "center_id", nullable = false)
    private Center center;
}

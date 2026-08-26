package uz.urspi.newurspi.facultystaff;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLRestriction;
import uz.urspi.newurspi.faculty.Faculty;
import uz.urspi.newurspi.utils.BaseEntity;
import uz.urspi.newurspi.utils.TableName;

@Entity
@Table(name = TableName.FACULTY_STAFF)
@SQLRestriction("status <> 'DELETED' ")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class FacultyStaff extends BaseEntity {

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
    @JoinColumn(name = "faculty_id", nullable = false)
    private Faculty faculty;
}

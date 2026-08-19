package uz.urspi.newurspi.teacher;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLRestriction;
import uz.urspi.newurspi.academicdegree.AcademicDegree;
import uz.urspi.newurspi.department.Department;
import uz.urspi.newurspi.faculty.Faculty;
import uz.urspi.newurspi.position.Position;
import uz.urspi.newurspi.utils.BaseEntity;
import uz.urspi.newurspi.utils.TableName;

@Entity
@Table(name = TableName.TEACHERS)
@SQLRestriction("status <> 'DELETED' ")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Teacher extends BaseEntity {
    @Column(nullable = false)
    private String fullNameUz;

    private String fullNameRu;
    private String fullNameEn;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false, unique = true)
    private String email;

    private String photoLink;
    private String cvLink;

    @Column(columnDefinition = "integer default 0")
    private Integer sortOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "faculty_id", nullable = false)
    private Faculty faculty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id", nullable = false)
    private Position position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "academic_degree_id", nullable = false)
    private AcademicDegree academicDegree;
}

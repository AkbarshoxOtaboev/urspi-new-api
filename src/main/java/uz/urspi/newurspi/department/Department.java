package uz.urspi.newurspi.department;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLRestriction;
import uz.urspi.newurspi.faculty.Faculty;
import uz.urspi.newurspi.teacher.Teacher;
import uz.urspi.newurspi.utils.BaseEntity;
import uz.urspi.newurspi.utils.TableName;

import java.util.List;

@Entity
@Table(name = TableName.DEPARTMENTS)
@SQLRestriction("status <> 'DELETED' ")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Department extends BaseEntity {
    @Column(name = "name")
    private String nameUz;
    private String nameRu;
    private String nameEn;

    @Column(name = "description")
    private String descriptionUz;
    private String descriptionRu;
    private String descriptionEn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "faculty_id", nullable = false)
    private Faculty faculty;

    @OneToMany(mappedBy = "department")
    private List<Teacher> teachers;
}

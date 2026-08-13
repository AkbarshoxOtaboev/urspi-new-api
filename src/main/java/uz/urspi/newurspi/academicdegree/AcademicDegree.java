package uz.urspi.newurspi.academicdegree;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLRestriction;
import uz.urspi.newurspi.teacher.Teacher;
import uz.urspi.newurspi.utils.BaseEntity;
import uz.urspi.newurspi.utils.TableName;

import java.util.List;

@Entity
@Table(name = TableName.ACADEMIC_DEGREES)
@SQLRestriction("status <> 'DELETED' ")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class AcademicDegree extends BaseEntity {
    @Column(unique = true, nullable = false)
    private String name;
    private String description;

    @OneToMany(mappedBy = "academicDegree")
    private List<Teacher> teachers;
}

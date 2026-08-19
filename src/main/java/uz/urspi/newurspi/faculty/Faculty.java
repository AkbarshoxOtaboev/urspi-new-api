package uz.urspi.newurspi.faculty;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLRestriction;
import uz.urspi.newurspi.department.Department;
import uz.urspi.newurspi.teacher.Teacher;
import uz.urspi.newurspi.utils.BaseEntity;
import uz.urspi.newurspi.utils.TableName;

import java.util.List;

@Entity
@Table(name = TableName.FACULTIES)
@SQLRestriction("status <> 'DELETED' ")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Faculty extends BaseEntity {
    private String code;
    private String nameUz;
    private String nameRu;
    private String nameEn;
    private String descriptionUz;
    private String descriptionRu;
    private String descriptionEn;
    private String logoLink;

    @OneToMany(mappedBy = "faculty")
    private List<Department> departments;

    @OneToMany(mappedBy = "faculty")
    private List<Teacher> teachers;
}

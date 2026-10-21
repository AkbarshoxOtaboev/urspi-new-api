package uz.urspi.newurspi.menu;

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
@Table(name = TableName.MENUS)
@SQLRestriction("status <> 'DELETED' ")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Menu extends BaseEntity {

    @Column(nullable = false)
    private String nameUz;

    private String nameRu;
    private String nameEn;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(columnDefinition = "integer default 0")
    private Integer sortOrder;
}

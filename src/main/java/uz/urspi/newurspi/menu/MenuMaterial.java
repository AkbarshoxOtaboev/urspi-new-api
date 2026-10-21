package uz.urspi.newurspi.menu;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLRestriction;
import uz.urspi.newurspi.utils.BaseEntity;
import uz.urspi.newurspi.utils.TableName;

import java.time.LocalDate;

@Entity
@Table(name = TableName.MENU_MATERIALS)
@SQLRestriction("status <> 'DELETED' ")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class MenuMaterial extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "info_id", nullable = false)
    private MenuInfo info;

    private Integer year;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String titleUz;

    @Column(columnDefinition = "TEXT")
    private String titleRu;

    @Column(columnDefinition = "TEXT")
    private String titleEn;

    private String fileLink;

    private String coverLink;

    private LocalDate publishedAt;
}

package uz.urspi.newurspi.photogallery;

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
@Table(name = TableName.PHOTO_GALLERIES)
@SQLRestriction("status <> 'DELETED' ")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PhotoGallery extends BaseEntity {

    @Column(nullable = false)
    private String titleUz;
    private String titleRu;
    private String titleEn;

    @Column(columnDefinition = "TEXT")
    private String descriptionUz;
    @Column(columnDefinition = "TEXT")
    private String descriptionRu;
    @Column(columnDefinition = "TEXT")
    private String descriptionEn;

    private String imageLink;
}

package uz.urspi.newurspi.greeninstitute;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLRestriction;
import uz.urspi.newurspi.utils.BaseEntity;
import uz.urspi.newurspi.utils.TableName;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = TableName.GREEN_INSTITUTES)
@SQLRestriction("status <> 'DELETED' ")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class GreenInstitute extends BaseEntity {

    @Column(nullable = false)
    private String titleUz;
    private String titleRu;
    private String titleEn;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "green_institute_images", joinColumns = @JoinColumn(name = "green_institute_id"))
    @Column(name = "image_link")
    private List<String> imageLinks = new ArrayList<>();
}

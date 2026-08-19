package uz.urspi.newurspi.leader;

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
@Table(name = TableName.LEADERS)
@SQLRestriction("status <> 'DELETED' ")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Leader extends BaseEntity {

    @Column(nullable = false)
    private String fullNameUz;
    private String fullNameRu;
    private String fullNameEn;

    @Column(nullable = false)
    private String positionTitleUz;
    private String positionTitleRu;
    private String positionTitleEn;

    private String addressUz;
    private String addressRu;
    private String addressEn;

    private String receptionTimeUz;
    private String receptionTimeRu;
    private String receptionTimeEn;

    private String phoneNumber;
    private String email;
    private String photoLink;
    private Integer sortOrder;
}

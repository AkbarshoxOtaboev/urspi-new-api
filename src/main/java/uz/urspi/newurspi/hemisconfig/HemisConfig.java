package uz.urspi.newurspi.hemisconfig;

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
@Table(name = TableName.HEMIS_CONFIGS)
@SQLRestriction("status <> 'DELETED' ")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class HemisConfig extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false, length = 500)
    private String baseUrl;

    @Column(nullable = false, length = 2000)
    private String token;

    @Column(nullable = false, length = 20)
    private String defaultLang;

    @Column(nullable = false)
    private boolean active;
}

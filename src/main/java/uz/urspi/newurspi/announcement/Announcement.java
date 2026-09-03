package uz.urspi.newurspi.announcement;

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

import java.time.LocalDate;

@Entity
@Table(name = TableName.ANNOUNCEMENTS)
@SQLRestriction("status <> 'DELETED' ")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Announcement extends BaseEntity {

    @Column(nullable = false, columnDefinition = "TEXT")
    private String titleUz;

    @Column(columnDefinition = "TEXT")
    private String titleRu;

    @Column(columnDefinition = "TEXT")
    private String titleEn;

    @Column(columnDefinition = "TEXT")
    private String contentUz;

    @Column(columnDefinition = "TEXT")
    private String contentRu;

    @Column(columnDefinition = "TEXT")
    private String contentEn;

    /** Manual publish date used for listing order (e.g. 01-01-2026). */
    private LocalDate publishedAt;

    private String imageLink;
}

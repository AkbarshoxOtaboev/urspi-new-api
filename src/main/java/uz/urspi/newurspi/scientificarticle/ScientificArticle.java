package uz.urspi.newurspi.scientificarticle;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.type.SqlTypes;
import uz.urspi.newurspi.teacher.Teacher;
import uz.urspi.newurspi.utils.BaseEntity;
import uz.urspi.newurspi.utils.ScientificArticleType;
import uz.urspi.newurspi.utils.TableName;

@Entity
@Table(name = TableName.SCIENTIFIC_ARTICLES)
@SQLRestriction("status <> 'DELETED' ")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ScientificArticle extends BaseEntity {

    @Column(nullable = false, columnDefinition = "TEXT")
    private String title;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(nullable = false, length = 30)
    private ScientificArticleType type;

    @Column(nullable = false)
    private Integer publicationYear;

    @Column(nullable = false)
    private String journalName;

    private String articleUrl;

    private String fileLink;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;
}

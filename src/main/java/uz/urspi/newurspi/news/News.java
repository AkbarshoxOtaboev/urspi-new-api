package uz.urspi.newurspi.news;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLRestriction;
import uz.urspi.newurspi.utils.BaseEntity;
import uz.urspi.newurspi.utils.TableName;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = TableName.NEWS)
@SQLRestriction("status <> 'DELETED' ")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class News extends BaseEntity {

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

    private String author;
    private String mainImageLink;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "news_images", joinColumns = @JoinColumn(name = "news_id"))
    @Column(name = "image_link")
    private List<String> imageLinks = new ArrayList<>();
}

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

    @Column(nullable = false)
    private String titleUz;
    private String titleRu;
    private String titleEn;

    @Column(columnDefinition = "TEXT")
    private String contentUz;
    @Column(columnDefinition = "TEXT")
    private String contentRu;
    @Column(columnDefinition = "TEXT")
    private String contentEn;

    private String author;
    private String mainImageLink;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "news_images", joinColumns = @JoinColumn(name = "news_id"))
    @Column(name = "image_link")
    private List<String> imageLinks = new ArrayList<>();
}

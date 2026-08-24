package uz.urspi.newurspi.rental;

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
@Table(name = TableName.RENTALS)
@SQLRestriction("status <> 'DELETED' ")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Rental extends BaseEntity {

    @Column(nullable = false)
    private String titleUz;
    private String titleRu;
    private String titleEn;

    private String addressUz;
    private String addressRu;
    private String addressEn;

    private String priceUz;
    private String priceRu;
    private String priceEn;

    private String phoneNumber;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "rental_images", joinColumns = @JoinColumn(name = "rental_id"))
    @Column(name = "image_link")
    private List<String> imageLinks = new ArrayList<>();
}

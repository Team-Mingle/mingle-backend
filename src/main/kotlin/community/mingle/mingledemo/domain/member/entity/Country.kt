package community.mingle.mingledemo.domain.member.entity

import jakarta.persistence.*

@Entity
@Table(name = "country")
class Country(
    @Id
    @Column(name = "country", nullable = false, length = 45)
    var country: String,
) {
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "country")
    var universities = mutableListOf<University>()
}
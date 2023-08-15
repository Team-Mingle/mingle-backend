package community.mingle.mingledemo.domain.member.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "country")
open class Country(
    @Id
    @Column(name = "country", nullable = false, length = 45)
    var country: String,
)
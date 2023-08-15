package community.mingle.mingledemo.domain.member.entity

import jakarta.persistence.*

@Entity
@Table(name = "university")
open class University(
    @Column(name = "email_domain", nullable = false, length = 100)
    var emailDomain: String,

    @Column(name = "name", nullable = false)
    var name: String,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "country", nullable = false)
    var country: Country,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Int? = null
}
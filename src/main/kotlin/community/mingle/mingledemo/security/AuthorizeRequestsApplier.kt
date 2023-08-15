package community.mingle.mingledemo.security

import org.springframework.security.config.annotation.web.builders.HttpSecurity

typealias AuthorizeRequestsApplier = (HttpSecurity) -> (Unit)

